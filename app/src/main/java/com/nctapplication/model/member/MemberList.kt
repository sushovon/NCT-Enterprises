package com.nctapplication.model.member

import com.google.gson.annotations.SerializedName


data class MemberList (

  @SerializedName("success" ) var success : Boolean?        = null,
  @SerializedName("message" ) var message : String?         = null,
  @SerializedName("data"    ) var data    : ArrayList<Data> = arrayListOf()

)