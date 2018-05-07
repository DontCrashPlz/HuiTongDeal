package com.huitong.deal.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huitong.deal.R;
import com.zheng.zchlibrary.apps.BaseFragment;

/**
 * Created by Zheng on 2018/4/13.
 */

public class DealFragment extends BaseFragment {

    public static DealFragment newInstance(int tag){
        DealFragment instance = new DealFragment();
        Bundle args = new Bundle();
        args.putInt("tag", tag);
        instance.setArguments(args);
        return instance;
    }

    private RecyclerView mRecycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_deal, container, false);

        mRecycler= mView.findViewById(R.id.deal_recycler);

        int mTag= getArguments().getInt("tag");
        if (mTag== 0){
            mRecycler.setBackgroundColor(Color.RED);
        }else {
            mRecycler.setBackgroundColor(Color.BLUE);
        }

        return mView;
    }
}
