package com.nexio.blog.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
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
import com.nexio.blog.persistence.model.Post;
import com.nexio.blog.persistence.model.dto.PostDto;
import com.nexio.blog.service.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {

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
  public void testGetAllPosts() throws Exception {
    ResponseEntity<String> response = restTemplate.getForEntity(API_URL + port + "/post", String.class);
    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode responseJson = objectMapper.readTree(response.getBody());
    assertThat(responseJson.isMissingNode(), is(false));
    assertThat(responseJson.toString(), equalTo("[]"));
  }

  @Test
  public void testCreatePost() {
    final Post post = new Post("first post", "post content");
    ResponseEntity<Post> response = restTemplate.postForEntity(API_URL + port + "/post", post, Post.class);

    assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    assertEquals("first post", response.getBody().getTitle());
    assertEquals("post content", response.getBody().getContent());
  }

  @Test
  public void testUpdatePost() {
    final Post post = new Post("first post", "post content");
    ResponseEntity<Post> response = restTemplate.postForEntity(API_URL + port + "/post", post, Post.class);

    assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    assertEquals("first post", response.getBody().getTitle());
    assertEquals("post content", response.getBody().getContent());

    post.setTitle("edited title");
    post.setId(response.getBody().getId());
    HttpEntity<Post> requestEntity = new HttpEntity<>(post);
    ResponseEntity<Post> responsePut = restTemplate.exchange(API_URL + port + "/post", HttpMethod.PUT, requestEntity, Post.class);

    assertThat(responsePut.getStatusCode(), equalTo(HttpStatus.OK));

    assertEquals("post content", responsePut.getBody().getContent());
    assertEquals("edited title", responsePut.getBody().getTitle());
  }

  @Test
  public void testGetSinglePost() {
    final Post post = new Post("post test single get", "post content");
    ResponseEntity<Post> response = restTemplate.postForEntity(API_URL + port + "/post", post, Post.class);

    assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    assertEquals("post test single get", response.getBody().getTitle());
    assertEquals("post content", response.getBody().getContent());
    post.setId(response.getBody().getId());

    response = restTemplate.getForEntity(API_URL + port + "/post/" + post.getId(), Post.class);

    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    assertEquals(post.getId(), response.getBody().getId());
    assertEquals("post test single get", response.getBody().getTitle());
    assertEquals("post content", response.getBody().getContent());
  }

  @Test
  public void testDeletePost() {
    final Post post = new Post("post test delete", "post content");
    ResponseEntity<Post> response = restTemplate.postForEntity(API_URL + port + "/post", post, Post.class);

    assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    assertEquals("post test delete", response.getBody().getTitle());
    assertEquals("post content", response.getBody().getContent());
    post.setId(response.getBody().getId());

    ResponseEntity<Post> responseDelete = restTemplate.exchange(API_URL + port + "/post/" + post.getId(), HttpMethod.DELETE, null, Post.class);

    assertThat(responseDelete.getStatusCode(), equalTo(HttpStatus.OK));
  }
}
