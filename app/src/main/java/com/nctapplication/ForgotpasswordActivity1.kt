package com.nctapplication


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.nctapplication.commons.MyApp
import com.nctapplication.databinding.ActivityForgotpassword1Binding
import com.nctapplication.model.checkmobile.Data
import com.nctapplication.response.CheckMobileResponse
import com.nctapplication.util.Utils
import com.nctapplication.viewmodel.CheckMobileViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import okhttp3.MultipartBody
import okhttp3.RequestBody
@AndroidEntryPoint
class ForgotpasswordActivity1 : AppCompatActivity() {
    private lateinit var binding : ActivityForgotpassword1Binding
    var doubleBounce: Sprite = DoubleBounce()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgotpassword1)
        binding.nextbtn.setOnClickListener {
            if(binding.phone.text.toString().trim().equals("") && binding.phone.text.toString().trim().length==0){
                Utils.showToast(resources.getString(R.string.blankfield),this@ForgotpasswordActivity1)
            }
            else{
                if(MyApp.getInstance()!!.isNetworkAvailable()){
                    checkmobile(binding.phone.text.toString())
                }else{
                    Utils.showToast(resources.getString(R.string.no_internet),this@ForgotpasswordActivity1)
                }
            }
        }
    }
    fun checkmobile(mobile: String){
        //val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            //.addFormDataPart("member_id", "11")
            .addFormDataPart("member_phoneno", mobile)
            .build()

            val viewModel: CheckMobileViewModel = ViewModelProvider(this).get(CheckMobileViewModel::class.java)

            viewModel.checkmobile(requestBody)?.observe(this@ForgotpasswordActivity1,object : Observer<CheckMobileResponse?> {
                override fun onChanged(apiResponse: CheckMobileResponse?) {

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
                                this@ForgotpasswordActivity1
                            )

                        } else {
                            if (apiResponse.getPosts().success == true) {
                                var data    : Data?=apiResponse.getPosts().data
                                var id : String? = data?.memberId
                                val intent = Intent(this@ForgotpasswordActivity1, ForgotPasswordActivity2::class.java)
                                intent.putExtra("memberid",id)
                                startActivity(intent)

                            } else if (apiResponse.getPosts().success==false) {
                                Utils.showToast(apiResponse.getPosts().message,this@ForgotpasswordActivity1)
                            }
                        }
                    } else {
                        // call failed.
                        binding.spinKit.visibility = View.GONE
                        val e = apiResponse.error
                    }
                }

            })

    }
}