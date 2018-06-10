package com.huitong.deal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.adapters.ChiCangListAdapter;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.ChiCangEntity;
import com.huitong.deal.beans.ChiCangEntity2;
import com.huitong.deal.beans.HttpResult;
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

    private RecyclerView mRecycler;
    private ChiCangListAdapter mAdapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_deal, container, false);

        mRecycler= mView.findViewById(R.id.deal_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mAdapter= new ChiCangListAdapter(R.layout.item_deal_recycler);
        mRecycler.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(mRecycler);

        progressBar= mView.findViewById(R.id.progressBar);

        final String token= MyApplication.getInstance().getToken();
        if (token!= null && token.length()> 0){
            addNetWork(
                    Observable.interval(3, TimeUnit.SECONDS)
                            .flatMap(new Function<Long, ObservableSource<HttpResult<ArrayList<ChiCangEntity2>>>>() {
                                @Override
                                public ObservableSource<HttpResult<ArrayList<ChiCangEntity2>>> apply(Long aLong) throws Exception {
                                    return Network.getInstance().getChiCangList(token);
                                }
                            })
                            .filter(new Predicate<HttpResult<ArrayList<ChiCangEntity2>>>() {
                                @Override
                                public boolean test(HttpResult<ArrayList<ChiCangEntity2>> arrayListHttpResult) throws Exception {
                                    if ("success".equals(arrayListHttpResult.getStatus())){
                                        return true;
                                    }
                                    return false;
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<HttpResult<ArrayList<ChiCangEntity2>>>() {
                                @Override
                                public void accept(HttpResult<ArrayList<ChiCangEntity2>> arrayListHttpResult) throws Exception {
                                    dismissProgressBar();
                                    if (arrayListHttpResult.getData().size()> 0){
                                        mAdapter.setNewData(arrayListHttpResult.getData());
                                        mAdapter.notifyDataSetChanged();
                                    }else {
                                        mAdapter.setNewData(new ArrayList<ChiCangEntity2>());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyApplication.mChiCangLastPriceMap.clear();
    }

}
