package com.nexio.blog.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.nexio.blog.persistence.model.Post;

/**
 * @author Khaled Ghali
 */
public interface PostRepository extends CrudRepository<Post, Long> {

}
