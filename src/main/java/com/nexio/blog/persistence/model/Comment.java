package com.nexio.blog.persistence.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Khaled Ghali
 */
@Entity
@Table(name = "comments", catalog = "blog")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Lob
  @Column(length=100000)
  private String message;

  @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,
      CascadeType.MERGE})
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;

  public Comment() {
  }

  public Comment(Long id, String message, Post post) {
    this.id = id;
    this.message = message;
    this.post = post;
  }

  public Comment(String message, Post post) {
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
    return "Comment{" +
        "id=" + id +
        ", message='" + message + '\'' +
        ", post=" + post +
        '}';
  }
}
