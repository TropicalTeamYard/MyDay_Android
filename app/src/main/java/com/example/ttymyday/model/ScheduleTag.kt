package com.example.ttymyday.model

data class ScheduleTag(var id:Long,
                       var name:String?,
                       var title:String?,
                       var owner:String?,
                       var icon:Int?,
                       var alarm_count:Int = 0,
                       var users:String? = null,
                       var create_time:String? = null,
                       var update_time:String? = null)