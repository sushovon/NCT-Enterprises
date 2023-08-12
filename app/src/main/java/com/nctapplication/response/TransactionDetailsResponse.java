package com.nctapplication.response;
import com.nctapplication.model.transactiondetails.TransactionDetails;

public class TransactionDetailsResponse {
    public TransactionDetails posts;
    private Throwable error;

    public TransactionDetailsResponse(TransactionDetails posts) {
        this.posts = posts;
        this.error = null;
    }

    public TransactionDetailsResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public TransactionDetails getPosts() {
        return posts;
    }

    public void setPosts(TransactionDetails posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
