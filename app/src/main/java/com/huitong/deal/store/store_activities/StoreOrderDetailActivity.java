package com.huitong.deal.store.store_activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans_store.AddressEntity;
import com.huitong.deal.beans_store.OrderItemEntity;
import com.huitong.deal.https.HttpUtils;
import com.huitong.deal.https.Network;
import com.huitong.deal.https.ResponseTransformer;
import com.huitong.deal.store.store_adapter.StoreOrderProductListAdapter;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/6/10.
 */

public class StoreOrderDetailActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFuncTv;

    private TextView mOrderStatusTv;
    private ImageView mOrderIconIv;

    private TextView mAddressNameTv;
    private TextView mAddressMobileTv;
    private TextView mAddressDetailTv;

    private RecyclerView mRecycler;
    private StoreOrderProductListAdapter mAdapter;
    private TextView mSubGouWuQuanTv;
    private TextView mSubTiHuoQuanTv;

    private TextView mOrderNumberTv;
    private TextView mOrderCreateTimeTv;

    private LinearLayout mButtonPanelBtn;
    private Button mCancelOrderBtn;
    private Button mPayOrderBtn;
    private Button mConfirmOrderBtn;

    private OrderItemEntity entity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_order_detail);

        entity= (OrderItemEntity) getIntent().getSerializableExtra("order_entity");
        if (entity== null){
            showShortToast("页面启动失败");
            finish();
            return;
        }

        initUI();
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
        mTitleTv.setText("订单详情");
        mFuncTv= findViewById(R.id.toolbar_function);
        mFuncTv.setVisibility(View.INVISIBLE);

        mOrderStatusTv= findViewById(R.id.order_detail_status);
        mOrderIconIv= findViewById(R.id.order_detail_icon);

        mAddressNameTv= findViewById(R.id.confirm_order_address_name);
        mAddressMobileTv= findViewById(R.id.confirm_order_address_mobile);
        mAddressDetailTv= findViewById(R.id.confirm_order_address_detail);

        mRecycler= findViewById(R.id.order_detail_recycler);
        mAdapter= new StoreOrderProductListAdapter(R.layout.store_layout_commodity_buying_gary, entity.getGoodlist());
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mRecycler.setAdapter(mAdapter);

        mSubGouWuQuanTv= findViewById(R.id.confirm_order_gouwuquan);
        mSubGouWuQuanTv.setText(String.valueOf(entity.getTotalprice()));
        mSubTiHuoQuanTv= findViewById(R.id.confirm_order_tihuoquan);
        mSubTiHuoQuanTv.setText(String.valueOf(entity.getTotalintegral()));

        mOrderNumberTv= findViewById(R.id.order_detail_orderno);
        mOrderNumberTv.setText(String.format(getString(R.string.order_detail_number), entity.getOrder_id()));
        mOrderCreateTimeTv= findViewById(R.id.order_detail_time);
        mOrderCreateTimeTv.setText(String.format(getString(R.string.order_detail_createtime), entity.getCreate_time()));

        mButtonPanelBtn= findViewById(R.id.item_order_button_panel);
        mCancelOrderBtn= findViewById(R.id.item_order_button_cancel);
        mPayOrderBtn= findViewById(R.id.item_order_button_pay);
        mConfirmOrderBtn= findViewById(R.id.item_order_button_confirm);

        requestAddressDetail(String.valueOf(entity.getAddr_id()));

        switch (entity.getOrder_status()){
            case 10:{
                mButtonPanelBtn.setVisibility(View.VISIBLE);
                mCancelOrderBtn.setVisibility(View.VISIBLE);
                mPayOrderBtn.setVisibility(View.VISIBLE);
                mConfirmOrderBtn.setVisibility(View.GONE);
                mOrderStatusTv.setText("等待买家付款");
                mOrderIconIv.setImageResource(R.mipmap.unpaid);
                mCancelOrderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(1, String.valueOf(entity.getId()));
                    }
                });
                mPayOrderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(getRealContext(), StorePayOrderActivity.class);
                        intent.putExtra("gouWuQuan_Money", entity.getTotalprice());
                        intent.putExtra("tiHuoQuan_Money", entity.getTotalintegral());
                        intent.putExtra("orderNo", entity.getOrder_id());
                        startActivity(intent);
                    }
                });
                break;
            }
            case 20:{
                mButtonPanelBtn.setVisibility(View.GONE);
                mCancelOrderBtn.setVisibility(View.GONE);
                mPayOrderBtn.setVisibility(View.GONE);
                mConfirmOrderBtn.setVisibility(View.GONE);
                mOrderStatusTv.setText("等待卖家发货");
                mOrderIconIv.setImageResource(R.mipmap.delivery_no);
                break;
            }
            case 30:{
                mButtonPanelBtn.setVisibility(View.VISIBLE);
                mCancelOrderBtn.setVisibility(View.GONE);
                mPayOrderBtn.setVisibility(View.GONE);
                mConfirmOrderBtn.setVisibility(View.VISIBLE);
                mOrderStatusTv.setText("卖家已发货");
                mOrderIconIv.setImageResource(R.mipmap.transport);
                mConfirmOrderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(2, String.valueOf(entity.getId()));
                    }
                });
                break;
            }
            case 50:{
                mButtonPanelBtn.setVisibility(View.GONE);
                mCancelOrderBtn.setVisibility(View.GONE);
                mPayOrderBtn.setVisibility(View.GONE);
                mConfirmOrderBtn.setVisibility(View.GONE);
                mOrderStatusTv.setText("交易完成");
                mOrderIconIv.setImageResource(R.mipmap.success_store);
                break;
            }
        }
    }

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在请求网络..");
    }

    /**
     * 弹出确认下单弹窗
     * @param tag 1表示取消订单，2表示确认收货
     */
    private void showDialog(final int tag, final String orderId){
        View view = LayoutInflater.from(getRealContext()).inflate(R.layout.layout_dialog_confirm_order, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(getRealContext(), R.style.custom_dialog_no_titlebar);
        dialog.setContentView(view);
        dialog.show();

        if (tag== 1){
            ((TextView)(dialog.findViewById(R.id.confirm_dialog_tv_title))).setText("您确认取消订单吗？");
        }else if (tag== 2){
            ((TextView)(dialog.findViewById(R.id.confirm_dialog_tv_title))).setText("您要确认收货吗？");
        }

        dialog.findViewById(R.id.confirm_dialog_btn_cancel)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

        dialog.findViewById(R.id.confirm_dialog_btn_confirm)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tag== 1){
                            //todo 取消订单
                            requestDeleteOrder(orderId);
                        }else if (tag== 2){
                            //todo 确认收货
                            requestConfirmOrder(orderId);
                        }
                        dialog.dismiss();
                    }
                });

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    /**
     * 取消订单网络请求
     * @param orderId
     */
    private void requestDeleteOrder(String orderId){
        addNetWork(Network.getInstance().deleteOrder(MyApplication.appToken, orderId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<String>handleResult())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        dismissDialog();
                        showShortToast("订单已取消");
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

    /**
     * 确认收货网络请求
     * @param orderId
     */
    private void requestConfirmOrder(String orderId){
        addNetWork(Network.getInstance().confirmOrder(MyApplication.appToken, orderId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<String>handleResult())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        dismissDialog();
                        showShortToast("确认收货成功");
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

    /**
     * 确认收货网络请求
     * @param add_id
     */
    private void requestAddressDetail(String add_id){
        addNetWork(Network.getInstance().findAddressById(String.valueOf(entity.getAddr_id()), MyApplication.appToken)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<AddressEntity>handleResult())
                .subscribe(new Consumer<AddressEntity>() {
                    @Override
                    public void accept(AddressEntity addressEntity) throws Exception {
                        dismissDialog();
                        mAddressNameTv.setText(addressEntity.getRecvname());
                        mAddressMobileTv.setText(addressEntity.getMobile());
                        mAddressDetailTv.setText(addressEntity.getAddress());
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
