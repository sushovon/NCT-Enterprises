package com.nctapplication.response;
import com.example.example.dashboard.DashboardMember;
import com.nctapplication.model.profile.ProfileData;


public class ProfileApiResponse {
    public ProfileData posts;
    private Throwable error;

    public ProfileApiResponse(ProfileData posts) {
        this.posts = posts;
        this.error = null;
    }

    public ProfileApiResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public ProfileData getPosts() {
        return posts;
    }

    public void setPosts(ProfileData posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
