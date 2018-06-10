package com.huitong.deal.store.store_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans_store.AddressEntity;
import com.huitong.deal.beans_store.ProductDetailEntity;
import com.huitong.deal.beans_store.ShopCartItemEntity;
import com.huitong.deal.beans_store.StoreOrderEntity;
import com.huitong.deal.https.HttpUtils;
import com.huitong.deal.https.Network;
import com.huitong.deal.https.ResponseTransformer;
import com.huitong.deal.store.store_adapter.ConfirmOrderCartItemAdapter;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/6/9.
 */

public class StoreConfirmBuyCartOrderActivity extends BaseActivity implements View.OnClickListener {

    //选择地址的请求码和返回码
    public static final int ORDER_ADDRESS_REQUEST_CODE= 102;
    public static final int ORDER_ADDRESS_RESULT_CODE= 103;

    //标题头
    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFuncTv;

    private RelativeLayout mAddressPanel;//地址选择按钮
    private TextView mNameTv;//收货人姓名
    private TextView mMobileTv;//收货人电话号码
    private TextView mDetailTv;//收货人详细地址

    private RecyclerView mRecycler;
    private ConfirmOrderCartItemAdapter mAdapter;

    private TextView mAllCount;//购买总数
    private TextView mGouWuQuanReal;//购物券实付款
    private TextView mTiHuoQuanReal;//提货券实付款
    private TextView mGouWuQuanAll;//购物券合计
    private TextView mTiHuoQuanAll;//提货券合计
    private Button mCommitBtn;//提交按钮

    private ArrayList<ShopCartItemEntity> goodsList;
    private int addressId;

    private int productNum= 0;
    private int gouWuQuanMoney= 0;//购物券总金额
    private int tiHuoQuanMoney= 0;//提货券总金额
    private StringBuilder cartItemIds= new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_confirm_buycart_order);

        goodsList= (ArrayList<ShopCartItemEntity>) getIntent().getSerializableExtra("selected_buycart_list");
        if (goodsList== null || goodsList.size()< 1){
            showShortToast("页面启动失败");
            finish();
            return;
        }

        initUI();

        getDefaultAddress();
    }

    private void initUI() {
        mBackIv= findViewById(R.id.toolbar_back);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv= findViewById(R.id.toolbar_title);
        mTitleTv.setText("确认订单");
        mFuncTv= findViewById(R.id.toolbar_function);
        mFuncTv.setVisibility(View.INVISIBLE);

        mAddressPanel= findViewById(R.id.confirm_order_address_panel);//地址选择按钮
        mAddressPanel.setOnClickListener(this);
        mNameTv= findViewById(R.id.confirm_order_address_name);//收货人姓名
        mMobileTv= findViewById(R.id.confirm_order_address_mobile);//收货人电话号码
        mDetailTv= findViewById(R.id.confirm_order_address_detail);//收货人详细地址

        mRecycler= findViewById(R.id.confirm_order_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mAdapter= new ConfirmOrderCartItemAdapter(R.layout.store_layout_commodity_buying_gary, goodsList);
        mRecycler.setAdapter(mAdapter);

        mAllCount= findViewById(R.id.confirm_order_buy_count);//购买总数
        mGouWuQuanReal= findViewById(R.id.confirm_order_gouwuquan);//购物券实付款
        mTiHuoQuanReal= findViewById(R.id.confirm_order_tihuoquan);//提货券实付款
        mGouWuQuanAll= findViewById(R.id.confirm_order_final_gouwuquan);//购物券合计
        mTiHuoQuanAll= findViewById(R.id.confirm_order_final_tihuoquan);//提货券合计
        mCommitBtn= findViewById(R.id.confirm_order_commit);//提交按钮
        mCommitBtn.setOnClickListener(this);

        for (ShopCartItemEntity entity : goodsList){
            productNum+= entity.getCount();
            gouWuQuanMoney+= entity.getSubtotalcash();
            tiHuoQuanMoney+= entity.getSubtotalintegral();
            cartItemIds.append(entity.getId() + "_");
        }
        cartItemIds.deleteCharAt(cartItemIds.length()-1);
        LogUtil.d("拼接完成后的cartItemIds参数", cartItemIds.toString());
        mAllCount.setText(
                String.format(
                        getString(R.string.store_buying_buy_count),
                        String.valueOf(productNum)));
        mGouWuQuanReal.setText(String.valueOf(gouWuQuanMoney));
        mTiHuoQuanReal.setText(String.valueOf(tiHuoQuanMoney));
        mGouWuQuanAll.setText(String.valueOf(gouWuQuanMoney));
        mTiHuoQuanAll.setText(String.valueOf(tiHuoQuanMoney));
    }

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在请求网络..");
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.confirm_order_address_panel){
            Intent intent= new Intent(getRealContext(), StoreMineAddressActivity.class);
            intent.putExtra("launch_from_order", 1);
            startActivityForResult(intent, ORDER_ADDRESS_REQUEST_CODE);
        }else if (v.getId()== R.id.confirm_order_commit){
            addNetWork(Network.getInstance().commitBuyCartOrder(
                    MyApplication.appToken,
                    cartItemIds.toString(),
                    String.valueOf(addressId))
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(ResponseTransformer.<StoreOrderEntity>handleResult())
                    .subscribe(new Consumer<StoreOrderEntity>() {
                        @Override
                        public void accept(StoreOrderEntity storeOrderEntity) throws Exception {
                            dismissDialog();
                            Intent intent= new Intent(getRealContext(), StorePayOrderActivity.class);
                            intent.putExtra("gouWuQuan_Money", gouWuQuanMoney);
                            intent.putExtra("tiHuoQuan_Money", tiHuoQuanMoney);
                            intent.putExtra("orderNo", storeOrderEntity.getOrder_no());
                            startActivity(intent);
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
    }

    /**
     * 获取默认地址
     */
    private void getDefaultAddress(){
        addNetWork(Network.getInstance().getAddressList(MyApplication.appToken)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<ArrayList<AddressEntity>>handleResult())
                .subscribe(new Consumer<ArrayList<AddressEntity>>() {
                    @Override
                    public void accept(ArrayList<AddressEntity> addressEntities) throws Exception {
                        dismissDialog();
                        for (AddressEntity entity : addressEntities){
                            if (entity.isIsdefault()){
                                mNameTv.setText(String.format(getString(R.string.store_confirm_order_name),entity.getRecvname()));
                                mMobileTv.setText(entity.getMobile());
                                mDetailTv.setText(String.format(getString(R.string.store_confirm_order_address), entity.getAddress()));
                                addressId= entity.getId();
                            }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode== ORDER_ADDRESS_REQUEST_CODE && resultCode== ORDER_ADDRESS_RESULT_CODE){
            AddressEntity entity= (AddressEntity) data.getSerializableExtra("selected_address");
            mNameTv.setText(String.format(getString(R.string.store_confirm_order_name),entity.getRecvname()));
            mMobileTv.setText(entity.getMobile());
            mDetailTv.setText(String.format(getString(R.string.store_confirm_order_address), entity.getAddress()));
            addressId= entity.getId();
        }
    }
}
