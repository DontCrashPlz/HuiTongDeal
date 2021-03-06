package com.huitong.deal.store.store_activities;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.PayEntity;
import com.huitong.deal.beans.PayStatusEntity;
import com.huitong.deal.beans.UserInfoDataEntity;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zheng.zchlibrary.utils.DownloadImageManager;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/5/14.
 */

public class StorePayActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mIconTv;

    private ImageView mQrcodeIv;
    private TextView mOrderNoTv;
    private TextView mStatusTv;
    private Button mButton1;
    private Button mButton2;

    private PayEntity entity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_pay_detail);

        entity= (PayEntity) getIntent().getSerializableExtra("pay_entity");
        if (entity== null){
            showShortToast("订单无效");
            finish();
            return;
        }

        mBackIv= findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv= findViewById(R.id.toolbar_title);
        mTitleTv.setText("支付");
        mIconTv= findViewById(R.id.toolbar_function);
        mIconTv.setVisibility(View.GONE);

        mQrcodeIv= findViewById(R.id.pay_detail_image);
        Glide.with(this)
                .load(entity.getPayqrcode())
                .asBitmap()
                .into(mQrcodeIv);

        mOrderNoTv= findViewById(R.id.pay_detail_orderno);
        mOrderNoTv.setText("订单编号： " + entity.getOrderno());

        mStatusTv= findViewById(R.id.pay_detail_status);
        mStatusTv.setText("待支付");

        mButton1= findViewById(R.id.pay_detail_btn1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 保存二维码
                addNetWork(Observable.create(new ObservableOnSubscribe<File>() {
                    @Override
                    public void subscribe(ObservableEmitter<File> e) throws Exception {
                        e.onNext(DownloadImageManager.getFileFromServer(getRealContext(), entity.getPayqrcode(), entity.getOrderno() + ".png"));
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(File file) throws Exception {
                                showLongToast("保存路径：" + file.getPath());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                showShortToast("保存失败");
                                LogUtil.e("保存二维码", throwable.toString());
                            }
                        }));
            }
        });
        mButton2= findViewById(R.id.pay_detail_btn2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryPayStatus();
            }
        });
    }

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在获取订单...");
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                clearNetWork();
            }
        });
    }

    private void queryPayStatus(){
        addNetWork(Network.getInstance().queryPay(MyApplication.appToken, entity.getOrderno())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HttpResult<PayStatusEntity>>() {
                    @Override
                    public void accept(HttpResult<PayStatusEntity> payStatusEntityHttpResult) throws Exception {
                        dismissDialog();
                        if ("error".equals(payStatusEntityHttpResult.getStatus())){
                            showShortToast(payStatusEntityHttpResult.getDescription());
                        }else if ("success".equals(payStatusEntityHttpResult.getStatus())){
                            if (payStatusEntityHttpResult.getData().getPaystatus()== 1){
                                mStatusTv.setText("已支付");
                                mStatusTv.setTextColor(Color.GREEN);
                                mButton2.setClickable(false);
                                mButton2.setBackgroundColor(Color.GRAY);
                                MyApplication.getInstance().refreshUser(null);
                            }else {
                                showShortToast("订单尚未支付");
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
    }

}
