package com.example.yuta9.exercisemanagebackend.controller.exception;

import com.example.yuta9.exercisemanagebackend.exception.NotFoundException;
import com.example.yuta9.exercisemanagebackend.exception.RequestParamException;
import com.example.yuta9.exercisemanagebackend.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;

/** エラーハンドリングコントローラー */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  public RestExceptionHandler() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return super.handleHttpRequestMethodNotSupported(ex, headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return super.handleHttpMediaTypeNotSupported(ex, headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
      HttpMediaTypeNotAcceptableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return super.handleHttpMediaTypeNotAcceptable(ex, headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleMissingPathVariable(
      MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return super.handleMissingPathVariable(ex, headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return super.handleMissingServletRequestParameter(ex, headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleServletRequestBindingException(
      ServletRequestBindingException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return super.handleServletRequestBindingException(ex, headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleConversionNotSupported(
      ConversionNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return super.handleConversionNotSupported(ex, headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return super.handleTypeMismatch(ex, headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return super.handleHttpMessageNotReadable(ex, headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotWritable(
      HttpMessageNotWritableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return super.handleHttpMessageNotWritable(ex, headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    ErrorResponse body = createErrorResponse(ex.getBindingResult());
    return handleExceptionInternal(ex, body, headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
      MissingServletRequestPartException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return super.handleMissingServletRequestPart(ex, headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleBindException(
      BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    ErrorResponse body = createErrorResponse(ex.getBindingResult());
    return handleExceptionInternal(ex, body, headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return super.handleNoHandlerFoundException(ex, headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
      AsyncRequestTimeoutException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest webRequest) {
    return super.handleAsyncRequestTimeoutException(ex, headers, status, webRequest);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
    if (!(body instanceof ErrorResponse)) {
      body = new ErrorResponse(status.getReasonPhrase(), "", ex.getMessage());
    }
    return new ResponseEntity<>(body, headers, status);
  }

  /**
   * BindingResult用エラーレスポンスを生成
   *
   * @param bindingResult
   * @return
   */
  private ErrorResponse createErrorResponse(BindingResult bindingResult) {
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    StringBuilder errorDetailStr = new StringBuilder();
    fieldErrors.forEach(
        fieldError ->
            errorDetailStr
                .append(fieldError.getField())
                .append(": ")
                .append(fieldError.getDefaultMessage())
                .append("; "));

    return new ErrorResponse("Bad Request", errorDetailStr.toString(), "");
  }

  /**
   * アノテーションバリデーションエラーハンドル
   *
   * @param e
   * @param request
   * @return
   */
  @ExceptionHandler({ConstraintViolationException.class, RequestParamException.class})
  public ResponseEntity<Object> handleConstraintViolation(Exception e, WebRequest request) {
    log.warn(e.getMessage());
    HttpHeaders headers = new HttpHeaders();
    ErrorResponse body = new ErrorResponse("Bad Request", e.getMessage(), "");
    HttpStatus status = HttpStatus.BAD_REQUEST;
    return handleExceptionInternal(e, body, headers, status, request);
  }

  /**
   * 404エラーハンドル
   *
   * @param e
   * @param request
   * @return
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Object> handle404Exception(NotFoundException e, WebRequest request) {
    log.warn(e.getMessage());
    HttpHeaders headers = new HttpHeaders();
    ErrorResponse body = new ErrorResponse("Not Found", e.getMessage(), "");
    HttpStatus status = HttpStatus.NOT_FOUND;
    return handleExceptionInternal(e, body, headers, status, request);
  }

  /**
   * DBコミット時の一意制約違反時のエラーハンドル
   *
   * @param e
   * @param request
   * @return
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handle409Exception(
      DataIntegrityViolationException e, WebRequest request) {
    log.warn(e.getMessage());
    HttpHeaders headers = new HttpHeaders();
    ErrorResponse body = new ErrorResponse("Conflict", e.getMessage(), "");
    HttpStatus status = HttpStatus.CONFLICT;
    return handleExceptionInternal(e, body, headers, status, request);
  }

  /**
   * 500エラーハンドル
   *
   * @param e
   * @param request
   * @return
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handle500Exception(Exception e, WebRequest request) {
    log.error(e.getMessage());
    HttpHeaders headers = new HttpHeaders();
    ErrorResponse body = new ErrorResponse("Internal Server Error", e.getMessage(), "");
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    return handleExceptionInternal(e, body, headers, status, request);
  }
}
