package com.mtj.common.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import com.mtj.common.R

/**
 * 自定义RecyclerView横向滚动条
 */
class HorizontalScrollBarDecoration: RecyclerView.ItemDecoration() {

    val paint = Paint()
    val barHeight = SizeUtils.dp2px(4f) //滚动条的高度
    val scrollWidth = SizeUtils.dp2px(100f)//滚动条容器的宽度
    val indicatorWidth = SizeUtils.dp2px(20f)//滚动条宽度
    val paddingBottom = SizeUtils.dp2px(12f)//向下距离

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val barX = (parent.width / 2 - scrollWidth / 2).toFloat() //滚动条计算值X
        val barY = (parent.height - paddingBottom - barHeight).toFloat()//滚动条计算值Y

        val extent = parent.computeHorizontalScrollExtent()
        val range = parent.computeHorizontalScrollRange()
        val offset = parent.computeHorizontalScrollOffset()
        val maxEndX = (range - extent).toFloat()
        //可滑动
        if (maxEndX > 0) {
            paint.isAntiAlias = true
            paint.color = Color.parseColor("#FFDDDDDD")
            paint.strokeCap = Paint.Cap.ROUND
            paint.strokeWidth = barHeight.toFloat()
            c.drawLine(barX, barY, barX + scrollWidth.toFloat(), barY, paint)

            val proportion = offset / maxEndX
            val scrollableDistance = scrollWidth - indicatorWidth
            val offsetX = scrollableDistance * proportion
            paint.color = ColorUtils.getColor(R.color.colorPrimary)
            c.drawLine(barX + offsetX, barY, barX + indicatorWidth.toFloat() + offsetX, barY, paint)
        } else {
            paint.color = ColorUtils.getColor(R.color.transparent)
            c.drawLine(barX, barY, barX + scrollWidth.toFloat(), barY, paint)
        }
    }

}