package com.nctapplication.response;
import com.nctapplication.model.couponprice.CouponPrice;

public class CouponPriceResponse {
    public CouponPrice posts;
    private Throwable error;

    public CouponPriceResponse(CouponPrice posts) {
        this.posts = posts;
        this.error = null;
    }

    public CouponPriceResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public CouponPrice getPosts() {
        return posts;
    }

    public void setPosts(CouponPrice posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
