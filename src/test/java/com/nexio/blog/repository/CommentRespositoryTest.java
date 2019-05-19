package com.nexio.blog.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.nexio.blog.persistence.model.Comment;
import com.nexio.blog.persistence.model.Post;
import com.nexio.blog.persistence.repository.CommentRepository;
import com.nexio.blog.persistence.repository.PostRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRespositoryTest {

  @Autowired
  TestEntityManager entityManager;

  @Autowired
  CommentRepository commentRepository;

  @Autowired
  PostRepository postRepository;

  @Test
  public void testFindAllCommentsForPostId(){
    final Post post = new Post("post to test find all comments", "post content");
    Comment comment1 = new Comment("comment 1", post);
    Comment comment2 = new Comment("comment 2", post);
    List<Comment> commentsList = new ArrayList<>();
    commentsList.add(comment1);
    commentsList.add(comment2);

    // Run the test
    final Post result = entityManager.persistAndFlush(post);
    entityManager.persist(comment1);
    entityManager.persist(comment2);

    assertEquals(commentRepository.findAllCommentsForPostId(result.getId()), commentsList);
  }

}
