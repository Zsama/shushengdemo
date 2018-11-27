package com.diandianzuan.aso.diandianzuan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.ll_fast)
    LinearLayout llFast;
    private ImageView ivAvatar;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        ivAvatar = byId(R.id.iv_avatar);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserCenterActivity.startMe(mActivity);
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick(R.id.ll_fast)
    public void onViewClicked() {
        Intent intent=new Intent(mActivity,MillionActivity.class);
        startActivity(intent);
    }
}
