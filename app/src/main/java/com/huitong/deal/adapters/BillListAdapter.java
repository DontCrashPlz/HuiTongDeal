package com.huitong.deal.adapters;

import android.content.Intent;
import android.graphics.Color;
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
import com.huitong.deal.beans.BillEntity;
import com.huitong.deal.beans.TiXianHistoryEntity;

/**
 * Created by Zheng on 2018/5/13.
 */

public class BillListAdapter extends BaseQuickAdapter<BillEntity, BillListAdapter.BillHolder> {

    public BillListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BillHolder helper, final BillEntity item) {
        helper.mPanelRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, BillDetailActivity.class);
                intent.putExtra("bill_entity", item);
                mContext.startActivity(intent);
            }
        });
        if (item.getFromtype()== 1){
            helper.mStatusTv.setText("充值");
        }else if (item.getFromtype()== 2){
            helper.mStatusTv.setText("提现");
        }else if (item.getFromtype()== 3){
            helper.mStatusTv.setText("转账");
        }else if (item.getFromtype()== 4){
            helper.mStatusTv.setText("消费");
        }else if (item.getFromtype()== 5){
            helper.mStatusTv.setText("分佣");
        }else if (item.getFromtype()== 6){
            helper.mStatusTv.setText("持仓");
        }else if (item.getFromtype()== 7){
            helper.mStatusTv.setText("持仓手续费");
        }else if (item.getFromtype()== 8){
            helper.mStatusTv.setText("平仓返本");
        }else if (item.getFromtype()== 9){
            helper.mStatusTv.setText("平仓核算");
        }

        if (item.isType()){//支出
            helper.mMoneyTv.setText("-" + item.getMoney());
            helper.mMoneyTv.setTextColor(Color.WHITE);
            Drawable drawableRight= ContextCompat.getDrawable(mContext,R.mipmap.voucher_02);
            helper.mMoneyTv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableRight, null);
        }else {//收入
            helper.mMoneyTv.setText("+" + item.getMoney());
            helper.mMoneyTv.setTextColor(Color.rgb(4, 188, 4));
            Drawable drawableRight= ContextCompat.getDrawable(mContext,R.mipmap.voucher_06);
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
