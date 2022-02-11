package net.basicmodel.holder

import android.content.Intent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.xxxxxxh.lib.base.BaseApplication
import me.panpf.sketch.SketchImageView
import net.basicmodel.R
import net.basicmodel.activity.PreviewActivity
import net.basicmodel.event.MessageEvent
import net.basicmodel.model.DataEntity
import org.greenrobot.eventbus.EventBus
import zhan.auto_adapter.AutoHolder


class F1Holder(itemView: View?, dataMap: MutableMap<String, Any>?) :
    AutoHolder<DataEntity>(itemView, dataMap) {

    private var iv: SketchImageView? = null
    private var tv: TextView? = null


    init {
        iv = itemView?.findViewById(R.id.iv)
        tv = itemView?.findViewById(R.id.tv)
    }

    override fun bind(p0: Int, p1: DataEntity?) {
        iv!!.displayImage(p1!!.img_url)
        tv!!.text = p1.img_title
        tv!!.setOnClickListener {
            EventBus.getDefault().post(MessageEvent("c1",p1))
        }
    }
}