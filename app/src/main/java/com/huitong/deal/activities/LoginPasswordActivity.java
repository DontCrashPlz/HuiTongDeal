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
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/16.
 */

public class LoginPasswordActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;

    private EditText mPasswordEt1;
    private EditText mPasswordEt2;
    private EditText mPasswordEt3;

    private Button mConfirmBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);

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
        mTitleTv.setText("设置登录密码");

        mPasswordEt1= (EditText) findViewById(R.id.resetpassword_et_password1);
        mPasswordEt2= (EditText) findViewById(R.id.resetpassword_et_password2);
        mPasswordEt3= (EditText) findViewById(R.id.resetpassword_et_password3);

        mConfirmBtn= (Button) findViewById(R.id.resetpassword_btn_confirm);
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1= mPasswordEt1.getText().toString().trim();
                String password2= mPasswordEt2.getText().toString().trim();
                String password3= mPasswordEt3.getText().toString().trim();

                if (password1== null || password1.length()< 1){
                    showShortToast("请输入旧密码");
                    return;
                }
                if (password2== null || password2.length()< 1) {
                    showShortToast("请输入新的密码");
                    return;
                }
                if (password3== null || password3.length()< 1){
                    showShortToast("请再次输入新的密码");
                    return;
                }
                if (!(password2.equals(password3))){
                    showShortToast("您两次输入的密码不一致");
                    return;
                }

                String token= MyApplication.getInstance().getToken();
                if (token!= null && token.length()> 0){
                    addNetWork(Network.getInstance().modifyPassword(
                            token,
                            password1,
                            password2,
                            password3)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<HttpResult<String>>() {
                                @Override
                                public void accept(HttpResult<String> stringHttpResult) throws Exception {
                                    if ("error".equals(stringHttpResult.getStatus())) {
                                        showShortToast(stringHttpResult.getDescription());
                                    } else if ("success".equals(stringHttpResult.getStatus())) {
                                        showShortToast("修改完成");
                                        finish();
                                    }
                                }
                            }));
                }
            }
        });
    }
}
