package com.example.ttymyday.provider

import android.content.Context
import android.util.Log
import com.example.ttymyday.data.DBHelper
import com.example.ttymyday.model.ScheduleTag
import com.example.ttymyday.util.TagConst
import com.example.ttymyday.util.TimeUtil
import java.util.*
import kotlin.collections.ArrayList

/**
 * @param context 依赖的上下文
 * @param tags 需要加载的对象
 */
class ScheduleProvider(private var context: Context,var tags:ArrayList<ScheduleTag>){

    /**
     * 初始化[tags],即从数据库中读取数据
     */
    fun initialize()
    {
        val helper = DBHelper(context)
        helper.fillScheduleTag(tags)
    }

    /**
     * 创建一个日程标签，依赖settings::schedule_tag_index 和 user.username
     */
    fun createScheduleTag(icon:Int,title:String):ScheduleTag{
        //TODO("当联网时，重写该层逻辑")
        //假设当前仅属于本地
        val helper= DBHelper(context)
        var index = helper.getSettingsValue(DBHelper.SCHEDULE_TAG_INDEX,"-1").toIntOrNull()
        if (index == null){
            Log.d(TagConst.SQL,"DBHelper::tag读取读取出现异常")
            index = -1
        }
        index++

        val scheduleTag = ScheduleTag(-1,"#LOCAL.$index",title,"#LOCAL",icon,0,"",TimeUtil.toDateTimeString(Date()),TimeUtil.toDateTimeString(Date()))
        //请求数据库添加一条记录
        helper.setScheduleTagValue(scheduleTag)

        //向数据源添加一条记录
        tags.add(scheduleTag)

        return scheduleTag
    }

    companion object {
        const val TAG_COUNT_MAX = 15
    }
}