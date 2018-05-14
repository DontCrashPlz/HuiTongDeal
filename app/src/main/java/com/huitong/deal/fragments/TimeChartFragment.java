package com.huitong.deal.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.TimeLineDataEntity;
import com.huitong.deal.beans.TimeLineEntity;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseFragment;
import com.zheng.zchlibrary.utils.LogUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/13.
 */

public class TimeChartFragment extends BaseFragment {

    public static TimeChartFragment newInstance(String stockCode){
        TimeChartFragment instance = new TimeChartFragment();
        Bundle args = new Bundle();
        args.putString("stockCode", stockCode);
        instance.setArguments(args);
        return instance;
    }

    private String mStockCode;
    private LineChart mLineChart;
    private ArrayList<Entry> values = new ArrayList<>();
    private LineDataSet lineDataSet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_chart_time, container, false);

        mStockCode= getArguments().getString("stockCode");

        mLineChart= mView.findViewById(R.id.line_chart);

        mLineChart.setNoDataText("没有数据");//没有数据时显示的文字
        mLineChart.setNoDataTextColor(Color.rgb(27, 130, 210));//没有数据时显示文字的颜色
        mLineChart.setDrawGridBackground(false);//chart 绘图区后面的背景矩形将绘制
//        mLineChart.setGridBackgroundColor(Color.RED);
        mLineChart.setDrawBorders(true);//禁止绘制图表边框的线
        mLineChart.setBorderColor(Color.GRAY); //设置 chart 边框线的颜色。
        mLineChart.setBorderWidth(1); //设置 chart 边界线的宽度，单位 dp。
        mLineChart.getLegend().setEnabled(false);
        mLineChart.getDescription().setEnabled(false);

        //获取此图表的x轴
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setEnabled(true);//设置轴启用或禁用 如果禁用以下的设置全部不生效
        xAxis.setDrawAxisLine(false);//是否绘制轴线
        xAxis.setDrawGridLines(true);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setTextSize(9);//设置字体
        xAxis.setTextColor(Color.WHITE);//设置字体颜色
        xAxis.setAvoidFirstLastClipping(true);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                DateFormat dateFormat = new SimpleDateFormat("MM-dd");
                Date date= new Date((long) value);
                return dateFormat.format(date);
            }
        });

        /**
         * Y轴默认显示左右两个轴线
         */
        mLineChart.getAxisRight().setEnabled(false);
        //获取左边的轴线
        YAxis leftAxis = mLineChart.getAxisLeft();
//        leftAxis.setEnabled(true);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(true);//设置x轴上每个点对应的线
        leftAxis.setDrawLabels(false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        String token= MyApplication.getInstance().getToken();
        addNetWork(Network.getInstance().getTimeLine(token, mStockCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HttpResult<TimeLineEntity>>() {
                    @Override
                    public void accept(HttpResult<TimeLineEntity> timeLineEntityHttpResult) throws Exception {
                        if ("error".equals(timeLineEntityHttpResult.getStatus())){
                            showShortToast(timeLineEntityHttpResult.getDescription());
                        }else if ("success".equals(timeLineEntityHttpResult.getStatus())){
                            if (timeLineEntityHttpResult.getData().getDatalist().size()> 0){
                                for (TimeLineDataEntity entity : timeLineEntityHttpResult.getData().getDatalist()){
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                                    Date date= dateFormat.parse(entity.getValue().get(0));
                                    float time= date.getTime();
                                    float price= Float.parseFloat(entity.getValue().get(1));
                                    String name= entity.getName();
                                    values.add(new Entry(time, price, name));
                                }
                                lineDataSet = new LineDataSet(values , null);
                                lineDataSet.setColor(Color.rgb(181, 225, 252));
                                lineDataSet.setDrawValues(false);
                                lineDataSet.setDrawCircles(false);
                                lineDataSet.setHighlightEnabled(false);
                                lineDataSet.setLineWidth(1f);//设置线宽
                                lineDataSet.setDrawFilled(true);//设置禁用范围背景填充
                                lineDataSet.setFillColor(Color.rgb(51, 181, 229));

                                //创建LineData对象 属于LineChart折线图的数据集合
                                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                                dataSets.add(lineDataSet); // add the datasets
                                // create a data object with the datasets
                                LineData data = new LineData(dataSets);
                                // set data
                                mLineChart.setData(data);
                                //绘制图表
                                mLineChart.invalidate();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.d("throwable", throwable.toString());
                        showShortToast("网络请求失败");
                    }
                }));

        return mView;
    }
}
