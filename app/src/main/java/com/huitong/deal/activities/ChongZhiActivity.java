package com.huitong.deal.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.ChongZhiEntity;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.PayEntity;
import com.huitong.deal.beans.PayTypeEntity;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.MyPayPsdInputView;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import java.util.ArrayList;

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
 * Created by Zheng on 2018/4/16.
 */

public class ChongZhiActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFunctionTv;

    private EditText mMoneyEt;
    private RelativeLayout mKuaiJieRly;
    private TextView mKuaiJieTv;
    private RelativeLayout mAlipayRly;
    private TextView mAlipayTv;
    private RelativeLayout mWeXinRly;
    private TextView mWeiXinTv;

    private String appToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        appToken=  MyApplication.getInstance().getToken();

        initUI();
    }

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在提交充值申请...");
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
        mTitleTv.setText("充值");
        mFunctionTv = (TextView) findViewById(R.id.toolbar_right_text);
        mFunctionTv.setVisibility(View.VISIBLE);
        mFunctionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getRealContext(), ChongZhiHistoryActivity.class));
            }
        });

        mMoneyEt = (EditText) findViewById(R.id.chongzhi_money);

        mKuaiJieRly = (RelativeLayout) findViewById(R.id.chongzhi_kuaijie);
        mKuaiJieRly.setOnClickListener(this);
        mKuaiJieRly.setClickable(false);
        mKuaiJieTv= (TextView) findViewById(R.id.chongzhi_textview_kuaijie);
        mKuaiJieTv.setTextColor(Color.GRAY);

        mAlipayRly = (RelativeLayout) findViewById(R.id.chongzhi_alipay);
        mAlipayRly.setOnClickListener(this);
        mAlipayRly.setClickable(false);
        mAlipayTv= (TextView) findViewById(R.id.chongzhi_textview_alipay);
        mAlipayTv.setTextColor(Color.GRAY);

        mWeXinRly = (RelativeLayout) findViewById(R.id.chongzhi_weixin);
        mWeXinRly.setOnClickListener(this);
        mWeXinRly.setClickable(false);
        mWeiXinTv= (TextView) findViewById(R.id.chongzhi_textview_weixin);
        mWeiXinTv.setTextColor(Color.GRAY);

        loadPayType();

    }

    private void loadPayType(){
        if (appToken!= null && appToken.length()> 0){
            addNetWork(Network.getInstance().getPayList(appToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<HttpResult<ArrayList<PayTypeEntity>>>() {
                        @Override
                        public void accept(HttpResult<ArrayList<PayTypeEntity>> arrayListHttpResult) throws Exception {
                            if ("error".equals(arrayListHttpResult.getStatus())){
                                showShortToast(arrayListHttpResult.getDescription());
                            }else if ("success".equals(arrayListHttpResult.getStatus())){
                                if (arrayListHttpResult.getData().size()> 0){
                                    for (PayTypeEntity entity : arrayListHttpResult.getData()){
                                        if ("unitscan".equals(entity.getPaytype()) && entity.getInstall()== 1){
                                            mKuaiJieRly.setClickable(true);
                                            mKuaiJieTv.setTextColor(Color.WHITE);
                                        }else if ("alipay".equals(entity.getPaytype()) && entity.getInstall()== 1){
                                            mAlipayRly.setClickable(true);
                                            mAlipayTv.setTextColor(Color.WHITE);
                                        }else if ("wxpay".equals(entity.getPaytype()) && entity.getInstall()== 1){
                                            mWeXinRly.setClickable(true);
                                            mWeiXinTv.setTextColor(Color.WHITE);
                                        }
                                    }
                                }
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
    }

    private void doChongZhi(String amount , final String payType){
        if (appToken!= null && appToken.length()> 0){
            addNetWork(Network.getInstance().chongZhi(appToken, amount)//调用充值接口
                    .subscribeOn(Schedulers.io())//在io线程进行网络请求
                    .observeOn(Schedulers.io())//在io线程进行网络请求转换
                    .flatMap(new Function<HttpResult<ChongZhiEntity>, ObservableSource<HttpResult<PayEntity>>>() {//根据充值接口返回的数据
                        @Override
                        public ObservableSource<HttpResult<PayEntity>> apply(HttpResult<ChongZhiEntity> chongZhiEntityHttpResult) throws Exception {
                            final HttpResult<PayEntity> result= new HttpResult<>();
                            if ("error".equals(chongZhiEntityHttpResult.getStatus())){
                                result.setDescription(chongZhiEntityHttpResult.getDescription());
                            }else if ("success".equals(chongZhiEntityHttpResult.getStatus())){//如果充值接口成功，调用支付接口
                                return Network.getInstance().requestPay(appToken, chongZhiEntityHttpResult.getData().getPc_no(), payType);
                            }
                            return new Observable<HttpResult<PayEntity>>() {
                                @Override
                                protected void subscribeActual(Observer<? super HttpResult<PayEntity>> observer) {
                                    result.setStatus("error");
                                    observer.onNext(result);
                                }
                            };
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())//在主线程处理充值接口数据
                    .subscribe(new Consumer<HttpResult<PayEntity>>() {
                        @Override
                        public void accept(HttpResult<PayEntity> payEntityHttpResult) throws Exception {
                            dismissDialog();
                            if ("error".equals(payEntityHttpResult.getStatus())) {
                                showShortToast(payEntityHttpResult.getDescription());
                            } else if ("success".equals(payEntityHttpResult.getStatus())) {
                                Intent intent = new Intent(getRealContext(), PayActivity.class);
                                intent.putExtra("pay_entity", payEntityHttpResult.getData());
                                getRealContext().startActivity(intent);
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


    @Override
    public void onClick(View v) {
        String amount= mMoneyEt.getText().toString().trim();
        if (amount== null || amount.length()< 1){
            showShortToast("请输入充值金额");
            return;
        }

        int vId= v.getId();
        switch (vId){
            case R.id.chongzhi_kuaijie:
                doChongZhi(amount, "unitscan");
                break;
            case R.id.chongzhi_alipay:
                doChongZhi(amount, "alipay");
                break;
            case R.id.chongzhi_weixin:
                doChongZhi(amount, "wxpay");
                break;
            default:
                break;
        }
    }
}
