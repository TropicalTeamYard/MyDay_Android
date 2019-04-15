package com.example.ttymyday.view.page

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.*
import com.example.ttymyday.R
import kotlinx.android.synthetic.main.activity_login.*
import com.example.ttymyday.util.*
import com.example.ttymyday.data.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity(), OnClickListener {
    override fun onClick(v: View?) {
        if (v == btn_login){
            val account:String = tbx_account.text.toString()
            val password:String = tbx_password.text.toString();

            if (account != "" && password!= ""){
                val data:String = "method=login&username=$account&password=${MD5Util.getMd5(password)}&devicetype=mobile"
                Log.d(TAG,"登录,,url:${API.getRoute(RegionParam.USER)},data:$data")
                AsyncTask.post(API.getRoute(RegionParam.USER),data) {
                    if (it==null){
                        DataSource.state = false
                    } else{
                        DataSource.state = true
                        val responceJson:JSONObject = JSONObject(it)
                        val code:Int = responceJson.getInt("code")
                        val msg:String = responceJson.getString("msg")
                        //说明登录成功
                        if(code == 200){
                            val _data:JSONObject = responceJson.getJSONObject("data")
                            val username:String = _data.getString("username")
                            val nickname:String = _data.getString("nickname")
                            val token:String = _data.getString("credit")
                            //val usertype:String = _data.getString("usertype")
                            //缓存用户数据
                            val user: User = User("#CLOUD", username, nickname, token)
                            DataSource.user = user
                            //将缓存数据保存到数据库
                            DataSource.saveUser(this)
                            Log.d(TAG,"登录,以成功登录")

                            val intent:Intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    }
                }
            }
        }
        else if (v == btn_login_local){
            DataSource.user.usertype = "#LOCAL"
            DataSource.saveUser(this)
            Log.d(TAG,"使用本地账号登录")
            val intent:Intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener(this)
        btn_login_local.setOnClickListener(this)
    }

    companion object {
        val TAG:String = "LoginActivity"
    }
}
