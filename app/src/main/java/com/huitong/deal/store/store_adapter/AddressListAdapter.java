package com.huitong.deal.store.store_adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.beans_store.AddressEntity;
import com.huitong.deal.beans_store.ProductParamEntity;
import com.huitong.deal.store.store_activities.StoreMineAddressActivity;

import java.util.List;

/**
 * Created by Zheng on 2018/6/7.
 */

public class AddressListAdapter extends BaseQuickAdapter<AddressEntity, AddressListAdapter.AddressListHolder> {

    public AddressListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(AddressListAdapter.AddressListHolder helper, AddressEntity item) {
        helper.mAreaTv.setText(item.getArea_name());
        helper.mYouBianTv.setText(item.getZip());
        helper.mAddressTv.setText(item.getAddress());
        helper.mNameTv.setText(item.getRecvname());
        helper.mMobileTv.setText(item.getMobile());
        if (item.isIsdefault()){
            helper.mDefaultCb.setCompoundDrawablesWithIntrinsicBounds(
                    mContext.getResources().getDrawable(R.mipmap.address_select),
                    null,
                    null,
                    null);
            helper.mDefaultCb.setTextColor(Color.rgb(51,51,51));
            helper.mModifyTv.setTextColor(Color.rgb(51,51,51));
            helper.mDeleteTv.setTextColor(Color.rgb(51,51,51));
        }else {
            helper.mDefaultCb.setCompoundDrawablesWithIntrinsicBounds(
                    mContext.getResources().getDrawable(R.mipmap.address_check),
                    null,
                    null,
                    null);
            helper.mDefaultCb.setTextColor(Color.rgb(153,153,153));
            helper.mModifyTv.setTextColor(Color.rgb(153,153,153));
            helper.mDeleteTv.setTextColor(Color.rgb(153,153,153));
        }
        helper.addOnClickListener(R.id.address_default)
                .addOnClickListener(R.id.address_modify)
                .addOnClickListener(R.id.address_delete);
    }

    class AddressListHolder extends BaseViewHolder {

        private CardView mPanelCv;
        private TextView mAreaTv;
        private TextView mYouBianTv;
        private TextView mAddressTv;
        private TextView mNameTv;
        private TextView mMobileTv;

        private TextView mDefaultCb;
        private TextView mModifyTv;
        private TextView mDeleteTv;

        public AddressListHolder(View view) {
            super(view);
            mPanelCv= view.findViewById(R.id.address_panel);
            mAreaTv= view.findViewById(R.id.address_area);
            mYouBianTv= view.findViewById(R.id.address_youbian);
            mAddressTv= view.findViewById(R.id.address_detail);
            mNameTv= view.findViewById(R.id.address_name);
            mMobileTv= view.findViewById(R.id.address_mobile);

            mDefaultCb= view.findViewById(R.id.address_default);
            mModifyTv= view.findViewById(R.id.address_modify);
            mDeleteTv= view.findViewById(R.id.address_delete);
        }

    }
}
