package com.nctapplication.response;
import com.nctapplication.model.Login;
import com.nctapplication.model.profileupdate.Profileupdate;

public class ProfileUpdateResponse {
    public Profileupdate posts;
    private Throwable error;

    public ProfileUpdateResponse(Profileupdate posts) {
        this.posts = posts;
        this.error = null;
    }

    public ProfileUpdateResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public Profileupdate getPosts() {
        return posts;
    }

    public void setPosts(Profileupdate posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
