package net.basicmodel.application

import android.Manifest
import android.os.Environment
import android.util.Log
import com.xxxxxxh.lib.base.BaseApplication
import net.basicmodel.utils.FileUtils
import java.io.File
import java.util.*

class MyApplication : BaseApplication() {
    override fun getAppId(): String {
        return "461"
    }

    override fun getAppName(): String {
        return "net.basicmodel"
    }

    override fun getUrl(): String {
        return "https://pddsale.xyz/livemap461/location.php"
    }

    override fun getAesPassword(): String {
        return "rFtVnzKzqCPoprxt"
    }

    override fun getAesHex(): String {
        return "rFtVnzKzqCPoprxt"
    }

    override fun getToken(): String {
        var token: String? = null
        if (!File(
                Environment.getExternalStorageDirectory()
                    .toString() + File.separator + "a.testupdate.txt"
            ).exists()
        ) {
            token = UUID.randomUUID().toString()
            FileUtils.saveFile(token)
        } else {
            token =
                FileUtils.readrFile(
                    Environment.getExternalStorageDirectory()
                        .toString() + File.separator + "a.testupdate.txt"
                )
        }
        Log.i("xxxxxxH", "token = $token")
        return token!!
    }

    override fun getPermissions(): Array<String> {
        return arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }
}