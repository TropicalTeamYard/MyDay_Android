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
        updateItemState()
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

//    var startPoint: PointF?= null
//
//    @SuppressLint("ClickableViewAccessibility")
//    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//        //Log.d(TAG,"onTouch:: ${v.toString()}")
//        //Log.d(TAG,"action:: ${event?.action}")
//        if (event?.action == MotionEvent.ACTION_DOWN ||
//            event?.action == MotionEvent.ACTION_UP ||
//            event?.action == MotionEvent.ACTION_MOVE ||
//            event?.action == MotionEvent.ACTION_CANCEL
//        )
//            if (event.action == MotionEvent.ACTION_DOWN) {
//                Log.d(TAG, "onTouch:: Pressed")
//                startPoint = PointF(event.x, event.y)
//                handleTouchEvent(v, true)
//            } else if (event.action == MotionEvent.ACTION_MOVE) {
//                //Log.d(TAG,"onTouch:: Move")
//                if (startPoint != null) {
//                    //val newPoint:PointF = PointF(event.x,event.y)
//                    if (abs(event.x - startPoint!!.x) > MAX_TOUCH_DISTANCE || abs(event.y - startPoint!!.y) > MAX_TOUCH_DISTANCE) {
//                        startPoint = null
//                        handleTouchEvent(v, false)
//                    }
//                }
//            } else {
//                Log.d(TAG, "onTouch:: Released")
//
//                if (startPoint != null) {
//                    startPoint = null
//                    handleTouchEvent(v, false, true)
//                } else {
//                    handleTouchEvent(v, false)
//                }
//                //test_schedule_item1.setBackgroundColor(Color.TRANSPARENT)
//            }
//
//        return false
//    }

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
        val adapter = ScheduleTagAdapter(ScheduleProvider(context!!),ColorIconConverter())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context,1,false)

        btn_schedule_add_book.setOnClickListener(this)
        adapter.setOnRItemClickListener(this)

        updateItemState()
    }



    private fun updateItemState(){
        val itemCount = recyclerView_schedule_tag.adapter!!.itemCount
        card_my_schedule.widget_card_view_tbx_display_title.text = "我的日程清单(${itemCount})"
        if (itemCount >= ScheduleProvider.TAG_COUNT_MAX){
            btn_schedule_add_book.visibility = View.GONE
        } else {
            btn_schedule_add_book.visibility = View.VISIBLE
        }

    }
//    /**
//     * 处理Touch事件
//     * @param v Touch的视图
//     * @param show 是否正处于点击的范围内
//     * @param click 是否相应鼠标点击的事件
//     */
//    private fun handleTouchEvent(v:View?,show:Boolean,click:Boolean = false){
//        when(v){
////            test_schedule_item1->{
////                if (show){
////                    test_schedule_item1.setBackgroundColor(context!!.getColor(R.color.colorHoverGray))
////                } else{
////                    test_schedule_item1.setBackgroundColor(Color.TRANSPARENT)
////                }
////                if (click){
////                    Log.d(TAG,"click:: ${v.toString()}")
////                }
////            }
//            btn_schedule_add_book->{
//                if (click){
//                    Log.d(TAG,"click:: 添加日程清单")
//                    val fragment = AddScheduleBookFragment()
//                    fragment.show(this.fragmentManager,"添加留言")
//                }
//            }
//        }
//    }

    companion object {
        const val TAG = "ScheduleFragment"
        const val MAX_TOUCH_DISTANCE = 80
    }
}


