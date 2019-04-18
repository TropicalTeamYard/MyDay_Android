package com.example.ttymyday.model

data class ScheduleTag(var id:Int,
                       var name:String?,
                       var title:String?,
                       var owner:String?,
                       var icon:Int?,
                       var users:String?,
                       var create_time:String?,
                       var update_time:String?,
                       var alarm_count:Int = 0)