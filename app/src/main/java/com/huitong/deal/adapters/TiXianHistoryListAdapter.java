package com.huitong.deal.adapters;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.beans.TiXianHistoryEntity;

/**
 * Created by Zheng on 2018/5/13.
 */

public class TiXianHistoryListAdapter extends BaseQuickAdapter<TiXianHistoryEntity, TiXianHistoryListAdapter.TiXianHolder> {

    public TiXianHistoryListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(TiXianHolder helper, TiXianHistoryEntity item) {
        helper.mPanelRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        helper.mStatusTv.setText();
    }

    class TiXianHolder extends BaseViewHolder{

        private RelativeLayout mPanelRly;
        private TextView mStatusTv;
        private TextView mTimeTv;
        private TextView mMoneyTv;

        public TiXianHolder(View view) {
            super(view);
            mPanelRly= view.findViewById(R.id.item_pay_panel);
            mStatusTv= view.findViewById(R.id.item_pay_status);
            mTimeTv= view.findViewById(R.id.item_pay_time);
            mMoneyTv= view.findViewById(R.id.item_pay_money);
        }
    }

}
