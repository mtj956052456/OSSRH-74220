package com.mtj.common.event

class PayEvent(val pay_state :Int,var err_msg :String ="") {
    companion object {
        const val PAY_SUCCESS = 0   //支付成功
        const val PAY_FAIL = 1      //支付失败
        const val PAY_CANCEL = 2    //支付取消
    }
}