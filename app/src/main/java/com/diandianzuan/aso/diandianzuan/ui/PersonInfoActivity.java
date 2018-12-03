package com.diandianzuan.aso.diandianzuan.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.adapter.recycler.RecyclerCommonAdapter;
import com.diandianzuan.aso.diandianzuan.adapter.recycler.base.ViewHolder;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;
import com.diandianzuan.aso.diandianzuan.bean.TypeBean;
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
import com.diandianzuan.aso.diandianzuan.util.SPUtil;
import com.diandianzuan.aso.diandianzuan.util.ToastUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class PersonInfoActivity extends BaseActivity {
    private static final String TAG = "PersonInfoActivity";
    @BindView(R.id.ll_layout_back_top_bar_back)
    LinearLayout llLayoutBackTopBarBack;
    @BindView(R.id.tv_layout_back_top_bar_title)
    TextView tvLayoutBackTopBarTitle;
    @BindView(R.id.tv_layout_back_top_bar_operate)
    TextView tvLayoutBackTopBarOperate;
    @BindView(R.id.iv_activity_user_info_avatar)
    ImageView ivActivityUserInfoAvatar;
    @BindView(R.id.ll_activity_user_info_avatar)
    LinearLayout llActivityUserInfoAvatar;
    @BindView(R.id.et_activity_user_info_nickname)
    EditText etActivityUserInfoNickname;
    @BindView(R.id.ll_activity_user_info_nickname)
    LinearLayout llActivityUserInfoNickname;
    @BindView(R.id.tv_activity_user_info_sex)
    TextView tvActivityUserInfoSex;
    @BindView(R.id.ll_activity_user_info_sex)
    LinearLayout llActivityUserInfoSex;
    @BindView(R.id.tv_activity_user_info_phone)
    TextView tvActivityUserInfoPhone;
    @BindView(R.id.ll_activity_user_info_phone)
    LinearLayout llActivityUserInfoPhone;
    @BindView(R.id.tv_save)
    TextView tvSave;
    private PopupWindow mSexPopwindow;
    private RecyclerView mSexRV;
    private RecyclerCommonAdapter<TypeBean> mSexAdapter;
    private List<TypeBean> mSexList = new ArrayList<>();
    private TypeBean mTempSex;
    private TypeBean mSelectedSex;



    private LinearLayout mAvatarLL;
    private PopupWindow mSelectPopwindow;
    private String mPhotoPath;
    private String mCropPath;
    private String mUploadPath="";

    public static final int REQUEST_CODE_IMAGE = 1;
    public static final int REQUEST_CODE_CAMERA = 2;
    public static final int REQUEST_CODE_CROP = 3;

    public static final int PERMISSION_CODE_TAKE_PHOTO = 100;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_person_info;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        initUserInfo();
        //初始化性别数据
        String[] sexArray = getResources().getStringArray(R.array.sex_category);
        for (int i = 0; i < sexArray.length; i++) {
            TypeBean typeBean = new TypeBean();
            typeBean.setName(sexArray[i]);
            typeBean.setId(i + 1 + "");
            if (typeBean.getId().equals("3")){
                typeBean.setId("0");
            }
            mSexList.add(typeBean);
        }
        initImagePicker();
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @OnClick(R.id.ll_layout_back_top_bar_back)
    public void onLlLayoutBackTopBarBackClicked() {
    }

    @OnClick(R.id.ll_activity_user_info_avatar)
    public void onLlActivityUserInfoAvatarClicked() {
        showSelectPopwindow();
    }

    @OnClick(R.id.ll_activity_user_info_sex)
    public void onLlActivityUserInfoSexClicked() {

        showSexPopupWindow();
    }

    @OnClick(R.id.tv_save)
    public void onTvSaveClicked() {
        updateAvatar(mUploadPath);
    }
    private void showSexPopupWindow() {

        if (mSexPopwindow != null && mSexPopwindow.isShowing()) {
            mSexPopwindow.dismiss();
        } else if (mSexPopwindow != null) {
            mSexPopwindow.showAtLocation(tvActivityUserInfoSex, Gravity.NO_GRAVITY, 0, 0);
        } else {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.popwindow_option, null);
            TextView cancelTV = (TextView) view.findViewById(R.id.tv_popwindow_option_cancel);
            TextView confirmTV = (TextView) view.findViewById(R.id.tv_popwindow_option_confirm);
            cancelTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSexPopwindow.dismiss();
                }
            });
            confirmTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTempSex != null) {
                        mSexPopwindow.dismiss();
//                        DialogUtil.showProgress(mActivity, "正在修改性别");
//                        updateSex(mTempSex);
                        tvActivityUserInfoSex.setText(mTempSex.getName());
                    }
                }
            });
            mSexRV = (RecyclerView) view.findViewById(R.id.rv_popwindow_option);
            showSexRecycleView();
            mSexPopwindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mSexPopwindow.showAtLocation(tvActivityUserInfoSex, Gravity.NO_GRAVITY, 0, 0);
        }
    }

    private void showSexRecycleView() {

        if (mSexAdapter == null) {
            mSexAdapter = new RecyclerCommonAdapter<TypeBean>(mActivity, R.layout.item_option, mSexList) {
                @Override
                protected void convert(ViewHolder holder, final TypeBean typeBean, int position) {
                    TextView optionTV = holder.getView(R.id.tv_item_option);
                    ImageView selectIV = holder.getView(R.id.iv_item_option_select);
                    optionTV.setText(typeBean.getName());
                    if (mTempSex != null) {
                        if (typeBean.getName().equals(mTempSex.getName())) {
                            optionTV.setTextColor(ContextCompat.getColor(mActivity, R.color.colorYellow));
                            selectIV.setVisibility(View.VISIBLE);
                        } else {
                            optionTV.setTextColor(ContextCompat.getColor(mActivity, R.color.colorFontDark));
                            selectIV.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        optionTV.setTextColor(ContextCompat.getColor(mActivity, R.color.colorFontDark));
                        selectIV.setVisibility(View.INVISIBLE);
                    }
                    holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTempSex = typeBean;
                            mSexAdapter.notifyDataSetChanged();
                        }
                    });
                }
            };
            mSexRV.setAdapter(mSexAdapter);
            mSexRV.setLayoutManager(new LinearLayoutManager(mActivity));
        } else {
            mSexAdapter.notifyDataSetChanged();
        }
    }
    private void initUserInfo() {

        tvActivityUserInfoPhone.setText(AccountManager.sUserBean.getTelNumber()+"");

        //头像
        Glide.with(mActivity)
                .load(AccountManager.sUserBean.getHeadPortrait())
                .bitmapTransform(new CropCircleTransformation(mActivity)).placeholder(R.mipmap.center)
                .into(ivActivityUserInfoAvatar);

        //昵称
        etActivityUserInfoNickname.setText(AccountManager.sUserBean.getNickName());
        //性别
        switch (AccountManager.sUserBean.getSex()) {
            case 1:
                mTempSex = new TypeBean();
                mTempSex.setId(1 + "");
                mTempSex.setName("男");
                mSelectedSex = mTempSex;
                break;
            case 2:
                mTempSex = new TypeBean();
                mTempSex.setId(2 + "");
                mTempSex.setName("女");
                mSelectedSex = mTempSex;
                break;
        }
        if (mSelectedSex != null) {
            tvActivityUserInfoSex.setText(mSelectedSex.getName());
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



    private void updateAvatar(final String avatarPath) {
        DialogUtil.showProgress(mActivity, "正在保存");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(mActivity).load(avatarPath).asBitmap().into(-1, -1).get();
                    LogUtil.e(TAG, bitmap + "");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                String base64="";
                try {
                  base64
                            = ImageUtil.bitmapToBase64String(bitmap);
                }catch (Exception e){

                }

//                LogUtil.e(TAG, base64);

                final String finalBase6 = base64;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> map = new HashMap<>();
                        map.put("customer_id", AccountManager.sUserBean.getId());
                        map.put("avatar", finalBase6);
                        map.put("nicename", etActivityUserInfoNickname.getText().toString().trim());
                        map.put("sex", mTempSex.getId());
                        LogUtil.e(TAG, map.toString());
                        RequestManager.mRetrofitManager
                                .createRequest(RetrofitRequestInterface.class)
                                .ChangeUserInfo(RequestManager.encryptParams(map))
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
                                                if (!avatarPath.equals("")){
                                                    AccountManager.sUserBean.setHeadPortrait(avatarPath);
                                                    Glide.with(mActivity)
                                                            .load(avatarPath)
                                                            .bitmapTransform(new CropCircleTransformation(mActivity))
                                                            .into(ivActivityUserInfoAvatar);
                                                }

                                                AccountManager.sUserBean.setSex(Integer.valueOf(mTempSex.getId()));


                                                String base64 = CommonUtil.objectToBase64(AccountManager.sUserBean);
                                                SPUtil.put(Constant.USER, base64);


//                                                EventBus.getDefault().post(new UpdateUserInfoEvent());
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
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri photoURI = FileProvider.getUriForFile(PersonInfoActivity.this, "com.diandianzuan.aso.diandianzuan.fileprovider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            PersonInfoActivity.this.startActivityForResult(intent, REQUEST_CODE_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_IMAGE) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);

                mUploadPath=images.get(0).path;
//                updateAvatar(images.get(0).path);
                Glide.with(mActivity)
                        .load(mUploadPath)
                        .bitmapTransform(new CropCircleTransformation(mActivity))
                        .into(ivActivityUserInfoAvatar);
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CAMERA) {
            LogUtil.e(TAG, mPhotoPath);
            cutPhoto(mPhotoPath);
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CROP) {

            mUploadPath=mCropPath;
            Glide.with(mActivity)
                    .load(mUploadPath)
                    .bitmapTransform(new CropCircleTransformation(mActivity))
                    .into(ivActivityUserInfoAvatar);
//            updateAvatar(mCropPath);
        }
    }
    private File createImageFile() throws IOException {
        String imageFileName = "photo";
        //.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, AccountManager.sUserBean.getId() + ".jpg");
        if (!image.exists()) {
            image.createNewFile();
        }
        mPhotoPath = image.getAbsolutePath();
        File crop = new File(storageDir, AccountManager.sUserBean.getId() + "_crop" + ".jpg");

        if (!crop.exists()) {
            crop.createNewFile();
        }
        mCropPath = crop.getAbsolutePath();
        return image;
    }

    /**
     * 裁剪拍摄的照片
     *
     * @param photoPath
     */
    public void cutPhoto(String photoPath) {
        File photoFile = new File(photoPath);
        File cropFile = new File(mCropPath);
        if (!cropFile.getParentFile().exists()) {
            cropFile.getParentFile().mkdirs();
        }
        if (!cropFile.exists()) {
            try {
                cropFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(FileProvider.getUriForFile(this, "com.diandianzuan.aso.diandianzuan.fileprovider", photoFile), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropFile));
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG);
        intent.putExtra("outputX", 720);
        intent.putExtra("outputY", 720);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", false);
        startActivityForResult(intent, REQUEST_CODE_CROP);
    }
    private void showSelectPopwindow() {
        if (mSelectPopwindow != null && mSelectPopwindow.isShowing()) {
            mSelectPopwindow.dismiss();
        } else {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.popwindow_select_picture, null);
            TextView takePhotoTV = (TextView) view.findViewById(R.id.tv_popwindow_select_picture_take_photo);
            TextView albumTV = (TextView) view.findViewById(R.id.tv_popwindow_select_picture_album);
            TextView cancelTV = (TextView) view.findViewById(R.id.tv_popwindow_select_picture_cancel);
            takePhotoTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mSelectPopwindow.dismiss();

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
                        } else {
                            Log.e(TAG, "调用系统照相机拍照");
                            //调用系统照相机拍照
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File photoFile = null;
                            try {
                                photoFile = createImageFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                            Uri photoURI = FileProvider.getUriForFile(PersonInfoActivity.this, "com.diandianzuan.aso.diandianzuan.fileprovider", photoFile);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            PersonInfoActivity.this.startActivityForResult(intent, REQUEST_CODE_CAMERA);
                        }
                    } else {
                        //调用系统照相机拍照
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Uri photoURI = FileProvider.getUriForFile(PersonInfoActivity.this, "com.diandianzuan.aso.diandianzuan.fileprovider", photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        PersonInfoActivity.this.startActivityForResult(intent, REQUEST_CODE_CAMERA);
                    }
                }
            });
            albumTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ImageGridActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_IMAGE);
                    mSelectPopwindow.dismiss();
                }
            });
            cancelTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectPopwindow.dismiss();
                }
            });
            mSelectPopwindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mSelectPopwindow != null && mSelectPopwindow.isShowing()) {
                        mSelectPopwindow.dismiss();
                    }
                    return false;
                }
            });
            mSelectPopwindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
        }
    }
}
