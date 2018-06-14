package com.huitong.deal.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.UserInfoDataEntity;
import com.huitong.deal.fragments.HomeDealFragment;
import com.huitong.deal.fragments.HomeMarketFragment;
import com.huitong.deal.fragments.HomeMineFragment;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.ActivityManager;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.LogUtil;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
        ActivityManager.getInstance().removeAllExceptTop();

        initUI();

        mMarketRbtn.setChecked(true);
    }

    @Override
    public void initProgressDialog() {

    }

    private void initUI(){

        mManager= getSupportFragmentManager();

        mFragmentFly= findViewById(R.id.home_fly_fragment);
        mMarketRbtn= findViewById(R.id.home_rbtn_market);
        mMarketRbtn.setOnCheckedChangeListener(this);
        mDealRbtn= findViewById(R.id.home_rbtn_deal);
        mDealRbtn.setOnCheckedChangeListener(this);
        mMineRbtn= findViewById(R.id.home_rbtn_mine);
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

    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                showShortToast("再按一次退出程序");
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                MyApplication.getInstance().AppExit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
