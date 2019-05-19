package com.nexio.blog.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexio.blog.persistence.model.Comment;
import com.nexio.blog.persistence.model.Post;
import com.nexio.blog.persistence.repository.PostRepository;
import com.nexio.blog.service.PostService;

/**
 * @author Khaled Ghali
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {

  @Autowired
  PostRepository postRepository;

  @Override
  public Post save(Post post) {
    return postRepository.save(post);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Post> findAll() {
    List<Post> list = new ArrayList<>();
    Iterator<Post> it = postRepository.findAll().iterator();
    while (it.hasNext()) {
      list.add(it.next());
    }
    //reverse list to get recent post first
    list.sort(Comparator.comparing(Post::getId).reversed());
    return list;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Post> findOne(Long id) {
    return postRepository.findById(id);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    postRepository.deleteById(id);
  }
}
