package com.huitong.deal.store.store_adapter;

import android.app.Activity;
import android.app.Fragment;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.beans_store.ShopCartItemEntity;
import com.huitong.deal.store.store_fragments.StoreHomeShoppingFragment;

/**
 * Created by Zheng on 2018/6/7.
 */

public class ShopCartShowAdapter extends BaseQuickAdapter<ShopCartItemEntity, BaseViewHolder> {

    public ShopCartShowAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopCartItemEntity item) {
        Glide.with(mContext)
                .load(item.getImgurl())
                .into((ImageView) (helper.getView(R.id.commodity_shopping_image)));
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
        if (StoreHomeShoppingFragment.mSelectedList.contains(item)){
            ((CheckBox)(helper.getView(R.id.commodity_shopping_checkbox))).setChecked(true);
        }else {
            ((CheckBox)(helper.getView(R.id.commodity_shopping_checkbox))).setChecked(false);
        }
    }
}
