package com.diandianzuan.aso.diandianzuan.ui;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;
import com.diandianzuan.aso.diandianzuan.manager.AccountManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartPageActivity extends BaseActivity {
    @BindView(R.id.tv_start)
    TextView tvStart;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_start_page;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

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
