package com.huitong.deal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.huitong.deal.R;
import com.huitong.deal.activities.LoginActivity;
import com.zheng.zchlibrary.apps.BaseFragment;

/**
 * Created by Zheng on 2018/4/13.
 */

public class KLineChartFragment extends BaseFragment {

    public static final int KLINE_TAG_5_MINUTE= 1;
    public static final int KLINE_TAG_30_MINUTE= 2;
    public static final int KLINE_TAG_60_MINUTE= 3;
    public static final int KLINE_TAG_1_DAY= 4;

    public static KLineChartFragment newInstance(int tag){
        KLineChartFragment instance = new KLineChartFragment();
        Bundle args = new Bundle();
        args.putInt("tag", tag);
        instance.setArguments(args);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_chart, container, false);

        return mView;
    }
}
