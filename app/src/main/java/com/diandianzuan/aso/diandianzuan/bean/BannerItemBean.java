package com.diandianzuan.aso.diandianzuan.bean;



public class BannerItemBean {
    private String mImageUrl;
    /**
     * 1：内部项目 2：外部
     */
    private int mType;

    public static final int TYPE_INSIDE_PRODUCT = 1;
    public static final int TYPE_OUTSIDE_URL = 2;

    private String mProductId;

    private String mUrl;

    public String getImageUrl() {
        return mImageUrl;
    }

    public int getType() {
        return mType;
    }

    public String getProductId() {
        return mProductId;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public void setType(int type) {
        mType = type;
    }

    public void setProductId(String productId) {
        mProductId = productId;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
