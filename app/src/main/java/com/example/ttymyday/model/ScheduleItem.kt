package com.example.ttymyday.model

data class ScheduleItem (
    var tag:String,
    var title:String,
    var sourceId:Int = 0,
    var create_time:String? = null,
    var end_date:String? = null,
    var start_time:String? = null,
    var during:String? =null,
    var repeat:String? = null,
    var alarm:String? =null,
    var location:String? = null,
    var star:Boolean = false,
    var remark:String? =null,
    var completed:String?=null,
    var steps:String?=null,
    var update_time:String? =null,
    var extra_data:String?= null
)