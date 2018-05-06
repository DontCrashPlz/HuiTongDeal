package com.zheng.zchlibrary.widgets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zheng.zchlibrary.R;

import java.util.List;

/**
 * Created by Zheng on 2018/1/8.
 */

public class BaseRecyclerFragment<T> extends LazyLoadFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener{

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    private boolean isPrepared;
    private boolean isLoadedOnce;

    private int mPageIndex;
    private int mMaxPageIndex;

    private BaseQuickAdapter mAdapter;
    private RecyclerView.ItemDecoration mItemDecoration;

    public BaseRecyclerFragment(BaseQuickAdapter adapter, RecyclerView.ItemDecoration itemDecoration){
        mAdapter= adapter;
        mItemDecoration= itemDecoration;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_loadmore_recycler, container, false);
        mRefreshLayout= (SwipeRefreshLayout) mView.findViewById(R.id.swiperefresh);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark),
                getResources().getColor(R.color.colorPrimary));
        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerView= (RecyclerView) mView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter.setOnLoadMoreListener(this,mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(mItemDecoration);

        mProgressBar= (ProgressBar) mView.findViewById(R.id.progressBar);
//        mEmptyTip= (TextView) mView.findViewById(R.id.empty_tip);

        isPrepared= true;

        if (isVisible) lazyLoad();


        return mView;
    }

    @Override
    public void lazyLoad() {
        if (isLoadedOnce || !isPrepared || !isVisible)
            return;

        onRefresh();
    }

    public void dismissProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * 页面处理refresh的数据   call this method when refresh request successed.
     * @param entities
     * @param pageNum
     */
    public void refreshData(List<T> entities, int pageNum) {

        if (mProgressBar.getVisibility()== View.VISIBLE) dismissProgressBar();

        if (mRefreshLayout.isRefreshing()){
            mRefreshLayout.setRefreshing(false);
        }

        mRefreshLayout.setEnabled(true);
        mAdapter.setEnableLoadMore(true);

        if (entities.size()== 0){
            mAdapter.setEmptyView(R.layout.layout_empty);
        }else {
            mAdapter.setNewData(entities);
            mMaxPageIndex= pageNum;
        }

        isLoadedOnce= true;
    }

    /**
     * 页面处理loadMore的数据   call this method when loadMore request successed.
     * @param entities
     */
    public void loadMoreData(List<T> entities) {

        mRefreshLayout.setEnabled(true);
        mAdapter.setEnableLoadMore(true);

        mAdapter.addData(entities);
        mAdapter.loadMoreComplete();
    }

    /**
     * 页面处理数据加载失败   call this method when net request failed.
     * @param msg
     */
    public void dataLoadFailed(String msg) {
        mRefreshLayout.setEnabled(true);
        mAdapter.setEnableLoadMore(true);
        Toast.makeText( this.getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mRefreshLayout.setEnabled(false);
        mAdapter.setEnableLoadMore(false);
        mPageIndex= 1;
//        mPresenter.refreshData();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mPageIndex+= 1;
        if (mPageIndex> mMaxPageIndex){
            mAdapter.loadMoreEnd();
            return;
        }
        mRefreshLayout.setEnabled(false);
        mAdapter.setEnableLoadMore(false);
//        mPresenter.loadMoreData(mPageIndex);
    }
}
