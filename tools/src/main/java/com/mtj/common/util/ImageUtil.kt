package com.mtj.common.util

import android.widget.ImageView
import com.blankj.utilcode.util.ScreenUtils
import android.graphics.Bitmap
import android.view.View
import java.lang.Exception


object ImageUtil {

    fun setWHPic(ivPic: ImageView) {
        val width = ScreenUtils.getScreenWidth() / 2 //- CommonParam.getDimen(R.dimen.dp_2)
        val height = width
        val param = ivPic.layoutParams
        param.width = width
        param.height = height
        ivPic.layoutParams = param //获取宽高比设置图片的大小
    }
}