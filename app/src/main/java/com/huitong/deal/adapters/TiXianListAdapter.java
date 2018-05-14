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
import com.huitong.deal.activities.TiXianDetailActivity;
import com.huitong.deal.beans.BillEntity;
import com.huitong.deal.beans.TiXianHistoryEntity;

/**
 * Created by Zheng on 2018/5/13.
 */

public class TiXianListAdapter extends BaseQuickAdapter<TiXianHistoryEntity, TiXianListAdapter.BillHolder> {

    public TiXianListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BillHolder helper, final TiXianHistoryEntity item) {
        helper.mPanelRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, TiXianDetailActivity.class);
                intent.putExtra("tixian_entity", item);
                mContext.startActivity(intent);
            }
        });
        if (item.getStatus()== 0){
            helper.mStatusTv.setText("未支付");
            helper.mStatusTv.setTextColor(Color.WHITE);
        }else if (item.getStatus()== 1){
            helper.mStatusTv.setText("已支付");
            helper.mStatusTv.setTextColor(Color.rgb(4, 188, 4));
        }else if (item.getStatus()== 2){
            helper.mStatusTv.setText("已拒绝");
            helper.mStatusTv.setTextColor(Color.WHITE);
        }
        helper.mTimeTv.setText(item.getCach_time());
        helper.mMoneyTv.setText("-" + item.getCach_amount());
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
