package com.task.news.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.task.news.R

object DialogBuilder {
    fun showAdviceDialog(activity: AppCompatActivity , btnTitle: String, msg: String, block: (() -> Unit)?){
        val dialog = Dialog(activity)
            dialog.setContentView(R.layout.alert_dialog_layout)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        val btn: AppCompatButton = dialog.findViewById(R.id.dialog_btn)
        val msgText: TextView = dialog.findViewById(R.id.dialog_msg)
        if (btnTitle.isNotEmpty()){
            btn.text = btnTitle
        }
        msgText.text = msg
        btn.setOnClickListener {
            dialog.dismiss()
            block?.invoke()
        }
        dialog.show()
    }
}