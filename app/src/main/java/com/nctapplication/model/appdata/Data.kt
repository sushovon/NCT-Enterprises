package com.nctapplication.model.appdata

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("payout_id"         ) var payoutId        : String? = null,
  @SerializedName("member_id"         ) var memberId        : String? = null,
  @SerializedName("payout_amount"     ) var payoutAmount    : String? = null,
  @SerializedName("payout_account_no" ) var payoutAccountNo : String? = null,
  @SerializedName("payout_ifsc"       ) var payoutIfsc      : String? = null,
  @SerializedName("payout_remarks"    ) var payoutRemarks   : String? = null,
  @SerializedName("payout_file"       ) var payoutFile      : String? = null,
  @SerializedName("payout_date"       ) var payoutDate      : String? = null,
  @SerializedName("payout_status"     ) var payoutStatus    : String? = null,
  @SerializedName("payout_delete"     ) var payoutDelete    : String? = null

)