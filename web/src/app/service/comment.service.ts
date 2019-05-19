import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Comment } from '../model/comment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) { }
  baseUrl: string = 'http://localhost:8080/post';

  getComments(idPost: number) {
    return this.http.get<Comment[]>(this.baseUrl + '/' +idPost + '/comments');
  }

  getCommentById(idPost: number, idComment: number) {
    return this.http.get<Comment>(this.baseUrl + '/' + idPost + '/comments/' + idComment);
  }

  createComment(idPost: number, comment: Comment) {
    return this.http.post(this.baseUrl + '/' + idPost + '/comments', comment);
  }

  updateComment(idPost: number, comment: Comment) {
    return this.http.put(this.baseUrl + '/' + idPost +'/comments', comment);
  }

  deleteComment(idPost: number, idComment: number) {
    return this.http.delete(this.baseUrl + '/' + idPost + '/comments/' + idComment);
  }
}
