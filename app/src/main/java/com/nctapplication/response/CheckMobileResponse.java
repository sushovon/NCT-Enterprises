package com.nctapplication.response;
import com.nctapplication.model.Login;
import com.nctapplication.model.checkmobile.CheckMobile;

public class CheckMobileResponse {
    public CheckMobile posts;
    private Throwable error;

    public CheckMobileResponse(CheckMobile posts) {
        this.posts = posts;
        this.error = null;
    }

    public CheckMobileResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public CheckMobile getPosts() {
        return posts;
    }

    public void setPosts(CheckMobile posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
