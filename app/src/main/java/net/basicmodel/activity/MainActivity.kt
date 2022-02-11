package net.basicmodel.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.liulishuo.filedownloader.FileDownloader
import com.xxxxxxh.lib.utils.UpdateAssistant
import kotlinx.android.synthetic.main.activity_main.*
import net.basicmodel.R
import net.basicmodel.fragment.Fragment1
import net.basicmodel.fragment.Fragment2
import net.basicmodel.fragment.Fragment3

class MainActivity : AppCompatActivity() {
    private var views: ArrayList<Fragment> = ArrayList()
    private val title = arrayOf("Hot", "Types", "Setting")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FileDownloader.setup(this)
        views.add(Fragment1())
        views.add(Fragment2())
        views.add(Fragment3())
        tab.setViewPager(viewpager, title, this, views)
        UpdateAssistant.get().update(this)
    }
}