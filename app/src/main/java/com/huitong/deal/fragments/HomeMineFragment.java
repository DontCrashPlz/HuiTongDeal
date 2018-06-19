package com.huitong.deal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.huitong.deal.R;
import com.huitong.deal.activities.AboutUsActivity;
import com.huitong.deal.activities.BillActivity;
import com.huitong.deal.activities.ChongZhiActivity;
import com.huitong.deal.activities.LoginActivity;
import com.huitong.deal.activities.LoginPasswordActivity;
import com.huitong.deal.activities.PayPasswordActivity;
import com.huitong.deal.activities.RealNameActivity;
import com.huitong.deal.activities.TiXianActivity;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.UserInfoDataEntity;
import com.huitong.deal.store.store_activities.StoreHomeActivity;
import com.zheng.zchlibrary.apps.ActivityManager;
import com.zheng.zchlibrary.apps.BaseFragment;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zheng.zchlibrary.utils.SharedPrefUtils;
import com.zheng.zchlibrary.utils.TimeUtils;
import com.zheng.zchlibrary.utils.Tools;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/4/13.
 */

public class HomeMineFragment extends BaseFragment implements View.OnClickListener {

    public static HomeMineFragment newInstance(String content){
        HomeMineFragment instance = new HomeMineFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        instance.setArguments(args);
        return instance;
    }

    private ImageView mUserIconIv;
    private TextView mUserNameTv;
    private TextView mMobileTv;
    private TextView mTimeTv;
    private TextView mBalanceTv;
    private TextView mSecurityTv;

    private RelativeLayout mChongZhiBtn;
    private RelativeLayout mTiXianBtn;
    private RelativeLayout mBillBtn;
    private RelativeLayout mReturnBtn;
    private RelativeLayout mLogoutBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_home_mine2, container, false);

        initUI(mView);

        if (MyApplication.appUser== null) return mView;

        Glide.with(getRealContext()).load(MyApplication.appUser.getUserinfo().getHeadimgurl()).into(mUserIconIv);
        mUserNameTv.setText(MyApplication.appUser.getNickname());
        mMobileTv.setText(MyApplication.appUser.getMobile());
        mTimeTv.setText(TimeUtils.getUSATime());
        mBalanceTv.setText(
                String.format(
                        getString(R.string.home_mine_balance),
                        String.valueOf(MyApplication.appUser.getUserinfo().getAvailablebalance())));
        mSecurityTv.setText(
                String.format(
                        getString(R.string.home_mine_tihuo),
                        String.valueOf(MyApplication.appUser.getUserinfo().getIntegral())));

        return mView;
    }

    private void initUI(View view) {

        mUserIconIv= view.findViewById(R.id.store_mine_usericon);
        mUserNameTv= view.findViewById(R.id.store_mine_username);
        mMobileTv= view.findViewById(R.id.store_mine_mobile);
        mTimeTv= view.findViewById(R.id.store_mine_time);
        mBalanceTv= view.findViewById(R.id.home_mine_balance);
        mSecurityTv= view.findViewById(R.id.home_mine_security);

        mTiXianBtn= view.findViewById(R.id.home_mine_btn_tixian);
        mTiXianBtn.setOnClickListener(this);
        mChongZhiBtn= view.findViewById(R.id.home_mine_btn_chongzhi);
        mChongZhiBtn.setOnClickListener(this);
        mBillBtn= view.findViewById(R.id.home_mine_btn_bill);
        mBillBtn.setOnClickListener(this);
        mReturnBtn= view.findViewById(R.id.home_mine_btn_return);
        mReturnBtn.setOnClickListener(this);
        mLogoutBtn= view.findViewById(R.id.home_mine_btn_logout);
        mLogoutBtn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getInstance().refreshUser(new IAsyncLoadListener<UserInfoDataEntity>() {
            @Override
            public void onSuccess(UserInfoDataEntity userInfoDataEntity) {
                Glide.with(getRealContext()).load(userInfoDataEntity.getUserinfo().getHeadimgurl()).into(mUserIconIv);
                mUserNameTv.setText(userInfoDataEntity.getNickname());
                mMobileTv.setText(userInfoDataEntity.getMobile());
                mBalanceTv.setText(
                        String.format(
                                getString(R.string.home_mine_balance),
                                String.valueOf(userInfoDataEntity.getUserinfo().getAvailablebalance())));
                mSecurityTv.setText(
                        String.format(
                                getString(R.string.home_mine_tihuo),
                                String.valueOf(userInfoDataEntity.getUserinfo().getIntegral())));
            }

            @Override
            public void onFailure(String msg) {
                mUserNameTv.setText("未知");
                mMobileTv.setText("未知");
                mTimeTv.setText(TimeUtils.getUSATime());
                mBalanceTv.setText(
                        String.format(
                                getString(R.string.home_mine_balance),
                                "未知"));
                mSecurityTv.setText(
                        String.format(
                                getString(R.string.home_mine_tihuo),
                                "未知"));
            }
        });
    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.home_mine_btn_bill:
                startActivity(new Intent(getRealContext(), BillActivity.class));
                break;
            case R.id.home_mine_btn_tixian:
                startActivity(new Intent(getRealContext(), TiXianActivity.class));
                break;
            case R.id.home_mine_btn_chongzhi:
                startActivity(new Intent(getRealContext(), ChongZhiActivity.class));
                break;
            case R.id.home_mine_btn_return:
                Intent storeIntent= new Intent(getRealContext(), StoreHomeActivity.class);
                storeIntent.putExtra("launcher_tag", 1);
                startActivity(storeIntent);
                break;
            case R.id.home_mine_btn_logout:
                SharedPrefUtils.remove(getRealContext(), MyApplication.TOKEN_TAG);
                MyApplication.getInstance().AppExit();
                break;
            default:
                break;
        }
    }

    @Override
    public void initProgressDialog() {

    }
}
