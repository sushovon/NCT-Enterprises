package com.nctapplication.commons

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity

object Commonfun {
    fun Commonmethod(url: String, activity: AppCompatActivity){
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        activity.startActivity(openURL)
    }

    fun share(code: String, context: Context){
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type="text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, code);
        context.startActivity(Intent.createChooser(shareIntent,"Share Via"))
    }
}