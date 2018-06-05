package com.huitong.deal.store.store_adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.beans.CommodityDetailEntity;

/**
 * Created by Zheng on 2018/4/26.
 */

public class CommodityListAdapter extends BaseQuickAdapter<String, CommodityListAdapter.CommodityListHolder> {

    public CommodityListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(CommodityListHolder helper, String item) {
        Glide.with(mContext).load("http://47.92.94.101/upload/temp/690ca792ce164dbfac39c227f73a4475.png").into(helper.mCommodityImage);
    }

    class CommodityListHolder extends BaseViewHolder{

        private ImageView mCommodityImage;

        public CommodityListHolder(View view) {
            super(view);
            mCommodityImage= view.findViewById(R.id.commodity_image);
        }

    }

}
