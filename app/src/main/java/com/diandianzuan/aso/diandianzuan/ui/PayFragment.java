package com.diandianzuan.aso.diandianzuan.ui;

import android.support.v4.content.ContextCompat;
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
import com.diandianzuan.aso.diandianzuan.base.BaseFragment;
import com.diandianzuan.aso.diandianzuan.bean.ProductBean;
import com.diandianzuan.aso.diandianzuan.manager.AccountManager;
import com.diandianzuan.aso.diandianzuan.manager.RequestManager;
import com.diandianzuan.aso.diandianzuan.net.RetrofitCallBack;
import com.diandianzuan.aso.diandianzuan.net.RetrofitRequestInterface;
import com.diandianzuan.aso.diandianzuan.util.DialogUtil;
import com.diandianzuan.aso.diandianzuan.util.DisplayUtil;
import com.diandianzuan.aso.diandianzuan.util.LogUtil;
import com.diandianzuan.aso.diandianzuan.util.NetworkUtil;
import com.diandianzuan.aso.diandianzuan.util.ToastUtil;
import com.diandianzuan.aso.diandianzuan.widget.CustomerRefreshView;
import com.diandianzuan.aso.diandianzuan.widget.DividerItemDecoration;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayFragment extends BaseFragment {
    private TwinklingRefreshLayout mRefreshLayout;
    private RecyclerView mOrderRV;
    private RecyclerCommonAdapter<ProductBean> mOrderAdapter;
    private List<ProductBean> mOrderList = new ArrayList<>();

    private int mDataStatus = STATUS_REFRESH;
    private int mPage = 1;
    private static final int STATUS_REFRESH = 1;
    private static final int STATUS_LOAD = 2;

    private int mType;

    public static final int TYPE_ALL = -1; //全部
    public static final int TYPE_PAY_IN= 1; //收入

    public static final int TYPE_PAY_OUT = 2;//支出


    private View mEmptyView;

    private static final String TAG = "PayFragment";
    @Override
    public void initView() {
        mRefreshLayout = byId(R.id.tfl_fragment_order);
//        setRefreshStyle();
        mOrderRV = byId(R.id.rv_fragment_order);

        mEmptyView = byId(R.id.layout_order_empty);
    }

    @Override
    public void initData() {
        mType = getArguments().getInt("type");

//        createTestData();

        getData();
    }


    @Override
    protected int getInflateViewId() {
        return R.layout.fragment_pay;
    }

    @Override
    public void initEvent() {
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mDataStatus = STATUS_REFRESH;
                mPage = 1;
                getData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                mDataStatus = STATUS_LOAD;
                mPage++;
                getData();
            }
        });
    }

    private void setRefreshStyle() {
        CustomerRefreshView headerView = new CustomerRefreshView(mActivity);
        BallPulseView loadingView = new BallPulseView(mActivity);
        loadingView.setAnimatingColor(ContextCompat.getColor(mActivity, R.color.colorPink));
        mRefreshLayout.setHeaderView(headerView);
        mRefreshLayout.setBottomView(loadingView);
    }



    private void showDataRecycleView() {

        if (mOrderAdapter == null) {
            mOrderAdapter = new RecyclerCommonAdapter<ProductBean>(mActivity, R.layout.item_order_pay, mOrderList) {
                @Override
                protected void convert(ViewHolder holder, final ProductBean productBean, int position) {
                    final ImageView pictureIV = holder.getView(R.id.iv_item_star_main_product_picture);
                    final TextView tv_plus = holder.getView(R.id.tv_plus);
                    final TextView tv_yuan = holder.getView(R.id.tv_yuan);
                    final TextView tv_price = holder.getView(R.id.tv_price);

                    Glide.with(mActivity).load(productBean.getPictureUrl()).asBitmap().placeholder(R.mipmap.logo).centerCrop().into(pictureIV);
//
                    holder.setText(R.id.tv_time, productBean.getEndTime());
                    String status="完成快速任务奖励";
                    switch (productBean.getChildOrderStatus()){

                        case 1:
                            status="完成快速任务奖励";
                            tv_plus.setText("+");
                            break;
                        case 2:
                            tv_plus.setText("+");
                            status="完成高级任务奖励";
                            break;
                        case 3:
                            tv_plus.setText("-");
                            status="提现";
                            break;
                    }
//
                    holder.setText(R.id.tv_title,status);
                    tv_price.setText(productBean.getPrice()+"");
                    holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                        }
                    });

                }
            };
            mOrderRV.setLayoutManager(new LinearLayoutManager(mActivity));
            mOrderRV.addItemDecoration(new DividerItemDecoration(mActivity,
                    DividerItemDecoration.VERTICAL_LIST,
                    (int) DisplayUtil.dpToPx(mActivity, 8f),
                    ContextCompat.getColor(mActivity, R.color.colorGray5)));
            mOrderRV.setAdapter(mOrderAdapter);
        } else {
            mOrderAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取订单数据
     */
    private void getData() {
        Map<String, String> map = new HashMap<>();
        if (AccountManager.sUserBean != null) {
            map.put("customer_id", AccountManager.sUserBean.getId());
        }
        if (mType==-1){
            map.put("type",  "");
        }else {
            map.put("type", mType + "");
        }

        map.put("page", mPage + "");
        LogUtil.d(TAG,map.toString());
        RequestManager.mRetrofitManager
                .createRequest(RetrofitRequestInterface.class)
                .GetMyMoneyAllLogs(RequestManager.encryptParams(map))
                .enqueue(new RetrofitCallBack() {

                    @Override
                    public void onSuccess(String s) {
                        DialogUtil.hideProgress();
                        LogUtil.e(TAG, s);
                        switch (mDataStatus) {
                            case STATUS_REFRESH:
                                mRefreshLayout.finishRefreshing();
                                break;
                            case STATUS_LOAD:
                                mRefreshLayout.finishLoadmore();
                                break;
                        }
                        try {
                            JSONObject response = new JSONObject(s);
                            int code = response.getInt("code");
                            String info = response.getString("info");
                            if (code == 0) {
                                switch (mDataStatus) {
                                    case STATUS_REFRESH:
                                        mOrderList.clear();
                                        break;
                                }
                                JSONArray orderArray = response.getJSONArray("data");
                                for (int i = 0; i < orderArray.length(); i++) {

                                    JSONObject orderItem = orderArray.getJSONObject(i);
                                    ProductBean orderBean = new ProductBean();
//                                    orderBean.setPictureUrl(orderItem.getString("image"));
//                                    orderBean.setName(orderItem.getString("name"));
                                    orderBean.setChildOrderStatus(orderItem.getInt("type"));
                                    orderBean.setPrice(orderItem.getDouble("money"));
                                    orderBean.setEndTime(orderItem.getString("create_time"));


                                    mOrderList.add(orderBean);
                                }

                                showDataRecycleView();

                                if (mOrderList.size() == 0) {
                                    mEmptyView.setVisibility(View.VISIBLE);
                                } else {
                                    mEmptyView.setVisibility(View.GONE);
                                }
                            } else {
                                ToastUtil.showShort(mActivity, info);
                            }
                        } catch (JSONException e) {
                            switch (mDataStatus) {
                                case STATUS_REFRESH:
                                    mRefreshLayout.finishRefreshing();
                                    break;
                                case STATUS_LOAD:
                                    mRefreshLayout.finishLoadmore();
                                    break;
                            }
                            e.printStackTrace();
                            ToastUtil.showShort(mActivity, "数据异常");
                            LogUtil.e(TAG, e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        DialogUtil.hideProgress();
                        switch (mDataStatus) {
                            case STATUS_REFRESH:
                                mRefreshLayout.finishRefreshing();
                                break;
                            case STATUS_LOAD:
                                mRefreshLayout.finishLoadmore();
                                break;
                        }
                        if (!NetworkUtil.isConnected()) {
                            ToastUtil.showShort(mActivity, "网络未连接");
                        } else {
                            ToastUtil.showShort(mActivity, getString(R.string.network_error));
                        }
                    }
                });


    }


    @Override
    public void onResume() {
        super.onResume();
        mDataStatus = STATUS_REFRESH;
        getData();
    }
}
