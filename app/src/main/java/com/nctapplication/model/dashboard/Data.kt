package com.example.example.dashboard

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("total_membership" ) var totalMembership : Int?                   = null,
  @SerializedName("coupon_price"     ) var couponPrice     : String?                = null,
  @SerializedName("directsale"       ) var directsale      : String?                = null,
  @SerializedName("repurchase"       ) var repurchase      : String?                = null,
  @SerializedName("payout"           ) var payout          : String?                = null,
  @SerializedName("loan_claim"       ) var loanClaim       : String?                = null,
  @SerializedName("wallet_balance"   ) var walletBalance   : Int?                   = null,
  @SerializedName("referal_code"     ) var referalCode     : ReferalCode?           = ReferalCode(),
  @SerializedName("child_member"     ) var childMember     : ArrayList<ChildMember> = arrayListOf()

)