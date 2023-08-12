package com.nctapplication.commons
import com.example.example.dashboard.DashboardMember
import com.nctapplication.model.couponprice.CouponPrice
import com.nctapplication.model.Login
import com.nctapplication.model.addrepurchase.Addrepurchase
import com.nctapplication.model.appdata.AppData
import com.nctapplication.model.checkmobile.CheckMobile
import com.nctapplication.model.claim.Claim
import com.nctapplication.model.directsale.Directsale
import com.nctapplication.model.member.MemberList
import com.nctapplication.model.memberaward.Member
import com.nctapplication.model.payout.Payout
import com.nctapplication.model.price.Price
import com.nctapplication.model.productlist.Productlist
import com.nctapplication.model.profile.ProfileData
import com.nctapplication.model.profileupdate.Profileupdate
import com.nctapplication.model.register.Register
import com.nctapplication.model.repurchase.Repurchase
import com.nctapplication.model.transactiondetails.TransactionDetails
import com.nctapplication.model.updatepassword.UpdatePassword
import com.nctapplication.model.wallet.Wallet
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiInterface {

    @POST("login")
    fun login(@Body body: RequestBody?): Call<Login>

    @POST("member_dashboard")
    fun memberdashboard(@Body body: RequestBody?): Call<DashboardMember>

    @POST("profile")
    fun profile(@Body body: RequestBody?): Call<ProfileData>

    @POST("wallet_details")
    fun wallet_details(@Body body: RequestBody?): Call<Wallet>

    @POST("repurchase")
    fun repurchase(@Body body: RequestBody?): Call<Repurchase>

    @POST("directsale")
    fun directsale(@Body body: RequestBody?): Call<Directsale>

    @POST("member_award")
    fun member(@Body body: RequestBody?): Call<Member>

    @POST("claim")
    fun claim(@Body body: RequestBody?): Call<Claim>

    @POST("payout")
    fun payout(@Body body: RequestBody?): Call<Payout>

    @GET("member")
    fun member(): Call<MemberList>

    @POST("update_profile")
    @Multipart
    fun updateprofile(@Part("member_id")member_id: RequestBody ,@Part("member_email")member_email: RequestBody,@Part("member_image\"; filename=\"member_image.jpg\" ")file: RequestBody):
            Call<Profileupdate>

    @POST("check_phoneno")
    fun checkmobile(@Body body: RequestBody?): Call<CheckMobile>

    @POST("updatepassword")
    fun updatepassword(@Body body:RequestBody?): Call<UpdatePassword>

    @POST("register")
    @Multipart
    fun register(@Part("first_name")first_name: RequestBody , @Part("last_name")last_name: RequestBody , @Part("phoneno")phoneno: RequestBody ,
                 @Part("email") email:RequestBody , @Part("password")password: RequestBody ,
                 @Part("referral_code")referral_code: RequestBody , @Part("aadharcard")aadharcard: RequestBody ,
                 @Part("pancard")pancard: RequestBody ,@Part("pancard_file\"; filename=\"pancard_file.jpg\" ")file: RequestBody ,
                 @Part("aadharcard_file\"; filename=\"aadharcard_file.jpg\" ")adharfile: RequestBody ) :Call<Register>

    @POST("coupon_price")
    fun coupon_price(@Body body:RequestBody?): Call<CouponPrice>

    @GET("nct")
    fun price(): Call<Price>

    @GET("product_category")
    fun product_category(): Call<Productlist>

    @POST("add_repurchase")
    fun add_repurchase(@Body body:RequestBody?): Call<Addrepurchase>

    @POST("insert_transactiondtls")
    fun insert_transactiondtls(@Body body:RequestBody?): Call<TransactionDetails>


    @POST("app_payout")
    fun app_payout(@Body body:RequestBody?): Call<AppData>
}