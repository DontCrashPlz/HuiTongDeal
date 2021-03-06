package com.huitong.deal.activities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.CommitOrderEntity;
import com.huitong.deal.beans.CommodityDetailEntity;
import com.huitong.deal.beans.CommodityListEntity;
import com.huitong.deal.beans.DealTableEntity;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.LeverageEntity;
import com.huitong.deal.beans.UserInfoDataEntity;
import com.huitong.deal.fragments.ForgetPasswordFragment;
import com.huitong.deal.fragments.KLineChartFragment;
import com.huitong.deal.fragments.LoginWithPasswordFragment;
import com.huitong.deal.fragments.LoginWithVerificationFragment;
import com.huitong.deal.fragments.SignInFragment;
import com.huitong.deal.fragments.SignInSuccessFragment;
import com.huitong.deal.fragments.TimeChartFragment;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.MyPayPsdInputView;

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
@Deprecated
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
//    private FrameLayout mChartFly;

    private Button mRenGouBtn;
    private Button mHuiGouBtn;

    private String stockid;
    private String stock_name;
    private String stock_code;

    private ArrayList<LeverageEntity> mLeverageList= new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private String appToken;

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
            showShortToast("获取产品编码失败");
            return;
        }

        mTabEntities.add(new DealTableEntity("分时", 0, 0));
        mTabEntities.add(new DealTableEntity("5分", 0, 0));
        mTabEntities.add(new DealTableEntity("30分", 0, 0));
        mTabEntities.add(new DealTableEntity("60分", 0, 0));
        mTabEntities.add(new DealTableEntity("日K", 0, 0));

        initUI();

        appToken= MyApplication.getInstance().getToken();
        loadLeverageData(appToken, stock_code);

        if (appToken!= null && appToken.length()> 0){
            addNetWork(
                    Observable.interval(1, TimeUnit.SECONDS)
                            .flatMap(new Function<Long, ObservableSource<HttpResult<CommodityDetailEntity>>>() {
                                @Override
                                public ObservableSource<HttpResult<CommodityDetailEntity>> apply(Long aLong) throws Exception {
                                    return Network.getInstance().getCommodityDetail(appToken, stockid);
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
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    LogUtil.d("throwable", throwable.toString());
                                    showShortToast("网络请求失败");
                                }
                            }));
        }

        changeChart(0);

    }

    @Override
    public void initProgressDialog() {

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
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            LogUtil.d("throwable", throwable.toString());
                            showShortToast("网络请求失败");
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

//        mChartFly = (FrameLayout) findViewById(R.id.market_detail_framelayout);

        mRenGouBtn= (Button) findViewById(R.id.market_detail_btn_rengou);
        mRenGouBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showXiaDanDialog(getRealContext(), 0);
            }
        });
        mHuiGouBtn= (Button) findViewById(R.id.market_detail_btn_huigou);
        mHuiGouBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showXiaDanDialog(getRealContext(), 1);
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

        if (xiaDanDialog!= null && xiaDanDialog.isShowing()){
            dialogCurPrice.setText(String.valueOf(entity.getNow_price()));
            nowPrice= String.valueOf(entity.getNow_price());
        }
    }

    public void changeChart(int chartTag){
        switch (chartTag){
            case FRAGMENT_TAG_TIME:{//分时图
                manager.beginTransaction()
                        .replace(R.id.market_detail_framelayout, TimeChartFragment.newInstance(stock_code))
                        .commit();
                break;
            }
            case FRAGMENT_TAG_5_K:{//5分钟K线图
                manager.beginTransaction()
                        .replace(R.id.market_detail_framelayout, KLineChartFragment.newInstance(stock_code, KLineChartFragment.KLINE_TAG_5_MINUTE))
                        .commit();
                break;
            }
            case FRAGMENT_TAG_30_K:{//30分钟K线图
                manager.beginTransaction()
                        .replace(R.id.market_detail_framelayout, KLineChartFragment.newInstance(stock_code, KLineChartFragment.KLINE_TAG_30_MINUTE))
                        .commit();
                break;
            }
            case FRAGMENT_TAG_60_K:{//60分钟K线图
                manager.beginTransaction()
                        .replace(R.id.market_detail_framelayout, KLineChartFragment.newInstance(stock_code, KLineChartFragment.KLINE_TAG_60_MINUTE))
                        .commit();
                break;
            }
            case FRAGMENT_TAG_DAY_K:{//1天K线图
                manager.beginTransaction()
                        .replace(R.id.market_detail_framelayout, KLineChartFragment.newInstance(stock_code, KLineChartFragment.KLINE_TAG_1_DAY))
                        .commit();
                break;
            }
            default:
                showShortToast("页面切换失败");
                break;
        }
    }

    /**************************下单Dialog相关*******************************/
    private Dialog xiaDanDialog;
