package com.diandianzuan.aso.diandianzuan.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.List;
import java.util.Stack;

/**
 * Activity堆栈工具类
 */
public class ActivityUtil {

    /**
     * 单一实例
     */
    private static ActivityUtil sActivityUtil;
    /**
     * Activity堆栈 Stack:线程安全
     */
    private Stack<Activity> mActivityStack = new Stack<>();

    /**
     * 私有构造器 无法外部创建
     */
    private ActivityUtil() {
    }

    /**
     * 获取单一实例 双重锁定
     *
     * @return this
     */
    public static ActivityUtil getInstance() {
        if (sActivityUtil == null) {
            synchronized (ActivityUtil.class) {
                if (sActivityUtil == null) {
                    sActivityUtil = new ActivityUtil();
                }
            }
        }
        return sActivityUtil;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        mActivityStack.add(activity);
    }

    /**
     * 移除堆栈中的Activity
     *
     * @param activity Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null && mActivityStack.contains(activity)) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 获取当前Activity (堆栈中最后一个添加的)
     *
     * @return Activity
     */
    public Activity getCurrentActivity() {
        return mActivityStack.lastElement();
    }

    /**
     * 获取指定类名的Activity
     */
    public Activity getActivity(Class<?> cls) {
        if (mActivityStack != null)
            for (Activity activity : mActivityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    /**
     * 结束当前Activity (堆栈中最后一个添加的)
     */
    public void finishCurrentActivity() {
        Activity activity = mActivityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && mActivityStack.contains(activity)) {
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     *
     * @param clazz Activity.class
     */
    public void finishActivity(Class<?> clazz) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(clazz)) {
                finishActivity(activity);
                break;
            }
        }
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (mActivityStack.get(i) != null) {
                finishActivity(mActivityStack.get(i));
            }
        }
        mActivityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        try {
            finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        } /*finally {
            System.exit(0);
        }*/
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
            }
        }
        return false;
    }


    /**
     * 是否在后台
     *
     * @param context context
     * @return if background return true
     */
    public boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
            }
        }
        return false;
    }

}
