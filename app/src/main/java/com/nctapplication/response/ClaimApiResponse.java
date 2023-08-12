package com.nctapplication.response;
import com.nctapplication.model.Login;
import com.nctapplication.model.claim.Claim;

public class ClaimApiResponse {
    public Claim posts;
    private Throwable error;

    public ClaimApiResponse(Claim posts) {
        this.posts = posts;
        this.error = null;
    }

    public ClaimApiResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public Claim getPosts() {
        return posts;
    }

    public void setPosts(Claim posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
