package com.example.ttymyday.view.helper

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup

class AutoFixLinearLayoutManager : LinearLayoutManager {
    var fixedItemCount = 8

    constructor(context: Context) : super(context) {}
    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout) {}
    constructor(context:Context,orientation: Int,reverseLayout: Boolean,fixedItemCount:Int):this(context,orientation,reverseLayout){
        this.fixedItemCount = fixedItemCount
    }

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        var count = state.itemCount

        if(count >0){
            if (count > fixedItemCount)
                count = fixedItemCount
            var realHeight =0
            var realWidth = 0
            for (i in 0 until  count){
                val view = recycler.getViewForPosition(0)
                measureChild(view,widthSpec,heightSpec)
                val measuredWidth = View.MeasureSpec.getSize(widthSpec)
                val measuredHeight = view.measuredHeight
                realWidth = if(realWidth > measuredWidth) realWidth else measuredWidth
                realHeight+=measuredHeight
            }
            setMeasuredDimension(realWidth,realHeight)
        }else {
            super.onMeasure(recycler, state, widthSpec, heightSpec)
        }
    }
}