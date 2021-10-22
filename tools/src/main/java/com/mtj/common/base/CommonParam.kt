package com.mtj.common.base

import com.blankj.utilcode.util.Utils

object CommonParam {

    fun getDimen(dimen: Int): Float = Utils.getApp().resources.getDimension(dimen)

}