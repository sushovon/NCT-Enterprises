package com.nctapplication

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nctapplication.commons.Commonfun
import com.nctapplication.commons.Resource
import com.nctapplication.databinding.ActivityOtpactivityBinding
import com.nctapplication.util.Utils
import com.nctapplication.viewmodel.ResendViewModel
import com.nctapplication.viewmodel.ValidateOTPViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper

@AndroidEntryPoint
class OTPActivity : AppCompatActivity() {
    lateinit var binding: ActivityOtpactivityBinding
    lateinit var viewmodel: ResendViewModel
    lateinit var validateOTPViewModel: ValidateOTPViewModel
    lateinit var loader : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_otpactivity)
        viewmodel= ViewModelProvider(this).get(ResendViewModel::class.java)
        validateOTPViewModel=ViewModelProvider(this).get(ValidateOTPViewModel::class.java)
        binding.lifecycleOwner=this
        binding.viewmodel=viewmodel
        loader= Commonfun.loaderDialog(this@OTPActivity)
        observer()
        validateObserver()

        binding.etOTP.addTextChangedListener (object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length == 4) {
                    if (binding.etOTP.getText() != null) {
                        validateOTPViewModel.validate(binding.etOTP.getText().toString())
                    }
                }
            }
        })

        //Paper.book().read<Int>("memberid", 0).toString()
    }
    fun observer(){
        viewmodel.resenddata.observe(this@OTPActivity){response->
            when(response){
                is Resource.Loading->{
                    loader.show()
                }
                is Resource.Success->{
                    loader.dismiss()
                    response.data?.let {
                        Utils.showToast(it.message,this@OTPActivity)
                    }

                }
                is Resource.Error->{
                    loader.dismiss()
                    Utils.showToast(response.message.toString(),this@OTPActivity)

                }
                else -> {}
            }
        }
    }
    fun validateObserver(){
        validateOTPViewModel.validatedata.observe(this@OTPActivity){response->
            when(response){
                is Resource.Loading->{
                    loader.show()
                }
                is Resource.Success->{
                    loader.dismiss()

                    response.data?.let {
                        Utils.showToast(it.message,this@OTPActivity)
                        val intent = Intent(this@OTPActivity, BuyActivity::class.java)
                        startActivity(intent)
                    }

                }
                is Resource.Error->{
                    loader.dismiss()
                    Utils.showToast(response.message.toString(),this@OTPActivity)
                }
                else -> {}
            }
        }
    }
}