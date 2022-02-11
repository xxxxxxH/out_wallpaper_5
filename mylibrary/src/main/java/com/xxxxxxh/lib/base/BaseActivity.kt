package com.xxxxxxh.lib.base

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.xxxxxxh.lib.entity.FacebookEntity
import com.xxxxxxh.lib.utils.MainInfoManager
import com.xxxxxxh.lib.utils.MyReceiver
import lolodev.permissionswrapper.callback.OnRequestPermissionsCallBack
import lolodev.permissionswrapper.wrapper.PermissionWrapper

abstract class BaseActivity : AppCompatActivity() {
    protected var appLink: String? = null
    protected var installReferrer: String? = null
    var msgCount = 0

    private val handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                msgCount++
                if (msgCount == 2) {
                    closeLoading()
                    startMainActivity()
                } else {
                    if (!TextUtils.isEmpty(appLink) && !TextUtils.isEmpty(installReferrer)) {
                        closeLoading()
                        startMainActivity()
                    } else {
                        sendEmptyMessageDelayed(1, 1000)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        val intentFilter = IntentFilter()
        intentFilter.addAction("action_download")
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        intentFilter.addDataScheme("package")
        registerReceiver(MyReceiver(), intentFilter)
        PermissionWrapper.Builder(this)
            .addPermissions(BaseApplication.instance!!.getPermissions())
            .addPermissionsGoSettings(true)
            .addRequestPermissionsCallBack(object : OnRequestPermissionsCallBack {
                override fun onGrant() {
                    showLoading()
                    appLink = MainInfoManager.getInstance().getFacebookInfo(this@BaseActivity).appLink
                    installReferrer =
                        MainInfoManager.getInstance().getGoogleInfo(this@BaseActivity).installReferrer
                    handler.sendEmptyMessage(1)
                }

                override fun onDenied(p0: String?) {
                    finish()
                }

            }).build().request()
    }

    abstract fun getLayoutId(): Int

    abstract fun startMainActivity()

    abstract fun showLoading()

    abstract fun closeLoading()
}