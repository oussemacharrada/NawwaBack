package com.dev.nawwa.exception.domain;

import java.io.IOException;
import java.util.Objects;

import javax.persistence.NoResultException;

import com.dev.nawwa.domain.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.TokenExpiredException;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController
{
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administrator";
	private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
	private static final String INTERNAL_SERVER_ERROR_MSG = "An error occured while processing the request";
	private static final String INCORRECT_CREDENTIALS = "Username / password incorrect. Please try again";
	private static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administrator";
	private static final String ERROR_PROCESSING_FILE = "Error occured while processing file";
	private static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";
	public static final String ERROR_PATH = "/error";
	
	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<ServiceResponse> accountDisabledException(){
		return createHttpResponse(HttpStatus.BAD_REQUEST, ACCOUNT_DISABLED);
	}
	
	@ExceptionHandler(LockedException.class)
	public ResponseEntity<ServiceResponse> accountLockedException(){
		return createHttpResponse(HttpStatus.UNAUTHORIZED, ACCOUNT_LOCKED);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ServiceResponse> internalServerErrorException(Exception ex){
		LOGGER.error(ex.getMessage());
		return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ServiceResponse> incorrectCredentialsException(){
		return createHttpResponse(HttpStatus.BAD_REQUEST, INCORRECT_CREDENTIALS);
	}
	
	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<ServiceResponse> tokenExpiredException(TokenExpiredException ex){
		return createHttpResponse(HttpStatus.UNAUTHORIZED, ex.getMessage().toUpperCase());
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ServiceResponse> accessDeniedException(){
		return createHttpResponse(HttpStatus.FORBIDDEN, NOT_ENOUGH_PERMISSION);
	}
	
	
	@ExceptionHandler(EmailExistException.class)
	public ResponseEntity<ServiceResponse> emailExistException(EmailExistException ex){
		return createHttpResponse(HttpStatus.BAD_REQUEST, ex.getMessage().toUpperCase());
	}
	
	@ExceptionHandler(UsernameExistException.class)
	public ResponseEntity<ServiceResponse> usernameExistException(UsernameExistException ex){
		return createHttpResponse(HttpStatus.BAD_REQUEST, ex.getMessage().toUpperCase());
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ServiceResponse> userNotFoundException(UserNotFoundException ex){
		return createHttpResponse(HttpStatus.BAD_REQUEST, ex.getMessage().toUpperCase());
	}
	
	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<ServiceResponse> emailNotFoundException(EmailNotFoundException ex){
		return createHttpResponse(HttpStatus.BAD_REQUEST, ex.getMessage().toUpperCase());
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ServiceResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException ex){
		HttpMethod httpMethod = Objects.requireNonNull(ex.getSupportedHttpMethods()).iterator().next();
		return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, httpMethod));
	}
	
	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<ServiceResponse> notFoundException(NoResultException ex){
		LOGGER.error(ex.getMessage());
		return createHttpResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ServiceResponse> notFoundException(IOException ex){
		LOGGER.error(ex.getMessage());
		return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
	}
	
	private ResponseEntity<ServiceResponse> createHttpResponse(HttpStatus httpStatus, String message ){
		ServiceResponse serviceResponse = new ServiceResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase());
		return new ResponseEntity<ServiceResponse>(serviceResponse, httpStatus); //sending serviceResponse as a body for ResponseEntity with httpStatus as second param
	}

	
	
	
	/*
	 * Handle default error request using ErrorController interface
	 * */
	
	@RequestMapping(value = ERROR_PATH)
	public ResponseEntity<ServiceResponse> notFound404(){
		return createHttpResponse(HttpStatus.NOT_FOUND, "There is no mapping for this URL");
	}
	@Override
	public String getErrorPath()
	{
		return ERROR_PATH;
	}

}
