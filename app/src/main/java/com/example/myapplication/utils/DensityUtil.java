package com.example.myapplication.utils;

import android.content.Context;

public class DensityUtil {
    /**
     * 根据手机的分辨率从dip的单位转成px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从px(像素)的单位转成dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
