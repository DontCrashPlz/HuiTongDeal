package com.huitong.deal.store.store_adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.activities.BillDetailActivity;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.BillEntity;
import com.huitong.deal.beans_store.StoreBillEntity;
import com.huitong.deal.store.store_activities.StoreBillDetailActivity;
import com.zheng.zchlibrary.utils.Tools;

/**
 * Created by Zheng on 2018/5/13.
 */

public class StoreBillListAdapter extends BaseQuickAdapter<StoreBillEntity, StoreBillListAdapter.BillHolder> {

    public StoreBillListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BillHolder helper, final StoreBillEntity item) {
        helper.mPanelRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, StoreBillDetailActivity.class);
                intent.putExtra("bill_entity", item);
                mContext.startActivity(intent);
            }
        });
        helper.mStatusTv.setText(item.getFrom_type_name());

        if (item.isType()){//支出
            helper.mMoneyTv.setText("-" + Tools.formatFloat(item.getMoney()));
            helper.mMoneyTv.setTextColor(MyApplication.colorGreen);
            Drawable drawableRight= ContextCompat.getDrawable(mContext,R.mipmap.voucher_06);
            helper.mMoneyTv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableRight, null);
        }else {//收入
            helper.mMoneyTv.setText("+" + Tools.formatFloat(item.getMoney()));
            helper.mMoneyTv.setTextColor(MyApplication.colorOrange);
            Drawable drawableRight= ContextCompat.getDrawable(mContext,R.mipmap.voucher_07);
            helper.mMoneyTv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableRight, null);
        }
        helper.mTimeTv.setText(item.getAddtime());
    }

    class BillHolder extends BaseViewHolder{

        private RelativeLayout mPanelRly;
        private TextView mStatusTv;
        private TextView mTimeTv;
        private TextView mMoneyTv;

        public BillHolder(View view) {
            super(view);
            mPanelRly= view.findViewById(R.id.item_pay_panel);
            mStatusTv= view.findViewById(R.id.item_pay_status);
            mTimeTv= view.findViewById(R.id.item_pay_time);
            mMoneyTv= view.findViewById(R.id.item_pay_money);
        }
    }

}
