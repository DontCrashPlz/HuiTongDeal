package com.huitong.deal.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.beans.BillEntity;
import com.zheng.zchlibrary.apps.BaseActivity;

/**
 * Created by Zheng on 2018/5/13.
 */

public class BillDetailActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mIconTv;

    private TextView mMoneyTv;
    private TextView mTypeTv;
    private TextView mTimeTv;
    private TextView mNumberTv;
    private TextView mBalanceTv;

    private BillEntity entity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        entity= (BillEntity) getIntent().getSerializableExtra("bill_entity");
        if (entity== null){
            showShortToast("获取账单详情失败");
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
        mTitleTv.setText("账单详情");
        mIconTv = (TextView) findViewById(R.id.toolbar_right_text);
        mIconTv.setVisibility(View.GONE);

        mMoneyTv = (TextView) findViewById(R.id.bill_detail_money);

        mTypeTv = (TextView) findViewById(R.id.bill_detail_type);
        mTimeTv = (TextView) findViewById(R.id.bill_detail_time);
        mTimeTv.setText(entity.getAddtime());
        mNumberTv = (TextView) findViewById(R.id.bill_detail_number);
        mNumberTv.setText(entity.getOrderno());
        mBalanceTv = (TextView) findViewById(R.id.bill_detail_balance);
        if (entity.isType()){
            mMoneyTv.setText("-" + String.valueOf(entity.getMoney()));
            mTypeTv.setText("支出");
            mBalanceTv.setText(String.valueOf(entity.getBefore_money() - entity.getMoney()));
        }else {
            mMoneyTv.setText("+" + String.valueOf(entity.getMoney()));
            mTypeTv.setText("收入");
            mBalanceTv.setText(String.valueOf(entity.getBefore_money() + entity.getMoney()));
        }

//        if (entity.getFromtype()== 1){
//            mTypeTv.setText("充值");
//        }else if (entity.getFromtype()== 2){
//            mTypeTv.setText("提现");
//        }else if (entity.getFromtype()== 3){
//            mTypeTv.setText("转账");
//        }else if (entity.getFromtype()== 4){
//            mTypeTv.setText("消费");
//        }else if (entity.getFromtype()== 5){
//            mTypeTv.setText("分佣");
//        }else if (entity.getFromtype()== 6){
//            mTypeTv.setText("持仓");
//        }else if (entity.getFromtype()== 7){
//            mTypeTv.setText("持仓手续费");
//        }else if (entity.getFromtype()== 8){
//            mTypeTv.setText("平仓返本");
//        }else if (entity.getFromtype()== 9){
//            mTypeTv.setText("平仓核算");
//        }

    }
}
