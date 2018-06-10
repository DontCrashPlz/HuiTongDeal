package com.huitong.deal.store.store_activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/16.
 */

public class StorePayPasswordActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFuncTv;

    private LinearLayout mPanel1;
    private LinearLayout mPanel2;
    private LinearLayout mPanel3;

    private EditText mPasswordEt1;
    private EditText mPasswordEt2;
    private EditText mPasswordEt3;

    private Button mConfirmBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_pay_password);

        initUI();
    }

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在提交修改...");
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                clearNetWork();
            }
        });
    }

    private void initUI() {
        mBackIv= findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv= findViewById(R.id.toolbar_title);
        mTitleTv.setText("修改交易密码");
        mFuncTv= findViewById(R.id.toolbar_function);
        mFuncTv.setVisibility(View.INVISIBLE);

        mPanel1= findViewById(R.id.resetpassword_panel1);
        mPanel2= findViewById(R.id.resetpassword_panel2);
        mPanel3= findViewById(R.id.resetpassword_panel3);

        mPasswordEt1= findViewById(R.id.resetpassword_et_password1);
        mPasswordEt2= findViewById(R.id.resetpassword_et_password2);
        mPasswordEt3= findViewById(R.id.resetpassword_et_password3);

        mConfirmBtn= findViewById(R.id.resetpassword_btn_confirm);
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1= mPasswordEt1.getText().toString().trim();
                String password2= mPasswordEt2.getText().toString().trim();
                String password3= mPasswordEt3.getText().toString().trim();
                mPanel1.setBackgroundResource(R.drawable.store_edittext_background_white_corners);
                mPanel2.setBackgroundResource(R.drawable.store_edittext_background_white_corners);
                mPanel3.setBackgroundResource(R.drawable.store_edittext_background_white_corners);
                if (password1== null || password1.length()< 1){
                    mPanel1.setBackgroundResource(R.drawable.store_edittext_background_pink_corners);
                    return;
                }
                if (password2== null || password2.length()< 1) {
                    mPanel2.setBackgroundResource(R.drawable.store_edittext_background_pink_corners);
                    return;
                }
                if (password3== null || password3.length()< 1){
                    mPanel3.setBackgroundResource(R.drawable.store_edittext_background_pink_corners);
                    return;
                }
                if (!(password2.equals(password3))){
                    mPanel3.setBackgroundResource(R.drawable.store_edittext_background_pink_corners);
                    mPasswordEt3.setHint("两次输入的密码不一致");
                    return;
                }
                addNetWork(Network.getInstance().resetPayPass(
                        MyApplication.appToken,
                        password1,
                        password2,
                        password3)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<HttpResult<String>>() {
                            @Override
                            public void accept(HttpResult<String> stringHttpResult) throws Exception {
                                dismissDialog();
                                if ("error".equals(stringHttpResult.getStatus())) {
                                    showShortToast(stringHttpResult.getDescription());
                                } else if ("success".equals(stringHttpResult.getStatus())) {
                                    showShortToast("修改完成");
                                    finish();
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
    }
}
