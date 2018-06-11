package com.huitong.deal.store.store_fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.widget.MsgView;
import com.huitong.deal.R;
import com.huitong.deal.activities.LoginActivity;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.DealTableEntity;
import com.huitong.deal.beans.UserInfoDataEntity;
import com.huitong.deal.store.store_activities.FeedBackActivity;
import com.huitong.deal.store.store_activities.StoreLoginPasswordActivity;
import com.huitong.deal.store.store_activities.StoreMineAddressActivity;
import com.huitong.deal.store.store_activities.StoreMineWalletActivity;
import com.huitong.deal.store.store_activities.StoreOrderListActivity;
import com.huitong.deal.store.store_activities.StorePayPasswordActivity;
import com.huitong.deal.store.store_activities.StoreRealNameActivity;
import com.huitong.deal.store.store_activities.StoreSecretPortalActivity;
import com.huitong.deal.store.store_activities.StoreUpdateUserInfoActivity;
import com.zheng.zchlibrary.apps.BaseFragment;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.SharedPrefUtils;
import com.zheng.zchlibrary.utils.TimeUtils;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/4/13.
 */

public class StoreHomeMineFragment extends BaseFragment implements View.OnClickListener {

    public static final int LOGIN_REQUEST_CODE= 100;//跳转到登录界面的请求码
    public static final int LOGIN_SUCCESS_RESULT_CODE= 101;//登录成功返回的结果码

    public static StoreHomeMineFragment newInstance(String content){
        StoreHomeMineFragment instance = new StoreHomeMineFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        instance.setArguments(args);
        return instance;
    }

    private ImageView mUserIconIv;
    private TextView mUserNameTv;
    private TextView mMobileTv;
    private TextView mTimeTv;
    private TextView mGouWuQuanTv;
    private TextView mTiHuoQuanTv;

