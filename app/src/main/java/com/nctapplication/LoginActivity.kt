package com.nctapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.nctapplication.commons.MyApp
import com.nctapplication.commons.MyApplication
import com.nctapplication.databinding.ActivityLoginBinding
import com.nctapplication.model.Data
import com.nctapplication.response.ApiResponse
import com.nctapplication.util.Utils
import com.nctapplication.viewmodel.LoginViewModel
import io.paperdb.Paper
import okhttp3.MultipartBody
import okhttp3.RequestBody

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var phn: String? =null
    var psd: String?=null
    var doubleBounce: Sprite = DoubleBounce()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.submitbtn.setOnClickListener(View.OnClickListener {
            if(binding.phone.text!!.trim()=="" && binding.phone.text!!.length==0){
                Utils.showToast(resources.getString(R.string.blankfield), this)
            }
            else if(binding.password.text!!.trim()=="" && binding.password.text!!.length==0){
                Utils.showToast(resources.getString(R.string.blankfield), this)
            }else{
                phn= binding.phone.text!!.trim().toString()
                psd=binding.password.text!!.trim().toString()
                login(phn!!, psd!!)

            }

        })
        binding.forgotpass.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotpasswordActivity1::class.java)
            startActivity(intent)
        }
        binding.register.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    fun login(phn: String, psd: String){
        val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)



        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("phoneno", phn)
            .addFormDataPart("password", psd)
            .build()
        /*val loginViewModel = LoginViewModel()
        loginViewModel.getLogin(requestBody)?.observe(this,object :Observer<ApiResponse?>{
            override fun onChanged(t: ApiResponse?) {
                TODO("Not yet implemented")
            }

        })*/
        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val loginViewModel = LoginViewModel()
            loginViewModel.getLogin(requestBody)?.observe(this,object :Observer<ApiResponse?>{
                override fun onChanged(apiResponse: ApiResponse?) {

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
                                this@LoginActivity
                            )

                        } else {

                            if (apiResponse.getPosts().success == true) {
                                Utils.showToast("Login success",this@LoginActivity)
                               var data: Data? = apiResponse.getPosts().data
                                Paper.book().write("memberid", data?.memberId.toString())
                                Paper.book().write("login", 1)
                                val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                                startActivity(intent)
                                finish()

                            } else if (apiResponse.getPosts().success==false) {
                                Utils.showToast(apiResponse.getPosts().message,this@LoginActivity)
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
            Utils.showToast(resources.getString(R.string.no_internet),this@LoginActivity)
        }
    }
}



