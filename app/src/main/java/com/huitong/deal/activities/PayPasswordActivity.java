package com.huitong.deal.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.MyPayPsdInputView;

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
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                LogUtil.d("throwable", throwable.toString());
                                showShortToast("网络请求失败");
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

                showNextDialog(getRealContext(), verification, mobile, loginPassword);
            }
        });
    }

    private void doSetPayPass(String smsCode, String mobile, String password, String payPassword){
        String token= MyApplication.getInstance().getToken();
        if (token!= null && token.length()> 0){
            addNetWork(Network.getInstance().setPayPassword(
                    token,
                    smsCode,
                    mobile,
                    password,
                    payPassword)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<HttpResult<String>>() {
                        @Override
                        public void accept(HttpResult<String> stringHttpResult) throws Exception {
                            if ("error".equals(stringHttpResult.getStatus())){
                                showShortToast(stringHttpResult.getDescription());
                            }else if ("success".equals(stringHttpResult.getStatus())){
                                showSuccessDialog(getRealContext());
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

    private void showNextDialog(Context context, final String smsCode, final String mobile, final String password){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_paypass_dialog, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(context, R.style.custom_dialog_no_titlebar);
        dialog.setContentView(view);
        dialog.show();

        final MyPayPsdInputView payPsdInputView= view.findViewById(R.id.paypass_dialog_et_password);
        final TextView tipTv= view.findViewById(R.id.paypass_dialog_tv_tip);

        // 监听
        view.findViewById(R.id.paypass_dialog_btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.paypass_dialog_btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payPass= payPsdInputView.getPasswordString();
                if (payPass== null || payPass.length()!= 6){
                    tipTv.setVisibility(View.VISIBLE);
                    return;
                }
                doSetPayPass(smsCode, mobile, password, payPass);
                dialog.dismiss();
            }
        });

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    private void showSuccessDialog(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_paypass_success_dialog, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(context, R.style.custom_dialog_no_titlebar);
        dialog.setContentView(view);
        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

}
