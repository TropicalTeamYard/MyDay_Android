package com.example.ttymyday.view.page

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import com.example.ttymyday.R
import com.example.ttymyday.data.DataSource
import kotlinx.android.synthetic.main.activity_user_detail.*


class UserDetailActivity : AppCompatActivity() ,View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        tv_ud_accout.text = DataSource.user.username
        edt_ud_nickname.text.clear()
        edt_ud_nickname.text.append(DataSource.user.nickname)


        btn_exitLogin.setOnClickListener(this)
    }

    //region events
    override fun onClick(v: View?) {
        Log.d(TAG,"点击了注销按钮")
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setMessage("注销后，我们会在此设备上忘记您的账户，直到您再次登录为止。")
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"取消",fun (dialog, which){
            dialog.cancel()
        })
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"是的，注销",fun(dialog,which){
            DataSource.user.usertype="#NONE"
            DataSource.saveUser(this)
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            dialog.cancel()
        })
        alertDialog.show()
    }
    //endregion

    companion object {
        var TAG:String = "UserDetailActivity"
    }
}
