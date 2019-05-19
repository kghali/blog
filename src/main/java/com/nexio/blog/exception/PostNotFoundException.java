package com.nexio.blog.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Khaled Ghali
 */
public class PostNotFoundException extends BlogException {
  public PostNotFoundException(Long id) {
    super(HttpStatus.NOT_FOUND, "Post with id " + id + " not found");
  }
}
