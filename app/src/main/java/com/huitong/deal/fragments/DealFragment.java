package com.huitong.deal.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

//    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private RecyclerView mRecycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_deal, container, false);

//        textView2= mView.findViewById(R.id.deal_text2);
        textView3= mView.findViewById(R.id.deal_text3);
        textView4= mView.findViewById(R.id.deal_text4);

        mRecycler= mView.findViewById(R.id.deal_recycler);

        int mTag= getArguments().getInt("tag");
        if (mTag== 0){
//            textView2.setText("建仓价");
            textView3.setText("现价");
            textView4.setText("浮动");
            mRecycler.setBackgroundColor(Color.RED);
        }else {
//            textView2.setText("建仓价");
            textView3.setText("平仓价");
            textView4.setText("盈亏");
            mRecycler.setBackgroundColor(Color.BLUE);
        }

        return mView;
    }
}
