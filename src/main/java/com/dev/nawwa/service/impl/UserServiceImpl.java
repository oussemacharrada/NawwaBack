package com.dev.nawwa.service.impl;


import static com.dev.nawwa.constant.FileConstant.DEFAULT_USER_IMAGE_PATH;
import static com.dev.nawwa.constant.FileConstant.DIRECTORY_CREATED;
import static com.dev.nawwa.constant.FileConstant.DOT;
import static com.dev.nawwa.constant.FileConstant.FILE_SAVED_IN_FILE_SYSTEM;
import static com.dev.nawwa.constant.FileConstant.JPG_EXTENSION;
import static com.dev.nawwa.constant.FileConstant.USER_FOLDER;
import static com.dev.nawwa.constant.FileConstant.USER_IMAGE_PATH;
import static com.dev.nawwa.constant.FileConstant.FORWARD_SLASH;
import static com.dev.nawwa.constant.UserImplContant.EMAIL_ALREADY_EXISTS;
import static com.dev.nawwa.constant.UserImplContant.FOUND_USER_WITH_USERNAME;
import static com.dev.nawwa.constant.UserImplContant.NO_USER_FOUND_BY_EMAIL;
import static com.dev.nawwa.constant.UserImplContant.NO_USER_FOUND_BY_USERNAME;
import static com.dev.nawwa.constant.UserImplContant.USERNAME_ALREADY_EXISTS;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.dev.nawwa.service.EmailService;
import com.dev.nawwa.service.LoginAttemptService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dev.nawwa.domain.User;
import com.dev.nawwa.domain.UserPrincipal;
import com.dev.nawwa.enumeration.Role;
import com.dev.nawwa.exception.domain.EmailExistException;
import com.dev.nawwa.exception.domain.EmailNotFoundException;
import com.dev.nawwa.exception.domain.UserNotFoundException;
import com.dev.nawwa.exception.domain.UsernameExistException;
import com.dev.nawwa.repository.UserRepository;
import com.dev.nawwa.service.UserService;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService
{
	

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private LoginAttemptService loginAttemptService;
	@Autowired
	private EmailService emailService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = userRepository.findUserByUserName(username);
		if(user == null)
		{
			LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
			throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
		}
		else
		{
			validateUserLoginAttempt(user);
			user.setLastLoginDateDisplay(user.getLastLoginDate());
			user.setLastLoginDate(new Date());
			userRepository.save(user);

			UserPrincipal userPrincipal = new UserPrincipal(user);
			LOGGER.info(FOUND_USER_WITH_USERNAME + username);
			return userPrincipal;
		}

	}

	private void validateUserLoginAttempt(User user)
	{
		if(user.isNotLocked()) {
			if(loginAttemptService.hasExceededMaxAttempts(user.getUserName())) {
				user.setNotLocked(false);
			}else {
				user.setNotLocked(true);
			}
		}else {
			loginAttemptService.evictUserFromLoginAttemptCache(user.getUserName());
		}
	}

	@Override
	public User register( String userName, String email) throws UserNotFoundException, UsernameExistException, EmailExistException
	{
		validateNewUsernameAndEmail(StringUtils.EMPTY, userName, email);
		User user = new User();
		user.setUserId(generateUserId());
		
		String password = generatePassword();
		String encodedPassword = encodePassword(password);
		user.setPassword(encodedPassword);
		user.setUserName(userName);
		user.setEmail(email);
		user.setJoinDate(new Date());
		user.setActive(true);
		user.setNotLocked(true);
		user.setRole(Role.ROLE_USER.name());
		user.setAuthorities(Role.ROLE_USER.getAuthorities());
		System.out.println("temp profile url set : "+ getTemporaryProfileImageUrl(userName));
		user.setProfileImageUrl(getTemporaryProfileImageUrl(userName));
		userRepository.save(user);
		LOGGER.info("New user password: "+ password);
		emailService.sendNewPasswordEmail("firstName", email, password);
		return user;
	}

	@Override
	public User addUser(String username, String email, String role,
	      boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException
	{
		validateNewUsernameAndEmail(StringUtils.EMPTY, username, email);
		User user = new User();
		String password = generatePassword();
		user.setUserId(generateUserId());

		user.setJoinDate(new Date());
		user.setUserName(username);
		user.setEmail(email);
		user.setPassword(encodePassword(password));
		user.setActive(isActive);
		user.setNotLocked(isNonLocked);
		user.setRole(getRoleEnumName(role).name());
		user.setAuthorities(getRoleEnumName(role).getAuthorities());
		user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
		userRepository.save(user);
		saveProfileImage(user, profileImage);
		return user;
	}

	

	@Override
	public User updateUser(String currentUsername, String newUsername,
	      String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException
	{
		User currentUser = validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);

		currentUser.setUserName(newUsername);
		currentUser.setEmail(newEmail);
		currentUser.setActive(isActive);
		currentUser.setNotLocked(isNonLocked);
		currentUser.setRole(getRoleEnumName(role).name());
		currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
		userRepository.save(currentUser);
		saveProfileImage(currentUser, profileImage);
		return currentUser;
	}

	@Override
	public void deleteUser(Long id)
	{
		userRepository.deleteById(id);
	}

	@Override
	public void resetPassword(String email) throws EmailNotFoundException
	{
		User user = findUserByEmail(email);
		if(user == null) {
			throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL + email);
		}
		String password = generatePassword();
		user.setPassword(encodePassword(password));
		userRepository.save(user);
		emailService.sendNewPasswordEmail("user.getFirstName()", user.getEmail(), password);
	}

	@Override
	public User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException
	{
		User user = validateNewUsernameAndEmail(username, null, null);
		saveProfileImage(user, profileImage);
		return user;
	}

	@Override
	public List<User> getUsers()
	{
		return userRepository.findAll();
	}

	@Override
	public User findByUserName(String userName)
	{
		return userRepository.findUserByUserName(userName);
	}

	@Override
	public User findUserByEmail(String email)
	{
		return userRepository.findUserByEmail(email);
	}

	private String getTemporaryProfileImageUrl(String username)
	{
		// return http://localhost:8081/
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
	}

	private String encodePassword(String password)
	{
		return passwordEncoder.encode(password);
	}

	private String generatePassword()
	{
		return RandomStringUtils.randomAlphanumeric(10);
	}

	private String generateUserId()
	{
		return RandomStringUtils.randomNumeric(10);
	}

	private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String email) throws UserNotFoundException, UsernameExistException, EmailExistException
	{

		User userByNewUsername = userRepository.findUserByUserName(newUsername);
		User userByNewEmail = userRepository.findUserByEmail(email);
		
		if(StringUtils.isNotBlank(currentUsername))
		{
			User currentUser = userRepository.findUserByUserName(currentUsername);
			if(currentUser == null)
			{
				throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
			}
			if(userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId()))
			{
				throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
			}
			if(userByNewEmail != null && !currentUser.getEmail().equals(userByNewEmail.getEmail()))
			{
				throw new EmailExistException(EMAIL_ALREADY_EXISTS);
			}
			return currentUser;
		}
		else
		{
			if(userByNewUsername != null)
			{
				throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
			}
			if(userByNewEmail != null)
			{
				throw new EmailExistException(EMAIL_ALREADY_EXISTS);
			}
			return null;
		}
	}

	
	private void saveProfileImage(User user, MultipartFile profileImage) throws IOException
	{
		if(profileImage != null) {
			Path userFolder = Paths.get(USER_FOLDER + user.getUserName()).toAbsolutePath().normalize();
			if(!Files.exists(userFolder)){
				Files.createDirectories(userFolder);
				LOGGER.info(DIRECTORY_CREATED + userFolder);
			}
			
			/** Delete the existing user profile image if exists */
			Files.deleteIfExists(Paths.get(userFolder + user.getUserName() + DOT + JPG_EXTENSION));
			/** copy actual profile data to output location */
			Files.copy(profileImage.getInputStream(), userFolder.resolve( user.getUserName() + DOT + JPG_EXTENSION), REPLACE_EXISTING);
			user.setProfileImageUrl(setProfileImageUrl(user.getUserName()));
			userRepository.save(user);
			LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
		}
	}

	private String setProfileImageUrl(String userName)
	{
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH + userName + FORWARD_SLASH + userName + DOT + JPG_EXTENSION).toUriString();
	}

	private Role getRoleEnumName(String role)
	{
		return Role.valueOf(role.toUpperCase());
	}

	
}
