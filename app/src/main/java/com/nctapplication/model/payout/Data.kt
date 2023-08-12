package com.nctapplication.model.payout

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("coupon_price"    ) var couponPrice    : String?               = null,
  @SerializedName("previuos_payout" ) var previuosPayout : String?               = null,
  @SerializedName("next_payout"     ) var nextPayout     : String?               = null,
  @SerializedName("balance"         ) var balance        : Int?                  = null,
  @SerializedName("payout_list"     ) var payoutList     : ArrayList<PayoutList> = arrayListOf()

)