package com.nctapplication.response;
import com.nctapplication.model.Login;
import com.nctapplication.model.register.Register;

public class RegisterResponse {
    public Register posts;
    private Throwable error;

    public RegisterResponse(Register posts) {
        this.posts = posts;
        this.error = null;
    }

    public RegisterResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public Register getPosts() {
        return posts;
    }

    public void setPosts(Register posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
