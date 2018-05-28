package com.huitong.deal.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.UserInfoDataEntity;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.MyPayPsdInputView;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/16.
 */

public class TiXianActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFunctionTv;

    private EditText mNameEt;
    private EditText mBankCardEt;
    private EditText mKaiHuHangEt;
    private EditText mKaiHuZhiHangEt;
    private EditText mMoneyEt;
    private Button mCommitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_deposit);

        initUI();
    }

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在提交申请...");
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                clearNetWork();
            }
        });
    }

    private void initUI() {
        mBackIv = (ImageView) findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv = (TextView) findViewById(R.id.toolbar_title);
        mTitleTv.setText("提现");
        mFunctionTv = (TextView) findViewById(R.id.toolbar_right_text);
        mFunctionTv.setVisibility(View.VISIBLE);
        mFunctionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getRealContext(), TiXianHistoryActivity.class));
            }
        });

        mNameEt = (EditText) findViewById(R.id.tixian_et_name);
        mBankCardEt = (EditText) findViewById(R.id.tixian_et_bankcard);
        mKaiHuHangEt= (EditText) findViewById(R.id.tixian_et_kaihuhang);
        mKaiHuZhiHangEt= (EditText) findViewById(R.id.tixian_et_kaihuzhihang);
        mMoneyEt = (EditText) findViewById(R.id.tixian_et_money);
        if (MyApplication.appUser!= null){
            if (MyApplication.appUser.getUserinfo()!= null){
                float availableBalance= MyApplication.appUser.getUserinfo().getAvailablebalance();
                mMoneyEt.setHint(String.format(getString(R.string.hint_withdraw_deposit), String.valueOf(availableBalance)));
            }else {
                mMoneyEt.setHint("请输入提现金额");
            }
        }else {
            mMoneyEt.setHint("请输入提现金额");
        }
        mCommitBtn = (Button) findViewById(R.id.tixian_btn_commit);
        mCommitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= mNameEt.getText().toString().trim();
                String bankCard= mBankCardEt.getText().toString().trim();
                String money= mMoneyEt.getText().toString().trim();
                String bank= mKaiHuHangEt.getText().toString().trim();
                String bank_branch= mKaiHuZhiHangEt.getText().toString().trim();

                if (name== null || name.length()< 1){
                    showShortToast("请输入您的姓名");
                    return;
                }
                if (bankCard== null || bankCard.length()< 1){
                    showShortToast("请输入您的银行卡号");
                    return;
                }
                if (money== null || money.length()< 1){
                    showShortToast("请输入您的提现金额");
                    return;
                }
                if (bank== null || bank.length()< 1){
                    showShortToast("请输入您的开户行");
                    return;
                }
                if (bank_branch== null || bank_branch.length()< 1){
                    showShortToast("请输入您的开户支行");
                    return;
                }

                showNextDialog(getRealContext(), name, bankCard, money, bank, bank_branch);
            }
        });
    }

    private void doTiXian(String name, String bankCard, String money, String payPassword, String bank, String bank_branch){
        String token= MyApplication.getInstance().getToken();
        if (token!= null && token.length()> 0){
            addNetWork(Network.getInstance().tiXian(
                    token,
                    name,
                    bankCard,
                    money,
                    payPassword,
                    bank,
                    bank_branch)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<HttpResult<String>>() {
                        @Override
                        public void accept(HttpResult<String> stringHttpResult) throws Exception {
                            dismissDialog();
                            if ("error".equals(stringHttpResult.getStatus())){
                                showShortToast(stringHttpResult.getDescription());
                            }else if ("success".equals(stringHttpResult.getStatus())){
                                //todo 提现成功
                                showShortToast("提现成功");
                                addNetWork(MyApplication.getInstance().refreshUser(new IAsyncLoadListener<UserInfoDataEntity>() {
                                    @Override
                                    public void onSuccess(UserInfoDataEntity userInfoDataEntity) {

                                    }

                                    @Override
                                    public void onFailure(String msg) {
                                        showShortToast(msg);
                                    }
                                }));
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
    }

    private void showNextDialog(Context context,
                                final String name,
                                final String bankCard,
                                final String money,
                                final String bank,
                                final String bank_branch){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_tixian_paypass_dialog, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(context, R.style.custom_dialog_no_titlebar);
        dialog.setContentView(view);
        dialog.show();

        final MyPayPsdInputView payPsdInputView= view.findViewById(R.id.paypass_dialog_et_password);
        final TextView tipTv= view.findViewById(R.id.paypass_dialog_tv_tip);
        tipTv.setText(String.format(getString(R.string.hint_tixian_money), money));

        // 监听
        view.findViewById(R.id.paypass_dialog_btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.paypass_dialog_btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payPass= payPsdInputView.getPasswordString();
                if (payPass== null || payPass.length()!= 6){
                    showShortToast("请输入6位支付密码");
                    return;
                }
                doTiXian(name, bankCard, money, payPass, bank, bank_branch);
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
