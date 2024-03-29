package com.empresaRest.handler;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.empresaRest.exception.CpfException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.empresaRest.exception.LimitAgeException;
import com.empresaRest.exception.ResourceNotFoundException;
import com.empresaRest.validation.GenericErrorDetails;
import com.empresaRest.validation.ResourceNotFoundDetails;
import com.empresaRest.validation.ValidationErrorDetails;

@ControllerAdvice
public class EmpresaExceptionHandler {

	@ExceptionHandler(CpfException.class)
	public ResponseEntity<?> errorCPFDuplicado(ConstraintViolationException e) {
		ValidationErrorDetails veDetails = ValidationErrorDetails.Builder.newBuilder().timestamp(LocalDate.now())
				.title("Erros de validações.")
				.status(HttpStatus.BAD_REQUEST.value())
				.detail("Erros de validações.")
				.developerMessage(e.getClass()
				.getName()).field(e.getLocalizedMessage())
				.fieldMessage(e.getMessage())
				.build();
		return new ResponseEntity<>(veDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(LimitAgeException.class)
	public ResponseEntity<?> LimitAge(LimitAgeException e) {
		ValidationErrorDetails veDetails = ValidationErrorDetails.Builder.newBuilder().timestamp(LocalDate.now())
				.title("Erros de validações.")
				.status(HttpStatus.BAD_REQUEST.value())
				.detail("Erros de validações.")
				.developerMessage(e.getClass()
				.getName()).field(e.getLocalizedMessage())
				.fieldMessage(e.getMessage())
				.build();
		return new ResponseEntity<>(veDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> errorNullPointer(NullPointerException e) {
		GenericErrorDetails grDetails = GenericErrorDetails.Builder.newBuilder().timestamp(LocalDate.now())
				.title("Ocorreu um erro interno. Favor entrar em contato com o administrador")
				.timestamp(LocalDate.now())
				.detail(e.getMessage())
				.developerMessage(e.getClass().getName())
				.stackTrace(e.getStackTrace())
				.build();
		return new ResponseEntity<>(grDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	  public ResponseEntity<?>  handlerResouceNotFoundException1(ResourceNotFoundException e) {
		ResourceNotFoundDetails rnfDetails = ResourceNotFoundDetails.Builder
				.newBuilder()
				.timestamp(LocalDate.now())
				.title("Recurso não encontrado")
				.detail(e.getMessage())
				.developerMessage(e.getClass().getName())
				.stackTrace(e.getStackTrace())
				.build();
		return new ResponseEntity<>(rnfDetails, HttpStatus.BAD_REQUEST);
	}
	public ResponseEntity<?> handlerResouceNotFoundException(ResourceNotFoundException e) {
		ResourceNotFoundDetails rnfDetails = ResourceNotFoundDetails.Builder.newBuilder().timestamp(LocalDate.now())
				.title("Recurso não encontrado")
				.developerMessage(e.getClass()
				.getName())
				.status(404)
				.detail(e.getMessage())
				.build();
		return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handlerResouceNotFoundException(MethodArgumentNotValidException e) {

		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		String fields = fieldErrors.stream()
				.map(FieldError::getField)
				.collect(Collectors.joining(","));
		
		String fieldMessages = fieldErrors.stream()
				.map(FieldError::getDefaultMessage)
				.collect(Collectors.joining(","));

		ValidationErrorDetails veDetails = ValidationErrorDetails.Builder.newBuilder().timestamp(LocalDate.now())
				.title("Erros de validações.")
				.status(HttpStatus.BAD_REQUEST.value())
				.detail("Erros de validações.")
				.developerMessage(e.getClass()
				.getName()).field(fields)
				.fieldMessage(fieldMessages)
				.build();
		return new ResponseEntity<>(veDetails, HttpStatus.BAD_REQUEST);
	}
}
