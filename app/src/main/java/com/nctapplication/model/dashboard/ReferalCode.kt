package com.example.example.dashboard

import com.google.gson.annotations.SerializedName


data class ReferalCode (

  @SerializedName("member_id"     ) var memberId     : String? = null,
  @SerializedName("referral_code" ) var referralCode : String? = null

)