package com.mtj.common.pop

import android.content.Context
import android.view.View
import android.widget.TextView
import com.mtj.common.R
import razerdp.basepopup.BasePopupWindow

class TipsPop(context: Context) : BasePopupWindow(context) {

    var tvTitle: TextView
    var tvContent: TextView
    var tvCancel: TextView
    var tvOk: TextView
    var viewCenter: View

    override fun onCreateContentView(): View = createPopupById(R.layout.pop_tips_layout)

    init {
        setOutSideDismiss(false)
        //setKeyEventListener { keyEvent -> true }
        tvTitle = findViewById(R.id.tvTitle)
        tvContent = findViewById(R.id.tvContent)
        tvCancel = findViewById(R.id.tvCancel)
        tvOk = findViewById(R.id.tvOk)
        viewCenter = findViewById(R.id.viewCenter)

        tvCancel.setOnClickListener {
            mOnCancelClickListener?.callBack()
            dismiss()
        }
        tvOk.setOnClickListener {
            mOnClickListener?.callBack()
            dismiss()
        }
    }

    fun setTitle(title: String?): TipsPop {
        tvTitle.text = title
        return this
    }

    fun setTitleVisibility(visibility: Boolean): TipsPop {
        tvTitle.visibility = if (visibility) View.VISIBLE else View.GONE
        return this
    }

    fun setBtnCancelVisibility(visibility: Boolean): TipsPop {
        tvCancel.visibility = if (visibility) View.VISIBLE else View.GONE
        viewCenter.visibility = if (visibility) View.VISIBLE else View.GONE
        return this
    }

    fun setContent(content: CharSequence?): TipsPop {
        tvContent.text = content
        return this
    }
    fun setContentSize(sp: Int): TipsPop {
        tvContent.textSize = sp.toFloat()
        return this
    }

    fun setCancelText(text: String?): TipsPop {
        tvCancel.text = text
        return this
    }

    fun setOkText(text: String?): TipsPop {
        tvOk.text = text
        return this
    }


    interface OnClickListener {
        fun callBack()
    }

    interface OnCancelClickListener {
        fun callBack()
    }

    private var mOnClickListener: OnClickListener? = null
    private var mOnCancelClickListener: OnCancelClickListener? = null

    fun setOnClickListener(mOnClickListener: OnClickListener?): TipsPop {
        this.mOnClickListener = mOnClickListener
        return this
    }

    fun setOnCancelClickListener(mOnClickListener: OnCancelClickListener?): TipsPop {
        this.mOnCancelClickListener = mOnClickListener
        return this
    }

}