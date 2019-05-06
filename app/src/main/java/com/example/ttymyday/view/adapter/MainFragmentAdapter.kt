package com.example.ttymyday.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.ttymyday.view.page.*

class MainFragmentAdapter(fm:FragmentManager):FragmentPagerAdapter(fm) {
    private var pages : ArrayList<Fragment> = ArrayList()
    init{
        pages.add(HomeFragment())
        pages.add(ScheduleFragment())
        pages.add(TeamFragment())
        pages.add(NewsFragment())
        pages.add(MyFragment())
    }

    override fun getItem(p0: Int): Fragment {
        return pages[p0]
    }

    override fun getCount(): Int {
        return pages.size
    }

}