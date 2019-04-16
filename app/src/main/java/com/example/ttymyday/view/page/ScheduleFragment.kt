package com.example.ttymyday.view.page

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import com.example.ttymyday.R
import com.example.ttymyday.data.DBHelper
import com.example.ttymyday.data.DataSource
import kotlinx.android.synthetic.main.fragment_schedule.*


class ScheduleFragment : Fragment(),View.OnTouchListener {
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        when(v){
            test_schedule_item1->{
                if (event?.buttonState == MotionEvent.ACTION_BUTTON_PRESS ){
                    test_schedule_item1.setBackgroundColor(Color.GRAY)
                }
                else{
                    test_schedule_item1.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //btn_test_addc.setOnClickListener(this)
        //btn_test_printc.setOnClickListener(this)
        test_schedule_item1.setOnTouchListener(this)
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val TAG = "ScheduleFragment"
    }
}
