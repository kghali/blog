import { Comment } from './comment';

export class Post {
    public id: number
    public comments: Comment[] = [];
    constructor(
        public title: String,
        public content: String
    ) { }
}
