package com.diandianzuan.aso.diandianzuan.bean;

import java.util.ArrayList;
import java.util.List;


public class OrderBean {

    private String mOrderId;
    private List<ProductBean> mProductList = new ArrayList<>();

    /**
     * 订单合计
     */
    private double mRealPrice;

    /**
     * 订单状态
     */
    private int mStatus;

    public static final int STATUS_WAIT_PAY = 2; //待支付

    public static final int TYPE_WAIT_SEND = 3; //待发货

    public static final int STATUS_COMPLETED = 5;//已完成
    public static final int TYPE_WAIT_RECEIVE = 4;//待收货

    public static final int STATUS_WAIT_EVALUATE = 6; //待评价

    public static final int STATUS_CANCELED = 1; //已取消

    public static final int STATUS_REFUND = 7;

    /**
     * 退款状态
     */
    private int mRefundStatus;

    public static final int STATUS_IN_REFUND = 1; //退款中

    public static final int STATUS_REFUND_SUCCESS = 2; //退款成功

    public static final int STATUS_REFUND_FAIL = 3; //退款失败

    /**
     * 支付方式
     */
    private int mPaymentMethod;

    public static final int PAYMENT_METHOD_ONLINE_BANKING = 2;//网银
    public static final int PAYMENT_METHOD_ALIPAY = 3; //支付宝
    public static final int PAYMENT_METHOD_WECHAT = 4;//微信
    public static final int PAYMENT_METHOD_BANK = 5;//银行转账，汇款
    public static final int PAYMENT_METHOD_POS = 6;//POS机
    public static final int PAYMENT_METHOD_CASH = 7;//现金支付
    public static final int PAYMENT_METHOD_OFFLINE_REMITTANCE = 8;//线下汇款


    public List<ProductBean> getProductList() {
        return mProductList;
    }

    public void setProductList(List<ProductBean> productList) {
        mProductList = productList;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(String orderId) {
        mOrderId = orderId;
    }

    public double getRealPrice() {
        return mRealPrice;
    }

    public void setRealPrice(double realPrice) {
        mRealPrice = realPrice;
    }

    public int getRefundStatus() {
        return mRefundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        mRefundStatus = refundStatus;
    }

    public int getPaymentMethod() {
        return mPaymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        mPaymentMethod = paymentMethod;
    }
}
