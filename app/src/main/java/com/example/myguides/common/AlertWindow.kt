package com.example.myguides.common

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

class AlertWindow {
    companion object {
        fun show(context: Context, error: String) {
            val dlgAlert: AlertDialog.Builder =
                AlertDialog.Builder(context)

            dlgAlert.setMessage(error)
            dlgAlert.setTitle("Something went wrong")
            dlgAlert.setPositiveButton("OK", null)
            dlgAlert.setCancelable(true)
            dlgAlert.create().show()

            dlgAlert.setPositiveButton("Ok",
                DialogInterface.OnClickListener { dialog, which -> })
        }
    }
}
