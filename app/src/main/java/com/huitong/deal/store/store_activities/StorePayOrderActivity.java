package com.huitong.deal.store.store_activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.PayEntity;
import com.huitong.deal.beans.PayTypeEntity;
import com.huitong.deal.https.HttpUtils;
import com.huitong.deal.https.Network;
import com.huitong.deal.https.ResponseTransformer;
import com.huitong.deal.store.store_adapter.StorePayOrderAdapter;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.widgets.MyPayPsdInputView;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/6/10.
 */

public class StorePayOrderActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFuncTv;

    private TextView mGouWuQuanTv;
    private TextView mTiHuoQuanTv;

    private RecyclerView mRecycler;
    private StorePayOrderAdapter mAdapter;

    private int gouWuQuanMoney;
    private int tiHuoQuanMoney;
    private String orderNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_pay_order);

        gouWuQuanMoney= getIntent().getIntExtra("gouWuQuan_Money", 0);
        tiHuoQuanMoney= getIntent().getIntExtra("tiHuoQuan_Money", 0);
        orderNo= getIntent().getStringExtra("orderNo");

        mBackIv= findViewById(R.id.toolbar_back);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv= findViewById(R.id.toolbar_title);
        mTitleTv.setText("订单支付");
        mFuncTv= findViewById(R.id.toolbar_function);
        mFuncTv.setVisibility(View.INVISIBLE);

        mGouWuQuanTv= findViewById(R.id.pay_order_gouwuquan);
        mGouWuQuanTv.setText(String.valueOf(gouWuQuanMoney));
        mTiHuoQuanTv= findViewById(R.id.pay_order_tihuoquan);
        mTiHuoQuanTv.setText(String.valueOf(tiHuoQuanMoney));

        mRecycler= findViewById(R.id.pay_order_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));

        addNetWork(Network.getInstance().getStorePayList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<ArrayList<PayTypeEntity>>handleResult())
                .subscribe(new Consumer<ArrayList<PayTypeEntity>>() {
                    @Override
                    public void accept(final ArrayList<PayTypeEntity> payTypeEntities) throws Exception {
                        dismissDialog();
                        mAdapter= new StorePayOrderAdapter(R.layout.store_item_pay_order, payTypeEntities);
                        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                if ("balance".equals(payTypeEntities.get(position).getPaytype())){
                                    showPayPasswordDialog(0);
                                }else if ("integral".equals(payTypeEntities.get(position).getPaytype())){
                                    showPayPasswordDialog(1);
                                }else if ("unitscan".equals(payTypeEntities.get(position).getPaytype())){
                                    requestPayByUnit("unitscan");
                                }
                            }
                        });
                        mRecycler.setAdapter(mAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        showShortToast(HttpUtils.parseThrowableMsg(throwable));
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                }));
    }

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在请求网络..");
    }

    /**
     * 弹出支付密码框
     * @param payTypeTag 0表示购物券支付，1表示提货券支付
     */
    private void showPayPasswordDialog(final int payTypeTag){
        View view = LayoutInflater.from(getRealContext()).inflate(R.layout.layout_paypass_dialog2, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(getRealContext(), R.style.custom_dialog_no_titlebar);
        dialog.setContentView(view);
        dialog.show();

        TextView moneyTv= view.findViewById(R.id.paypass_dialog_tv_tip);//提现金额显示
        final MyPayPsdInputView payPsdInputView= view.findViewById(R.id.paypass_dialog_et_password);//密码输入框
        TextView forgetTv= view.findViewById(R.id.paypass_dialog_btn_forget);//忘记密码
        Button cancelBtn= view.findViewById(R.id.paypass_dialog_btn_cancel);//取消按钮
        final TextView confirmBtn= view.findViewById(R.id.paypass_dialog_btn_confirm);//确认按钮

        if (payTypeTag== 0){
            moneyTv.setText(String.valueOf(gouWuQuanMoney));
        }else if (payTypeTag== 1){
            moneyTv.setText(String.valueOf(tiHuoQuanMoney));
        }

        payPsdInputView.setOnPassInputCompleteListener(new MyPayPsdInputView.IPassInputCompleteListener() {
            @Override
            public void onComplete() {
                confirmBtn.setBackgroundResource(R.drawable.button_background_orange_corners_selector);
                confirmBtn.setClickable(true);
            }

            @Override
            public void onUnCompleted() {
                confirmBtn.setBackgroundResource(R.drawable.button_background_gary_corners);
                confirmBtn.setClickable(false);
            }
        });

        forgetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getRealContext(), StorePayPasswordActivity.class));
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payPass= payPsdInputView.getPasswordString();
                if (payPass== null || payPass.length()== 0) return;
                if (payTypeTag== 0){
                    requestPayOrder(payPass, payTypeTag, gouWuQuanMoney);
                }else if (payTypeTag== 1){
                    requestPayOrder(payPass, payTypeTag, tiHuoQuanMoney);
                }
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

    /**
     * 购物券和提货券支付
     * @param payPass
     * @param payType
     * @param price
     */
    private void requestPayOrder(String payPass, int payType, int price){
        addNetWork(Network.getInstance().payForOrder(MyApplication.appToken, orderNo, payPass, payType, price)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<String>handleResult())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        dismissDialog();
                        showShortToast("支付成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        showShortToast(HttpUtils.parseThrowableMsg(throwable));
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                }));
    }

    /**
     * 银联扫码支付
     * @param payType
     */
    private void requestPayByUnit(String payType){
        addNetWork(Network.getInstance().storeRequestPay(MyApplication.appToken, orderNo, payType)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<PayEntity>handleResult())
                .subscribe(new Consumer<PayEntity>() {
                    @Override
                    public void accept(PayEntity payEntity) throws Exception {
                        dismissDialog();
                        Intent intent = new Intent(getRealContext(), StorePayActivity.class);
                        intent.putExtra("pay_entity", payEntity);
                        startActivity(intent);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        showShortToast(HttpUtils.parseThrowableMsg(throwable));
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                }));
    }

}
