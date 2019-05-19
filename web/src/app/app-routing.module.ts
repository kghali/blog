import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SinglePostComponent } from './post/single-post/single-post.component';
import { ListPostComponent } from './post/list-post/list-post.component';
import { AddPostComponent } from './post/add-post/add-post.component';

const routes: Routes = [
  { path: 'blog', component: ListPostComponent },
  { path: 'blog/new', component: AddPostComponent },
  { path: 'blog/post/:id', component: SinglePostComponent },
  { path: '', redirectTo: 'blog', pathMatch: 'full' },
  { path: '**', redirectTo: 'blog' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
