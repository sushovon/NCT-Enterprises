package com.nctapplication.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.nctapplication.R
import com.nctapplication.commons.Constants
import com.nctapplication.commons.MyApp
import com.nctapplication.commons.MyApplication
import com.nctapplication.databinding.FragmentProfileBinding
import com.nctapplication.model.profileupdate.Data
import com.nctapplication.response.ProfileApiResponse
import com.nctapplication.response.ProfileUpdateResponse
import com.nctapplication.util.Utils
import com.nctapplication.viewmodel.ProfileViewModel
import com.nctapplication.viewmodel.UpdateProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var rootView: View? = null
    private lateinit var binding: FragmentProfileBinding
    var doubleBounce: Sprite = DoubleBounce()
    val IMAGE_REQ_CODE : Int = 102
    lateinit var imageFilePath : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (rootView != null) {
            val parent = rootView!!.getParent() as ViewGroup
            if (parent != null) parent.removeView(rootView)
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        val rootView = binding.root
        retainInstance = true

        return rootView
    }
    fun getprofile(){
        //val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("member_id", Paper.book().read<Int>("memberid", 0).toString())
            .build()

        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val viewModel: ProfileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
            viewModel.getProfile(requestBody)?.observe(viewLifecycleOwner,object : Observer<ProfileApiResponse?> {
                override fun onChanged(apiResponse: ProfileApiResponse?) {

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


                                var data : ArrayList<com.nctapplication.model.profile.Data> = apiResponse.getPosts().data

                                binding.name.setText(data.get(0).memberFname)
                                binding.phone.setText(data.get(0).memberPhoneno)
                                binding.email.setText(data.get(0).memberEmail)
                                binding.adhar.setText(data.get(0).memberAadharcard)
                                binding.pan.setText(data.get(0).memberPancard)
                                binding.userid.text= getResources().getString(R.string.userid)+":\n"+data.get(0).userId
                                binding.referid.text=getResources().getString(R.string.referid)+":\n"+data.get(0).referralCode


                                if (MyApplication.isValidContextForGlide(context)) {
                                    // Load image via Glide lib using context
                                    Glide.with(context!!)
                                        .load(Constants.BASE_URL_IMAGE+data.get(0).memberImage).placeholder(R.drawable.placeholderprofile)
                                        .error(Glide.with(context!!).load(R.drawable.placeholderprofile))
                                        .into(binding.profilePic)
                                } else {
                                    Glide.with(context!!).load(R.drawable.placeholderprofile).into(binding.profilePic)
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
        getprofile()
        binding.camera.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
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
        binding.submitbtn.setOnClickListener {
            if(binding.email.text.toString().trim().length==0 && binding.email.text.toString().trim().equals("")){
                Utils.showToast(resources.getString(R.string.blankfieldemail),activity)
            }
            else if(imageFilePath.length==0){
                Utils.showToast(resources.getString(R.string.profileimageblank),activity)
            }else{
                val file = File(imageFilePath)
                val requestimage = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)

                val member_id: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), Paper.book().read<Int>("memberid", 0).toString())
                val member_email: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), binding.email.text.toString().trim())
                getupdateprofile(member_id, member_email, requestimage)
            }
        }
    }
    fun launch(){
        ImagePicker.with(this) // User can only capture image from Camera
            .cameraOnly() // Image size will be less than 1024 KB
            .compress(1024)
            .crop() // Image resolution will be less than 1080 x 1920
            .maxResultSize(540, 960) //  Path: /storage/sdcard0/Android/data/package/files
            .saveDir(requireActivity().getExternalFilesDir(null)!!) //  Path: /storage/sdcard0/Android/data/package/files/DCIM
            .saveDir(requireActivity().getExternalFilesDir(Environment.DIRECTORY_DCIM)!!) //  Path: /storage/sdcard0/Android/data/package/files/Download
            .saveDir(requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!) //  Path: /storage/sdcard0/Android/data/package/files/Pictures
            .saveDir(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!) //  Path: /storage/sdcard0/Android/data/package/files/Pictures/ImagePicker
            .saveDir(
                File(
                    requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "ImagePicker"
                )
            ) //  Path: /storage/sdcard0/Android/data/package/files/ImagePicker
            .saveDir(requireActivity().getExternalFilesDir("ImagePicker")!!) //  Path: /storage/sdcard0/Android/data/package/cache/ImagePicker
            .saveDir(
                File(
                    requireActivity().externalCacheDir,
                    "ImagePicker"
                )
            ) //  Path: /data/data/package/cache/ImagePicker
            .saveDir(
                File(
                    requireActivity().cacheDir,
                    "ImagePicker"
                )
            ) //  Path: /data/data/package/files/ImagePicker
            .saveDir(File(requireActivity().filesDir, "ImagePicker"))
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
                    imageFilePath = getPath(uri)
                    Glide.with(requireActivity())
                        .load(File(getPath(uri)))
                        .into(binding.profilePic)
                }

            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireActivity(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getPath(selectedImaeUri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().managedQuery(
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
    fun getupdateprofile(member_id : RequestBody, member_email :RequestBody, requestimage :RequestBody){
        //val TAG = javaClass.simpleName
        binding.spinKit.visibility = View.VISIBLE
        binding.spinKit.setIndeterminateDrawable(doubleBounce)


        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val viewModel: UpdateProfileViewModel = ViewModelProvider(this).get(UpdateProfileViewModel::class.java)
            viewModel.updateprofile(member_id,member_email,requestimage)?.observe(viewLifecycleOwner,object : Observer<ProfileUpdateResponse?> {
                override fun onChanged(apiResponse: ProfileUpdateResponse?) {

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
                                Utils.showToast(apiResponse.getPosts().message,activity)
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