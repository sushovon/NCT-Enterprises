package com.nctapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.nctapplication.adapter.RepurchaseAdapter
import com.nctapplication.commons.MyApp
import com.nctapplication.databinding.ActivityPurchaseBinding
import com.nctapplication.model.productlist.Data
import com.nctapplication.model.repurchase.RepurchaseList
import com.nctapplication.response.AddrepurchaseResponse
import com.nctapplication.response.ProductlistResponse
import com.nctapplication.response.RepurchaseApiResponse
import com.nctapplication.response.TransactionDetailsResponse
import com.nctapplication.util.Utils
import com.nctapplication.viewmodel.AddrepurchaseViewModel
import com.nctapplication.viewmodel.ProductlistViewModel
import com.nctapplication.viewmodel.RepurchaseViewModel
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
class PurchaseActivity : AppCompatActivity(), PaymentStatusListener {
    private lateinit var binding: ActivityPurchaseBinding
    var doubleBounce: Sprite = DoubleBounce()
    lateinit var productid1: String ;
    lateinit var productid2: String
    lateinit var productid3: String
    lateinit var productid4: String
    var total = 0
    var total_quantity= 0
    var total1 :Int = 0
    var total2 :Int= 0
    var total3:Int= 0
    var total4:Int = 0
    var price:Int=0
    lateinit var product_qnty1: String
    lateinit var product_qnty2: String
    lateinit var product_qnty3: String
    lateinit var product_qnty4: String
    private lateinit var easyUpiPayment: EasyUpiPayment
    val transaction_Id = "TID" + System.currentTimeMillis()
    lateinit var descriptionupi: String
    var productprice : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase)
        price= intent.getIntExtra("price",0)
        getproduct()
       /* var customers = ArrayList<String>()
        customers.add("Cash")
        customers.add("Bank Transfer")
        customers.add("Gateway Transfer")*/
        val option = resources.getStringArray(R.array.Languages)
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, option)

        binding.customerTextView1.setAdapter(adapter)

        /*var TextWatcher =object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("sushovon",s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }*/
        binding.productquantity1.addTextChangedListener(textWatcher)
        binding.productquantity2.addTextChangedListener(textWatcher)
        binding.productquantity3.addTextChangedListener(textWatcher)
        binding.productquantity4.addTextChangedListener(textWatcher)
        binding.productprice.setText("Product price per piece :$price")

        binding.submitButton.setOnClickListener {

            if(binding.customerTextView1.text.length==0){
                Utils.showToast(
                    resources.getString(R.string.selecttype),
                    this@PurchaseActivity
                )
            }else if(binding.productquantity1.length()==0 && binding.productquantity2.length()==0 && binding.productquantity3.length()==0 && binding.productquantity4.length()==0){
                Utils.showToast(
                    resources.getString(R.string.selectproduct),
                    this@PurchaseActivity
                )
            }
            else{
                   addrepurchase()
            }
        }
    }
    fun getproduct(){
        //val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)


        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val viewModel: ProductlistViewModel = ViewModelProvider(this).get(ProductlistViewModel::class.java)
            viewModel.product_category()?.observe(this@PurchaseActivity,object : Observer<ProductlistResponse?> {
                override fun onChanged(apiResponse: ProductlistResponse?) {

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
                                this@PurchaseActivity
                            )

                        } else {
                            if (apiResponse.getPosts().success == true) {
                                var data    : ArrayList<Data>   = apiResponse.getPosts().data
                                if(data.size>0){
                                    for(i in 0 until (data.size)){
                                        if(data.get(i).productCategoryId.equals("1")){
                                            binding.productname1.setText(data.get(i).productCategory)
                                            productid1=data.get(i).productCategoryId.toString()
                                        }
                                        else if(data.get(i).productCategoryId.equals("2")){
                                            binding.productname2.setText(data.get(i).productCategory)
                                            productid2=data.get(i).productCategoryId.toString()
                                        }
                                        else if(data.get(i).productCategoryId.equals("3")){
                                            binding.productname3.setText(data.get(i).productCategory)
                                            productid3=data.get(i).productCategoryId.toString()
                                        }
                                        else if(data.get(i).productCategoryId.equals("4")){
                                            binding.productname4.setText(data.get(i).productCategory)
                                            productid4=data.get(i).productCategoryId.toString()
                                        }
                                    }
                                }

                            } else if (apiResponse.getPosts().success==false) {
                                Utils.showToast(apiResponse.getPosts().message,this@PurchaseActivity)
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
            Utils.showToast(resources.getString(R.string.no_internet),this@PurchaseActivity)
        }
    }
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            if (s.hashCode() == binding.productquantity1.getText().hashCode()) {
                // do other things
                if (s != null) {
                    total1 = if (s.length != 0) {
                        binding.productquantity1.getText().toString().trim().toInt() * price
                    } else {
                        0
                    }
                }
            }

            if (s.hashCode() == binding.productquantity2.getText().hashCode()) {
                // do other things
                total2 = if (s!!.length != 0) {
                    binding.productquantity2.getText().toString().trim().toInt() * price
                } else {
                    0
                }
            }
            if (s.hashCode() == binding.productquantity3.getText().hashCode()) {
                // do other things
                total3 = if (s!!.length != 0) {
                    binding.productquantity3.getText().toString().trim().toInt() * price
                } else {
                    0
                }
            }
            if (s.hashCode() == binding.productquantity4.getText().hashCode()) {
                // do other things
                total4 = if (s!!.length != 0) {
                    binding.productquantity4.getText().toString().trim().toInt() * price
                } else {
                    0
                }
            }
            total = total1 + total2 + total3 + total4
            binding.productTotalprice.setText("Total Product price :$total")
        }
    }
    fun addrepurchase(){
        //val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)
        total_quantity=total/price

        if(binding.productquantity1.text.toString().equals(""))
            product_qnty1="0"
        else
            product_qnty1=binding.productquantity1.text.toString()


        if(binding.productquantity2.text.toString().equals(""))
            product_qnty2="0"
        else
            product_qnty2=binding.productquantity2.text.toString()


        if(binding.productquantity3.text.toString().equals(""))
            product_qnty3="0"
        else
            product_qnty3=binding.productquantity3.text.toString()


        if(binding.productquantity4.text.toString().equals(""))
            product_qnty4="0"
        else
            product_qnty4=binding.productquantity4.text.toString()


        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("member_id", Paper.book().read<Int>("memberid", 0).toString())
            .addFormDataPart("total_quantity", total_quantity.toString())
            .addFormDataPart("transaction_type", binding.customerTextView1.text.toString())

            .addFormDataPart("product_id1", productid1)
            .addFormDataPart("product_name1", binding.productname1.text.toString())
            .addFormDataPart("product_total_price1", total1.toString())
            .addFormDataPart("product_qnty1", product_qnty1)



            .addFormDataPart("product_id2", productid2)
            .addFormDataPart("product_name2", binding.productname2.text.toString())
            .addFormDataPart("product_total_price2", total2.toString())
            .addFormDataPart("product_qnty2", product_qnty2)

            .addFormDataPart("product_id3", productid3)
            .addFormDataPart("product_name3", binding.productname3.text.toString())
            .addFormDataPart("product_total_price3", total3.toString())
            .addFormDataPart("product_qnty3", product_qnty3)

            .addFormDataPart("product_id4", productid4)
            .addFormDataPart("product_name4", binding.productname4.text.toString())
            .addFormDataPart("product_total_price4", total4.toString())
            .addFormDataPart("product_qnty4", product_qnty4)
            .build()

        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val viewModel: AddrepurchaseViewModel = ViewModelProvider(this).get(AddrepurchaseViewModel::class.java)
            viewModel.add_repurchase(requestBody)?.observe(this@PurchaseActivity,object : Observer<AddrepurchaseResponse?> {
                override fun onChanged(apiResponse: AddrepurchaseResponse?) {

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
                                this@PurchaseActivity
                            )

                        } else {
                            if (apiResponse.getPosts().success == true) {
                                Utils.showToast(apiResponse.getPosts().message,this@PurchaseActivity)
                                finish()
                            } else if (apiResponse.getPosts().success==false) {
                                Utils.showToast(apiResponse.getPosts().message,this@PurchaseActivity)
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
            Utils.showToast(resources.getString(R.string.no_internet),this@PurchaseActivity)
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
                this@PurchaseActivity
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
            this@PurchaseActivity
        )

    }

    private fun onTransactionSuccess(transactionid: String) {
        // Payment Success
        Utils.showToast(
            "Success",
            this@PurchaseActivity
        )
        uploadtransaction(transactionid)
    }

    private fun onTransactionSubmitted() {
        // Payment Pending
        Utils.showToast(
            "Pending | Submitted",
            this@PurchaseActivity
        )

    }

    private fun onTransactionFailed() {
        // Payment Failed
        Utils.showToast(
            "Failed",
            this@PurchaseActivity
        )

    }
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
            viewModel.insert_transactiondtls(requestBody)?.observe(this@PurchaseActivity,object :
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
                                this@PurchaseActivity
                            )

                        } else {
                            if (apiResponse.getPosts().success == true) {
                                Utils.showToast(apiResponse.getPosts().message,this@PurchaseActivity)
                                finish()
                            } else if (apiResponse.getPosts().success==false) {
                                Utils.showToast(apiResponse.getPosts().message,this@PurchaseActivity)
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
            Utils.showToast(resources.getString(R.string.no_internet),this@PurchaseActivity)
        }
    }
}