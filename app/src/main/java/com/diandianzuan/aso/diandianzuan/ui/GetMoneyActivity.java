package com.diandianzuan.aso.diandianzuan.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetMoneyActivity extends BaseActivity {

    @BindView(R.id.ll_layout_back_top_bar_back)
    LinearLayout llLayoutBackTopBarBack;
    @BindView(R.id.tv_layout_back_top_bar_title)
    TextView tvLayoutBackTopBarTitle;
    @BindView(R.id.tv_layout_back_top_bar_operate)
    TextView tvLayoutBackTopBarOperate;
    @BindView(R.id.et_count)
    EditText etCount;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_get_money;
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

    @OnClick(R.id.ll_layout_back_top_bar_back)
    public void onLlLayoutBackTopBarBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_submit)
    public void onTvSubmitClicked() {
    }
}
