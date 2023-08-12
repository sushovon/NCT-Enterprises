package com.nctapplication.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nctapplication.R
import com.nctapplication.circleimage.CircleImageView
import com.nctapplication.commons.MyApplication.isValidContextForGlide


class HorizontalRecyclerViewAdapter :
    RecyclerView.Adapter<HorizontalRecyclerViewAdapter.MyViewHolder> {

    var data: ArrayList<HashMap<String, String>>? = null
    var resultp = HashMap<String, String>()
    var context: Context? = null

    class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var name: TextView
        var profile_image: CircleImageView

        init {
            profile_image = itemView.findViewById(R.id.profile_image)
            name = itemView.findViewById<TextView>(R.id.name)
        }
    }

    constructor(context: Context?,
                arraylist: ArrayList<HashMap<String, String>>?)  {
        data = arraylist
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.dashboard_member_listitem, parent, false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return data?.size!!
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        resultp = data!![position]
        if (isValidContextForGlide(context)) {
            // Load image via Glide lib using context
            Glide.with(context!!)
                .load(resultp["image"]).placeholder(R.drawable.placeholderprofile)
                .error(Glide.with(context!!).load(R.drawable.placeholderprofile))
                .into(holder.profile_image)
        } else {
            Glide.with(context!!).load(R.drawable.placeholderprofile).into(holder.profile_image)
        }

        holder.name.text = resultp["name"]
    }


}