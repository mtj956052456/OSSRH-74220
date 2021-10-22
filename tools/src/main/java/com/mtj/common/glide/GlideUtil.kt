package com.mtj.common.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ScreenUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.mtj.common.R
import com.mtj.common.http.BASE_URL
import com.github.chrisbanes.photoview.PhotoView
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlin.concurrent.thread
import com.bumptech.glide.load.resource.bitmap.CircleCrop

/**
 * @author  孟腾蛟
 * @Description
 * @date 2021/3/31
 */
object GlideUtil {

    /**
     * 加载同等比例的宽高图片
     * @return 图片的高度
     */
    fun loadWHImage(context: Context, url: String, iv: View) {
        Glide.with(context)
            .load(getGlideUrl(url))
            .dontAnimate()
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    val bitmap = (resource as BitmapDrawable).bitmap
                    val width = ScreenUtils.getScreenWidth()
                    val value = (bitmap.height.toFloat() / bitmap.width.toFloat())
                    val height: Float = value * width.toFloat()
                    val param = iv.layoutParams
                    param.width = width
                    param.height = height.toInt()
                    iv.layoutParams = param //获取宽高比设置图片的大小
                    return false
                }

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    return false
                }
            })
            .into(if (iv is PhotoView) iv as PhotoView else iv as ImageView)
    }

    interface DownLoadInterFace {
        fun success()
    }

    /**
     * 下载图片到相册
     */
    fun downLoadImages(context: Context, urlList: List<String>, callBack: DownLoadInterFace) {
        thread(true) {
            var count = 0
            urlList.forEach {
                Glide.with(context)
                    .load(getGlideUrl(it))
                    .dontAnimate()
                    .into(object : CustomTarget<Drawable>() {
                        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                            val bitmap = (resource as BitmapDrawable).bitmap
                            val file = ImageUtils.save2Album(bitmap, Bitmap.CompressFormat.PNG, 40)
                            if (file != null) count++
                            if (count == urlList.size) callBack.success()
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })
            }
        }
    }

    fun loadImageView(context: Context, url: String, view: ImageView) {
        Glide.with(context)
            .load(getGlideUrl(url))
            .dontAnimate()
            .error(R.mipmap.ic_weixin)
            .placeholder(R.mipmap.ic_weixin)
            .into(view)
    }

    fun loadImageView(context: Context, res: Int, view: ImageView) {
        Glide.with(context)
            .load(res)
            .dontAnimate()
            .error(R.mipmap.ic_weixin)
            .placeholder(R.mipmap.ic_weixin)
            .into(view)
    }

    fun loadIVtoRoundCorner(context: Context, url: String, view: ImageView, corner: Float) {
        Glide.with(context)
            .load(getGlideUrl(url))
            .dontAnimate()
            .error(R.mipmap.ic_weixin)
            .placeholder(R.mipmap.ic_weixin)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    val bitmap = (resource as BitmapDrawable).bitmap
                    view.setImageBitmap(ImageUtils.toRoundCorner(bitmap, corner))
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                }
            })
    }

    fun loadIVtoRoundCorner(context: Context, res_id: Int, view: ImageView, corner: Float) {
        Glide.with(context)
            .load(res_id)
            .dontAnimate()
            .error(R.mipmap.ic_weixin)
            .placeholder(R.mipmap.ic_weixin)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    val bitmap = (resource as BitmapDrawable).bitmap
                    view.setImageBitmap(ImageUtils.toRoundCorner(bitmap, corner))
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                }
            })
    }

    fun loadIVtoCircle(context: Context, url: String, view: ImageView) {
        Glide.with(context)
            .load(getGlideUrl(url))
            .dontAnimate()
            .error(R.mipmap.ic_weixin)
            .placeholder(R.mipmap.ic_weixin)
            .apply(bitmapTransform(CircleCrop()))
            .into(view)
    }

    fun loadIVtoCircle(context: Context, res_id: Int, view: ImageView) {
        Glide.with(context)
            .load(res_id)
            .dontAnimate()
            .error(R.mipmap.ic_weixin)
            .placeholder(R.mipmap.ic_weixin)
            .apply(bitmapTransform(CircleCrop()))
            .into(view)
    }

    /**
     *  模糊
     */
    fun loadBlurToImageView(context: Context, url: String, view: ImageView) {
        //Glide实现图片高斯模糊
        Glide.with(context)
            .load(getGlideUrl(url))
            .dontAnimate()
            .apply(bitmapTransform(BlurTransformation(25, 3)))
            .skipMemoryCache(false)
            .into(view)
    }

    fun getGlideUrl(url: String) = if (url.startsWith("http")) url else "$BASE_URL${url}"

//    fun getGlideUrl(url: String): IMTokenGlideUrl = IMTokenGlideUrl(
//        if (url.startsWith("http")) url else "$BASE_URL${url}",
//        LazyHeaders.Builder()
//            .addHeader("time", (System.currentTimeMillis() / 1000).toString())
//            .addHeader("sign", EncryptUtils.encryptMD5ToString("${System.currentTimeMillis() / 1000}${YTW_SIGN}").toLowerCase())
//            .build()
//    )

}