package com.huitong.deal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.activities.HomeActivity;
import com.huitong.deal.activities.LoginActivity;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.LoginEntity;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/13.
 */

public class LoginWithPasswordFragment extends BaseFragment implements View.OnClickListener {

    public static LoginWithPasswordFragment newInstance(String content){
        LoginWithPasswordFragment instance = new LoginWithPasswordFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        instance.setArguments(args);
        return instance;
    }

    private Button mLoginWithVerifivationBtn;
    private TextView mForgetTv;
    private Button mRegisterBtn;
    private Button mLoginBtn;
    private EditText mUserNameEt;
    private EditText mPasswordEt;
    private CheckBox mRememberCb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_login_with_password, container, false);

        mLoginWithVerifivationBtn= mView.findViewById(R.id.login_btn_verification);
        mLoginWithVerifivationBtn.setOnClickListener(this);

        mForgetTv= mView.findViewById(R.id.login_tv_forget);
        mForgetTv.setOnClickListener(this);

        mRegisterBtn= mView.findViewById(R.id.login_btn_register);
        mRegisterBtn.setOnClickListener(this);

        mLoginBtn= mView.findViewById(R.id.login_btn_login);
        mLoginBtn.setOnClickListener(this);

        mUserNameEt= mView.findViewById(R.id.login_et_username);
        mPasswordEt= mView.findViewById(R.id.login_et_password);
        mRememberCb= mView.findViewById(R.id.login_cb_remember2);

        return mView;
    }

    @Override
    public void onClick(View v) {
        int vid= v.getId();
        switch (vid){
            case R.id.login_btn_verification:
                ((LoginActivity)getActivity()).changeFragment(LoginActivity.FRAGMENT_TAG_VERIFICATION);
                break;
            case R.id.login_tv_forget:
                ((LoginActivity)getActivity()).changeFragment(LoginActivity.FRAGMENT_TAG_FORGET);
                break;
            case R.id.login_btn_register:
                ((LoginActivity)getActivity()).changeFragment(LoginActivity.FRAGMENT_TAG_SIGNIN);
                break;
            case R.id.login_btn_login:
                String userName= mUserNameEt.getText().toString().trim();
                String password= mPasswordEt.getText().toString().trim();
                if (userName== null || userName.length()< 1){
                    showShortToast("请输入用户名");
                    return;
                }
                if (password== null || password.length()< 1){
                    showShortToast("请输入密码");
                    return;
                }
                addNetWork(Network.getInstance().doLoginWithAccount(userName, password)
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
