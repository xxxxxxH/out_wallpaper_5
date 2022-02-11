package net.basicmodel.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.fondesa.recyclerviewdivider.dividerBuilder
import kotlinx.android.synthetic.main.layout_f1.*
import kotlinx.android.synthetic.main.layout_f2.*
import kotlinx.android.synthetic.main.layout_f2.recycler
import net.basicmodel.R
import net.basicmodel.activity.Activity2
import net.basicmodel.event.MessageEvent
import net.basicmodel.holder.F2Holder
import net.basicmodel.utils.Constant
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import zhan.auto_adapter.AutoRecyclerAdapter

class Fragment2 : Fragment() {
    val data = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_f2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        val adapter = AutoRecyclerAdapter()
        recycler.adapter = adapter
        activity!!.dividerBuilder().build().addTo(recycler)
        recycler.layoutManager = GridLayoutManager(activity, 3)
        adapter.setHolder(F2Holder::class.java, R.layout.layout_item2)
        for (item in Constant.TYPES) {
            data.add(item)
        }
        adapter.setDataList(F2Holder::class.java, data).notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(even: MessageEvent) {
        val msg = even.getMessage()
        if (msg[0] == "c2") {
            val type = data[msg[1] as Int]
            val i = Intent(activity, Activity2::class.java)
            i.putExtra("type", type)
            startActivity(i)
        }
    }
}