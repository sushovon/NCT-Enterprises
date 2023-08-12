package com.nctapplication.response;
import com.nctapplication.model.addrepurchase.Addrepurchase;
import com.nctapplication.model.appdata.AppData;

public class AppDataResponse {
    public AppData posts;
    private Throwable error;

    public AppDataResponse(AppData posts) {
        this.posts = posts;
        this.error = null;
    }

    public AppDataResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public AppData getPosts() {
        return posts;
    }

    public void setPosts(AppData posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
