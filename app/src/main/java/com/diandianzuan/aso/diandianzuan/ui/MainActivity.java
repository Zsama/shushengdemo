package com.diandianzuan.aso.diandianzuan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.ll_fast)
    LinearLayout llFast;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.iv_msg)
    ImageView ivMsg;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.ll_tixian)
    LinearLayout llTixian;
    @BindView(R.id.ll_adv)
    LinearLayout llAdv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);

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


    @OnClick(R.id.ll_fast)
    public void onViewClicked() {
        MillionActivity.startMe(mActivity,0);
    }



    @OnClick(R.id.iv_msg)
    public void onIvMsgClicked() {
    }

    @OnClick(R.id.ll_tixian)
    public void onLlTixianClicked() {
        startActivity(new Intent(mActivity,GetMoneyActivity.class));

    }

    @OnClick(R.id.ll_adv)
    public void onLlAdvClicked() {
       MillionActivity.startMe(mActivity,1);
    }
}
