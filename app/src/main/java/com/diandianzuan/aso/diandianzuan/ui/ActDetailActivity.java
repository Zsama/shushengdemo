package com.diandianzuan.aso.diandianzuan.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;
import com.diandianzuan.aso.diandianzuan.bean.BannerItemBean;
import com.diandianzuan.aso.diandianzuan.manager.AccountManager;
import com.diandianzuan.aso.diandianzuan.manager.RequestManager;
import com.diandianzuan.aso.diandianzuan.net.RetrofitCallBack;
import com.diandianzuan.aso.diandianzuan.net.RetrofitRequestInterface;
import com.diandianzuan.aso.diandianzuan.util.DialogUtil;
import com.diandianzuan.aso.diandianzuan.util.LogUtil;
import com.diandianzuan.aso.diandianzuan.util.NetworkUtil;
import com.diandianzuan.aso.diandianzuan.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActDetailActivity extends BaseActivity {

    private static final String TAG = "ActDetailActivity";
    @BindView(R.id.ll_layout_back_top_bar_back)
    LinearLayout llLayoutBackTopBarBack;
    @BindView(R.id.tv_layout_back_top_bar_title)
    TextView tvLayoutBackTopBarTitle;
    @BindView(R.id.tv_layout_back_top_bar_operate)
    TextView tvLayoutBackTopBarOperate;
    @BindView(R.id.wv_web)
    WebView wvWeb;
    @BindView(R.id.tv_goto)
    TextView tvGoto;
    private String mId;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_act_detail;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        tvLayoutBackTopBarTitle.setText("活动详情");
    }

    @Override
    public void initData() {
        Intent intent=getIntent();
        mId=intent.getStringExtra("id");



    }

    @Override
    public void initEvent() {
        getData();
        tvGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://down.lt.zqgame.com/ltzr109006964371.apk");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
    }


    @OnClick(R.id.ll_layout_back_top_bar_back)
    public void onLlLayoutBackTopBarBackClicked() {
    }

    @OnClick(R.id.tv_goto)
    public void onTvGotoClicked() {
    }


    private void getData() {
        Map<String, String> map = new HashMap<>();
        if (AccountManager.sUserBean != null) {
            map.put("customer_id", AccountManager.sUserBean.getId());

        }
        map.put("discount_id", mId);
        RequestManager.mRetrofitManager
                .createRequest(RetrofitRequestInterface.class)
                .GetDiscountDetail(RequestManager.encryptParams(map))
                .enqueue(new RetrofitCallBack() {

                    @Override
                    public void onSuccess(String response) {
                        LogUtil.e(TAG, response);
                        DialogUtil.hideProgress();
                        try {
                            JSONObject res = new JSONObject(response);
                            int code = res.getInt("code");
                            if (code==0){
                                JSONObject data=res.getJSONObject("data");
                                String title=data.getString("title");
                                if (title.equals("裂天守梦，魔幻冒险一触即发")){
                                    tvGoto.setVisibility(View.VISIBLE);
                                }else {
                                    tvGoto.setVisibility(View.GONE);
                                }




                                String wvdata = data.getString("info");
                                String customHtml = replace("&lt;", "<", wvdata);
                                customHtml = replace("&gt;", ">", customHtml);
                                customHtml = replace("&quot;", "\"", customHtml);
                                customHtml = replace("&amp;nbsp;", "  ", customHtml);
                                customHtml = replace(".jpg\"/>", ".jpg\" style=\"max-width:100%;height:auto\"/>", customHtml);
                                customHtml = replace(".png\"/>", ".png\" style=\"max-width:100%;height:auto\"/>", customHtml);
                                customHtml = replace("style=\"\"", "style=\"max-width:100%;height:auto\"", customHtml);

                                wvWeb.loadDataWithBaseURL(null, customHtml, "text/html", "utf-8", null);
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
    public static String replace(String from, String to, String source) {
        if (source == null || from == null || to == null)
            return null;
        StringBuffer bf = new StringBuffer("");
        int index = -1;


        while ((index = source.indexOf(from)) != -1) {
            bf.append(source.substring(0, index) + to);
            source = source.substring(index + from.length());
            index = source.indexOf(from);
        }
        bf.append(source);
        return bf.toString();
    }
}
