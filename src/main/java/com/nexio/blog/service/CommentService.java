package com.nexio.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.nexio.blog.persistence.model.Comment;

/**
 * @author Khaled Ghali
 */
public interface CommentService {

  Comment save(Comment comment);

  @Transactional(readOnly = true)
  List<Comment> findAll();

  @Transactional(readOnly = true)
  Optional<Comment> findOne(Long id);

  @Transactional
  void delete(Long id);

  List<Comment> findAllCommentsForPostId(Long id);
}
