package com.nexio.blog.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Khaled Ghali
 */
public class NotCommentOfPostException extends BlogException {
  public NotCommentOfPostException(Long idPost, Long idComment) {
    super(HttpStatus.NOT_FOUND, "Comment with id " + idComment + " is not related to " + idPost);
  }
}
