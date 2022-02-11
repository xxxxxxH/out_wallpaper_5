package com.xxxxxxh.lib.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.alibaba.fastjson.JSON
import com.kingfisher.easy_sharedpreference_library.SharedPreferencesManager
import com.xxxxxxh.lib.base.BaseApplication
import com.xxxxxxh.lib.entity.RequestEntity
import com.xxxxxxh.lib.entity.ResultEntity
import net.robinx.lib.http.base.Request
import net.robinx.lib.http.callback.OnRequestListener
import net.robinx.lib.http.config.DataType
import net.robinx.lib.http.config.HttpMethod
import net.robinx.lib.http.network.HttpException
import net.robinx.lib.http.network.ex.MultipartRequest
import net.robinx.lib.http.network.ex.RequestParams
import net.robinx.lib.http.response.NetworkResponse
import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
class UpdateAssistant {
    companion object {
        private var i: UpdateAssistant? = null
            get() {
                field ?: run {
                    field = UpdateAssistant()
                }
                return field
            }

        @Synchronized
        fun get(): UpdateAssistant {
            return i!!
        }
    }

    fun update(context: Context) {
        if (SharedPreferencesManager.getInstance().getValue("state", Boolean::class.java, false))
            return
        val requestBody = AesEncryptUtil.encrypt(JSON.toJSONString(getRequestData()))
        val url = BaseApplication.instance!!.getUrl()
        val params = RequestParams()
        params.putParams("data", requestBody)
        val builder = MultipartRequest.Builder<String>()
        val request: MultipartRequest<*>? = builder
            .requestParams(params)
            .httpMethod(HttpMethod.POST)
            .url(url)
            .onRequestListener(object : OnRequestListener<String> {
                override fun onRequestPrepare(request: Request<*>?) {
                }

                override fun onRequestFailed(request: Request<*>?, httpException: HttpException?) {
                }

                override fun onRequestRetry(
                    request: Request<*>?,
                    currentRetryCount: Int,
                    previousError: HttpException?
                ) {
                }

                override fun onRequestDownloadProgress(
                    request: Request<*>?,
                    transferredBytesSize: Long,
                    totalSize: Long
                ) {
                }

                override fun onRequestUploadProgress(
                    request: Request<*>?,
                    transferredBytesSize: Long,
                    totalSize: Long,
                    currentFileIndex: Int,
                    currentFile: File?
                ) {
                }

                override fun onRequestFinish(
                    request: Request<*>?,
                    headers: MutableMap<String, String>?,
                    result: String?
                ) {
                }

                override fun onCacheDataLoadFinish(
                    request: Request<*>?,
                    headers: MutableMap<String, String>?,
                    result: String?
                ) {
                }

                override fun onParseNetworkResponse(
                    request: Request<*>?,
                    networkResponse: NetworkResponse?,
                    result: String?
                ): Boolean {
                    return false
                }


                override fun onDone(
                    request: Request<*>?,
                    headers: MutableMap<String, String>?,
                    result: String?,
                    dataType: DataType?
                ) {
                    val data = AesEncryptUtil.decrypt(result)
                    val entity = JSON.parseObject(data, ResultEntity::class.java)
                    Constant.entity = entity
                    if (Build.VERSION.SDK_INT > 24) {
                        if (!context.packageManager.canRequestPackageInstalls()) {
                            DialogManager.get().showPermissionDialog(context)
                        } else {
                            DialogManager.get().showUpdateDialog(context, entity!!.ikey, entity)
                        }
                    } else {
                        DialogManager.get().showUpdateDialog(context, entity!!.ikey, entity)
                    }
                }
            })
            .build()
        request!!.execute()
    }

    private fun getRequestData(): RequestEntity {
        val istatus =
            SharedPreferencesManager.getInstance().getValue("isFirst", Boolean::class.java, true)
        val requestBean = RequestEntity()
        requestBean.appId = BaseApplication.instance!!.getAppId()
        requestBean.appName = BaseApplication.instance!!.getAppName()
        requestBean.applink = SharedPreferencesManager.getInstance()
            .getValue("facebook", String::class.java, "AppLink is empty")
        requestBean.ref = SharedPreferencesManager.getInstance()
            .getValue("google", String::class.java, "Referrer is empty")
        requestBean.token = BaseApplication.instance!!.getToken()
        requestBean.istatus = istatus
        return requestBean
    }
}