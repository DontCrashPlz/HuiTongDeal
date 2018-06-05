package com.huitong.deal.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatSeekBar;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.CommitOrderEntity;
import com.huitong.deal.beans.CommodityDetailEntity;
import com.huitong.deal.beans.DealTableEntity;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.LeverageEntity;
import com.huitong.deal.beans.UserInfoDataEntity;
import com.huitong.deal.fragments.KLineChartFragment;
import com.huitong.deal.fragments.TimeChartFragment;
import com.huitong.deal.https.Network;
import com.huitong.deal.widgets.ClearableEditText;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.interfaces.IAsyncLoadListener;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.Tools;
import com.zheng.zchlibrary.widgets.CustomTabLayout.Tool;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/12.
 */

public class MarketDetailActivity2 extends BaseActivity {

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

    private WebView mWebView;

    private Button mRenGouBtn;
    private Button mHuiGouBtn;

    private String stockid;
    private String stock_name;
    private String stock_code;

    private ArrayList<LeverageEntity> mLeverageList= new ArrayList<>();

    private String appToken;

    private ProgressDialog mInitDialog;

    private float mLastPrice;
    private float mNowPrice;
    private float mFloatPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_detail2);

        mInitDialog= new ProgressDialog(getRealContext());
        mInitDialog.setLabel("加载中..");
        if (mInitDialog!= null && !mInitDialog.isShowing()){
            mInitDialog.show();
        }

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

        initUI();

        appToken= MyApplication.getInstance().getToken();
        loadLeverageData(appToken, stock_code);

        if (appToken!= null && appToken.length()> 0){
            addNetWork(
                    Observable.interval(2, TimeUnit.SECONDS)
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
                                    if (mInitDialog!= null && mInitDialog.isShowing()){
                                        mInitDialog.dismiss();
                                    }
                                    if (commodityDetailEntityHttpResult.getData()!= null){
                                        refreshUI(commodityDetailEntityHttpResult.getData());
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    if (mInitDialog!= null && mInitDialog.isShowing()){
                                        mInitDialog.dismiss();
                                    }
                                    LogUtil.e("throwable", throwable.toString());
                                    showShortToast("网络请求失败");
                                }
                            }));
        }

    }

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在提交订单..");
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
                            LogUtil.e("throwable", throwable.toString());
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
        mStatusTv.setText(stock_code);

        mPriceTv = (TextView) findViewById(R.id.market_detail_price);
        mFloatTv = (TextView) findViewById(R.id.market_detail_float);
        mNumberTv = (TextView) findViewById(R.id.market_detail_number);

        mHighTv = (TextView) findViewById(R.id.market_detail_high);
        mLowTv = (TextView) findViewById(R.id.market_detail_low);
        mTodayTv = (TextView) findViewById(R.id.market_detail_today);
        mYesterdayTv = (TextView) findViewById(R.id.market_detail_yesterday);

        mWebView= (WebView) findViewById(R.id.market_detail_webview);

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

        WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        // 设置文本编码
        webSetting.setDefaultTextEncodingName("UTF-8");
		/*
		 * LayoutAlgorithm是一个枚举用来控制页面的布局，有三个类型：
		 * 1.NARROW_COLUMNS：可能的话使所有列的宽度不超过屏幕宽度
		 * 2.NORMAL：正常显示不做任何渲染
		 * 3.SINGLE_COLUMN：把所有内容放大webview等宽的一列中
		 * 用SINGLE_COLUMN类型可以设置页面居中显示，
		 * 页面可以放大缩小，但这种方法不怎么好，
		 * 有时候会让你的页面布局走样而且我测了一下，只能显示中间那一块，超出屏幕的部分都不能显示。
		 */
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setSupportZoom(false);// 用于设置webview放大
        webSetting.setBuiltInZoomControls(false);
        String lineUrl= "http://47.92.28.185/api/stm/trade/echart/getTimeLine?stockCode="+ stock_code;
        mWebView.loadUrl(lineUrl);

    }

    private void refreshUI(CommodityDetailEntity entity){

        mNowPrice= entity.getNow_price();
        mFloatPrice= mNowPrice - mLastPrice;

        mPriceTv.setText(Tools.formatFloat(mNowPrice));
        if (xiaDanDialog!= null && xiaDanDialog.isShowing()){
            LogUtil.e("xiaDanDialog isShowing", "" + xiaDanDialog.isShowing());
            dialogCurPrice.setText(Tools.formatFloat(mNowPrice));
            nowPrice= String.valueOf(entity.getNow_price());
        }
        if (mFloatPrice< 0){
            mPriceTv.setTextColor(Color.rgb(0, 246, 1));
            if (xiaDanDialog!= null && xiaDanDialog.isShowing()){
                dialogCurPrice.setTextColor(Color.rgb(0, 246, 1));
            }
        }else {
            mPriceTv.setTextColor(Color.rgb(255, 63, 0));
            if (xiaDanDialog!= null && xiaDanDialog.isShowing()){
                dialogCurPrice.setTextColor(Color.rgb(255, 63, 0));
            }
        }

        mNumberTv.setText(entity.getCur_date() + " " + entity.getCur_time());

        if (entity.getFloat_rate()< 0){
            mFloatTv.setText("↓" + Tools.formatFloat(entity.getFloat_rate()) + "%");
            //mPriceTv.setTextColor(Color.rgb(0, 246, 1));
            mFloatTv.setTextColor(Color.rgb(0, 246, 1));
            if (xiaDanDialog!= null && xiaDanDialog.isShowing()){
                dialogFloat.setText("↓");
                dialogFloat.setTextColor(Color.rgb(0, 246, 1));
            }
        }else {
            mFloatTv.setText("↑" + Tools.formatFloat(entity.getFloat_rate()) + "%");
            //mPriceTv.setTextColor(Color.rgb(255, 63, 0));
            mFloatTv.setTextColor(Color.rgb(255, 63, 0));
            if (xiaDanDialog!= null && xiaDanDialog.isShowing()){
                dialogFloat.setText("↑");
                dialogFloat.setTextColor(Color.rgb(255, 63, 0));
            }
        }

        mHighTv.setText(String.format(getString(R.string.market_detail_high), Tools.formatFloat(entity.getHighest())));
        mLowTv.setText(String.format(getString(R.string.market_detail_low), Tools.formatFloat(entity.getLowest())));
        mTodayTv.setText(String.format(getString(R.string.market_detail_today), Tools.formatFloat(entity.getOpen_price())));
        mYesterdayTv.setText(String.format(getString(R.string.market_detail_yesterday), Tools.formatFloat(entity.getClose_price())));

        if (entity.getStock_state()== 0 || entity.getTrade_state()== 0){
            mStatusTv.setText("未开盘");
            mStatusTv.setBackgroundColor(Color.rgb(204, 204, 204));
            mRenGouBtn.setBackgroundColor(Color.rgb(204, 204, 204));
            mRenGouBtn.setClickable(false);
            mHuiGouBtn.setBackgroundColor(Color.rgb(204, 204, 204));
            mHuiGouBtn.setClickable(false);
        }else {
            mStatusTv.setText("交易中");
            mStatusTv.setBackgroundColor(Color.rgb(255, 152, 0));
            mRenGouBtn.setBackgroundResource(R.drawable.button_background_orange_selector);
            mRenGouBtn.setClickable(true);
            mHuiGouBtn.setBackgroundResource(R.drawable.button_background_green_selector);
            mHuiGouBtn.setClickable(true);
        }


    }

    /**************************下单Dialog相关*******************************/
    private Dialog xiaDanDialog;//下单弹窗
