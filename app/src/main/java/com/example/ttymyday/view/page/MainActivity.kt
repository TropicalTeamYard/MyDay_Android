package com.example.ttymyday.view.page

import android.content.Intent
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.provider.ContactsContract
import android.support.annotation.NonNull
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.example.ttymyday.R
import com.example.ttymyday.R.*
import com.example.ttymyday.data.DataSource
import com.example.ttymyday.data.UserUtil
import com.example.ttymyday.listener.ActionListener
import com.example.ttymyday.provider.ScheduleProvider
import com.example.ttymyday.util.TagConst
import com.example.ttymyday.view.adapter.MainFragmentAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() ,BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private lateinit var adapter:MainFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        adapter = MainFragmentAdapter(supportFragmentManager)
        contentView.adapter = adapter
        contentView.isScroll = true

        contentView.addOnPageChangeListener(this)
        navigation.setOnNavigationItemSelectedListener(this)

        DataSource.initAsync(this)
    }

    //region events
    override fun onNavigationItemSelected(@NonNull menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.navigation_home->{
                contentView.currentItem = 0
                true
            }
            R.id.navigation_schedule->{
                contentView.currentItem = 1
                true
            }
            R.id.navigation_team->{
                contentView.currentItem = 2
                true
            }
            R.id.navigation_news->{
                contentView.currentItem = 3
                true
            }
            R.id.navigation_my ->{
                contentView.currentItem = 4
                true
            }
            else -> false
        }
    }

    override fun onPageScrollStateChanged(p0: Int) {

    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
    }

    override fun onPageSelected(p0: Int) {
        Log.d(TagConst.UI,"main::页面第${p0}项被选中")

        tbx_main_title.text = when(p0){
            0-> getString(R.string.title_home)
            1->getString(R.string.title_schedule)
            2->getString(R.string.title_team)
            3-> getString(R.string.title_news)
            4->getString(R.string.title_my)
            else->"unset"
        }

        navigation.selectedItemId = when(p0){
            0-> R.id.navigation_home
            1->R.id.navigation_schedule
            2->R.id.navigation_team
            3->R.id.navigation_news
            4->R.id.navigation_my
            else -> -1
        }

    }
    //endregion


}
