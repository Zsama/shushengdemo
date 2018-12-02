package com.diandianzuan.aso.diandianzuan.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diandianzuan.aso.diandianzuan.R;
import com.diandianzuan.aso.diandianzuan.adapter.vp.VpFragmentAdapter;
import com.diandianzuan.aso.diandianzuan.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MissionRecordActivity extends BaseActivity {
    private LinearLayout mBackLL;
    private TextView mTitleTV;
    private TabLayout mTabLayout;
    private String[] mOrderCategory;
    private ViewPager mViewPager;
    private VpFragmentAdapter mVpFragmentAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();

    private int mPosition;
    private int mType;
    //0医美订单，1珠宝订单
    public static final int TYPE_HOSPITAL=0;
    public static final int TYPE_JEWELRY=1;

    public static final int POSITION_WAIT_PAY=1;
    public static final int POSITION_WAIT_USE=2;
    public static final int POSITION_WAIT_EVALUATE=3;
    public static final int POSITION_REFUND=4;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mission_record;
    }

    @Override
    public void initView() {
        mBackLL = byId(R.id.ll_layout_back_top_bar_back);
        mTitleTV = byId(R.id.tv_layout_back_top_bar_title);
        mTitleTV.setText("任务记录");

        mTabLayout = byId(R.id.tl_activity_user_order);
        mViewPager = byId(R.id.vp_activity_user_order);
    }

    @Override
    public void initData() {

        mPosition=getIntent().getIntExtra("position",0);
        mType=getIntent().getIntExtra("type",0);
        mTitleTV.setText("我的订单");

        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(mActivity, R.color.colorYellow));
        mTabLayout.setTabTextColors(ContextCompat.getColor(mActivity, R.color.colorFontDark),
                ContextCompat.getColor(mActivity, R.color.colorFontDark));

        OrderFragment orderFragmentA = new OrderFragment();
        Bundle bundleA = new Bundle();
        bundleA.putInt("type", OrderFragment.TYPE_ALL);
        orderFragmentA.setArguments(bundleA);

        OrderFragment orderFragmentB = new OrderFragment();
        Bundle bundleB = new Bundle();
        bundleB.putInt("type", OrderFragment.TYPE_WAIT_PAY);
        orderFragmentB.setArguments(bundleB);

        OrderFragment orderFragmentC = new OrderFragment();
        Bundle bundleC = new Bundle();
        bundleC.putInt("type", OrderFragment.TYPE_WAIT_SEND);
        orderFragmentC.setArguments(bundleC);

        OrderFragment orderFragmentD = new OrderFragment();
        Bundle bundleD = new Bundle();
        bundleD.putInt("type", OrderFragment.TYPE_WAIT_RECEIVE);
        orderFragmentD.setArguments(bundleD);

        OrderFragment orderFragmentE = new OrderFragment();
        Bundle bundleE = new Bundle();
        bundleE.putInt("type", OrderFragment.TYPE_WAIT_EVALUATE);
        orderFragmentE.setArguments(bundleE);
        mFragmentList.add(orderFragmentA);
        mFragmentList.add(orderFragmentB);
        mFragmentList.add(orderFragmentC);
        mFragmentList.add(orderFragmentD);
        mFragmentList.add(orderFragmentE);
        mVpFragmentAdapter = new VpFragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mVpFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mOrderCategory = getResources().getStringArray(R.array.order_category);


        for (int i = 0; i < mOrderCategory.length; i++) {
            mTabLayout.getTabAt(i).setText(mOrderCategory[i]);
        }
        mViewPager.setCurrentItem(mPosition);
    }

    @Override
    public void initEvent() {
        mBackLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public static void startMe(Activity mCtx) {
        Intent mIntent = new Intent(mCtx, MissionRecordActivity.class);
        mCtx.startActivity(mIntent);
    }

}
