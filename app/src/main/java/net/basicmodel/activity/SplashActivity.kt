package net.basicmodel.activity

import android.app.AlertDialog
import android.content.Intent
import com.xxxxxxh.lib.base.BaseActivity
import dmax.dialog.SpotsDialog
import net.basicmodel.R

class SplashActivity : BaseActivity() {

    private var dialog: AlertDialog?=null

    override fun onResume() {
        super.onResume()
        if (dialog == null){
            dialog = SpotsDialog.Builder().setContext(this).build()
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun showLoading() {
        dialog?.show()
    }

    override fun closeLoading() {
        dialog?.dismiss()
    }
}