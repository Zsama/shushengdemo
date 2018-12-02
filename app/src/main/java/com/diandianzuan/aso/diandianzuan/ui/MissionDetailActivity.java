package com.diandianzuan.aso.diandianzuan.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;
import com.diandianzuan.aso.diandianzuan.manager.AccountManager;
import com.diandianzuan.aso.diandianzuan.manager.RequestManager;
import com.diandianzuan.aso.diandianzuan.net.RetrofitCallBack;
import com.diandianzuan.aso.diandianzuan.net.RetrofitRequestInterface;
import com.diandianzuan.aso.diandianzuan.util.CommonUtil;
import com.diandianzuan.aso.diandianzuan.util.DialogUtil;
import com.diandianzuan.aso.diandianzuan.util.LogUtil;
import com.diandianzuan.aso.diandianzuan.util.NetworkUtil;
import com.diandianzuan.aso.diandianzuan.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
    @BindView(R.id.tv_left_time)
    TextView mTvLeftTime;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_words)
    TextView mTvWords;
    @BindView(R.id.tv_mall_name)
    TextView mTvMallName;
    @BindView(R.id.tv_open)
    TextView mTvOpen;
    @BindView(R.id.tv_get)
    TextView mTvGet;
    private String mKeywords = "";
    private String mId="";
    private String bundled="";
    private static final String TAG = "MissionDetailActivity";
    private boolean ifHavePkg=false;
    private boolean ifHaveTime=false;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mission_detail;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        tvLayoutBackTopBarTitle.setText("任务详情");
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mKeywords = intent.getStringExtra("keywords");
        mId=intent.getStringExtra("projectId");


    }

    @Override
    public void initEvent() {
        GetTestDetail();
    }

    public static void startMe(Activity mCtx, String projectId, int type, String keywords) {
        Intent mIntent = new Intent(mCtx, MissionDetailActivity.class);
        mIntent.putExtra("projectId", projectId);
        mIntent.putExtra("type", type);
        mIntent.putExtra("keywords", keywords);
        mCtx.startActivity(mIntent);
    }


    @OnClick(R.id.ll_layout_back_top_bar_back)
    public void onLlLayoutBackTopBarBackClicked() {
        finish();
    }

    @OnClick(R.id.ll_goto)
    public void onLlGotoClicked() {
        CommonUtil.jumpToMarketSearch(mActivity, mKeywords, "com.tencent.android.qqdownloader");
    }



    @OnClick(R.id.tv_open)
    public void onMTvOpenClicked() {

        if (ifHavePkg){
            PackageManager packageManager = getPackageManager();
            if (checkPackInfo(bundled)) {
                Intent intent = packageManager.getLaunchIntentForPackage(bundled);
                startActivity(intent);


                Timer timer = new Timer();
                timer.schedule(mTimerTask, 180000);
            } else {
                Toast.makeText(mActivity, "没有安装" + bundled, 1).show();
            }

        }

    }

    @OnClick(R.id.tv_get)
    public void onMTvGetClicked() {
        if (ifHaveTime){

        }
    }

    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
          ifHaveTime=true;
          refreshView();


        }
    };


    /**
     * 获取个人信息
     */
    private void GetTestDetail() {
        Map<String, String> map = new HashMap<>();
        if (AccountManager.sUserBean != null) {
            map.put("customer_id", AccountManager.sUserBean.getId());

        }
        map.put("keywords", mKeywords);
        map.put("post_id", mId);
        RequestManager.mRetrofitManager
                .createRequest(RetrofitRequestInterface.class)
                .GetTestDetail(RequestManager.encryptParams(map))
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
                                bundled=data.getString("bundled");
                                mTvPrice.setText("+"+data.getString("price")+"元");




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
    private void refreshView(){
        if ( ifHavePkg){
            mTvOpen.setBackgroundResource(R.mipmap.download_btn);
            mTvOpen.setTextColor(getResources().getColor(R.color.colorBlack));

        }else {
            mTvOpen.setBackgroundResource(R.mipmap.btn_gray);
            mTvOpen.setTextColor(getResources().getColor(R.color.colorWhite));

        }



        if (ifHaveTime){

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!bundled.equals("")){
            ifHavePkg=checkPackInfo(bundled);
            refreshView();



        }
    }

    /**
     * 检查包是否存在
     *
     * @param packname
     * @return
     */
    private boolean checkPackInfo(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }
}
