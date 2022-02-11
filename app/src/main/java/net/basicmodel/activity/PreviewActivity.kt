package net.basicmodel.activity

import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dmax.dialog.SpotsDialog
import io.github.kobakei.materialfabspeeddial.FabSpeedDialMenu
import kotlinx.android.synthetic.main.activity_preview.*
import net.basicmodel.R
import net.basicmodel.event.MessageEvent
import net.basicmodel.model.DataEntity
import net.basicmodel.utils.CommonUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PreviewActivity : AppCompatActivity() {

    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        EventBus.getDefault().register(this)
        val entity = intent.getSerializableExtra("data") as DataEntity
        iv.displayImage(entity.img_url)
        val menu = FabSpeedDialMenu(this)
        menu.add("Download").setIcon(R.drawable.ic_action_add)
        menu.add("Set Wallpaper").setIcon(R.drawable.ic_action_add)
        menu.add("Share").setIcon(R.drawable.ic_action_add)
        fab.setMenu(menu)
        if (dialog == null) {
            dialog = SpotsDialog.Builder().setContext(this).build()
        }
        fab.addOnMenuItemClickListener { _, tv, _ ->
            when (tv!!.text) {
                "Download" -> {
                    dialog?.show()
                    CommonUtils.downloadImage(entity.img_url, false)
                }
                "Set Wallpaper" -> {
                    dialog?.show()
                    CommonUtils.downloadImage(entity.img_url, true)
                }
                "Share" -> {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, this.packageName)
                    startActivity(Intent.createChooser(intent, title))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        val msg = event.getMessage()
        when (msg[0]) {
            "completed" -> {
                dialog?.dismiss()
                if (msg[2] == true) {
                    val wallpaperManager = WallpaperManager.getInstance(this)
                    val bitmap = BitmapFactory.decodeFile(msg[1].toString())
                    wallpaperManager.setBitmap(bitmap)
                    bitmap.recycle()
                }
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }
            "error" -> {
                dialog?.dismiss()
                Toast.makeText(this, "Download Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}