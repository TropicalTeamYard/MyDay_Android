package com.example.ttymyday

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ttymyday.data.DataSource
import kotlinx.android.synthetic.main.fragment_my.*
import kotlinx.android.synthetic.main.fragment_my.view.*


class MyFragment : Fragment(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG,"usertype:${DataSource.user.usertype}")
        if (DataSource.user.usertype =="#CLOUD"){
            tv_account.text = DataSource.user.nickname
            tv_account_description.text = "Hello ${DataSource.user.username}"
            if (DataSource.state == false) {
                tv_account_description.setTextColor(Color.RED)
                tv_account_description.text = "网络异常"
            } else if (DataSource.userstate == false){
                tv_account_description.setTextColor(Color.RED)
                tv_account_description.text ="用户异常，请尝试重新登录"
            }
        } else if (DataSource.user.usertype == "#LOCAL"){
            tv_account.text = "本地账户"
            tv_account_description.text="若要使用更多功能，请登录云账户"
        } else {
            tv_account.text =  getString(R.string.text_login)
            tv_account_description.text = getString(R.string.title_nologin_description)
        }

        my_toolbar.setOnClickListener(this)
    }

    //region events
    override fun onClick(v: View?) {
        //顶部导航按钮
        if (v == my_toolbar){
            if (DataSource.user.usertype == "#CLOUD"){
                val intent = Intent(context,UserDetailActivity::class.java)
                startActivity(intent)
                Log.d(TAG,"顶部导航栏单击，用户已登录")
            } else  {
                //跳转到登录界面
                val intent = Intent(context,LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    //endregion

    companion object {
        var TAG:String = "MyFragment"
    }
}
