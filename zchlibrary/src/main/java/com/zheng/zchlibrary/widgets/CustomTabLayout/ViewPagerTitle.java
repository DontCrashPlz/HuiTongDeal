package com.zheng.zchlibrary.widgets.CustomTabLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zheng.zchlibrary.R;
import com.zheng.zchlibrary.utils.DensityUtil;

import java.util.ArrayList;

import static com.zheng.zchlibrary.widgets.CustomTabLayout.Tool.getScreenWidth;
import static com.zheng.zchlibrary.widgets.CustomTabLayout.Tool.getTextViewLength;

public class ViewPagerTitle extends HorizontalScrollView {

    private String[] titles;
    private ArrayList<TextView> textViews = new ArrayList<>();
    private OnTextViewClick onTextViewClick;
    private DynamicLine dynamicLine;
    private ViewPager viewPager;
    private MyOnPageChangeListener onPageChangeListener;
    private int margin;
    private float defaultTextSize;
    private float selectedTextSize;
    private int defaultTextColor;
    private int shaderColorStart;
    private int lineHeight;
    private int shaderColorEnd;

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public void setDefaultTextSize(float defaultTextSize) {
        this.defaultTextSize = defaultTextSize;
    }

    public void setSelectedTextSize(float selectedTextSize) {
        this.selectedTextSize = selectedTextSize;
    }

    public void setDefaultTextColor(int defaultTextColor) {
        this.defaultTextColor = defaultTextColor;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public void setAllTextViewLength(int allTextViewLength) {
        this.allTextViewLength = allTextViewLength;
    }

    public void setBackgroundContentColor(int backgroundContentColor) {
        this.backgroundColor = backgroundContentColor;
    }

    public void setItemMargins(float itemMargins) {
        this.itemMargins = itemMargins;
    }

    private int selectedTextColor;
    private int allTextViewLength;
    private int backgroundColor;
    private float itemMargins;


    public ViewPagerTitle(Context context) {
        this(context, null);
    }

    public ViewPagerTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(context, attrs);
    }

    private void initAttributeSet(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerTitle);
        defaultTextColor = array.getColor(R.styleable.ViewPagerTitle_defaultTextViewColor, Color.GRAY);
        selectedTextColor = array.getColor(R.styleable.ViewPagerTitle_selectedTextViewColor, Color.BLACK);
        //此处获取到的单位是px，xml中的sp和dp已经被换算为px
        defaultTextSize = array.getDimension(R.styleable.ViewPagerTitle_defaultTextViewSize, DensityUtil.sp2px(getContext(), 14));
        selectedTextSize = array.getDimension(R.styleable.ViewPagerTitle_defaultTextViewSize, DensityUtil.sp2px(getContext(), 16));

        backgroundColor = array.getColor(R.styleable.ViewPagerTitle_background_content_color, Color.WHITE);
        itemMargins = array.getDimension(R.styleable.ViewPagerTitle_item_margins, 30);

        shaderColorStart = array.getColor(R.styleable.ViewPagerTitle_line_start_color, Color.parseColor("#ffc125"));
        shaderColorEnd = array.getColor(R.styleable.ViewPagerTitle_line_end_color, Color.parseColor("#ff4500"));
        lineHeight = array.getInteger(R.styleable.ViewPagerTitle_line_height, 20);

