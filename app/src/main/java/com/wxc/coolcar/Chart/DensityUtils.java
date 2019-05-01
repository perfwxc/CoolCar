package com.wxc.coolcar.Chart;

import android.content.Context;

/**
 * Created by Supreme on 2018/7/23.
 */

public class DensityUtils {
    public static int dp2px(Context context, float dp) {
        //获取设备密度
        float density = context.getResources().getDisplayMetrics().density;
        //4.3, 4.9, 加0.5是为了四舍五入
        int px = (int) (dp * density + 0.5f);
        return px;
    }

    public static float px2dp(Context context, int px) {
        //获取设备密度
        float density = context.getResources().getDisplayMetrics().density;
        float dp = px / density;
        return dp;
    }
}
