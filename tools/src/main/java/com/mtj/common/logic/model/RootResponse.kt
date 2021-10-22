package com.mtj.common.logic.model

/**
 * @author  孟腾蛟
 * @Description
 * @date 2021/3/29
 */
data class RootResponse(
    val access_token: String,
    val expires_in: Int,
    val openid: String,
    val refresh_token: String,
    val scope: String,
    val unionid: String,
    val errcode: String,
    val errmsg: String
)


data class WXLoginResponse(val status: Int, val msg: String, val result: Result) {
     class Result(
         val token: String,
         val openid: String,
         val head_pic: String,
         val nickname: String,
         val unionid: String
     )
}


class WeChatUserInfoBean(
    val openid: String, //普通用户的标识，对当前开发者帐号唯一
    val nickname: String,//普通用户昵称
    val sex: String,//普通用户性别，1为男性，2为女性
    val language: String,
    val province: String, //普通用户个人资料填写的省份
    val city: String, //普通用户个人资料填写的城市
    val country: String, //国家，如中国为CN
    val headimgurl: String, //用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
    val privilege: MutableList<String>, //用户特权信息，json数组，如微信沃卡用户为（chinaunicom）
    val unionid: String //用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
)



data class SampleResponse(val status: Int, val msg: String, val result: String)
