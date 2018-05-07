package com.huitong.deal.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.VerificationCodeEntity;
import com.huitong.deal.https.NetParams;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/16.
 */

public class PayPasswordActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;

    private EditText mLoginPasswordEt;
    private EditText mMobileEt;
    private EditText mVerificationEt;
    private Button mGetVerificationBtn;

    private Button mNextBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_password);

        initUI();
    }

    private void initUI() {
        mBackIv= (ImageView) findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv= (TextView) findViewById(R.id.toolbar_title);
        mTitleTv.setText("设置交易密码");

        mLoginPasswordEt= (EditText) findViewById(R.id.paypass_et_loginpass);
        mMobileEt= (EditText) findViewById(R.id.paypass_et_mobile);
        mVerificationEt= (EditText) findViewById(R.id.paypass_et_verification);

        mGetVerificationBtn= (Button) findViewById(R.id.paypass_btn_get_verification);
        mGetVerificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile= mMobileEt.getText().toString().trim();
                if (mobile== null || mobile.length()< 1){
                    showShortToast("请输入您的手机号");
                    return;
                }
                addNetWork(Network.getInstance().getVerificationCode(mobile, NetParams.VERIFICATION_USERTYPT_SETPAYPASS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<HttpResult<VerificationCodeEntity>>() {
                            @Override
                            public void accept(HttpResult<VerificationCodeEntity> verificationCodeEntityHttpResult) throws Exception {
                                if ("error".equals(verificationCodeEntityHttpResult.getStatus())){
                                    showShortToast(verificationCodeEntityHttpResult.getDescription());
                                }else if ("success".equals(verificationCodeEntityHttpResult.getStatus())){
                                    mGetVerificationBtn.setBackground(getRealContext().getResources().getDrawable(R.drawable.button_background_gary_corners));
                                    mGetVerificationBtn.setClickable(false);
                                }
                            }
                        }));
            }
        });

        mNextBtn= (Button) findViewById(R.id.paypass_btn_next);
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginPassword= mLoginPasswordEt.getText().toString().trim();
                String mobile= mMobileEt.getText().toString().trim();
                String verification= mVerificationEt.getText().toString().trim();

                if (loginPassword== null || loginPassword.length()< 1){
                    showShortToast("请输入您的登录密码");
                    return;
                }
                if (mobile== null || mobile.length()< 1){
                    showShortToast("请输入您的手机号码");
                    return;
                }
                if (verification== null || verification.length()< 1){
                    showShortToast("请输入您的验证码");
                    return;
                }

                String token= MyApplication.getInstance().getToken();
//                if (token!= null && token.length()> 0){
//                    addNetWork(Network.getInstance().certifyRealName(
//                            token,
//                            name,
//                            idcard,
//                            sex,
//                            nation,
//                            address)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(new Consumer<HttpResult<String>>() {
//                                @Override
//                                public void accept(HttpResult<String> stringHttpResult) throws Exception {
//                                    if ("error".equals(stringHttpResult.getStatus())){
//                                        showShortToast(stringHttpResult.getDescription());
//                                    }else if ("success".equals(stringHttpResult.getStatus())){
//                                        showShortToast("认证完成");
//                                        finish();
//                                    }
//                                }
//                            }));
//                }
            }
        });
    }

    private void showNextDialog(){

    }

}
