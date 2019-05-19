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
import com.nexio.blog.persistence.repository.PostRepository;

import javafx.geometry.Pos;

public class PostServiceImplTest {

  private static final List<Post> POST_LIST = Arrays.asList(
      new Post(1L, "first post", "first post content"),
      new Post(2L, "second post", "second post content"),
      new Post(3L, "third post", "third post content"));

  private static final List<Comment> COMMENT_LIST = Arrays.asList(
      new Comment(1L, "first comment", POST_LIST.get(0)),
      new Comment(2L, "second comment", POST_LIST.get(0)));

  private PostServiceImpl postServiceImplUnderTest;

  @Before
  public void setUp() {
    postServiceImplUnderTest = new PostServiceImpl();
    postServiceImplUnderTest.postRepository = mock(PostRepository.class);
  }

  @Test
  public void testSave() {
    // Setup 
    final Post post = new Post("test title", "test content");
    final Post expectedResult = new Post("test title", "test content");
    when(postServiceImplUnderTest.postRepository.save(post)).thenReturn(expectedResult);

    // Run the test 
    final Post result = postServiceImplUnderTest.save(post);

    // Verify the results 
    assertEquals(expectedResult, result);
  }

  @Test
  public void testFindAll() {
    // Setup 
    final List<Post> expectedResult = POST_LIST;
    when(postServiceImplUnderTest.postRepository.findAll()).thenReturn(expectedResult);

    // Run the test 
    final List<Post> result = postServiceImplUnderTest.findAll();

    // Verify the results 
    assertEquals(expectedResult, result);
  }

  @Test
  public void testFindOne() {
    // Setup 
    final Long id = 1L;
    final Post expectedResult = new Post(1L, "first post", "first post content");
    when(postServiceImplUnderTest.postRepository.findById(1L)).thenReturn(Optional.of(expectedResult));

    // Run the test 
    final Optional<Post> result = postServiceImplUnderTest.findOne(id);

    // Verify the results 
    assertEquals(Optional.of(expectedResult), result);
  }

  @Test
  public void testDelete() {
    // Setup 
    final Long id = 1L;

    // Run the test 
    postServiceImplUnderTest.delete(id);

    // Verify the results 
    verify(postServiceImplUnderTest.postRepository).deleteById(id);
  }

}
