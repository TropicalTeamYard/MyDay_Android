package com.example.ttymyday.view.page

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ttymyday.R
import com.example.ttymyday.data.DBHelper
import com.example.ttymyday.data.DataSource
import kotlinx.android.synthetic.main.fragment_schedule.*


class ScheduleFragment : Fragment() {

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
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val TAG = "ScheduleFragment"
    }
}
