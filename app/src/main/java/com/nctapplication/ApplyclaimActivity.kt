package com.nctapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.nctapplication.adapter.ClaimAdapter
import com.nctapplication.commons.MyApp
import com.nctapplication.databinding.ActivityApplyclaimBinding
import com.nctapplication.model.claim.ClaimList
import com.nctapplication.response.AppDataResponse
import com.nctapplication.response.ClaimApiResponse
import com.nctapplication.util.Utils
import com.nctapplication.viewmodel.AppDataViewModel
import com.nctapplication.viewmodel.ClaimViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import okhttp3.MultipartBody
import okhttp3.RequestBody
@AndroidEntryPoint
class ApplyclaimActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplyclaimBinding
    var doubleBounce: Sprite = DoubleBounce()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding=DataBindingUtil.setContentView(this,R.layout.activity_applyclaim)
        binding.submitButton.setOnClickListener {
            if(binding.claimamount.text.toString().length==0 )
                Utils.showToast(resources.getString(R.string.blankfield),this@ApplyclaimActivity)
            else if(binding.acnumber.text.toString().length==0 )
                Utils.showToast(resources.getString(R.string.blankfield),this@ApplyclaimActivity)
            else if(binding.ifsc.text.toString().length==0 )
                Utils.showToast(resources.getString(R.string.blankfield),this@ApplyclaimActivity)
            else{
                claimrequest(binding.claimamount.text.toString(),binding.acnumber.text.toString(),binding.ifsc.text.toString())
            }
        }
    }
    fun claimrequest(amount: String, acname: String, ifsc: String){
        //val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("member_id", Paper.book().read<Int>("memberid", 0).toString())
            .addFormDataPart("amount", amount)
            .addFormDataPart("account_no", acname)
            .addFormDataPart("ifsc", ifsc)

            .build()

        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val viewModel: AppDataViewModel = ViewModelProvider(this).get(AppDataViewModel::class.java)

            viewModel.app_payout(requestBody)?.observe(this@ApplyclaimActivity,object :
                Observer<AppDataResponse?> {
                override fun onChanged(apiResponse: AppDataResponse?) {

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
                                this@ApplyclaimActivity
                            )

                        } else {
                            if (apiResponse.getPosts().success == true) {
                                Utils.showToast(resources.getString(R.string.claimsuccess),this@ApplyclaimActivity)
                                finish()
                            } else if (apiResponse.getPosts().success==false) {
                                Utils.showToast(apiResponse.getPosts().message,this@ApplyclaimActivity)
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
            Utils.showToast(resources.getString(R.string.no_internet),this@ApplyclaimActivity)
        }
    }
}