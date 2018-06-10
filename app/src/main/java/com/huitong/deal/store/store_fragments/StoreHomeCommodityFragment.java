package com.huitong.deal.store.store_fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huitong.deal.R;
import com.huitong.deal.beans_store.HomePageCommodityEntity;
import com.huitong.deal.beans_store.ProductClassEntity;
import com.huitong.deal.beans_store.ProductListEntity;
import com.huitong.deal.https.HttpUtils;
import com.huitong.deal.https.Network;
import com.huitong.deal.https.ResponseTransformer;
import com.huitong.deal.store.store_adapter.CommodityListAdapter;
import com.zheng.zchlibrary.apps.BaseFragment;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/13.
 */

public class StoreHomeCommodityFragment extends BaseFragment implements View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    public static StoreHomeCommodityFragment newInstance(int gc_id){
        StoreHomeCommodityFragment instance = new StoreHomeCommodityFragment();
        Bundle args = new Bundle();
        args.putInt("gc_id", gc_id);
        instance.setArguments(args);
        return instance;
    }

    private DrawerLayout mDrawerLayout;
    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFuncTv;
    private TextView mSelectTv1;//综合
    private TextView mSelectTv2;//销量
    private TextView mSelectTv3;//价格
    private TextView mSelectTv4;//筛选
    private RecyclerView mRecycler;
    private CommodityListAdapter mAdapter;
    private TagFlowLayout mFlowLayout;
    private TagAdapter<ProductClassEntity> tagAdapter;
    private Button mSelectAllBtn;

    private int mTextColorRed= Color.rgb(218,37,28);
    private int mTextColorGary= Color.rgb(102,102,102);

    private int mPriceStatus= 0;//这个字段用于判断当前是否选定价格条件，如果除2余0，价格降序；除2余1，价格升序

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.store_fragment_home_commodity, container, false);

        mGc_id= getArguments().getInt("gc_id", 0);

        initUI(mView);

        return mView;
    }

    private void initUI(View view) {
        mDrawerLayout= view.findViewById(R.id.drawerlayout);
        mBackIv= view.findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.INVISIBLE);
        mTitleTv= view.findViewById(R.id.toolbar_title);
        mTitleTv.setText("全部商品");
        mFuncTv= view.findViewById(R.id.toolbar_function);
        mFuncTv.setVisibility(View.INVISIBLE);
        mSelectTv1= view.findViewById(R.id.store_commodity_zonghe);
        mSelectTv1.setOnClickListener(this);
        mSelectTv2= view.findViewById(R.id.store_commodity_xiaoliang);
        mSelectTv2.setOnClickListener(this);
        mSelectTv3= view.findViewById(R.id.store_commodity_jiage);
        mSelectTv3.setOnClickListener(this);
        mSelectTv4= view.findViewById(R.id.store_commodity_shaixuan);
        mSelectTv4.setOnClickListener(this);
        mRecycler= view.findViewById(R.id.store_commodity_recycler);
        mRecycler.setLayoutManager(new GridLayoutManager(getRealContext(), 2));
        mAdapter= new CommodityListAdapter(R.layout.store_layout_commodity_show);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        mRecycler.setAdapter(mAdapter);

        mFlowLayout= view.findViewById(R.id.store_commodity_flowlayout);

        mSelectAllBtn= view.findViewById(R.id.store_commodity_all);
        mSelectAllBtn.setOnClickListener(this);

        //加载商品分类列表
        doProductClassRequest();
        //页面初始化完成，默认选择综合，加载商品列表
        mSelectTv1.setTextColor(mTextColorRed);
        doProductListRequest();
    }

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在请求网络..");
    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.store_commodity_zonghe:{//选择综合排序
                mSelectTv1.setTextColor(mTextColorRed);
                mSelectTv2.setTextColor(mTextColorGary);
                mSelectTv3.setTextColor(mTextColorGary);
                mOrderType= 0;
                mOrderMode= 0;
                mCurrentPage= 1;
                doProductListRequest();
                break;
            }
            case R.id.store_commodity_xiaoliang:{//选择销量排序
                mSelectTv1.setTextColor(mTextColorGary);
                mSelectTv2.setTextColor(mTextColorRed);
                mSelectTv3.setTextColor(mTextColorGary);
                mOrderType= 0;
                mOrderMode= 0;
                mCurrentPage= 1;
                doProductListRequest();
                break;
            }
            case R.id.store_commodity_jiage:{//选择价格排序
                if (mSelectTv3.getCurrentTextColor()== mTextColorRed) mPriceStatus+= 1;
                mSelectTv1.setTextColor(mTextColorGary);
                mSelectTv2.setTextColor(mTextColorGary);
                mSelectTv3.setTextColor(mTextColorRed);
                mOrderType= 1;
                if (mPriceStatus % 2 == 0){
                    mOrderMode= 0;
                    mSelectTv3.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            null,
                            getResources().getDrawable(R.mipmap.up),
                            null);
                }else {
                    mOrderMode= 1;
                    mSelectTv3.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            null,
                            getResources().getDrawable(R.mipmap.down),
                            null);
                }
                mCurrentPage= 1;
                doProductListRequest();
                break;
            }
            case R.id.store_commodity_shaixuan:{//选择筛选排序
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                break;
            }
            case R.id.store_commodity_all:{
                mGc_id= 0;
                doProductListRequest();
                mDrawerLayout.closeDrawers();
                break;
            }
        }
    }

    private ArrayList<ProductClassEntity> mProductClassList= new ArrayList<>();//当前页面中保存的商品分类列表
    /**
     * 请求商品分类列表
     */
    private void doProductClassRequest(){
        addNetWork(Network.getInstance().getProductClass()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<ArrayList<ProductClassEntity>>handleResult())
                .subscribe(new Consumer<ArrayList<ProductClassEntity>>() {
                    @Override
                    public void accept(ArrayList<ProductClassEntity> productClassEntities) throws Exception {
                        dismissDialog();
                        mProductClassList= productClassEntities;
                        tagAdapter= new TagAdapter<ProductClassEntity>(productClassEntities) {
                            @Override
                            public View getView(FlowLayout parent, int position, ProductClassEntity productClassEntity) {
                                TextView textView= (TextView) LayoutInflater
                                        .from(getRealContext())
                                        .inflate(R.layout.store_flow_tag, parent, false);
                                textView.setText(productClassEntity.getClassname());
                                return textView;
                            }
                        };
                        mFlowLayout.setAdapter(tagAdapter);
                        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                            @Override
                            public boolean onTagClick(View view, int position, FlowLayout parent) {
                                mGc_id= mProductClassList.get(position).getId();
                                mDrawerLayout.closeDrawers();
                                mCurrentPage= 1;
                                doProductListRequest();
                                return false;
                            }
                        });
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

    private int mGc_id= 0;//当前商品分类id，用于筛选商品分类，等于0不传这个参数，将默认查询所有商品
    private int mOrderType= 0;//当前商品排序条件，等于0为综合和销量条件查询（默认），等于1为了价格条件查询
    private int mOrderMode= 0;//当前商品排序方式，等于0为降序（默认），等于1为升序
    private int mCurrentPage= 1;
    /**
     * 请求商品列表
     */
    private void doProductListRequest(){
        addNetWork(Network.getInstance().getProductList(mGc_id, mOrderType, mOrderMode, mCurrentPage)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<ProductListEntity>handleResult())
                .subscribe(new Consumer<ProductListEntity>() {
                    @Override
                    public void accept(ProductListEntity productListEntity) throws Exception {
                        dismissDialog();
                        if (mCurrentPage== 1){//如果现在加载的是第一页，考虑切换查询条件,设置新的数据集合
                            mAdapter.setNewData(new ArrayList<HomePageCommodityEntity>());
                        }
                        if (productListEntity.getList().size()> 0){//如果加载数据有效，添加到列表
                            mAdapter.addData(productListEntity.getList());
                            mAdapter.loadMoreComplete();
                        }else {//如果加载数据无效
                            mAdapter.loadMoreFail();
                            if (mCurrentPage== 1){//如果第一页加载无效，说明没有数据
                                mAdapter.setEmptyView(R.layout.layout_recycler_empty2);
                            }
                        }
                        if (productListEntity.isLast()) mAdapter.loadMoreEnd();//如果这是最后一页，加载更多结束
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
                        if (mCurrentPage== 1) showDialog();
                    }
                }));
    }

    @Override
    public void onLoadMoreRequested() {
        mCurrentPage+= 1;
        doProductListRequest();
    }
}
