package com.nexio.blog.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nexio.blog.exception.dto.BlogExceptionDto;

/**
 * @author Khaled Ghali
 */
@ControllerAdvice
public class BlogExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BlogException.class)
  protected ResponseEntity<Object> postNotFoundHandler(BlogException ex, WebRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    BlogExceptionDto blogExceptionDto = new BlogExceptionDto(ex.getHttpStatus().toString(), ex.getMessage());
    return handleExceptionInternal(ex, blogExceptionDto, headers, ex.getHttpStatus(), request);
  }
}