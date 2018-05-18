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
import com.huitong.deal.activities.ChongZhiDetailActivity;
import com.huitong.deal.beans.BillEntity;
import com.huitong.deal.beans.ChongZhiHistoryEntity;

/**
 * Created by Zheng on 2018/5/13.
 */

public class ChongZhiListAdapter extends BaseQuickAdapter<ChongZhiHistoryEntity, ChongZhiListAdapter.ChongZhiHolder> {

    public ChongZhiListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(ChongZhiHolder helper, final ChongZhiHistoryEntity item) {
        helper.mPanelRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, ChongZhiDetailActivity.class);
                intent.putExtra("chongzhi_entity", item);
                mContext.startActivity(intent);
            }
        });

        helper.mTimeTv.setText(item.getAddtime());
        helper.mMoneyTv.setText("+" + String.valueOf(item.getAmount()));
    }

    class ChongZhiHolder extends BaseViewHolder{

        private RelativeLayout mPanelRly;
        private TextView mStatusTv;
        private TextView mTimeTv;
        private TextView mMoneyTv;

        public ChongZhiHolder(View view) {
            super(view);
            mPanelRly= view.findViewById(R.id.item_pay_panel);
            mStatusTv= view.findViewById(R.id.item_pay_status);
            mTimeTv= view.findViewById(R.id.item_pay_time);
            mMoneyTv= view.findViewById(R.id.item_pay_money);
        }
    }

}
