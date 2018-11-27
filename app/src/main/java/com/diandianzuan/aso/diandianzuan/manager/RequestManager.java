package com.diandianzuan.aso.diandianzuan.manager;

import com.diandianzuan.aso.diandianzuan.net.RetrofitManager;
import com.diandianzuan.aso.diandianzuan.util.MD5Util;

import java.util.Arrays;
import java.util.Map;


public class RequestManager {
    public static RetrofitManager mRetrofitManager;
    public static final String mBaseUrl = "http://47.92.96.85/";
    public static final String mInterfacePrefix = "index.php?g=App&m=Appv1&a=";

    /**
     * 加密:增加时间戳 MD5签名
     *
     * @param map Map集合
     * @return 加密之后的Map集合
     */
    public static Map<String, String> encryptParams(Map<String, String> map) {
        String timestamp = Long.toString(System.currentTimeMillis()).substring(0, 10);
        map.put("timestamp", timestamp);
        String[] array = new String[map.size()];
        int i = 0;
        for (String key : map.keySet()) {
            array[i] = key;
            i++;
        }
        Arrays.sort(array);
        String signature = "";
        for (int j = 0; j < array.length; j++) {
            String key = array[j];
            if (signature.equals("")) {
                signature = signature + key + "=" + map.get(key);
            } else {
                signature = signature + "&" + key + "=" + map.get(key);
            }
            if (j == array.length - 1) {
                signature = signature + "&nado";
            }
        }
        map.put("sig", MD5Util.getMD5Str(signature));
        return map;
    }

}
