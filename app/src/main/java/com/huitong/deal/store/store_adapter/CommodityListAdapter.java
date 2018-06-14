package com.huitong.deal.store.store_adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.beans_store.HomePageCommodityEntity;
import com.huitong.deal.store.store_activities.StoreDetailActivity;

import java.util.List;

/**
 * Created by Zheng on 2018/4/26.
 */

public class CommodityListAdapter extends BaseQuickAdapter<HomePageCommodityEntity, CommodityListAdapter.CommodityListHolder> {

    public CommodityListAdapter(int layoutResId) {
        super(layoutResId);
    }

    public CommodityListAdapter(int layoutResId, @Nullable List<HomePageCommodityEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(CommodityListHolder helper, final HomePageCommodityEntity item) {
        helper.mPanelRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent= new Intent(mContext, StoreDetailActivity.class);
                mIntent.putExtra(StoreDetailActivity.GOOD_ID, String.valueOf(item.getId()));
                mContext.startActivity(mIntent);
            }
        });
        Glide.with(mContext)
                .load(item.getImgurl())
                .into(helper.mCommodityImage);
        helper.mCommodityName.setText(item.getGoods_name());
        helper.mGouWuQuanPrice.setText(String.valueOf(item.getStore_price()));
        helper.mTiHuoQuanPrice.setText(String.valueOf(item.getGoods_integral()));
    }

    class CommodityListHolder extends BaseViewHolder{

        private RelativeLayout mPanelRly;
        private ImageView mCommodityImage;
        private TextView mCommodityName;
        private TextView mGouWuQuanPrice;
        private TextView mTiHuoQuanPrice;

        public CommodityListHolder(View view) {
            super(view);
            mPanelRly= view.findViewById(R.id.commodity_panel);
            mCommodityImage= view.findViewById(R.id.commodity_image);
            mCommodityName= view.findViewById(R.id.commodity_name);
            mGouWuQuanPrice= view.findViewById(R.id.commodity_price_left);
            mTiHuoQuanPrice= view.findViewById(R.id.commodity_price_right);
        }

    }

}
