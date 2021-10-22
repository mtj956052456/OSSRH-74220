package com.mtj.common.base

import android.app.Application
import android.content.Context
import android.os.Environment
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.*
import com.chad.library.adapter.base.BaseQuickAdapter
//import com.coorchice.library.ImageEngine
import com.mtj.common.R
//import com.mtj.common.glide.GlideEngine
import com.mtj.common.util.Lg.lge
import com.mtj.common.util.MediaLoader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.mmkv.MMKV
//import com.webank.facelight.process.FaceVerifyStatus
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import java.io.File


/**
 * @author  孟腾蛟
 * @Description
 * @date 2020/5/7
 */
open class BaseAppImpl : Application() {

    companion object {
        //腾讯云
//        val nonce =  "DYKJ_YTW_666666" + System.currentTimeMillis()
//        val agreementNo =  "DYKJ_YTW_888888" + System.currentTimeMillis()
//        val mode = FaceVerifyStatus.Mode.GRADE

        //测试appid、keyLicence
        val appId = ""
        val keyLicence = ""
        //正式appid、keyLicence
//        val appId = "IDA8sqMo"
//        val keyLicence = "ghvEZl14JfCEwecbFum5Vs+2JjOknqvDQWvSriKf0jepRGXU52UVu9zVacP3eImqxuidtsYh3M2HyvmQzkTxoBeC6igqZhlEhIfnVIio0So6YkcPyX0cnA9lYjarXBy3NaoPW7qxTZMIhbae/hlJ0ra1gPyPztrcZ11hCbmwTs9iRbJNG/iTDEKG/4i7duuqVtZ+wal2yge7hPFRPolS6dU3YexY0BkGZ9I6+hzp9fD3FIPolgNMv2aIsnWWpzMuMKBMbOcSOUNh7yxUE2XL8rsnJWVdJkD0SOpjOIFv9QzBNgLL1Nu6PeUcUpfLppytQqRnq6pZ3gl8lFaZCEbuXQ=="

        var uniqueDeviceId = "" //设备唯一id
        var picPath = ""
        var videoPath = ""
        lateinit var context: Context
        lateinit var api: IWXAPI
        var WXAppID = "wx56a99a30ce461e4f"
        var WXAppSecret = "3dbc4f48951f5cdf532c289ddddda86d"
//        val WXAppID = "wx725f6157eed484e7"
//        val WXAppSecret = "3dbc4f48951f5cdf532c289ddddda86d"

        // 微信
        fun regToWx() {
            // 通过WXAPIFactory工厂，获取IWXAPI的实例
            api = WXAPIFactory.createWXAPI(context, WXAppID, true)
            // 将应用的appId注册到微信
            if (api.isWXAppInstalled) {
                api.registerApp(WXAppID)
            } else {
                ToastUtils.showShort("您还未安装微信")
            }
            ////建议动态监听微信启动广播进行注册到微信
            //registerReceiver(object : BroadcastReceiver() {
            //    override fun onReceive(context: Context?, intent: Intent?) {
            //        // 将该app注册到微信
            //        api.registerApp(WXAppID)
            //    }
            //}, IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP))
        }

        fun WxLogin() {
            regToWx()
            val req = SendAuth.Req()
            req.scope = "snsapi_userinfo"
            req.state = "ywt"
            api.sendReq(req)
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        picPath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path + File.separator + "picture"
        videoPath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path + File.separator + "video"
        initBlankj()
//        ARouter.openLog()
//        ARouter.openDebug()
        MMKV.initialize(this);
        ARouter.init(this)
        initSmartRefresh()
        uniqueDeviceId = DeviceUtils.getUniqueDeviceId()
//        ImageEngine.install(GlideEngine(this))// 安装图片引擎
        initAlbum()
        initExceptionCrash()
    }

    private fun initExceptionCrash() {
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            lge("${e.message}")
        }
    }

    private fun initBlankj() {
        Utils.init(this)
        CrashUtils.init {
//            TipsPop(this)
//                .setBtnCancelVisibility(false)
//                .setTitleVisibility(false)
//                .setContent(it.toString())
//                .showPopupWindow()
        }

//        NetworkUtils.registerNetworkStatusChangedListener(object : NetworkUtils.OnNetworkStatusChangedListener {
//            override fun onConnected(networkType: NetworkUtils.NetworkType?) {
//                ToastUtils.showShort("网络重新连接了")
//            }
//
//            override fun onDisconnected() {
//                ToastUtils.showShort("网络断开了")
//            }
//        })
    }

    private fun initAlbum() {
        Album.initialize(
            AlbumConfig.newBuilder(this)
                .setAlbumLoader(MediaLoader())
                .build()
        );
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initSmartRefresh() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.color_f5f7fa, R.color.black) //全局设置主题颜色
            ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }
}