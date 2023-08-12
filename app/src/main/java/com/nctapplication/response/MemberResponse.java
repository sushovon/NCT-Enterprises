package com.nctapplication.response;
import com.nctapplication.model.Login;
import com.nctapplication.model.memberaward.Member;

public class MemberResponse {
    public Member posts;
    private Throwable error;

    public MemberResponse(Member posts) {
        this.posts = posts;
        this.error = null;
    }

    public MemberResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public Member getPosts() {
        return posts;
    }

    public void setPosts(Member posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
