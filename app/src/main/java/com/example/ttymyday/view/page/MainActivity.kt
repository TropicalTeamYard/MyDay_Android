package com.example.ttymyday.view.page

import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.example.ttymyday.R
import com.example.ttymyday.R.*
import com.example.ttymyday.data.DataSource
import com.example.ttymyday.data.UserUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() ,BottomNavigationView.OnNavigationItemSelectedListener {

    //region Fields And Function With navigation
    private var fragmentindex:Int = 0


    fun setDefaultFragment(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contentView,HomeFragment()).commit()
    }

    fun switchFragment(index:Int)
    {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contentView,when(index){
            0->HomeFragment()
            1->ScheduleFragment()
            2->TeamFragment()
            3->NewsFragment()
            4->MyFragment()
            else->Fragment()
        }).commit()
    }


    //endregion

    //region Events
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        //initFragment()
        setDefaultFragment()
        navigation.setOnNavigationItemSelectedListener(this)

        //kotlin.run { DataSource.loadScheduleTag(this) }
        Log.d(TAG,"加载日程标签信息.")
    }


    override fun onNavigationItemSelected(@NonNull menuItem: MenuItem): Boolean {
        Log.d(TAG,"点击item")
        when (menuItem.itemId){
            id.navigation_home ->{
                switchFragment(0)
                return true
            }
            id.navigation_schedule -> {
                switchFragment(1)
                return true
            }
            id.navigation_team ->{
                switchFragment(2)
                return true
            }
            id.navigation_news ->{
                switchFragment(3)
                return true
            }
            id.navigation_my -> {
                switchFragment(4)
                return true
            }
            else ->{return false}
        }
    }
    //endregion

    companion object{
        val TAG:String = "MainActivity"
    }
}
