package com.xxxxxxh.lib.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

@RequiresApi(api = Build.VERSION_CODES.O)
object PermissionCheckManager {

    private fun requestPermission(context: Context) {
        Observable.just<Boolean>(context.packageManager.canRequestPackageInstalls())
            .flatMap(Function<Boolean?, ObservableSource<Boolean?>?> { aBoolean ->
                checkPermission(aBoolean, context)
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { aBoolean ->

            }
    }

    private fun checkPermission(b: Boolean, context: Context): Observable<Boolean>? {
        return Observable.just(b).filter {
            if (!b) {
                toInstallPermissionSettingIntent(context)
                false
            } else {
                true
            }
        }
    }

    private fun toInstallPermissionSettingIntent(context: Context) {
        val packageURI = Uri.parse("package:" + context.packageName)
        val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI)
        (context as Activity).startActivity(intent)
    }
}