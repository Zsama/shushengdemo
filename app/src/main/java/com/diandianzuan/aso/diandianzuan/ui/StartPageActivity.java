package com.diandianzuan.aso.diandianzuan.ui;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.diandianzuan.aso.diandianzuan.BuildConfig;
import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;
import com.diandianzuan.aso.diandianzuan.manager.AccountManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartPageActivity extends BaseActivity {
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_start_page;
    }

    @Override
    public void initView() {
        tvVersion=findViewById(R.id.tv_version);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        tvVersion.setText(BuildConfig.VERSION_NAME);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_start)
    public void onViewClicked() {
        if (AccountManager.sUserBean==null){
            startActivity(new Intent( mActivity,LoginActivity.class));
            finish();
        }else {
            startActivity(new Intent( mActivity,MainActivity.class));
            finish();
        }


    }
}
