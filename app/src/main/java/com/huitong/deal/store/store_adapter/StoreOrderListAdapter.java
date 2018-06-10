package com.huitong.deal.store.store_adapter;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.PayTypeEntity;
import com.huitong.deal.beans_store.OrderItemEntity;
import com.huitong.deal.https.HttpUtils;
import com.huitong.deal.https.Network;
import com.huitong.deal.https.ResponseTransformer;
import com.huitong.deal.store.store_activities.StoreOrderDetailActivity;
import com.huitong.deal.store.store_activities.StoreOrderListActivity;
import com.huitong.deal.store.store_activities.StorePayOrderActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/6/7.
 */

public class StoreOrderListAdapter extends BaseQuickAdapter<OrderItemEntity, BaseViewHolder> {


    public StoreOrderListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderItemEntity item) {
        helper.setText(R.id.item_order_orderno, item.getOrder_id())
                .setText(R.id.item_order_gouwuquan, String.valueOf(item.getTotalprice()))
                .setText(R.id.item_order_tihuoquan, String.valueOf(item.getTotalintegral()));
        RecyclerView recyclerView= helper.getView(R.id.item_order_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new StoreOrderProductListAdapter(R.layout.store_layout_commodity_buying_gary, item.getGoodlist()));
        switch (item.getOrder_status()){
            case 10:{
                helper.setText(R.id.item_order_status, "等待买家付款");
                helper.setVisible(R.id.item_order_button_cancel, true)
                        .setVisible(R.id.item_order_button_detail, true)
                        .setVisible(R.id.item_order_button_pay, true)
                        .setGone(R.id.item_order_button_confirm,false);
                helper.getView(R.id.item_order_button_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(1, String.valueOf(item.getId()));
                    }
                });
                helper.getView(R.id.item_order_button_pay).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(mContext, StorePayOrderActivity.class);
                        intent.putExtra("gouWuQuan_Money", item.getTotalprice());
                        intent.putExtra("tiHuoQuan_Money", item.getTotalintegral());
                        intent.putExtra("orderNo", item.getOrder_id());
                        mContext.startActivity(intent);
                    }
                });
                break;
            }
            case 20:{
                helper.setText(R.id.item_order_status, "等待卖家发货");
                helper.setGone(R.id.item_order_button_cancel, false)
                        .setVisible(R.id.item_order_button_detail, true)
                        .setGone(R.id.item_order_button_pay, false)
                        .setGone(R.id.item_order_button_confirm,false);
                break;
            }
            case 30:{
                helper.setText(R.id.item_order_status, "卖家已发货");
                helper.setGone(R.id.item_order_button_cancel, false)
                        .setVisible(R.id.item_order_button_detail, true)
                        .setGone(R.id.item_order_button_pay, false)
                        .setVisible(R.id.item_order_button_confirm,true);
                helper.getView(R.id.item_order_button_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(2, String.valueOf(item.getId()));
                    }
                });
                break;
            }
            case 50:{
                helper.setText(R.id.item_order_status, "交易完成");
                helper.setGone(R.id.item_order_button_cancel, false)
                        .setVisible(R.id.item_order_button_detail, true)
                        .setGone(R.id.item_order_button_pay, false)
                        .setGone(R.id.item_order_button_confirm,false);
                break;
            }
        }
        helper.getView(R.id.item_order_button_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, StoreOrderDetailActivity.class);
                intent.putExtra("order_entity", item);
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 弹出确认下单弹窗
     * @param tag 1表示取消订单，2表示确认收货
     */
    private void showDialog(final int tag, final String orderId){
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_confirm_order, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(mContext, R.style.custom_dialog_no_titlebar);
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
        if (mContext instanceof StoreOrderListActivity){
            ((StoreOrderListActivity)mContext).addNetWork(Network.getInstance().deleteOrder(MyApplication.appToken, orderId)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(ResponseTransformer.<String>handleResult())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            ((StoreOrderListActivity)mContext).dismissDialog();
                            ((StoreOrderListActivity)mContext).showShortToast("订单已取消");
                            StoreOrderListAdapter.this.notifyDataSetChanged();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            ((StoreOrderListActivity)mContext).dismissDialog();
                            ((StoreOrderListActivity)mContext).showShortToast(HttpUtils.parseThrowableMsg(throwable));
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {

                        }
                    }, new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            ((StoreOrderListActivity)mContext).showDialog();
                        }
                    }));
        }
    }

    /**
     * 确认收货网络请求
     * @param orderId
     */
    private void requestConfirmOrder(String orderId){
        if (mContext instanceof StoreOrderListActivity){
            ((StoreOrderListActivity)mContext).addNetWork(Network.getInstance().confirmOrder(MyApplication.appToken, orderId)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(ResponseTransformer.<String>handleResult())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            ((StoreOrderListActivity)mContext).dismissDialog();
                            ((StoreOrderListActivity)mContext).showShortToast("确认收货成功");
                            StoreOrderListAdapter.this.notifyDataSetChanged();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            ((StoreOrderListActivity)mContext).dismissDialog();
                            ((StoreOrderListActivity)mContext).showShortToast(HttpUtils.parseThrowableMsg(throwable));
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {

                        }
                    }, new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            ((StoreOrderListActivity)mContext).showDialog();
                        }
                    }));
        }
    }

}
