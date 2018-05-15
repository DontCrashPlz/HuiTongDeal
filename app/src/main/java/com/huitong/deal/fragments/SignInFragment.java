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
import android.widget.CheckBox;
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
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/13.
 */

public class SignInFragment extends BaseFragment {

    public static SignInFragment newInstance(String content){
        SignInFragment instance = new SignInFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        instance.setArguments(args);
        return instance;
    }

    private EditText mMobileEt;
    private EditText mVerificationEt;
    private EditText mPasswordEt;
    private EditText mInviteCodeEt;
    private EditText mAddressEt;

    private Button mGetVerificationBtn;
    private Button mRegisterBtn;

    private CheckBox mReadedCb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_login_sign_in, container, false);

        mMobileEt= mView.findViewById(R.id.login_et_username);
        mVerificationEt= mView.findViewById(R.id.login_et_verification);
        mPasswordEt= mView.findViewById(R.id.login_et_password);
        mInviteCodeEt= mView.findViewById(R.id.login_et_invitecode);
        mAddressEt= mView.findViewById(R.id.login_et_address);

        mGetVerificationBtn= mView.findViewById(R.id.login_btn_get_verification);
        mGetVerificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile= mMobileEt.getText().toString().trim();
                if (mobile== null || mobile.length()!= 11){
                    showShortToast("请正确输入您的手机号码");
                    return;
                }
                addNetWork(Network.getInstance().getVerificationCode(mobile, NetParams.VERIFICATION_USERTYPT_REG)
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
            }
        });
        mRegisterBtn= mView.findViewById(R.id.login_btn_register);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mobile= mMobileEt.getText().toString().trim();
                final String verification= mVerificationEt.getText().toString().trim();
                final String password= mPasswordEt.getText().toString().trim();
                final String inviteCode= mInviteCodeEt.getText().toString().trim();
                final String address= mAddressEt.getText().toString().trim();
                if (mobile== null || mobile.length()!= 11){
                    showShortToast("请正确输入您的手机号码");
                    return;
                }
                if (verification== null || verification.length()< 1){
                    showShortToast("请输入验证码");
                    return;
                }
                if (password== null || password.length()< 1){
                    showShortToast("请设置登录密码");
                    return;
                }
                if (!mReadedCb.isChecked()){
                    showShortToast("请阅读并同意《汇通承诺》");
                    return;
                }

                addNetWork(Network.getInstance().isExistMobile(mobile)
                        .subscribeOn(Schedulers.io())//io线程上执行检查号码的网络请求
                        .observeOn(Schedulers.io())//io线程上处理注册网络请求
                        .flatMap(new Function<HttpResult<String>, ObservableSource<HttpResult<String>>>() {//根据检查号码的结果决定是否注册
                            @Override
                            public ObservableSource<HttpResult<String>> apply(HttpResult<String> stringHttpResult) throws Exception {
                                final HttpResult<String> result= new HttpResult<>();
                                if ("error".equals(stringHttpResult.getStatus())){
                                    result.setDescription(stringHttpResult.getDescription());
                                }else if ("success".equals(stringHttpResult.getStatus())){
                                    return Network.getInstance().doRigister(mobile, verification, password, inviteCode, address);
                                }
                                return new ObservableSource<HttpResult<String>>() {
                                    @Override
                                    public void subscribe(Observer<? super HttpResult<String>> observer) {
                                        result.setStatus("error");
                                        observer.onNext(result);
                                    }
                                };
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())//回到主线程处理注册结果
                        .subscribe(new Consumer<HttpResult<String>>() {
                            @Override
                            public void accept(HttpResult<String> stringHttpResult) throws Exception {
                                dismissDialog();
                                if ("error".equals(stringHttpResult.getStatus())){
                                    showShortToast(stringHttpResult.getDescription());
                                }else if ("success".equals(stringHttpResult.getStatus())){
                                    ((LoginActivity)getActivity()).changeFragment(LoginActivity.FRAGMENT_TAG_SIGNSUCCESS);
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

            }
        });

        mReadedCb= mView.findViewById(R.id.login_cb_agreement);
        mReadedCb.setChecked(true);

        return mView;
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
        dialog.setLabel("正在注册...");
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                clearNetWork();
            }
        });
    }
}
