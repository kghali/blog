package com.nexio.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.nexio.blog.persistence.model.Post;

/**
 * @author Khaled Ghali
 */
public interface PostService {

  Post save(Post post);

  @Transactional(readOnly = true)
  List<Post> findAll();

  @Transactional(readOnly = true)
  Optional<Post> findOne(Long id);

  @Transactional
  void delete(Long id);

}
