package com.mtj.common.pop

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.mtj.common.R
import com.mtj.common.base.ActivityHolder
import com.tuo.customview.VerificationCodeView
import razerdp.basepopup.BasePopupWindow
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig

class CheckPassWordPop(context: Context?) : BasePopupWindow(context) {

    val verificationCodeView = findViewById<VerificationCodeView>(R.id.verificationCodeView)
    val ivClose = findViewById<ImageView>(R.id.ivClose)
    val tvForgetPw = findViewById<TextView>(R.id.tvForgetPw)

    init {
        ivClose.setOnClickListener { dismiss() }
        setAutoShowInputMethod(true)
        tvForgetPw.setOnClickListener {
            ARouter.getInstance().build(ActivityHolder.PERSON_INFO_MODIFY).with(Bundle().apply {
                putString("type", "PAY_PW")
            }).navigation()
        }
        verificationCodeView.setPwdMode(true)
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
        return createPopupById(R.layout.pop_check_password)
    }
}