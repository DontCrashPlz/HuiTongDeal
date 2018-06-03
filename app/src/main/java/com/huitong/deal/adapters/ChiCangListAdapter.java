package com.huitong.deal.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.activities.ChiCangDetailActivity;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.ChiCangEntity;
import com.zheng.zchlibrary.utils.Tools;

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
    protected void convert(ChiCangListHolder helper, final ChiCangEntity item) {
        helper.mPanelLly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, ChiCangDetailActivity.class);
                intent.putExtra("chicang_detail_entity", item);
                mContext.startActivity(intent);
            }
        });

        float lastPrice= 0;
        float nowPrice= item.getCurpoint();
        if (MyApplication.mChiCangLastPriceMap.containsKey(item.getId())){
            lastPrice= MyApplication.mChiCangLastPriceMap.get(item.getId());
        }

        helper.mNameTv.setText(item.getStockname());
        if (item.getBuy_type()== 1){
            helper.mIconTv.setText("回购");
            helper.mIconTv.setTextColor(colorGreen);
            helper.mIconTv.setBackgroundResource(R.drawable.textview_background_deal_item_green);
        }else if (item.getBuy_type()== 2){
            helper.mIconTv.setText("认购");
            helper.mIconTv.setTextColor(colorOrange);
            helper.mIconTv.setBackgroundResource(R.drawable.textview_background_deal_item_orange);
        }else {
            helper.mIconTv.setText("未知");
        }
        helper.mIconRemarkTv.setText(item.getStock_no());
        helper.mText2.setText(Tools.formatFloat(item.getNow_price()));

        helper.mText3.setText(Tools.formatFloat(nowPrice));
        MyApplication.mChiCangLastPriceMap.put(item.getId(), nowPrice);
        float floatPrice= nowPrice - lastPrice;
        if (floatPrice< 0){
            helper.mText3.setTextColor(colorGreen);
        }else {
            helper.mText3.setTextColor(colorOrange);
        }

        helper.mText4.setText(Tools.formatFloat(item.getGain()));
        float currentGain= item.getGain();
        if (currentGain< 0F){
            //helper.mText3.setTextColor(colorGreen);
            helper.mText4.setTextColor(colorGreen);
        }else {
            //helper.mText3.setTextColor(colorOrange);
            helper.mText4.setTextColor(colorOrange);
        }
    }

    class ChiCangListHolder extends BaseViewHolder{

        private LinearLayout mPanelLly;
        private TextView mNameTv;
        private TextView mIconTv;
        private TextView mIconRemarkTv;

        private TextView mText2;
        private TextView mText3;
        private TextView mText4;

        public ChiCangListHolder(View view) {
            super(view);
            mPanelLly= view.findViewById(R.id.item_deal_panel);
            mNameTv= view.findViewById(R.id.item_deal_name);
            mIconTv= view.findViewById(R.id.item_deal_icon);
            mIconRemarkTv= view.findViewById(R.id.item_deal_icon_remark);

            mText2= view.findViewById(R.id.item_deal_text2);
            mText3= view.findViewById(R.id.item_deal_text3);
            mText4= view.findViewById(R.id.item_deal_text4);
        }

    }

}
