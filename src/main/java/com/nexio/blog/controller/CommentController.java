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
import com.nexio.blog.exception.CommentNotFoundException;
import com.nexio.blog.exception.NotCommentOfPostException;
import com.nexio.blog.exception.PostNotFoundException;
import com.nexio.blog.persistence.model.Comment;
import com.nexio.blog.persistence.model.Post;
import com.nexio.blog.persistence.model.dto.CommentDto;
import com.nexio.blog.persistence.model.dto.PostDto;
import com.nexio.blog.service.CommentService;
import com.nexio.blog.service.PostService;

/**
 * @author Khaled Ghali
 */
@RestController
@RequestMapping("/post")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentController {

  private static final String POST_URI = "/post/";
  @Autowired
  private PostService postService;
  @Autowired
  private CommentService commentService;

  @GetMapping("/{id}/comments")
  public ResponseEntity<List<Comment>> getAllCommentsForPost(@PathVariable Long id) {
    postService.findOne(id).orElseThrow(() -> new PostNotFoundException(id));
    return ResponseEntity.ok().body(commentService.findAllCommentsForPostId(id));
  }

  @GetMapping("/{idPost}/comments/{idComment}")
  public ResponseEntity<Comment> getSingleComment(@PathVariable Long idPost, @PathVariable Long idComment) {
    postService.findOne(idPost).orElseThrow(() -> new PostNotFoundException(idPost));
    Comment comment = commentService.findOne(idComment).orElseThrow(() -> new CommentNotFoundException(idComment));
    if (comment.getPost().getId() == idPost) {
      return ResponseEntity.ok().body(comment);
    } else {
      throw new NotCommentOfPostException(idPost, idComment);
    }
  }

  @PostMapping("/{id}/comments")
  public ResponseEntity<Comment> createComment(@PathVariable Long id, @RequestBody CommentDto commentDto) throws URISyntaxException {
    if (commentDto.getId() != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    Post post = postService.findOne(id).orElseThrow(() -> new PostNotFoundException(id));
    Comment comment = new Comment(commentDto.getMessage(), commentDto.getPost());
    comment.setPost(post);
    Comment result = commentService.save(comment);
    return ResponseEntity.created(new URI(POST_URI + id)).body(result);
  }

  @PutMapping("/{id}/comments")
  public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) throws URISyntaxException {
    Post post = postService.findOne(id).orElseThrow(() -> new PostNotFoundException(id));
    if (commentDto.getId() == null) {
      return createComment(id, commentDto);
    }
    Comment comment = commentService.findOne(commentDto.getId()).orElseThrow(() -> new CommentNotFoundException(commentDto.getId()));
    comment.setMessage(commentDto.getMessage());
    comment.setPost(post);
    Comment result = commentService.save(comment);
    return ResponseEntity.ok().body(result);
  }

  @DeleteMapping("/{idPost}/comments/{idComment}")
  public ResponseEntity<Void> deleteComment(@PathVariable Long idPost, @PathVariable Long idComment) {
    postService.findOne(idPost).orElseThrow(() -> new PostNotFoundException(idPost));
    Comment comment = commentService.findOne(idComment).orElseThrow(() -> new CommentNotFoundException(idComment));
    if (comment.getPost().getId() == idPost) {
      commentService.delete(idComment);
      return ResponseEntity.ok().build();
    } else {
      throw new NotCommentOfPostException(idPost, idComment);
    }
  }
}
