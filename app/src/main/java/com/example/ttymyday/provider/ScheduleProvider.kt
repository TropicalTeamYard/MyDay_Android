package com.example.ttymyday.provider

import android.content.Context
import android.util.Log
import com.example.ttymyday.data.DBHelper
import com.example.ttymyday.model.ScheduleTag
import com.example.ttymyday.util.TimeUtil
import java.util.*
import kotlin.collections.ArrayList

class ScheduleProvider(private var context: Context){

    /**
     * 创建一个日程标签，依赖settings::schedule_tag_index 和 user.username
     */
    fun createScheduleTag(icon:Int,title:String):ScheduleTag{
        //TODO("当联网时，重写该层逻辑")
        //假设当前仅属于本地
        val helper= DBHelper(context)
        var index = helper.getSettingsValue(DBHelper.SCHEDULE_TAG_INDEX,"-1").toIntOrNull()
        if (index == null){
            Log.d(DBHelper.TAG,"schedule_tag_index:: 读取出现异常")
            index = -1
        }
        index++

        val scheduleTag = ScheduleTag(-1,"#LOCAL.$index",title,"#LOCAL",icon,"",TimeUtil.toDateTimeString(Date()),TimeUtil.toDateTimeString(Date()))
        helper.setScheduleTagValue(scheduleTag)

        return scheduleTag
    }

    fun getScheduleTags():ArrayList<ScheduleTag>{
        val helper =DBHelper(context)
        return helper.getScheduleTagValues()
    }

    companion object {
        const val TAG_COUNT_MAX = 15
    }
}