package com.dev.nawwa.controller;

import static com.dev.nawwa.constant.FileConstant.TEMP_PROFILE_IMAGE_BASE_URL;
import static com.dev.nawwa.constant.FileConstant.USER_FOLDER;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.dev.nawwa.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dev.nawwa.constant.FileConstant;
import com.dev.nawwa.constant.SecurityConstant;
import com.dev.nawwa.domain.ServiceResponse;
import com.dev.nawwa.domain.UserPrincipal;
import com.dev.nawwa.exception.domain.EmailExistException;
import com.dev.nawwa.exception.domain.EmailNotFoundException;
import com.dev.nawwa.exception.domain.ExceptionHandling;
import com.dev.nawwa.exception.domain.UserNotFoundException;
import com.dev.nawwa.exception.domain.UsernameExistException;
import com.dev.nawwa.service.UserService;
import com.dev.nawwa.utility.JWTTokenProvider;

@RestController
@RequestMapping(path = { "/", "/user" })
public class UserController extends ExceptionHandling
{

	private static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";

	private static final String EMAIL_SENT = "An email with the new password was sent to: ";

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTTokenProvider jwtTokenProvider;
	
	@PostMapping(value = "/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException{
		User newUser = userService.register(user.getUserName(), user.getEmail());
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user){
		authenticate(user.getUserName(), user.getPassword()); // it validates entered Username, Password (if correct or not)
		User loginUser = userService.findByUserName(user.getUserName());
		UserPrincipal userPrincipal = new UserPrincipal(loginUser);
		HttpHeaders jwtHeader = getJwtHeader(userPrincipal); // returns JWT Token with header
		return new ResponseEntity<User>(loginUser, jwtHeader, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<User> addNewUser(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("username") String username,
			@RequestParam("email") String email,
			@RequestParam("role") String role,
			@RequestParam("isNonLocked") String isNonLocked,
			@RequestParam("isActive") String isActive,
			@RequestParam(value = "profileImage", required = false) MultipartFile profileImage
			) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException{
		User newUser = userService.addUser(username, email, role, Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive), profileImage);
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<User> updateUser(
			@RequestParam("currentUsername") String currentUsername,
			@RequestParam("username") String username,
			@RequestParam("email") String email,
			@RequestParam("role") String role,
			@RequestParam("isNonLocked") String isNonLocked,
			@RequestParam("isActive") String isActive,
			@RequestParam(value = "profileImage", required = false) MultipartFile profileImage
			) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException{
		User updatedUser = userService.updateUser(currentUsername, username, email, role, Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive), profileImage);
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
	}
	
	@GetMapping("/find/{username}")
	public ResponseEntity<User> getUser(@PathVariable("username") String username){
		User user = userService.findByUserName(username);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> users = userService.getUsers();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping("/resetPassword/{email}")
	public ResponseEntity<ServiceResponse> resetPassword(@PathVariable("email") String email) throws EmailNotFoundException{
		userService.resetPassword(email);
		return  response(HttpStatus.OK, EMAIL_SENT+ email);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAnyAuthority('user:delete')") /** Only user: delete authority users can access this method*/
	public ResponseEntity<ServiceResponse> deleteUser(@PathVariable("id") Long id){
		userService.deleteUser(id);
		return response(HttpStatus.NO_CONTENT, USER_DELETED_SUCCESSFULLY);
	}
	
	@PostMapping("/updateProfileImage")
	public ResponseEntity<User> updateProfileImage(@RequestParam("username") String username,
			@RequestParam(value = "profileImage") MultipartFile profileImage
			) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException{
		User user = userService.updateProfileImage(username, profileImage);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping(path = "/image/{username}/{filename}", produces = IMAGE_JPEG_VALUE )
	public byte[] getProfileImage(@PathVariable("username") String username, @PathVariable("filename") String filename) throws IOException {
		return Files.readAllBytes(Paths.get(USER_FOLDER + username + FileConstant.FORWARD_SLASH + filename));
	}
	
	@GetMapping(path = "/image/profile/{username}", produces = IMAGE_JPEG_VALUE )
	public byte[] getTempProfileImage(@PathVariable("username") String username) throws IOException {
		/** Get temporary profile image from the robohash*/
		URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + username);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try(InputStream inputStream = url.openStream()){
			int bytesRead;
			byte[] chunk = new byte[1024];
			while((bytesRead = inputStream.read(chunk)) > 0) {
				byteArrayOutputStream.write(chunk, 0, bytesRead);
			}
		}
		return byteArrayOutputStream.toByteArray();
	}
	
	private ResponseEntity<ServiceResponse> response(HttpStatus httpStatus, String message)
	{
		ServiceResponse body = new ServiceResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message);
		return new ResponseEntity<ServiceResponse>(body, httpStatus);
	}

	private HttpHeaders getJwtHeader(UserPrincipal userPrincipal)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add(SecurityConstant.JWT_TOKEN_HEADER, jwtTokenProvider.generateJWTToken(userPrincipal));
		return headers;
	}

	private void authenticate(String userName, String password)
	{
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
	}
}
