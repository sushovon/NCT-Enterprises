package com.nctapplication.model.wallet

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("total_membership" ) var totalMembership : Int?    = null,
  @SerializedName("coupon_price"     ) var couponPrice     : String? = null,
  @SerializedName("directsale"       ) var directsale      : String? = null,
  @SerializedName("repurchase"       ) var repurchase      : String? = null,
  @SerializedName("payout"           ) var payout          : String? = null,
  @SerializedName("loan_claim"       ) var loanClaim       : String? = null,
  @SerializedName("wallet_balance"   ) var walletBalance   : Int?    = null

)