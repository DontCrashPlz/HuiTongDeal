package com.huitong.deal.store.store_activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.huitong.deal.R;
import com.huitong.deal.store.store_adapter.CommodityListAdapter;
import com.zheng.zchlibrary.apps.BaseActivity;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/6/5.
 */

public class StoreTestActivity extends BaseActivity {

    private RecyclerView mRecycler;
    private CommodityListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_test);

        mRecycler= findViewById(R.id.recyclerview);
        mRecycler.setLayoutManager(new GridLayoutManager(getRealContext(), 2));
        mAdapter= new CommodityListAdapter(R.layout.store_layout_commodity_show);
        mRecycler.setAdapter(mAdapter);

        ArrayList<String> dataList= new ArrayList<>();
        for (int i= 0; i< 10; i++){
            dataList.add("test");
        }
        mAdapter.addData(dataList);

    }

    @Override
    public void initProgressDialog() {

    }
}
