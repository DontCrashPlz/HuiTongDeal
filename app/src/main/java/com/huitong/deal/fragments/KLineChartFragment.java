package com.huitong.deal.fragments;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.CommodityDetailEntity;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.KLineEntity;
import com.huitong.deal.beans.TimeLineDataEntity;
import com.huitong.deal.beans.TimeLineEntity;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseFragment;
import com.zheng.zchlibrary.utils.LogUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/13.
 */
@Deprecated
public class KLineChartFragment extends BaseFragment {

    public static final int KLINE_TAG_5_MINUTE= 1;
    public static final int KLINE_TAG_30_MINUTE= 2;
    public static final int KLINE_TAG_60_MINUTE= 3;
    public static final int KLINE_TAG_1_DAY= 4;

    public static KLineChartFragment newInstance(String stockCode, int tag){
        KLineChartFragment instance = new KLineChartFragment();
        Bundle args = new Bundle();
        args.putString("stockCode", stockCode);
        args.putInt("tag", tag);
        instance.setArguments(args);
        return instance;
    }

    private String mStockCode;
    private int tag;

    private String mKLineType;
    private CandleStickChart mCandleChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_chart_k, container, false);

        mStockCode= getArguments().getString("stockCode");
        tag= getArguments().getInt("tag");
        switch (tag){
            case KLINE_TAG_5_MINUTE:
                mKLineType= "5M";
                break;
            case KLINE_TAG_30_MINUTE:
                mKLineType= "30M";
                break;
            case KLINE_TAG_60_MINUTE:
                mKLineType= "60M";
                break;
            case KLINE_TAG_1_DAY:
                mKLineType= "1D";
                break;
            default:
                showShortToast("K线图类型获取失败");
                return null;
        }

        mCandleChart= mView.findViewById(R.id.combined_chart);

        mCandleChart.getDescription().setEnabled(false);
        mCandleChart.setMaxVisibleValueCount(0);
        mCandleChart.setPinchZoom(false);
        mCandleChart.setDrawGridBackground(false);
        mCandleChart.setNoDataText("没有数据");//没有数据时显示的文字
        mCandleChart.setNoDataTextColor(Color.rgb(27, 130, 210));//没有数据时显示文字的颜色
        mCandleChart.setDrawGridBackground(false);//chart 绘图区后面的背景矩形将绘制
        mCandleChart.setDrawBorders(true);//禁止绘制图表边框的线
        mCandleChart.setBorderColor(Color.GRAY); //设置 chart 边框线的颜色。
        mCandleChart.setBorderWidth(1); //设置 chart 边界线的宽度，单位 dp。
        mCandleChart.getLegend().setEnabled(false);
        mCandleChart.setAutoScaleMinMaxEnabled(true);
        //设置chart是否可以触摸
        mCandleChart.setTouchEnabled(false);
        //设置是否可以拖拽
        mCandleChart.setDragEnabled(false);
        //设置是否可以缩放 x和y，默认true
        mCandleChart.setScaleEnabled(false);
        //是否缩放X轴
//        mCandleChart.setScaleXEnabled(true);
        //X轴缩放比例
//        mCandleChart.setScaleX(1.5f);
        //Y轴缩放比例
//        mCandleChart.setScaleY(1.5f);
        //是否缩放Y轴
//        mCandleChart.setScaleYEnabled(true);
        //设置是否可以通过双击屏幕放大图表。默认是true
        mCandleChart.setDoubleTapToZoomEnabled(false);
//        mCandleChart.getViewPortHandler().getMatrixTouch().postScale(3f, 1f);

        XAxis xAxis = mCandleChart.getXAxis();
        xAxis.setEnabled(true);//设置轴启用或禁用 如果禁用以下的设置全部不生效
        xAxis.setDrawAxisLine(false);//是否绘制轴线
        xAxis.setDrawGridLines(true);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setTextSize(9);//设置字体
        xAxis.setTextColor(Color.WHITE);//设置字体颜色
        xAxis.setAvoidFirstLastClipping(true);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                DateFormat dateFormat = new SimpleDateFormat("MM-dd");
//                Date date= new Date((long) value);
//                return dateFormat.format(date);
//            }
//        });
        xAxis.setLabelCount(5, true);

        YAxis leftAxis = mCandleChart.getAxisLeft();
        leftAxis.setLabelCount(7, true);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(true);//设置x轴上每个点对应的线
        leftAxis.setDrawLabels(true);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        YAxis rightAxis = mCandleChart.getAxisRight();
        rightAxis.setEnabled(false);


        final String token= MyApplication.getInstance().getToken();
        addNetWork(Network.getInstance().getKLine(token, mStockCode, mKLineType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HttpResult<KLineEntity>>() {
                                @Override
                                public void accept(HttpResult<KLineEntity> kLineEntityHttpResult) throws Exception {
                                    if (kLineEntityHttpResult.getData()!= null){
//                                        preprocessData(kLineEntityHttpResult.getData());
                                        mCandleChart.resetTracking();
                                        ArrayList<CandleEntry> yVals1 = new ArrayList<>();
                                        List<ArrayList<String>> dataList= kLineEntityHttpResult.getData().getKlinelist();
                                        if (dataList.size()> 60){
                                            int size= dataList.size();
                                            dataList= dataList.subList(size-61,size-1);
                                        }
                                        for (int i= 0; i< dataList.size(); i++){
                                            ArrayList<String> list= dataList.get(i);
                                            if (tag== KLINE_TAG_1_DAY){
                                                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                            }else {
                                                dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                                            }
                                            Date date= dateFormat.parse(list.get(0));
                                            float time= date.getTime();
                                            yVals1.add(
                                                    new CandleEntry(
                                                            i,
                                                            Float.parseFloat(list.get(3)),
                                                            Float.parseFloat(list.get(4)),
                                                            Float.parseFloat(list.get(1)),
                                                            Float.parseFloat(list.get(2))));
                                        }
                                        CandleDataSet set1 = new CandleDataSet(yVals1, "Data Set");
                                        set1.setDrawIcons(false);
                                        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                                        set1.setShadowColor(Color.DKGRAY);
                                        set1.setShadowWidth(0.7f);
                                        set1.setDecreasingColor(Color.RED);
                                        set1.setDecreasingPaintStyle(Paint.Style.FILL);
                                        set1.setIncreasingColor(Color.rgb(122, 242, 84));
                                        set1.setIncreasingPaintStyle(Paint.Style.STROKE);
                                        set1.setNeutralColor(Color.BLUE);
                                        set1.setHighlightEnabled(false);
                                        set1.setShadowColorSameAsCandle(true);

                                        CandleData data = new CandleData(set1);

                                        mCandleChart.setData(data);
                                        mCandleChart.invalidate();
                                    }
                                }
                            }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.d("throwable", throwable.toString());
                        showShortToast("网络请求失败");
                    }
                }));
