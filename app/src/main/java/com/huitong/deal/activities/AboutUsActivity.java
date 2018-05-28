package com.huitong.deal.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huitong.deal.R;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.zheng.zchlibrary.apps.BaseActivity;

/**
 * Created by Zheng on 2018/5/23.
 */

public class AboutUsActivity extends BaseActivity {

    public static final String LAUNCH_TAG= "launch_tag";
    public static final String LAUNCH_TAG_HUITONGXIEYI= "huitong_xieyi";
    public static final String LAUNCH_TAG_ABOUTUS= "about_us";

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFunctionTv;

    private WebView mWebview;

    private String mLaunchTag;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        mBackIv = (ImageView) findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv = (TextView) findViewById(R.id.toolbar_title);
        mFunctionTv = (TextView) findViewById(R.id.toolbar_right_text);
        mFunctionTv.setVisibility(View.GONE);

        mWebview= (WebView) findViewById(R.id.about_us_webview);

        mLaunchTag= getIntent().getStringExtra(LAUNCH_TAG);
        if (LAUNCH_TAG_HUITONGXIEYI.equals(mLaunchTag)){
            mTitleTv.setText("尚品会承诺");
            url= "http://47.92.28.185/wap/helpcenter/userProtocol";
        }else if (LAUNCH_TAG_ABOUTUS.equals(mLaunchTag)){
            mTitleTv.setText("关于尚品会");
            url= "http://47.92.28.185/wap/helpcenter/aboutUs";
        }

        if (url== null || url.length()== 0){
            showShortToast("无效链接");
            finish();
        }

        WebSettings webSetting = mWebview.getSettings();
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
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setSupportZoom(false);// 用于设置webview放大
        webSetting.setBuiltInZoomControls(false);
        mWebview.loadUrl(url);
    }

    @Override
    public void initProgressDialog() {

    }
}
