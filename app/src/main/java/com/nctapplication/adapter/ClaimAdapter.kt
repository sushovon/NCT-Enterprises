package com.nctapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.nctapplication.R

class ClaimAdapter :
    RecyclerView.Adapter<ClaimAdapter.MyViewHolder> {

    var data: ArrayList<HashMap<String, String>>? = null
    var resultp = HashMap<String, String>()
    var context: Context? = null

    class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var memberFname: AppCompatButton
        var payoutDate: AppCompatButton
        var payoutAmount: AppCompatButton

        init {
            memberFname = itemView.findViewById(R.id.memberFname)
            payoutDate = itemView.findViewById(R.id.payoutDate)
            payoutAmount = itemView.findViewById(R.id.payoutAmount)

        }
    }

    constructor(context: Context?,
                arraylist: ArrayList<HashMap<String, String>>?)  {
        data = arraylist
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_claim_listitem, parent, false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return data?.size!!
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        resultp = data!![position]

        holder.payoutDate.text = resultp["date"]
        holder.payoutAmount.text = resultp["total"]
        holder.memberFname.text = resultp["name"]
    }


}