package com.huitong.deal.activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.CommodityDetailEntity;
import com.huitong.deal.beans.CommodityListEntity;
import com.huitong.deal.beans.DealTableEntity;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.LeverageEntity;
import com.huitong.deal.fragments.ForgetPasswordFragment;
import com.huitong.deal.fragments.KLineChartFragment;
import com.huitong.deal.fragments.LoginWithPasswordFragment;
import com.huitong.deal.fragments.LoginWithVerificationFragment;
import com.huitong.deal.fragments.SignInFragment;
import com.huitong.deal.fragments.SignInSuccessFragment;
import com.huitong.deal.fragments.TimeChartFragment;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseActivity;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/12.
 */

public class MarketDetailActivity extends BaseActivity {

    private final int FRAGMENT_TAG_TIME= 0;
    private final int FRAGMENT_TAG_5_K= 1;
    private final int FRAGMENT_TAG_30_K= 2;
    private final int FRAGMENT_TAG_60_K= 3;
    private final int FRAGMENT_TAG_DAY_K= 4;

    private FragmentManager manager;

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mStatusTv;

    private TextView mPriceTv;
    private TextView mFloatTv;
    private TextView mNumberTv;

    private TextView mHighTv;
    private TextView mLowTv;
    private TextView mTodayTv;
    private TextView mYesterdayTv;

    private CommonTabLayout mTabLayout;
    private FrameLayout mChartFly;

    private Button mRenGouBtn;
    private Button mHuiGouBtn;

    private String stockid;
    private String stock_name;
    private String stock_code;

