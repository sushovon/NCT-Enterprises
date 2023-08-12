package com.nctapplication.response;
import com.nctapplication.model.Login;
import com.nctapplication.model.directsale.Directsale;

public class DirectsaleResponse {
    public Directsale posts;
    private Throwable error;

    public DirectsaleResponse(Directsale posts) {
        this.posts = posts;
        this.error = null;
    }

    public DirectsaleResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public Directsale getPosts() {
        return posts;
    }

    public void setPosts(Directsale posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