    private FrameLayout mAllOrderPanel;
    private CommonTabLayout mTabLayout;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private FrameLayout mButton1;
    private FrameLayout mButton2;
    private FrameLayout mButton3;
    private FrameLayout mButton4;
    private FrameLayout mButton5;
    private FrameLayout mButton6;
    private FrameLayout mButton7;
    private FrameLayout mButton8;
    private FrameLayout mButton9;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.store_fragment_home_mine, container, false);

        initUI(mView);

        Glide.with(getRealContext()).load(MyApplication.appUser.getUserinfo().getHeadimgurl()).into(mUserIconIv);
        mUserNameTv.setText(MyApplication.appUser.getNickname());
        mMobileTv.setText(MyApplication.appUser.getMobile());
        mTimeTv.setText(TimeUtils.getUSATime());
        mGouWuQuanTv.setText(
                String.format(
                        getString(R.string.home_mine_balance),
                        String.valueOf(MyApplication.appUser.getUserinfo().getAvailablebalance())));
        mTiHuoQuanTv.setText(
                String.format(
                        getString(R.string.home_mine_tihuo),
                        String.valueOf(MyApplication.appUser.getUserinfo().getIntegral())));

        return mView;
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
                mGouWuQuanTv.setText(
                        String.format(
                                getString(R.string.home_mine_balance),
                                String.valueOf(userInfoDataEntity.getUserinfo().getAvailablebalance())));
                mTiHuoQuanTv.setText(
                        String.format(
                                getString(R.string.home_mine_tihuo),
                                String.valueOf(userInfoDataEntity.getUserinfo().getIntegral())));
                if (userInfoDataEntity.getOrderstatusstats().getOrdernum_waitpay()> 0){
                    mTabLayout.showMsg(0, userInfoDataEntity.getOrderstatusstats().getOrdernum_waitpay());
                }else {
                    mTabLayout.hideMsg(0);
                }
                if (userInfoDataEntity.getOrderstatusstats().getOrdernum_waitSend()> 0){
                    mTabLayout.showMsg(1, userInfoDataEntity.getOrderstatusstats().getOrdernum_waitSend());
                }else {
                    mTabLayout.hideMsg(1);
                }
                if (userInfoDataEntity.getOrderstatusstats().getOrdernum_waitReceive()> 0){
                    mTabLayout.showMsg(2, userInfoDataEntity.getOrderstatusstats().getOrdernum_waitReceive());
                }else {
                    mTabLayout.hideMsg(2);
                }
                if (userInfoDataEntity.getOrderstatusstats().getOrdernum_received()> 0){
                    mTabLayout.showMsg(3, userInfoDataEntity.getOrderstatusstats().getOrdernum_received());
                }else {
                    mTabLayout.hideMsg(3);
                }
            }

            @Override
            public void onFailure(String msg) {
                mUserNameTv.setText("未知");
                mMobileTv.setText("未知");
                mTimeTv.setText(TimeUtils.getUSATime());
                mGouWuQuanTv.setText(
                        String.format(
                                getString(R.string.home_mine_balance),
                                "未知"));
                mTiHuoQuanTv.setText(
                        String.format(
                                getString(R.string.home_mine_tihuo),
                                "未知"));
                mTabLayout.hideMsg(0);
                mTabLayout.hideMsg(1);
                mTabLayout.hideMsg(2);
                mTabLayout.hideMsg(3);
            }
        });
    }

    private void initUI(View view) {
        mUserIconIv= view.findViewById(R.id.store_mine_usericon);
        mUserNameTv= view.findViewById(R.id.store_mine_username);
        mMobileTv= view.findViewById(R.id.store_mine_mobile);
        mTimeTv= view.findViewById(R.id.store_mine_time);
        mGouWuQuanTv= view.findViewById(R.id.store_mine_gouwu);
        mTiHuoQuanTv= view.findViewById(R.id.store_mine_tihuo);

        mAllOrderPanel= view.findViewById(R.id.store_mine_panel_all_order);
        mAllOrderPanel.setOnClickListener(this);

        mTabLayout= view.findViewById(R.id.store_mine_tab);
        mTabEntities.add(new DealTableEntity("待付款", R.mipmap.pending_payment, R.mipmap.pending_payment));
        mTabEntities.add(new DealTableEntity("待发货", R.mipmap.user_consumption, R.mipmap.user_consumption));
        mTabEntities.add(new DealTableEntity("待收货", R.mipmap.receiving, R.mipmap.receiving));
        mTabEntities.add(new DealTableEntity("已收货", R.mipmap.received_select, R.mipmap.received_select));
        mTabLayout.setTabData(mTabEntities);
        //设置未读消息背景
        mTabLayout.setMsgMargin(0, -15, 5);
        mTabLayout.setMsgMargin(1, -15, 5);
        mTabLayout.setMsgMargin(2, -15, 5);
        mTabLayout.setMsgMargin(3, -15, 5);
        MsgView msgView0 = mTabLayout.getMsgView(0);
        if (msgView0 != null) {
            msgView0.setTextColor(Color.rgb(245,39,59));
            msgView0.setStrokeWidth(1);
            msgView0.setStrokeColor(Color.rgb(245,39,59));
            msgView0.setBackgroundColor(Color.WHITE);
        }
        MsgView msgView1 = mTabLayout.getMsgView(1);
        if (msgView1 != null) {
            msgView1.setTextColor(Color.rgb(245,39,59));
            msgView1.setStrokeWidth(1);
            msgView1.setStrokeColor(Color.rgb(245,39,59));
            msgView1.setBackgroundColor(Color.WHITE);
        }
        MsgView msgView2 = mTabLayout.getMsgView(2);
        if (msgView2 != null) {
            msgView2.setTextColor(Color.rgb(245,39,59));
            msgView2.setStrokeWidth(1);
            msgView2.setStrokeColor(Color.rgb(245,39,59));
            msgView2.setBackgroundColor(Color.WHITE);
        }
        MsgView msgView3 = mTabLayout.getMsgView(3);
        if (msgView3 != null) {
            msgView3.setTextColor(Color.rgb(245,39,59));
            msgView3.setStrokeWidth(1);
            msgView3.setStrokeColor(Color.rgb(245,39,59));
            msgView3.setBackgroundColor(Color.WHITE);
        }
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                LogUtil.e("onTabSelect", "" + position);
                if (position== 0){
                    //todo 跳转到待付款订单
                    Intent intent= new Intent(getRealContext(), StoreOrderListActivity.class);
                    intent.putExtra(StoreOrderListActivity.ORDER_TYPE_TAG, StoreOrderListActivity.ORDER_TYPE_WAITPAY);
                    startActivity(intent);
                }else if (position== 1){
                    //todo 跳转到待发货订单
                    Intent intent= new Intent(getRealContext(), StoreOrderListActivity.class);
                    intent.putExtra(StoreOrderListActivity.ORDER_TYPE_TAG, StoreOrderListActivity.ORDER_TYPE_WAITSEND);
                    startActivity(intent);
                }else if (position== 2){
                    //todo 跳转到待收货订单
                    Intent intent= new Intent(getRealContext(), StoreOrderListActivity.class);
                    intent.putExtra(StoreOrderListActivity.ORDER_TYPE_TAG, StoreOrderListActivity.ORDER_TYPE_WAITRECEIVE);
                    startActivity(intent);
                }else if (position== 3){
                    //todo 跳转到已收货订单
                    Intent intent= new Intent(getRealContext(), StoreOrderListActivity.class);
                    intent.putExtra(StoreOrderListActivity.ORDER_TYPE_TAG, StoreOrderListActivity.ORDER_TYPE_RECEIVED);
                    startActivity(intent);
                }
            }

            @Override
            public void onTabReselect(int position) {
                LogUtil.e("onTabReselect", "" + position);
                if (position== 0){
                    //todo 跳转到待付款订单
                    Intent intent= new Intent(getRealContext(), StoreOrderListActivity.class);
                    intent.putExtra(StoreOrderListActivity.ORDER_TYPE_TAG, StoreOrderListActivity.ORDER_TYPE_WAITPAY);
                    startActivity(intent);
                }else if (position== 1){
                    //todo 跳转到待发货订单
                    Intent intent= new Intent(getRealContext(), StoreOrderListActivity.class);
                    intent.putExtra(StoreOrderListActivity.ORDER_TYPE_TAG, StoreOrderListActivity.ORDER_TYPE_WAITSEND);
                    startActivity(intent);
                }else if (position== 2){
                    //todo 跳转到待收货订单
                    Intent intent= new Intent(getRealContext(), StoreOrderListActivity.class);
                    intent.putExtra(StoreOrderListActivity.ORDER_TYPE_TAG, StoreOrderListActivity.ORDER_TYPE_WAITRECEIVE);
                    startActivity(intent);
                }else if (position== 3){
                    //todo 跳转到已收货订单
                    Intent intent= new Intent(getRealContext(), StoreOrderListActivity.class);
                    intent.putExtra(StoreOrderListActivity.ORDER_TYPE_TAG, StoreOrderListActivity.ORDER_TYPE_RECEIVED);
                    startActivity(intent);
                }
            }
        });

        mButton1= view.findViewById(R.id.store_mine_btn1);
        mButton1.setOnClickListener(this);
        mButton2= view.findViewById(R.id.store_mine_btn2);
        mButton2.setOnClickListener(this);
        mButton3= view.findViewById(R.id.store_mine_btn3);
        mButton3.setOnClickListener(this);
        mButton4= view.findViewById(R.id.store_mine_btn4);
        mButton4.setOnClickListener(this);
        mButton5= view.findViewById(R.id.store_mine_btn5);
        mButton5.setOnClickListener(this);
        mButton6= view.findViewById(R.id.store_mine_btn6);
        mButton6.setOnClickListener(this);
        mButton7= view.findViewById(R.id.store_mine_btn7);
        mButton7.setOnClickListener(this);
        mButton8= view.findViewById(R.id.store_mine_btn8);
        mButton8.setOnClickListener(this);
        mButton9= view.findViewById(R.id.store_mine_btn9);
        mButton9.setOnClickListener(this);
    }

    @Override
    public void initProgressDialog() {

    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.store_mine_panel_all_order:{//全部订单
                Intent intent= new Intent(getRealContext(), StoreOrderListActivity.class);
                intent.putExtra(StoreOrderListActivity.ORDER_TYPE_TAG, StoreOrderListActivity.ORDER_TYPE_ALL);
                startActivity(intent);
                break;
            }
            case R.id.store_mine_btn1:{//我的钱包
                startActivity(new Intent(getRealContext(), StoreMineWalletActivity.class));
                break;
            }
            case R.id.store_mine_btn2:{//实名认证
                startActivity(new Intent(getRealContext(), StoreRealNameActivity.class));
                break;
            }
            case R.id.store_mine_btn3:{//修改资料
                startActivity(new Intent(getRealContext(), StoreUpdateUserInfoActivity.class));
                break;
            }
            case R.id.store_mine_btn4:{//收货地址管理
                startActivity(new Intent(getRealContext(), StoreMineAddressActivity.class));
                break;
            }
            case R.id.store_mine_btn5:{//修改登录密码
                startActivity(new Intent(getRealContext(), StoreLoginPasswordActivity.class));
                break;
            }
            case R.id.store_mine_btn6:{//修改交易密码
                startActivity(new Intent(getRealContext(), StorePayPasswordActivity.class));
                break;
            }
            case R.id.store_mine_btn7:{//特惠专区
                startActivity(new Intent(getRealContext(), StoreSecretPortalActivity.class));
                break;
            }
            case R.id.store_mine_btn8:{//帮助反馈
                startActivity(new Intent(getRealContext(), FeedBackActivity.class));
                break;
            }
            case R.id.store_mine_btn9:{//退出登录
                SharedPrefUtils.remove(getRealContext(), MyApplication.TOKEN_TAG);
                MyApplication.getInstance().AppExit();
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode== LOGIN_REQUEST_CODE && resultCode== LOGIN_SUCCESS_RESULT_CODE){
            //todo 登录成功！刷新页面
        }
    }
}
