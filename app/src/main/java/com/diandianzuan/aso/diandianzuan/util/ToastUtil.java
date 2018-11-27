package com.diandianzuan.aso.diandianzuan.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 * 不连续弹出Toast
 * 需初始化sContext
 */
public class ToastUtil {

    private static Toast sToast = null;

    /**
     * 短时间显示Toast
     *
     * @param context context
     * @param message 信息
     */
    public static void showShort(Context context, CharSequence message) {
        if (sToast == null) {
            sToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(message);
        }
        sToast.show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context context
     * @param message 信息
     */
    public static void showShort(Context context, int message) {
        if (sToast == null) {
            sToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(message);
        }
        sToast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context context
     * @param message 信息
     */
    public static void showLong(Context context, CharSequence message) {
        if (sToast == null) {
            sToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        } else {
            sToast.setText(message);
        }
        sToast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context context
     * @param message 信息
     */
    public static void showLong(Context context, int message) {
        if (sToast == null) {
            sToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        } else {
            sToast.setText(message);
        }
        sToast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  context
     * @param message  信息
     * @param duration 时长
     */
    public static void showDuration(Context context, CharSequence message, int duration) {
        if (sToast == null) {
            sToast = Toast.makeText(context, message, duration);
        } else {
            sToast.setText(message);
        }
        sToast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  context
     * @param message  信息
     * @param duration 时长
     */
    public static void showDuration(Context context, int message, int duration) {
        if (sToast == null) {
            sToast = Toast.makeText(context, message, duration);
        } else {
            sToast.setText(message);
        }
        sToast.show();
    }

}
