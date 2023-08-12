package com.nctapplication.model.memberaward

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("member_award" ) var memberAward : ArrayList<MemberAward> = arrayListOf()

)