package com.example.ttymyday.data

import android.content.Context
import com.example.ttymyday.listener.ActionListener
import com.example.ttymyday.model.ScheduleTag
import com.example.ttymyday.provider.ScheduleProvider
import com.example.ttymyday.view.converter.ColorIconConverter

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
    var autoTags:ArrayList<ScheduleTag> = ArrayList()
    var sharedTags:ArrayList<ScheduleTag> = ArrayList()

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
        //TODO("异步加载数据与资源")
        //Log.d(TagConst.DATA, "data::正在异步加载资源")
        autoTags.add(ScheduleTag(-1,"clockIn","打卡",null,ColorIconConverter.FAVUSER,10))
        autoTags.add(ScheduleTag(-1,"memory","生日&纪念日",null,ColorIconConverter.MEDAL,4))
        autoTags.add(ScheduleTag(-1,"important","重要",null,ColorIconConverter.MEDICINE,3))
        autoTags.add(ScheduleTag(-1,"table","课程表",null,ColorIconConverter.VISITOR,9))

        sharedTags.add(ScheduleTag(-1,"clockIn","Item1",null,ColorIconConverter.FAVUSER))
        sharedTags.add(ScheduleTag(-1,"memory","Item2",null,ColorIconConverter.MEDAL))
        sharedTags.add(ScheduleTag(-1,"important","Item3",null,ColorIconConverter.CONCAT))
        sharedTags.add(ScheduleTag(-1,"table","Item4",null,ColorIconConverter.VISITOR))

        val provider: ScheduleProvider = ScheduleProvider(context, DataSource.tags)
        if (!DataSource.isTagsLoaded) {
            DataSource.isTagsLoaded = true
            provider.initialize()
        }

        //Log.d(TagConst.DATA, "data::异步加载资源完毕")
    }
}


