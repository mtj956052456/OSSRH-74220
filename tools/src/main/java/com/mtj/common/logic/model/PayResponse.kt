package com.mtj.common.logic.model

/**
 * @author  孟腾蛟
 * @Description
 * @date 2021/3/29
 */
 class PayResponse(val status: Int, val msg: String, val result: Result) {
    class Result(
        val appid: String,
        val noncestr: String,
        val `package`: String,
        val partnerid: String,
        val prepayid: String,
        val sign: String,
        val timestamp: String
    )
}