package com.huitong.deal.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.ChiCangEntity;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/5/12.
 */

public class ChiCangDetailActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mTextView4;
    private TextView mTextView5;
    private TextView mTextView6;
    private TextView mTextView7;
    private TextView mTextView8;
    private TextView mTextView9;
    private TextView mTextView10;
    private Button mPingCangBtn;

    private ChiCangEntity mEntity;
    private String appToken;
    private String postionNo;
    private String closePrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicang_detail);

        mEntity= (ChiCangEntity) getIntent().getSerializableExtra("chicang_detail_entity");
        if (mEntity== null){
            showShortToast("获取详情失败");
            finish();
        }

        appToken= MyApplication.getInstance().getToken();

        mBackIv= (ImageView) findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleTv= (TextView) findViewById(R.id.toolbar_title);
        mTitleTv.setText(mEntity.getStockname()+"详情");

        mTextView1= (TextView) findViewById(R.id.chicang_detail_text1);
        mTextView1.setText(mEntity.getPosition_no());
        postionNo= mEntity.getPosition_no();

        mTextView2= (TextView) findViewById(R.id.chicang_detail_text2);
        if (mEntity.getBuy_type()== 0){
            mTextView2.setText("回购");
        }else {
            mTextView2.setText("认购");
        }

        mTextView3= (TextView) findViewById(R.id.chicang_detail_text3);
        mTextView3.setText(String.valueOf(mEntity.getNow_price()));

        mTextView4= (TextView) findViewById(R.id.chicang_detail_text4);
        mTextView4.setText(String.valueOf(mEntity.getCurpoint()));
        closePrice= String.valueOf(mEntity.getCurpoint());

        mTextView5= (TextView) findViewById(R.id.chicang_detail_text5);
        mTextView5.setText(String.valueOf(mEntity.getBuy_count()));

        mTextView6= (TextView) findViewById(R.id.chicang_detail_text6);
        mTextView6.setText(String.valueOf(mEntity.getLeverage()));

        mTextView7= (TextView) findViewById(R.id.chicang_detail_text7);
        mTextView7.setText(String.valueOf(mEntity.getOrder_money()));

        mTextView8= (TextView) findViewById(R.id.chicang_detail_text8);
        mTextView8.setText(String.valueOf(mEntity.getService_fee()));

        mTextView9= (TextView) findViewById(R.id.chicang_detail_text9);
        mTextView9.setText(String.valueOf(mEntity.getGain()));

        mTextView10= (TextView) findViewById(R.id.chicang_detail_text10);
        mTextView10.setText(mEntity.getBuy_time());

        mPingCangBtn= (Button) findViewById(R.id.chicang_detail_btn_pingcang);
        mPingCangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appToken!= null && appToken.length()> 0){
                    addNetWork(Network.getInstance().pingCang(appToken, postionNo, closePrice)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<HttpResult<Boolean>>() {
                                @Override
                                public void accept(HttpResult<Boolean> booleanHttpResult) throws Exception {
                                    dismissDialog();
                                    if ("error".equals(booleanHttpResult.getStatus())){
                                        showShortToast(booleanHttpResult.getDescription());
                                    }else if ("success".equals(booleanHttpResult.getStatus())){
                                        showShortToast("平仓成功");
                                        MyApplication.getInstance().refreshUser();
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
        });

        if (appToken!= null && appToken.length()> 0){
            addNetWork(
                    Observable.interval(1, TimeUnit.SECONDS)
                            .flatMap(new Function<Long, ObservableSource<HttpResult<ChiCangEntity>>>() {
                                @Override
                                public ObservableSource<HttpResult<ChiCangEntity>> apply(Long aLong) throws Exception {
                                    return Network.getInstance().getChiCangDetail(appToken, postionNo);
                                }
                            })
                            .filter(new Predicate<HttpResult<ChiCangEntity>>() {
                                @Override
                                public boolean test(HttpResult<ChiCangEntity> chiCangEntityHttpResult) throws Exception {
                                    if ("success".equals(chiCangEntityHttpResult.getStatus())){
                                        return true;
                                    }
                                    return false;
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<HttpResult<ChiCangEntity>>() {
                                @Override
                                public void accept(HttpResult<ChiCangEntity> chiCangEntityHttpResult) throws Exception {
                                    if (chiCangEntityHttpResult.getData()!= null){
                                        refreshUI(chiCangEntityHttpResult.getData());
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

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在提交平仓申请...");
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                clearNetWork();
            }
        });
    }

    private void refreshUI(ChiCangEntity entity){
        mTextView4.setText(String.valueOf(entity.getCurpoint()));
        closePrice= String.valueOf(entity.getCurpoint());

        mTextView9.setText(String.valueOf(entity.getGain()));
    }

}
