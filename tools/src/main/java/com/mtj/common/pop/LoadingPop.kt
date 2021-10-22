package com.mtj.common.pop

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.mtj.common.R
import razerdp.basepopup.BasePopupWindow

class LoadingPop(context: Context?) : BasePopupWindow(context) {
    var tvContent: TextView
    fun setContent(content: String?) {
        tvContent.text = content
    }

    override fun onCreateContentView(): View {
        return createPopupById(R.layout.pop_loading_layout)
    }

    init {
        setOutSideDismiss(false)
        setBackPressEnable(false)
        setBackgroundColor(Color.parseColor("#00000000"))
        tvContent = findViewById(R.id.tvLoading)
        val ivLoading = findViewById<ImageView>(R.id.ivLoading)
        ivLoading.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.progress_anim))
    }

}