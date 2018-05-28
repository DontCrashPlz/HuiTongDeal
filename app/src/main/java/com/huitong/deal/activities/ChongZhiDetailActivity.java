package com.huitong.deal.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.beans.ChongZhiHistoryEntity;
import com.huitong.deal.beans.TiXianHistoryEntity;
import com.zheng.zchlibrary.apps.BaseActivity;

/**
 * Created by Zheng on 2018/5/13.
 */

public class ChongZhiDetailActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mIconTv;

    private TextView mStatusTv;
    private TextView mMoneyTv;
    private TextView mTypeTv;
    private TextView mTimeTv;
    private TextView mNumberTv;
    private TextView mBalanceTv;

    private ChongZhiHistoryEntity entity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chongzhi_detail);

        entity= (ChongZhiHistoryEntity) getIntent().getSerializableExtra("chongzhi_entity");
        if (entity== null){
            showShortToast("获取充值详情失败");
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
        mTitleTv.setText("购买详情");
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
        mMoneyTv.setText("+"+entity.getAmount());

        mTypeTv = (TextView) findViewById(R.id.tixian_detail_type);
        if (entity.getPaytypename()!= null && entity.getPaytypename().length()> 0)
        mTypeTv.setText(entity.getPaytypename());

        mTimeTv = (TextView) findViewById(R.id.tixian_detail_time);
        mTimeTv.setText(entity.getAddtime());

        mNumberTv = (TextView) findViewById(R.id.tixian_detail_number);
        mNumberTv.setText(entity.getPc_no());

        mBalanceTv = (TextView) findViewById(R.id.tixian_detail_balance);
        mBalanceTv.setText(String.valueOf(entity.getAfter_balance()));

    }

    @Override
    public void initProgressDialog() {

    }
}
