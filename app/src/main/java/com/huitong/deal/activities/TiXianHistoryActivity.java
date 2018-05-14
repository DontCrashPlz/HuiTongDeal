package com.huitong.deal.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huitong.deal.R;
import com.huitong.deal.adapters.BillListAdapter;
import com.huitong.deal.adapters.TiXianListAdapter;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.BillEntity;
import com.huitong.deal.beans.ChiCangHistoryQueryParam;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.ListDataEntity;
import com.huitong.deal.beans.TiXianHistoryEntity;
import com.huitong.deal.beans.TiXianHistoryQueryParam;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.LogUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/16.
 */

public class TiXianHistoryActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFunctionTv;

    private RecyclerView mRecycler;
    private TiXianListAdapter mAdapter;

    private String appToken;
    private int currentPage= 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_deposit_history);

        initUI();
    }

    private void initUI() {
        mBackIv = (ImageView) findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv = (TextView) findViewById(R.id.toolbar_title);
        mTitleTv.setText("提现记录");
        mFunctionTv = (TextView) findViewById(R.id.toolbar_right_text);
        mFunctionTv.setVisibility(View.GONE);

        mRecycler= (RecyclerView) findViewById(R.id.tixian_history_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mAdapter= new TiXianListAdapter(R.layout.item_pay_recycler_white);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        mRecycler.setAdapter(mAdapter);

        appToken= MyApplication.getInstance().getToken();

        requestNetData();
    }

    private void requestNetData(){
        if (appToken!= null && appToken.length()> 0){
            addNetWork(Network.getInstance().getTiXianHistory(appToken, currentPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<HttpResult<ListDataEntity<TiXianHistoryEntity, TiXianHistoryQueryParam>>>() {
                        @Override
                        public void accept(HttpResult<ListDataEntity<TiXianHistoryEntity, TiXianHistoryQueryParam>> listDataEntityHttpResult) throws Exception {
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
                            LogUtil.d("throwable", throwable.toString());
                            showShortToast("网络请求失败");
                            mAdapter.loadMoreFail();
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
