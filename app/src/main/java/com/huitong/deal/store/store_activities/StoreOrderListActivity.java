package com.huitong.deal.store.store_activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans_store.OrderItemEntity;
import com.huitong.deal.beans_store.OrderListEntity;
import com.huitong.deal.https.HttpUtils;
import com.huitong.deal.https.Network;
import com.huitong.deal.https.ResponseTransformer;
import com.huitong.deal.store.store_adapter.StoreOrderListAdapter;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/6/10.
 */

public class StoreOrderListActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, BaseQuickAdapter.RequestLoadMoreListener {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFuncTv;

    private RadioButton mRbtn1;
    private RadioButton mRbtn2;
    private RadioButton mRbtn3;
    private RadioButton mRbtn4;
    private RadioButton mRbtn5;

    private RecyclerView mRecycler;
    private StoreOrderListAdapter mAdapter;

    private int mCurrentPage= 1;
    private int mCurrentType= ORDER_TYPE_ALL;

    public static final String ORDER_TYPE_TAG= "order_type";
    public static final int ORDER_TYPE_ALL= 0;
    public static final int ORDER_TYPE_WAITPAY= 10;
    public static final int ORDER_TYPE_WAITSEND= 20;
    public static final int ORDER_TYPE_WAITRECEIVE= 30;
    public static final int ORDER_TYPE_RECEIVED= 50;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_order_list);

        mCurrentType= getIntent().getIntExtra(ORDER_TYPE_TAG, ORDER_TYPE_ALL);

        mBackIv= findViewById(R.id.toolbar_back);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv= findViewById(R.id.toolbar_title);
        mTitleTv.setText("全部订单");
        mFuncTv= findViewById(R.id.toolbar_function);
        mFuncTv.setVisibility(View.INVISIBLE);

        mRbtn1= findViewById(R.id.order_list_rbtn1);
        mRbtn1.setOnCheckedChangeListener(this);
        mRbtn2= findViewById(R.id.order_list_rbtn2);
        mRbtn2.setOnCheckedChangeListener(this);
        mRbtn3= findViewById(R.id.order_list_rbtn3);
        mRbtn3.setOnCheckedChangeListener(this);
        mRbtn4= findViewById(R.id.order_list_rbtn4);
        mRbtn4.setOnCheckedChangeListener(this);
        mRbtn5= findViewById(R.id.order_list_rbtn5);
        mRbtn5.setOnCheckedChangeListener(this);

        mRecycler= findViewById(R.id.order_list_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mAdapter= new StoreOrderListAdapter(R.layout.store_item_order_list);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        mRecycler.setAdapter(mAdapter);

        if (mCurrentType== ORDER_TYPE_ALL){
            mRbtn1.setChecked(true);
        }else if (mCurrentType== ORDER_TYPE_WAITPAY){
            mRbtn2.setChecked(true);
        }else if (mCurrentType== ORDER_TYPE_WAITSEND){
            mRbtn3.setChecked(true);
        }else if (mCurrentType== ORDER_TYPE_WAITRECEIVE){
            mRbtn4.setChecked(true);
        }else if (mCurrentType== ORDER_TYPE_RECEIVED){
            mRbtn5.setChecked(true);
        }
    }

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在请求网络..");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.order_list_rbtn1:{
                if (isChecked){
                    mCurrentPage= 1;
                    mCurrentType= ORDER_TYPE_ALL;
                    requestOrderList();
                }
                break;
            }
            case R.id.order_list_rbtn2:{
                if (isChecked){
                    mCurrentPage= 1;
                    mCurrentType= ORDER_TYPE_WAITPAY;
                    requestOrderList();
                }
                break;
            }
            case R.id.order_list_rbtn3:{
                if (isChecked){
                    mCurrentPage= 1;
                    mCurrentType= ORDER_TYPE_WAITSEND;
                    requestOrderList();
                }
                break;
            }
            case R.id.order_list_rbtn4:{
                if (isChecked){
                    mCurrentPage= 1;
                    mCurrentType= ORDER_TYPE_WAITRECEIVE;
                    requestOrderList();
                }
                break;
            }
            case R.id.order_list_rbtn5:{
                if (isChecked){
                    mCurrentPage= 1;
                    mCurrentType= ORDER_TYPE_RECEIVED;
                    requestOrderList();
                }
                break;
            }
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mCurrentPage+= 1;
        requestOrderList();
    }

    private void requestOrderList(){
        addNetWork(Network.getInstance().getOrderList(MyApplication.appToken,
                mCurrentPage, mCurrentType)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<OrderListEntity>handleResult())
                .subscribe(new Consumer<OrderListEntity>() {
                    @Override
                    public void accept(OrderListEntity orderListEntity) throws Exception {
                        dismissDialog();
                        if (orderListEntity.getList().size()== 0) {
                            mAdapter.loadMoreFail();
                            if (mCurrentPage== 1){
                                mAdapter.setNewData(new ArrayList<OrderItemEntity>());
                                mAdapter.setEmptyView(R.layout.layout_recycler_empty2);
                            }
                        }else {
                            if (mCurrentPage== 1){
                                mAdapter.setNewData(orderListEntity.getList());
                            }else {
                                mAdapter.addData(orderListEntity.getList());
                            }
                            mAdapter.loadMoreComplete();
                        }
                        if (orderListEntity.isLast()) mAdapter.loadMoreEnd();

                        mRbtn2.setText("待付款(" + orderListEntity.getExtData().getOrdernum_waitpay() + ")");
                        mRbtn3.setText("待发货(" + orderListEntity.getExtData().getOrdernum_waitSend() + ")");
                        mRbtn4.setText("待收货(" + orderListEntity.getExtData().getOrdernum_waitReceive() + ")");
                        mRbtn5.setText("已发货(" + orderListEntity.getExtData().getOrdernum_received() + ")");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        showShortToast(HttpUtils.parseThrowableMsg(throwable));
                        mAdapter.loadMoreFail();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                }));
    }

}
