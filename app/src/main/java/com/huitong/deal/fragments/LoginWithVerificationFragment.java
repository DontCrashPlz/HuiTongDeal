package com.huitong.deal.fragments;

import android.content.DialogInterface;
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
import com.huitong.deal.store.store_fragments.StoreHomeMineFragment;
import com.zheng.zchlibrary.apps.BaseFragment;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
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
                                    changeVerificationBtn();
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                LogUtil.d("throwable", throwable.toString());
                                showShortToast("网络请求失败");
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
                                dismissDialog();
                                if ("error".equals(loginEntityHttpResult.getStatus())){
                                    showShortToast(loginEntityHttpResult.getDescription());
                                }else if ("success".equals(loginEntityHttpResult.getStatus())){
                                    String token= loginEntityHttpResult.getData().getApptoken();
                                    if (token== null || token.length()< 1){
                                        showShortToast("登录错误：token无效");
                                    }else {
                                        MyApplication.getInstance().setToken(token);
                                        //getRealContext().startActivity(new Intent(getActivity(), HomeActivity.class));
                                        MyApplication.getInstance().refreshUser(null);
                                        getActivity().setResult(StoreHomeMineFragment.LOGIN_SUCCESS_RESULT_CODE);
                                        getActivity().finish();
                                    }
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dismissDialog();
                                LogUtil.d("throwable", throwable.toString());
                                showShortToast("网络请求失败");
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                dismissDialog();
                            }
                        }, new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                showDialog();
                            }
                        }));
                break;
            default:
                break;
        }
    }

    private void changeVerificationBtn(){
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        addNetWork(d);
                        disposable= d;
                    }

                    @Override
                    public void onNext(Long value) {
                        long i= 30 - value;
                        mGetVerificationBtn.setText(String.valueOf(i));
                        if (i== 0){
                            mGetVerificationBtn.setBackground(getRealContext().getResources().getDrawable(R.drawable.button_background_green_corners_selector));
                            mGetVerificationBtn.setClickable(true);
                            mGetVerificationBtn.setText("获取验证码");
                            disposable.dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在登录...");
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                clearNetWork();
            }
        });
    }
}
