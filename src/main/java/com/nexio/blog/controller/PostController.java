package com.nexio.blog.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nexio.blog.exception.PostNotFoundException;
import com.nexio.blog.persistence.model.Post;
import com.nexio.blog.persistence.model.dto.PostDto;
import com.nexio.blog.service.PostService;

/**
 * @author Khaled Ghali
 */
@RestController
@RequestMapping("/post")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostController {

  private static final String POST_URI = "/post/";

  @Autowired
  private PostService postService;

  @GetMapping("")
  public ResponseEntity<List<Post>> getAllPosts() {
    return ResponseEntity.ok().body(postService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> getSinglePost(@PathVariable Long id) {
    Post post = postService.findOne(id).orElseThrow(() -> new PostNotFoundException(id));
    return ResponseEntity.ok().body(post);
  }

  @PostMapping("")
  public ResponseEntity<Post> createPost(@RequestBody PostDto postDto) throws URISyntaxException {
    Post post = new Post(postDto.getTitle(), postDto.getContent());
    if (post.getId() != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    Post result = postService.save(post);
    return ResponseEntity.created(new URI(POST_URI)).body(result);
  }

  @PutMapping("")
  public ResponseEntity<Post> updatePost(@RequestBody PostDto postDto) throws URISyntaxException {
    if (postDto.getId() == null) {
      return createPost(postDto);
    }
    Post post = postService.findOne(postDto.getId()).orElseThrow(() -> new PostNotFoundException(postDto.getId()));
    post.setTitle(postDto.getTitle());
    post.setContent(postDto.getContent());
    return ResponseEntity.ok().body(postService.save(post));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable Long id) {
    postService.findOne(id).orElseThrow(() -> new PostNotFoundException(id));
    postService.delete(id);
    return ResponseEntity.ok().build();
  }

}
