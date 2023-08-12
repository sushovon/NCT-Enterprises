package com.nctapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.nctapplication.R

class RepurchaseAdapter :
    RecyclerView.Adapter<RepurchaseAdapter.MyViewHolder> {

    var data: ArrayList<HashMap<String, String>>? = null
    var resultp = HashMap<String, String>()
    var context: Context? = null

    class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var date: AppCompatButton
        var qty: AppCompatButton
        var total: AppCompatButton
        var comission: AppCompatButton

        init {
            qty = itemView.findViewById(R.id.qty)
            date = itemView.findViewById(R.id.date)
            total = itemView.findViewById(R.id.total)
            comission = itemView.findViewById(R.id.comission)
        }
    }

    constructor(context: Context?,
                arraylist: ArrayList<HashMap<String, String>>?)  {
        data = arraylist
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_repurchase_listitem, parent, false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return data?.size!!
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        resultp = data!![position]

        holder.date.text = resultp["date"]
        holder.qty.text = resultp["qty"]
        holder.total.text = resultp["total"]
        holder.comission.text = resultp["comission"]
    }


}