    private ArrayList<LeverageEntity> mLeverageList= new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_detail);
        stockid= getIntent().getStringExtra("id");
        stock_name= getIntent().getStringExtra("stock_name");
        stock_code= getIntent().getStringExtra("stock_code");
        if (stockid== null || stockid.length()< 1){
            showShortToast("获取产品失败");
            return;
        }
        if (stock_code== null || stock_code.length()< 1){
            showShortToast("获取产品杠杆失败");
            return;
        }

        mTabEntities.add(new DealTableEntity("分时", 0, 0));
        mTabEntities.add(new DealTableEntity("5分", 0, 0));
        mTabEntities.add(new DealTableEntity("30分", 0, 0));
        mTabEntities.add(new DealTableEntity("60分", 0, 0));
        mTabEntities.add(new DealTableEntity("日K", 0, 0));

        initUI();

        final String token= MyApplication.getInstance().getToken();
        loadLeverageData(token, stock_code);

        if (token!= null && token.length()> 0){
            addNetWork(
                    Observable.interval(1, TimeUnit.SECONDS)
                            .flatMap(new Function<Long, ObservableSource<HttpResult<CommodityDetailEntity>>>() {
                                @Override
                                public ObservableSource<HttpResult<CommodityDetailEntity>> apply(Long aLong) throws Exception {
                                    return Network.getInstance().getCommodityDetail(token, stockid);
                                }
                            })
                            .filter(new Predicate<HttpResult<CommodityDetailEntity>>() {
                                @Override
                                public boolean test(HttpResult<CommodityDetailEntity> commodityDetailEntityHttpResult) throws Exception {
                                    if ("success".equals(commodityDetailEntityHttpResult.getStatus())){
                                        return true;
                                    }
                                    return false;
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<HttpResult<CommodityDetailEntity>>() {
                                @Override
                                public void accept(HttpResult<CommodityDetailEntity> commodityDetailEntityHttpResult) throws Exception {
                                    if (commodityDetailEntityHttpResult.getData()!= null){
                                        refreshUI(commodityDetailEntityHttpResult.getData());
                                    }
                                }
                            }));
        }


        changeChart(0);

    }

    /**
     * 加载杠杆数据
     */
    private void loadLeverageData(String appToken, String stock_code) {
        if (appToken!= null && appToken.length()> 0){
            addNetWork(Network.getInstance().getLeverageList(appToken, stock_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<HttpResult<ArrayList<LeverageEntity>>>() {
                        @Override
                        public void accept(HttpResult<ArrayList<LeverageEntity>> arrayListHttpResult) throws Exception {
                            if ("error".equals(arrayListHttpResult.getStatus())){
                                showShortToast(arrayListHttpResult.getDescription());
                            }else if ("success".equals(arrayListHttpResult.getStatus())){
                                if (arrayListHttpResult.getData().size()> 0)
                                    mLeverageList= arrayListHttpResult.getData();
                            }
                        }
                    }));
        }
    }

    private void initUI() {
        manager= getSupportFragmentManager();

        mBackIv = (ImageView) findViewById(R.id.toolbar_back);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv = (TextView) findViewById(R.id.toolbar_title);
        if (stock_name!= null && stock_name.length()> 0){
            mTitleTv.setText(stock_name);
        }
        mStatusTv = (TextView) findViewById(R.id.toolbar_icon);

        mPriceTv = (TextView) findViewById(R.id.market_detail_price);
        mFloatTv = (TextView) findViewById(R.id.market_detail_float);
        mNumberTv = (TextView) findViewById(R.id.market_detail_number);

        mHighTv = (TextView) findViewById(R.id.market_detail_high);
        mLowTv = (TextView) findViewById(R.id.market_detail_low);
        mTodayTv = (TextView) findViewById(R.id.market_detail_today);
        mYesterdayTv = (TextView) findViewById(R.id.market_detail_yesterday);

        mTabLayout = (CommonTabLayout) findViewById(R.id.market_detail_tab);
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                changeChart(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mChartFly = (FrameLayout) findViewById(R.id.market_detail_framelayout);

        mRenGouBtn= (Button) findViewById(R.id.market_detail_btn_rengou);
        mRenGouBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShortToast("认购");
            }
        });
        mHuiGouBtn= (Button) findViewById(R.id.market_detail_btn_huigou);
        mHuiGouBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShortToast("回购");
            }
        });
    }

    private void refreshUI(CommodityDetailEntity entity){
//        mTitleTv.setText(entity.getStock_name());
        mStatusTv.setText(entity.getState_name());
        if (entity.getStock_state()== 1){
            mStatusTv.setBackgroundColor(Color.rgb(255, 152, 0));
        }else {
            mStatusTv.setBackgroundColor(Color.rgb(204, 204, 204));
        }

        mPriceTv.setText(String.valueOf(entity.getNow_price()));
        mFloatTv.setText(String.valueOf(entity.getFloat_rate()));
        mNumberTv.setText(entity.getCur_date() + " " + entity.getCur_time());

        mHighTv.setText(String.valueOf(entity.getHighest()));
        mLowTv.setText(String.valueOf(entity.getLowest()));
        mTodayTv.setText(String.valueOf(entity.getOpen_price()));
        mYesterdayTv.setText(String.valueOf(entity.getClose_price()));

        if (entity.getStock_state()== 0 || entity.getTrade_state()== 0){
            mRenGouBtn.setBackgroundColor(Color.rgb(204, 204, 204));
            mRenGouBtn.setClickable(false);
            mHuiGouBtn.setBackgroundColor(Color.rgb(204, 204, 204));
            mHuiGouBtn.setClickable(false);
        }else {
            mRenGouBtn.setBackgroundResource(R.drawable.button_background_orange_selector);
            mRenGouBtn.setClickable(true);
            mHuiGouBtn.setBackgroundResource(R.drawable.button_background_green_selector);
            mHuiGouBtn.setClickable(true);
        }
    }

    public void changeChart(int chartTag){
        switch (chartTag){
            case FRAGMENT_TAG_TIME:{//分时图
                manager.beginTransaction()
                        .replace(R.id.market_detail_framelayout, TimeChartFragment.newInstance(""))
                        .commit();
                break;
            }
            case FRAGMENT_TAG_5_K:{//5分钟K线图
                manager.beginTransaction()
                        .replace(R.id.market_detail_framelayout, KLineChartFragment.newInstance(KLineChartFragment.KLINE_TAG_5_MINUTE))
                        .commit();
                break;
            }
            case FRAGMENT_TAG_30_K:{//30分钟K线图
                manager.beginTransaction()
                        .replace(R.id.market_detail_framelayout, KLineChartFragment.newInstance(KLineChartFragment.KLINE_TAG_30_MINUTE))
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case FRAGMENT_TAG_60_K:{//60分钟K线图
                manager.beginTransaction()
                        .replace(R.id.market_detail_framelayout, KLineChartFragment.newInstance(KLineChartFragment.KLINE_TAG_60_MINUTE))
                        .commit();
                break;
            }
            case FRAGMENT_TAG_DAY_K:{//1天K线图
                manager.beginTransaction()
                        .replace(R.id.market_detail_framelayout, KLineChartFragment.newInstance(KLineChartFragment.KLINE_TAG_1_DAY))
                        .addToBackStack(null)
                        .commit();
                break;
            }
            default:
                showShortToast("页面切换失败");
                break;
        }
    }

}
