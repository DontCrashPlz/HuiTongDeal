package com.huitong.deal.store.store_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans_store.ShopCartEntity;
import com.huitong.deal.beans_store.ShopCartItemEntity;
import com.huitong.deal.https.HttpUtils;
import com.huitong.deal.https.Network;
import com.huitong.deal.https.ResponseTransformer;
import com.huitong.deal.store.store_activities.StoreConfirmBuyCartOrderActivity;
import com.huitong.deal.store.store_adapter.ShopCartEditAdapter;
import com.huitong.deal.store.store_adapter.ShopCartShowAdapter;
import com.zheng.zchlibrary.apps.BaseFragment;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/13.
 */

public class StoreHomeShoppingFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFuncTv;

    private RecyclerView mRecycler;
    private ShopCartShowAdapter mShowAdapter;
    private ShopCartEditAdapter mEditAdapter;

    private CheckBox mSelectAllCb;
    private TextView mGouWuQuanTv;
    private TextView mTiHuoQuanTv;
    private Button mCommitBtn;

    private int mAllProductCount;//所有商品的总量
    private ArrayList<ShopCartItemEntity> mShopCartItemList= new ArrayList<>();//购物车列表数据
    public static ArrayList<ShopCartItemEntity> mSelectedList= new ArrayList<>();

    public static StoreHomeShoppingFragment newInstance(String content){
        StoreHomeShoppingFragment instance = new StoreHomeShoppingFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        instance.setArguments(args);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.store_fragment_home_shopping, container, false);

        initUI(mView);

        requestShopCartList();

        return mView;
    }

    private void initUI(View view) {
        mBackIv= view.findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.INVISIBLE);
        mTitleTv= view.findViewById(R.id.toolbar_title);
        mTitleTv.setText("购物车");
        mFuncTv= view.findViewById(R.id.toolbar_function);
        mFuncTv.setText("编辑");
        mFuncTv.setOnClickListener(this);
        mFuncTv.setVisibility(View.INVISIBLE);

        mRecycler= view.findViewById(R.id.home_shopping_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
       // mEditAdapter= new ShopCartEditAdapter(R.layout.store_layout_commodity_buying_edit);

        mSelectAllCb= view.findViewById(R.id.home_shopping_select_all);
        mSelectAllCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mSelectedList.clear();
                    mSelectedList.addAll(mShopCartItemList);
                    computeAllPrice();
                    mShowAdapter.notifyDataSetChanged();
                }else {
                    mSelectedList.clear();
                    computeAllPrice();
                    mShowAdapter.notifyDataSetChanged();
                }
            }
        });
        mGouWuQuanTv= view.findViewById(R.id.home_shopping_gouwuquan);
        mTiHuoQuanTv= view.findViewById(R.id.home_shopping_tihuoquan);
        mCommitBtn= view.findViewById(R.id.home_shopping_commit);
        mCommitBtn.setText(String.format(getString(R.string.store_shopping_commit), "0"));
        mCommitBtn.setOnClickListener(this);
    }

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在请求网络..");
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.toolbar_function){

        }else if (v.getId()== R.id.home_shopping_commit){
            Intent intent= new Intent(getRealContext(), StoreConfirmBuyCartOrderActivity.class);
            ArrayList<ShopCartItemEntity> selectedList= new ArrayList<>();
            selectedList.addAll(mSelectedList);
            intent.putExtra("selected_buycart_list", selectedList);
            startActivity(intent);
        }
    }

    /**
     * 请求购物车列表数据
     */
    private void requestShopCartList(){
        addNetWork(Network.getInstance().getShopCartList(MyApplication.appToken)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<ShopCartEntity>handleResult())
                .subscribe(new Consumer<ShopCartEntity>() {
                    @Override
                    public void accept(ShopCartEntity shopCartEntity) throws Exception {
                        dismissDialog();
                        mShowAdapter= new ShopCartShowAdapter(R.layout.store_layout_commodity_buying_white);
                        mShowAdapter.bindToRecyclerView(mRecycler);
                        mShowAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                CheckBox checkBox= view.findViewById(R.id.commodity_shopping_checkbox);
                                if (checkBox.isChecked()){
                                    mSelectedList.remove(mShopCartItemList.get(position));
                                    computeAllPrice();
                                    mShowAdapter.notifyDataSetChanged();
                                }else {
                                    mSelectedList.add(mShopCartItemList.get(position));
                                    computeAllPrice();
                                    mShowAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                        //mShowAdapter.bindToRecyclerView(mRecycler);
                        mRecycler.setAdapter(mShowAdapter);
                        mAllProductCount= shopCartEntity.getCart_goods_count();
                        mTitleTv.setText("购物车(" + mAllProductCount + ")");
                        mShopCartItemList= shopCartEntity.getCart_list().get(0).getItemlist();
                        if (mShopCartItemList== null || mShopCartItemList.size()== 0){
                            mShowAdapter.setEmptyView(R.layout.store_layout_buycart_empty);
                        }else {
                            mShowAdapter.addData(mShopCartItemList);
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
     * 计算合计价格
     */
    private void computeAllPrice(){
        int allGouWuQuanPrice= 0;
        int allTiHuoQuanPrice= 0;
        if (mSelectedList== null || mSelectedList.size()< 1){
            mGouWuQuanTv.setText("0");
            mTiHuoQuanTv.setText("0");
            mCommitBtn.setText(
                    String.format(
                            getString(R.string.store_shopping_commit), "0"));
        }else {
            for (ShopCartItemEntity entity : mSelectedList){
                allGouWuQuanPrice+= entity.getSubtotalcash();
                allTiHuoQuanPrice+= entity.getSubtotalintegral();
            }
            mGouWuQuanTv.setText(String.valueOf(allGouWuQuanPrice));
            mTiHuoQuanTv.setText(String.valueOf(allTiHuoQuanPrice));
            mCommitBtn.setText(
                    String.format(
                            getString(R.string.store_shopping_commit),
                            String.valueOf(mSelectedList.size())));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSelectedList!= null && mSelectedList.size()> 0) mSelectedList.clear();
    }
}
