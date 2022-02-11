package com.xxxxxxh.lib.base

import android.app.Application
import com.kingfisher.easy_sharedpreference_library.SharedPreferencesManager
import com.ladingwu.glidelibrary.GlideImageLocader
import com.lasingwu.baselibrary.ImageLoaderConfig
import com.lasingwu.baselibrary.ImageLoaderManager
import com.lasingwu.baselibrary.LoaderEnum
import com.yanzhenjie.nohttp.NoHttp
import net.robinx.lib.http.XRequest


abstract class BaseApplication : Application() {
    companion object{
        var instance:BaseApplication?=null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance=this
        SharedPreferencesManager.init(this, true)
        XRequest.initXRequest(applicationContext);
        NoHttp.initialize(this)
        val config = ImageLoaderConfig.Builder(LoaderEnum.GLIDE, GlideImageLocader())
            .maxMemory(40 * 1024 * 1024L) // 配置内存缓存，单位为Byte
            .build()
        ImageLoaderManager.getInstance().init(this, config)
    }

    abstract fun getAppId(): String
    abstract fun getAppName(): String
    abstract fun getUrl(): String
    abstract fun getAesPassword(): String
    abstract fun getAesHex(): String
    abstract fun getToken(): String
    abstract fun getPermissions():Array<String>

}