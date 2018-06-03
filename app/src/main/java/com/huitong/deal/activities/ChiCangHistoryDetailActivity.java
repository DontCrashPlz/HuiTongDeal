package com.huitong.deal.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.ChiCangEntity;
import com.huitong.deal.beans.ChiCangHistoryEntity;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.Tools;
import com.zheng.zchlibrary.widgets.CustomTabLayout.Tool;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/5/12.
 */

public class ChiCangHistoryDetailActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;

    private TextView mNameTv;
    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mTextView4;
    private TextView mTextView5;
    private TextView mTextView6;
    private TextView mDanJiaTv;
    private TextView mTextView7;
    private TextView mTextView8;
    private TextView mTextView9;
    private TextView mTextView10;
    private TextView mTextView11;
    private TextView mTextView12;

    private ChiCangHistoryEntity mEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicang_history_detail);

        mEntity= (ChiCangHistoryEntity) getIntent().getSerializableExtra("chicang_detail_entity");
        if (mEntity== null){
            showShortToast("获取详情失败");
            finish();
        }

        mBackIv= (ImageView) findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleTv= (TextView) findViewById(R.id.toolbar_title);
        //mTitleTv.setText(mEntity.getStock_name()+"详情");
        mTitleTv.setText("历史订单详情");

        mNameTv= findViewById(R.id.chicang_detail_name);
        mNameTv.setText(mEntity.getStock_name());

        mTextView1= (TextView) findViewById(R.id.chicang_detail_text1);
        mTextView1.setText(mEntity.getPosition_no());

        mTextView2= (TextView) findViewById(R.id.chicang_detail_text2);
        if (mEntity.getBuy_type()== 1){
            mTextView2.setText("回购");
            mTextView2.setTextColor(MyApplication.colorGreen);
        }else if (mEntity.getBuy_type()== 2){
            mTextView2.setText("认购");
            mTextView2.setTextColor(MyApplication.colorOrange);
        }else {
            mTextView2.setText("未知");
        }

        mTextView3= (TextView) findViewById(R.id.chicang_detail_text3);
        mTextView3.setText(Tools.formatFloat(mEntity.getNow_price()));

        mTextView4= (TextView) findViewById(R.id.chicang_detail_text4);
        mTextView4.setText(Tools.formatFloat(mEntity.getEnd_price()));

        mTextView5= (TextView) findViewById(R.id.chicang_detail_text5);
        mTextView5.setText(String.valueOf(mEntity.getBuy_count()));

        mTextView6= (TextView) findViewById(R.id.chicang_detail_text6);
        mTextView6.setText(String.valueOf(mEntity.getLeverage()));

        mDanJiaTv= findViewById(R.id.chicang_detail_text_danjia);
        mDanJiaTv.setText(String.valueOf(mEntity.getBuy_pirce()));

        mTextView7= (TextView) findViewById(R.id.chicang_detail_text7);
        mTextView7.setText(Tools.formatFloat(mEntity.getOrder_money()));

        mTextView8= (TextView) findViewById(R.id.chicang_detail_text8);
        mTextView8.setText(Tools.formatFloat(mEntity.getService_fee()));

        mTextView9= (TextView) findViewById(R.id.chicang_detail_text9);
        mTextView9.setText(Tools.formatFloat(mEntity.getGain()));

        mTextView10= (TextView) findViewById(R.id.chicang_detail_text10);
        mTextView10.setText(mEntity.getBuy_time());

        mTextView11= (TextView) findViewById(R.id.chicang_detail_text11);
        mTextView11.setText(mEntity.getClose_type_name() + "平仓");

        mTextView12= (TextView) findViewById(R.id.chicang_detail_text12);
        mTextView12.setText(mEntity.getEnd_time());

        if (mEntity.getGain()< 0){
            //mTextView4.setTextColor(MyApplication.colorGreen);
            mTextView9.setTextColor(MyApplication.colorGreen);
        }else {
            //mTextView4.setTextColor(MyApplication.colorOrange);
            mTextView9.setTextColor(MyApplication.colorOrange);
        }
    }

    @Override
    public void initProgressDialog() {

    }

}
