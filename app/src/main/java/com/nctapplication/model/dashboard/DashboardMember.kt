package com.example.example.dashboard

import com.google.gson.annotations.SerializedName


data class DashboardMember (

  @SerializedName("success" ) var success : Boolean? = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("data"    ) var data    : Data?    = Data()

)