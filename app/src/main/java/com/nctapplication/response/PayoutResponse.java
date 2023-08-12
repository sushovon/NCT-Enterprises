package com.nctapplication.response;
import com.nctapplication.model.payout.Payout;

public class PayoutResponse {
    public Payout posts;
    private Throwable error;

    public PayoutResponse(Payout posts) {
        this.posts = posts;
        this.error = null;
    }

    public PayoutResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public Payout getPosts() {
        return posts;
    }

    public void setPosts(Payout posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