        array.recycle();
    }


    /**
     * 初始化时，调用这个方法。ViewPager需要设置Adapter，且titles的数据长度需要与Adapter中的数据长度一置。
     * @param titles 标题1、标题2 etc
     * @param viewPager
     * @param defaultIndex 默认选择的第几个页面
     */
    public void initData(String[] titles, ViewPager viewPager, int defaultIndex) {
        this.titles = titles;
        this.viewPager = viewPager;

        createTextViews(titles);

        int fixLeftDis = getFixLeftDis();
        onPageChangeListener = new MyOnPageChangeListener(getContext(), viewPager, dynamicLine, this, allTextViewLength, margin, fixLeftDis);
        setDefaultIndex(defaultIndex);

        viewPager.addOnPageChangeListener(onPageChangeListener);

    }

    private int getFixLeftDis() {
        TextView textView = new TextView(getContext());
        textView.setTextSize(defaultTextSize);
        textView.setText(titles[0]);
        float defaultTextSize = getTextViewLength(textView);
        textView.setTextSize(selectedTextSize);
        float selectTextSize = getTextViewLength(textView);
        return (int) (selectTextSize - defaultTextSize) / 2;
    }

    public ArrayList<TextView> getTextView() {
        return textViews;
    }

    /**
     * 添加TextView
     * @param titles
     */
    private void createTextViews(String[] titles) {
        FrameLayout contentView = new FrameLayout(getContext());
        contentView.setBackgroundColor(backgroundColor);
        contentView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(contentView);

        //添加underLine
        LayoutParams underLineLp=
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        underLineLp.gravity= Gravity.BOTTOM;
        dynamicLine = new DynamicLine(getContext(), shaderColorStart, shaderColorEnd, lineHeight);
        dynamicLine.setLayoutParams(underLineLp);
        contentView.addView(dynamicLine);

        //添加标题布局
        LayoutParams textLayoutLp=
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLayoutLp.gravity= Gravity.CENTER_VERTICAL;
        LinearLayout textViewLl = new LinearLayout(getContext());
        textViewLl.setOrientation(LinearLayout.HORIZONTAL);
        textViewLl.setLayoutParams(textLayoutLp);
        contentView.addView(textViewLl);

        //添加小标题
        margin = getTextViewMargins(titles);
        LinearLayout.LayoutParams textViewParams=
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewParams.setMargins(margin, 0, margin, 0);
        for (int i = 0; i < titles.length; i++) {
            TextView textView = new TextView(getContext());
            textView.setText(titles[i]);
            textView.setTextColor(Color.GRAY);
//            float size= DensityUtil.px2sp(getContext(), defaultTextSize);
            //setTextSize默认的单位为sp
            textView.setTextSize(DensityUtil.px2sp(getContext(), defaultTextSize));
            textView.setLayoutParams(textViewParams);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setOnClickListener(onClickListener);
            textView.setTag(i);
            textViews.add(textView);
            textViewLl.addView(textView);
        }

    }

    private int getTextViewMargins(String[] titles) {
        float countLength = 0;
        TextView textView = new TextView(getContext());
        textView.setTextSize(DensityUtil.px2sp(getContext(), defaultTextSize));
        TextPaint paint = textView.getPaint();


        for (int i = 0; i < titles.length; i++) {
            countLength = countLength + itemMargins + paint.measureText(titles[i]) + itemMargins;
        }
        int screenWidth = getScreenWidth(getContext());

        if (countLength <= screenWidth) {
            allTextViewLength = screenWidth;
            return (screenWidth / titles.length - (int) paint.measureText(titles[0])) / 2;
        } else {
            allTextViewLength = (int) countLength;
            return (int) itemMargins;
        }
    }


    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            setCurrentItem((int) v.getTag());
            viewPager.setCurrentItem((int) v.getTag());
            if (onTextViewClick != null) {
                onTextViewClick.textViewClick((TextView) v, (int) v.getTag());
            }

        }
    };

    public void setDefaultIndex(int index) {
        setCurrentItem(index);
    }

    public void setCurrentItem(int index) {
        for (int i = 0; i < textViews.size(); i++) {
            if (i == index) {
                textViews.get(i).setTextColor(selectedTextColor);
                textViews.get(i).setTextSize(DensityUtil.px2sp(getContext(), selectedTextSize));
            } else {
                textViews.get(i).setTextColor(defaultTextColor);
                textViews.get(i).setTextSize(DensityUtil.px2sp(getContext(), defaultTextSize));
            }
        }
    }

    public interface OnTextViewClick {
        void textViewClick(TextView textView, int index);
    }

    public void setOnTextViewClickListener(OnTextViewClick onTextViewClick) {
        this.onTextViewClick = onTextViewClick;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        viewPager.removeOnPageChangeListener(onPageChangeListener);
    }


}
