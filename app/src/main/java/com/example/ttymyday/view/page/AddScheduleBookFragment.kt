package com.example.ttymyday.view.page

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.ttymyday.R
import com.example.ttymyday.view.adapter.IconAdapter
import com.example.ttymyday.view.converter.ColorIconConverter
import kotlinx.android.synthetic.main.add_schedule_book_fragment.*
import java.util.*

class AddScheduleBookFragment : DialogFragment(),View.OnClickListener ,IconAdapter.OnItemClickListener{
    override fun onItemClick(v: View?, position: Int) {
        Log.d(TAG,"你点击了第 ${position}个图标")
    }

    override fun onClick(v: View?) {
        when(v){
            btn_cancel_add_schedule_book->{
                dialog.cancel()
            }
        }
    }


    override fun onStart() {
        super.onStart()
        //设置对话框的呈现大小和位置
        val dm:DisplayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)
        dialog.window!!.setLayout(dm.widthPixels,dialog.window!!.attributes.height)
        dialog.window!!.setGravity(Gravity.BOTTOM)
        //设置对话框不可取消
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_schedule_book_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        btn_cancel_add_schedule_book.setOnClickListener(this)
        btn_ok_add_schedule_book.setOnClickListener(this)
    }

    private fun init(){
        val layoutManager:RecyclerView.LayoutManager = GridLayoutManager(context,6)
        recyclerView_icon_add_schedule_book.layoutManager = layoutManager
        recyclerView_icon_add_schedule_book.adapter = IconAdapter(ColorIconConverter(),this)

    }

    companion object {
        const val TAG = "ADSC"
    }
}
