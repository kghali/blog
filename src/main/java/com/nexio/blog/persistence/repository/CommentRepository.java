package com.nexio.blog.persistence.repository;

/**
 * @author Khaled Ghali
 */
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nexio.blog.persistence.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

  @Query("SELECT c FROM Comment c WHERE c.post.id = :id")
  List<Comment> findAllCommentsForPostId(@Param("id") Long id);
}
