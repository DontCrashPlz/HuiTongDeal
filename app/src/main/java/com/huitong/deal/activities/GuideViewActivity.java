package com.huitong.deal.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.huitong.deal.R;
import com.huitong.deal.fragments.GuidePageFragment;
import com.zheng.zchlibrary.apps.BaseActivity;

/**
 * 引导页
 * Created by Zheng on 2016/8/5.
 */
public class GuideViewActivity extends BaseActivity {
    private ViewPager mVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guideview);
        mVp= findViewById(R.id.guideview_vp);

        mVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return GuidePageFragment.newInstance(position + 1);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        mVp.setCurrentItem(0);
        mVp.setOffscreenPageLimit(2);
    }

    @Override
    public void initProgressDialog() {

    }

}
