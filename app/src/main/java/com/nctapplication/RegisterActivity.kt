package com.nctapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.nctapplication.commons.MyApp
import com.nctapplication.databinding.ActivityRegisterBinding
import com.nctapplication.response.ProfileUpdateResponse
import com.nctapplication.response.RegisterResponse
import com.nctapplication.util.Utils
import com.nctapplication.viewmodel.RegisterViewModel
import com.nctapplication.viewmodel.UpdateProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.File
@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    var doubleBounce: Sprite = DoubleBounce()
    lateinit var imageFilePath :String
    lateinit var imageFilePathPan :String
    val IMAGE_REQ_CODE : Int = 102
    var adhar :Int =1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        binding.submitbtn.setOnClickListener {
            if (binding.firstname.text.toString().trim().equals("") && binding.firstname.text.toString().length== 0)
                Utils.showToast(resources.getString(R.string.blankfield), this)
            else if (binding.lastname.text.toString().trim().equals("") && binding.lastname.text.toString().trim().length == 0)
                Utils.showToast(resources.getString(R.string.blankfield), this)
            else if (binding.phone.text.toString().trim().equals("") && binding.phone.text.toString().trim().length == 0)
                Utils.showToast(resources.getString(R.string.blankfield), this)
            else if (binding.password.text.toString().trim().equals("") && binding.password.text.toString().trim().length == 0)
                Utils.showToast(resources.getString(R.string.blankfield), this)
            else if (binding.aadhar.text.toString().trim().equals("") && binding.aadhar.text.toString().trim().length == 0)
                Utils.showToast(resources.getString(R.string.blankfield), this)
            else if (binding.pan.text.toString().trim().equals("") && binding.pan.text.toString().trim().length == 0)
                Utils.showToast(resources.getString(R.string.blankfield), this)
            else if (!this::imageFilePath.isInitialized)
                Utils.showToast(resources.getString(R.string.blankfieldadhar), this)
            else if (!this::imageFilePathPan.isInitialized)
                Utils.showToast(resources.getString(R.string.blankfieldpan), this)
            else{
                val adharfile = File(imageFilePath)
                val requestimage = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), adharfile)

                val panfile = File(imageFilePath)
                val requestimagepan = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), panfile)

                val firstname: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), binding.firstname.text.toString())
                val lastname: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), binding.lastname.text.toString())
                val phone: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), binding.phone.text.toString())
                val password: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), binding.password.text.toString())
                val aadhar: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), binding.aadhar.text.toString())
                val pan: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), binding.pan.text.toString())
                val email: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), binding.email.text.toString())
                val refercode: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), binding.referral.text.toString())

                register(firstname,lastname,phone,email,password,refercode,aadhar,pan,requestimage,requestimagepan)
            }
        }

        binding.adharbtn.setOnClickListener {
            adhar=1
            dialog()
        }

        binding.panbtn.setOnClickListener {
            adhar=2
            dialog()
        }
    }
    fun dialog(){
        val builder = AlertDialog.Builder(this@RegisterActivity)
        builder.setTitle("Pick Image")
        builder.setMessage("CHOOSE IMAGE MEDIA")

        builder.setPositiveButton("Camera") { dialog, which ->
            launch()
        }

        builder.setNegativeButton("Gallery") { dialog, which ->
            gallery()
        }

        builder.show()
    }
    fun launch(){
        ImagePicker.with(this) // User can only capture image from Camera
            .cameraOnly() // Image size will be less than 1024 KB
            .compress(1024)
            .crop() // Image resolution will be less than 1080 x 1920
            .maxResultSize(540, 960) //  Path: /storage/sdcard0/Android/data/package/files
            .saveDir(this@RegisterActivity.getExternalFilesDir(null)!!) //  Path: /storage/sdcard0/Android/data/package/files/DCIM
            .saveDir(this@RegisterActivity.getExternalFilesDir(Environment.DIRECTORY_DCIM)!!) //  Path: /storage/sdcard0/Android/data/package/files/Download
            .saveDir(this@RegisterActivity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!) //  Path: /storage/sdcard0/Android/data/package/files/Pictures
            .saveDir(this@RegisterActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!) //  Path: /storage/sdcard0/Android/data/package/files/Pictures/ImagePicker
            .saveDir(
                File(
                    this@RegisterActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "ImagePicker"
                )
            ) //  Path: /storage/sdcard0/Android/data/package/files/ImagePicker
            .saveDir(this@RegisterActivity.getExternalFilesDir("ImagePicker")!!) //  Path: /storage/sdcard0/Android/data/package/cache/ImagePicker
            .saveDir(
                File(
                    this@RegisterActivity.externalCacheDir,
                    "ImagePicker"
                )
            ) //  Path: /data/data/package/cache/ImagePicker
            .saveDir(
                File(
                    this@RegisterActivity.cacheDir,
                    "ImagePicker"
                )
            ) //  Path: /data/data/package/files/ImagePicker
            .saveDir(File(this@RegisterActivity.filesDir, "ImagePicker"))
            .start(IMAGE_REQ_CODE)
    }
    fun gallery(){
        ImagePicker.with(this)
            .galleryOnly()	//User can only select image from Gallery
            .compress(1024)
            .crop()
            .galleryMimeTypes(  //Exclude gif images
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
            // Image resolution will be less than 1080 x 1920
            //.maxResultSize(1080, 1920)
            .maxResultSize(540, 960)
            .start(IMAGE_REQ_CODE);
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            // Uri object will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            when (requestCode) {
                IMAGE_REQ_CODE -> {
                    if(adhar==1){
                        imageFilePath = getPath(uri)
                        binding.adharbtn.text = resources.getString(R.string.uploaded)
                        Utils.showToast("adhar card image uploaded.", this)
                    }
                    else if(adhar==2){
                        imageFilePathPan = getPath(uri)
                        binding.panbtn.text = resources.getString(R.string.uploaded)
                        Utils.showToast("pan card image uploaded.", this)
                    }


                }

            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this@RegisterActivity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@RegisterActivity, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getPath(selectedImaeUri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = this@RegisterActivity.managedQuery(
            selectedImaeUri, projection, null, null,
            null
        )
        if (cursor != null) {
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            return cursor.getString(columnIndex)
        }
        return selectedImaeUri.path.toString()
    }
    fun register(first_name: RequestBody,last_name: RequestBody,phoneno: RequestBody ,email:RequestBody,password: RequestBody,
                 referral_code: RequestBody,aadharcard: RequestBody,pancard: RequestBody,file: RequestBody,adharfile: RequestBody){
        //val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)


        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val viewModel: RegisterViewModel = ViewModelProvider(this).get(
                RegisterViewModel::class.java)
            viewModel.register(first_name,last_name,phoneno,email,password,referral_code,aadharcard,pancard,file,adharfile)?.observe(this@RegisterActivity,object :
                Observer<RegisterResponse?> {
                override fun onChanged(apiResponse: RegisterResponse?) {

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
                                this@RegisterActivity
                            )

                        } else {
                            if (apiResponse.getPosts().success == true) {
                                Utils.showToast(apiResponse.getPosts().message,this@RegisterActivity)
                                val intent = Intent(this@RegisterActivity, BuyActivity::class.java)
                                startActivity(intent)
                            } else if (apiResponse.getPosts().success==false) {
                                Utils.showToast(apiResponse.getPosts().message,this@RegisterActivity)
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
            Utils.showToast(resources.getString(R.string.no_internet),this@RegisterActivity)
        }
    }
}