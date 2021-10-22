package com.mtj.common.util

import com.tencent.mmkv.MMKV

/**
 * 共享参数类
 */
object MMVKUtils {

    const val TOKEN = "TOKEN"

    val mmvkUtils by lazy {
        MMKV.mmkvWithID("APP")
    }

    fun clear(): MMVKUtils {
        mmvkUtils?.clear()
        return this
    }

    fun <T> save(keyword: String?, value: T?): MMVKUtils {
        when (value) {
            null -> mmvkUtils?.reKey(keyword)
            is String -> mmvkUtils?.encode(keyword, value)
            is Int -> mmvkUtils?.encode(keyword, value)
            is Boolean -> mmvkUtils?.encode(keyword, value)
            is Long -> mmvkUtils?.encode(keyword, value)
            is Float -> mmvkUtils?.encode(keyword, value)
        }
        return this
    }

    operator fun <T> get(keyword: String?, defValue: T): T {
        return when (defValue) {
            is String -> mmvkUtils?.decodeString(keyword, defValue) as T
            is Int -> mmvkUtils?.decodeInt(keyword,defValue) as T
            is Boolean -> mmvkUtils?.decodeBool(keyword,defValue) as T
            is Long -> mmvkUtils?.decodeLong(keyword,defValue) as T
            is Float -> mmvkUtils?.decodeFloat(keyword,defValue) as T
            else -> defValue
        }
    }
}