package net.basicmodel.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fondesa.recyclerviewdivider.dividerBuilder
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.layout_f1.*
import net.basicmodel.R
import net.basicmodel.event.MessageEvent
import net.basicmodel.holder.F1Holder
import net.basicmodel.model.DataEntity
import net.basicmodel.net.RequestService
import net.basicmodel.net.RetrofitUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import zhan.auto_adapter.AutoRecyclerAdapter

class Activity2 : AppCompatActivity() {
    private var dialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)
        EventBus.getDefault().register(this)
        if (dialog == null) {
            dialog = SpotsDialog.Builder().setContext(this).build()
        }
        dialog?.show()
        val type = intent.getStringExtra("type") as String
        RetrofitUtils.get().retrofit().create(RequestService::class.java).getData2(type)
            .enqueue(object : Callback<ArrayList<DataEntity>> {
                override fun onResponse(
                    call: Call<ArrayList<DataEntity>>,
                    response: Response<ArrayList<DataEntity>>
                ) {
                    val adapter = AutoRecyclerAdapter()
                    recycler.adapter = adapter
                    recycler.layoutManager = LinearLayoutManager(this@Activity2)
                    recycler.setHasFixedSize(true)
                   this@Activity2.dividerBuilder().build().addTo(recycler)
                    adapter.setHolder(F1Holder::class.java, R.layout.layout_item1)
                    adapter.setDataList(F1Holder::class.java, response.body()).notifyDataSetChanged()
                    dialog?.dismiss()
                }

                override fun onFailure(call: Call<ArrayList<DataEntity>>, t: Throwable) {
                    dialog?.dismiss()
                }

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        val msg = event.getMessage()
        if (msg[0] == "c1") {
            val entity = msg[1] as DataEntity
            val i = Intent(this, PreviewActivity::class.java)
            i.putExtra("data", entity)
            startActivity(i)
        }
    }
}