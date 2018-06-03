package com.huitong.deal.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.activities.MarketDetailActivity;
import com.huitong.deal.activities.MarketDetailActivity2;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.CommodityDetailEntity;
import com.huitong.deal.beans.CommodityListEntity;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.Tools;

/**
 * Created by Zheng on 2018/4/26.
 */

public class MarketListAdapter extends BaseQuickAdapter<CommodityDetailEntity, MarketListAdapter.MarketListHolder> {

    public MarketListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(MarketListHolder helper, final CommodityDetailEntity item) {
        helper.mPanelCly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, MarketDetailActivity2.class);
                intent.putExtra("id", String.valueOf(item.getId()));
                intent.putExtra("stock_name", item.getStock_name());
                intent.putExtra("stock_code", item.getStock_code());
                mContext.startActivity(intent);
            }
        });

        float lastPrice= 0;
        float nowPrice= item.getNow_price();
        if (MyApplication.mLastPriceMap.containsKey(item.getId())){
            lastPrice= MyApplication.mLastPriceMap.get(item.getId());
        }

        helper.mNameTv.setText(item.getStock_name());
        helper.mPriceTv.setText(Tools.formatFloat(nowPrice));
        MyApplication.mLastPriceMap.put(item.getId(), nowPrice);

        float floatPrice= nowPrice - lastPrice;

        if (floatPrice< 0){
            helper.mPriceTv.setTextColor(MyApplication.color_down_green);
        }else {
            helper.mPriceTv.setTextColor(MyApplication.color_up_red);
        }

        if (item.getStock_state()== 0 || item.getTrade_state()== 0){
            helper.mFloatTv.setBackgroundResource(R.drawable.market_float_background_gary);
            helper.mFloatTv.setText("休市中");
        }else {
            helper.mFloatTv.setText(Tools.formatFloat(item.getFloat_rate()) + "%");
            if (item.getFloat_rate()< 0){
                helper.mFloatTv.setBackgroundResource(R.drawable.market_float_background_green);
            }else {
                helper.mFloatTv.setBackgroundResource(R.drawable.market_float_background_orange);
            }
        }

    }

    class MarketListHolder extends BaseViewHolder{

        private LinearLayout mPanelCly;
        private TextView mNameTv;
        private TextView mPriceTv;
        private TextView mFloatTv;

        public MarketListHolder(View view) {
            super(view);
            mPanelCly= view.findViewById(R.id.market_list_panel);
            mNameTv= view.findViewById(R.id.market_list_name);
            mPriceTv= view.findViewById(R.id.market_list_price);
            mFloatTv= view.findViewById(R.id.market_list_float);
        }

    }

}
