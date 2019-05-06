package com.example.ttymyday.view.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class MViewPager : ViewPager {
    constructor(context:Context):super(context)
    constructor(context:Context,attributeSet: AttributeSet):super(context,attributeSet)

    var isScroll:Boolean = false

    override fun onTouchEvent(event: MotionEvent?): Boolean {
       return this.isScroll && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return this.isScroll && super.onInterceptTouchEvent(event)
    }

}