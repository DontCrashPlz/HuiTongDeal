package com.huitong.deal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.huitong.deal.R;
import com.huitong.deal.activities.LoginActivity;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.VerificationCodeEntity;
import com.huitong.deal.https.NetParams;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/13.
 */

public class ForgetPasswordFragment extends BaseFragment {

    public static ForgetPasswordFragment newInstance(String content){
        ForgetPasswordFragment instance = new ForgetPasswordFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        instance.setArguments(args);
        return instance;
    }

    private EditText mUserNameEt;
    private EditText mVerificationEt;
    private EditText mPasswordEt;
    private Button mGetVerificationBtn;
    private Button mConfirmBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_login_forget, container, false);

        mUserNameEt= mView.findViewById(R.id.login_et_username);
        mVerificationEt= mView.findViewById(R.id.login_et_verification);
        mPasswordEt= mView.findViewById(R.id.login_et_password);

        mGetVerificationBtn= mView.findViewById(R.id.login_btn_get_verification);
        mGetVerificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile= mUserNameEt.getText().toString().trim();
                if (mobile== null || mobile.length()!= 11){
                    showShortToast("请正确输入您的手机号码");
                    return;
                }
                addNetWork(Network.getInstance().getVerificationCode(mobile, NetParams.VERIFICATION_USERTYPT_FINDPASS)
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
        mConfirmBtn= mView.findViewById(R.id.login_btn_confirm);
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile= mUserNameEt.getText().toString().trim();
                String verificationCode= mVerificationEt.getText().toString().trim();
                String password= mPasswordEt.getText().toString().trim();
                if (mobile== null || mobile.length()!= 11){
                    showShortToast("请正确输入您的手机号码");
                    return;
                }
                if (verificationCode== null || verificationCode.length()< 1){
                    showShortToast("请输入您的验证码");
                    return;
                }
                if (password== null || password.length()< 1){
                    showShortToast("请输入您的新密码");
                    return;
                }
                addNetWork(Network.getInstance().resetPassword(mobile, verificationCode, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<HttpResult<String>>() {
                            @Override
                            public void accept(HttpResult<String> stringHttpResult) throws Exception {
                                if ("error".equals(stringHttpResult.getStatus())){
                                    showShortToast(stringHttpResult.getDescription());
                                }else if ("success".equals(stringHttpResult.getStatus())){
                                    showShortToast("密码已重置");
                                    ((LoginActivity)getActivity()).changeFragment(LoginActivity.FRAGMENT_TAG_PASSWORD);
                                }
                            }
                        }));
            }
        });

        return mView;
    }
}
