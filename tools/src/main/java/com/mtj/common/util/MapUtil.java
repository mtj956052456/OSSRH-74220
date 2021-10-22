package com.mtj.common.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.mtj.common.toast.ToastUtils;
import com.mtj.common.pop.SlideBottomListPop;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MapUtil {

    private double lat, lng;
    private String pointName;
    private Context context;

    public MapUtil(Context context, double lat, double lng, String pointName) {
        this.lat = lat;
        this.lng = lng;
        this.pointName = pointName;
        this.context = context;
    }

    /**
     * 选择地图
     *
     * @param lat        纬度
     * @param lon        经度
     * @param address    地址
     * @param type       1表示高德  2表示百度  3表示腾讯
     * @param selectType 1表示导航类型， 2表示周边类型
     * @param keyWords   关键字
     * @param address
     */
    public void makeUpMap(double lat, double lon, String address, int type, int selectType, String keyWords) {
        if (type == 1) {
            if (isInstallByread("com.autonavi.minimap")) {
                try {
                    //百度地图坐标先转换为高德坐标
                    //LatLng latlng = new LatLng(lat, lon);
                    String ss = "androidamap://route?sourceApplication=amap&dlat=" + lat + "&dlon=" + lon + "&dname=" + address + "&dev=0&t=2";
                    Intent intent = Intent.getIntent(ss);
                    context.startActivity(intent);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    ToastUtils.showShort("正在跳转安装高德地图");
                    Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                } catch (Exception e) {
                    ToastUtils.showShort("请先下载安装应用市场，再进行安装高德地图");
                }
            }
        } else if (type == 2) {
            if (isInstallByread("com.baidu.BaiduMap")) {
                try {
                    Intent intent = Intent.getIntent("intent://map/direction?destination=latlng:"
                            + lat + ","
                            + lon + "|name:" + address + //终点：该地址会在导航页面的终点输入框显示
                            "&mode=driving&" + //选择导航方式 此处为驾驶
                            "region=" + //
                            "&src=#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
//                    Intent intent = Intent.getIntent("intent://map/direction?origin=latlng:" + lat + "," + lon + "|name:" + address + "&destination=" + address + "&mode=driving®ion=" + address + "&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                    context.startActivity(intent); //启动调用
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    ToastUtils.showShort("正在跳转安装百度地图");
                    Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
                    context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } catch (Exception e) {
                    ToastUtils.showShort("请先下载安装应用市场，再进行安装百度地图");
                }
            }

        }
        //if (mapBean.getGaoDei() == null && mapBean.getTenXun() == null && mapBean.getBaiDu() == null) {
        //    goToMarket(FruitApplication.getInstance(), "com.autonavi.minimap");
        //    context.startActivity(intent);
        //}
        //高德地图
//        if (type == 1) {
//            if (selectType == 1) {
//                intent.setData(Uri.parse("androidamap://viewMap?sourceApplication=appname&poiname=" + address + "&lat=" + lat + "&lon=" + lon + "&dev=0"));
//            } else if (selectType == 2) {
//                intent.setData(Uri.parse("androidamap://arroundpoi?sourceApplication=softname&keywords=" + keyWords + "&lat=" + lat + "&lon=" + lon + "&dev=0"));
//            }
//        }
//        //腾讯地图
//        if (type == 2) {
//            if (selectType == 1) {
//                intent.setData(Uri.parse("http://apis.map.qq.com/uri/v1/marker?marker=coord:" + lat + "," + lon + ";" + "title:" + address + ";addr:" + " " + "&referer=myapp"));
//            } else if (selectType == 2) {
//                intent.setData(Uri.parse("http://apis.map.qq.com/uri/v1/search?keyword=" + keyWords + "¢er=" + lat + "," + lon + "&radius=5000&referer=myapp"));
//            }
//        }
//        //百度地图
//        if (type == 3) {
//            if (selectType == 1) {
//                intent.setData(Uri.parse("intent://map/direction?origin=latlng:" + lat + "," + lon + "|name:" + address + "&destination=" + address + "&mode=driving®ion=" + address + "&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end"));
//                //intent.setData(Uri.parse("baidumap://map/marker?location=" + lat + "," + lon + "&title=" + address + "&content=" + "" + "&traffic=on"));
//            } else if (selectType == 2) {
//                intent.setData(Uri.parse("baidumap://map/place/nearby?query=" + keyWords + "¢er=" + lat + "," + lon + ""));
//            }
//        }
    }


    /**
     * 去市场下载页面
     */
    public void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }

    public void showPop() {
        List<String> list = new ArrayList<>();
        //高德地图
        list.add("高德地图");
        //list.add("腾讯地图");
        list.add("百度地图");
        //list.add(new File("/data/data/" + "com.google.android.apps.maps").exists() ? "google地图" : "google地图");
        final SlideBottomListPop pop = new SlideBottomListPop(context, list.size());
        pop.setList(list);
        pop.setOnItemClickListener(new SlideBottomListPop.OnItemClickListener() {
            @Override
            public void callBack(String type) {
                if (type.contains("高德")) {
                    if (new File("/data/data/" + "com.autonavi.minimap").exists())
                        makeUpMap(lat, lng, pointName, 1, 1, "");
                    else
                        ToastUtils.showShort("未检测到" + type);
                }
//                if (type.contains("腾讯")) {
//                    if (new File("/data/data/" + "com.tencent.map").exists())
//                        makeUpMap(lat, lng, pointName, 2, 1, "");
//                    else
//                        ToastUtils.showShort("未检测到" + type);
//                }
                if (type.contains("百度")) {
                    if (new File("/data/data/" + "com.baidu.BaiduMap").exists())
                        makeUpMap(lat, lng, pointName, 2, 1, "");
                    else
                        ToastUtils.showShort("未检测到" + type);
                }
                pop.dismiss();
            }
        });
        pop.addCancelView();
        pop.showPopupWindow();
    }


    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

}
