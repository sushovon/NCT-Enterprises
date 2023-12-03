package com.nctapplication.commons

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.nctapplication.databinding.CustomProgressDialogBinding

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
    fun loaderDialog(context: Context): Dialog {
        val loaderDialog = Dialog(context)
        val layoutInflater = LayoutInflater.from(context)
        val progressDialog  = CustomProgressDialogBinding.inflate(layoutInflater)
        loaderDialog.setCancelable(false)
        loaderDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loaderDialog.setContentView(progressDialog.root)
        return loaderDialog
    }

    var mobile=""
}