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
import com.huitong.deal.activities.BillActivity;
import com.huitong.deal.activities.ChongZhiActivity;
import com.huitong.deal.activities.LoginActivity;
import com.huitong.deal.activities.LoginPasswordActivity;
import com.huitong.deal.activities.PayPasswordActivity;
import com.huitong.deal.activities.RealNameActivity;
import com.huitong.deal.activities.TiXianActivity;
import com.huitong.deal.apps.MyApplication;
import com.zheng.zchlibrary.apps.BaseFragment;

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

    private TextView mPropertyTv;
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

        return mView;
    }

    private void initUI(View view) {

        mBillTv= view.findViewById(R.id.toolbar_right_icon);
        mBillTv.setOnClickListener(this);

        mPropertyTv= view.findViewById(R.id.home_mine_property);
        mBalanceTv= view.findViewById(R.id.home_mine_balance);
        mSecurityTv= view.findViewById(R.id.home_mine_security);
        if (MyApplication.appUser!= null){
            if (MyApplication.appUser.getUserinfo()!= null){
                float availableBalance= MyApplication.appUser.getUserinfo().getAvailablebalance();
                float integral= MyApplication.appUser.getUserinfo().getIntegral();
                mPropertyTv.setText(String.valueOf(availableBalance + integral));
                mBalanceTv.setText(String.valueOf(availableBalance));
                mSecurityTv.setText(String.valueOf(integral));
            }
        }

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
                break;
            case R.id.home_mine_rly_logout:
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
