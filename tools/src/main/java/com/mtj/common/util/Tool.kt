package com.mtj.common.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.DynamicDrawableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.widget.TextView
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder


/**
 * @author  孟腾蛟
 * @Description
 * @date 2021/3/18
 */


/**
 * 验证手机和电话号码的格式
 *
 * @param str 校验手机字符串
 * @return 返回true, 否则为false
 */
fun isPhoneNum(str: String?): Boolean {
    if (str == null || "" == str) return false
    return if (str.length != 11) false else str.startsWith("1")
}


fun openBrowser(context: Context, url: String?) {
    val intent = Intent()
    intent.action = Intent.ACTION_VIEW
    intent.data = Uri.parse(url)
    context.startActivity(intent)
}

val singleGson by lazy {
    Gson()
}

//过滤掉value 为null或者""的字段
fun getSkipParamGson(vararg keys: String): Gson {
    return GsonBuilder().setExclusionStrategies(
        object : ExclusionStrategy {
            override fun shouldSkipField(f: FieldAttributes): Boolean {
                //过滤掉字段名包含"id","address"的字段
                var has = false
                keys.iterator().forEach {
                    if (f.name == it) has = true
                    return@forEach
                }
                return has
            }

            override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                // 过滤掉 类名包含 Bean的类
                return false
            }
        }).create()
}

/**
 * 设置颜色富文本
 */
fun getSpannableToColor(str: String, startIndex: Int, endIndex: Int, color: Int): SpannableString {
    val spannableString = SpannableString(str)
    spannableString.setSpan(ForegroundColorSpan(color), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
    return spannableString
}

/**
 * 设置图片在文字后边
 */
fun setEndImageSpan(context: Context, tv: TextView, str: String, resId: Int) {
    val text = "$str "
    val span = SpannableString(text)
    val image = ImageSpan(context, resId, DynamicDrawableSpan.ALIGN_BASELINE)
    span.setSpan(image, text.length - 1, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    tv.text = span
}