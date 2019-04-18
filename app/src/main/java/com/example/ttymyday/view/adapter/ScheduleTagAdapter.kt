package com.example.ttymyday.view.adapter

import android.content.Context
import android.graphics.drawable.Icon
import android.opengl.Visibility
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.ttymyday.R
import com.example.ttymyday.model.ScheduleTag
import com.example.ttymyday.provider.ScheduleProvider
import com.example.ttymyday.view.converter.IconConverter
import kotlinx.android.synthetic.main.sample_schedule_tag_view.view.*

class ScheduleTagAdapter(var provider:ScheduleProvider,var converter:IconConverter):RecyclerView.Adapter<ScheduleTagAdapter.ViewHolder>(){
    lateinit var tags:ArrayList<ScheduleTag>
    var mListener:OnRItemClickListener? = null


    lateinit var context: Context

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        context = p0.context
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.sample_schedule_tag_view,p0,false),mListener)
    }

    override fun getItemCount(): Int {
        tags = provider.getScheduleTags()
        return tags.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val tag = tags[p1]
        p0.imgIcon.setImageResource(converter.getIconRes(tag.icon!!))
        p0.tbxTitle.text = tag.title
        if (tag.alarm_count > 0){
            p0.imgAlarm.visibility = View.VISIBLE
            p0.tbxAlarmCount.text = if(tag.alarm_count > 99) "99+" else tag.alarm_count.toString()
            p0.tbxAlarmCount.visibility = View.VISIBLE
        } else{
            p0.imgAlarm.visibility = View.INVISIBLE
            p0.tbxAlarmCount.visibility = View.INVISIBLE
        }
    }

    fun setOnRItemClickListener(listener: OnRItemClickListener){
        mListener = listener;
    }

    class ViewHolder(v: View,var listener:OnRItemClickListener?):RecyclerView.ViewHolder(v),View.OnClickListener{
        override fun onClick(v: View?) {
            listener?.onItemClick(v,layoutPosition)
        }

        var imgIcon:ImageView = v.img_icon_schedule_tag
        var tbxTitle:TextView = v.tbx_title_schedule_tag
        var imgAlarm:ImageView = v.img_alarm_schedule_tag
        var tbxAlarmCount:TextView = v.tbx_alarm_count_schedule_tag
    }
}