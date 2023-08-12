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
import com.nctapplication.databinding.ActivityBuyBinding
import com.nctapplication.response.AddrepurchaseResponse
import com.nctapplication.response.PriceResponse
import com.nctapplication.response.TransactionDetailsResponse
import com.nctapplication.util.Utils
import com.nctapplication.viewmodel.AddrepurchaseViewModel
import com.nctapplication.viewmodel.PriceViewModel
import com.nctapplication.viewmodel.TransactionDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.shreyaspatil.easyupipayment.EasyUpiPayment
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener
import dev.shreyaspatil.easyupipayment.model.PaymentApp
import dev.shreyaspatil.easyupipayment.model.TransactionDetails
import dev.shreyaspatil.easyupipayment.model.TransactionStatus
import io.paperdb.Paper
import okhttp3.MultipartBody
import okhttp3.RequestBody

@AndroidEntryPoint
class BuyActivity : AppCompatActivity() , PaymentStatusListener {
    private lateinit var binding: ActivityBuyBinding
    var doubleBounce: Sprite = DoubleBounce()
    private lateinit var easyUpiPayment: EasyUpiPayment
    val transaction_Id = "TID" + System.currentTimeMillis()
    lateinit var descriptionupi: String
    var productprice : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buy)
        getprice()
        binding.paynow.setOnClickListener {
            descriptionupi= "Product purchase"
            if(MyApp.getInstance()!!.isNetworkAvailable())
                upipayment()
            else
                Utils.showToast(resources.getString(R.string.no_internet),this@BuyActivity)

        }
    }
    fun upipayment(){

        try {
            easyUpiPayment = EasyUpiPayment(this) {
                this.paymentApp = PaymentApp.ALL
                this.payeeVpa = "nct150@cnrb"
                this.payeeName = "N C T ENTERPRISES"
                this.transactionId = transaction_Id
                this.transactionRefId = transaction_Id
                this.payeeMerchantCode = transaction_Id
                this.description = descriptionupi
                this.amount = productprice.toDoubleOrNull()?.toString()
            }
            // END INITIALIZATION

            // Register Listener for Events
            easyUpiPayment.setPaymentStatusListener(this)

            // Start payment / transaction
            easyUpiPayment.startPayment()
        } catch (e: Exception) {
            e.printStackTrace()
            Utils.showToast(
                e.message,
                this@BuyActivity
            )
        }
    }
    override fun onTransactionCompleted(transactionDetails: TransactionDetails) {
        // Transaction Completed

        when (transactionDetails.transactionStatus) {
            TransactionStatus.SUCCESS -> onTransactionSuccess(transactionDetails.transactionId.toString())
            TransactionStatus.FAILURE -> onTransactionFailed()
            TransactionStatus.SUBMITTED -> onTransactionSubmitted()
        }
    }

    override fun onTransactionCancelled() {
        // Payment Cancelled by User
        Utils.showToast(
            "Cancelled by user",
            this@BuyActivity
        )

    }

    private fun onTransactionSuccess(transactionid: String) {
        // Payment Success
        Utils.showToast(
            "Success",
            this@BuyActivity
        )
        uploadtransaction(transactionid)
    }

    private fun onTransactionSubmitted() {
        // Payment Pending
        Utils.showToast(
            "Pending | Submitted",
            this@BuyActivity
        )

    }

    private fun onTransactionFailed() {
        // Payment Failed
        Utils.showToast(
           "Failed",
            this@BuyActivity
        )

    }
//test
    fun uploadtransaction(transactionid : String){
        //val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)



        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("member_id", Paper.book().read<Int>("memberid", 0).toString())
            .addFormDataPart("amount", productprice)
            .addFormDataPart("transaction_id", transactionid)
            .build()

        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val viewModel: TransactionDetailsViewModel = ViewModelProvider(this).get(
                TransactionDetailsViewModel::class.java)
            viewModel.insert_transactiondtls(requestBody)?.observe(this@BuyActivity,object :
                Observer<TransactionDetailsResponse?> {
                override fun onChanged(apiResponse: TransactionDetailsResponse?) {

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
                                this@BuyActivity
                            )

                        } else {
                            if (apiResponse.getPosts().success == true) {
                                Utils.showToast(apiResponse.getPosts().message,this@BuyActivity)
                                val intent = Intent(this@BuyActivity, LoginActivity::class.java)
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(intent)
                                finish()
                            } else if (apiResponse.getPosts().success==false) {
                                Utils.showToast(apiResponse.getPosts().message,this@BuyActivity)
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
            Utils.showToast(resources.getString(R.string.no_internet),this@BuyActivity)
        }
    }
    fun getprice(){
        //val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)


        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val viewModel: PriceViewModel = ViewModelProvider(this).get(PriceViewModel::class.java)
            viewModel.getprice()?.observe(this@BuyActivity,object : Observer<PriceResponse?> {
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
                                this@BuyActivity
                            )

                        } else {
                            if (apiResponse.getPosts().success == true) {
                                var data    : ArrayList<com.nctapplication.model.price.Data>  = apiResponse.getPosts().data
                                binding.firstname.setText(resources.getString(R.string.packet)+" ${data.get(0).productPrice}")
                                productprice=data.get(0).productPrice.toString()

                            } else if (apiResponse.getPosts().success==false) {
                                Utils.showToast(apiResponse.getPosts().message,this@BuyActivity)
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
            Utils.showToast(resources.getString(R.string.no_internet),this@BuyActivity)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@BuyActivity, LoginActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}