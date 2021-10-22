package com.mtj.common.glide;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;

import java.net.URL;
import java.util.Map;

public class IMTokenGlideUrl extends GlideUrl {

    private int mHashCode;

    public IMTokenGlideUrl(URL url) {
        super(url);
    }

    public IMTokenGlideUrl(String url) {
        super(url);
    }

    public IMTokenGlideUrl(URL url, Headers headers) {
        super(url, headers);
    }

    public IMTokenGlideUrl(String url, Headers headers) {
        super(url, headers);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GlideUrl) {
            GlideUrl other = (GlideUrl) o;
            return getCacheKey().equals(other.getCacheKey())
                    && !mapCompare(getHeaders(), other.getHeaders());

        }
        return false;
    }

    @Override
    public int hashCode() {
        if (mHashCode == 0) {
            mHashCode = getCacheKey().hashCode();
            if (getHeaders() != null) {
                for (String s : getHeaders().keySet()) {
                    if (getHeaders().get(s) != null) {
                        mHashCode = 31 * mHashCode + getHeaders().get(s).hashCode();
                    }
                }
            }
        }
        return mHashCode;
    }

    private static boolean mapCompare(Map<String, String> map1, Map<String, String> map2) {
        boolean differ = false;
        if (map1 != null && map2 != null) {
            if (map1.size() == map2.size()) {
                for (Map.Entry<String, String> entry1 : map1.entrySet()) {
                    String value1 = entry1.getValue() == null ? "" : entry1.getValue();
                    String value2 = map2.get(entry1.getKey()) == null ? "" : map2.get(entry1.getKey());
                    if (!value1.equals(value2)) {
                        differ = true;
                        break;
                    }
                }
            }
        } else differ = map1 != null || map2 != null;

        return differ;
    }
}