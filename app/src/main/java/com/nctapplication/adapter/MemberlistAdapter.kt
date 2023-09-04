package com.nctapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nctapplication.R
import com.nctapplication.circleimage.CircleImageView
import com.nctapplication.commons.Constants
import com.nctapplication.commons.MyApplication
import com.nctapplication.model.member.Data

class MemberlistAdapter :
    RecyclerView.Adapter<MemberlistAdapter.MyViewHolder> {

    var data: ArrayList<Data>? = null
    var resultp = HashMap<String, String>()
    var context: Context? = null

    class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var memberId: AppCompatTextView
        var memberFname: AppCompatTextView
        var memberPhoneno: AppCompatTextView
        var image: CircleImageView

        init {
            memberId = itemView.findViewById(R.id.memberId)
            memberFname = itemView.findViewById(R.id.memberFname)
            memberPhoneno = itemView.findViewById(R.id.memberPhoneno)
            image = itemView.findViewById(R.id.profile_pic)
        }
    }

    constructor(
        context: Context?,
        arraylist: ArrayList<Data>
    )  {
        data = arraylist
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_member_listitem, parent, false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return data?.size!!
    }
    fun filterList(filterlist: ArrayList<Data>) {
        // below line is to add our filtered
        // list in our course array list.
        data = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /*resultp = data!![position]

        holder.memberId.text = resultp["id"]
        holder.memberPhoneno.text = resultp["phone"]
        holder.memberFname.text = resultp["name"]
        if (MyApplication.isValidContextForGlide(context)) {
            // Load image via Glide lib using context
            Glide.with(context!!)
                .load(Constants.BASE_URL_IMAGE+resultp["image"]).placeholder(R.drawable.placeholderprofile)
                .error(Glide.with(context!!).load(R.drawable.placeholderprofile))
                .into(holder.image)
        } else {
            Glide.with(context!!).load(R.drawable.placeholderprofile).into(holder.image)
        }*/
        holder.memberId.text=data?.get(position)?.memberId
        holder.memberPhoneno.text=data?.get(position)?.memberPhoneno
        holder.memberFname.text=data?.get(position)?.memberFname
        if (MyApplication.isValidContextForGlide(context)) {
            // Load image via Glide lib using context
            Glide.with(context!!)
                .load(Constants.BASE_URL_IMAGE+data?.get(position)?.memberImage).placeholder(R.drawable.placeholderprofile)
                .error(Glide.with(context!!).load(R.drawable.placeholderprofile))
                .into(holder.image)
        } else {
            Glide.with(context!!).load(R.drawable.placeholderprofile).into(holder.image)
        }
    }


}