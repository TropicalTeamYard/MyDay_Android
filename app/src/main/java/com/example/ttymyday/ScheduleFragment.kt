package com.example.ttymyday

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ttymyday.data.DBHelper
import com.example.ttymyday.data.DataSource
import kotlinx.android.synthetic.main.fragment_schedule.*


class ScheduleFragment : Fragment(),View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            btn_test_addc->{
                DataSource.createScheduleTag(context!!,edt_test_c.text.toString())
                Log.d(TAG,"click the btn_test_addc")
            }
            btn_test_printc->{
                DBHelper.getInstance(context!!).printScheduleTag()
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
        return inflater.inflate(R.layout.fragment_schedule,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        btn_test_addc.setOnClickListener(this)
        btn_test_printc.setOnClickListener(this)
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val TAG = "ScheduleFragment"
    }
}
