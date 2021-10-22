package com.mtj.common.util

import com.tencent.mmkv.MMKV

/**
 * 共享参数类
 */
object MMVKMineUtils {

    const val IS_NEW = "IS_NEW" //新用户
    const val PASSWORD = "PASSWORD" //密码
    const val MOBILE = "MOBILE" //手机
    const val NICK_NAME = "NICK_NAME" //昵称
    const val USER_ID = "USER_ID"
    const val STORE_ID = "STORE_ID"
    const val IS_AUTHENTICATION = "IS_AUTHENTICATION" // 认证标记
    const val DEBT_PREPAY = "DEBT_PREPAY" //权限标识
    const val IS_B = "IS_B" //批发商

    const val HOME_POP_TIME = "HOME_POP_TIME"//首页时间
    const val AUTHENTICATION_TIPS = "AUTHENTICATION_TIPS"//认证提示开关
    const val BARGAIN_TIPS = "BARGAIN_TIPS"//砍价提示


    val mmvkMineUtils by lazy {
        MMKV.mmkvWithID("MINE")
    }

    fun clear(): MMVKMineUtils {
        mmvkMineUtils?.clear()
        return this
    }

    fun <T> save(keyword: String?, value: T?): MMVKMineUtils {
        when (value) {
            null -> mmvkMineUtils?.reKey(keyword)
            is String -> mmvkMineUtils?.encode(keyword, value)
            is Int -> mmvkMineUtils?.encode(keyword, value)
            is Boolean -> mmvkMineUtils?.encode(keyword, value)
            is Long -> mmvkMineUtils?.encode(keyword, value)
            is Float -> mmvkMineUtils?.encode(keyword, value)
        }
        return this
    }

    operator fun <T> get(keyword: String?, defValue: T): T {
        return when (defValue) {
            is String -> mmvkMineUtils?.decodeString(keyword, defValue) as T
            is Int -> mmvkMineUtils?.decodeInt(keyword, defValue) as T
            is Boolean -> mmvkMineUtils?.decodeBool(keyword, defValue) as T
            is Long -> mmvkMineUtils?.decodeLong(keyword, defValue) as T
            is Float -> mmvkMineUtils?.decodeFloat(keyword, defValue) as T
            else -> defValue
        }
    }

}