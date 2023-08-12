package com.nctapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.nctapplication.R

class DirectsaleAdapter :
    RecyclerView.Adapter<DirectsaleAdapter.MyViewHolder> {

    var data: ArrayList<HashMap<String, String>>? = null
    var resultp = HashMap<String, String>()
    var context: Context? = null

    class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var date: AppCompatButton
        var memberFname: AppCompatButton
        var parentComission: AppCompatButton

        init {
            parentComission = itemView.findViewById(R.id.parentComission)
            date = itemView.findViewById(R.id.date)
            memberFname = itemView.findViewById(R.id.memberFname)
        }
    }

    constructor(context: Context?,
                arraylist: ArrayList<HashMap<String, String>>?)  {
        data = arraylist
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_directsale_listitem, parent, false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return data?.size!!
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        resultp = data!![position]

        holder.date.text = resultp["date"]
        holder.memberFname.text = resultp["name"]
        holder.parentComission.text = resultp["comission"]
    }


}