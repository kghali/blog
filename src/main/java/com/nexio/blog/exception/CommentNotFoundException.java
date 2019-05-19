package com.nexio.blog.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Khaled Ghali
 */
public class CommentNotFoundException extends BlogException {

  public CommentNotFoundException(Long id) {
    super(HttpStatus.NOT_FOUND, "Comment with id " + id + " not found");
  }
}
