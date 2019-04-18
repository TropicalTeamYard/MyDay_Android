package com.example.ttymyday.data

import android.content.Context
import com.example.ttymyday.model.ScheduleTag

object DataSource
{
    var user: User = User()
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

    /**
     * 创建一个日程的标签，依赖 settings::schedule_tag_index 和 user.username
     * @param title 日程的标签名
     */
//    fun createScheduleTag(context:Context,title:String,owner:String? = null):ScheduleTag{
//        val dbHelper = DBHelper.getInstance(context)
//
//        //currentIndex 用于统计当前用户日程清单的索引号[或数量]
//
//        //Log.d("DataSource",dbHelper.getSettingsValue("schedule_tag_index","0"))
//
//        var currentIndex = dbHelper.getSettingsValue("schedule_tag_index","0").toInt()
//        currentIndex +=1
//
//        val scheduleTag = ScheduleTag(-1,"category_$currentIndex",title,(owner?: user.friendname),"owner")
//
//        dbHelper.setScheduleTagValue(scheduleTag)
//        dbHelper.setSettingsValue("schedule_tag_index",currentIndex.toString())
//
//        return scheduleTag
//    }

}