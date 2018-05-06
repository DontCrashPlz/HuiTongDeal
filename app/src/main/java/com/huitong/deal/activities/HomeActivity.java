package com.huitong.deal.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.huitong.deal.R;
import com.huitong.deal.fragments.HomeDealFragment;
import com.huitong.deal.fragments.HomeMarketFragment;
import com.huitong.deal.fragments.HomeMineFragment;
import com.zheng.zchlibrary.apps.BaseActivity;

/**
 * Created by Zheng on 2018/4/15.
 */

public class HomeActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    private FrameLayout mFragmentFly;
    private RadioButton mMarketRbtn;
    private RadioButton mDealRbtn;
    private RadioButton mMineRbtn;

    private FragmentManager mManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();

        mMarketRbtn.setChecked(true);
    }

    private void initUI(){

        mManager= getSupportFragmentManager();

        mFragmentFly= (FrameLayout) findViewById(R.id.home_fly_fragment);
        mMarketRbtn= (RadioButton) findViewById(R.id.home_rbtn_market);
        mMarketRbtn.setOnCheckedChangeListener(this);
        mDealRbtn= (RadioButton) findViewById(R.id.home_rbtn_deal);
        mDealRbtn.setOnCheckedChangeListener(this);
        mMineRbtn= (RadioButton) findViewById(R.id.home_rbtn_mine);
        mMineRbtn.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int vId= buttonView.getId();
        switch (vId){
            case R.id.home_rbtn_market:
                if (isChecked){
                    mManager.beginTransaction()
                            .replace(R.id.home_fly_fragment, HomeMarketFragment.newInstance(""))
                            .commit();
                }
                break;
            case R.id.home_rbtn_deal:
                if (isChecked){
                    mManager.beginTransaction()
                            .replace(R.id.home_fly_fragment, HomeDealFragment.newInstance(""))
                            .commit();
                }
                break;
            case R.id.home_rbtn_mine:
                if (isChecked){
                    mManager.beginTransaction()
                            .replace(R.id.home_fly_fragment, HomeMineFragment.newInstance(""))
                            .commit();
                }
                break;
            default:
                showShortToast("无效的导航条切换");
                break;
        }
    }
}
