package com.example.ttymyday.view.helper

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import java.text.FieldPosition

class SwiftTouchHelperCallBack<TV:RecyclerView.ViewHolder>(var listener:OnItemTouchCallBackListener<TV>):ItemTouchHelper.Callback(){
    var isEnableSwipe = false;
    var isEnableDrag = true;

    override fun getMovementFlags(p0: RecyclerView, p1: RecyclerView.ViewHolder): Int {
        if (p0.layoutManager is LinearLayoutManager ){
            val orientation = (p0.layoutManager as LinearLayoutManager).orientation
            var dragFlag = 0
            var swipeFlag = 0

            if (orientation == LinearLayoutManager.HORIZONTAL){
                swipeFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                dragFlag = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            } else if (orientation == LinearLayoutManager.VERTICAL){
                dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                swipeFlag = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            }
            return makeMovementFlags(dragFlag,swipeFlag)
        }

        return 0
    }

    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
        listener?.onMove(p1.adapterPosition,p2.adapterPosition)
        return false
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
        listener?.onSwiped(p0.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        listener?.onSelectedChanged(viewHolder as TV?,actionState)
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        listener?.clearView(recyclerView,viewHolder as TV)
        super.clearView(recyclerView, viewHolder)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return isEnableDrag
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return isEnableSwipe
    }

    interface  OnItemTouchCallBackListener<TV:RecyclerView.ViewHolder>{
        /**
         * 当某个Item被滑动删除时回调
         */
        fun onSwiped(adapterPosition:Int)

        /**
         * 当两个Item位置互换的时候被回调(拖拽)
         */
        fun onMove(srcPosition: Int,targetPosition:Int):Boolean;

        fun onSelectedChanged(viewHolder:TV?,actionState:Int);

        fun clearView(recyclerView: RecyclerView,viewHolder:TV);
    }
}

