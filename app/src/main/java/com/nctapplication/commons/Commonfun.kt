package com.nctapplication.commons

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity

object Commonfun {
    fun Commonmethod(url: String, activity: AppCompatActivity){
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        activity.startActivity(openURL)
    }
}