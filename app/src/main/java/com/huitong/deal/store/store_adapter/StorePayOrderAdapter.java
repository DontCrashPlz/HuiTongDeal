package com.huitong.deal.store.store_adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.beans.PayTypeEntity;

import java.util.List;

/**
 * Created by Zheng on 2018/6/7.
 */

public class StorePayOrderAdapter extends BaseQuickAdapter<PayTypeEntity, BaseViewHolder> {


    public StorePayOrderAdapter(int layoutResId, @Nullable List<PayTypeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayTypeEntity item) {
        helper.setText(R.id.store_pay_type_text, item.getName());
        if (item.getInstall()== 1){
            helper.setImageResource(R.id.store_pay_type_image, R.mipmap.order_shopping_voucher);
        }else if (item.getInstall()== 2){
            helper.setImageResource(R.id.store_pay_type_image, R.mipmap.order_bill_lading);
        }else if (item.getInstall()== 3){
            helper.setImageResource(R.id.store_pay_type_image, R.mipmap.recharge_unionpay);
        }
    }
}
