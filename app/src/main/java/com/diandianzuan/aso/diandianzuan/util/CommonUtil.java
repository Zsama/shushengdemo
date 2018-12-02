package com.diandianzuan.aso.diandianzuan.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by licrynoob on 2016/guide_2/12 <br>
 * Copyright (C) 2016 <br>
 * Email:licrynoob@gmail.com <p>
 * 常用工具类
 */
public class CommonUtil {


    /**
     * 跳转到应用市场
     *
     * @param appPkg
     *            ：上传到应用市场上app的包名,不是本项目的包名
     * @param marketPkg
     *            ：应用市场的包名
     */
    public static void jumpToMarket(Activity activity, String appPkg, String marketPkg) {
        Uri uri = Uri.parse("market://details?id=" + appPkg);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (marketPkg != null) {// 如果没给市场的包名，则系统会弹出市场的列表让你进行选择。
            intent.setPackage(marketPkg);
        }
        activity.startActivity(intent);
    }

    public static void jumpToMarketSearch(Activity activity, String keyword,String marketPkg) {

        Uri uri = Uri.parse("market://search?q=pub:+" + keyword);
        if (marketPkg.equals("com.xiaomi.market")){
            uri = Uri.parse("market://search?q=" + keyword);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (marketPkg != null) {// 如果没给市场的包名，则系统会弹出市场的列表让你进行选择。
            intent.setPackage(marketPkg);
        }
        activity.startActivity(intent);
    }
    /**
     * 文本替换工具
     */

    public static String replace(String from, String to, String source) {
        if (source == null || from == null || to == null)
            return null;
        StringBuffer bf = new StringBuffer("");
        int index = -1;
        while ((index = source.indexOf(from)) != -1) {
            bf.append(source.substring(0, index) + to);
            source = source.substring(index + from.length());
            index = source.indexOf(from);
        }
        bf.append(source);
        return bf.toString();
    }
    /**
     * 判断是否为手机号
     *
     * @param phone 手机号
     * @return true
     */
    public static boolean isPhone(String phone) {
        Pattern p;
        Matcher m;
        boolean b;
        p = Pattern.compile("^[1][0-9][0-9]{9}$");
        m = p.matcher(phone);
        b = m.matches();
        return b;
    }

    /**
     * 密码验证
     * @param password
     * @return
     */
    public static boolean isPassword(String password){
        Pattern p;
        Matcher m;
        boolean b;
        p = Pattern.compile("[A-Za-z0-9~!@#$%^&*()_+;',.:<>]{6,20}");
        m = p.matcher(password);
        b=m.matches();
        return b;
    }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    public static boolean isNet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 打开网络设置界面
     */
    public static void openNetSet(Activity activity, int requestCode) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 对象转为Base64位字符串
     *
     * @param object Object
     * @return base64
     */
    public static String objectToBase64(Object object) {
        String userBase64;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            userBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            return userBase64;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Base64位字符串转为对象
     *
     * @param base64Str 64位字符串
     * @return Object
     */
    public static Object base64ToObject(String base64Str) {
        Object object;
        byte[] buffer = Base64.decode(base64Str, Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
            object = ois.readObject();
            return object;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bais.close();
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Map 转 JsonStr
     *
     * @param map Map
     * @return JSON字符串
     */
    public static String mapToJson(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "null";
        }
        String json = "{";
        Set<?> keySet = map.keySet();
        for (Object key : keySet) {
            json += "\"" + key + "\":\"" + map.get(key) + "\",";
        }
        json = json.substring(1, json.length() - 2);
        json += "}";
        return json;
    }

    /**
     * JsonStr 转 Map
     *
     * @param json JSON字符串
     * @return Map
     */
    public static Map jsonToMap(String json) {
        String sb = json.substring(1, json.length() - 1);
        String[] name = sb.split("\\\",\\\"");
        String[] nn;
        Map<String, String> map = new HashMap<>();
        for (String aName : name) {
            nn = aName.split("\\\":\\\"");
            map.put(nn[0], nn[1]);
        }
        return map;
    }

    /**
     * 通过资源设置文字颜色
     */
    public static void setTextColorByRes(TextView textView, int colorRes) {
        textView.setTextColor(ContextCompat.getColor(textView.getContext(), colorRes));
    }

    /**
     * 获取非空Str
     *
     * @param tagStr     目标Str
     * @param defaultStr 默认Str
     * @return ResultStr
     */
    public static String setNoNullStr(String tagStr, String defaultStr) {
        if (!TextUtils.isEmpty(tagStr)) {
            return tagStr;
        }
        return defaultStr;
    }

    /**
     * 去除空字符
     * @param param old
     * @return new
     */
    public static String replaceBlank(String param) {
        String dest = "";
        if (!TextUtils.isEmpty(param)) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(param);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 获取包信息
     *
     * @return PackageInfo
     */
    public static PackageInfo getPackageInfo(Context context) throws PackageManager.NameNotFoundException {

            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

    }

}
