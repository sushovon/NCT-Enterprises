package com.nctapplication.fragments

import android.content.Intent
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
import com.nctapplication.ApplyclaimActivity
import com.nctapplication.R
import com.nctapplication.adapter.ClaimAdapter
import com.nctapplication.adapter.DirectsaleAdapter
import com.nctapplication.adapter.MemberAdapter
import com.nctapplication.commons.MyApp
import com.nctapplication.databinding.FragmentClaimBinding
import com.nctapplication.databinding.FragmentMembershipBinding
import com.nctapplication.model.claim.ClaimList
import com.nctapplication.model.directsale.DirectsaleList
import com.nctapplication.model.memberaward.Data
import com.nctapplication.model.memberaward.MemberAward
import com.nctapplication.response.ClaimApiResponse
import com.nctapplication.response.MemberResponse
import com.nctapplication.util.Utils
import com.nctapplication.viewmodel.ClaimViewModel

import com.nctapplication.viewmodel.MemberViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import okhttp3.MultipartBody
import okhttp3.RequestBody

@AndroidEntryPoint
class ClaimFragment : Fragment() {
    private var rootView: View? = null
    private lateinit var binding: FragmentClaimBinding
    var doubleBounce: Sprite = DoubleBounce()
    var claimdata: ArrayList<HashMap<String, String>>? = ArrayList()
    lateinit var adapter : ClaimAdapter
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_claim, container, false)
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
            val viewModel: ClaimViewModel = ViewModelProvider(this).get(ClaimViewModel::class.java)

            viewModel.getclaim(requestBody)?.observe(viewLifecycleOwner,object : Observer<ClaimApiResponse?> {
                override fun onChanged(apiResponse: ClaimApiResponse?) {

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
                                var data    : com.nctapplication.model.claim.Data? =  apiResponse.getPosts().data
                                var claimList : ArrayList<ClaimList>? =
                                    data?.claimList
                                if (claimList != null) {
                                    if(claimList.size>0){
                                        for(i in 0 until (claimList.size)){
                                            var resultp = HashMap<String, String>()
                                            resultp.put("name",claimList.get(i).memberFname.toString())

                                            var newStrg=claimList.get(i).payoutDate.toString()
                                            val mString = newStrg!!.split(" ").toTypedArray()
                                            resultp.put("date",mString[0])
                                            resultp.put("total",claimList.get(i).payoutAmount.toString())

                                            claimdata?.add(resultp)

                                        }
                                        adapter= ClaimAdapter(activity,claimdata)
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
        binding.claimbtn.setOnClickListener {
            val intent=Intent(activity,ApplyclaimActivity::class.java)
            startActivity(intent)
        }
    }

}