package com.appsdeveloperblog.estore.productsservice.core.errorhandling;

import java.util.Date;

import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ProductServiceErrorHandler {

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<Object> handleIllegalStateException(
			IllegalStateException e, WebRequest request) {
		return createErrorResponse(e);
	}
	
	@ExceptionHandler(CommandExecutionException.class)
	public ResponseEntity<Object> handleCommandExecutionException(
			CommandExecutionException e, WebRequest request) {
		return createErrorResponse(e);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleOtherException(
			Exception e, WebRequest request) {
		return createErrorResponse(e);
	}
	
	private ResponseEntity<Object> createErrorResponse(Exception e) {
		return createErrorResponse(e, null);
	}
	
	private ResponseEntity<Object> createErrorResponse(Exception e, HttpStatus httpStatus) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), e.getMessage());
		return new ResponseEntity<>(
				errorMessage, new HttpHeaders(),
				(httpStatus != null) ? httpStatus : HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
