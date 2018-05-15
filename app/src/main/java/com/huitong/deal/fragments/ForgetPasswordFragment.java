package com.huitong.deal.fragments;

import android.content.DialogInterface;
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
                                dismissDialog();
                                if ("error".equals(stringHttpResult.getStatus())){
                                    showShortToast(stringHttpResult.getDescription());
                                }else if ("success".equals(stringHttpResult.getStatus())){
                                    showShortToast("密码已重置");
                                    ((LoginActivity)getActivity()).changeFragment(LoginActivity.FRAGMENT_TAG_PASSWORD);
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
        dialog.setLabel("正在重置密码...");
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                clearNetWork();
            }
        });
    }
}
