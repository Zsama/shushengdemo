package com.diandianzuan.aso.diandianzuan.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;
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
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class UserCenterActivity extends BaseActivity {
    @BindView(R.id.tv_layout_back_top_bar_title)
    TextView tvLayoutBackTopBarTitle;
    @BindView(R.id.tv_layout_back_top_bar_operate)
    TextView tvLayoutBackTopBarOperate;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_nicename)
    TextView tvNicename;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.ll_tixian)
    LinearLayout llTixian;
    @BindView(R.id.ll_cash_detail)
    LinearLayout llCashDetail;
    @BindView(R.id.ll_order_record)
    LinearLayout llOrderRecord;
    @BindView(R.id.ll_about_us)
    LinearLayout llAboutUs;
    private TextView mTvExit;
    private LinearLayout mLLBack;
    private static final String TAG = "UserCenterActivity";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_center;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        mTvExit = byId(R.id.tv_exit);
        mLLBack = byId(R.id.ll_layout_back_top_bar_back);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            getUserInfo();
        }catch (Exception e){

        }
    }

    @Override
    public void initEvent() {
        mTvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AccountManager.logout();
                ToastUtil.showShort(mActivity, "已退出登录");
                finish();
                startActivity(new Intent(mActivity, LoginActivity.class));


            }
        });
        mLLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getUserInfo();
    }

    public static void startMe(Activity mActivity) {


        Intent mIntent = new Intent(mActivity, UserCenterActivity.class);
        mActivity.startActivity(mIntent);
    }




    @OnClick(R.id.iv_avatar)
    public void onIvAvatarClicked() {
    }

    @OnClick(R.id.tv_nicename)
    public void onTvNicenameClicked() {
        startActivity(new Intent(mActivity,PersonInfoActivity.class));
    }

    @OnClick(R.id.ll_tixian)
    public void onLlTixianClicked() {
        startActivity(new Intent(mActivity,GetMoneyActivity.class));
    }

    @OnClick(R.id.ll_cash_detail)
    public void onLlCashDetailClicked() {
        PayRecordActivity.startMe(mActivity);
    }

    @OnClick(R.id.ll_order_record)
    public void onLlOrderRecordClicked() {
        MissionRecordActivity.startMe(mActivity);
    }

    @OnClick(R.id.ll_about_us)
    public void onLlAboutUsClicked() {
    }


    /**
     * 获取个人信息
     */
    private void getUserInfo() {
        Map<String, String> map = new HashMap<>();
        if (AccountManager.sUserBean != null) {
            map.put("customer_id", AccountManager.sUserBean.getId());
        }
        RequestManager.mRetrofitManager
                .createRequest(RetrofitRequestInterface.class)
                .GetUserinfo(RequestManager.encryptParams(map))
                .enqueue(new RetrofitCallBack() {

                    @Override
                    public void onSuccess(String response) {
                        LogUtil.e(TAG, response);
                        DialogUtil.hideProgress();
                        try {
                            JSONObject res = new JSONObject(response);
                            int code = res.getInt("code");
                            String info = res.getString("info");
                            if (code == 0) {
                                JSONObject data = res.getJSONObject("data");

                                String nicename=data.getString("nicename");
                                if (nicename.contains("用户")){
                                    nicename="试玩新手";
                                }
                                AccountManager.sUserBean.setNickName(nicename);
//                                AccountManager.sUserBean.setNickName(data.getString("nicename"));
                                AccountManager.sUserBean.setHeadPortrait(data.getString("avatar"));

                                    AccountManager.sUserBean.setSex(data.getInt("sex"));


                                AccountManager.sUserBean.setId(data.getString("id"));
                                AccountManager.sUserBean.setTelNumber(data.getString("phone"));
                                AccountManager.sUserBean.setEmoney(data.getString("money"));
                                AccountManager.sUserBean.setmFreezmoney(data.getString("freezing_money"));
                                AccountManager.sUserBean.setmFreemoney(data.getString("tixian_money"));
                                AccountManager.sUserBean.setmAliName(data.getString("ali_login"));
                                AccountManager.sUserBean.setRealName(data.getString("real_name"));
                                AccountManager.sUserBean.setToday_money(data.getString("today_money"));
                                AccountManager.sUserBean.setTotal_money(data.getString("total_money"));



                                String base64 = CommonUtil.objectToBase64(AccountManager.sUserBean);
                                SPUtil.put(Constant.USER, base64);
                                setUserInfo();

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
    private void setUserInfo(){

        Glide.with(mActivity)
                .load(AccountManager.sUserBean.getHeadPortrait())
                .bitmapTransform(new CropCircleTransformation(mActivity))
                .into(ivAvatar);

//        Glide.with(mActivity)
//                .load(AccountManager.sUserBean.getHeadPortrait())
//                .bitmapTransform(new CropCircleTransformation(mActivity))
//                .into(ivAvatar);
        tvMoney.setText(AccountManager.sUserBean.getEmoney());
        tvNicename.setText(AccountManager.sUserBean.getNickName());
        tvId.setText("ID:"+AccountManager.sUserBean.getId());
    }



}