//        if (token!= null && token.length()> 0){
//            addNetWork(
//                    Observable.interval(5, TimeUnit.SECONDS)
//                            .flatMap(new Function<Long, ObservableSource<HttpResult<KLineEntity>>>() {
//                                @Override
//                                public ObservableSource<HttpResult<KLineEntity>> apply(Long aLong) throws Exception {
//                                    return Network.getInstance().getKLine(token, mStockCode, mKLineType);
//                                }
//                            })
//                            .filter(new Predicate<HttpResult<KLineEntity>>() {
//                                @Override
//                                public boolean test(HttpResult<KLineEntity> kLineEntityHttpResult) throws Exception {
//                                    if ("success".equals(kLineEntityHttpResult.getStatus())){
//                                        return true;
//                                    }
//                                    return false;
//                                }
//                            })
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(new Consumer<HttpResult<KLineEntity>>() {
//                                @Override
//                                public void accept(HttpResult<KLineEntity> kLineEntityHttpResult) throws Exception {
//                                    if (kLineEntityHttpResult.getData()!= null){
//                                        mCombinedChart.resetTracking();
//                                        preprocessData(kLineEntityHttpResult.getData());
//                                        data.setData(generateCandleData(kLineEntityHttpResult.getData().getKlinelist()));
//                                        data.setData(generateLineData(kLineEntityHttpResult.getData().getM5list()));
//                                        mCombinedChart.setData(data);
//                                        mCombinedChart.invalidate();
//                                    }
//                                }
//                            }));
//        }

        return mView;
    }

    private ArrayList<Float> xList= new ArrayList<>();
    private DateFormat dateFormat;
    private void preprocessData(KLineEntity entity) throws ParseException {
        if (entity.getKlinelist()== null || entity.getKlinelist().size()<1){
            showShortToast("无K线数据");
            return;
        }
        for (ArrayList<String> list : entity.getKlinelist()){
            String valueTime= list.get(0);

            if (tag== KLINE_TAG_1_DAY){
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            }else {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            }
            Date date= dateFormat.parse(valueTime);
            float time= date.getTime();
            xList.add(time);
        }
    }

    private LineData generateLineData(ArrayList<String> m5List) {

        ArrayList<Entry> entries = new ArrayList<>();

        for (int i= 0; i< m5List.size(); i++){
            String m5Str= m5List.get(i);
            if (m5Str== null || "null".equals(m5Str)){
                continue;
            }
            entries.add(new Entry(xList.get(i), Float.parseFloat(m5Str)));
        }

        LineDataSet lineDataSet = new LineDataSet(entries, null);
        lineDataSet.setColor(Color.rgb(181, 225, 252));
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setHighlightEnabled(false);
        lineDataSet.setLineWidth(1f);//设置线宽
        lineDataSet.setDrawFilled(false);//设置禁用范围背景填充

        //创建LineData对象 属于LineChart折线图的数据集合
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet); // add the datasets

        return new LineData(dataSets);
    }

    protected CandleData generateCandleData(ArrayList<ArrayList<String>> klineList) {

        ArrayList<CandleEntry> entries = new ArrayList<>();

        for (int i= 0; i< klineList.size(); i++){
            ArrayList<String> list= klineList.get(i);
            entries.add(
                    new CandleEntry(
                            xList.get(i),
                            Float.parseFloat(list.get(3)),
                            Float.parseFloat(list.get(4)),
                            Float.parseFloat(list.get(1)),
                            Float.parseFloat(list.get(2))));
        }

        CandleDataSet candleDataSet = new CandleDataSet(entries, null);
//        candleDataSet.setDecreasingColor(Color.rgb(142, 150, 175));
//        candleDataSet.setShadowColor(Color.DKGRAY);
//        candleDataSet.setBarSpace(0.3f);
//        candleDataSet.setValueTextSize(10f);
//        candleDataSet.setDrawValues(false);
        candleDataSet.setDrawValues(false);
        candleDataSet.setDrawIcons(false);
        candleDataSet.setShadowColor(Color.DKGRAY);
        candleDataSet.setShadowWidth(0.7f);
        candleDataSet.setDecreasingColor(Color.RED);
        candleDataSet.setDecreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setIncreasingColor(Color.rgb(122, 242, 84));
        candleDataSet.setIncreasingPaintStyle(Paint.Style.STROKE);
        candleDataSet.setNeutralColor(Color.BLUE);

        return new CandleData(candleDataSet);
    }

    @Override
    public void initProgressDialog() {

    }
}