//    private ImageView dialogCancel;
    //private CommonTabLayout dialogTabLayout;//选择认购回购
    private RadioButton mRenGouRbtn;
    private RadioButton mHuiGouRbtn;
    private TextView dialogStockName;//股票名称
    //private TextView dialogStockStatus;//股票状态
    private TextView dialogCurPrice;//股票现价
    private TextView dialogFloat;//浮动状态
    private RadioButton dialogRbtn_100;//100杠杆
    private RadioButton dialogRbtn_50;//50杠杆
    private RadioButton dialogRbtn_1;//1杠杆
    private TextView dialogBuyCount;//购买手数
    private TextView dialogMaxCount;//最多购买手数
//    private EditText dialogEditText;
    private TextView dialogMinCount;
    private AppCompatSeekBar dialogSeekBar;//滑动条选择购买手数
    private Button dialogSubtractBtn;//减购买手数按钮
    private ClearableEditText dialogBuyCountEt;//购买手数输入框
    private Button dialogPlusBtn;//加购买手数按钮
    private TextView dialogZhiSunTv;//止损率
    private TextView dialogZhiYingTv;//止盈率
    private TextView dialogBuyPrice;//购买金额
    private TextView dialogServerPrice;//服务费
    private Button dialogButton;//下单按钮

    private String nowPrice;//股票现价
    private LeverageEntity leverageEntity;//现在选择的杠杆
    private int buyCount;//购买手数
    private int buyType;//购买类型（认购回购）
    private int minCount;//最小购买数量
    private int maxCount;//最大购买数量

    //private ArrayList<CustomTabEntity> tabEntities;

    /**
     * 弹出下单弹窗
     * @param context
     * @param currentTab
     */
    private void showXiaDanDialog(Context context, int currentTab){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_xiadan_dialog, null);
        // 设置style 控制默认dialog带来的边距问题
        xiaDanDialog= new Dialog(context, R.style.custom_dialog_no_titlebar);
        xiaDanDialog.setContentView(view);
        xiaDanDialog.show();

        mRenGouRbtn= view.findViewById(R.id.tixian_dialog_tab_rengou);
        mRenGouRbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    buyType= 2;
                    dialogButton.setBackgroundColor(Color.rgb(0, 159, 233));
                }
            }
        });
        mHuiGouRbtn= view.findViewById(R.id.tixian_dialog_tab_huigou);
        mHuiGouRbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    buyType= 1;
                    dialogButton.setBackgroundColor(Color.rgb(240, 173, 78));
                }
            }
        });
