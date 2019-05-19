package com.nexio.blog.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexio.blog.persistence.model.Comment;
import com.nexio.blog.persistence.model.Post;
import com.nexio.blog.persistence.model.dto.CommentDto;
import com.nexio.blog.service.CommentService;
import com.nexio.blog.service.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentControllerTest {

  private static final String API_URL = "http://localhost:";

  @LocalServerPort
  private int port;
  TestRestTemplate restTemplate = new TestRestTemplate();
  HttpHeaders headers = new HttpHeaders();

  @Before
  public void setUp() {
    initMocks(this);
  }

  @Test
  public void testGetAllCommentsForPost() throws Exception {
    final Post post = new Post("first post", "post content");
    ResponseEntity<Post> responsePost = restTemplate.postForEntity(API_URL + port + "/post", post, Post.class);
    post.setId(responsePost.getBody().getId());
    final Comment comment1 = new Comment("comment 1 for first post", post);
    final Comment comment2 = new Comment("comment 2 for first post", post);
    ResponseEntity<Comment> responseCom1 = restTemplate.postForEntity(API_URL + port + "/post/" + post.getId() + "/comments",
        comment1, Comment.class);
    comment1.setId(responseCom1.getBody().getId());
    ResponseEntity<Comment> responseCom2 = restTemplate.postForEntity(API_URL + port + "/post/" + post.getId() + "/comments",
        comment2, Comment.class);
    comment2.setId(responseCom2.getBody().getId());
    final List<Comment> commentList = Arrays.asList(comment1, comment2);

    ResponseEntity<String> response = restTemplate
        .getForEntity(API_URL + port + "/post/" + responsePost.getBody().getId() + "/comments", String.class);

    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode responseJson = objectMapper.readTree(response.getBody());
    assertThat(responseJson.isMissingNode(), is(false));
    assertThat(responseJson.toString(), equalTo(objectMapper.writeValueAsString(commentList)));
  }

  @Test
  public void testGetSingleComment() throws Exception {
    final Post post = new Post("first post", "post content");
    ResponseEntity<Post> responsePost = restTemplate.postForEntity(API_URL + port + "/post", post, Post.class);
    post.setId(responsePost.getBody().getId());
    final Comment comment1 = new Comment("comment 1 for first post", post);
    ResponseEntity<Comment> responseCom1 = restTemplate.postForEntity(API_URL + port + "/post/" + post.getId() + "/comments",
        comment1, Comment.class);
    comment1.setId(responseCom1.getBody().getId());

    assertThat(responseCom1.getStatusCode(), equalTo(HttpStatus.CREATED));
    assertEquals("comment 1 for first post", responseCom1.getBody().getMessage());
    comment1.setId(responseCom1.getBody().getId());

    ResponseEntity<Comment> response = restTemplate
        .getForEntity(API_URL + port + "/post/" + post.getId() + "/comments/" + comment1.getId(), Comment.class);

    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    assertEquals(comment1.getId(), response.getBody().getId());
    assertEquals("comment 1 for first post", response.getBody().getMessage());
  }

  @Test
  public void testCreateComment() {
    final Post post = new Post("first post", "post content");
    ResponseEntity<Post> responsePost = restTemplate.postForEntity(API_URL + port + "/post", post, Post.class);
    post.setId(responsePost.getBody().getId());
    final Comment comment1 = new Comment("comment 1 for first post", post);
    ResponseEntity<Comment> responseCom1 = restTemplate.postForEntity(API_URL + port + "/post/" + post.getId() + "/comments",
        comment1, Comment.class);
    comment1.setId(responseCom1.getBody().getId());

    assertThat(responseCom1.getStatusCode(), equalTo(HttpStatus.CREATED));
    assertEquals("comment 1 for first post", responseCom1.getBody().getMessage());
    assertEquals(post.toString(), responseCom1.getBody().getPost().toString());
  }

  @Test
  public void testUpdateComment() {
    final Post post = new Post("first post", "post content");
    ResponseEntity<Post> responsePost = restTemplate.postForEntity(API_URL + port + "/post", post, Post.class);
    post.setId(responsePost.getBody().getId());
    final Comment comment1 = new Comment("comment 1 for first post", post);
    ResponseEntity<Comment> responseCom1 = restTemplate.postForEntity(API_URL + port + "/post/" + post.getId() + "/comments",
        comment1, Comment.class);
    comment1.setId(responseCom1.getBody().getId());

    assertThat(responsePost.getStatusCode(), equalTo(HttpStatus.CREATED));
    assertEquals("first post", responsePost.getBody().getTitle());
    assertEquals("post content", responsePost.getBody().getContent());

    comment1.setMessage("edited comment 1 for first post");
    comment1.setId(responseCom1.getBody().getId());
    HttpEntity<Comment> requestEntity = new HttpEntity<>(comment1);
    ResponseEntity<Comment> responsePut = restTemplate.exchange(API_URL + port + "/post/" + post.getId() + "/comments",
        HttpMethod.PUT, requestEntity, Comment.class);

    assertThat(responsePut.getStatusCode(), equalTo(HttpStatus.OK));

    assertEquals("edited comment 1 for first post", responsePut.getBody().getMessage());
  }

  @Test
  public void testDeleteComment() {


    final Post post = new Post("first post", "post content");
    ResponseEntity<Post> responsePost = restTemplate.postForEntity(API_URL + port + "/post", post, Post.class);
    post.setId(responsePost.getBody().getId());
    final Comment comment1 = new Comment("comment 1 for first post", post);
    ResponseEntity<Comment> responseCom1 = restTemplate.postForEntity(API_URL + port + "/post/" + post.getId() + "/comments",
        comment1, Comment.class);
    comment1.setId(responseCom1.getBody().getId());

    assertThat(responseCom1.getStatusCode(), equalTo(HttpStatus.CREATED));
    assertEquals("comment 1 for first post", responseCom1.getBody().getMessage());

    ResponseEntity<Comment> responseDelete = restTemplate.exchange(API_URL + port + "/post/" + post.getId() + "/comments/" + comment1.getId(), HttpMethod.DELETE, null, Comment.class);

    assertThat(responseDelete.getStatusCode(), equalTo(HttpStatus.OK));
  }
}
