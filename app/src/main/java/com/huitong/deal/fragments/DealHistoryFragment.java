package com.huitong.deal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huitong.deal.R;
import com.huitong.deal.adapters.ChiCangHistoryListAdapter;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.ChiCangHistoryEntity;
import com.huitong.deal.beans.ChiCangHistoryEntity2;
import com.huitong.deal.beans.ChiCangHistoryQueryParam;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.ListDataEntity;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseFragment;
import com.zheng.zchlibrary.utils.LogUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
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

    private RecyclerView mRecycler;
    private ChiCangHistoryListAdapter mAdapter;

    private ProgressBar progressBar;

    private String appToken;
    private int currentPage= 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_deal, container, false);

        mRecycler= mView.findViewById(R.id.deal_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mAdapter= new ChiCangHistoryListAdapter(R.layout.item_deal_recycler);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        mRecycler.setAdapter(mAdapter);

        progressBar= mView.findViewById(R.id.progressBar);

        appToken= MyApplication.getInstance().getToken();

        requestNetData();

        return mView;
    }

    private void requestNetData(){
        if (appToken!= null && appToken.length()> 0){
            addNetWork(Network.getInstance().getChiCangHistoryList(appToken, String.valueOf(currentPage))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<HttpResult<ListDataEntity<ChiCangHistoryEntity2, ChiCangHistoryQueryParam>>>() {
                        @Override
                        public void accept(HttpResult<ListDataEntity<ChiCangHistoryEntity2, ChiCangHistoryQueryParam>> listDataEntityHttpResult) throws Exception {
                            dismissProgressBar();
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
                            if (currentPage== 1) showProgressBar();
                        }
                    }));
        }
    }

    @Override
    public void onLoadMoreRequested() {
        currentPage+= 1;
        requestNetData();
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
