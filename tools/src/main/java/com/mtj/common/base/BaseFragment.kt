package com.mtj.common.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.mtj.common.R
import com.mtj.common.pop.LoadingPop
import com.mtj.common.util.Lg
import com.mtj.common.util.MMVKMineUtils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.greenrobot.eventbus.EventBus

/**
 * 创建者 孟腾蛟
 * 创建时间 2019/3/23
 * 描述 碎片基类
 */
abstract class BaseFragment : Fragment() {
    private var rootView: View? = null
    private var savedInstanceState: Bundle? = null
    private var loadingPop: LoadingPop? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.savedInstanceState = savedInstanceState
        if (null == rootView) {
            rootView = inflater.inflate(setView(), container, false)
        } else {
            if (null != rootView!!.parent) {
                val parent = rootView!!.parent as ViewGroup
                parent.removeAllViews()
            }
        }
        return rootView
    }

    var aList: MutableList<*>? = null
    var page = 1
    var rows = 10

    override fun onResume() {
        super.onResume()
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    open fun findViewById(id: Int): View? {
        return rootView!!.findViewById(id)
    }

    open fun initCustomToolbar(title: String) {
        customtoolbar.setMainTitleLeftDrawable(R.mipmap.ic_back_black)
        customtoolbar.setMainTitle("" + title)
        lt_main_title_left.setOnClickListener { activity?.finish() }
    }

    open fun intoActivity(path: String?) {
        ARouter.getInstance().build(path).navigation(activity)
    }

    open fun intoActivityWithBundle(path: String?, bundle: Bundle?) {
        ARouter.getInstance().build(path).with(bundle).navigation(activity)
    }

    fun intoActivityForResult(path: String?, bundle: Bundle?, resultCode: Int) {
        ARouter.getInstance().build(path).with(bundle).navigation(activity, resultCode)
    }

    open fun finishIntoActivity(path: String?) {
        ARouter.getInstance().build(path).navigation(activity, object : NavigationCallback {
            override fun onFound(postcard: Postcard) {}
            override fun onLost(postcard: Postcard) {
                activity!!.finish()
            }

            override fun onArrival(postcard: Postcard) {}
            override fun onInterrupt(postcard: Postcard) {}
        })
    }

    private var isShown = 0 //是否显示过

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (isShown == 0) setUserVisibleHintData()
            isShown++
        }
    }

    open fun showLoading(show: Boolean, content: String = "请稍后...") {
        if (activity!!.isFinishing)
            return
        if (loadingPop == null) {
            loadingPop = LoadingPop(activity)
        }
        if (show) {
            loadingPop?.setContent(content)
            loadingPop?.showPopupWindow()
        } else {
            loadingPop?.dismiss()
        }
    }

    /**
     * 初始化数据
     *
     * @return
     */
    protected open fun setUserVisibleHintData() {
        //第一次加载数据
    }

    protected abstract fun setView(): Int

    protected abstract fun initView()

    protected open fun setTvText(textView: TextView, text: Any?) {
        if (text != null) textView.text = text.toString()
    }

    protected open fun setImageRes(imageView: ImageView, res: Int) {
        if (res != 0) imageView.setImageResource(res)
    }

    /**
     * 给控件添加一个状态栏的高度,同时用padding把这段高度给占掉,让布局正产显示
     */
    protected open fun setHeadView(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val statusId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (statusId > 0) {
                val layoutParams = view.layoutParams
                var height = layoutParams.height
                val dimensionPixelSize = resources.getDimensionPixelSize(statusId)
                height = height + dimensionPixelSize
                layoutParams.height = height
                val left = view.paddingLeft
                var top = view.paddingTop
                val right = view.paddingRight
                val bottom = view.paddingBottom
                top = top + dimensionPixelSize
                view.setPadding(left, top, right, bottom)
                view.layoutParams = layoutParams
            }
        }
    }

    protected open fun intoActivity(cls: Class<*>?) {
        startActivity(Intent(activity, cls))
    }

    override fun onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    protected open fun isEmpty(msg: String?): Boolean {
        return TextUtils.isEmpty(msg)
    }

    protected open fun setTv(msg: String?, tv: TextView?) {
        if (!TextUtils.isEmpty(msg)) {
            if (null != tv) {
                tv.text = msg
            } else {
                Log.e("MTJ", "控件空指针")
            }
        } else {
            Log.e("MTJ", "msg的数据为空")
        }
    }

    protected open fun isEmpty(list: List<*>?): Boolean {
        return null == list || list.isEmpty()
    }

    protected open fun setVisible(view: View?, isVisible: Boolean) {
        if (null != view) {
            if (isVisible) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.GONE
            }
        }
    }


    /**
     * 微信是否登录
     *
     * @return
     */
//    open fun wxIsLogin(): Boolean {
//        return !TextUtils.isEmpty(SPWXUtil.get(SPWXUtil.WX_OPENID)) && !TextUtils.isEmpty(SPWXUtil.get(SPWXUtil.WX_USER_GID))
//    }

    /**
     * 设置下拉刷新颜色
     *
     * @param refreshLayout
     */
    open fun setSwipeRefreshLayout(refreshLayout: SwipeRefreshLayout?) {
        if (refreshLayout != null) {
            //refreshLayout.setColorSchemeColors(resources.getColor(BaseConstant.toolBarColor))
        }
    }

    //判断如果去商店是自己的门店id  则去我的小店
    fun goToShopDetail(store_id: String) {
        if (store_id == MMVKMineUtils[MMVKMineUtils.STORE_ID, ""])
            intoActivity(ActivityHolder.STORE_ROOT)
        else intoActivityWithBundle(ActivityHolder.SHOP_DETAIL, Bundle().apply { putString("STORE_ID", store_id) })
    }
}