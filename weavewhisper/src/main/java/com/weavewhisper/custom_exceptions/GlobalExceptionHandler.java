package com.weavewhisper.custom_exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.weavewhisper.dtos.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, e.getMessage()));
	}

	
	@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
	private ResponseEntity<?> handleIncorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, e.getMessage()));
	}

	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<?> handleAmazonS3Exception(AmazonS3Exception e) {
		return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(false, e.getErrorMessage()));
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, e.getMessage()));
	}

	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<?> handleDuplicateEmailException(DuplicateEmailException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false, e.getMessage()));
	}
	
	@ExceptionHandler(DuplicateWishListException.class)
	public ResponseEntity<?> handleDuplicateWishListException(DuplicateWishListException e){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false, e.getMessage()));
	}
	
	@ExceptionHandler(DuplicateProductNameException.class)
	public ResponseEntity<?> handleDuplicateProductNameException(DuplicateProductNameException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false, e.getMessage()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		Map<String, String> hashMap = new HashMap<>();

		e.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			hashMap.put(fieldName, message);
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashMap);
	}

	
	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ResponseEntity<?> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, e.getMessage()));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false, "Something went wrong!"));
	}

}