//    private ImageView dialogCancel;
    //private CommonTabLayout dialogTabLayout;
    private TextView dialogStockName;
    //private TextView dialogStockStatus;
    private TextView dialogCurPrice;
    private RadioButton dialogRbtn_100;
    private RadioButton dialogRbtn_50;
    private RadioButton dialogRbtn_1;
    private TextView dialogBuyCount;
    private TextView dialogMaxCount;
    private EditText dialogEditText;
    private TextView dialogBuyPrice;
    private TextView dialogServerPrice;
    private Button dialogButton;

    private String nowPrice;
    private LeverageEntity leverageEntity;
    private int buyCount;
    private int buyType;
    private int maxCount;

    private ArrayList<CustomTabEntity> tabEntities;

    private void showXiaDanDialog(Context context, int currentTab){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_xiadan_dialog, null);
        // 设置style 控制默认dialog带来的边距问题
        xiaDanDialog= new Dialog(context, R.style.custom_dialog_no_titlebar);
        xiaDanDialog.setContentView(view);
        xiaDanDialog.show();

//        dialogCancel=  view.findViewById(R.id.tixian_dialog_cancel);
//        dialogCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                xiaDanDialog.dismiss();
//            }
//        });

        //dialogTabLayout= view.findViewById(R.id.tixian_dialog_tab);
        tabEntities= new ArrayList<>();
        tabEntities.add(new DealTableEntity("认购", 0, 0));
        tabEntities.add(new DealTableEntity("回购", 0, 0));
//        dialogTabLayout.setTabData(tabEntities);
//        dialogTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelect(int position) {
//                if (position== 0){
//                    buyType= 2;
//                }else if (position== 1){
//                    buyType= 1;
//                }
//            }
//
//            @Override
//            public void onTabReselect(int position) {
//
//            }
//        });
//        dialogTabLayout.setCurrentTab(currentTab);
//        if (currentTab== 0){
//            buyType= 2;
//        }else if (currentTab== 1){
//            buyType= 1;
//        }

        dialogStockName= view.findViewById(R.id.tixian_dialog_name);
        dialogStockName.setText(stock_name);

        //dialogStockStatus= view.findViewById(R.id.tixian_dialog_status);

        dialogCurPrice= view.findViewById(R.id.tixian_dialog_price);

        dialogRbtn_100= view.findViewById(R.id.tixian_dialog_rbtn_100);
        dialogRbtn_50= view.findViewById(R.id.tixian_dialog_rbtn_50);
        dialogRbtn_1= view.findViewById(R.id.tixian_dialog_rbtn_1);
        dealLeverage();

        dialogBuyCount= view.findViewById(R.id.tixian_dialog_buycount);
        dialogBuyCount.setText(String.format(getString(R.string.xiadan_dialog_buycount), "0"));
        dialogMaxCount= view.findViewById(R.id.tixian_dialog_maxcount);
        dialogMaxCount.setText(String.format(getString(R.string.xiadan_dialog_maxcount), "0", "0"));
