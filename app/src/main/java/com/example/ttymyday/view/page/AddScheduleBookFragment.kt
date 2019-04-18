package com.example.ttymyday.view.page

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.ttymyday.R
import com.example.ttymyday.provider.ScheduleProvider
import com.example.ttymyday.util.NameLengthFilter
import com.example.ttymyday.view.adapter.IconAdapter
import com.example.ttymyday.view.adapter.OnRItemClickListener
import com.example.ttymyday.view.converter.ColorIconConverter
import kotlinx.android.synthetic.main.add_schedule_book_fragment.*

class AddScheduleBookFragment : DialogFragment(),View.OnClickListener ,OnRItemClickListener,TextWatcher{

    private var mListener:DialogInterface.OnDismissListener? = null

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    @SuppressLint("ResourceAsColor")
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (count == 0){
            btn_ok_add_schedule_book.isEnabled = false
            btn_ok_add_schedule_book.setTextColor(context!!.getColor(R.color.colorNormal))
        } else {
            btn_ok_add_schedule_book.isEnabled = true
            btn_ok_add_schedule_book.setTextColor(context!!.getColor(R.color.colorPrimary))
        }

    }

    private var selectedIndex:Int = 0

    override fun onItemClick(v: View?, position: Int) {
        Log.d(TAG,"你点击了第 ${position}个图标")
        selectedIndex = position
        img_icon_add_schedule_book.setImageResource(ColorIconConverter().getIconRes(selectedIndex))
    }

    override fun onClick(v: View?) {
        when(v){
            btn_cancel_add_schedule_book->{
                dialog.cancel()
            }
            btn_ok_add_schedule_book->{
                val provider = ScheduleProvider(context!!)
                val tag = provider.createScheduleTag(selectedIndex,edt_title_add_schedule_book.text.toString())

                Log.d(TAG,"添加了一条日程清单: $tag")
                dialog.dismiss()
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
        edt_title_add_schedule_book.filters = arrayOf(NameLengthFilter(15))
    }

    fun setOnDismissListener(listener:DialogInterface.OnDismissListener){
        mListener = listener
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        mListener?.onDismiss(dialog)
    }

    private fun init(){
        val layoutManager:RecyclerView.LayoutManager = GridLayoutManager(context,6)
        recyclerView_icon_add_schedule_book.layoutManager = layoutManager
        val adapter = IconAdapter(ColorIconConverter())
        adapter.setOnRItemClickListener(this)
        recyclerView_icon_add_schedule_book.adapter = adapter

        //btn_ok_add_schedule_book.setOnFocusChangeListener(this)
        edt_title_add_schedule_book.addTextChangedListener(this)
    }

    companion object {
        const val TAG = "ADSC"
    }
}
