package com.mtj.common.pop

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import com.coorchice.library.SuperTextView
import com.mtj.common.R
import com.mtj.common.base.BaseActivity
import com.mtj.common.permissionx_library.PermissionCallBack
import razerdp.basepopup.BasePopupWindow
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig

/**
 * 创建者 孟腾蛟
 * 创建时间 2019/03/21
 * 描述
 */
class CallMobilePop(context: Context?, mobile: String, var title: String = "联系商家") : BasePopupWindow(context) {

    val tvTitle = findViewById<TextView>(R.id.tvTitle)
    val tvMobile = findViewById<TextView>(R.id.tvMobile)

    init {
        tvTitle.text = title
        tvMobile.text = mobile
        findViewById<SuperTextView>(R.id.tvCall).setOnClickListener {
            val baseActivity = context as BaseActivity
            baseActivity.checkPermission(object : PermissionCallBack {
                override fun agree() {
                    context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${mobile}")))
                }

                override fun refuse() {}
            }, Manifest.permission.READ_PHONE_STATE)
        }
        findViewById<SuperTextView>(R.id.tvCancel).setOnClickListener {
            dismiss()
        }
        findViewById<ImageView>(R.id.ivClose).setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateShowAnimation(): Animation {
        return AnimationHelper.asAnimation()
            .withTranslation(TranslationConfig.FROM_BOTTOM)
            .toShow()
    }

    override fun onCreateDismissAnimation(): Animation {
        return AnimationHelper.asAnimation()
            .withTranslation(TranslationConfig.TO_BOTTOM)
            .toDismiss()
    }

    override fun onCreateContentView(): View {
        return createPopupById(R.layout.pop_call_mobile)
    }
}