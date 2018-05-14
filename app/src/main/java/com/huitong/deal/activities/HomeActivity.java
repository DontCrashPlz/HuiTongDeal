package com.huitong.deal.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
        initUI();

        mMarketRbtn.setChecked(true);

        String appToken= MyApplication.getInstance().getToken();
        if (appToken!= null && appToken.length()> 0){
            addNetWork(Network.getInstance().getUserInfo(appToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<HttpResult<UserInfoDataEntity>>() {
                        @Override
                        public void accept(HttpResult<UserInfoDataEntity> userInfoDataEntityHttpResult) throws Exception {
                            if ("error".equals(userInfoDataEntityHttpResult.getStatus())){
                                showShortToast(userInfoDataEntityHttpResult.getDescription());
                            }else if ("success".equals(userInfoDataEntityHttpResult.getStatus())){
                                if (userInfoDataEntityHttpResult.getData()!= null)
                                    MyApplication.appUser= userInfoDataEntityHttpResult.getData();
                                if (mMineRbtn.isChecked()){
                                    Fragment fragment= mManager.findFragmentById(R.id.home_fly_fragment);

                                }
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            LogUtil.d("throwable", throwable.toString());
                            showShortToast("网络请求失败");
                        }
                    }));
        }
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
