package com.xxxxxxh.lib.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kingfisher.easy_sharedpreference_library.SharedPreferencesManager

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_PACKAGE_ADDED) {
            val data = intent.dataString.toString()
            data.let {
                if (data.contains(context!!.packageName.toString())) {
                    SharedPreferencesManager.getInstance().putValue("state", true)
                }
            }
        }
    }
}