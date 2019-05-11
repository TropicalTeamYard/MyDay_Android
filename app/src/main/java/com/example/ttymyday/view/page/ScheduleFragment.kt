package com.example.ttymyday.view.page

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.util.TypedValue
import android.view.*
import com.example.ttymyday.R
import com.example.ttymyday.data.DataSource
import com.example.ttymyday.listener.ActionListener
import com.example.ttymyday.provider.ScheduleProvider
import com.example.ttymyday.util.TagConst
import com.example.ttymyday.listener.OnRItemClickListener
import com.example.ttymyday.view.adapter.ScheduleTagAdapter
import com.example.ttymyday.view.converter.ColorIconConverter
import com.example.ttymyday.view.helper.SwiftTouchHelperCallBack
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.sample_card_view.view.*
import java.util.*


class ScheduleFragment : Fragment(),View.OnClickListener, DialogInterface.OnDismissListener,SwiftTouchHelperCallBack.OnItemTouchCallBackListener<ScheduleTagAdapter.ViewHolder>{

    private var rItemListener = RItemListener()
    private var autoRItemListener = AutoRItemListener()
    private var sharedRItemListener = SharedRItemListener()
    private var sharedMoveItemListener = SharedMoveItemListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule,container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = recyclerView_schedule_tag
        recyclerView.setHasFixedSize(true)
        recyclerView.isNestedScrollingEnabled = false
        val adapter = ScheduleTagAdapter(DataSource.tags,ColorIconConverter())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context,1,false)
        //recyclerView.layoutManager = AutoFixLinearLayoutManager(context!!,1,false,6)
        adapter.setOnRItemClickListener(rItemListener)
        val touchHelper = ItemTouchHelper(SwiftTouchHelperCallBack<ScheduleTagAdapter.ViewHolder>(this))
        touchHelper.attachToRecyclerView(recyclerView)

        val autoRecyclerView = recyclerView_auto_tag
        val autoAdapter = ScheduleTagAdapter(DataSource.autoTags,ColorIconConverter())
        autoRecyclerView.adapter = autoAdapter
        autoRecyclerView.layoutManager = LinearLayoutManager(context,1,false)
        autoAdapter.setOnRItemClickListener(autoRItemListener)

        val sharedRecyclerView = recyclerView_shared_tag
        sharedRecyclerView.setHasFixedSize(true)
        sharedRecyclerView.isNestedScrollingEnabled = false
        val sharedAdapter = ScheduleTagAdapter(DataSource.sharedTags,ColorIconConverter())
        sharedRecyclerView.adapter = sharedAdapter
        sharedRecyclerView.layoutManager = LinearLayoutManager(context,1,false)
        sharedAdapter.setOnRItemClickListener(sharedRItemListener)
        val sharedTouchHelper = ItemTouchHelper(SwiftTouchHelperCallBack<ScheduleTagAdapter.ViewHolder>(sharedMoveItemListener))
        sharedTouchHelper.attachToRecyclerView(sharedRecyclerView)

        //DataSource.setOnInitCompletedListener(this)
        btn_schedule_add_book.setOnClickListener(this)

        updateItemState()
    }

    //region events
