package com.nctapplication

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.nctapplication.circleimage.CircleImageView
import com.nctapplication.commons.Constants
import com.nctapplication.commons.MyApp
import com.nctapplication.commons.MyApplication
import com.nctapplication.databinding.ActivityDashboardBinding
import com.nctapplication.fragments.*
import com.nctapplication.model.couponprice.Data
import com.nctapplication.response.CouponPriceResponse
import com.nctapplication.response.ProfileApiResponse
import com.nctapplication.util.Utils
import com.nctapplication.viewmodel.CouponPriceViewModel
import com.nctapplication.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import okhttp3.MultipartBody
import okhttp3.RequestBody

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityDashboardBinding
    lateinit var head : View
    lateinit var profileimage : CircleImageView
    lateinit var name : TextView
    lateinit var email : TextView
    lateinit var coupon_price :TextView
    //private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        // Pass the ActionBarToggle action into the drawerListener
        actionBarToggle = ActionBarDrawerToggle(this, binding.drawerLayout,binding.toolbar, 0, 0)//added toolbar to show the hamburger menu
        actionBarToggle.isDrawerIndicatorEnabled = true
        // Call syncState() on the action bar so it'll automatically change to the back button when the drawer layout is open
        actionBarToggle.syncState()
        binding.drawerLayout.addDrawerListener(actionBarToggle)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))

        // Display the hamburger icon to launch the drawer
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        head = binding.navigationView.getHeaderView(0)
        profileimage = head.findViewById(R.id.profile_pic)
        name=head.findViewById(R.id.name)
        email=head.findViewById(R.id.email)
        coupon_price=head.findViewById(R.id.coupon_price)

        //val data=1/0
        getprofile()
        couponprice()
        /*val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fooFragment = DashboardFragment()
        fragmentTransaction.add(R.id.content_frame, fooFragment)*/
        displayView(0)


        binding.navigationView.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {

                R.id.dashboard -> {
                    //fragmentManager.beginTransaction().add(R.id.content_frame, DashboardFragment()).commit()
                    displayView(0)
                    //this.binding.drawerLayout.closeDrawer(GravityCompat.START)
                    closeDrawer()
                    true
                }
                R.id.profile -> {
                    displayView(1)
                    closeDrawer()
                    true
                }
                R.id.wallet -> {
                    displayView(2)
                    closeDrawer()

                    true
                }
                R.id.repurchse -> {
                    displayView(3)
                    closeDrawer()
                    true
                }
                R.id.directsale -> {
                    displayView(4)
                    closeDrawer()
                    true
                }
                R.id.membership -> {
                    displayView(5)
                    closeDrawer()
                    true
                }
                R.id.claim -> {
                    displayView(6)
                    closeDrawer()
                    true
                }
                R.id.payout -> {
                    displayView(7)
                    closeDrawer()
                    true
                }
                R.id.memberlist -> {
                    displayView(8)
                    closeDrawer()
                    true
                }
                R.id.logout -> {
                    displayView(9)
                    closeDrawer()
                    true
                }
                else -> {
                    false
                }
            }
        }

    }
    // override the onSupportNavigateUp() function to launch the Drawer when the hamburger icon is clicked
    override fun onSupportNavigateUp(): Boolean {
        binding.drawerLayout.openDrawer(binding.navigationView)
        return true
    }

    // override the onBackPressed() function to close the Drawer when the back button is clicked
    override fun onBackPressed() {

        if (this.binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

        /*if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)*/


    }
    fun closeDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }
    private fun displayView(position: Int) {
        var fragment: Fragment? = null
        val title = getString(R.string.app_name)
        when (position) {
            0 -> fragment = DashboardFragment()
            1 -> fragment = ProfileFragment()
            2 -> fragment = WalletFragment()
            3 -> fragment = RepurchaseFragment()
            4 -> fragment = DirectFragment()
            5 -> fragment = MemberFragment()
            6 -> fragment = ClaimFragment()
            7 -> fragment = PayoutFragment()
            8 -> fragment = MemberlistFragment()
            9 -> {
                val builder1 = AlertDialog.Builder(this@DashboardActivity)
                builder1.setMessage("Do you want to logout?")
                builder1.setCancelable(true)
                builder1.setPositiveButton(
                    "Yes"
                ) { dialog: DialogInterface, id: Int ->
                    dialog.cancel()
                    Paper.book().delete("login")
                    Paper.book().delete("memberid")
                    startActivity(Intent(this@DashboardActivity, MainActivity::class.java))
                    finish()
                }
                builder1.setNegativeButton(
                    "No"
                ) { dialog: DialogInterface, id: Int -> dialog.cancel() }
                val alert = builder1.create()
                alert.show()
            }
            else -> {}
        }
        if (fragment != null) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.content_frame, fragment)
            fragmentTransaction.commit()

            // set the toolbar title
            supportActionBar!!.setTitle(title)
        }
    }
    fun getprofile(){
        //val TAG = javaClass.simpleName

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("member_id", Paper.book().read<Int>("memberid", 0).toString())
            .build()

        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val viewModel: ProfileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
            viewModel.getProfile(requestBody)?.observe(this@DashboardActivity,object :
                Observer<ProfileApiResponse?> {
                override fun onChanged(apiResponse: ProfileApiResponse?) {

                    if (apiResponse == null) {
                        // handle error here
                        return
                    }
                    if (apiResponse.error == null) {
                        // call is successful
                        if (apiResponse.posts == null) {

                        } else {

                            if (apiResponse.getPosts().success == true) {


                                var data : ArrayList<com.nctapplication.model.profile.Data> = apiResponse.getPosts().data

                                name.setText(data.get(0).memberFname)
                                email.setText(data.get(0).memberEmail)

                                if (MyApplication.isValidContextForGlide(this@DashboardActivity)) {
                                    // Load image via Glide lib using context
                                    Glide.with(this@DashboardActivity)
                                        .load(Constants.BASE_URL_IMAGE+data.get(0).memberImage).placeholder(R.drawable.placeholderprofile)
                                        .error(Glide.with(this@DashboardActivity).load(R.drawable.placeholderprofile))
                                        .into(profileimage)
                                } else {
                                    Glide.with(this@DashboardActivity).load(R.drawable.placeholderprofile).into(profileimage)
                                }

                            } else if (apiResponse.getPosts().success==false) {

                            }


                        }
                    } else {
                        // call failed.

                        val e = apiResponse.error
                    }
                }

            })
        }else{

        }
    }
    fun couponprice(){
        //val TAG = javaClass.simpleName

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("member_id", Paper.book().read<Int>("memberid", 0).toString())
            .build()

        if(MyApp.getInstance()!!.isNetworkAvailable()){
            val viewModel: CouponPriceViewModel = ViewModelProvider(this).get(CouponPriceViewModel::class.java)
            viewModel.coupon_price(requestBody)?.observe(this@DashboardActivity,object :
                Observer<CouponPriceResponse?> {
                override fun onChanged(apiResponse: CouponPriceResponse?) {

                    if (apiResponse == null) {
                        // handle error here
                        return
                    }
                    if (apiResponse.error == null) {
                        // call is successful
                        if (apiResponse.posts == null) {

                        } else {

                            if (apiResponse.getPosts().success == true) {
                                var data    : Data? = apiResponse.getPosts().data
                                coupon_price.setText("Total coupon price: "+ data?.totalCouponPrice.toString())


                            } else if (apiResponse.getPosts().success==false) {

                            }


                        }
                    } else {
                        // call failed.

                        val e = apiResponse.error
                    }
                }

            })
        }else{

        }
    }

}