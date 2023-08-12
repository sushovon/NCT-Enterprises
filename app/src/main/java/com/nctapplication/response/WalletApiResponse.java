package com.nctapplication.response;
import com.nctapplication.model.wallet.Wallet;


public class WalletApiResponse {
    public Wallet posts;
    private Throwable error;

    public WalletApiResponse(Wallet posts) {
        this.posts = posts;
        this.error = null;
    }

    public WalletApiResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public Wallet getPosts() {
        return posts;
    }

    public void setPosts(Wallet posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
