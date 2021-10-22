package com.mtj.common.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mtj.common.R
import com.mtj.common.glide.GlideUtil
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.AlbumLoader

class MediaLoader : AlbumLoader {

    override fun load(imageView: ImageView, albumFile: AlbumFile) {
        load(imageView, albumFile.path)
    }

    override fun load(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .error(R.mipmap.ic_weixin)
            .placeholder(R.mipmap.ic_weixin)
            //.crossFade()
            .into(imageView)
    }
}