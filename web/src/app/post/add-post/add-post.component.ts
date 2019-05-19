import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Post } from 'src/app/model/post';
import { PostService } from 'src/app/service/post.service';
import { Router } from '@angular/router';
import {Location} from '@angular/common';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {

  newPostForm: FormGroup;

  constructor(private router: Router,
    private postService: PostService,
    private formBuilder: FormBuilder,
    private location: Location) { }

  ngOnInit() {
    this.initForm();
  }

  //methods for adding new post
  initForm() {
    this.newPostForm = this.formBuilder.group({
      title: ['', Validators.required],
      content: ['', Validators.required]
    });
  }

  onSubmit() {
    const title = this.newPostForm.value['title'];
    const content = this.newPostForm.value['content'];
    const newPost = new Post(title, content);
    this.postService.createPost(newPost).subscribe(
      () => {
        this.router.navigate(['blog']);
      }
    );
  }

  goBack(): void {
    this.location.back();
  }

}
