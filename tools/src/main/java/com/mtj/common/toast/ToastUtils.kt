package com.mtj.common.toast

import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.coorchice.library.SuperTextView
import com.mtj.common.R
import com.mtj.common.base.BaseAppImpl


class ToastUtils {

    companion object {

        var toast: Toast? = null
        var mTextView: SuperTextView? = null

        @JvmStatic
        fun showShort(content: String?) {
            content?.apply {
                if (contains("null")) return  //null排除
                if (contains("JSON")) return  //解析异常排除
                if (contains("host")) return  //网络异常toast排除
            }
            cancelToast()
            toast = Toast(BaseAppImpl.context)
            val layout: View = View.inflate(BaseAppImpl.context, R.layout.toast_layout, null)
            mTextView = layout.findViewById<SuperTextView>(R.id.tv_content)
            mTextView?.setText(content)
            toast?.apply {
                view = layout
                setGravity(Gravity.CENTER, 0, 0) //设置Toast的位置在屏幕中间
                duration = Toast.LENGTH_SHORT
                show()
            }

        }

        fun cancelToast() {
            if (toast != null) {
                toast?.cancel()
                toast = null
                mTextView = null
            }
        }
    }
}