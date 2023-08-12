package com.nctapplication.response;
import com.nctapplication.model.repurchase.Repurchase;


public class RepurchaseApiResponse {
    public Repurchase posts;
    private Throwable error;

    public RepurchaseApiResponse(Repurchase posts) {
        this.posts = posts;
        this.error = null;
    }

    public RepurchaseApiResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public Repurchase getPosts() {
        return posts;
    }

    public void setPosts(Repurchase posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
