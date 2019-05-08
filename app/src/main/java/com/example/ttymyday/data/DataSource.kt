package com.example.ttymyday.data

import android.content.Context
import android.provider.Contacts
import android.support.annotation.UiThread
import android.util.Log
import com.example.ttymyday.listener.ActionListener
import com.example.ttymyday.model.ScheduleTag
import com.example.ttymyday.provider.ScheduleProvider
import com.example.ttymyday.util.TagConst
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object DataSource
{
    /**
     * 临时缓存-用户信息
     */
    var user: User = User()
    /**
     * 临时缓存-网络状态
     */
    var state:Boolean=true
    /**
     * 临时缓存-用户状态
     */
    var userstate:Boolean = true

    var isLoad = false

    var isTagsLoaded = false
    /**
     * 临时缓存-日程标签
     */
    var tags:ArrayList<ScheduleTag> = ArrayList()

    var mListener: ActionListener? = null

    fun loadUser(context:Context) {
        val dbHelper = DBHelper(context)
        user.usertype = dbHelper.getSettingsValue("usertype","#NONE")
        user.username = dbHelper.getSettingsValue("username","NULL")
        user.nickname = dbHelper.getSettingsValue("nickname","NULL")
        user.token = dbHelper.getSettingsValue("token","NULL")
    }

    fun saveUser(context:Context){
        val dbHelper = DBHelper(context)
        dbHelper.setSettingsValue("usertype", user.usertype)
        dbHelper.setSettingsValue("username", user.username)
        dbHelper.setSettingsValue("nickname", user.nickname)
        dbHelper.setSettingsValue("token", user.token)
    }

    fun setOnInitCompletedListener(listener: ActionListener){
        mListener = listener;
    }

    fun initAsync(context:Context){
        //TODO("异步加载数据")
        //Log.d(TagConst.DATA, "data::正在异步加载资源")

        val provider: ScheduleProvider = ScheduleProvider(context, DataSource.tags)
        if (!DataSource.isTagsLoaded) {
            DataSource.isTagsLoaded = true
            provider.initialize()
        }

        //Log.d(TagConst.DATA, "data::异步加载资源完毕")
    }
}


