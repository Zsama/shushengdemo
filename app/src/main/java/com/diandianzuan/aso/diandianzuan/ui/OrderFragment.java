package com.diandianzuan.aso.diandianzuan.ui;

import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.adapter.recycler.RecyclerCommonAdapter;
import com.diandianzuan.aso.diandianzuan.adapter.recycler.base.ViewHolder;
import com.diandianzuan.aso.diandianzuan.base.BaseFragment;
import com.diandianzuan.aso.diandianzuan.bean.OrderBean;
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

public class OrderFragment extends BaseFragment {
    private TwinklingRefreshLayout mRefreshLayout;
    private RecyclerView mOrderRV;
    private RecyclerCommonAdapter<OrderBean> mOrderAdapter;
    private List<OrderBean> mOrderList = new ArrayList<>();

    private int mDataStatus = STATUS_REFRESH;
    private int mPage = 1;
    private static final int STATUS_REFRESH = 1;
    private static final int STATUS_LOAD = 2;

    private int mType;

    public static final int TYPE_ALL = 1; //全部
    public static final int TYPE_WAIT_PAY = 2; //待支付
    public static final int TYPE_WAIT_SEND= 3; //待发货

    public static final int TYPE_WAIT_RECEIVE = 4;//待收货
    public static final int TYPE_WAIT_EVALUATE = 5; //已完成


    private View mEmptyView;

    private static final String TAG = "OrderFragment";
    @Override
    public void initView() {
        mRefreshLayout = byId(R.id.tfl_fragment_order);
        setRefreshStyle();
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
        return R.layout.fragment_order;
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
            mOrderAdapter = new RecyclerCommonAdapter<OrderBean>(mActivity, R.layout.item_order_list_product, mOrderList) {
                @Override
                protected void convert(ViewHolder holder, final OrderBean orderBean, int position) {


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
        map.put("type", mType + "");
        RequestManager.mRetrofitManager
                .createRequest(RetrofitRequestInterface.class)
                .myOrder(RequestManager.encryptParams(map))
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
//                                JSONObject data = response.getJSONObject("data");
                                JSONArray orderArray = response.getJSONArray("data");
                                for (int i = 0; i < orderArray.length(); i++) {

                                    JSONObject orderItem = orderArray.getJSONObject(i);
                                    List<ProductBean> productList = new ArrayList<>();
                                    OrderBean orderBean = new OrderBean();
                                    //还有一个ID
                                    orderBean.setOrderId(orderItem.getString("id"));
                                    orderBean.setRealPrice(orderItem.getDouble("total_price"));
//                                    orderBean.setPaymentMethod(orderItem.getInt("payment_id"));

                                    int  is_ship =orderItem.getInt("is_ship");//是否发货(1为已发货，0未发货)
                                    int is_confirm_receipt=orderItem.getInt("is_confirm_receipt"); //是否确认签收(1为已确认收货，0待收货)
                                    int status=0 ;
                                    if (is_ship==0){
                                        status=OrderBean.STATUS_WAIT_PAY;
                                    }


                                    orderBean.setStatus(status);

                                    JSONArray contentArray = orderItem.getJSONArray("productlist");

                                    for (int j = 0; j < contentArray.length(); j++) {

                                            JSONObject productItem = contentArray.getJSONObject(j);
                                            ProductBean productBean = new ProductBean();
                                            productBean.setPictureUrl(productItem.getString("img_url"));
                                            productBean.setId(productItem.getString("product_id"));
                                            productBean.setSharePoint(productItem.getInt("integral_price"));
                                            productBean.setName(productItem.getString("product_name"));
                                            productBean.setPrice(productItem.getDouble("discount_price"));
                                            productBean.setOriginPrice(productItem.getString("original_price"));
                                            productBean.setSpec(productItem.getString("standard"));
                                            productBean.setSeller(productItem.getString("brandname"));
                                            productBean.setBuyNum(productItem.getInt("quantity"));


                                        productList.add(productBean);
                                    }
                                    orderBean.setProductList(productList);
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
