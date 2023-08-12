package com.nctapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.nctapplication.databinding.ActivityForgotPassword2Binding
import com.nctapplication.response.UpdatePasswordResponse
import com.nctapplication.util.Utils
import com.nctapplication.viewmodel.UpdatePasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody
import okhttp3.RequestBody
@AndroidEntryPoint
class ForgotPasswordActivity2 : AppCompatActivity() {
    private lateinit var binding : ActivityForgotPassword2Binding
    lateinit var id : String
    var doubleBounce: Sprite = DoubleBounce()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password2)
        id = intent.getStringExtra("memberid").toString()
        binding.submitbtn.setOnClickListener {
            if(binding.password.text.toString().trim().equals("") && binding.password.text.toString().trim().length==0){
                Utils.showToast(resources.getString(R.string.blankfield),this@ForgotPasswordActivity2)
            }
            else if(binding.confpass.text.toString().trim().equals("") && binding.confpass.text.toString().trim().length==0){
                Utils.showToast(resources.getString(R.string.blankfield),this@ForgotPasswordActivity2)
            }
            else{
                updatepassword(id,binding.password.text.toString(),binding.confpass.text.toString())
            }
        }
    }
    // Expected BEGIN_OBJECT but was STRING at line 1 column 66 path $.data
    fun updatepassword(id: String, password: String, conpassword: String){
        //val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("member_id", id)
            .addFormDataPart("password", password)
            .addFormDataPart("confirm_password", conpassword)
            .build()

        val viewModel: UpdatePasswordViewModel = ViewModelProvider(this).get(UpdatePasswordViewModel::class.java)

        viewModel.updatepassword(requestBody)?.observe(this@ForgotPasswordActivity2,object :
            Observer<UpdatePasswordResponse?> {
            override fun onChanged(apiResponse: UpdatePasswordResponse?) {

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
                            this@ForgotPasswordActivity2
                        )

                    } else {
                        if (apiResponse.getPosts().success == true) {
                            Utils.showToast(apiResponse.getPosts().message,this@ForgotPasswordActivity2)
                            binding.text1.text = resources.getString(R.string.password_update)
                            binding.text2.text = resources.getString(R.string.password_chang)
                            binding.midRel.visibility = View.GONE
                            binding.image.visibility = View.VISIBLE


                            Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
                                override fun run() {
                                    val intent = Intent(this@ForgotPasswordActivity2, LoginActivity::class.java)
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    startActivity(intent)
                                    finish()
                                }
                            },3000)

                        } else if (apiResponse.getPosts().success==false) {
                            Utils.showToast(apiResponse.getPosts().message,this@ForgotPasswordActivity2)
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