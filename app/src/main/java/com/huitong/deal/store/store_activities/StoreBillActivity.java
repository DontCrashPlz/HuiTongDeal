package com.huitong.deal.store.store_activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huitong.deal.R;
import com.huitong.deal.adapters.BillListAdapter;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.BillEntity;
import com.huitong.deal.beans.ChiCangHistoryQueryParam;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.ListDataEntity;
import com.huitong.deal.beans_store.StoreBillEntity;
import com.huitong.deal.https.Network;
import com.huitong.deal.store.store_adapter.StoreBillListAdapter;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.LogUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/5/13.
 */

public class StoreBillActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {
    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mCalenderIv;

    private RecyclerView mRecycler;
    private StoreBillListAdapter mAdapter;

    private ProgressBar progressBar;

    private int currentPage= 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_bill);

        initUI();
    }

    private void initUI() {
        mBackIv = findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv = findViewById(R.id.toolbar_title);
        mTitleTv.setText("账单");
        mCalenderIv = findViewById(R.id.toolbar_function);
        mCalenderIv.setVisibility(View.GONE);

        mRecycler= findViewById(R.id.pay_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mAdapter= new StoreBillListAdapter(R.layout.store_item_pay_recycler);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        mRecycler.setAdapter(mAdapter);

        progressBar= findViewById(R.id.progressBar);

        requestNetData();
    }

    private void requestNetData(){
        addNetWork(Network.getInstance().getStoreBill(MyApplication.appToken, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HttpResult<ListDataEntity<StoreBillEntity, ChiCangHistoryQueryParam>>>() {
                    @Override
                    public void accept(HttpResult<ListDataEntity<StoreBillEntity, ChiCangHistoryQueryParam>> listDataEntityHttpResult) throws Exception {
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
                                    mAdapter.setEmptyView(R.layout.layout_recycler_empty2);
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
                        mAdapter.loadMoreFail();
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
