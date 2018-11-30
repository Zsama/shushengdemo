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
import com.diandianzuan.aso.diandianzuan.manager.AccountManager;
import com.diandianzuan.aso.diandianzuan.manager.RequestManager;
import com.diandianzuan.aso.diandianzuan.net.RetrofitCallBack;
import com.diandianzuan.aso.diandianzuan.net.RetrofitRequestInterface;
import com.diandianzuan.aso.diandianzuan.util.DialogUtil;
import com.diandianzuan.aso.diandianzuan.util.LogUtil;
import com.diandianzuan.aso.diandianzuan.util.NetworkUtil;
import com.diandianzuan.aso.diandianzuan.util.ToastUtil;
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
        if (mType==1){
            llMain.setBackgroundResource(R.mipmap.bg_blue);
        }

    }

    @Override
    public void initEvent() {
        getList();
    }


    /**
     * 获取任务列表
     */
    private void getList() {
        Map<String, String> map = new HashMap<>();
        if (AccountManager.sUserBean != null) {
            map.put("customer_id", AccountManager.sUserBean.getId());

        }
        map.put("page", "1");
        map.put("type", "0");
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
                        try {
                            JSONObject res = new JSONObject(response);
                            int code = res.getInt("code");
                            String info = res.getString("info");
                            if (code == 0) {
                                mDataList.clear();
                                JSONArray jsonArrayData = res.getJSONArray("data");
                                for (int i = 0; i < jsonArrayData.length(); i++) {
                                    JSONObject object = jsonArrayData.getJSONObject(i);
                                    ProductBean productBean = new ProductBean();
                                    productBean.setId(object.getString("id"));
                                    productBean.setName(object.getString("name"));
                                    productBean.setmKeywords(object.getString("keywords"));
                                    productBean.setmKucun(object.getInt("count"));
                                    productBean.setPictureUrl(object.getString("image"));
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

                Glide.with(mActivity).load(productBean.getPictureUrl()).asBitmap().placeholder(R.mipmap.logo).centerCrop().into(pictureIV);

                holder.setText(R.id.tv_title, productBean.getmKeywords());
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MissionDetailActivity.startMe(mActivity, productBean.getId(), mType,productBean.getmKeywords());

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
}
