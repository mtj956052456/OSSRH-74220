package com.mtj.common.glide

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.Headers
import java.net.URL

/**
 * 添加请求头，写这个类的目的是解决图片加载闪烁
 */
class MyGlideUrl : GlideUrl {
    private var mHashCode = 0

    constructor(url: URL?) : super(url) {}
    constructor(url: String?) : super(url) {}
    constructor(url: URL?, headers: Headers?) : super(url, headers) {}
    constructor(url: String?, headers: Headers?) : super(url, headers) {}

    override fun equals(o: Any?): Boolean {
        o?.apply {
            if (this is GlideUrl) {
                val other = this
                return cacheKey == other.cacheKey && !mapCompare(headers, other.headers)
            }
        }
        return false
    }

    override fun hashCode(): Int {
        if (mHashCode == 0) {
            mHashCode = cacheKey.hashCode()
            if (headers != null) {
                for (s in headers.keys) {
                    if (headers[s] != null) {
                        mHashCode = 31 * mHashCode + headers[s].hashCode()
                    }
                }
            }
        }
        return mHashCode
    }

    companion object {
        private fun mapCompare(map1: Map<String, String?>?, map2: Map<String, String?>?): Boolean {
            var differ = false
            if (map1 != null && map2 != null) {
                if (map1.size == map2.size) {
                    for ((key, value) in map1) {
                        val value1 = if (value == null) "" else value
                        val value2 = if (map2[key] == null) "" else map2[key]!!
                        if (value1 != value2) {
                            differ = true
                            break
                        }
                    }
                }
            } else differ = map1 != null || map2 != null
            return differ
        }
    }
}