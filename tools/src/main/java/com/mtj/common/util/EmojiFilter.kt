package com.mtj.common.util

import android.text.InputFilter
import android.text.Spanned
import com.mtj.common.toast.ToastUtils
import java.util.regex.Matcher
import java.util.regex.Pattern


object EmojiFilter {

    val inputEmojiFilters = arrayOf(
        object : InputFilter {
            var emoji: Pattern = Pattern.compile(
                "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE or Pattern.CASE_INSENSITIVE
            )

            override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
                val emojiMatcher: Matcher = emoji.matcher(source)
                if (emojiMatcher.find()) {
                    ToastUtils.showShort("不支持输入表情")
                    return ""
                }
                return null
            }
        },
        InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                val type = Character.getType(source[i])
                if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                    ToastUtils.showShort("不支持输入表情")
                    return@InputFilter ""
                }
            }
            null
        }
        /**这里限制输入的长度为200 */
        //LengthFilter(200)
    )
}