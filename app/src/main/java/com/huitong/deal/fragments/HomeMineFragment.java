package com.huitong.deal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.huitong.deal.store.store_activities.StoreTestActivity;
import com.zheng.zchlibrary.apps.BaseFragment;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zheng.zchlibrary.utils.SharedPrefUtils;
import com.zheng.zchlibrary.utils.Tools;

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

    private TextView mBillTv;

    private TextView mBalanceTv;
    private TextView mSecurityTv;
    private Button mTiXianBtn;
    private Button mChongZhiBtn;
    private RelativeLayout mRealNameRly;
    private RelativeLayout mLoginPassRly;
    private RelativeLayout mPayPassRly;
    private RelativeLayout mAboutUsRly;
    private RelativeLayout mLogoutRly;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_home_mine, container, false);

        initUI(mView);

        mView.findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getRealContext(), StoreTestActivity.class));
            }
        });

        return mView;
    }

    private void initUI(View view) {

        mBillTv= view.findViewById(R.id.toolbar_right_icon);
        mBillTv.setOnClickListener(this);

        mBalanceTv= view.findViewById(R.id.home_mine_balance);
        mSecurityTv= view.findViewById(R.id.home_mine_security);

        mTiXianBtn= view.findViewById(R.id.home_mine_btn_tixian);
        mTiXianBtn.setOnClickListener(this);
        mChongZhiBtn= view.findViewById(R.id.home_mine_btn_chongzhi);
        mChongZhiBtn.setOnClickListener(this);
        mRealNameRly= view.findViewById(R.id.home_mine_rly_realname);
        mRealNameRly.setOnClickListener(this);
        mLoginPassRly= view.findViewById(R.id.home_mine_rly_loginpass);
        mLoginPassRly.setOnClickListener(this);
        mPayPassRly= view.findViewById(R.id.home_mine_rly_paypass);
        mPayPassRly.setOnClickListener(this);
        mAboutUsRly= view.findViewById(R.id.home_mine_rly_aboutus);
        mAboutUsRly.setOnClickListener(this);
        mLogoutRly= view.findViewById(R.id.home_mine_rly_logout);
        mLogoutRly.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        addNetWork(MyApplication.getInstance().refreshUser(new IAsyncLoadListener<UserInfoDataEntity>() {
            @Override
            public void onSuccess(UserInfoDataEntity userInfoDataEntity) {
                mBalanceTv.setText(String.format(
                        getString(R.string.home_mine_balance),
                        Tools.formatFloat(userInfoDataEntity.getUserinfo().getAvailablebalance())));
                mSecurityTv.setText(String.format(
                        getString(R.string.home_mine_tihuo),
                        Tools.formatFloat(userInfoDataEntity.getUserinfo().getIntegral())));
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
            }
        }));
    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.toolbar_right_icon:
                startActivity(new Intent(getRealContext(), BillActivity.class));
                break;
            case R.id.home_mine_btn_tixian:
                startActivity(new Intent(getRealContext(), TiXianActivity.class));
                break;
            case R.id.home_mine_btn_chongzhi:
                startActivity(new Intent(getRealContext(), ChongZhiActivity.class));
                break;
            case R.id.home_mine_rly_realname:
                startActivity(new Intent(getRealContext(), RealNameActivity.class));
                break;
            case R.id.home_mine_rly_loginpass:
                startActivity(new Intent(getRealContext(), LoginPasswordActivity.class));
                break;
            case R.id.home_mine_rly_paypass:
                startActivity(new Intent(getRealContext(), PayPasswordActivity.class));
                break;
            case R.id.home_mine_rly_aboutus:
                Intent aboutIntent= new Intent(getRealContext(), AboutUsActivity.class);
                aboutIntent.putExtra(AboutUsActivity.LAUNCH_TAG, AboutUsActivity.LAUNCH_TAG_ABOUTUS);
                startActivity(aboutIntent);
                break;
            case R.id.home_mine_rly_logout:
                SharedPrefUtils.remove(getRealContext(), MyApplication.TOKEN_TAG);
                Intent intent= new Intent(getRealContext(), LoginActivity.class);
                intent.putExtra("launcher_tag", 1);
                startActivity(intent);
                showShortToast("注销成功");
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void initProgressDialog() {

    }
}
