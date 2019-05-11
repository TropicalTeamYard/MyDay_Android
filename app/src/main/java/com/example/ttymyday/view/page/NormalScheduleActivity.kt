package com.example.ttymyday.view.page

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.ttymyday.R
import com.example.ttymyday.data.DataSource
import com.example.ttymyday.listener.OnRItemClickListener
import com.example.ttymyday.model.ScheduleTag
import com.example.ttymyday.util.TagConst
import com.example.ttymyday.view.adapter.ScheduleItemAdapter
import com.example.ttymyday.view.converter.ColorIconConverter
import kotlinx.android.synthetic.main.activity_normal_schedule.*

class NormalScheduleActivity : AppCompatActivity(), View.OnClickListener,OnRItemClickListener {
    override fun onItemClick(v: View?, position: Int) {
        Log.d(TagConst.UI,"item:: 点击了第${position}项")
    }

    override fun onClick(v: View?) {
        when(v){
            img_back_normal_schedule->{
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_schedule)

        if (intent.extras!=null){
            val bundle:Bundle = intent.extras!!
            val id = bundle.getLong("ID")
            val tag: ScheduleTag = DataSource.tags.find { scheduleTag -> scheduleTag.id == id  }!!

            //初始化
            img_icon_normal.setImageResource(ColorIconConverter().getIconRes(tag.icon!!))
            tbx_title_normal.text = tag.title
            //TODO("获取用户人数")
            val userCount = 0
            if (userCount == 0){
                tbx_user_normal.visibility = View.GONE
            }else{
                tbx_user_normal.text = userCount.toString()
                tbx_user_normal.visibility = View.VISIBLE
            }
        } else{
            Log.d(TagConst.UI,"scheduleActivity::不存在bundle")
        }

        //TODO("重写读取数据集的逻辑")
        val adapter = ScheduleItemAdapter(DataSource.scheduleMap["item1"]!!)

        recyclerView_item.adapter = adapter;
        recyclerView_item.layoutManager = LinearLayoutManager(this,1,false)

        adapter.setOnRItemClickListener(this)

        img_back_normal_schedule.setOnClickListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

}
