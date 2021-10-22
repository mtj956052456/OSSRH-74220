package com.mtj.common.base

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.KeyboardUtils
import com.mtj.common.R
import com.mtj.common.base.ActivityHolder.addActivity
import com.mtj.common.base.ActivityHolder.removeActivity
import com.mtj.common.permissionx_library.CustomDialogFragment
import com.mtj.common.permissionx_library.PermissionCallBack
import com.mtj.common.permissionx_library.PermissionReadPhoneStateDialog
import com.mtj.common.pop.LoadingPop
import com.mtj.common.swipeback.ViewDragHelper
import com.mtj.common.swipeback.app.SwipeBackActivity
import com.mtj.common.util.Lg.lgd
import com.mtj.common.util.MMVKMineUtils
import com.gyf.immersionbar.ImmersionBar
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.greenrobot.eventbus.EventBus
import java.util.ArrayList


abstract class BaseActivity : AppCompatActivity() {
    private var mBaseActivity: BaseActivity? = null
    var savedInstanceState: Bundle? = null
    private var loadingPop: LoadingPop? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState
        setContentView(setView())
        super.onCreate(savedInstanceState)
        mBaseActivity = this
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //强制竖屏
        ARouter.getInstance().inject(this) //注册路由
        addActivity(this)
        //setSwipeBackEnable(true)
        //swipeBackLayout.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)
        initView() //界面控件初始化
        changeStatusBarColor(statusBarColor())
    }

    open fun setSwipeBackEnable(enable: Boolean) {
        //这个方法是重写侧滑删除的  用的时候重写此类，继承SwipeBackActivity
    }

    var aList: MutableList<*>? = null
    var page = 1
    var rows = 10

    override fun onResume() {
        super.onResume()
        //changeStatusBarColor(statusBarColor())
        aList?.apply {
            rows = if (size > 10) rows * page else 10
            page = 1
        }
    }

    //列表操作后，刷新列表停留在离开时的位置
    fun resumePosition() {
        if (page == 1) {
            page = rows / 10
            rows = 10
            aList?.clear()
        }
    }

    //刷新第一页
    fun resumePage1() {
        if (page == 1) {
            aList?.clear()
        }
    }

    open fun statusBarColor(): Int = R.color.white

    // 改变状态栏颜色
    open fun changeStatusBarColor(color: Int) {
        ImmersionBar.with(this)
            .statusBarColor(color)
            .fitsSystemWindows(true)
            .statusBarDarkFont(!isLightColor(color))
            .init()
    }

    open fun isLightColor(@ColorInt color: Int): Boolean = ColorUtils.calculateLuminance(color) >= 0.5

    /**
     * 初始化ToolBar
     *
     * @param title
     */
    open fun initCustomToolbar(title: String) {
        customtoolbar.setMainTitleLeftDrawable(R.mipmap.ic_back_black)
        customtoolbar.setMainTitle("" + title)
        lt_main_title_left.setOnClickListener { finish() }
    }

    open fun setHeadTitle(title: String) {
        customtoolbar.setMainTitle("" + title)
    }

    //    @SingleClick
    fun intoActivityForResult(path: String?, bundle: Bundle?, resultCode: Int) {
        ARouter.getInstance().build(path).with(bundle).navigation(this, resultCode)
    }

    //    @SingleClick
    fun intoActivity(path: String?) {
        ARouter.getInstance().build(path).navigation(this)
    }

    //    @SingleClick
    fun intoActivityWithBundle(path: String?, bundle: Bundle?) {
        ARouter.getInstance().build(path).with(bundle).navigation(this, object : NavigationCallback {
            override fun onFound(postcard: Postcard) {}
            override fun onLost(postcard: Postcard) {}
            override fun onArrival(postcard: Postcard) {}
            override fun onInterrupt(postcard: Postcard) {}
        })
    }

    //    @SingleClick
    fun finishIntoActivity(path: String?) {
        ARouter.getInstance().build(path).navigation(this, object : NavigationCallback {
            override fun onFound(postcard: Postcard) {
                lgd("${javaClass.simpleName}: onFound")
            }

            override fun onLost(postcard: Postcard) {
                lgd("${javaClass.simpleName}: onLost")
                finish()
            }

            override fun onArrival(postcard: Postcard) {
                lgd("${javaClass.simpleName}: onArrival")
            }

            override fun onInterrupt(postcard: Postcard) {
                lgd("${javaClass.simpleName}: onInterrupt")
            }
        })
    }

    //设置界面之前做的事
    protected fun beforeSetView() {}
    protected abstract fun setView(): Int

    //设置界面
    protected open fun initView() {
    }


    open fun showLoading(show: Boolean, content: String = "请稍后...") {
        if (isFinishing)
            return
        if (loadingPop == null) {
            loadingPop = LoadingPop(this)
        }
        if (show) {
            loadingPop?.setContent(content)
            loadingPop?.showPopupWindow()
        } else {
            loadingPop?.dismiss()
        }
    }

    /**
     * 权限检查
     */
    open fun checkPermission(callBack: PermissionCallBack?, vararg permissions: String?) {
        PermissionX.init(this)
            .permissions(
                *permissions
                //Manifest.permission.CAMERA,
                //Manifest.permission.ACCESS_FINE_LOCATION,
                //Manifest.permission.RECORD_AUDIO,
                //Manifest.permission.READ_CALENDAR,
                //Manifest.permission.READ_CALL_LOG,
                //Manifest.permission.READ_CONTACTS,
                //Manifest.permission.READ_PHONE_STATE,
                //Manifest.permission.BODY_SENSORS,
                //Manifest.permission.ACTIVITY_RECOGNITION,
                //Manifest.permission.SEND_SMS,
                //Manifest.permission.READ_EXTERNAL_STORAGE

                //Manifest.permission.READ_PHONE_NUMBERS
                //Manifest.permission.READ_PHONE_STATE,
            )
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope, deniedList, beforeRequest ->
                if (beforeRequest) {
                    if (deniedList.contains(Manifest.permission.READ_PHONE_STATE)) {
                        val message = "是否允许“拼掌柜”获取设备信息？"
                        val content = "读取通话状态和移动网络信息"
                        val dialog = PermissionReadPhoneStateDialog(message, content, deniedList)
                        scope.showRequestReasonDialog(dialog)
                    } else if (deniedList.contains(Manifest.permission.READ_PHONE_NUMBERS)) {
                        val message = "允许读取手机状态权限以便开启\n一键登录功能"
                        val dialog = PermissionReadPhoneStateDialog(message, deniedList)
                        scope.showRequestReasonDialog(dialog)
                    } else {
                        val message = "需要您同意以下权限才能正常使用"
                        scope.showRequestReasonDialog(deniedList, message, "同意", "拒绝")
                    }
                }
            }
            .onForwardToSettings { scope, deniedList ->
                val message = "您需要去设置中手动开启以下权限"
                val dialog = CustomDialogFragment(message, deniedList)
                scope.showForwardToSettingsDialog(dialog)
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    callBack?.agree()
                } else {
                    callBack?.refuse()
                    Toast.makeText(this, "您拒绝了权限，部分功能将无法使用", Toast.LENGTH_SHORT).show()
                }
            }
    }

    //点击空白处隐藏软件盘
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                KeyboardUtils.hideSoftInput(this)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    // Return whether touch the view.
    open fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationOnScreen(l)
            val left = l[0]
            val top = l[1]
            val bottom: Int = top + v.getHeight()
            val right: Int = left + v.getWidth()
            return !(event.rawX > left && event.rawX < right && event.rawY > top && event.rawY < bottom)
        }
        return false
    }

    /**
     * 设置最大系统字体为标准字体
     *
     * @return
     */
    override fun getResources(): Resources {
        val resources = super.getResources()
        val configuration = resources.configuration
        configuration.fontScale = 1.0f
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return resources
    }

    override fun onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this)
        removeActivity(this)
        super.onDestroy()
    }

    //判断如果去商店是自己的门店id  则去我的小店
    fun goToShopDetail(store_id: String) {
        if (store_id == MMVKMineUtils[MMVKMineUtils.STORE_ID, ""])
            intoActivity(ActivityHolder.STORE_ROOT)
        else intoActivityWithBundle(ActivityHolder.SHOP_DETAIL, Bundle().apply { putString("STORE_ID", store_id) })
    }
}