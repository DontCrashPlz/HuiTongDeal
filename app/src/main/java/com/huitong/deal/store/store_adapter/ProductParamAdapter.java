package com.huitong.deal.store.store_adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.beans_store.ProductParamEntity;

import java.util.List;

/**
 * Created by Zheng on 2018/6/7.
 */

public class ProductParamAdapter extends BaseQuickAdapter<ProductParamEntity, ProductParamAdapter.ProductParamListHolder> {


    public ProductParamAdapter(@LayoutRes int layoutResId, @Nullable List<ProductParamEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ProductParamAdapter.ProductParamListHolder helper, ProductParamEntity item) {
        helper.mParamKey.setText(item.getKey());
        helper.mParamValue.setText(item.getValue());
    }

    class ProductParamListHolder extends BaseViewHolder {

        private TextView mParamKey;
        private TextView mParamValue;

        public ProductParamListHolder(View view) {
            super(view);
            mParamKey= view.findViewById(R.id.param_key);
            mParamValue= view.findViewById(R.id.param_value);
        }

    }

}
