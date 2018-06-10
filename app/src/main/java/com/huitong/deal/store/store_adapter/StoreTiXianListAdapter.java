package com.huitong.deal.store.store_adapter;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.beans.TiXianHistoryEntity;
import com.huitong.deal.store.store_activities.StoreTiXianDetailActivity;

/**
 * Created by Zheng on 2018/5/13.
 */

public class StoreTiXianListAdapter extends BaseQuickAdapter<TiXianHistoryEntity, StoreTiXianListAdapter.BillHolder> {

    public StoreTiXianListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BillHolder helper, final TiXianHistoryEntity item) {
        helper.mPanelRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, StoreTiXianDetailActivity.class);
                intent.putExtra("tixian_entity", item);
                mContext.startActivity(intent);
            }
        });
        helper.mStatusTv.setText(item.getStatusname());
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
