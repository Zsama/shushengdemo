package com.diandianzuan.aso.diandianzuan.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diandianzuan.aso.diandianzuan.BuildConfig;
import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;
import com.diandianzuan.aso.diandianzuan.bean.BannerItemBean;
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
import com.diandianzuan.aso.diandianzuan.widget.BannerLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
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
    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    @BindView(R.id.bl_fragment_homepage)
    BannerLayout mBannerLayout;
    private List<String> mBannerImageList = new ArrayList<>();
    private List<BannerItemBean> mBannerItemList = new ArrayList<>();
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
        setUserInfo();

    }

    @Override
    public void initEvent() {
        getUserInfo();
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserCenterActivity.startMe(mActivity);
            }
        });
        checkVersion();
        GetHome();

    }
    /**
     * 显示
     */
    private void showBanner() {
        mBannerLayout.setViewUrls(mBannerImageList);

        mBannerLayout.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent=new Intent(mActivity,ActDetailActivity.class);
                intent.putExtra("id",mBannerItemList.get(position).getProductId());
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        try {
            getUserInfo();
        } catch (Exception e) {

        }
    }

    @OnClick(R.id.ll_fast)
    public void onViewClicked() {
        MillionActivity.startMe(mActivity, 0);
    }


    @OnClick(R.id.iv_msg)
    public void onIvMsgClicked() {
    }

    @OnClick(R.id.ll_tixian)
    public void onLlTixianClicked() {
        startActivity(new Intent(mActivity, GetMoneyActivity.class));

    }

    @OnClick(R.id.ll_adv)
    public void onLlAdvClicked() {
        MillionActivity.startMe(mActivity, 1);
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

    private void setUserInfo() {
        try {
            Glide.with(mActivity)
                    .load(AccountManager.sUserBean.getHeadPortrait())
                    .bitmapTransform(new CropCircleTransformation(mActivity)).placeholder(R.mipmap.center)
                    .into(ivAvatar);
//            Glide.with(mActivity).load(AccountManager.sUserBean.getHeadPortrait()).asBitmap().placeholder(R.mipmap.logo).centerCrop().into(ivAvatar);
//            Glide.with(mActivity)
//                    .load(AccountManager.sUserBean.getHeadPortrait())
//                    .bitmapTransform(new CropCircleTransformation(mActivity))
//                    .into(ivAvatar);
            tvMoney.setText(AccountManager.sUserBean.getEmoney());
            tvDescribe.setText("今日收益：" + AccountManager.sUserBean.getToday_money() + "  累计收益：" + AccountManager.sUserBean.getTotal_money());
        } catch (Exception e) {

        }


    }


    private void checkVersion() {
        Map<String, String> map = new HashMap<>();

        RequestManager.mRetrofitManager
                .createRequest(RetrofitRequestInterface.class)
                .CheckVersion(RequestManager.encryptParams(map))
                .enqueue(new RetrofitCallBack() {

                    @Override
                    public void onSuccess(String response) {
                        LogUtil.e(TAG, response);
                        DialogUtil.hideProgress();
                        try {
                            JSONObject res = new JSONObject(response);
                            int code = res.getInt("code");
                            int version = res.getInt("version");
                            if (version > BuildConfig.VERSION_CODE) {
                                ToastUtil.showShort(mActivity, "发现新版本前往下载！");
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri content_url = Uri.parse(res.getString("data"));
                                intent.setData(content_url);
                                startActivity(intent);
                                finish();

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

    private void GetHome() {
        Map<String, String> map = new HashMap<>();

        RequestManager.mRetrofitManager
                .createRequest(RetrofitRequestInterface.class)
                .GetHome(RequestManager.encryptParams(map))
                .enqueue(new RetrofitCallBack() {

                    @Override
                    public void onSuccess(String response) {
                        LogUtil.e(TAG, response);
                        DialogUtil.hideProgress();
                        try {
                            JSONObject res = new JSONObject(response);
                            int code = res.getInt("code");
                            JSONObject data=res.getJSONObject("data");
                            JSONArray BussinessData=data.getJSONArray("discount");
                            for (int i = 0; i < BussinessData.length(); i++) {
                                JSONObject item = BussinessData.getJSONObject(i);
                                BannerItemBean bannerItemBean = new BannerItemBean();
                                bannerItemBean.setProductId(item.getString("id"));

                                bannerItemBean.setImageUrl( item.getString("image"));
                                mBannerImageList.add(item.getString("image"));
                                mBannerItemList.add(bannerItemBean);
                            }

                            showBanner();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
