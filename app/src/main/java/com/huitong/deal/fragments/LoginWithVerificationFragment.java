package com.huitong.deal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.huitong.deal.R;
import com.huitong.deal.activities.HomeActivity;
import com.huitong.deal.activities.LoginActivity;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.LoginEntity;
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

public class LoginWithVerificationFragment extends BaseFragment implements View.OnClickListener {

    public static LoginWithVerificationFragment newInstance(String content){
        LoginWithVerificationFragment instance = new LoginWithVerificationFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        instance.setArguments(args);
        return instance;
    }

    private EditText mUserNameEt;
    private EditText mVerificationEt;
    private Button mLoginWithAccountBtn;
    private Button mGetVerificationBtn;
    private Button mLoginBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_login_with_verification, container, false);

        mUserNameEt= mView.findViewById(R.id.login_et_username);
        mVerificationEt= mView.findViewById(R.id.login_et_verification);

        mLoginWithAccountBtn= mView.findViewById(R.id.login_btn_password);
        mLoginWithAccountBtn.setOnClickListener(this);

        mGetVerificationBtn= mView.findViewById(R.id.login_btn_get_verification);
        mGetVerificationBtn.setOnClickListener(this);

        mLoginBtn= mView.findViewById(R.id.login_btn_login);
        mLoginBtn.setOnClickListener(this);

        return mView;
    }

    @Override
    public void onClick(View v) {
        int vid= v.getId();
        switch (vid){
            case R.id.login_btn_password:
                ((LoginActivity)getActivity()).changeFragment(LoginActivity.FRAGMENT_TAG_PASSWORD);
                break;
            case R.id.login_btn_get_verification:
                String mobile= mUserNameEt.getText().toString().trim();
                if (mobile== null || mobile.length()!= 11){
                    showShortToast("请正确输入您的手机号码");
                    return;
                }
                addNetWork(Network.getInstance().getVerificationCode(mobile, NetParams.VERIFICATION_USERTYPT_LOGIN)
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
                break;
            case R.id.login_btn_login:
                String userName= mUserNameEt.getText().toString().trim();
                String verification= mVerificationEt.getText().toString().trim();
                if (userName== null || userName.length()!= 11){
                    showShortToast("请正确输入您的手机号码");
                    return;
                }
                if (verification== null || verification.length()< 1){
                    showShortToast("请输入验证码");
                    return;
                }
                addNetWork(Network.getInstance().doLoginWithMobile(userName, verification)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<HttpResult<LoginEntity>>() {
                            @Override
                            public void accept(HttpResult<LoginEntity> loginEntityHttpResult) throws Exception {
                                if ("error".equals(loginEntityHttpResult.getStatus())){
                                    showShortToast(loginEntityHttpResult.getDescription());
                                }else if ("success".equals(loginEntityHttpResult.getStatus())){
                                    String token= loginEntityHttpResult.getData().getApptoken();
                                    if (token== null || token.length()< 1){
                                        showShortToast("登录错误：token无效");
                                    }else {
                                        MyApplication.getInstance().setToken(token);
                                        getRealContext().startActivity(new Intent(getActivity(), HomeActivity.class));
                                        getActivity().finish();
                                    }
                                }
                            }
                        }));
                break;
            default:
                break;
        }
    }
}
