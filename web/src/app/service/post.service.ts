import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Post } from '../model/post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }
  baseUrl: string = 'http://localhost:8080/post';

  getPosts() {
    return this.http.get<Post[]>(this.baseUrl);
  }

  getPostById(id: number) {
    return this.http.get<Post>(this.baseUrl + '/' + id);
  }

  createPost(post: Post) {
    return this.http.post(this.baseUrl, post);
  }

  updatePost(post: Post) {
    return this.http.put(this.baseUrl, post);
  }

  deletePost(id: number) {
    return this.http.delete(this.baseUrl + '/' + id);
  }
}
