package com.mtj.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.util.AttributeSet
import android.widget.TextView

/**
 * @author mtj
 * @time 2018/11/19 2018 11
 * @des
 */
@SuppressLint("AppCompatCustomView")
class TableTitleTextView : TextView {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    var rightImgId = 0
        private set

    //设置排序表头图标
    fun setRatioRightImg(img_Id: Int) {
        try {
            rightImgId = img_Id
            val str = text.toString()
            val span = SpannableString(str)
            val image = ImageSpan(context, rightImgId, DynamicDrawableSpan.ALIGN_BASELINE)
            span.setSpan(image, str.length - 1, str.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            text = span
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}