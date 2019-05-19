package com.nexio.blog.persistence.model.dto;

import com.nexio.blog.persistence.model.Post;

/**
 * @author Khaled Ghali
 */
public class CommentDto {

  private Long id;
  private String message;
  private Post post;

  public CommentDto() {
  }

  public CommentDto(Long id, String message, Post post) {
    this.id = id;
    this.message = message;
    this.post = post;
  }

  public CommentDto(String message, Post post) {
    this.message = message;
    this.post = post;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Post getPost() {
    return post;
  }

  public void setPost(Post post) {
    this.post = post;
  }

  @Override
  public String toString() {
    return "CommentDto{" +
        "id=" + id +
        ", message='" + message + '\'' +
        ", post=" + post +
        '}';
  }
}
