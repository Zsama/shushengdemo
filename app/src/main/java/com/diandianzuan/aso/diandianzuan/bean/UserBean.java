package com.diandianzuan.aso.diandianzuan.bean;
import java.io.Serializable;


public class UserBean implements Serializable {
    private String mId;
    private String mNickName;
    private String mRegisterTime;
    private String mTelNumber;//手机号码
    private double mIntegral;//积分
    private String mRecommend;//推荐级数
    private String mVipLevel;
    private String mIntegralDetailTime;//积分明细时间
    private String mIntegralDetail;//积分明细
    private String mPassWord;
    private String mHeadPortrait;//用户头像地址
    private boolean mCanSign = false; //是否可以签到
    private int mFaceScore;  //颜值
    private double mRedPackage; //红包
    private double earnings;//收益
    private boolean mHaveNewMessage;//是否有新消息  0表示没有false
    private String mWxName="";
    private String mQRCode;//二维码
    private String customer_no;//hui元编号

    private int mIntegerType; //积分类型
    public static final int TYPE_GET_INTEGER_FIRST=1; //获得积分
    public static final int TYPE_GET_INTEGER_SECOND=4;//获得积分
    public static final int TYPE_DEDUCT_INTEGER=6;//扣除积分

    private String mEmoney="0";   //余额
    private String mFreezmoney="0";//冻结金额
    private String mFreemoney="0";//可提现金额

    private String mAliName="";



    private String mBank="";
    private String mBank_card="";
    private String mRealName="";
    private String mPersonNumber="";

    public String getmAliName() {
        return mAliName;
    }

    public void setmAliName(String mAliName) {
        this.mAliName = mAliName;
    }

    public String getmFreemoney() {
        return mFreemoney;
    }

    public void setmFreemoney(String mFreemoney) {
        this.mFreemoney = mFreemoney;
    }

    public String getmFreezmoney() {
        return mFreezmoney;
    }

    public void setmFreezmoney(String mFreezmoney) {
        this.mFreezmoney = mFreezmoney;
    }

    public String getBank() {
        return mBank;
    }

    public void setBank(String bank) {
        mBank = bank;
    }

    public String getBank_card() {
        return mBank_card;
    }

    public void setBank_card(String bank_card) {
        mBank_card = bank_card;
    }

    public String getRealName() {
        return mRealName;
    }

    public void setRealName(String realName) {
        mRealName = realName;
    }

    public String getPersonNumber() {
        return mPersonNumber;
    }

    public void setPersonNumber(String personNumber) {
        mPersonNumber = personNumber;
    }

    public String getEmoney() {
        return mEmoney;
    }

    public void setEmoney(String emoney) {
        mEmoney = emoney;
    }

    public String getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    public String getWxName() {
        return mWxName;
    }

    public void setWxName(String wxName) {
        mWxName = wxName;
    }

    /**
     * 签今日签到颜值
     */
    private int mSignFaceScore;//签到颜值

    /**
     * 性别 1:男 2：女 3:保密
     */
    private int mSex;
    /**
     * 出生日期
     */
    private String mBirthday;

    public boolean isHaveNewMessage() {
        return mHaveNewMessage;
    }

    public void setHaveNewMessage(boolean haveNewMessage) {
        mHaveNewMessage = haveNewMessage;
    }

    public String getHeadPortrait() {
        return mHeadPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        mHeadPortrait = headPortrait;
    }

    public String getPassWord() {
        return mPassWord;
    }

    public void setPassWord(String passWord) {
        mPassWord = passWord;
    }

    public String getIntegralDetailTime() {
        return mIntegralDetailTime;
    }

    public void setIntegralDetailTime(String integralDetailTime) {
        mIntegralDetailTime = integralDetailTime;
    }

    public String getIntegralDetail() {
        return mIntegralDetail;
    }

    public void setIntegralDetail(String integralDetail) {
        mIntegralDetail = integralDetail;
    }

    public String getVipLevel() {
        return mVipLevel;
    }

    public void setVipLevel(String vipLevel) {
        mVipLevel = vipLevel;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String nickName) {
        mNickName = nickName;
    }

    public String getRegisterTime() {
        return mRegisterTime;
    }

    public void setRegisterTime(String registerTime) {
        mRegisterTime = registerTime;
    }

    public String getTelNumber() {
        return mTelNumber;
    }

    public void setTelNumber(String telNumber) {
        mTelNumber = telNumber;
    }

    public double getIntegral() {
        return mIntegral;
    }

    public void setIntegral(double integral) {
        mIntegral = integral;
    }

    public String getRecommend() {
        return mRecommend;
    }

    public void setRecommend(String recommend) {
        mRecommend = recommend;
    }

    public boolean isCanSign() {
        return mCanSign;
    }

    public void setCanSign(boolean canSign) {
        mCanSign = canSign;
    }

    public int getFaceScore() {
        return mFaceScore;
    }

    public void setFaceScore(int faceScore) {
        mFaceScore = faceScore;
    }

    public double getRedPackage() {
        return mRedPackage;
    }

    public void setRedPackage(double redPackage) {
        mRedPackage = redPackage;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public int getSignFaceScore() {
        return mSignFaceScore;
    }

    public void setSignFaceScore(int signFaceScore) {
        mSignFaceScore = signFaceScore;
    }

    public int getSex() {
        return mSex;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setSex(int sex) {
        mSex = sex;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public String getQRCode() {
        return mQRCode;
    }

    public void setQRCode(String QRCode) {
        mQRCode = QRCode;
    }

    public int getIntegerType() {
        return mIntegerType;
    }

    public void setIntegerType(int integerType) {
        mIntegerType = integerType;
    }
}
