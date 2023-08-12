package com.nctapplication.response;
import com.nctapplication.model.Login;
import com.nctapplication.model.updatepassword.UpdatePassword;

public class UpdatePasswordResponse {
    public UpdatePassword posts;
    private Throwable error;

    public UpdatePasswordResponse(UpdatePassword posts) {
        this.posts = posts;
        this.error = null;
    }

    public UpdatePasswordResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public UpdatePassword getPosts() {
        return posts;
    }

    public void setPosts(UpdatePassword posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
