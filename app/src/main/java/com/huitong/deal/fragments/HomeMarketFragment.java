package com.huitong.deal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.huitong.deal.R;
import com.huitong.deal.activities.HomeActivity;
import com.huitong.deal.activities.LoginActivity;
import com.huitong.deal.adapters.MarketListAdapter;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.CommodityDetailEntity;
import com.huitong.deal.beans.CommodityListEntity;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.LoginEntity;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseFragment;
import com.zheng.zchlibrary.utils.LogUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/13.
 */

public class HomeMarketFragment extends BaseFragment {

    public static HomeMarketFragment newInstance(String content){
        HomeMarketFragment instance = new HomeMarketFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        instance.setArguments(args);
        return instance;
    }

    private RecyclerView mRecycler;
    private MarketListAdapter mAdapter;

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_home_market, container, false);

        mRecycler= mView.findViewById(R.id.home_market_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mAdapter= new MarketListAdapter(R.layout.item_market_recycler);
        mRecycler.setAdapter(mAdapter);

        progressBar= mView.findViewById(R.id.progressBar);

        final String token= MyApplication.getInstance().getToken();
        if (token!= null && token.length()> 0){
            addNetWork(
                    Observable.interval(1, TimeUnit.SECONDS)
                            .flatMap(new Function<Long, ObservableSource<HttpResult<ArrayList<CommodityDetailEntity>>>>() {
                                @Override
                                public ObservableSource<HttpResult<ArrayList<CommodityDetailEntity>>> apply(Long aLong) throws Exception {
                                    return Network.getInstance().getCommodityList(token);
                                }
                            })
                            .filter(new Predicate<HttpResult<ArrayList<CommodityDetailEntity>>>() {
                                @Override
                                public boolean test(HttpResult<ArrayList<CommodityDetailEntity>> arrayListHttpResult) throws Exception {
                                    if ("success".equals(arrayListHttpResult.getStatus())){
                                        return true;
                                    }
                                    return false;
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<HttpResult<ArrayList<CommodityDetailEntity>>>() {
                                @Override
                                public void accept(HttpResult<ArrayList<CommodityDetailEntity>> arrayListHttpResult) throws Exception {
                                    dismissProgressBar();
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
                                    dismissProgressBar();
                                    LogUtil.d("throwable", throwable.toString());
                                    showShortToast("网络请求失败");
                                }
                            }, new Action() {
                                @Override
                                public void run() throws Exception {
                                    dismissProgressBar();
                                }
                            }, new Consumer<Disposable>() {
                                @Override
                                public void accept(Disposable disposable) throws Exception {
                                    showProgressBar();
                                }
                            }));
        }

        return mView;
    }

    private void showProgressBar(){
        if (progressBar!= null && !progressBar.isShown()){
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void dismissProgressBar(){
        if (progressBar!= null && progressBar.isShown()){
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void initProgressDialog() {

    }
}
