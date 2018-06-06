package com.huitong.deal.store.store_adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.beans_store.HomePageFloorEntity;

/**
 * Created by Zheng on 2018/4/26.
 */

public class HomePageFloorAdapter extends BaseQuickAdapter<HomePageFloorEntity, HomePageFloorAdapter.FloorListHolder> {

    public HomePageFloorAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(FloorListHolder helper, HomePageFloorEntity item) {
        helper.mFloorName.setText(item.getFloorname());
        helper.mFloorRecycler.setLayoutManager(new GridLayoutManager(mContext, 2));
        helper.mFloorRecycler.setAdapter(new CommodityListAdapter(R.layout.store_layout_commodity_show, item.getGoodslist()));
    }

    class FloorListHolder extends BaseViewHolder{

        private TextView mFloorName;
        private RecyclerView mFloorRecycler;

        public FloorListHolder(View view) {
            super(view);
            mFloorName= view.findViewById(R.id.floor_name);
            mFloorRecycler= view.findViewById(R.id.floor_recyclerview);
        }

    }

}
