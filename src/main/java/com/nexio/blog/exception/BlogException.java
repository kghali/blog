package com.nexio.blog.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Khaled Ghali
 */
public class BlogException extends RuntimeException {

  private HttpStatus httpStatus;
  public BlogException(HttpStatus httpStatus, String message) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }
}
