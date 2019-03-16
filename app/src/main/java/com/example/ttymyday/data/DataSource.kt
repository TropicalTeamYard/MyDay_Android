package com.example.ttymyday.data

import android.content.Context
import java.security.AccessControlContext

class DataSource
{
    companion object {
        var user:User = User()
        /**
         * [临时缓存信息] 网络状态
         */
        var state:Boolean=true
        /**
         * [临时缓存信息] 用户状态
         */
        var userstate:Boolean = true

        var isLoad = false
        fun loadUser(context:Context) {
            val dbHelper = DBHelper(context)
            user.usertype = dbHelper.getValue("usertype","#NONE")
            user.username = dbHelper.getValue("username","NULL")
            user.nickname = dbHelper.getValue("nickname","NULL")
            user.token = dbHelper.getValue("token","NULL")
        }

        fun saveUser(context:Context){
            val dbHelper = DBHelper(context)
            dbHelper.setValue("usertype",user.usertype)
            dbHelper.setValue("username",user.username)
            dbHelper.setValue("nickname",user.nickname)
            dbHelper.setValue("token",user.token)
        }
    }
}