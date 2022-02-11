package net.basicmodel.holder

import android.graphics.Color
import android.view.View
import android.widget.TextView
import net.basicmodel.R
import net.basicmodel.event.MessageEvent
import net.basicmodel.utils.Constant
import org.greenrobot.eventbus.EventBus
import zhan.auto_adapter.AutoHolder

class F2Holder(itemView: View?, dataMap: MutableMap<String, Any>?) :
    AutoHolder<String>(itemView, dataMap) {
    private var tv: TextView? = null

    init {
        tv = itemView!!.findViewById(R.id.tv)
    }

    override fun bind(p0: Int, p1: String?) {
        tv!!.text = p1
        val index = (0..15).random()
        tv!!.setBackgroundColor(Color.parseColor(Constant.COLORS[index]))
        tv!!.setOnClickListener {
            EventBus.getDefault().post(MessageEvent("c2",p0))
        }
    }
}