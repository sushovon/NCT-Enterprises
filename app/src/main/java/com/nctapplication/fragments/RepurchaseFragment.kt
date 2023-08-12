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
import com.bumptech.glide.Glide
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.nctapplication.PurchaseActivity
import com.nctapplication.R
import com.nctapplication.adapter.HorizontalRecyclerViewAdapter
import com.nctapplication.adapter.RepurchaseAdapter
import com.nctapplication.commons.Constants
import com.nctapplication.commons.MyApp
import com.nctapplication.commons.MyApplication
import com.nctapplication.databinding.FragmentProfileBinding
import com.nctapplication.databinding.FragmentRepurchaseBinding
import com.nctapplication.databinding.FragmentWalletBinding
import com.nctapplication.model.repurchase.RepurchaseList
import com.nctapplication.model.wallet.Data
import com.nctapplication.response.PriceResponse
import com.nctapplication.response.ProfileApiResponse
import com.nctapplication.response.RepurchaseApiResponse
import com.nctapplication.response.WalletApiResponse
import com.nctapplication.util.Utils
import com.nctapplication.viewmodel.PriceViewModel
import com.nctapplication.viewmodel.ProfileViewModel
import com.nctapplication.viewmodel.RepurchaseViewModel
import com.nctapplication.viewmodel.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import okhttp3.MultipartBody
import okhttp3.RequestBody

@AndroidEntryPoint
class RepurchaseFragment : Fragment() {
    private var rootView: View? = null
    private lateinit var binding: FragmentRepurchaseBinding
    var doubleBounce: Sprite = DoubleBounce()
    var repurchasedata: ArrayList<HashMap<String, String>>? = ArrayList()
    lateinit var adapter : RepurchaseAdapter
    private var horizontalLayoutManager: LinearLayoutManager? = null
    var price: Int =0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (rootView != null) {
            val parent = rootView!!.getParent() as ViewGroup
            if (parent != null) parent.removeView(rootView)
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repurchase, container, false)
        val rootView = binding.root
        retainInstance = true

        return rootView
    }
    fun getrepurchase(){
        //val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("member_id", Paper.book().read<Int>("memberid", 0).toString())
            .build()

        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val viewModel: RepurchaseViewModel = ViewModelProvider(this).get(RepurchaseViewModel::class.java)
            viewModel.getRepurchase(requestBody)?.observe(viewLifecycleOwner,object : Observer<RepurchaseApiResponse?> {
                override fun onChanged(apiResponse: RepurchaseApiResponse?) {

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
                                var data : com.nctapplication.model.repurchase.Data?  = apiResponse.getPosts().data
                                var repurchaseList : ArrayList<RepurchaseList> = data!!.repurchaseList
                                if(repurchaseList.size>0){
                                    for(i in 0 until(repurchaseList.size)){
                                        var resultp = HashMap<String, String>()
                                        var newStrg=repurchaseList.get(i).purchaseCreatedAt.toString()
                                        val mString = newStrg!!.split(" ").toTypedArray()
                                        resultp.put("date",mString[0])
                                        resultp.put("qty",repurchaseList.get(i).quantity.toString())
                                        resultp.put("total",repurchaseList.get(i).totalPrice.toString())
                                        resultp.put("comission",repurchaseList.get(i).buyerComission.toString())
                                        repurchasedata?.add(resultp)
                                    }
                                    //adapter.notifyDataSetChanged()
                                    adapter= RepurchaseAdapter(activity,repurchasedata)
                                    horizontalLayoutManager = LinearLayoutManager(
                                        activity,
                                        LinearLayoutManager.VERTICAL,
                                        false
                                    )
                                    binding.recyclerView.layoutManager =
                                        horizontalLayoutManager
                                    binding.recyclerView.adapter=adapter
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
        binding.repurchase.setOnClickListener {
            val intent= Intent(activity, PurchaseActivity::class.java)
            intent.putExtra("price",price)
            startActivity(intent)
        }
        getrepurchase()
        getprice()
    }
    fun getprice(){
        //val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)


        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val viewModel: PriceViewModel = ViewModelProvider(this).get(PriceViewModel::class.java)
            viewModel.getprice()?.observe(viewLifecycleOwner,object : Observer<PriceResponse?> {
                override fun onChanged(apiResponse: PriceResponse?) {

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
                                var data    : ArrayList<com.nctapplication.model.price.Data>  = apiResponse.getPosts().data
                                price= Integer.parseInt(data.get(0).productPrice)

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
}