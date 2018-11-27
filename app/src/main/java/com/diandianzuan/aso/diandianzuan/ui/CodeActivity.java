package com.diandianzuan.aso.diandianzuan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;
import com.diandianzuan.aso.diandianzuan.bean.UserBean;
import com.diandianzuan.aso.diandianzuan.global.Constant;
import com.diandianzuan.aso.diandianzuan.manager.AccountManager;
import com.diandianzuan.aso.diandianzuan.manager.RequestManager;
import com.diandianzuan.aso.diandianzuan.net.RetrofitCallBack;
import com.diandianzuan.aso.diandianzuan.net.RetrofitRequestInterface;
import com.diandianzuan.aso.diandianzuan.util.CommonUtil;
import com.diandianzuan.aso.diandianzuan.util.DialogUtil;
import com.diandianzuan.aso.diandianzuan.util.LogUtil;
import com.diandianzuan.aso.diandianzuan.util.NetworkUtil;
import com.diandianzuan.aso.diandianzuan.util.SPUtil;
import com.diandianzuan.aso.diandianzuan.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CodeActivity extends BaseActivity {
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_code1)
    EditText etCode1;
    @BindView(R.id.et_code2)
    EditText etCode2;
    @BindView(R.id.et_code3)
    EditText etCode3;
    @BindView(R.id.et_code4)
    EditText etCode4;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    private static final String TAG = "CodeActivity";
    private String phone="";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_code;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        Intent intent=getIntent();
        phone=intent.getStringExtra("phone");

        getCode(phone);
    }

    @Override
    public void initEvent() {
        tvPhone.setText(phone);
        etCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etCode1.getText().toString().trim().length()==1){
                    etCode2.requestFocus();
                }
            }
        });
        etCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etCode2.getText().toString().trim().length()==1){
                    etCode3.requestFocus();
                }
            }
        });
        etCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etCode3.getText().toString().trim().length()==1){
                    etCode4.requestFocus();
                }
            }
        });
        etCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etCode4.getText().toString().trim().length()==1){
                   tvLogin.setBackgroundResource(R.mipmap.btn_yellow);
                }
            }
        });
    }

    @OnClick(R.id.iv_close)
    public void onIvCloseClicked() {
        finish();
    }

    @OnClick(R.id.tv_time)
    public void onTvTimeClicked() {

    }

    @OnClick(R.id.tv_login)
    public void onTvLoginClicked() {

//        startActivity(new Intent(mActivity,MainActivity.class));


        String code=etCode1.getText().toString().trim()+etCode2.getText().toString().trim()+etCode3.getText().toString().trim()+etCode4.getText().toString().trim();

        if (TextUtils.isEmpty(code)) {
            ToastUtil.showShort(mContext,"请输入验证码！");
        } else {
            login(phone,code);
        }

    }
    private void login(final String phone, final String codeP) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", codeP);
        map.put("client", "1");//1固定是安卓
        RequestManager.mRetrofitManager
                .createRequest(RetrofitRequestInterface.class)
                .login(RequestManager.encryptParams(map))
                .enqueue(new RetrofitCallBack() {

                    @Override
                    public void onSuccess(String response) {
                        DialogUtil.hideProgress();
                        Log.e(TAG, response + "");
                        try {

                            JSONObject res = new JSONObject(response);
                            int code = res.getInt("code");
                            String info = res.getString("info");


                            if (code==0) {
                                JSONObject data = res.getJSONObject("data");
                                AccountManager.sUserBean = new UserBean();
                                AccountManager.sUserBean.setId(data.getString("id"));
                                AccountManager.sUserBean.setTelNumber(phone);
                                AccountManager.sUserBean.setPassWord("");
                                String userBase64 = CommonUtil.objectToBase64(AccountManager.sUserBean);
                                SPUtil.put(Constant.USER, userBase64);
                                startActivity(new Intent(mActivity,MainActivity.class));
                                finish();
                            } else  {
                                ToastUtil.showShort(mContext, info);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (!NetworkUtil.isConnected()) {
                            ToastUtil.showShort(mContext, "网络未连接");
                        } else {
                            ToastUtil.showShort(mContext, getString(R.string.network_error));
                        }
                    }
                });
    }
    private void getCode(final String phone) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        RequestManager.mRetrofitManager
                .createRequest(RetrofitRequestInterface.class)
                .getCode(RequestManager.encryptParams(map))
                .enqueue(new RetrofitCallBack() {
                    @Override
                    public void onSuccess(String response) {
                        DialogUtil.hideProgress();
                        LogUtil.e(TAG, response);
                        try {
                            JSONObject res = new JSONObject(response);
                            int code = res.getInt("code");
                            String info = res.getString("info");

                            if (code == 0) {
                                ToastUtil.showShort(mContext, info);
                            } else if (code == 1) {
                                ToastUtil.showShort(mContext, info);
                            } else if (code == 2) {
                                ToastUtil.showShort(mContext, info);
                            } else {
                                ToastUtil.showShort(mContext, info);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Throwable t) {
                        DialogUtil.hideProgress();
                        LogUtil.e(TAG, t.getMessage());
                        if (!NetworkUtil.isConnected()) {
                            ToastUtil.showShort(mActivity, "网络未连接");
                        } else {
                            ToastUtil.showShort(mActivity, "请求出错");
                        }
                    }
                });
    }
}

