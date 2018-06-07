package com.huitong.deal.store.store_activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.huitong.deal.R;
import com.zheng.zchlibrary.apps.BaseActivity;

/**
 * Created by Zheng on 2018/6/7.
 */

public class StoreMineWalletActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFuncTv;

    private TextView mGouWuQuanTv;
    private TextView mTiHuoQuanTv;

    private FrameLayout mBuyBtn;
    private FrameLayout mTiXianBtn;
    private FrameLayout mBillBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_mine_wallet);

        initUI();
    }

    private void initUI() {
        mBackIv= findViewById(R.id.toolbar_back);
        mBackIv.setOnClickListener(this);
        mTitleTv= findViewById(R.id.toolbar_title);
        mTitleTv.setText("我的钱包");
        mFuncTv= findViewById(R.id.toolbar_function);
        mFuncTv.setVisibility(View.INVISIBLE);

        mGouWuQuanTv= findViewById(R.id.wallet_gouwuquan);
        mTiHuoQuanTv= findViewById(R.id.wallet_tihuoquan);

        mBuyBtn= findViewById(R.id.wallet_btn_buy);
        mBuyBtn.setOnClickListener(this);
        mTiXianBtn= findViewById(R.id.wallet_btn_tixian);
        mTiXianBtn.setOnClickListener(this);
        mBillBtn= findViewById(R.id.wallet_btn_bill);
        mBillBtn.setOnClickListener(this);
    }

    @Override
    public void initProgressDialog() {

    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case  R.id.toolbar_back:{
                finish();
                break;
            }
            case  R.id.wallet_btn_buy:{
                //todo 转到充值页面
                break;
            }
            case  R.id.wallet_btn_tixian:{
                //todo 转到提现页面
                break;
            }
            case  R.id.wallet_btn_bill:{
                //todo 转到账单页面
                break;
            }
        }
    }
}
