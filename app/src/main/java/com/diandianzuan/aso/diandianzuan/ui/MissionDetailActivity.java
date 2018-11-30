package com.diandianzuan.aso.diandianzuan.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;
import com.diandianzuan.aso.diandianzuan.util.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MissionDetailActivity extends BaseActivity {


    @BindView(R.id.ll_layout_back_top_bar_back)
    LinearLayout llLayoutBackTopBarBack;
    @BindView(R.id.tv_layout_back_top_bar_title)
    TextView tvLayoutBackTopBarTitle;
    @BindView(R.id.tv_layout_back_top_bar_operate)
    TextView tvLayoutBackTopBarOperate;
    @BindView(R.id.ll_goto)
    TextView llGoto;
    private String mKeywords="";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mission_detail;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        Intent intent=getIntent();
        mKeywords=intent.getStringExtra("keywords");


    }

    @Override
    public void initEvent() {

    }

    public static void startMe(Activity mCtx, String projectId, int type,String keywords) {
        Intent mIntent = new Intent(mCtx, MissionDetailActivity.class);
        mIntent.putExtra("projectId", projectId);
        mIntent.putExtra("type", type);
        mIntent.putExtra("keywords", keywords);
        mCtx.startActivity(mIntent);
    }



    @OnClick(R.id.ll_layout_back_top_bar_back)
    public void onLlLayoutBackTopBarBackClicked() {
    }

    @OnClick(R.id.ll_goto)
    public void onLlGotoClicked() {
        CommonUtil.jumpToMarketSearch(mActivity,mKeywords,"com.tencent.android.qqdownloader");
    }
}