//    override fun action() {
//        Log.d(TagConst.UI,"f::通知adapter更新数据${DataSource.tags.count()}")
//        recyclerView_schedule_tag.adapter!!.notifyDataSetChanged()
//        updateItemState()
//    }

    override fun onDismiss(dialog: DialogInterface?) {
        Log.d(TagConst.UI,"dialog::正确关闭对话框")
        updateItemState()
        //由于绑定了arrayList,所以不需要通知。
        //recyclerView_schedule_tag.adapter!!.notifyDataSetChanged()
    }

    inner class RItemListener:OnRItemClickListener{
        override fun onItemClick(v: View?, position: Int) {
            Log.d(TagConst.UI,"item_click:: 你点击了第${position}个清单")
            //跳转界面
            val bundle = Bundle()
            bundle.putLong("ID",DataSource.tags[position].id)

            val intent= Intent(context,NormalScheduleActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)

        }
    }

    inner class AutoRItemListener:OnRItemClickListener{
        override fun onItemClick(v: View?, position: Int) {

            Log.d(TagConst.UI,"auto_item_click:: 你点击了第${position}个清单")
        }
    }

    inner  class SharedRItemListener:OnRItemClickListener{
        override fun onItemClick(v: View?, position: Int) {
            Log.d(TagConst.UI,"shared_item_click:: 你点击了第${position}个清单")
        }

    }

    override fun onClick(v: View?) {
        when(v){
            btn_schedule_add_book->{
                Log.d(TagConst.UI,"click:: 添加日程清单")
                val fragment = AddScheduleBookFragment()
                fragment.show(this.fragmentManager,"添加留言")
                fragment.setOnDismissListener(this)
            }
        }
    }

    inner class SharedMoveItemListener:SwiftTouchHelperCallBack.OnItemTouchCallBackListener<ScheduleTagAdapter.ViewHolder>{
        override fun onSwiped(adapterPosition: Int) {

        }

        override fun onMove(srcPosition: Int, targetPosition: Int): Boolean {
            val size = DataSource.sharedTags.size
            if (srcPosition in 0..(size - 1) && targetPosition in 0..(size -1)){
                Collections.swap(DataSource.sharedTags,srcPosition,targetPosition)
                recyclerView_shared_tag.adapter!!.notifyItemMoved(srcPosition,targetPosition)
                return true
            }

            return false
        }

        override fun onSelectedChanged(viewHolder: ScheduleTagAdapter.ViewHolder?, actionState: Int) {
            if(viewHolder!= null){
                Log.d(TagConst.UI,"touchMove::${viewHolder.itemView}")
                viewHolder.itemView.setBackgroundColor(Color.WHITE)
                viewHolder.itemView.translationZ = 30F
                //viewHolder.itemView.setBackgroundResource(R.drawable.)
            }
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: ScheduleTagAdapter.ViewHolder) {
            val typedValue = TypedValue()
            context!!.theme.resolveAttribute(R.attr.selectableItemBackground,typedValue,true)

            viewHolder.itemView.setBackgroundResource(typedValue.resourceId)
            viewHolder.itemView.translationZ = 0F
        }

    }

    override fun onSwiped(adapterPosition: Int) {

    }

    override fun onMove(srcPosition: Int, targetPosition: Int):Boolean {
        val size = DataSource.tags.size
        if (srcPosition in 0..(size - 1) && targetPosition in 0..(size -1)){
            Collections.swap(DataSource.tags,srcPosition,targetPosition)
            recyclerView_schedule_tag.adapter!!.notifyItemMoved(srcPosition,targetPosition)
            return true
        }

        return false
    }

    override fun onSelectedChanged(viewHolder: ScheduleTagAdapter.ViewHolder?, actionState: Int) {
        if(viewHolder!= null){
            Log.d(TagConst.UI,"touchMove::${viewHolder.itemView}")
            viewHolder.itemView.setBackgroundColor(Color.WHITE)
            viewHolder.itemView.translationZ = 30F
            //viewHolder.itemView.setBackgroundResource(R.drawable.)
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: ScheduleTagAdapter.ViewHolder) {
        val typedValue = TypedValue()
        context!!.theme.resolveAttribute(R.attr.selectableItemBackground,typedValue,true)

        viewHolder.itemView.setBackgroundResource(typedValue.resourceId)
        viewHolder.itemView.translationZ = 0F
    }

    //endregion

    private fun updateItemState(){
        val itemCount = DataSource.tags.size
        card_my_schedule.widget_card_view_tbx_display_title.text = "${context!!.getString(R.string.title_mySchedule)}(${itemCount})"
        if (itemCount >= ScheduleProvider.TAG_COUNT_MAX){
            btn_schedule_add_book.visibility = View.GONE
        } else {
            btn_schedule_add_book.visibility = View.VISIBLE
        }

        val sharedItemCount = DataSource.sharedTags.size
        card_shared_schedule.widget_card_view_tbx_display_title.text="${context!!.getString( R.string.title_sharedSchedule)}(${sharedItemCount})"

    }
}