//        dialogTabLayout= view.findViewById(R.id.tixian_dialog_tab);
//        tabEntities= new ArrayList<>();
//        tabEntities.add(new DealTableEntity("认购", 0, 0));
//        tabEntities.add(new DealTableEntity("回购", 0, 0));
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
//            @Override
//            public void onTabReselect(int position) {
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

//        dialogStockStatus= view.findViewById(R.id.tixian_dialog_status);
//        dialogStockStatus.setText(stock_code);

        dialogCurPrice= view.findViewById(R.id.tixian_dialog_price);
        dialogFloat= view.findViewById(R.id.tixian_dialog_float);
        dialogRbtn_100= view.findViewById(R.id.tixian_dialog_rbtn_100);
        dialogRbtn_50= view.findViewById(R.id.tixian_dialog_rbtn_50);
        dialogRbtn_1= view.findViewById(R.id.tixian_dialog_rbtn_1);
        dialogBuyCount= view.findViewById(R.id.tixian_dialog_buycount);
        dialogBuyCount.setText(String.format(getString(R.string.xiadan_dialog_buycount), "0"));
        dialogMaxCount= view.findViewById(R.id.tixian_dialog_maxcount);
        dialogMaxCount.setText(String.format(getString(R.string.xiadan_dialog_maxcount), "0", "0"));
        dialogMinCount= view.findViewById(R.id.tixian_dialog_tv_mincount);
        dialogSeekBar= view.findViewById(R.id.tixian_dialog_seekbar);
        dialogSubtractBtn= view.findViewById(R.id.tixian_dialog_btn_subtract);
        dialogBuyCountEt= view.findViewById(R.id.tixian_dialog_et_buycount);
        dialogPlusBtn= view.findViewById(R.id.tixian_dialog_btn_plus);
        dialogZhiSunTv= view.findViewById(R.id.tixian_dialog_tv_zhisunxian);
        dialogZhiYingTv= view.findViewById(R.id.tixian_dialog_tv_zhiyingxian);
        dialogBuyPrice= view.findViewById(R.id.tixian_dialog_buyprice);
        dialogServerPrice= view.findViewById(R.id.tixian_dialog_serverprice);
        dialogServerPrice.setText(String.format(getString(R.string.xiadan_dialog_shouxufei), "0"));
        dialogButton= view.findViewById(R.id.tixian_dialog_xiadan);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e("点击下单按钮时的购买手数", "" + buyCount);
                if (buyCount > maxCount){
                    showShortToast("当前余额不能购买" + buyCount + "手");
                    return;
                }
                if (buyCount < minCount){
                    showShortToast("当前杠杆最低手数为" + minCount);
                    return;
                }
                showConfirmDialog();
            }
        });
        if (currentTab== 0){
            mRenGouRbtn.setChecked(true);
        }else if (currentTab== 1){
            mHuiGouRbtn.setChecked(true);
        }

        dealLeverage();

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
                            dismissDialog();
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
                            dismissDialog();
                            LogUtil.d("throwable", throwable.toString());
                            showShortToast("网络请求失败");
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            dismissDialog();
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
                            minCount= 10;
                            computeMaxCount();
                            dialogMaxCount.setText(
                                    String.format(
                                            getString(R.string.xiadan_dialog_maxcount),
                                            String.valueOf(leverageEntity.getPrice()),
                                            String.valueOf(maxCount)));
                            dialogZhiSunTv.setText(
                                    String.format(
                                            getString(R.string.xiadan_dialog_zhisunxian),
                                            leverageEntity.getLoseRate() * 100 + "%"));
                            dialogZhiYingTv.setText(
                                    String.format(
                                            getString(R.string.xiadan_dialog_zhiyingxian),
                                            leverageEntity.getGainRate() * 100 + "%"));
                            resetSeekBar(100);
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
                            minCount= 10;
                            computeMaxCount();
                            dialogMaxCount.setText(
                                    String.format(
                                            getString(R.string.xiadan_dialog_maxcount),
                                            String.valueOf(leverageEntity.getPrice()),
                                            String.valueOf(maxCount)));
                            dialogZhiSunTv.setText(
                                    String.format(
                                            getString(R.string.xiadan_dialog_zhisunxian),
                                            leverageEntity.getLoseRate() * 100 + "%"));
                            dialogZhiYingTv.setText(
                                    String.format(
                                            getString(R.string.xiadan_dialog_zhiyingxian),
                                            leverageEntity.getGainRate() * 100 + "%"));
                            resetSeekBar(50);
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
                            minCount= 1;
                            computeMaxCount();
                            dialogMaxCount.setText(
                                    String.format(
                                            getString(R.string.xiadan_dialog_maxcount),
                                            String.valueOf(leverageEntity.getPrice()),
                                            String.valueOf(maxCount)));
                            dialogZhiSunTv.setText(
                                    String.format(
                                            getString(R.string.xiadan_dialog_zhisunxian),
                                            leverageEntity.getLoseRate() * 100 + "%"));
                            dialogZhiYingTv.setText(
                                    String.format(
                                            getString(R.string.xiadan_dialog_zhiyingxian),
                                            leverageEntity.getGainRate() * 100 + "%"));
                            resetSeekBar(1);
                        }
                    }
                });
            }
        }
        //默认选定第一个杠杆
        if (mLeverageList.get(0).getLeverage()== 100){
            dialogRbtn_100.setChecked(true);
        }else if (mLeverageList.get(0).getLeverage()== 50){
            dialogRbtn_50.setChecked(true);
        }else if (mLeverageList.get(0).getLeverage()== 1){
            dialogRbtn_1.setChecked(true);
        }
    }

    /**
     * 计算当前杠杆下的最大手数
     * 区间为0-100，因为最大手数为100
     */
    private void computeMaxCount(){
        float balance= MyApplication.appUser.getUserinfo().getAvailablebalance();
        float danJia= leverageEntity.getPrice();
        float fuWuFeiLv= leverageEntity.getFeeRate();
        float danBiZongJia= danJia + danJia*fuWuFeiLv;
        //最大购买手数为可买的手数和100中的最大值
        int value= (int) (balance/danBiZongJia);
        LogUtil.d("可购买的最大手数", ""+value);
        maxCount= Math.min(100, value);
//        if (maxCount> 100) maxCount= 100;//如果可买超过100手，最多可买100手
//
//        if (leverage== 100 || leverage== 50){//如果买不起最低限购量，最多可买0手
//            if (maxCount< 10) maxCount= 0;
//        }else if (leverage== 1){
//            if (maxCount< 1) maxCount= 0;
//        }
    }

    /**
     * 弹出确认下单弹窗
     */
    private void showConfirmDialog(){
        View view = LayoutInflater.from(getRealContext()).inflate(R.layout.layout_dialog_confirm_order, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(getRealContext(), R.style.custom_dialog_no_titlebar);
        dialog.setContentView(view);
        dialog.show();

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
                        xiaDan();
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
     * 重置seekbar
     * @param leverage
     */
    private void resetSeekBar(final int leverage){
        if (leverage== 100 || leverage== 50){//如果杠杆为100和50，最小手数为10
            dialogMinCount.setText("10");
            dialogSeekBar.setMax(90);
        }else if (leverage== 1){//如果杠杆为1，最小手数为1
            dialogMinCount.setText("1");
            dialogSeekBar.setMax(99);
        }
        dialogSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (leverage== 100 || leverage== 50){
                    dialogBuyCountEt.setText(String.valueOf(progress + 10));
                }else if (leverage== 1){
                    dialogBuyCountEt.setText(String.valueOf(progress + 1));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        dialogSubtractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框中现在的数字
                int value= Integer.parseInt(dialogBuyCountEt.getText().toString().trim());
                //如果输入框的数字大于最小限购值，将输入框中的数字减一
                if (value> minCount){
                    value -= 1;
                    dialogBuyCountEt.setText(String.valueOf(value));
                }
            }
        });
        dialogBuyCountEt.clearTextChangedListeners();
        dialogBuyCountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim()== null || s.toString().trim().length()< 1){
                    return;
                }
                int value= Integer.parseInt(s.toString().trim());
                LogUtil.e("edittext中输入手数：", ""+value);
                if (value> 100) {
                    value= 100;
                    dialogBuyCountEt.setText(String.valueOf(value));
                    return;
                }
                if (value< minCount){
                    value= minCount;
                    dialogBuyCountEt.setText(String.valueOf(value));
                    return;
                }
                dialogBuyCount.setText(
                        String.format(
                                getString(R.string.xiadan_dialog_buycount),
                                String.valueOf(value)));
                float zongJia= leverageEntity.getPrice()*value;
                float shouXuFei= zongJia*leverageEntity.getFeeRate();
                dialogBuyPrice.setText(Tools.formatFloat(zongJia));
                dialogServerPrice.setText(String.format(
                        getString(R.string.xiadan_dialog_shouxufei),
                        Tools.formatFloat(shouXuFei)));
                buyCount= value;

                if (leverage== 100 || leverage== 50){
                    if ((value - 10)!= dialogSeekBar.getProgress()){
                        dialogSeekBar.setProgress(value - 10);
                    }
                }else if (leverage== 1){
                    if ((value - 1)!= dialogSeekBar.getProgress()){
                        dialogSeekBar.setProgress(value - 1);
                    }
                }
            }
        });
        dialogPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框中现在的数字
                int value= Integer.parseInt(dialogBuyCountEt.getText().toString().trim());
                //如果输入框的数字小于100，将输入框中的数字加一
                if (value< 100){
                    value += 1;
                    dialogBuyCountEt.setText(String.valueOf(value));
                }
            }
        });
        dialogBuyCountEt.setText(String.valueOf(minCount));
    }

}
