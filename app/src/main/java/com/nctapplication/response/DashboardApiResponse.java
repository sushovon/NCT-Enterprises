package com.nctapplication.response;
import com.example.example.dashboard.DashboardMember;


public class DashboardApiResponse {
    public DashboardMember posts;
    private Throwable error;

    public DashboardApiResponse(DashboardMember posts) {
        this.posts = posts;
        this.error = null;
    }

    public DashboardApiResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public DashboardMember getPosts() {
        return posts;
    }

    public void setPosts(DashboardMember posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
