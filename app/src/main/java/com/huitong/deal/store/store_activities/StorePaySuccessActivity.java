package com.huitong.deal.store.store_activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huitong.deal.R;
import com.zheng.zchlibrary.apps.BaseActivity;

/**
 * Created by Zheng on 2018/6/13.
 */

public class StorePaySuccessActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFuncTv;

    private Button mDetailBtn;
    private Button mHomePageBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_pay_successd);

        mBackIv= findViewById(R.id.toolbar_back);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv= findViewById(R.id.toolbar_title);
        mTitleTv.setText("支付完成");
        mFuncTv= findViewById(R.id.toolbar_function);
        mFuncTv.setVisibility(View.INVISIBLE);

        mDetailBtn= findViewById(R.id.store_pay_successd_detail);
        mHomePageBtn= findViewById(R.id.store_pay_successd_homepage);

    }

    @Override
    public void initProgressDialog() {

    }
}
