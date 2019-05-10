package com.example.ttymyday.model

/**
 * 日程标签
 * @param name 标签的名称
 * @param title 标题
 * @param icon 图标
 * @param alarm_count 以重定义为未完成事项的个数
 * @param users 用户与权限列表
 * @param create_time 创建时间
 * @param update_time 更新时间
 * @param extra_tag 额外标签，若为clockIn，则为打卡，若为memory，则为生日与纪念日，若为important，则为重要，若为table，则为课程表
 * @param isVisible 是否是可见的
 * @param operatingType 操作类型 0:普通模式，1:切换可视性模式
 */
data class ScheduleTag(var id:Long,
                       var name:String?,
                       var title:String?,
                       var owner:String?,
                       var icon:Int?,
                       var alarm_count:Int = 0,
                       var users:String? = null,
                       var create_time:String? = null,
                       var update_time:String? = null,
                       var extra_tag:String?=null,
                       var isVisible:Boolean= true,
                       var operatingType:Int = 0)