package com.diandianzuan.aso.diandianzuan.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;
import com.diandianzuan.aso.diandianzuan.bean.ColorBean;
import com.diandianzuan.aso.diandianzuan.global.Constant;
import com.diandianzuan.aso.diandianzuan.manager.AccountManager;
import com.diandianzuan.aso.diandianzuan.manager.RequestManager;
import com.diandianzuan.aso.diandianzuan.net.RetrofitCallBack;
import com.diandianzuan.aso.diandianzuan.net.RetrofitRequestInterface;
import com.diandianzuan.aso.diandianzuan.util.CommonUtil;
import com.diandianzuan.aso.diandianzuan.util.DialogUtil;
import com.diandianzuan.aso.diandianzuan.util.GlideImageLoader;
import com.diandianzuan.aso.diandianzuan.util.ImageUtil;
import com.diandianzuan.aso.diandianzuan.util.LogUtil;
import com.diandianzuan.aso.diandianzuan.util.NetworkUtil;
import com.diandianzuan.aso.diandianzuan.util.ToastUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MissionAdvDetailActivity extends BaseActivity {
    public static final int REQUEST_CODE_IMAGE = 1;
    public static final int REQUEST_CODE_CAMERA = 2;
    public static final int REQUEST_CODE_CROP = 3;

    public static final int PERMISSION_CODE_TAKE_PHOTO = 100;

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
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.ll_submit)
    TextView llSubmit;
    @BindView(R.id.wv_web)
    WebView wvWeb;

    private String mKeywords = "";
    private String mId = "";
    private String bundled = "";
    private static final String TAG = "MissionAdvDetailActivity";
    private boolean ifHavePkg = false;
    private boolean ifHaveTime = false;
    private String market_pkg_name = "com.tencent.android.qqdownloader";
    private String join_id = "";
    private String imgPath = "";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mission_adv_detail;
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
        mId = intent.getStringExtra("projectId");
        mTvWords.setText("关键词：" + mKeywords);
        initImagePicker();
    }

    @Override
    public void initEvent() {
        GetTestDetail();
    }

    public static void startMe(Activity mCtx, String projectId, int type, String keywords) {
        Intent mIntent = new Intent(mCtx, MissionAdvDetailActivity.class);
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
        try {
            if (checkPackInfo(market_pkg_name)) {
                CommonUtil.jumpToMarketSearch(mActivity, mKeywords, market_pkg_name);
            } else {
                ToastUtil.showShort(mActivity, "请先安装" + mTvMallName.getText().toString());
            }

        } catch (Exception e) {

        }

    }


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
                                bundled = data.getString("bundled");
                                mTvPrice.setText("+" + data.getString("price") + "元");

                                JSONObject join = data.getJSONObject("join");
                                join_id = join.getString("join_id");
                                String wvdata = data.getString("info");
                                String customHtml = replace("&lt;", "<", wvdata);
                                customHtml = replace("&gt;", ">", customHtml);
                                customHtml = replace("&quot;", "\"", customHtml);
                                customHtml = replace("&amp;nbsp;", "  ", customHtml);
                                customHtml=replace(".jpg\"/>",".jpg\" style=\"max-width:100%;height:auto\"/>",customHtml);
                                customHtml=replace(".png\"/>",".png\" style=\"max-width:100%;height:auto\"/>",customHtml);

                                wvWeb.loadDataWithBaseURL(null, customHtml, "text/html", "utf-8", null);
                                int platform2 = data.getInt("platform2");
                                switch (platform2) {
                                    case Constant.TX_MARKET:
                                        mTvMallName.setText("腾讯应用宝");
                                        market_pkg_name = "com.tencent.android.qqdownloader";
                                        break;
                                    case Constant.MI_MARKET:
                                        mTvMallName.setText("小米应用商店");
                                        market_pkg_name = "com.xiaomi.market";
                                        break;
                                    case Constant.OPPO_MARKET:
                                        mTvMallName.setText("OPPO应用市场");
                                        market_pkg_name = "com.oppo.market";
                                        break;
                                    case Constant.HW_MARKET:
                                        mTvMallName.setText("华为应用市场");
                                        market_pkg_name = "com.huawei.appmarket";
                                        break;
                                    case Constant.BAIDU_MARKET:
                                        mTvMallName.setText("百度手机助手");
                                        market_pkg_name = "com.baidu.appsearch";
                                        break;
                                    case Constant.SANLIULING_MARKET:
                                        mTvMallName.setText("360手机助手");
                                        market_pkg_name = "com.qihoo.appstore";
                                        break;
                                }
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

    /**
     * 高级任务审核
     */
    private void HighTestSubmit() {
        Map<String, String> map = new HashMap<>();
        if (AccountManager.sUserBean != null) {
            map.put("customer_id", AccountManager.sUserBean.getId());

        }
        map.put("join_id", join_id);
        map.put("img_flag", join_id);
        RequestManager.mRetrofitManager
                .createRequest(RetrofitRequestInterface.class)
                .HighTestSubmit(RequestManager.encryptParams(map))
                .enqueue(new RetrofitCallBack() {

                    @Override
                    public void onSuccess(String response) {
                        LogUtil.e(TAG, response);
                        DialogUtil.hideProgress();
                        try {
                            JSONObject res = new JSONObject(response);
                            int code = res.getInt("code");
                            String info = res.getString("info");
                            ToastUtil.showShort(mContext, info);
                            if (code == 0) {
                                finish();


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

    @Override
    protected void onResume() {
        super.onResume();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_camera)
    public void onIvCameraClicked() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(mContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {//用户已拒绝过一次
                    //提示用户如果想要正常使用，要手动去设置中授权。
                    ToastUtil.showShort(mActivity, "请到设置-应用管理中开启此应用的读写权限");
                } else {
                    ActivityCompat.requestPermissions((Activity) mContext,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            PERMISSION_CODE_TAKE_PHOTO);
                }
            } else if (ContextCompat.checkSelfPermission(mContext,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                        Manifest.permission.CAMERA)) {//用户已拒绝过一次
                    //提示用户如果想要正常使用，要手动去设置中授权。
                    ToastUtil.showShort(mActivity, "请到设置-应用管理中开启此应用的相机权限");
                } else {
                    ActivityCompat.requestPermissions((Activity) mContext,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA},
                            PERMISSION_CODE_TAKE_PHOTO);
                }
            }
        }
        try {
            Intent intent = new Intent(mActivity, ImageGridActivity.class);
            startActivityForResult(intent, REQUEST_CODE_IMAGE);
        } catch (Exception e) {
            Log.e("error", "出错:", e);
        }


    }

    @OnClick(R.id.ll_submit)
    public void onLlSubmitClicked() {
        if (!imgPath.equals("")) {
            updateAvatar(imgPath);
        } else {
            ToastUtil.showShort(mActivity, "请上传截图");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE_TAKE_PHOTO) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ToastUtil.showShort(mActivity, "请到设置-应用管理中打开应用的读写权限");
                return;
            }
            if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                ToastUtil.showShort(mActivity, "请到设置-应用管理中打开应用的相机权限");
                return;
            }
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Uri photoURI = FileProvider.getUriForFile(UserInfoActivity.this, "com.nado.youyoumei.fileprovider", photoFile);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            UserInfoActivity.this.startActivityForResult(intent, REQUEST_CODE_CAMERA);
        }
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000); //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000); //保存文件的高度。单位像素
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_IMAGE) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Glide.with(mActivity).load(images.get(0).path).asBitmap().placeholder(R.mipmap.logo).centerCrop().into(ivCamera);
                imgPath = images.get(0).path;
                LogUtil.d(TAG, "path" + imgPath);

            }
        }
    }

    private void updateAvatar(final String avatarPath) {
        DialogUtil.showProgress(mActivity, "正在提交...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                final JSONArray jsonArray = new JSONArray();
                try {
                    bitmap = Glide.with(mActivity).load(avatarPath).asBitmap().into(-1, -1).get();
                    ArrayList<ColorBean> list = new ArrayList<ColorBean>();

                    int height = bitmap.getHeight();
                    int width = bitmap.getWidth();
                    list.clear();
                    for (int ib = 1; ib < 10; ib++) {
                        int x = (int) width / 10 * ib;
                        int y = (int) height / 10 * ib;
                        int color = bitmap.getPixel(x, y);
                        int r = Color.red(color);
                        int g = Color.green(color);
                        int b = Color.blue(color);
                        int a = Color.alpha(color);
                        ColorBean colorBean = new ColorBean();
                        colorBean.setR(r);
                        colorBean.setG(g);
                        colorBean.setB(b);
                        list.add(colorBean);
                        LogUtil.d(TAG, x + "," + y + "r=" + r + ",g=" + g + ",b=" + b);
                    }
                    JSONObject jsonObject = new JSONObject();
                    JSONObject tmpObj = null;
                    int count = list.size();
                    for (int i = 0; i < count; i++) {
                        try {
                            tmpObj = new JSONObject();
                            tmpObj.put("r", list.get(i).getR());
                            tmpObj.put("g", list.get(i).getG());
                            tmpObj.put("b", list.get(i).getB());
                            jsonArray.put(tmpObj);

                            tmpObj = null;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    LogUtil.d(TAG, jsonArray.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                final String base64 = ImageUtil.bitmapToBase64String(bitmap);
//                LogUtil.e(TAG, base64);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> map = new HashMap<>();
                        map.put("customer_id", AccountManager.sUserBean.getId());

                        map.put("join_id", join_id);
                        map.put("img_flag", jsonArray.toString());

                        LogUtil.e(TAG, map.toString());
                        map.put("image", base64);
                        RequestManager.mRetrofitManager
                                .createRequest(RetrofitRequestInterface.class)
                                .HighTestSubmit(RequestManager.encryptParams(map))
                                .enqueue(new RetrofitCallBack() {

                                    @Override
                                    public void onSuccess(String s) {
                                        DialogUtil.hideProgress();
                                        LogUtil.e(TAG, s);
                                        try {
                                            JSONObject response = new JSONObject(s);
                                            int code = response.getInt("code");
                                            String info = response.getString("info");
                                            if (code == 0) {
                                                ToastUtil.showLong(mActivity, info);
                                                finish();
                                            } else {
                                                ToastUtil.showShort(mActivity, info);
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
                });
            }
        }).start();


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
