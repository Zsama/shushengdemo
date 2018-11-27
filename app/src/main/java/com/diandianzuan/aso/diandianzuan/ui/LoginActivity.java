package com.diandianzuan.aso.diandianzuan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;
import com.diandianzuan.aso.diandianzuan.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.tv_next)
    TextView mTvNext;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
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

    @OnClick(R.id.tv_next)
    public void onMTvNextClicked() {
        if (!mEtPhone.getText().equals("")){
            Intent intent=new Intent(LoginActivity.this,CodeActivity.class);
            intent.putExtra("phone",mEtPhone.getText().toString().trim());
            startActivity(intent);
        }


    }
}
