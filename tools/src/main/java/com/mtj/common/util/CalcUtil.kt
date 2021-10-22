package com.mtj.common.util

import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import java.math.BigDecimal

/**
 * 小数保留两位
 */
fun Any?.format2Point(): String {
    val d = try {
        when (this) {
            is String -> toDouble()
            is Float -> toDouble()
            is Double -> this
            null -> this
            else -> 0.0
        }
    } catch (e: Exception) {
        return toString()
    }
    return String.format("%.2f", d)
}

//小数后两位字体变小
fun changTVSize(value: CharSequence, size: Float = 0.6f): SpannableString? {
    val spannableString = SpannableString(value)
    if (value.contains(".")) {
        spannableString.setSpan(RelativeSizeSpan(size), value.indexOf("."), value.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return spannableString
}

object CalcUtil {

    /**
     * 加法运算
     * @param m1
     * @param m2
     * @return
     */
    fun add(m1: Double, m2: Double): Double = BigDecimal.valueOf(m1).add(BigDecimal.valueOf(m2)).toDouble()

    /**
     * 减法运算
     * @param m1
     * @param m2
     * @return
     */
    fun sub(m1: Double, m2: Double): Double = BigDecimal.valueOf(m1).subtract(BigDecimal.valueOf(m2)).toDouble()

    /**
     * 乘法运算
     * @param m1
     * @param m2
     * @return
     */
    fun mul(m1: Double, m2: Double): Double = BigDecimal.valueOf(m1).multiply(BigDecimal.valueOf(m2)).toDouble()


    /**
     * 除法运算
     * @param   m1
     * @param   m2
     * @param   scale
     * @return
     */
    fun div(m1: Double, m2: Double, scale: Int): Double {
        require(scale >= 0) { "Parameter error" }
        return BigDecimal.valueOf(m1).divide(BigDecimal.valueOf(m2), scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

}