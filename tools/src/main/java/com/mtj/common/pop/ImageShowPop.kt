package com.mtj.common.pop

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mtj.common.R
import com.mtj.common.glide.GlideUtil.loadWHImage
import com.github.chrisbanes.photoview.PhotoView
import razerdp.basepopup.BasePopupWindow

/**
 * @author  孟腾蛟
 * @Description
 * @date 2021/3/31
 */
class ImageShowPop(context: Context, imageList: MutableList<String>, curPosition: Int) : BasePopupWindow(context) {
    val viewPager2: ViewPager2 = findViewById(R.id.viewPager)
    val tvNumber: TextView = findViewById(R.id.tvNumber)

    init {
        val adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_image_show, imageList) {
            override fun convert(holder: BaseViewHolder, item: String) {
                loadWHImage(context, item, holder.getView<PhotoView>(R.id.photoView))
            }
        }
        tvNumber.text = "${curPosition + 1}/${imageList.size}"
        adapter.addChildClickViewIds(R.id.photoView)
        adapter.setOnItemClickListener { adapter, view, position -> dismiss() }
        adapter.setOnItemChildClickListener { adapter, view, position -> dismiss() }
        viewPager2.adapter = adapter
        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tvNumber.text = "${position + 1}/${imageList.size}"
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
        viewPager2.setCurrentItem(curPosition, false)
        viewPager2.setOnClickListener { dismiss() }
    }

    override fun onCreateContentView(): View = createPopupById(R.layout.pop_image_show)

}