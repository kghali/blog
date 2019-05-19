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
import com.nexio.blog.persistence.repository.CommentRepository;
import com.nexio.blog.service.CommentService;

/**
 * @author Khaled Ghali
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

  @Autowired
  CommentRepository commentRepository;

  @Override
  public Comment save(Comment comment) {
    return commentRepository.save(comment);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Comment> findAll() {
    List<Comment> list = new ArrayList<>();
    Iterator<Comment> it = commentRepository.findAll().iterator();
    while (it.hasNext()) {
      list.add(it.next());
    }
    return list;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Comment> findOne(Long id) {
    return commentRepository.findById(id);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    commentRepository.deleteById(id);
  }


  @Override
  public List<Comment> findAllCommentsForPostId(Long id) {
    List<Comment> comments = commentRepository.findAllCommentsForPostId(id);
    comments.sort(Comparator.comparing(Comment::getId).reversed());
    return comments;
  }
}
