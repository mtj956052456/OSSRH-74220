package com.mtj.common.pop

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mtj.common.R
import razerdp.basepopup.BasePopupWindow
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig
import java.util.*

/**
 * 创建者 孟腾蛟
 * 创建时间 2019/03/21
 * 描述
 */
class SlideBottomListPop : BasePopupWindow {
    constructor(context: Context?, size: Int, addFootView: Boolean) : super(context) {
        this.addFootView = addFootView
        initView(size)
    }

    constructor(context: Context?, size: Int) : super(context) {
        initView(size)
    }

    private var mAdapter: SlideBottomListAdapter? = null
    private val mList: MutableList<String> = ArrayList()
    private var addFootView = false
    private fun initView(size: Int) {
        popupGravity = Gravity.BOTTOM
        val llParent = findViewById<LinearLayout>(R.id.llParent)
        val mRecyclerView = findViewById<RecyclerView>(R.id.recycler_slide_bottom_list)
        mAdapter = SlideBottomListAdapter(R.layout.pop_slide_bottom_list_item, mList)
        mAdapter!!.setOnItemChildClickListener { adapter, view, position -> mOnItemClickListener!!.callBack(mList[position]) }
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        val l = llParent.layoutParams
        var height = 0
        height += ConvertUtils.dp2px(5f)//头
        if (addFootView) height += ConvertUtils.dp2px(30f) //底部
        val itemHeight = ConvertUtils.dp2px(25f)
        val defaultHeight = ConvertUtils.dp2px(120f)
        height += if (size * itemHeight < defaultHeight)
            size * itemHeight   //默认高度
        else
            defaultHeight  //限高
        l.height = height
        l.width = ViewGroup.LayoutParams.MATCH_PARENT
        llParent.layoutParams = l
        if (addFootView) addCancelView()
    }

    fun addCancelView() {
        findViewById<View>(R.id.llCancel).visibility = View.VISIBLE
        findViewById<View>(R.id.tvCancel).setOnClickListener { dismiss() }
    }

    override fun onCreateShowAnimation(): Animation {
        return AnimationHelper.asAnimation()
            .withTranslation(TranslationConfig.FROM_BOTTOM)
            .toShow()
    }

    override fun onCreateDismissAnimation(): Animation {
        return AnimationHelper.asAnimation()
            .withTranslation(TranslationConfig.TO_BOTTOM)
            .toDismiss()
    }

    override fun onCreateContentView(): View {
        return createPopupById(R.layout.pop_slide_bottom_list)
    }

    fun setList(list: List<String>) {
        mList.clear()
        mList.addAll(list)
        mAdapter!!.notifyDataSetChanged()
    }

    fun setList(vararg value: String?) {
        mList.clear()
        mList.addAll(Arrays.asList<String>(*value))
        mAdapter!!.notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun callBack(value: String)
    }
    private var mOnItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    inner class SlideBottomListAdapter(layoutResId: Int, data: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
        override fun convert(holder: BaseViewHolder, item: String) {
            holder.setGone(R.id.view_line, mList.size - 1 == holder.adapterPosition)
            holder.setText(R.id.tv_name_item, item)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
            addChildClickViewIds(R.id.tv_name_item)
            return super.onCreateViewHolder(parent, viewType)
        }
    }
}