package com.diandianzuan.aso.diandianzuan.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.global.LocalApplication;
import com.diandianzuan.aso.diandianzuan.util.ActivityUtil;
import com.diandianzuan.aso.diandianzuan.util.OSUtil;
import com.diandianzuan.aso.diandianzuan.util.StatusBarUtil;

public abstract class BaseActivity extends AppCompatActivity implements BaseView{
    protected Context mContext;
    protected Activity mActivity;
    protected LocalApplication mApp;
    protected ActivityUtil mActivityUtil;
    protected LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        mActivity = this;
        mApp = LocalApplication.getInstance();
        mInflater = LayoutInflater.from(this);
        mActivityUtil = ActivityUtil.getInstance();
        mActivityUtil.addActivity(this);

        setSystemUi();
        initInstanceState(savedInstanceState);
        beforeSetContentView();
        setContentView(getContentViewId());
        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityUtil.removeActivity(this);
    }

    /**
     * 设置系统界面
     */
    protected void setSystemUi() {

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(mContext, R.color.colorTopBar));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(mContext, R.color.colorTopBar));
        }

        if (OSUtil.getRomType() == OSUtil.ROM_TYPE.MIUI) {
            StatusBarUtil.MIUISetStatusBarLightMode(this.getWindow(), true);
        } else if (OSUtil.getRomType() == OSUtil.ROM_TYPE.FLYME) {
            StatusBarUtil.FlymeSetStatusBarLightMode(this.getWindow(), true);
        }

    }

    /**
     * 初始化保存的数据
     */
    protected void initInstanceState(Bundle savedInstanceState) {

    }

    /**
     * setContentView之前调用
     */
    protected void beforeSetContentView() {

    }

    /**
     * 获取布局Id
     *
     * @return
     */
    protected abstract int getContentViewId();

    /**
     * 通过id初始化View
     *
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T byId(int viewId) {
        return (T) findViewById(viewId);
    }
}
