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
import com.huitong.deal.beans.ChiCangEntity2;
import com.zheng.zchlibrary.utils.Tools;
import com.zheng.zchlibrary.widgets.CustomTabLayout.Tool;

/**
 * Created by Zheng on 2018/4/26.
 */

public class ChiCangListAdapter extends BaseQuickAdapter<ChiCangEntity2, ChiCangListAdapter.ChiCangListHolder> {

    private int colorGreen= Color.rgb(0, 246, 1);
    private int colorOrange= Color.rgb(255, 63, 0);

    public ChiCangListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(ChiCangListHolder helper, final ChiCangEntity2 item) {
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

        helper.mText1.setText(item.getStockname());
        if (item.getBuy_type()== 1){
            helper.mText2.setText("回购");
            helper.mText2.setTextColor(colorGreen);
            helper.mText2.setBackgroundResource(R.drawable.textview_background_deal_item_green);
            helper.mText8.setTextColor(colorGreen);
            helper.mText9.setTextColor(colorGreen);
            helper.mText10.setTextColor(colorOrange);
            helper.mText11.setTextColor(colorOrange);
        }else if (item.getBuy_type()== 2){
            helper.mText2.setText("认购");
            helper.mText2.setTextColor(colorOrange);
            helper.mText2.setBackgroundResource(R.drawable.textview_background_deal_item_orange);
            helper.mText8.setTextColor(colorOrange);
            helper.mText9.setTextColor(colorOrange);
            helper.mText10.setTextColor(colorGreen);
            helper.mText11.setTextColor(colorGreen);
        }else {
            helper.mText2.setText("未知");
        }
        helper.mText3.setText(item.getPosition_no());

        helper.mText5.setText(Tools.formatFloat(item.getNow_price()));
        helper.mText6.setText("最新");
        helper.mText7.setText(Tools.formatFloat(item.getCurpoint()));
        MyApplication.mChiCangLastPriceMap.put(item.getId(), nowPrice);
        float floatPrice= nowPrice - lastPrice;
        if (floatPrice< 0){
            helper.mText7.setTextColor(colorGreen);
        }else {
            helper.mText7.setTextColor(colorOrange);
        }

        helper.mText9.setText(Tools.formatFloat(item.getGain_price()));
        helper.mText11.setText(Tools.formatFloat(item.getLose_price()));
        helper.mText13.setText(Tools.formatFloat(item.getOrder_money()));
        helper.mText14.setText("预期收益");
        helper.mText15.setText(Tools.formatFloat(item.getGain()));
        if (item.getGain()< 0){
            helper.mText15.setTextColor(colorGreen);
        }else {
            helper.mText15.setTextColor(colorOrange);
        }

        helper.mText16.setText(item.getBuy_time());

    }

    class ChiCangListHolder extends BaseViewHolder{

        private LinearLayout mPanelLly;
        private TextView mText1;
        private TextView mText2;
        private TextView mText3;
        private TextView mText4;
        private TextView mText5;
        private TextView mText6;
        private TextView mText7;
        private TextView mText8;
        private TextView mText9;
        private TextView mText10;
        private TextView mText11;
        private TextView mText12;
        private TextView mText13;
        private TextView mText14;
        private TextView mText15;
        private TextView mText16;

        public ChiCangListHolder(View view) {
            super(view);
            mPanelLly= view.findViewById(R.id.item_deal_panel);

            mText1= view.findViewById(R.id.item_deal_text1);
            mText2= view.findViewById(R.id.item_deal_text2);
            mText3= view.findViewById(R.id.item_deal_text3);
            mText4= view.findViewById(R.id.item_deal_text4);
            mText5= view.findViewById(R.id.item_deal_text5);
            mText6= view.findViewById(R.id.item_deal_text6);
            mText7= view.findViewById(R.id.item_deal_text7);
            mText8= view.findViewById(R.id.item_deal_text8);
            mText9= view.findViewById(R.id.item_deal_text9);
            mText10= view.findViewById(R.id.item_deal_text10);
            mText11= view.findViewById(R.id.item_deal_text11);
            mText12= view.findViewById(R.id.item_deal_text12);
            mText13= view.findViewById(R.id.item_deal_text13);
            mText14= view.findViewById(R.id.item_deal_text14);
            mText15= view.findViewById(R.id.item_deal_text15);
            mText16= view.findViewById(R.id.item_deal_text16);
        }

    }

}
