package com.diandianzuan.aso.diandianzuan.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.adapter.recycler.RecyclerCommonAdapter;
import com.diandianzuan.aso.diandianzuan.adapter.recycler.base.ViewHolder;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;
import com.diandianzuan.aso.diandianzuan.bean.ProductBean;
import com.diandianzuan.aso.diandianzuan.global.Constant;
import com.diandianzuan.aso.diandianzuan.manager.AccountManager;
import com.diandianzuan.aso.diandianzuan.manager.RequestManager;
import com.diandianzuan.aso.diandianzuan.net.RetrofitCallBack;
import com.diandianzuan.aso.diandianzuan.net.RetrofitRequestInterface;
import com.diandianzuan.aso.diandianzuan.util.DialogUtil;
import com.diandianzuan.aso.diandianzuan.util.LogUtil;
import com.diandianzuan.aso.diandianzuan.util.NetworkUtil;
import com.diandianzuan.aso.diandianzuan.util.ToastUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

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

public class MillionActivity extends BaseActivity {
    private static final String TAG = "MillionActivity";
    @BindView(R.id.ll_layout_back_top_bar_back)
    LinearLayout llLayoutBackTopBarBack;
    @BindView(R.id.tv_layout_back_top_bar_title)
    TextView tvLayoutBackTopBarTitle;
    @BindView(R.id.tv_layout_back_top_bar_operate)
    TextView tvLayoutBackTopBarOperate;
    @BindView(R.id.rv_activity_million)
    RecyclerView rvActivityMillion;
    @BindView(R.id.trl_activity_million)
    TwinklingRefreshLayout trlActivityMillion;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.tv_describe_top)
    TextView tvDescribeTop;

    private int mType = 0;//0快速 1高级

    private int mDataStatus = STATUS_REFRESH;
    private int mPage = 1;
    private static final int STATUS_REFRESH = 1;
    private static final int STATUS_LOAD = 2;
    private RecyclerCommonAdapter<ProductBean> mStarMainAdapter;
    private List<ProductBean> mDataList = new ArrayList<>();
    @Override
    protected int getContentViewId() {
        return R.layout.activity_million;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mType = intent.getIntExtra("type", 0);
        if (mType == 1) {
            llMain.setBackgroundResource(R.mipmap.bg_blue);
            tvLayoutBackTopBarTitle.setText("高级任务");
            tvDescribeTop.setText("①按要求完成-②截图审核-③奖励到账");
        } else {
            tvLayoutBackTopBarTitle.setText("快速任务");
            tvDescribeTop.setText("①下载应用-②试玩3分钟-③领取奖励");
        }

    }

    @Override
    public void initEvent() {
        getList();
        trlActivityMillion.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mDataStatus = STATUS_REFRESH;
                mPage = 1;
                getList();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                mDataStatus = STATUS_LOAD;
                mPage++;
                getList();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            getList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取任务列表
     */
    private void getList() {
        Map<String, String> map = new HashMap<>();
        if (AccountManager.sUserBean != null) {
            map.put("customer_id", AccountManager.sUserBean.getId());

        }
        map.put("page", mPage+"");
        map.put("type", mType + "");
        map.put("platform", "2");
        LogUtil.e(TAG, map.toString());
        RequestManager.mRetrofitManager
                .createRequest(RetrofitRequestInterface.class)
                .GetTestList(RequestManager.encryptParams(map))
                .enqueue(new RetrofitCallBack() {

                    @Override
                    public void onSuccess(String response) {
                        LogUtil.e(TAG, response);
                        DialogUtil.hideProgress();
                        switch (mDataStatus) {
                            case STATUS_REFRESH:
                                trlActivityMillion.finishRefreshing();
                                break;
                            case STATUS_LOAD:
                                trlActivityMillion.finishLoadmore();
                                break;
                        }
                        try {
                            JSONObject res = new JSONObject(response);
                            int code = res.getInt("code");
                            String info = res.getString("info");
                            if (code == 0) {
                                switch (mDataStatus) {
                                    case STATUS_REFRESH:
                                        mDataList.clear();
                                        break;
                                }

                                JSONArray jsonArrayData = res.getJSONArray("data");
                                for (int i = 0; i < jsonArrayData.length(); i++) {
                                    JSONObject object = jsonArrayData.getJSONObject(i);
                                    ProductBean productBean = new ProductBean();
                                    productBean.setId(object.getString("id"));
                                    productBean.setName(object.getString("name"));
                                    productBean.setmKeywords(object.getString("keywords"));
                                    productBean.setmKucun(object.getInt("count"));
                                    productBean.setPictureUrl(object.getString("image"));
                                    productBean.setPrice(object.getDouble("price"));
                                    productBean.setmMarketType(object.getInt("platform2"));
                                    mDataList.add(productBean);

                                }
                                showDataRecycleView();
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

    private void showDataRecycleView() {
        int layoutId = R.layout.item_star_main_product;


        mStarMainAdapter = new RecyclerCommonAdapter<ProductBean>(mActivity, layoutId, mDataList) {
            @Override
            protected void convert(ViewHolder holder, final ProductBean productBean, final int position) {
                LinearLayout contentLL = holder.getView(R.id.ll_item_star_main_product_content);


                final ImageView pictureIV = holder.getView(R.id.iv_item_star_main_product_picture);
                final ImageView iv_mall_icon = holder.getView(R.id.iv_mall_icon);

                Glide.with(mActivity).load(productBean.getPictureUrl()).asBitmap().placeholder(R.mipmap.logo).centerCrop().into(pictureIV);

                holder.setText(R.id.tv_title, productBean.getmKeywords());
                holder.setText(R.id.tv_describe, "剩余" + productBean.getmKucun() + "份");
                holder.setText(R.id.tv_price, productBean.getPrice() + "");
                int platform2 = productBean.getmMarketType();

                switch (platform2) {
                    case Constant.TX_MARKET:
                        holder.setText(R.id.tv_mall, "腾讯应用宝");
                        iv_mall_icon.setImageResource(R.mipmap.tx);
                        break;
                    case Constant.MI_MARKET:
                        holder.setText(R.id.tv_mall, "小米应用商店");
                        iv_mall_icon.setImageResource(R.mipmap.mi);
                        break;
                    case Constant.OPPO_MARKET:
                        holder.setText(R.id.tv_mall, "OPPO应用市场");
                        iv_mall_icon.setImageResource(R.mipmap.oppo);
                        break;
                    case Constant.HW_MARKET:
                        holder.setText(R.id.tv_mall, "华为应用市场");
                        iv_mall_icon.setImageResource(R.mipmap.huawei);
                        break;
                    case Constant.BAIDU_MARKET:
                        holder.setText(R.id.tv_mall, "百度手机助手");
                        iv_mall_icon.setImageResource(R.mipmap.baidu);
                        break;
                    case Constant.SANLIULING_MARKET:
                        holder.setText(R.id.tv_mall, "360手机助手");
                        iv_mall_icon.setImageResource(R.mipmap.sanliuling);
                        break;

                    case Constant.VIVO_MARKET:
                        holder.setText(R.id.tv_mall, "VIVO应用市场");
                        iv_mall_icon.setImageResource(R.mipmap.vivo);
                        break;
                }

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckTest(productBean.getId(), mType, productBean.getmKeywords());



                    }
                });
            }
        };
        rvActivityMillion.setAdapter(mStarMainAdapter);

        rvActivityMillion.setLayoutManager(new LinearLayoutManager(mActivity));

    }

    @OnClick(R.id.ll_layout_back_top_bar_back)
    public void onViewClicked() {
        finish();
    }


    public static void startMe(Activity mCtx, int type) {
        Intent mIntent = new Intent(mCtx, MillionActivity.class);
        mIntent.putExtra("type", type);
        mCtx.startActivity(mIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    /**
     * 检查任务
     */
    private void CheckTest(final String post_id, final int mType, final String keyword) {
        Map<String, String> map = new HashMap<>();
        if (AccountManager.sUserBean != null) {
            map.put("customer_id", AccountManager.sUserBean.getId());

        }
        map.put("post_id", post_id);
        LogUtil.e(TAG, map.toString());
        RequestManager.mRetrofitManager
                .createRequest(RetrofitRequestInterface.class)
                .CheckTest(RequestManager.encryptParams(map))
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
                                if (mType==0){
                                    MissionDetailActivity.startMe(mActivity, post_id, mType, keyword);
                                }else {
                                    MissionAdvDetailActivity.startMe(mActivity, post_id, mType, keyword);
                                }
                            }else {
                                ToastUtil.showShort(mActivity,info);
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
