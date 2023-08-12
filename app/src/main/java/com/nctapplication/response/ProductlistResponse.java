package com.nctapplication.response;
import com.nctapplication.model.Login;
import com.nctapplication.model.productlist.Productlist;

public class ProductlistResponse {
    public Productlist posts;
    private Throwable error;

    public ProductlistResponse(Productlist posts) {
        this.posts = posts;
        this.error = null;
    }

    public ProductlistResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public Productlist getPosts() {
        return posts;
    }

    public void setPosts(Productlist posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
