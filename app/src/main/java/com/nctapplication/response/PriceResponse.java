package com.nctapplication.response;
import com.nctapplication.model.Login;
import com.nctapplication.model.price.Price;

public class PriceResponse {
    public Price posts;
    private Throwable error;

    public PriceResponse(Price posts) {
        this.posts = posts;
        this.error = null;
    }

    public PriceResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public Price getPosts() {
        return posts;
    }

    public void setPosts(Price posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
