package com.huitong.deal.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huitong.deal.R;
import com.zheng.zchlibrary.apps.BaseActivity;

/**
 * Created by Zheng on 2018/4/16.
 */

public class TiXianHistoryActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFunctionTv;

    private EditText mNameEt;
    private EditText mBankCardEt;
    private EditText mMoneyEt;
    private Button mCommitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_deposit);

        initUI();
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

            }
        });

        mNameEt = (EditText) findViewById(R.id.tixian_et_name);
        mBankCardEt = (EditText) findViewById(R.id.tixian_et_bankcard);
        mMoneyEt = (EditText) findViewById(R.id.tixian_et_money);
        mCommitBtn = (Button) findViewById(R.id.tixian_btn_commit);
    }
}
