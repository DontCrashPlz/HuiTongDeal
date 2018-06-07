package com.huitong.deal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.activities.LoginActivity;
import com.huitong.deal.store.store_activities.StoreHomeActivity;
import com.zheng.zchlibrary.apps.BaseFragment;

/**
 * Created by Zheng on 2018/4/13.
 */

public class GuidePageFragment extends BaseFragment {

    public static GuidePageFragment newInstance(int tag){
        GuidePageFragment instance = new GuidePageFragment();
        Bundle args = new Bundle();
        args.putInt("tag", tag);
        instance.setArguments(args);
        return instance;
    }

    private int index;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        index= getArguments().getInt("tag");

        View v= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_guideview, container,false);
        ImageView mRl= v.findViewById(R.id.rl_guidepage_back);
        TextView mBtn= v.findViewById(R.id.btn_guidepage_start);
        if(index==1){
            mRl.setImageResource(R.mipmap.guide_01);
        }else if(index==2){
            mRl.setImageResource(R.mipmap.guide_02);
        }else{
            mRl.setImageResource(R.mipmap.guide_03);
            mBtn.setVisibility(View.VISIBLE);
            mBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
//                    startActivity(getActivity().getIntent().setClass(getActivity(), LoginActivity.class));
                    startActivity(getActivity().getIntent().setClass(getActivity(), StoreHomeActivity.class));
                    getActivity().finish();
                }
            });
        }
        return v;
    }

    @Override
    public void initProgressDialog() {

    }
}
