package com.nctapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.nctapplication.R
import com.nctapplication.adapter.DirectsaleAdapter
import com.nctapplication.adapter.MemberAdapter
import com.nctapplication.commons.MyApp
import com.nctapplication.databinding.FragmentMembershipBinding
import com.nctapplication.model.directsale.DirectsaleList
import com.nctapplication.model.memberaward.Data
import com.nctapplication.model.memberaward.MemberAward
import com.nctapplication.response.MemberResponse
import com.nctapplication.util.Utils

import com.nctapplication.viewmodel.MemberViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import okhttp3.MultipartBody
import okhttp3.RequestBody

@AndroidEntryPoint
class MemberFragment : Fragment() {
    private var rootView: View? = null
    private lateinit var binding: FragmentMembershipBinding
    var doubleBounce: Sprite = DoubleBounce()
    var directsaledata: ArrayList<HashMap<String, String>>? = ArrayList()
    lateinit var adapter : MemberAdapter
    private var LayoutManager: LinearLayoutManager? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (rootView != null) {
            val parent = rootView!!.getParent() as ViewGroup
            if (parent != null) parent.removeView(rootView)
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_membership, container, false)
        val rootView = binding.root
        retainInstance = true

        return rootView
    }
    fun getDirectsale(){
        //val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("member_id", Paper.book().read<Int>("memberid", 0).toString())
            .build()

        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val viewModel: MemberViewModel = ViewModelProvider(this).get(MemberViewModel::class.java)
            viewModel.getMember(requestBody)?.observe(viewLifecycleOwner,object : Observer<MemberResponse?> {
                override fun onChanged(apiResponse: MemberResponse?) {

                    if (apiResponse == null) {
                        // handle error here
                        binding.spinKit.visibility = View.GONE
                        return
                    }
                    if (apiResponse.error == null) {
                        // call is successful
                        binding.spinKit.setVisibility(View.GONE);
                        if (apiResponse.posts == null) {
                            binding.spinKit.visibility = View.GONE
                            Utils.showToast(
                                resources.getString(R.string.data_not_found),
                                activity
                            )

                        } else {
                            if (apiResponse.getPosts().success == true) {
                                var data    : Data? =  apiResponse.getPosts().data
                                var memberAwardlist : ArrayList<MemberAward>? =
                                    data?.memberAward
                                if (memberAwardlist != null) {
                                    if(memberAwardlist.size>0){
                                        for(i in 0 until (memberAwardlist.size)){
                                            var resultp = HashMap<String, String>()
                                            resultp.put("name",memberAwardlist.get(i).memberFname.toString())
                                            resultp.put("qty",memberAwardlist.get(i).quantity.toString())
                                            var newStrg=memberAwardlist.get(i).purchaseCreatedAt.toString()
                                            val mString = newStrg!!.split(" ").toTypedArray()
                                            resultp.put("date",mString[0])
                                            resultp.put("total",memberAwardlist.get(i).totalPrice.toString())
                                            resultp.put("comission",memberAwardlist.get(i).parentComission.toString())
                                            directsaledata?.add(resultp)

                                        }
                                        adapter= MemberAdapter(activity,directsaledata)
                                        LayoutManager = LinearLayoutManager(
                                            activity,
                                            LinearLayoutManager.VERTICAL,
                                            false
                                        )
                                        binding.recyclerView.layoutManager =
                                            LayoutManager
                                        binding.recyclerView.adapter=adapter
                                    }
                                }


                            } else if (apiResponse.getPosts().success==false) {
                                Utils.showToast(apiResponse.getPosts().message,activity)
                            }
                        }
                    } else {
                        // call failed.
                        binding.spinKit.visibility = View.GONE
                        val e = apiResponse.error
                        Log.e("sushovon",e.message.toString())
                    }
                }

            })
        }else{
            Utils.showToast(resources.getString(R.string.no_internet),activity)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getDirectsale()
    }

}