//        dialogEditText= view.findViewById(R.id.tixian_dialog_edittext);
//        dialogEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.toString().trim()== null || s.toString().trim().length()< 1){
//                    return;
//                }
//                int value= Integer.parseInt(s.toString().trim());
//                if (value> maxCount) {
//                    value= maxCount;
//                    dialogEditText.setText(String.valueOf(value));
//                }
//                if (value== 0){
//                    return;
//                }
//                dialogBuyCount.setText(
//                        String.format(
//                                getString(R.string.xiadan_dialog_buycount),
//                                String.valueOf(value)));
//                float zongJia= leverageEntity.getPrice()*value;
//                float shouXuFei= zongJia*leverageEntity.getFeeRate();
//                dialogBuyPrice.setText(String.valueOf(zongJia));
//                dialogServerPrice.setText(String.format(
//                        getString(R.string.xiadan_dialog_shouxufei),
//                        String.valueOf(shouXuFei)));
//
//                buyCount= value;
//            }
//        });

        dialogBuyPrice= view.findViewById(R.id.tixian_dialog_buyprice);
        dialogServerPrice= view.findViewById(R.id.tixian_dialog_serverprice);
        dialogButton= view.findViewById(R.id.tixian_dialog_xiadan);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xiaDan();
            }
        });

        // 设置相关位置，一定要在 show()之后
        Window window = xiaDanDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

    /**
     * 提交下单请求
     */
    private void xiaDan(){
        if (appToken!= null && appToken.length()> 0){

            if (leverageEntity== null){
                showShortToast("请选择杠杆");
                return;
            }
            if (buyCount== 0){
                showShortToast("请填写手数");
                return;
            }

            addNetWork(Network.getInstance().commitOrder(
                    appToken,
                    stockid,
                    stock_code,
                    nowPrice,
                    String.valueOf(leverageEntity.getPrice()),
                    String.valueOf(buyCount),
                    String.valueOf(leverageEntity.getFeeRate()),
                    String.valueOf(leverageEntity.getLeverage()),
                    String.valueOf(buyType))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<HttpResult<CommitOrderEntity>>() {
                        @Override
                        public void accept(HttpResult<CommitOrderEntity> commitOrderEntityHttpResult) throws Exception {
                            if ("error".equals(commitOrderEntityHttpResult.getStatus())){
                                showShortToast(commitOrderEntityHttpResult.getDescription());
                            }else if ("success".equals(commitOrderEntityHttpResult.getStatus())){
                                showShortToast("下单成功");
                                if (xiaDanDialog!= null && xiaDanDialog.isShowing()){
                                    xiaDanDialog.dismiss();
                                }
                                addNetWork(MyApplication.getInstance().refreshUser(new IAsyncLoadListener<UserInfoDataEntity>() {
                                    @Override
                                    public void onSuccess(UserInfoDataEntity userInfoDataEntity) {

                                    }

                                    @Override
                                    public void onFailure(String msg) {
                                        showShortToast(msg);
                                    }
                                }));
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            LogUtil.d("throwable", throwable.toString());
                            showShortToast("网络请求失败");
                        }
                    }));
        }
    }

    /**
     * 处理杠杆列表
     */
    private void dealLeverage(){
        if (mLeverageList== null || mLeverageList.size()< 1){
            showShortToast("杠杆列表无效");
            if (xiaDanDialog!= null && xiaDanDialog.isShowing()){
                xiaDanDialog.dismiss();
                return;
            }
        }
        dialogRbtn_100.setClickable(false);
        dialogRbtn_100.setVisibility(View.INVISIBLE);
        dialogRbtn_50.setClickable(false);
        dialogRbtn_50.setVisibility(View.INVISIBLE);
        dialogRbtn_1.setClickable(false);
        dialogRbtn_1.setVisibility(View.INVISIBLE);
        for (final LeverageEntity entity : mLeverageList){
            if (entity.getLeverage()== 100){
                dialogRbtn_100.setClickable(true);
                dialogRbtn_100.setVisibility(View.VISIBLE);
                dialogRbtn_100.setText(entity.getLeverageName());
                dialogRbtn_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            leverageEntity= entity;
                            computeMaxCount();
                            dialogMaxCount.setText(
                                    String.format(
                                            getString(R.string.xiadan_dialog_maxcount),
                                            String.valueOf(leverageEntity.getPrice()),
                                            String.valueOf(maxCount)));
                        }
                    }
                });
            }else if(entity.getLeverage()== 50){
                dialogRbtn_50.setClickable(true);
                dialogRbtn_50.setVisibility(View.VISIBLE);
                dialogRbtn_50.setText(entity.getLeverageName());
                dialogRbtn_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            leverageEntity= entity;
                            computeMaxCount();
                            dialogMaxCount.setText(
                                    String.format(
                                            getString(R.string.xiadan_dialog_maxcount),
                                            String.valueOf(leverageEntity.getPrice()),
                                            String.valueOf(maxCount)));
                        }
                    }
                });
            }else if(entity.getLeverage()== 1){
                dialogRbtn_1.setClickable(true);
                dialogRbtn_1.setVisibility(View.VISIBLE);
                dialogRbtn_1.setText(entity.getLeverageName());
                dialogRbtn_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            leverageEntity= entity;
                            computeMaxCount();
                            dialogMaxCount.setText(
                                    String.format(
                                            getString(R.string.xiadan_dialog_maxcount),
                                            String.valueOf(leverageEntity.getPrice()),
                                            String.valueOf(maxCount)));
                        }
                    }
                });
            }
        }
    }

    private void computeMaxCount(){
        float balance= MyApplication.appUser.getUserinfo().getAvailablebalance();
        float danJia= leverageEntity.getPrice();
        float fuWuFeiLv= leverageEntity.getFeeRate();
        float danBiZongJia= danJia + danJia*fuWuFeiLv;
        maxCount= (int) (balance/danBiZongJia);
    }

}
