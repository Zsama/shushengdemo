package com.diandianzuan.aso.diandianzuan.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ProductBean implements Serializable {
    private String mId;
    private String mName;
    private String mbusiness_title;
    private int mKucun;
    private double mPrice;
    private int mSharePoint;
    private String mPictureUrl;
    private String mProductDetail;
    private String mInstitutionId;
    private String mSpec;
    private String mOriginPrice;
    private String mSeller;
    private String mKeywords;

    public String getmKeywords() {
        return mKeywords;
    }

    public void setmKeywords(String mKeywords) {
        this.mKeywords = mKeywords;
    }

    public String getMbusiness_title() {
        return mbusiness_title;
    }

    public void setMbusiness_title(String mbusiness_title) {
        this.mbusiness_title = mbusiness_title;
    }

    public int getmKucun() {
        return mKucun;
    }

    public void setmKucun(int mKucun) {
        this.mKucun = mKucun;
    }

    public String getSeller() {
        return mSeller;
    }

    public void setSeller(String seller) {
        mSeller = seller;
    }

    public String getOriginPrice() {
        return mOriginPrice;
    }

    public void setOriginPrice(String originPrice) {
        mOriginPrice = originPrice;
    }

    public String getSpec() {
        return mSpec;
    }

    public void setSpec(String spec) {
        mSpec = spec;
    }

    public String getInstitutionId() {
        return mInstitutionId;
    }

    public void setInstitutionId(String institutionId) {
        mInstitutionId = institutionId;
    }

    public String getProductDetail() {
        return mProductDetail;
    }

    public void setProductDetail(String productDetail) {
        mProductDetail = productDetail;
    }

    /* 副ID，例如收藏ID*/
    private String mIdCommon;
    private String mHospitalName;

    public String getHospitalName() {
        return mHospitalName;
    }

    public void setHospitalName(String hospitalName) {
        mHospitalName = hospitalName;
    }

    public String getIdCommon() {
        return mIdCommon;
    }

    public void setIdCommon(String idCommon) {
        mIdCommon = idCommon;
    }

    /**
     * 已付款人数
     */
    private int mPaidNum;

    /**
     * 购买数量
     */
    private int mBuyNum;
    private boolean mSelected = false;
    private int mCarId = 0;
    /**
     * 是否显示医院
     */
    private boolean mShowHospital;

    /**
     * 活动结束时间
     */
    private String mEndTime;

    /**
     * 购买人数
     */
    private int mBuyerNum;

    /**
     * 足记Id
     */
    private String mFootprintId;

    /**
     * 所属子订单状态
     * 9:待评价 8.已取消 7.待使用  6退款失败，5退款成功，4退款处理中，3待支付，2已使用，1已完成
     */
    private int mChildOrderStatus;


    /**
     * 子订单Id
     */
    private String mChildOrderId;


    /**
     * 子订单金额
     */
    private double mChildOrderMoney;

    /**
     * 是否是尊享招募卡
     */
    private boolean mRespectCard=false;

    /**
     * 是否可退款 0:可以退款 1:不可退款
     */
    private int mCanRefund=0;

    public static final int CAN_REFUND=0;
    public static final int CAN_NOT_REFUND=1;

    public int getCarId() {
        return mCarId;
    }

    public void setCarId(int carId) {
        mCarId = carId;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public double getPrice() {
        return mPrice;
    }

    public int getSharePoint() {
        return mSharePoint;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public void setSharePoint(int sharePoint) {
        mSharePoint = sharePoint;
    }

    public void setPictureUrl(String pictureUrl) {
        mPictureUrl = pictureUrl;
    }

    public int getPaidNum() {
        return mPaidNum;
    }

    public void setPaidNum(int paidNum) {
        mPaidNum = paidNum;
    }

    public int getBuyNum() {
        return mBuyNum;
    }

    public void setBuyNum(int buyNum) {
        mBuyNum = buyNum;
    }

    public int getBuyerNum() {
        return mBuyerNum;
    }

    public void setBuyerNum(int buyerNum) {
        mBuyerNum = buyerNum;
    }

    public boolean isShowHospital() {
        return mShowHospital;
    }

    public void setShowHospital(boolean showHospital) {
        mShowHospital = showHospital;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public String getFootprintId() {
        return mFootprintId;
    }

    public void setFootprintId(String footprintId) {
        mFootprintId = footprintId;
    }

    public int getChildOrderStatus() {
        return mChildOrderStatus;
    }

    public void setChildOrderStatus(int childOrderStatus) {
        mChildOrderStatus = childOrderStatus;
    }

    public String getChildOrderId() {
        return mChildOrderId;
    }

    public void setChildOrderId(String childOrderId) {
        mChildOrderId = childOrderId;
    }


    public double getChildOrderMoney() {
        return mChildOrderMoney;
    }

    public void setChildOrderMoney(double childOrderMoney) {
        mChildOrderMoney = childOrderMoney;
    }

    public boolean isRespectCard() {
        return mRespectCard;
    }

    public void setRespectCard(boolean respectCard) {
        mRespectCard = respectCard;
    }

    public int getCanRefund() {
        return mCanRefund;
    }

    public void setCanRefund(int canRefund) {
        mCanRefund = canRefund;
    }
}
