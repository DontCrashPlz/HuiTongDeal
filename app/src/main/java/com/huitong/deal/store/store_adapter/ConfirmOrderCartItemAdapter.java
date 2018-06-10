package com.huitong.deal.store.store_adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.beans.PayTypeEntity;
import com.huitong.deal.beans_store.ShopCartItemEntity;

import java.util.List;

/**
 * Created by Zheng on 2018/6/7.
 */

public class ConfirmOrderCartItemAdapter extends BaseQuickAdapter<ShopCartItemEntity, BaseViewHolder> {


    public ConfirmOrderCartItemAdapter(int layoutResId, @Nullable List<ShopCartItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopCartItemEntity item) {
        Glide.with(mContext).load(item.getImgurl()).into((ImageView) helper.getView(R.id.commodity_shopping_image));
        helper.setText(R.id.commodity_shopping_name, item.getGoods_name())
                .setText(R.id.commodity_shopping_remark, "")
                .setText(R.id.commodity_shopping_price_red, String.valueOf(item.getPrice()))
                .setText(R.id.commodity_shopping_red_unit,
                        String.format(mContext.getString(R.string.store_buying_unit),
                                item.getSale_unit()))
                .setText(R.id.commodity_shopping_price_yellow, String.valueOf(item.getIntegral()))
                .setText(R.id.commodity_shopping_yellow_unit,
                        String.format(mContext.getString(R.string.store_buying_unit),
                                item.getSale_unit()))
                .setText(R.id.commodity_shopping_num, "x" + item.getCount());
    }
}
