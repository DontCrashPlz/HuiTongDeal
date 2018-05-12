package com.huitong.deal.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huitong.deal.R;
import com.huitong.deal.adapters.ChiCangHistoryListAdapter;
import com.huitong.deal.adapters.ChiCangListAdapter;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.ChiCangEntity;
import com.huitong.deal.beans.ChiCangHistoryEntity;
import com.huitong.deal.beans.ChiCangHistoryQueryParam;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.ListDataEntity;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseFragment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/13.
 */

public class DealHistoryFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    public static DealHistoryFragment newInstance(int tag){
        DealHistoryFragment instance = new DealHistoryFragment();
        Bundle args = new Bundle();
        args.putInt("tag", tag);
        instance.setArguments(args);
        return instance;
    }

    private TextView textView3;
    private TextView textView4;
    private RecyclerView mRecycler;
    private ChiCangHistoryListAdapter mAdapter;

    private String appToken;
    private int currentPage= 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_deal, container, false);

        textView3= mView.findViewById(R.id.deal_text3);
        textView4= mView.findViewById(R.id.deal_text4);
        textView3.setText("平仓价");
        textView4.setText("盈亏");

        mRecycler= mView.findViewById(R.id.deal_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mAdapter= new ChiCangHistoryListAdapter(R.layout.item_deal_recycler);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        mRecycler.setAdapter(mAdapter);
        appToken= MyApplication.getInstance().getToken();

        return mView;
    }

    private void requestNetData(){
        if (appToken!= null && appToken.length()> 0){
            addNetWork(Network.getInstance().getChiCangHistoryList(appToken, String.valueOf(currentPage))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<HttpResult<ListDataEntity<ChiCangHistoryEntity, ChiCangHistoryQueryParam>>>() {
                        @Override
                        public void accept(HttpResult<ListDataEntity<ChiCangHistoryEntity, ChiCangHistoryQueryParam>> listDataEntityHttpResult) throws Exception {
                            if ("error".equals(listDataEntityHttpResult.getStatus())){
                                mAdapter.loadMoreFail();
                                showShortToast(listDataEntityHttpResult.getDescription());
                            }else if ("success".equals(listDataEntityHttpResult.getStatus())){
                                if (listDataEntityHttpResult.getData().getList().size()> 0){
                                    mAdapter.addData(listDataEntityHttpResult.getData().getList());
                                    mAdapter.loadMoreComplete();
                                }else {
                                    mAdapter.loadMoreFail();
                                    if (currentPage== 1){
                                        mAdapter.setEmptyView(R.layout.layout_recycler_empty);
                                    }
                                }
                                if (listDataEntityHttpResult.getData().isLast()){
                                    mAdapter.loadMoreEnd();
                                }
                            }
                        }
                    }));
        }
    }

    @Override
    public void onLoadMoreRequested() {
        currentPage+= 1;
        requestNetData();
    }
}
