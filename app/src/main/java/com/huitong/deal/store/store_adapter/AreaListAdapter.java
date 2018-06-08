package com.huitong.deal.store.store_adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.beans_store.AreaEntity;

import java.util.List;

/**
 * Created by Zheng on 2018/6/7.
 */

public class AreaListAdapter extends BaseQuickAdapter<AreaEntity, BaseViewHolder> {

    public AreaListAdapter(@LayoutRes int layoutResId, @Nullable List<AreaEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AreaEntity item) {
        helper.setText(R.id.area_selector_item_text, item.getText());
    }
}
