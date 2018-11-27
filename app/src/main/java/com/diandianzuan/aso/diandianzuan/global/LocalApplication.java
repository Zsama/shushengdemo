package com.diandianzuan.aso.diandianzuan.global;

import android.app.Application;
import android.content.Context;

import com.diandianzuan.aso.diandianzuan.bean.UserBean;
import com.diandianzuan.aso.diandianzuan.manager.AccountManager;
import com.diandianzuan.aso.diandianzuan.manager.RequestManager;
import com.diandianzuan.aso.diandianzuan.net.RetrofitManager;
import com.diandianzuan.aso.diandianzuan.util.CommonUtil;
import com.diandianzuan.aso.diandianzuan.util.LogUtil;
import com.diandianzuan.aso.diandianzuan.util.SPUtil;


public class LocalApplication extends Application {

    private static LocalApplication sApp;
    private Context mContext;

    private String TAG = "LocalApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        mContext = getApplicationContext();
        initLogUtil();
        initLoginStatus();
        initRetrofit();
    }

    /**
     * 获取MyApplication实例
     */
    public static LocalApplication getInstance() {
        return sApp;
    }
    private void initLoginStatus() { //保存登录状态
        String userBase64 = (String) SPUtil.get(Constant.USER, "");
        if (!"".equals(userBase64)) {
            AccountManager.sUserBean = (UserBean) CommonUtil.base64ToObject(userBase64);
        }
    }

    private void initLogUtil() {
        LogUtil.DEBUG_LEVEL = 0;
    }
    /**
     * 初始Retrofit
     */
    private void initRetrofit() {
        RequestManager.mRetrofitManager = new RetrofitManager.Builder()
                .baseUrl(RequestManager.mBaseUrl)
                .build();
    }
}
