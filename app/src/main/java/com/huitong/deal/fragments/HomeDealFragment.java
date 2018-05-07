package com.huitong.deal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.huitong.deal.R;
import com.huitong.deal.activities.LoginActivity;
import com.huitong.deal.beans.DealTableEntity;
import com.zheng.zchlibrary.apps.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/4/13.
 */

public class HomeDealFragment extends BaseFragment {

    public static HomeDealFragment newInstance(String content){
        HomeDealFragment instance = new HomeDealFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        instance.setArguments(args);
        return instance;
    }

    private CommonTabLayout mTabLayout;
    private ViewPager mViewPager;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_home_deal, container, false);

        mTabEntities.add(new DealTableEntity("持仓", R.mipmap.transaction_position_select, R.mipmap.transaction_position));
        mTabEntities.add(new DealTableEntity("历史", R.mipmap.transaction_history_select, R.mipmap.transaction_history));

        mTabLayout= mView.findViewById(R.id.home_tly_dealtab);
        mViewPager= mView.findViewById(R.id.home_vp_content);
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return DealFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return mView;
    }
}
