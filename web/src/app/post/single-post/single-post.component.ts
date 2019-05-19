import { Component, OnInit } from '@angular/core';
import { switchMap } from 'rxjs/operators';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { PostService } from 'src/app/service/post.service';
import { Post } from 'src/app/model/post';
import { CommentService } from 'src/app/service/comment.service';
import { Location } from '@angular/common';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Comment } from 'src/app/model/comment';

@Component({
  selector: 'app-single-post',
  templateUrl: './single-post.component.html',
  styleUrls: ['./single-post.component.css']
})
export class SinglePostComponent implements OnInit {

  post: Post = new Post("", "");
  showCommentInput: boolean = false;
  editMode: boolean = false;
  title = "test aaa";
  editPostForm: FormGroup;
  newCommentForm: FormGroup;
  editCommentMode: number;
  noComment: boolean = false;
  showAddCommentButton: boolean = false;

  constructor(private postService: PostService,
    private route: ActivatedRoute,
    private commentService: CommentService,
    private formBuilder: FormBuilder,
    private location: Location) { }

  ngOnInit() {
    this.route.params
      .pipe(switchMap((params: Params) => this.postService.getPostById(+params['id'])))
      .subscribe(post => this.post = post);
    this.initForm();
    this.initCommentForm();
  }

  //methods for adding new post
  initForm() {
    this.editPostForm = this.formBuilder.group({
      title: ['', Validators.required],
      content: ['', Validators.required]
    });
  }

  initCommentForm(){
    this.newCommentForm = this.formBuilder.group({
      message: ['', Validators.required]
    });
  }

  goBack(): void {
    this.location.back();
  }

  onSubmit() {
    const title = this.editPostForm.value['title'];
    const content = this.editPostForm.value['content'];
    const editedPost = new Post(title, content);
    editedPost.id = this.post.id;
    this.postService.updatePost(editedPost).subscribe(
      (data: Post) => {
        this.post = data;
        this.editMode = !this.editMode;
      }
    );
  }

  editPost(): void {
    this.editMode = !this.editMode;
  };

  //get comments for a post
  viewComments(post: Post): void {
    this.showAddCommentButton = true;
    this.commentService.getComments(post.id)
      .subscribe(data => {
        if (data.length > 0) {
          this.post.comments = data;
        }
        else {
          this.noComment = true;
        }
      });
  }

  onNewCommentSubmit() {
    const message = this.newCommentForm.value['message'];
    const comment = new Comment(message);
    this.commentService.createComment(this.post.id, comment).subscribe(
      (data: Comment) => {
        if (this.post.comments) {
          this.post.comments.unshift(data);
        }
        else {
          this.post.comments = new Array();
          this.post.comments.push(data);
        }
        this.showCommentInput = !this.showCommentInput;
        this.noComment = false;
        this.initCommentForm();
      }
    );
  }

  editComment(comment: Comment, index: number) {
    this.editCommentMode = index;
  }

  onSaveComment(event: any) {
    const message = event.target.value;
    const comment = new Comment(message);
    comment.id = this.post.comments[this.editCommentMode].id;
    this.commentService.updateComment(this.post.id, comment).subscribe(
      (data: Comment) => {
        this.post.comments[this.editCommentMode] = data;
        this.editCommentMode = -1;
      }
    );
  }

  deleteComment(comment: Comment) {
    if (confirm("Are you sure to delete this comment")) {
      this.commentService.deleteComment(this.post.id, comment.id)
        .subscribe(() => {
          this.post.comments = this.post.comments.filter(u => u !== comment);
          if(this.post.comments.length == 0){
            this.noComment = true;
          }
        })
    }

  }

}
