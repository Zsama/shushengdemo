package com.diandianzuan.aso.diandianzuan.global;

import android.os.Environment;


import java.io.File;

public class Constant {
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
