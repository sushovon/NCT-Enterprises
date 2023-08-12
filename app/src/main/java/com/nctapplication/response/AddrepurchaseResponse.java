package com.nctapplication.response;
import com.nctapplication.model.Login;
import com.nctapplication.model.addrepurchase.Addrepurchase;

public class AddrepurchaseResponse {
    public Addrepurchase posts;
    private Throwable error;

    public AddrepurchaseResponse(Addrepurchase posts) {
        this.posts = posts;
        this.error = null;
    }

    public AddrepurchaseResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public Addrepurchase getPosts() {
        return posts;
    }

    public void setPosts(Addrepurchase posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
