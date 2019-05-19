import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from 'src/app/model/post';
import { PostService } from 'src/app/service/post.service';
import { CommentService } from 'src/app/service/comment.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.css']
})
export class ListPostComponent implements OnInit {

  posts: Post[];

  constructor(private router: Router,
    private postService: PostService) { }

  ngOnInit() {
    //get all posts
    this.postService.getPosts()
      .subscribe(data => {
        this.posts = data;
      });
  }

  showPost(post: Post): void {
    this.router.navigate(['blog/post', post.id], { state: { post: "test" } });
  }

  deletePost(post: Post): void {
    if (confirm("Are you sure to delete this post")) {
      this.postService.deletePost(post.id)
        .subscribe(data => {
          this.posts = this.posts.filter(u => u !== post);
        })
    }
  };

  addPost(): void {
    this.router.navigate(['blog/new']);
  };
}
