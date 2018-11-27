package com.diandianzuan.aso.diandianzuan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginNextActivity extends BaseActivity {


    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.im_clear)
    ImageView mImClear;
    @BindView(R.id.tv_next)
    TextView mTvNext;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login_next;
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

    @OnClick(R.id.iv_close)
    public void onMIvCloseClicked() {
        finish();
    }

    @OnClick(R.id.im_clear)
    public void onMImClearClicked() {
        mEtPhone.setText("");
    }

    @OnClick(R.id.tv_next)
    public void onMTvNextClicked() {
      // Intent intent=new Intent(mActivity,) //跳转到验证码页面activity_code
    }
}
