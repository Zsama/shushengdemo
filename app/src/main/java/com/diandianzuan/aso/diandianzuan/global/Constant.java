package com.diandianzuan.aso.diandianzuan.global;

import android.os.Environment;


import java.io.File;

public class Constant {
    public static final int TX_MARKET=1;
    public static final int MI_MARKET=2;
    public static final int OPPO_MARKET=3;
    public static final int HW_MARKET=4;
    public static final int BAIDU_MARKET=5;
    public static final int SANLIULING_MARKET=6;
    public static final int DEMO_TIME=180000;

    /**
     * SharePreferences文件名
     */
    public static final String SP_NAME = "FoBenYuan";
    /**
     * 用户信息
     */
    public static final String USER = "user";
    /**
     * 新旧用户
     */
    public static final String FIRST_STATUS = "first_status";



    /**
     * SDCard绝对路径
     */
    public static final String SDCARD_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    /**
     * SDCard文件夹
     */
    public static final String SDCARD_PATH = SDCARD_ROOT_PATH + File.separator + "you_you_mei";
}
