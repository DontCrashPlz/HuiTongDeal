package com.huitong.deal.store.store_activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.BillEntity;
import com.huitong.deal.beans_store.StoreBillEntity;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.Tools;
import com.zheng.zchlibrary.widgets.CustomTabLayout.Tool;

/**
 * Created by Zheng on 2018/5/13.
 */

public class StoreBillDetailActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mIconTv;

    private TextView mMoneyTv;
    private TextView mTypeTv;
    private TextView mTimeTv;
    private TextView mNumberTv;
    private TextView mBalanceTv;

    private StoreBillEntity entity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_bill_detail);

        entity= (StoreBillEntity) getIntent().getSerializableExtra("bill_entity");
        if (entity== null){
            showShortToast("获取账单详情失败");
            finish();
            return;
        }

        mBackIv = findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv = findViewById(R.id.toolbar_title);
        mTitleTv.setText("账单详情");
        mIconTv = findViewById(R.id.toolbar_function);
        mIconTv.setVisibility(View.GONE);

        mMoneyTv = findViewById(R.id.bill_detail_money);

        mTypeTv = findViewById(R.id.bill_detail_type);
        mTimeTv = findViewById(R.id.bill_detail_time);
        mTimeTv.setText(entity.getAddtime());
        mNumberTv = findViewById(R.id.bill_detail_number);
        mNumberTv.setText(entity.getOrderno());
        mBalanceTv = findViewById(R.id.bill_detail_balance);

        if (entity.isType()){
            mMoneyTv.setText("-" + Tools.formatFloat(entity.getMoney()));
            mMoneyTv.setTextColor(MyApplication.colorGreen);
            Drawable drawableRight= ContextCompat.getDrawable(getRealContext(),R.mipmap.voucher_06);
            mMoneyTv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableRight, null);
        }else {
            mMoneyTv.setText("+" + Tools.formatFloat(entity.getMoney()));
            mMoneyTv.setTextColor(MyApplication.colorOrange);
            Drawable drawableRight= ContextCompat.getDrawable(getRealContext(),R.mipmap.voucher_07);
            mMoneyTv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableRight, null);
        }
        mTypeTv.setText(entity.getAccount_type());
        mBalanceTv.setText(Tools.formatFloat(entity.getAfter_money()));
    }

    @Override
    public void initProgressDialog() {

    }
}
