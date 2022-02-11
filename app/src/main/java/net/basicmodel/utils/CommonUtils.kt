package net.basicmodel.utils

import android.os.Environment
import android.os.Message
import android.util.Log
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import net.basicmodel.event.MessageEvent
import org.greenrobot.eventbus.EventBus
import java.io.File

object CommonUtils {

    fun downloadImage(url:String,b:Boolean){
        val imagePath = Environment.getExternalStorageDirectory().toString() + File.separator + "${System.currentTimeMillis()}.jpg"
        FileDownloader.getImpl().create(url).setPath(imagePath).setListener(object : FileDownloadListener(){
            override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

            }

            override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

            }

            override fun completed(task: BaseDownloadTask?) {
                EventBus.getDefault().post(MessageEvent("completed",task!!.path,b))
            }


            override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

            }

            override fun error(task: BaseDownloadTask?, e: Throwable?) {
                EventBus.getDefault().post(MessageEvent("error"))
            }

            override fun warn(task: BaseDownloadTask?) {

            }

        }).start()
    }

}