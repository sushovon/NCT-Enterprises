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
import com.nctapplication.adapter.MemberlistAdapter
import com.nctapplication.commons.MyApp
import com.nctapplication.databinding.FragmentMemberlistBinding
import com.nctapplication.response.MemberListResponse
import com.nctapplication.util.Utils
import com.nctapplication.viewmodel.MemberlistViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MemberlistFragment : Fragment() {
    private var rootView: View? = null
    private lateinit var binding: FragmentMemberlistBinding
    var doubleBounce: Sprite = DoubleBounce()
    var claimdata: ArrayList<HashMap<String, String>>? = ArrayList()
    lateinit var adapter : MemberlistAdapter
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_memberlist, container, false)
        val rootView = binding.root
        retainInstance = true

        return rootView
    }
    fun getDirectsale(){
        //val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)


        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val viewModel: MemberlistViewModel = ViewModelProvider(this).get(MemberlistViewModel::class.java)
            viewModel.getmember()?.observe(viewLifecycleOwner,object : Observer<MemberListResponse?> {
                override fun onChanged(apiResponse: MemberListResponse?) {

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
                                var data    : ArrayList<com.nctapplication.model.member.Data> =  apiResponse.getPosts().data

                                if (data != null) {
                                    if(data.size>0){
                                        for(i in 0 until (data.size)){
                                            var resultp = HashMap<String, String>()
                                            resultp.put("name",data.get(i).memberFname.toString())
                                            resultp.put("phone",data.get(i).memberPhoneno.toString())
                                            resultp.put("id",data.get(i).memberId.toString())
                                            resultp.put("image",data.get(i).memberImage.toString())
                                            claimdata?.add(resultp)

                                        }
                                        adapter= MemberlistAdapter(activity,claimdata)
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