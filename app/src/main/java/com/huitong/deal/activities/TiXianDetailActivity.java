package com.huitong.deal.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.beans.BillEntity;
import com.huitong.deal.beans.TiXianHistoryEntity;
import com.zheng.zchlibrary.apps.BaseActivity;

/**
 * Created by Zheng on 2018/5/13.
 */

public class TiXianDetailActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mIconTv;

    private TextView mStatusTv;
    private TextView mMoneyTv;
    private TextView mTypeTv;
    private TextView mTimeTv;
    private TextView mNumberTv;
    private TextView mBalanceTv;

    private TiXianHistoryEntity entity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tixian_detail);

        entity= (TiXianHistoryEntity) getIntent().getSerializableExtra("tixian_entity");
        if (entity== null){
            showShortToast("获取提现详情失败");
            finish();
            return;
        }

        mBackIv = (ImageView) findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv = (TextView) findViewById(R.id.toolbar_title);
        mTitleTv.setText("提现详情");
        mIconTv = (TextView) findViewById(R.id.toolbar_right_text);
        mIconTv.setVisibility(View.GONE);

        mStatusTv=  (TextView) findViewById(R.id.tixian_detail_status);
        mStatusTv.setText(entity.getStatusname());
//        if (entity.getStatus()== 0){
//            mStatusTv.setText("未支付");
//        }else if (entity.getStatus()== 1){
//            mStatusTv.setText("已支付");
//        }else if (entity.getStatus()== 2){
//            mStatusTv.setText("已拒绝");
//        }

        mMoneyTv = (TextView) findViewById(R.id.tixian_detail_money);
        mMoneyTv.setText("-"+entity.getCach_amount());

        mTypeTv = (TextView) findViewById(R.id.tixian_detail_type);
        if (entity.getStatus()== 0){
            mTypeTv.setText("未支付");
        }else if (entity.getStatus()== 1){
            mTypeTv.setText("已支付");
        }else if (entity.getStatus()== 2){
            mTypeTv.setText("已拒绝");
        }
//        mTypeTv.setText("未知");

        mTimeTv = (TextView) findViewById(R.id.tixian_detail_time);
        mTimeTv.setText(entity.getCach_time());

        mNumberTv = (TextView) findViewById(R.id.tixian_detail_number);
        mNumberTv.setText(entity.getCach_no());

        mBalanceTv = (TextView) findViewById(R.id.tixian_detail_balance);
        mBalanceTv.setText(String.valueOf(entity.getAfter_balance()));

    }

    @Override
    public void initProgressDialog() {

    }
}
