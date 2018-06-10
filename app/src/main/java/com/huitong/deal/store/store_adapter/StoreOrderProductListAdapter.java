package com.huitong.deal.store.store_adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.beans.PayTypeEntity;
import com.huitong.deal.beans_store.OrderProductEntity;

import java.util.List;

/**
 * Created by Zheng on 2018/6/7.
 */

public class StoreOrderProductListAdapter extends BaseQuickAdapter<OrderProductEntity, BaseViewHolder> {


    public StoreOrderProductListAdapter(int layoutResId, @Nullable List<OrderProductEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderProductEntity item) {
        Glide.with(mContext)
                .load(item.getImgurl())
                .into((ImageView) helper.getView(R.id.commodity_shopping_image));
        helper.setText(R.id.commodity_shopping_name, item.getGoods_name())
                .setText(R.id.commodity_shopping_remark, "")
                .setText(R.id.commodity_shopping_price_red, String.valueOf(item.getStore_price()))
                .setText(R.id.commodity_shopping_red_unit, String.format(mContext.getString(R.string.store_buying_unit), item.getSale_unit()))
                .setText(R.id.commodity_shopping_price_yellow, String.valueOf(item.getGoods_integral()))
                .setText(R.id.commodity_shopping_yellow_unit, String.format(mContext.getString(R.string.store_buying_unit), item.getSale_unit()))
                .setText(R.id.commodity_shopping_num, "x" + item.getCount());

    }
}
