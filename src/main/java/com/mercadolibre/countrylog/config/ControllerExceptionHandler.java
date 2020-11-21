package com.mercadolibre.countrylog.config;

import javax.servlet.http.HttpServletRequest;

import com.mercadolibre.countrylog.exception.ApiError;
import com.mercadolibre.countrylog.exception.IPNotFoundException;
import com.mercadolibre.countrylog.exception.ServiceNotAvailableException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ApiError> noHandlerFoundException(HttpServletRequest req, NoHandlerFoundException ex) {
		ApiError apiError = new ApiError("route_not_found", String.format("Route %s not found", req.getRequestURI()), HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(apiError.getStatus())
				.body(apiError);
	}

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<ApiError> handleUnknownException(Exception e) {

		ApiError apiError = new ApiError("internal_error", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		return ResponseEntity.status(apiError.getStatus())
				.body(apiError);
	}
    
    @ExceptionHandler(IPNotFoundException.class)
	public ResponseEntity<ApiError> noHandlerFoundException(HttpServletRequest req, IPNotFoundException ex) {
		ApiError apiError = new ApiError("Error getting IP information", ex.getMessage(), HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(apiError.getStatus())
				.body(apiError);
	}

	@ExceptionHandler(ServiceNotAvailableException.class)
	public ResponseEntity<ApiError> noHandlerFoundException(HttpServletRequest req, ServiceNotAvailableException ex) {
		ApiError apiError = new ApiError("Service Not Available", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		return ResponseEntity.status(apiError.getStatus())
				.body(apiError);
	}
}
