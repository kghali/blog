package com.nexio.blog.exception.dto;

/**
 * @author Khaled Ghali
 */
public class BlogExceptionDto {
  private String httpStatus;
  private String message;

  public BlogExceptionDto() {
  }

  public BlogExceptionDto(String httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  public String getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(String httpStatus) {
    this.httpStatus = httpStatus;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
