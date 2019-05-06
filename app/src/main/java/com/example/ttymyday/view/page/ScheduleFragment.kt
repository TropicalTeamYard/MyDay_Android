package com.example.ttymyday.view.page

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Dialog
import android.app.SearchManager
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Point
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.util.Log
import android.view.*
import com.example.ttymyday.R
import com.example.ttymyday.data.DBHelper
import com.example.ttymyday.data.DataSource
import com.example.ttymyday.model.ScheduleTag
import com.example.ttymyday.provider.ScheduleProvider
import com.example.ttymyday.view.adapter.OnRItemClickListener
import com.example.ttymyday.view.adapter.ScheduleTagAdapter
import com.example.ttymyday.view.converter.ColorIconConverter
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.sample_card_view.view.*
import java.lang.Math.abs


class ScheduleFragment : Fragment(),View.OnClickListener,OnRItemClickListener,DialogInterface.OnDismissListener{
    override fun onDismiss(dialog: DialogInterface?) {
        Log.d(TAG,"dialog::正确关闭对话框")
        updateItemState()
        //由于绑定了arrayList,所以不需要通知。
        //recyclerView_schedule_tag.adapter!!.notifyDataSetChanged()
    }

    override fun onItemClick(v: View?, position: Int) {
        Log.d(TAG,"item_click:: 你点击了第${position}个清单")
    }

    override fun onClick(v: View?) {
        when(v){
            btn_schedule_add_book->{
                Log.d(TAG,"click:: 添加日程清单")
                val fragment = AddScheduleBookFragment()
                fragment.show(this.fragmentManager,"添加留言")
                fragment.setOnDismissListener(this)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule,container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = recyclerView_schedule_tag

        val provider:ScheduleProvider = ScheduleProvider(context!!,DataSource.tags)
        if (!DataSource.isTagsLoaded){
            DataSource.isTagsLoaded = true
            provider.initialize()
        }

        val adapter = ScheduleTagAdapter(DataSource.tags,ColorIconConverter())

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context,1,false)

        btn_schedule_add_book.setOnClickListener(this)
        adapter.setOnRItemClickListener(this)

        updateItemState()
    }


    private fun updateItemState(){
        val itemCount = DataSource.tags.size
        card_my_schedule.widget_card_view_tbx_display_title.text = "${context!!.getString(R.string.title_myschedule)}(${itemCount})"
        if (itemCount >= ScheduleProvider.TAG_COUNT_MAX){
            btn_schedule_add_book.visibility = View.GONE
        } else {
            btn_schedule_add_book.visibility = View.VISIBLE
        }

    }

    companion object {
        const val TAG = "ScheduleFragment"
        const val MAX_TOUCH_DISTANCE = 80
    }
}


