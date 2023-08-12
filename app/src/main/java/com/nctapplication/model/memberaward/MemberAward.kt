package com.nctapplication.model.memberaward

import com.google.gson.annotations.SerializedName


data class MemberAward (

  @SerializedName("member_fname"        ) var memberFname       : String? = null,
  @SerializedName("member_lname"        ) var memberLname       : String? = null,
  @SerializedName("purchase_created_at" ) var purchaseCreatedAt : String? = null,
  @SerializedName("quantity"            ) var quantity          : String? = null,
  @SerializedName("total_price"         ) var totalPrice        : String? = null,
  @SerializedName("parent_comission"    ) var parentComission   : String? = null,
  @SerializedName("joining_bonus"       ) var joiningBonus      : String? = null

)