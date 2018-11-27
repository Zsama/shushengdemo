package com.diandianzuan.aso.diandianzuan.ui;

import android.app.Activity;
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

public class UserCenterActivity extends BaseActivity {
    private TextView mTvExit;
    private LinearLayout mLLBack;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_center;
    }

    @Override
    public void initView() {
        mTvExit=byId(R.id.tv_exit);
        mLLBack=byId(R.id.ll_layout_back_top_bar_back);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        mTvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity,LoginActivity.class));
            }
        });
        mLLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public static void startMe(Activity mActivity) {
        Intent mIntent = new Intent(mActivity, UserCenterActivity.class);
        mActivity.startActivity(mIntent);
    }


}
