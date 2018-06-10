package com.huitong.deal.store.store_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.beans_store.HomePageBannerEntity;
import com.huitong.deal.beans_store.HomePageEntity;
import com.huitong.deal.beans_store.HomePageFloorEntity;
import com.huitong.deal.https.HttpUtils;
import com.huitong.deal.https.Network;
import com.huitong.deal.https.ResponseTransformer;
import com.huitong.deal.store.store_activities.StoreDetailActivity;
import com.huitong.deal.store.store_activities.StoreHomeActivity;
import com.huitong.deal.store.store_adapter.HomePageFloorAdapter;
import com.huitong.deal.widgets.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
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

public class StoreHomeHomeFragment extends BaseFragment implements View.OnClickListener {

    public static StoreHomeHomeFragment newInstance(String content){
        StoreHomeHomeFragment instance = new StoreHomeHomeFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        instance.setArguments(args);
        return instance;
    }

    private Banner mBanner;
    private TextView mTextBtn1;
    private TextView mTextBtn2;
    private TextView mTextBtn3;
    private TextView mTextBtn4;
    private TextView mTextBtn5;
    private TextView mTextBtn6;
    private TextView mTextBtn7;
    private TextView mTextBtn8;
    private RecyclerView mRecycler;
    private HomePageFloorAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.store_fragment_home_homepage, container, false);

        mBanner= mView.findViewById(R.id.homepage_banner);
        mTextBtn1= mView.findViewById(R.id.homepage_btn1);
        mTextBtn1.setOnClickListener(this);
        mTextBtn2= mView.findViewById(R.id.homepage_btn2);
        mTextBtn2.setOnClickListener(this);
        mTextBtn3= mView.findViewById(R.id.homepage_btn3);
        mTextBtn3.setOnClickListener(this);
        mTextBtn4= mView.findViewById(R.id.homepage_btn4);
        mTextBtn4.setOnClickListener(this);
        mTextBtn5= mView.findViewById(R.id.homepage_btn5);
        mTextBtn5.setOnClickListener(this);
        mTextBtn6= mView.findViewById(R.id.homepage_btn6);
        mTextBtn6.setOnClickListener(this);
        mTextBtn7= mView.findViewById(R.id.homepage_btn7);
        mTextBtn7.setOnClickListener(this);
        mTextBtn8= mView.findViewById(R.id.homepage_btn8);
        mTextBtn8.setOnClickListener(this);
        mRecycler= mView.findViewById(R.id.homepage_recycler);
        mRecycler.setFocusable(false);
        mRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        mAdapter= new HomePageFloorAdapter(R.layout.store_layout_floor);
        mRecycler.setAdapter(mAdapter);

        addNetWork(Network.getInstance().getStoreHomeData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<HomePageEntity>handleResult())
                .subscribe(new Consumer<HomePageEntity>() {
                    @Override
                    public void accept(HomePageEntity homePageEntity) throws Exception {
                        dismissDialog();
                        ArrayList<String> mBannerImageList= new ArrayList<>();
                        final ArrayList<String> mBannerGoodIdList= new ArrayList<>();
                        for (HomePageBannerEntity entity : homePageEntity.getBannerlist()){
                            mBannerImageList.add(entity.getImgUrl());
                            mBannerGoodIdList.add(entity.getGood_id());
                        }
                        mBanner.setImageLoader(new GlideImageLoader());
                        mBanner.setImages(mBannerImageList);
                        mBanner.setOnBannerListener(new OnBannerListener() {
                            @Override
                            public void OnBannerClick(int position) {
                                Intent mIntent= new Intent(getRealContext(), StoreDetailActivity.class);
                                mIntent.putExtra(StoreDetailActivity.GOOD_ID, mBannerGoodIdList.get(position));
                                startActivity(mIntent);
                            }
                        });
                        //设置banner样式
                        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                        //设置指示器位置（当banner模式中有指示器时）
                        mBanner.setIndicatorGravity(BannerConfig.LEFT);
                        mBanner.setDelayTime(2000);
                        mBanner.start();

                        ArrayList<HomePageFloorEntity> floorList= new ArrayList<>();
                        for (HomePageFloorEntity entity : homePageEntity.getFloorlist()){
                            if (entity.getGoodslist()!= null && entity.getGoodslist().size()> 0){
                                floorList.add(entity);
                            }
                        }
                        mAdapter.addData(floorList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        HttpUtils.parseThrowableMsg(throwable);
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

        return mView;
    }

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在加载..");
    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.homepage_btn1:{
                ((StoreHomeActivity)getActivity()).gc_id= 56;
                ((StoreHomeActivity)getActivity()).mCommodityRbtn.setChecked(true);
                break;
            }
            case R.id.homepage_btn2:{
                ((StoreHomeActivity)getActivity()).gc_id= 59;
                ((StoreHomeActivity)getActivity()).mCommodityRbtn.setChecked(true);
                break;
            }
            case R.id.homepage_btn3:{
                ((StoreHomeActivity)getActivity()).gc_id= 57;
                ((StoreHomeActivity)getActivity()).mCommodityRbtn.setChecked(true);
                break;
            }
            case R.id.homepage_btn4:{
                ((StoreHomeActivity)getActivity()).gc_id= 58;
                ((StoreHomeActivity)getActivity()).mCommodityRbtn.setChecked(true);
                break;
            }
            case R.id.homepage_btn5:{
                ((StoreHomeActivity)getActivity()).gc_id= 62;
                ((StoreHomeActivity)getActivity()).mCommodityRbtn.setChecked(true);
                break;
            }
            case R.id.homepage_btn6:{
                ((StoreHomeActivity)getActivity()).gc_id= 63;
                ((StoreHomeActivity)getActivity()).mCommodityRbtn.setChecked(true);
                break;
            }
            case R.id.homepage_btn7:{
                ((StoreHomeActivity)getActivity()).gc_id= 64;
                ((StoreHomeActivity)getActivity()).mCommodityRbtn.setChecked(true);
                break;
            }
            case R.id.homepage_btn8:{
                ((StoreHomeActivity)getActivity()).gc_id= 65;
                ((StoreHomeActivity)getActivity()).mCommodityRbtn.setChecked(true);
                break;
            }
        }
    }
}
