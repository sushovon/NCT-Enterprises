package com.nctapplication.response;
import com.nctapplication.model.Login;
import com.nctapplication.model.member.MemberList;

public class MemberListResponse {
    public MemberList posts;
    private Throwable error;

    public MemberListResponse(MemberList posts) {
        this.posts = posts;
        this.error = null;
    }

    public MemberListResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public MemberList getPosts() {
        return posts;
    }

    public void setPosts(MemberList posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
