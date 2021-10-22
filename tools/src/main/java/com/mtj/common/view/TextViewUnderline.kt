package com.mtj.common.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/5/16
 * 描述:
 */
class TextViewUnderline @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    private var underlineColor = 0x00000000
    var isChecked = false
    private val mPaint = Paint()

    fun setUnderlineColor(underlineColor: Int) {
        this.underlineColor = underlineColor
        invalidate()
    }

    fun getUnderlineColor(): Int {
        return underlineColor
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //getPaint()
        if (isChecked) {
            mPaint.color = underlineColor
        } else {
            mPaint.color = 0x00000000
        }
        val string = text.toString()
        val width = paint.measureText(string)
        canvas.drawRect((getWidth() - width) / 2, (height - 5).toFloat(), width + (getWidth() - width) / 2, height.toFloat(), mPaint)
    }
}