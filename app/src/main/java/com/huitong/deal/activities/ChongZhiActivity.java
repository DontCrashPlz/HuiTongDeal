package com.huitong.deal.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.widgets.MyPayPsdInputView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/16.
 */

public class ChongZhiActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFunctionTv;

    private EditText mMoneyEt;
    private RelativeLayout mKuaiJieRly;
    private RelativeLayout mAlipayRly;
    private RelativeLayout mWeXinRly;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        initUI();
    }

    private void initUI() {
        mBackIv = (ImageView) findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv = (TextView) findViewById(R.id.toolbar_title);
        mTitleTv.setText("充值");
        mFunctionTv = (TextView) findViewById(R.id.toolbar_right_text);
        mFunctionTv.setVisibility(View.VISIBLE);
        mFunctionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getRealContext(), ChongZhiHistoryActivity.class));
            }
        });

        mMoneyEt = (EditText) findViewById(R.id.chongzhi_money);
        mKuaiJieRly = (RelativeLayout) findViewById(R.id.chongzhi_kuajie);
        mKuaiJieRly.setOnClickListener(this);
        mAlipayRly = (RelativeLayout) findViewById(R.id.chongzhi_alipay);
        mAlipayRly.setOnClickListener(this);
        mWeXinRly = (RelativeLayout) findViewById(R.id.chongzhi_weixin);
        mWeXinRly.setOnClickListener(this);


    }

    private void doTiXian(String name, String bankCard, String money, String payPassword){
        String token= MyApplication.getInstance().getToken();
        if (token!= null && token.length()> 0){
            addNetWork(Network.getInstance().tiXian(
                    token,
                    name,
                    bankCard,
                    money,
                    payPassword)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<HttpResult<String>>() {
                        @Override
                        public void accept(HttpResult<String> stringHttpResult) throws Exception {
                            if ("error".equals(stringHttpResult.getStatus())){
                                showShortToast(stringHttpResult.getDescription());
                            }else if ("success".equals(stringHttpResult.getStatus())){
                                //todo 提现成功
                            }
                        }
                    }));
        }
    }


    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.chongzhi_kuajie:
                break;
            case R.id.chongzhi_alipay:
                break;
            case R.id.chongzhi_weixin:
                break;
            default:
                break;
        }
    }
}
