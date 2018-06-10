package com.huitong.deal.store.store_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans_store.AddressEntity;
import com.huitong.deal.beans_store.HomePageBannerEntity;
import com.huitong.deal.beans_store.ProductDetailEntity;
import com.huitong.deal.beans_store.StoreOrderEntity;
import com.huitong.deal.https.HttpUtils;
import com.huitong.deal.https.Network;
import com.huitong.deal.https.ResponseTransformer;
import com.huitong.deal.store.store_adapter.ProductParamAdapter;
import com.huitong.deal.widgets.GlideImageLoader;
import com.youth.banner.BannerConfig;
import com.zheng.zchlibrary.apps.BaseActivity;
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

public class StoreConfirmOrderActivity extends BaseActivity implements View.OnClickListener {

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

    private ImageView mImageIv;//商品图片
    private TextView mProductNameTv;//商品名称
    private TextView mProductRemarkTv;//商品描述
    private TextView mGouWuQuanPrice;//购物券单价
    private TextView mGouWuQuanUnit;//计量单位
    private TextView mTiHuoQuanPrice;//提货券单价
    private TextView mTiHuoQuanUnit;//计量单位

    private TextView mBuyCount;//购买数量
    private TextView mAllCount;//购买总数
    private TextView mGouWuQuanReal;//购物券实付款
    private TextView mTiHuoQuanReal;//提货券实付款
    private TextView mGouWuQuanAll;//购物券合计
    private TextView mTiHuoQuanAll;//提货券合计
    private Button mCommitBtn;//提交按钮

    private String goodId;
    private int buyCount;
    private int addressId;
    private int gouWuQuanMoney;//购物券总金额
    private int tiHuoQuanMoney;//提货券总金额

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_confirm_order);

        goodId= getIntent().getStringExtra("good_id");
        buyCount= getIntent().getIntExtra("buycount", 1);
        if (goodId== null || goodId.length()< 1){
            showShortToast("页面启动失败");
            finish();
            return;
        }

        initUI();

        getDefaultAddress();
        getProductDetail();
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

        mImageIv= findViewById(R.id.commodity_shopping_image);//商品图片
        mProductNameTv= findViewById(R.id.commodity_shopping_name);//商品名称
        mProductRemarkTv= findViewById(R.id.commodity_shopping_remark);//商品描述
        mGouWuQuanPrice= findViewById(R.id.commodity_shopping_price_red);//购物券单价
        mGouWuQuanUnit= findViewById(R.id.commodity_shopping_red_unit);//计量单位
        mTiHuoQuanPrice= findViewById(R.id.commodity_shopping_price_yellow);//提货券单价
        mTiHuoQuanUnit= findViewById(R.id.commodity_shopping_yellow_unit);//计量单位
        mBuyCount= findViewById(R.id.commodity_shopping_num);//购买数量

        mAllCount= findViewById(R.id.confirm_order_buy_count);//购买总数
        mGouWuQuanReal= findViewById(R.id.confirm_order_gouwuquan);//购物券实付款
        mTiHuoQuanReal= findViewById(R.id.confirm_order_tihuoquan);//提货券实付款
        mGouWuQuanAll= findViewById(R.id.confirm_order_final_gouwuquan);//购物券合计
        mTiHuoQuanAll= findViewById(R.id.confirm_order_final_tihuoquan);//提货券合计
        mCommitBtn= findViewById(R.id.confirm_order_commit);//提交按钮
        mCommitBtn.setOnClickListener(this);
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
            addNetWork(Network.getInstance().commitNowBuyOrder(
                    String.valueOf(addressId),
                    goodId,
                    String.valueOf(buyCount),
                    MyApplication.appToken)
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

    /**
     * 获取商品详情
     */
    private void getProductDetail(){
        addNetWork(Network.getInstance().getProductDetail(goodId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<ProductDetailEntity>handleResult())
                .subscribe(new Consumer<ProductDetailEntity>() {
                    @Override
                    public void accept(ProductDetailEntity productDetailEntity) throws Exception {
                        dismissDialog();
                        Glide.with(getRealContext()).load(productDetailEntity.getImgurl()).into(mImageIv);
                        mProductNameTv.setText(productDetailEntity.getGoods_name());
                        mProductRemarkTv.setText(productDetailEntity.getGoods_name_english());
                        mGouWuQuanPrice.setText(String.valueOf(productDetailEntity.getStore_price()));
                        mGouWuQuanUnit.setText(String.format(
                                getString(R.string.store_buying_unit),
                                productDetailEntity.getSale_unit()));
                        mTiHuoQuanPrice.setText(String.valueOf(productDetailEntity.getGoods_integral()));
                        mTiHuoQuanUnit.setText(String.format(
                                getString(R.string.store_buying_unit),
                                productDetailEntity.getSale_unit()));

                        mBuyCount.setText("x" + buyCount);
                        mAllCount.setText(String.format(getString(R.string.store_buying_buy_count), String.valueOf(buyCount)));
                        gouWuQuanMoney= productDetailEntity.getStore_price()*buyCount;
                        tiHuoQuanMoney= productDetailEntity.getGoods_integral()*buyCount;
                        mGouWuQuanReal.setText(String.valueOf(gouWuQuanMoney));
                        mTiHuoQuanReal.setText(String.valueOf(tiHuoQuanMoney));
                        mGouWuQuanAll.setText(String.valueOf(gouWuQuanMoney));
                        mTiHuoQuanAll.setText(String.valueOf(tiHuoQuanMoney));
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
