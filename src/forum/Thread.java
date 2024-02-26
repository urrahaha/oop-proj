package forum;

import login.User;

import java.util.ArrayList;

public class Thread {
    private final int id;
    private final ArrayList<Post> posts = new ArrayList<>();
    private final User originalPoster;
    private final Section attachedSection;
    private String threadTitle;

    private Thread(String title, String content, User poster, Section section) {
        this.id = Forum.getThreads().size();
        this.originalPoster = poster;
        this.attachedSection = section;
        this.threadTitle = title;
        Post.createPost(content, poster, this);
        Forum.getThreads().add(this);
    }

    public int getID() { return id; }

    public ArrayList<Post> getPosts() { return posts; }

    public User getOriginalPoster() { return originalPoster; }

    public Section getAttachedSection() { return attachedSection; }

    public String getThreadTitle() { return threadTitle; }
    public void editThreadTitle(String threadTitle) { this.threadTitle = threadTitle; }

    public static Thread createThread(String title, String content, User poster, Section section) {
        return new Thread(title, content, poster, section);
    }

    public Post createThreadPost(String title, String content, User poster) {
        this.threadTitle = title;
        return Post.createPost(content, poster, this);
    }
    public Post createThreadPost(String content, User poster) {
        return Post.createPost(content, poster, this);
    }

    //////////////////////////////////////// POST CLASS ////////////////////////////////////////

    public static class Post {
        private final int id;
        private String content;
        private final User poster;
        private final Thread attachedThread;

        private Post(String content, User poster, Thread thread){
            this.id = thread.getPosts().size();
            this.content = content;
            this.poster = poster;
            this.attachedThread = thread;
            thread.getPosts().add(this);
        }

        public int getID() { return id; }

        public String getContent() { return content; }
        public void editContent(String newContent) { content = newContent; }

        public User getPoster() { return poster; }

        public Thread getAttachedThread() { return attachedThread; }

        public static Post createPost(String content, User poster, Thread thread) {
            return new Post(content, poster, thread);
        }
    }
}
