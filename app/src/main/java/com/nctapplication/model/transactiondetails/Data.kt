package com.nctapplication.model.transactiondetails

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("id"             ) var id            : String? = null,
  @SerializedName("member_id"      ) var memberId      : String? = null,
  @SerializedName("transaction_id" ) var transactionId : String? = null,
  @SerializedName("amount"         ) var amount        : String? = null,
  @SerializedName("created_at"     ) var createdAt     : String? = null

)