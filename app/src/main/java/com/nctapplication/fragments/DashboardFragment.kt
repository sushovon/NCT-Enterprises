package com.nctapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.example.dashboard.ChildMember
import com.example.example.dashboard.Data
import com.example.example.dashboard.ReferalCode
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.nctapplication.R
import com.nctapplication.adapter.HorizontalRecyclerViewAdapter
import com.nctapplication.commons.MyApp
import com.nctapplication.databinding.FragmentDashboardBinding
import com.nctapplication.response.DashboardApiResponse
import com.nctapplication.util.Utils
import com.nctapplication.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import okhttp3.MultipartBody
import okhttp3.RequestBody

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private var rootView: View? = null
    private lateinit var binding: FragmentDashboardBinding
    var doubleBounce: Sprite = DoubleBounce()
    var memberdata: ArrayList<HashMap<String, String>>? = ArrayList()
    lateinit var adapter : HorizontalRecyclerViewAdapter
    private var horizontalLayoutManager: LinearLayoutManager? = null
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (rootView != null) {
            val parent = rootView!!.getParent() as ViewGroup
            if (parent != null) parent.removeView(rootView)
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        val rootView = binding.root
        retainInstance = true

        return rootView
    }
    fun dashboard(){
        //val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("member_id", Paper.book().read<Int>("memberid", 0).toString())
            .build()

        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val viewModel: DashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
            viewModel.getDashboard(requestBody)?.observe(viewLifecycleOwner,object : Observer<DashboardApiResponse?> {
                override fun onChanged(apiResponse: DashboardApiResponse?) {

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

                                var data    : Data?=apiResponse.getPosts().data
                                binding.directsale.text=data?.directsale
                                binding.membership.text=data?.totalMembership.toString()
                                binding.repurchasing.text=data?.repurchase
                                var referalCode: ReferalCode?=data?.referalCode
                                binding.referid.setText(referalCode?.referralCode)//for edittext use settext
                                binding.claimamount.text=data?.loanClaim
                                binding.payoutamount.text=data?.payout
                                var childMember : ArrayList<ChildMember> = data!!.childMember
                                if(childMember.size>0){
                                    for(i in 0 until (childMember.size)){
                                       var resultp = HashMap<String, String>()
                                        resultp.put("image",
                                            childMember.get(i).memberImage.toString()
                                        )
                                        resultp.put("name",childMember.get(i).memberFname.toString())
                                        memberdata?.add(resultp)

                                    }

                                    adapter= HorizontalRecyclerViewAdapter(activity,memberdata)
                                    horizontalLayoutManager = LinearLayoutManager(
                                        activity,
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                    )
                                    binding.horizontalRecyclerView.layoutManager =
                                        horizontalLayoutManager
                                    binding.horizontalRecyclerView.adapter=adapter
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
        dashboard()
    }
}