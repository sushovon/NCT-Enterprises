package com.nctapplication.model.claim

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("claim_list" ) var claimList : ArrayList<ClaimList> = arrayListOf()

)