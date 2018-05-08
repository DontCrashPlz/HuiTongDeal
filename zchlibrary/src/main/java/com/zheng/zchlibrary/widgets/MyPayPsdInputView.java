package com.zheng.zchlibrary.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.zheng.zchlibrary.R;
import com.zheng.zchlibrary.utils.LogUtil;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by Allen on 2017/5/7.
 * 自定义支付密码输入框
 */

public class MyPayPsdInputView extends EditText {

    private Context mContext;
    /**
     * 第一个圆开始绘制的圆心坐标
     */
    private float startX;
    private float startY;

    /**
     * view的宽高
     */
    private int height;
    private int width;

    /**
     * 当前输入密码位数
     */
    private int textLength = 0;
    /**
     * 最大输入位数
     */
    private int maxCount = 6;
    /**
     * 圆点的半径  默认10
     */
    private int circleRadius = 10;
    /**
     * 圆点的颜色  默认BLACK
     */
    private int circleColor = Color.BLACK;
    /**
     * 边框圆角  默认5
     */
    private int strokeCorners = 5;
    /**
     * 边框颜色 默认黑色
     */
    private int strokeColor = Color.BLACK;
    /**
     * 边框宽度  默认2
     */
    private int strokeWidth = 1;
    /**
     * 分割线的宽度  默认2
     */
    private int divideLineWidth = 1;
    /**
     * 竖直分割线的颜色 默认黑色
     */
    private int divideLineColor = Color.BLACK;


    /**
     * 竖直分割线的画笔
     */
    private Paint divideLinePaint;
    /**
     * 圆的画笔
     */
    private Paint circlePaint;
    /**
     * 边框的画笔
     */
    private Paint strokePaint;


    /**
     * 当前输入的位置索引
     */
    private int position = 0;
    /**
     * 边框矩形
     */
    private RectF strokeRect= new RectF();
    private float gridWidth;

    public MyPayPsdInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        getAtt(attrs);
        initPaint();

        this.setBackgroundColor(Color.TRANSPARENT);
        this.setCursorVisible(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});

    }

    private void getAtt(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.MyPayPsdInputView);
        maxCount = typedArray.getInt(R.styleable.MyPayPsdInputView_maxCount, maxCount);

        circleRadius = typedArray.getDimensionPixelOffset(R.styleable.MyPayPsdInputView_circleRadius, circleRadius);
        circleColor = typedArray.getColor(R.styleable.MyPayPsdInputView_circleColor, circleColor);

        strokeCorners = typedArray.getDimensionPixelOffset(R.styleable.MyPayPsdInputView_strokeCorners, strokeCorners);
        strokeColor = typedArray.getColor(R.styleable.MyPayPsdInputView_strokeColor, strokeColor);
        strokeWidth = typedArray.getDimensionPixelOffset(R.styleable.MyPayPsdInputView_strokeWidth, strokeWidth);

        divideLineWidth = typedArray.getDimensionPixelSize(R.styleable.MyPayPsdInputView_divideLineWidth, divideLineWidth);
        divideLineColor = typedArray.getColor(R.styleable.MyPayPsdInputView_divideLineColor, divideLineColor);

        typedArray.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        divideLinePaint = getPaint(divideLineWidth, Paint.Style.FILL, divideLineColor);
        circlePaint = getPaint(circleRadius, Paint.Style.FILL, circleColor);
        strokePaint = getPaint(strokeWidth, Paint.Style.STROKE, strokeColor);
    }

    /**
     * 设置画笔
     *
     * @param strokeWidth 画笔宽度
     * @param style       画笔风格
     * @param color       画笔颜色
     * @return
     */
    private Paint getPaint(int strokeWidth, Paint.Style style, int color) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(style);
        paint.setColor(color);
        paint.setAntiAlias(true);

        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width= MeasureSpec.getSize(widthMeasureSpec);
        height= MeasureSpec.getSize(heightMeasureSpec);
        Log.e("MyPayPsdInputView onMeasure :" , "width = " + width + " ; height = " + height);
        Log.e("MyPayPsdInputView onMeasure :" , "getWidth = " + getWidth() + " ; getHeight = " + getHeight());
        Log.e("MyPayPsdInputView onMeasure :" , "getPaddingLeft = " + getPaddingLeft() + " ; getPaddingRight = " + getPaddingRight());

        strokeRect.set(0, 0, width, height);

        gridWidth= (width - (strokeWidth * 2) - ((maxCount - 1)*divideLineWidth))/maxCount;

        startX= strokeWidth + gridWidth/2;
        startY= height/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //不删除的画会默认绘制输入的文字
//       super.onDraw(canvas);

        drawWeChatBorder(canvas);

        drawPsdCircle(canvas);
    }

    /**
     * 画微信支付密码的样式
     *
     * @param canvas
     */
    private void drawWeChatBorder(Canvas canvas) {

        if (strokeCorners> 0){
            canvas.drawRoundRect(strokeRect, strokeCorners, strokeCorners, strokePaint);
        }else if (strokeCorners== 0){
            canvas.drawRect(strokeRect, strokePaint);
        }


        for (int i = 0; i < maxCount - 1; i++) {
            float divideX= strokeWidth + (i*divideLineWidth) + ((i + 1)*gridWidth);
            canvas.drawLine(divideX,
                    0,
                    divideX,
                    height,
                    divideLinePaint);
        }

    }

    /**
     * 画密码实心圆
     *
     * @param
     */
    private void drawPsdCircle(Canvas canvas) {
        for (int i = 0; i < textLength; i++) {
            canvas.drawCircle(startX + (i*(gridWidth + divideLineWidth)),
                    startY,
                    circleRadius,
                    circlePaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.position = start + lengthAfter;
        textLength = text.toString().length();
        Log.e("onTextChanged", String.valueOf(textLength));

        invalidate();

    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

        //保证光标始终在最后
        if (selStart == selEnd) {
            setSelection(getText().length());
        }
    }

    /**
     * 获取输入的密码
     *
     * @return
     */
    public String getPasswordString() {
        return getText().toString().trim();
    }

    /**
     * 清空密码
     */
    public void cleanPsd() {
        setText("");
    }

}
