package com.huitong.deal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.adapters.ChiCangListAdapter;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.ChiCangEntity;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseFragment;
import com.zheng.zchlibrary.utils.LogUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by Zheng on 2018/4/13.
 */

public class DealChiCangFragment extends BaseFragment {

    public static DealChiCangFragment newInstance(int tag){
        DealChiCangFragment instance = new DealChiCangFragment();
        Bundle args = new Bundle();
        args.putInt("tag", tag);
        instance.setArguments(args);
        return instance;
    }

    private TextView textView3;
    private TextView textView4;
    private RecyclerView mRecycler;
    private ChiCangListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_deal, container, false);

        textView3= mView.findViewById(R.id.deal_text3);
        textView4= mView.findViewById(R.id.deal_text4);
        textView3.setText("现价");
        textView4.setText("浮动");

        mRecycler= mView.findViewById(R.id.deal_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mAdapter= new ChiCangListAdapter(R.layout.item_deal_recycler);
        mRecycler.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(mRecycler);
        final String token= MyApplication.getInstance().getToken();
        if (token!= null && token.length()> 0){
            addNetWork(
                    Observable.interval(1, TimeUnit.SECONDS)
                            .flatMap(new Function<Long, ObservableSource<HttpResult<ArrayList<ChiCangEntity>>>>() {
                                @Override
                                public ObservableSource<HttpResult<ArrayList<ChiCangEntity>>> apply(Long aLong) throws Exception {
                                    return Network.getInstance().getChiCangList(token);
                                }
                            })
                            .filter(new Predicate<HttpResult<ArrayList<ChiCangEntity>>>() {
                                @Override
                                public boolean test(HttpResult<ArrayList<ChiCangEntity>> arrayListHttpResult) throws Exception {
                                    if ("success".equals(arrayListHttpResult.getStatus())){
                                        return true;
                                    }
                                    return false;
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<HttpResult<ArrayList<ChiCangEntity>>>() {
                                @Override
                                public void accept(HttpResult<ArrayList<ChiCangEntity>> arrayListHttpResult) throws Exception {
                                    if (arrayListHttpResult.getData().size()> 0){
                                        mAdapter.setNewData(arrayListHttpResult.getData());
                                        mAdapter.notifyDataSetChanged();
                                    }else {
                                        mAdapter.setEmptyView(R.layout.layout_recycler_empty);
                                        clearNetWork();
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    LogUtil.d("throwable", throwable.toString());
                                    showShortToast("网络请求失败");
                                }
                            }));
        }

        return mView;
    }
}
