package com.mtj.common.video

import cn.jzvd.Jzvd
import com.mtj.common.toast.ToastUtils
import com.mtj.common.R
import com.mtj.common.base.BaseActivity
import com.mtj.common.http.BASE_URL
import com.mtj.common.util.AlbumUtil
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : BaseActivity() {

    override fun setView(): Int = R.layout.activity_video

    override fun initView() {
        super.initView()
        var url = ""
        var title = ""
        try {
            url = intent.getStringExtra("url")?: ""
            if (url.contains("mp4") && !url.startsWith("http"))
                url = BASE_URL + url
            title = intent.getStringExtra("title")?: ""
        } catch (e: Exception) {
        }
        if (url.isEmpty()) {
            ToastUtils.showShort("视频地址有误")
            finish()
        }
//        videoView.setUrl(url) //设置视频地址
//        val controller = StandardVideoController(this)
//        controller.addDefaultControlComponent(title, false)
//        videoView.setVideoController(controller) //设置控制器
//        //videoView.start() //开始播放，不调用则不自动播放
        jz_video.setUp(url,title)
        jz_video.posterImageView.setImageBitmap(AlbumUtil.getVideoThumbnail(url))
    }
    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }
    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

//    override fun onPause() {
//        super.onPause()
//        videoView.pause()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        videoView.resume()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        videoView.release()
//    }
//
//    override fun onBackPressed() {
//        if (!videoView.onBackPressed()) {
//            super.onBackPressed()
//        }
//    }

}