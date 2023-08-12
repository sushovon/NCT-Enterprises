package com.nctapplication.model.price

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("id"                   ) var id                 : String? = null,
  @SerializedName("prefix_referral_code" ) var prefixReferralCode : String? = null,
  @SerializedName("userid_prefix"        ) var useridPrefix       : String? = null,
  @SerializedName("upper_level_price"    ) var upperLevelPrice    : String? = null,
  @SerializedName("lower_level_price"    ) var lowerLevelPrice    : String? = null,
  @SerializedName("product_price"        ) var productPrice       : String? = null,
  @SerializedName("updated_at"           ) var updatedAt          : String? = null

)