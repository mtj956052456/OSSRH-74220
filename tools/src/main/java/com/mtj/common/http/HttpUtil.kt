package com.mtj.common.http

import androidx.lifecycle.liveData
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.GsonUtils
import com.mtj.common.base.ActivityHolder
import com.mtj.common.base.BaseAppImpl
import com.mtj.common.util.Lg.lgd
import com.mtj.common.util.MMVKMineUtils
import com.mtj.common.util.MMVKUtils
import com.hjq.gson.factory.GsonFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author  孟腾蛟
 * @Description   网络请求公用顶层代码
 * @date 2021/3/18
 */
object HttpUtil {
    fun initDefaultMap() = HashMap<String, String>().apply {
        put("is_json", "1")
        put("is_app", "1")
        put("unique_id", BaseAppImpl.uniqueDeviceId)
        put("token", MMVKUtils[MMVKUtils.TOKEN, ""])
    }
}

const val BASE_URL =""

private val client: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(2, TimeUnit.MINUTES)
    .writeTimeout(2, TimeUnit.MINUTES)
    .addInterceptor {
        val request = it.request()
        val builder = request.newBuilder()
//        val encryptMD5ToString = EncryptUtils.encryptMD5ToString("${System.currentTimeMillis() / 1000}${YTW_SIGN}")
        val build = builder
            .addHeader("time", (System.currentTimeMillis() / 1000).toString())
//            .addHeader("sign", encryptMD5ToString.toLowerCase())
            .build()
        it.proceed(build)
    }.build()

//EncryptUtils
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create(GsonFactory.getSingletonGson()))
    .build()

fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

const val WX_URL = "https://api.weixin.qq.com" //微信接口
private val wxRetrofit = Retrofit.Builder()
    .baseUrl(WX_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

fun <T> createWX(serviceClass: Class<T>): T = wxRetrofit.create(serviceClass)

const val BAIDU_URL = "https://aip.baidubce.com" //百度云 地址识别
private val bdRetrofit = Retrofit.Builder()
    .baseUrl(BAIDU_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

fun <T> createBaiDu(serviceClass: Class<T>): T = bdRetrofit.create(serviceClass)

inline fun <reified T> create(): T = create(T::class.java)

fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
    liveData(context) {
        val result = try {
            block()
        } catch (e: Exception) {
            Result.failure<T>(e)
        }
        emit(result)
    }

suspend fun <T> Call<T>.await(): T {
    return suspendCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                //lgd(Gson().toJson(body))
                try {
                    val jsonObject = JSONObject(GsonUtils.toJson(body))
                    if (jsonObject.getInt("status") == -101 || jsonObject.getInt("status") == -100) {
                        ARouter.getInstance().build(ActivityHolder.LOGIN).navigation()
                        MMVKMineUtils.clear()
                        MMVKUtils.clear()
                    }
                } catch (e: Exception) {
                }
                if (body != null) continuation.resume(body)
                else continuation.resumeWithException(RuntimeException("response body is null"))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}