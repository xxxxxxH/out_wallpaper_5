package com.xxxxxxh.lib.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.github.javiersantos.bottomdialogs.BottomDialog
import com.yanzhenjie.nohttp.Headers
import com.yanzhenjie.nohttp.RequestMethod
import com.yanzhenjie.nohttp.download.DownloadRequest
import com.yanzhenjie.nohttp.download.SimpleDownloadListener
import com.yanzhenjie.nohttp.download.SyncDownloadExecutor
import java.io.File

class Download {
    companion object {
        private var i: Download? = null
            get() {
                field ?: run {
                    field = Download()
                }
                return field
            }

        @Synchronized
        fun get(): Download {
            return i!!
        }
    }

    fun download(context: Context, url: String,listener: CustomDownloadListener) {
        val downloadPath = Environment.getExternalStorageDirectory().toString()
        if (TextUtils.isEmpty(url)) {
            return
        }
        val request = DownloadRequest(url, RequestMethod.GET, downloadPath, true, true)
        Thread(Runnable {
            SyncDownloadExecutor.INSTANCE.execute(0, request, object : SimpleDownloadListener() {
                override fun onDownloadError(what: Int, exception: Exception?) {
                    super.onDownloadError(what, exception)
                    listener.downloadError(exception.toString())
                }

                override fun onStart(
                    what: Int,
                    isResume: Boolean,
                    rangeSize: Long,
                    responseHeaders: Headers?,
                    allCount: Long
                ) {
                    super.onStart(what, isResume, rangeSize, responseHeaders, allCount)
                }

                override fun onProgress(what: Int, progress: Int, fileCount: Long, speed: Long) {
                    super.onProgress(what, progress, fileCount, speed)
                    listener.progress(progress)
                }

                override fun onFinish(what: Int, filePath: String?) {
                    super.onFinish(what, filePath)
                    listener.downloadDone(filePath!!)
                }

            })
        }).start()

    }

}