package com.huitong.deal.store.store_activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.huitong.deal.R;
import com.huitong.deal.beans_store.ProductDetailEntity;
import com.huitong.deal.https.HttpUtils;
import com.huitong.deal.https.Network;
import com.huitong.deal.https.ResponseTransformer;
import com.huitong.deal.widgets.GlideImageLoader;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.LogUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/6/6.
 */

public class StoreDetailActivity extends BaseActivity {

    public static final String GOOD_ID= "good_id";

    private Banner mBanner;
    private ImageView mBackIv;
    private TextView mNameTv;
    private TextView mRemarkTv;
    private TextView mGouWuQuanTv;
    private TextView mTiHuoQuanTv;
    private TextView mUnitTv;
    private TextView mSelledTv;
    private CommonTabLayout mTabLayout;
    private WebView mWebView;
    private ListView mListView;
    private Button mShoppingBtn;
    private Button mBuyNowBtn;

    private String mGoodId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_commodity_detail);

        mGoodId= getIntent().getStringExtra(GOOD_ID);
        if (mGoodId== null || mGoodId.trim().length()< 1){
            showShortToast("未获取产品id");
            finish();
            return;
        }

        initUI();

        addNetWork(Network.getInstance().getProductDetail(mGoodId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<ProductDetailEntity>handleResult())
                .subscribe(new Consumer<ProductDetailEntity>() {
                    @Override
                    public void accept(ProductDetailEntity productDetailEntity) throws Exception {
                        dismissDialog();
                        mBanner.setImageLoader(new GlideImageLoader());
                        mBanner.setImages(productDetailEntity.getImgurllist());
                        //设置banner样式
                        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                        //设置指示器位置（当banner模式中有指示器时）
                        mBanner.setIndicatorGravity(BannerConfig.LEFT);
                        mBanner.setDelayTime(2000);
                        mBanner.start();

                        mNameTv.setText(productDetailEntity.getGoods_name());
                        mRemarkTv.setText(productDetailEntity.getGoods_name_english());
                        mGouWuQuanTv.setText(String.valueOf(productDetailEntity.getStore_price()));
                        mTiHuoQuanTv.setText(String.valueOf(productDetailEntity.getGoods_integral()));
                        mUnitTv.setText(productDetailEntity.getSale_unit());
                        mSelledTv.setText(String.valueOf(productDetailEntity.getGoods_salenum()));

                        mWebView.loadDataWithBaseURL("http://47.92.94.101/", productDetailEntity.getGoods_details(), "text/html", "utf-8", null);
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

    private void initUI() {
        mBanner= findViewById(R.id.commodity_detail_banner);
        mBackIv= findViewById(R.id.commodity_detail_back);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mNameTv= findViewById(R.id.commodity_detail_name);
        mRemarkTv= findViewById(R.id.commodity_detail_remark);
        mGouWuQuanTv= findViewById(R.id.commodity_detail_gouwuquan);
        mTiHuoQuanTv= findViewById(R.id.commodity_detail_tihuoquan);
        mUnitTv= findViewById(R.id.commodity_detail_unit);
        mSelledTv= findViewById(R.id.commodity_detail_selled);
//        mTabLayout= findViewById(R.id.commodity_detail_tab);
        mWebView= findViewById(R.id.commodity_detail_webview);
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        // 设置文本编码
        webSetting.setDefaultTextEncodingName("UTF-8");
		/*
		 * LayoutAlgorithm是一个枚举用来控制页面的布局，有三个类型：
		 * 1.NARROW_COLUMNS：可能的话使所有列的宽度不超过屏幕宽度
		 * 2.NORMAL：正常显示不做任何渲染
		 * 3.SINGLE_COLUMN：把所有内容放大webview等宽的一列中
		 * 用SINGLE_COLUMN类型可以设置页面居中显示，
		 * 页面可以放大缩小，但这种方法不怎么好，
		 * 有时候会让你的页面布局走样而且我测了一下，只能显示中间那一块，超出屏幕的部分都不能显示。
		 */
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(false);// 用于设置webview放大
        webSetting.setBuiltInZoomControls(false);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i== 100){
                    LogUtil.d("webview渲染完成： ", "onProgressChanged");
                    dismissDialog();
                }
            }
        });
        mListView= findViewById(R.id.commodity_detail_listview);
        mShoppingBtn= findViewById(R.id.commodity_detail_shopping);
        mBuyNowBtn= findViewById(R.id.commodity_detail_buynow);
    }

    @Override
    public void initProgressDialog() {

    }
}
