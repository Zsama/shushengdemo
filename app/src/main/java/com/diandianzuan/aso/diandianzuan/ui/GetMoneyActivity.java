package com.diandianzuan.aso.diandianzuan.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;
import com.diandianzuan.aso.diandianzuan.manager.AccountManager;
import com.diandianzuan.aso.diandianzuan.manager.RequestManager;
import com.diandianzuan.aso.diandianzuan.net.RetrofitCallBack;
import com.diandianzuan.aso.diandianzuan.net.RetrofitRequestInterface;
import com.diandianzuan.aso.diandianzuan.util.DialogUtil;
import com.diandianzuan.aso.diandianzuan.util.LogUtil;
import com.diandianzuan.aso.diandianzuan.util.NetworkUtil;
import com.diandianzuan.aso.diandianzuan.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    private static final String TAG = "GetMoneyActivity";
    @Override
    protected int getContentViewId() {
        return R.layout.activity_get_money;
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

    }


    @OnClick(R.id.ll_layout_back_top_bar_back)
    public void onLlLayoutBackTopBarBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_submit)
    public void onTvSubmitClicked() {
        int money=Integer.valueOf(etMoney.getText().toString().trim());
        if (money>=10&&money%10==0){
            PutMoney();
        }else {
            ToastUtil.showShort(mActivity,"未满足提现要求！");
        }
    }
    private void PutMoney() {
        Map<String, String> map = new HashMap<>();
        if (AccountManager.sUserBean != null) {
            map.put("customer_id", AccountManager.sUserBean.getId());

        }
        map.put("money", etMoney.getText().toString().trim());
        map.put("ali_login", etCount.getText().toString().trim());
        map.put("real_name", etName.getText().toString().trim());
        LogUtil.e(TAG, map.toString());
        RequestManager.mRetrofitManager
                .createRequest(RetrofitRequestInterface.class)
                .PutMoney(RequestManager.encryptParams(map))
                .enqueue(new RetrofitCallBack() {

                    @Override
                    public void onSuccess(String response) {
                        LogUtil.e(TAG, response);
                        DialogUtil.hideProgress();
                        try {
                            JSONObject res = new JSONObject(response);
                            int code = res.getInt("code");
                            String info = res.getString("info");
                            ToastUtil.showShort(mActivity,info);
                            if (code == 0) {
                               finish();
                            }else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtil.showShort(mActivity, "数据异常");
                            LogUtil.e(TAG, e.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        if (!NetworkUtil.isConnected()) {
                            ToastUtil.showShort(mActivity, "网络未连接");
                        } else {
                            ToastUtil.showShort(mActivity, getString(R.string.network_error));
                        }
                    }
                });


    }

}
