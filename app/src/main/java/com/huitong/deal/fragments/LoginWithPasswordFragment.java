package com.huitong.deal.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.zheng.zchlibrary.widgets.MyPayPsdInputView;

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

    private Button testButton;

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

        testButton= mView.findViewById(R.id.button_test);
        testButton.setOnClickListener(this);

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
            case R.id.button_test:
                showNextDialog(getRealContext());
                break;
            default:
                break;
        }
    }

    private void showNextDialog(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_paypass_dialog, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(context, R.style.custom_dialog_no_titlebar);
        dialog.setContentView(view);
        dialog.show();

        final MyPayPsdInputView payPsdInputView= view.findViewById(R.id.paypass_dialog_et_password);

        // 监听
        view.findViewById(R.id.paypass_dialog_btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShortToast("取消");
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.paypass_dialog_btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShortToast(payPsdInputView.getPasswordString());
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
}
