package com.mtj.common.glide

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.coorchice.library.ImageEngine
import com.coorchice.library.image_engine.Engine
import com.mtj.common.R
import com.mtj.common.http.BASE_URL


class GlideEngine(private val context: Context) : Engine {
    override fun load(url: String, callback: ImageEngine.Callback) {
        Glide.with(context)
            .asDrawable()
            .load(GlideUtil.getGlideUrl(if (url.startsWith("http")) url else "$BASE_URL${url}"))
            //.error(R.mipmap.icon_login_bg)
            .placeholder(R.mipmap.ic_weixin)
            //.fitXY()
            .into(object : CustomTarget<Drawable>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                    callback.onCompleted(placeholder)
                }

                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    // 主要是通过callback返回Drawable对象给SuperTextView
                    callback.onCompleted(resource)
                }
            })
    }
}