package com.example.myapplication.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.myapplication.domain.Pie;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class PieChart extends View {
    // 颜色表，ARGB
    private int[] mColors = {0xFF184D47, 0xFF96BB7C, 0xFFD6EFC7, 0xFFEEBB4D, 0xFFFECEAB, 0xFFFF8C69, 0xFFFF847C,
            0xFF5A3D55, 0xFFBAC964,0xFF436F8A};
    // 饼状图初始绘制角度
    private float mStartAngle = 0;
    // 数据
    private ArrayList<Pie> mData;
    // 宽高
    private int mWidth, mHeight;
    // 画笔
    private Paint mPaint = new Paint();

    public PieChart(Context context) {
        this(context, null);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //设置画笔模式为填充
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mData == null || mData.size() < 1) {
            return;
        }
        // 当前起始角度
        float currentStartAngle = mStartAngle;
        // 将画布坐标原点移动到中心位置
        canvas.translate(mWidth / 2, mHeight / 2);
        // 饼状图半径
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.7);
        // 饼状图绘制区域
        RectF rectF = new RectF(-r, -r, r, r);

        //饼图
        for (Pie pie : mData) {
            mPaint.setColor(pie.getColor());
            canvas.drawArc(rectF, currentStartAngle, pie.getAngle(), true, mPaint);
            currentStartAngle += pie.getAngle();
        }
        mPaint.setTextSize(15);
        canvas.translate(10-mWidth/2, 100-mHeight/2);

        //文字
        int y = 0;
        for (Pie pie : mData) {
            mPaint.setColor(pie.getColor());
            canvas.drawText(pie.getClassName()+":" + pie.getPercentage() * 100 / 1 + "%", 0, y, mPaint);
            y += 50;
        }
    }

    // 设置数据
    public void setData(ArrayList<Pie> list) {
        this.mData = list;
        initData(list);
        invalidate();   //刷新
    }

    // 初始化数据
    private void initData(ArrayList<Pie> mData) {
        // 数据有问题 直接返回
        if (null == mData || mData.size() == 0) {
            return;
        }
        //计算数值和
        float sumValue = 0;
        for (int i = 0; i < mData.size(); i++) {
            Pie pie = mData.get(i);
            sumValue += pie.getValue();
            //设置颜色，怕颜色表不够用所以循环利用颜色表
            pie.setColor(mColors[i % mColors.length]);
        }
        for (Pie pie : mData) {
            // 百分比
            float percentage = pie.getValue() / sumValue;
            // 记录百分比
            pie.setPercentage(percentage);
            // 对应的角度
            float angle = percentage * 360;
            // 记录角度大小
            pie.setAngle(angle);
        }
    }
}
