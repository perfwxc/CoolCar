package com.wxc.coolcar.Chart;

/**
 * Created by Supreme on 2018/7/23.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.wxc.coolcar.R;


public class CreditScoreView extends View {

    //数据个数
    private int dataCount = 5;
    //每个角的弧度
    private float radian = (float) (Math.PI * 2 / dataCount);
    //雷达图半径
    private float radius;
    //中心X坐标
    private int centerX;
    //中心Y坐标
    private int centerY;
    //各维度标题
    private String[] titles = {"酒精浓度", "血压", "血氧", "心率", "温度"};
    //各维度图标
    private int[] icons = {R.mipmap.ic_performance, R.mipmap.ic_history, R.mipmap.ic_contacts,
            R.mipmap.ic_predilection, R.mipmap.ic_identity};
    //各维度分值
    private float[] data = {0, 0, 0, 0, 0};
    //数据最大值
    private float maxValue = 20;
    //雷达图与标题的间距
    private int radarMargin = DensityUtils.dp2px(getContext(), 15);
    //雷达区画笔
    private Paint mainPaint;
    //数据区画笔
    private Paint valuePaint;
    //分数画笔
    private Paint scorePaint;
    //标题画笔
    private Paint titlePaint;
    //图标画笔
    private Paint iconPaint;
    //分数大小
    private int scoreSize = DensityUtils.dp2px(getContext(), 28);
    //标题文字大小
    private int titleSize = DensityUtils.dp2px(getContext(), 13);

    public CreditScoreView(Context context) {
        this(context, null);
    }

    public CreditScoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CreditScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setData(String aa, String bb, String cc1, String cc2, String dd, String ee) {
        float a = Float.parseFloat(aa);
        float b = Float.parseFloat(bb);
        float c1 = Float.parseFloat(cc1);
        float c2 = Float.parseFloat(cc2);
        float d = Float.parseFloat(dd);
        float e = Float.parseFloat(ee);
        int A = 0;
        if (a >= 36.3 && a <= 37.2) A = 20;
        if ((a >= 36.0 && a <= 36.2) || (a >= 37.3 && a <= 37.5)) A = 18;
        if ((a >= 35.5 && a <= 35.9) || (a >= 37.6 && a <= 37.8)) A = 16;
        if ((a >= 35.0 && a <= 35.4) || (a >= 37.9 && a <= 38.0)) A = 14;
        if ((a >= 34.6 && a <= 34.9) || (a >= 38.1 && a <= 38.2)) A = 12;
        if ((a >= 34.2 && a <= 34.5) || (a >= 38.1 && a <= 38.2)) A = 10;
        if ((a >= 33.6 && a <= 34.1) || (a >= 38.3 && a <= 38.4)) A = 8;
        if ((a >= 33.1 && a <= 33.5) || (a >= 38.5 && a <= 38.6)) A = 6;
        if ((a >= 32.6 && a <= 33.0) || (a >= 38.7 && a <= 38.8)) A = 5;
        if ((a >= 32.0 && a <= 32.5) || (a >= 38.9 && a <= 39.0)) A = 4;
        if ((a >= 30.0 && a <= 31.9) || (a >= 40.4 && a <= 41.0)) A = 3;
        if ((a >= 27.1 && a <= 29.9) || (a >= 39.7 && a <= 40.3)) A = 2;
        if ((a >= 25.0 && a <= 27.0) || (a >= 39.1 && a <= 39.6)) A = 1;
        data[4] = A;


        int B = 0;
        if (b >= 60 && b <= 70) B = 20;
        if (b >= 71 && b <= 75) B = 19;
        if (b >= 76 && b <= 80) B = 18;
        if (b >= 81 && b <= 85) B = 17;
        if (b >= 86 && b <= 90) B = 16;
        if (b >= 91 && b <= 95) B = 15;
        if (b >= 96 && b <= 100) B = 14;
        if ((b >= 59 && b < 60) || (b >= 101 && b <= 105)) B = 10;
        if ((b >= 57 && b <= 58) || (b >= 106 && b <= 110)) B = 9;
        if ((b >= 55 && b <= 56) || (b >= 111 && b <= 115)) B = 8;
        if ((b >= 53 && b <= 54) || (b >= 116 && b <= 120)) B = 7;
        if ((b > 51 && b <= 52) || (b >= 121 && b <= 125)) B = 6;
        if ((b >= 51 && b < 52) || (b >= 126 && b <= 130)) B = 5;
        if ((b >= 48 && b <= 50) || (b >= 131 && b <= 137)) B = 4;
        if ((b >= 45 && b <= 47) || (b >= 138 && b <= 146)) B = 3;
        if ((b >= 42 && b <= 44) || (b >= 147 && b <= 153)) B = 2;
        if ((b >= 40 && b <= 41) || (b >= 154 && b <= 160)) B = 1;
        data[3] = B;

        int C = 0;
        int C1 = 0;
        if (c1 >= 110 && c1 <= 120) C1 = 20;
        if (c1 == 109 || c1 == 121) C1 = 19;
        if (c1 == 108 || c1 == 122) C1 = 18;
        if (c1 == 107 || c1 == 123) C1 = 17;
        if (c1 == 106 || c1 == 124) C1 = 16;
        if (c1 == 105 || c1 == 125) C1 = 15;
        if (c1 == 104 || c1 == 126) C1 = 14;
        if (c1 == 103 || c1 == 127) C1 = 13;
        if (c1 == 102 || c1 == 128) C1 = 12;
        if (c1 == 101 || c1 == 129) C1 = 11;
        if (c1 == 100 || c1 == 130) C1 = 10;
        if (c1 == 99 || c1 == 131) C1 = 9;
        if (c1 == 98 || c1 == 132) C1 = 8;
        if (c1 == 97 || c1 == 133) C1 = 7;
        if (c1 == 96 || c1 == 134) C1 = 6;
        if (c1 == 95 || c1 == 135) C1 = 5;
        if (c1 == 94 || c1 == 136) C1 = 4;
        if (c1 == 93 || c1 == 137) C1 = 3;
        if (c1 == 92 || c1 == 138) C1 = 2;
        if (c1 == 91 || c1 == 139) C1 = 1;
        if (c1 == 90 || c1 == 140) C1 = 1;

        int C2 = 0;
        if (c2 >= 75 && c2 <= 80) C2 = 20;
        if (c2 == 74 || c2 == 81) C2 = 18;
        if (c2 == 73 || c2 == 82) C2 = 16;
        if (c2 == 72 || c2 == 83) C2 = 14;
        if (c2 == 71 || c2 == 84) C2 = 12;
        if (c2 == 70 || c2 == 85) C2 = 10;
        if (((c2 >= 68) && (c2 <= 69)) || c2 == 86) C2 = 8;
        if (((c2 >= 66) && (c2 <= 67)) || c2 == 87) C2 = 6;
        if (((c2 >= 64) && (c2 <= 65)) || c2 == 88) C2 = 4;
        if (((c2 >= 62) && (c2 <= 63)) || c2 == 89) C2 = 2;
        if (((c2 >= 60) && (c2 <= 61)) || c2 == 90) C2 = 1;
        C = C1 > C2 ? C2 : C1;
        data[1] = C;

        int D = 0;
        if (d == 98) D = 20;
        if (d == 97) D = 19;
        if (d == 96) D = 18;
        if (d == 95) D = 17;
        if (d == 94) D = 16;
        if (d == 93) D = 12;
        if (d == 92) D = 8;
        if (d == 91) D = 4;
        if (d == 90) D = 2;
        if (d < 90) D = 1;
        data[2] = D;

        int E = 0;
        if (e == 0) E = 20;
        if (e == 1) E = 19;
        if (e >= 2 && e <= 4) E = 18;
        if (e >= 5 && e <= 7) E = 17;
        if (e >= 8 && e <= 10) E = 16;
        if (e >= 11 && e <= 13) E = 15;
        if (e >= 14 && e <= 16) E = 14;
        if (e >= 17 && e <= 19) E = 13;
        if (e >= 20 && e <= 24) E = 12;
        if (e >= 25 && e <= 29) E = 11;
        if (e >= 30 && e <= 34) E = 10;
        if (e >= 35 && e <= 40) E = 9;
        if (e >= 41 && e <= 44) E = 8;
        if (e >= 45 && e <= 49) E = 7;
        if (e >= 50 && e <= 54) E = 6;
        if (e >= 55 && e <= 60) E = 5;
        if (e >= 61 && e <= 67) E = 4;
        if (e >= 68 && e <= 72) E = 3;
        if (e >= 73 && e <= 77) E = 2;
        if (e >= 78 && e <= 80) E = 1;
        data[0] = E;
    }

    private void init() {
        mainPaint = new Paint();
        mainPaint.setAntiAlias(true);
        mainPaint.setStrokeWidth(1.5f);
        mainPaint.setColor(Color.WHITE);
        mainPaint.setStyle(Paint.Style.STROKE);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(Color.WHITE);
        valuePaint.setAlpha(120);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        scorePaint = new Paint();
        scorePaint.setAntiAlias(true);
        scorePaint.setTextSize(scoreSize);
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextAlign(Paint.Align.CENTER);
        scorePaint.setStyle(Paint.Style.FILL);

        titlePaint = new Paint();
        titlePaint.setAntiAlias(true);
        titlePaint.setTextSize(titleSize);
        titlePaint.setColor(Color.WHITE);
        titlePaint.setStyle(Paint.Style.FILL);

        iconPaint = new Paint();
        iconPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //雷达图半径
        radius = Math.min(h, w) / 2 * 0.5f;
        //中心坐标
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawPolygon(canvas);
        drawLines(canvas);
        drawRegion(canvas);
        drawScore(canvas);
        drawTitle(canvas);
        drawIcon(canvas);
    }

    /**
     * 绘制多边形
     *
     * @param canvas 画布
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < dataCount; i++) {
            if (i == 0) {
                path.moveTo(getPoint(i).x, getPoint(i).y);
            } else {
                path.lineTo(getPoint(i).x, getPoint(i).y);
            }
        }

        //闭合路径
        path.close();
        canvas.drawPath(path, mainPaint);
    }

    /**
     * 绘制连接线
     *
     * @param canvas 画布
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < dataCount; i++) {
            path.reset();
            path.moveTo(centerX, centerY);
            path.lineTo(getPoint(i).x, getPoint(i).y);
            canvas.drawPath(path, mainPaint);
        }
    }

    /**
     * 绘制覆盖区域
     *
     * @param canvas 画布
     */
    private void drawRegion(Canvas canvas) {
        Path path = new Path();

        for (int i = 0; i < dataCount; i++) {
            //计算百分比
            float percent = data[i] / maxValue;
            int x = getPoint(i, 0, percent).x;
            int y = getPoint(i, 0, percent).y;
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        //绘制填充区域的边界
        path.close();
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);

        //绘制填充区域
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);
    }

    /**
     * 绘制分数
     *
     * @param canvas 画布
     */
    private void drawScore(Canvas canvas) {
        int score = 0;
        //计算总分
        for (int i = 0; i < dataCount; i++) {
            score += data[i];
        }
        canvas.drawText(score + "", centerX, centerY + scoreSize / 2, scorePaint);
    }

    /**
     * 绘制标题
     *
     * @param canvas 画布
     */
    private void drawTitle(Canvas canvas) {
        for (int i = 0; i < dataCount; i++) {
            int x = getPoint(i, radarMargin, 1).x;
            int y = getPoint(i, radarMargin, 1).y;

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icons[i]);
            int iconHeight = bitmap.getHeight();
            float titleWidth = titlePaint.measureText(titles[i]);

            //底下两个角的坐标需要向下移动半个图片的位置（1、2）
            if (i == 1) {
                y += (iconHeight / 2);
            } else if (i == 2) {
                x -= titleWidth;
                y += (iconHeight / 2);
            } else if (i == 3) {
                x -= titleWidth;
            } else if (i == 4) {
                x -= titleWidth / 2;
            }
            canvas.drawText(titles[i], x, y, titlePaint);
        }
    }

    /**
     * 绘制图标
     *
     * @param canvas 画布
     */
    private void drawIcon(Canvas canvas) {
        for (int i = 0; i < dataCount; i++) {
            int x = getPoint(i, radarMargin, 1).x;
            int y = getPoint(i, radarMargin, 1).y;

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icons[i]);
            int iconWidth = bitmap.getWidth();
            int iconHeight = bitmap.getHeight();
            float titleWidth = titlePaint.measureText(titles[i]);

            //上面获取到的x、y坐标是标题左下角的坐标
            //需要将图标移动到标题上方居中位置
            if (i == 0) {
                x += (titleWidth - iconWidth) / 2;
                y -= (iconHeight + getTextHeight(titlePaint));
            } else if (i == 1) {
                x += (titleWidth - iconWidth) / 2;
                y -= (iconHeight / 2 + getTextHeight(titlePaint));
            } else if (i == 2) {
                x -= (iconWidth + (titleWidth - iconWidth) / 2);
                y -= (iconHeight / 2 + getTextHeight(titlePaint));
            } else if (i == 3) {
                x -= (iconWidth + (titleWidth - iconWidth) / 2);
                y -= (iconHeight + getTextHeight(titlePaint));
            } else if (i == 4) {
                x -= iconWidth / 2;
                y -= (iconHeight + getTextHeight(titlePaint));
            }

            canvas.drawBitmap(bitmap, x, y, titlePaint);
        }
    }

    /**
     * 获取雷达图上各个点的坐标
     *
     * @param position 坐标位置（右上角为0，顺时针递增）
     * @return 坐标
     */
    private Point getPoint(int position) {
        return getPoint(position, 0, 1);
    }

    /**
     * 获取雷达图上各个点的坐标（包括维度标题与图标的坐标）
     *
     * @param position    坐标位置
     * @param radarMargin 雷达图与维度标题的间距
     * @param percent     覆盖区的的百分比
     * @return 坐标
     */
    private Point getPoint(int position, int radarMargin, float percent) {
        int x = 0;
        int y = 0;

        if (position == 0) {
            x = (int) (centerX + (radius + radarMargin) * Math.sin(radian) * percent);
            y = (int) (centerY - (radius + radarMargin) * Math.cos(radian) * percent);

        } else if (position == 1) {
            x = (int) (centerX + (radius + radarMargin) * Math.sin(radian / 2) * percent);
            y = (int) (centerY + (radius + radarMargin) * Math.cos(radian / 2) * percent);

        } else if (position == 2) {
            x = (int) (centerX - (radius + radarMargin) * Math.sin(radian / 2) * percent);
            y = (int) (centerY + (radius + radarMargin) * Math.cos(radian / 2) * percent);

        } else if (position == 3) {
            x = (int) (centerX - (radius + radarMargin) * Math.sin(radian) * percent);
            y = (int) (centerY - (radius + radarMargin) * Math.cos(radian) * percent);

        } else if (position == 4) {
            x = centerX;
            y = (int) (centerY - (radius + radarMargin) * percent);
        }

        return new Point(x, y);
    }

    /**
     * 获取文本的高度
     *
     * @param paint 文本绘制的画笔
     * @return 文本高度
     */
    private int getTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (int) (fontMetrics.descent - fontMetrics.ascent);
    }
}
