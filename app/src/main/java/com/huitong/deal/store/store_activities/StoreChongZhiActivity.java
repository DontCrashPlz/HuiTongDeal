package com.huitong.deal.store.store_activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.ChongZhiEntity;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.PayEntity;
import com.huitong.deal.beans.PayTypeEntity;
import com.huitong.deal.https.Network;
import com.huitong.deal.store.store_adapter.StoreChongZhiStyleAdapter;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.LogUtil;
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

public class StoreChongZhiActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFunctionTv;

    private EditText mMoneyEt;
    private EditText mRemarkEt;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_chongzhi);

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
        mBackIv = findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv = findViewById(R.id.toolbar_title);
        mTitleTv.setText("买购物券");
        mFunctionTv = findViewById(R.id.toolbar_function);
        mFunctionTv.setVisibility(View.INVISIBLE);

        mMoneyEt = findViewById(R.id.chongzhi_money);
        mRemarkEt= findViewById(R.id.chongzhi_remark);
        mRecyclerView= findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getRealContext()));

        loadPayType();

    }

    private void loadPayType(){
        addNetWork(Network.getInstance().getPayList(MyApplication.appToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HttpResult<ArrayList<PayTypeEntity>>>() {
                    @Override
                    public void accept(HttpResult<ArrayList<PayTypeEntity>> arrayListHttpResult) throws Exception {
                        if ("error".equals(arrayListHttpResult.getStatus())){
                            showShortToast(arrayListHttpResult.getDescription());
                        }else if ("success".equals(arrayListHttpResult.getStatus())){
                            if (arrayListHttpResult.getData().size()> 0){
                                mRecyclerView.setAdapter(new StoreChongZhiStyleAdapter(StoreChongZhiActivity.this, arrayListHttpResult.getData()));
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

    public void doChongZhi(final String payType){
        String amount= mMoneyEt.getText().toString().trim();
        if (amount== null || amount.length()< 1){
            showShortToast("请输入充值金额");
            return;
        }
        addNetWork(Network.getInstance().chongZhi(MyApplication.appToken, amount, mRemarkEt.getText().toString().trim())//调用充值接口
                .subscribeOn(Schedulers.io())//在io线程进行网络请求
                .observeOn(Schedulers.io())//在io线程进行网络请求转换
                .flatMap(new Function<HttpResult<ChongZhiEntity>, ObservableSource<HttpResult<PayEntity>>>() {//根据充值接口返回的数据
                    @Override
                    public ObservableSource<HttpResult<PayEntity>> apply(HttpResult<ChongZhiEntity> chongZhiEntityHttpResult) throws Exception {
                        final HttpResult<PayEntity> result= new HttpResult<>();
                        if ("error".equals(chongZhiEntityHttpResult.getStatus())){
                            result.setDescription(chongZhiEntityHttpResult.getDescription());
                        }else if ("success".equals(chongZhiEntityHttpResult.getStatus())){//如果充值接口成功，调用支付接口
                            return Network.getInstance().requestPay(MyApplication.appToken, chongZhiEntityHttpResult.getData().getPc_no(), payType);
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
                            Intent intent = new Intent(getRealContext(), StorePayActivity.class);
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
