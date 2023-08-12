package com.nctapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.nctapplication.R
import com.nctapplication.commons.Constants
import com.nctapplication.commons.MyApp
import com.nctapplication.commons.MyApplication
import com.nctapplication.databinding.FragmentProfileBinding
import com.nctapplication.databinding.FragmentWalletBinding
import com.nctapplication.model.wallet.Data
import com.nctapplication.response.ProfileApiResponse
import com.nctapplication.response.WalletApiResponse
import com.nctapplication.util.Utils
import com.nctapplication.viewmodel.ProfileViewModel
import com.nctapplication.viewmodel.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import okhttp3.MultipartBody
import okhttp3.RequestBody

@AndroidEntryPoint
class WalletFragment : Fragment() {
    private var rootView: View? = null
    private lateinit var binding: FragmentWalletBinding
    var doubleBounce: Sprite = DoubleBounce()

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet, container, false)
        val rootView = binding.root
        retainInstance = true

        return rootView
    }
    fun getwallet(){
        //val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("member_id", Paper.book().read<Int>("memberid", 0).toString())
            .build()

        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val viewModel: WalletViewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
            viewModel.getWallet(requestBody)?.observe(viewLifecycleOwner,object : Observer<WalletApiResponse?> {
                override fun onChanged(apiResponse: WalletApiResponse?) {

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


                                var data : Data? = apiResponse.getPosts().data

                                binding.total.setText(resources.getString(R.string.rupee)+" "+data?.walletBalance.toString())
                                binding.directsale.setText(resources.getString(R.string.rupee)+" "+data?.directsale.toString())
                                binding.membership.setText(resources.getString(R.string.rupee)+" "+data?.totalMembership.toString())
                                binding.repurchase.setText(resources.getString(R.string.rupee)+" "+data?.repurchase.toString())
                                binding.claim.setText(resources.getString(R.string.rupee)+" "+data?.loanClaim.toString())
                                binding.payout.setText(resources.getString(R.string.rupee)+" "+data?.payout.toString())
                                binding.balance.setText(resources.getString(R.string.rupee)+" "+data?.walletBalance.toString())

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
        getwallet()
    }
}