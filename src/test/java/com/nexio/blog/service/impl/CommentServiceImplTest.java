package com.nexio.blog.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.nexio.blog.persistence.model.Comment;
import com.nexio.blog.persistence.model.Post;
import com.nexio.blog.persistence.repository.CommentRepository;

public class CommentServiceImplTest {

  private static final List<Post> POST_LIST = Arrays.asList(
      new Post(1L, "first post", "first post content"),
      new Post(2L, "second post", "second post content"),
      new Post(3L, "third post", "third post content"));

  private static final List<Comment> COMMENT_LIST = Arrays.asList(
      new Comment(1L, "first comment", POST_LIST.get(0)),
      new Comment(2L, "second comment", POST_LIST.get(0)));

  private CommentServiceImpl commentServiceImplUnderTest;

  @Before
  public void setUp() {
    commentServiceImplUnderTest = new CommentServiceImpl();
    commentServiceImplUnderTest.commentRepository = mock(CommentRepository.class);
  }

  @Test
  public void testSave() {
    // Setup 
    final Comment comment = new Comment("new comment", POST_LIST.get(0));
    final Comment expectedResult = new Comment("new comment", POST_LIST.get(0));;
    when(commentServiceImplUnderTest.commentRepository.save(comment)).thenReturn(expectedResult);

    // Run the test 
    final Comment result = commentServiceImplUnderTest.save(comment);

    // Verify the results 
    assertEquals(expectedResult, result);
  }

  @Test
  public void testFindAll() {
    // Setup 
    final List<Comment> expectedResult = COMMENT_LIST;
    when(commentServiceImplUnderTest.commentRepository.findAll()).thenReturn(COMMENT_LIST);

    // Run the test 
    final List<Comment> result = commentServiceImplUnderTest.findAll();

    // Verify the results 
    assertEquals(expectedResult, result);
  }

  @Test
  public void testFindOne() {
    // Setup 
    final Long id = 1L;
    final Optional<Comment> expectedResult = Optional.of(COMMENT_LIST.get(0));
    when(commentServiceImplUnderTest.commentRepository.findById(id)).thenReturn(expectedResult);

    // Run the test 
    final Optional<Comment> result = commentServiceImplUnderTest.findOne(id);

    // Verify the results 
    assertEquals(expectedResult, result);
  }

  @Test
  public void testDelete() {
    // Setup 
    final Long id = 1L;

    // Run the test 
    commentServiceImplUnderTest.delete(id);

    // Verify the results 
    verify(commentServiceImplUnderTest.commentRepository).deleteById(id);
  }

  @Test
  public void testFindAllComments() {
    // Setup
    final Long id = 1L;
    final List<Comment> expectedResult = COMMENT_LIST;
    when(commentServiceImplUnderTest.findAllCommentsForPostId(1L)).thenReturn(expectedResult);

    // Run the test
    final List<Comment> result = commentServiceImplUnderTest.findAllCommentsForPostId(id);

    // Verify the results
    assertEquals(expectedResult, result);
  }
}
