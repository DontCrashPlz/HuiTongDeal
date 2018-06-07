package com.huitong.deal.store.store_activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans_store.AddressEntity;
import com.huitong.deal.https.HttpUtils;
import com.huitong.deal.https.Network;
import com.huitong.deal.https.ResponseTransformer;
import com.huitong.deal.store.store_adapter.AddressListAdapter;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/6/7.
 */

public class StoreMineAddressActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFuncTv;

    private RecyclerView mRecycler;
    private AddressListAdapter mAdapter;
    private Button mButton;

    private ArrayList<AddressEntity> mAddressList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_mine_address);

        mBackIv= findViewById(R.id.toolbar_back);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv= findViewById(R.id.toolbar_title);
        mTitleTv.setText("收货地址");
        mFuncTv= findViewById(R.id.toolbar_function);
        mFuncTv.setVisibility(View.INVISIBLE);

        mRecycler= findViewById(R.id.address_manage_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mAdapter= new AddressListAdapter(R.layout.store_item_address);
        mAdapter.onAttachedToRecyclerView(mRecycler);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showShortToast("positon:" + position);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.address_default:{
                        showShortToast("默认" + "--positionId" + mAddressList.get(position).getId());
                        break;
                    }
                    case R.id.address_modify:{
                        showShortToast("修改" + "--positionId" + mAddressList.get(position).getId());
                        break;
                    }
                    case R.id.address_delete:{
                        showShortToast("删除" + "--positionId" + mAddressList.get(position).getId());
                        break;
                    }
                }
            }
        });
        mRecycler.setAdapter(mAdapter);

        mButton= findViewById(R.id.address_manage_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        requestAddressList();
    }

    private void requestAddressList(){
        addNetWork(Network.getInstance().getAddressList(MyApplication.appToken)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<ArrayList<AddressEntity>>handleResult())
                .subscribe(new Consumer<ArrayList<AddressEntity>>() {
                    @Override
                    public void accept(ArrayList<AddressEntity> addressEntities) throws Exception {
                        dismissDialog();
                        mAddressList= addressEntities;
                        if (addressEntities.size()== 0){
                            mAdapter.setNewData(new ArrayList<AddressEntity>());
                            mAdapter.setEmptyView(R.layout.store_layout_address_empty);
                        }else if (addressEntities.size()> 0){
                            mAdapter.setNewData(addressEntities);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        showShortToast(HttpUtils.parseThrowableMsg(throwable));
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

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在请求网络..");
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                clearNetWork();
            }
        });
    }
}
