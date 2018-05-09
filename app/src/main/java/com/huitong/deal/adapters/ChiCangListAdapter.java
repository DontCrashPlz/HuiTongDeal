package com.huitong.deal.adapters;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.beans.ChiCangEntity;

/**
 * Created by Zheng on 2018/4/26.
 */

public class ChiCangListAdapter extends BaseQuickAdapter<ChiCangEntity, ChiCangListAdapter.ChiCangListHolder> {

    private int colorGreen= Color.rgb(0, 246, 1);
    private int colorOrange= Color.rgb(255, 63, 0);

    public ChiCangListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(ChiCangListHolder helper, ChiCangEntity item) {
        helper.mNameTv.setText(item.getStockname());
        helper.mText2.setText(String.valueOf(item.getNow_price()));
        helper.mText3.setText(String.valueOf(item.getCurpoint()));
        helper.mText4.setText(String.valueOf(item.getGain()));
        float currentGain= item.getGain();
        if (currentGain> 0F){
            helper.mIconTv.setTextColor(colorGreen);
            helper.mText3.setTextColor(colorOrange);
            helper.mText4.setTextColor(colorOrange);
        }else {
            helper.mIconTv.setTextColor(colorOrange);
            helper.mText3.setTextColor(colorGreen);
            helper.mText4.setTextColor(colorGreen);
        }
    }

    class ChiCangListHolder extends BaseViewHolder{

        private TextView mNameTv;
        private TextView mIconTv;
        private TextView mIconRemarkTv;

        private TextView mText2;
        private TextView mText3;
        private TextView mText4;

        public ChiCangListHolder(View view) {
            super(view);
            mNameTv= view.findViewById(R.id.item_deal_name);
            mIconTv= view.findViewById(R.id.item_deal_icon);
            mIconRemarkTv= view.findViewById(R.id.item_deal_icon_remark);

            mText2= view.findViewById(R.id.item_deal_text2);
            mText3= view.findViewById(R.id.item_deal_text3);
            mText4= view.findViewById(R.id.item_deal_text4);
        }

    }

}
