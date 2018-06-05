package com.huitong.deal.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.SharedPrefUtils;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/13.
 */

public class LoginWithPasswordFragment extends BaseFragment implements View.OnClickListener {

    public static final String SP_TAG_USERNAME= "huitong_username";
    public static final String SP_TAG_PASSWORD= "huitong_password";
    public static final String SP_TAG_REMEMBER= "huitong_remember";

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
    private CheckBox mSeePasswordCb;
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
        mSeePasswordCb= mView.findViewById(R.id.login_cb_see_password);
        mSeePasswordCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mPasswordEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                }else {
                    mPasswordEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        mRememberCb= mView.findViewById(R.id.login_cb_remember2);

        if (SharedPrefUtils.contains(getRealContext(), SP_TAG_USERNAME)){
            String savedUserName= (String) SharedPrefUtils.get(getRealContext(), SP_TAG_USERNAME, "");
            if (savedUserName!= null && savedUserName.length()> 0){
                mUserNameEt.setText(savedUserName);
            }
        }
        if (SharedPrefUtils.contains(getRealContext(), SP_TAG_REMEMBER)){
            boolean isRemember= (boolean) SharedPrefUtils.get(getRealContext(), SP_TAG_REMEMBER, false);
            if (isRemember){
                mRememberCb.setChecked(true);
                if (SharedPrefUtils.contains(getRealContext(), SP_TAG_PASSWORD)){
                    String savedPassword= (String) SharedPrefUtils.get(getRealContext(), SP_TAG_PASSWORD, "");
                    if (savedPassword!= null && savedPassword.length()> 0){
                        mPasswordEt.setText(savedPassword);
                    }
                }
            }else {
                mRememberCb.setChecked(false);
            }
        }

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
                final String userName= mUserNameEt.getText().toString().trim();
                final String password= mPasswordEt.getText().toString().trim();
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
                                dismissDialog();
                                if ("error".equals(loginEntityHttpResult.getStatus())) {
                                    showShortToast(loginEntityHttpResult.getDescription());
                                } else if ("success".equals(loginEntityHttpResult.getStatus())) {
                                    String token = loginEntityHttpResult.getData().getApptoken();
                                    Log.e("token", token);
                                    if (token == null || token.length() < 1) {
                                        showShortToast("登录错误：token无效");
                                    } else {
                                        SharedPrefUtils.put(getRealContext(), SP_TAG_USERNAME, userName);
                                        if (mRememberCb.isChecked()) {
                                            SharedPrefUtils.put(getRealContext(), SP_TAG_REMEMBER, true);
                                            SharedPrefUtils.put(getRealContext(), SP_TAG_PASSWORD, password);
                                        } else {
                                            SharedPrefUtils.put(getRealContext(), SP_TAG_REMEMBER, false);
                                            if (SharedPrefUtils.contains(getRealContext(), SP_TAG_PASSWORD)) {
                                                SharedPrefUtils.remove(getContext(), SP_TAG_PASSWORD);
                                            }
                                        }
                                        MyApplication.getInstance().setToken(token);
                                        getRealContext().startActivity(new Intent(getActivity(), HomeActivity.class));
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
