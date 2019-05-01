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


public class EnviScoreView extends View {

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
    private String[] titles = {"一氧化碳浓度", "硫化氢浓度", "光照强度", "烟雾浓度", "温湿度"};
    //各维度图标
    private int[] icons = {R.mipmap.co, R.mipmap.h2s, R.mipmap.gz,
            R.mipmap.yw, R.mipmap.ic_identity};
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

    public EnviScoreView(Context context) {
        this(context, null);
    }

    public void setData (String aa , String bb , String cc , String dd , String ee ,String ff )
    {
        float a = Float.parseFloat(aa);
        float a2 = Float.parseFloat(bb);
        float b = Float.parseFloat(cc);
        float c = Float.parseFloat(dd);
        float d = Float.parseFloat(ee);
        float e = Float.parseFloat(ff);
        int A1 = 0;//温度评分

        if(a == 25) A1 =20;
        if(a == 24 || a == 26) A1 =19;
        if(a == 23 || a == 27) A1 =18;
        if(a == 22 || a == 28) A1 =17;
        if(a == 21 || a == 29) A1 =16;
        if(a == 20 || a == 30) A1=15;
        if(a == 19 || a == 31) A1 =14;
        if(a == 18 || a == 32) A1 =13;
        if(a == 17 || a == 33) A1 =12;
        if(a == 16 || a == 34) A1 =11;
        if(a == 15 || a == 35) A1 =10;
        if(a == 14 || a == 36) A1 =9;
        if(a == 13 || a == 37) A1 =8;
        if(a == 12 || a == 38) A1 =7;
        if(a == 11 || a == 39) A1 =6;
        if(a == 10 || a == 40) A1 =5;
        if(a == 9 || a == 41) A1 =4;
        if(a == 8 || a == 42) A1 =3;
        if(a == 7 || a == 43) A1 =2;
        if(a == 6 || a == 44) A1 =1;
        if(a < 6 || a > 44) A1 = 0;

        int A2 = 0;
        int z = (int)a2;
        if(z == 55) A2 = 20;
        if(z == 54 || z == 56) A2 = 19;
        if(z == 53 || z == 57) A2 = 18;
        if(z == 52 || z == 58) A2 = 17;
        if(z == 51 || z == 59) A2 = 16;
        if(z == 50 || z == 60) A2 = 15;
        if(z == 49 || z == 61) A2 = 14;
        if(z == 48 || z == 62) A2 = 13;
        if(z == 47 || z == 63) A2 = 12;
        if(z == 46 || z == 64) A2 = 11;
        if(z == 45 || z == 65) A2 = 10;
        if(z == 44 || z == 66) A2 = 9;
        if(z == 43 || z == 67) A2 = 8;
        if(z == 42 || z == 68) A2 = 7;
        if(z == 41 || z == 69) A2 = 6;
        if(z == 40 || z == 70) A2 = 5;
        if(z == 39 || z == 71) A2 = 4;
        if(z == 38 || z == 72) A2 = 3;
        if(z == 37 || z == 73) A2 = 2;
        if(z == 36 || z == 74) A2 = 1;
        if(z<36 || z >74)  A2 = 0;
        int A = A1>A2?A2:A1;
        data[4] = A;

        int B = 0;
        if(b >= 0 && b < 1000) B = 20;
        if(b >= 1000 && b < 2000) B = 18;
        if(b >= 2000 && b < 3000) B = 16;
        if(b >= 3000 && b < 4000) B = 14;
        if(b >= 4000 && b < 5000) B = 10;
        if(b >= 5000 && b < 6000) B = 6;
        if(b >= 3000) B = 0;
        data[3] = B;

        int C = 0;
        if(c >= 0 && c < 1000) 	C = 20;
        if(c >= 1000 && c < 2000) C = 18;
        if(c >= 2000 && c < 3000) C = 16;
        if(c >= 3000 && c < 4000) C = 14;
        if(c >= 4000 && c < 5000) C = 10;
        if(c >= 5000 && c < 6000) C = 6;
        if(c >= 3000) C = 0;
        data[0] = C;


        int D = 0;
        if(d >= 0 && d < 1000) D = 20;
        if(d >= 1000 && d < 2000) D = 18;
        if(d >= 2000 && d < 3000) D = 16;
        if(d >= 3000 && d < 4000) D = 14;
        if(d >= 4000 && d < 5000) D = 10;
        if(d >= 5000 && d < 6000) D = 6;
        if(d >= 3000) D = 0;
        data[1] = D;

        int E = 0;
        if(e >= 30 && e < 1000) E = 20;
        if((e >= 28 && e < 30) || (e >=1000 && e < 1500)) E = 18;
        if((e >= 26 && e < 28) || (e >=1500 && e < 2000)) E = 16;
        if((e >= 24 && e < 26) || (e >=2000 && e < 2500)) E = 14;
        if((e >= 22 && e < 24) || (e >=2500 && e < 3000)) E = 12;
        if((e >= 20 && e < 22) || (e >=3000 && e < 3500)) E = 10;
        if((e >= 18 && e < 20) || (e >=3500 && e < 4000)) E = 8;
        if((e >= 16 && e < 18) || (e >=4000 && e < 4500)) E = 6;
        if((e >= 14 && e < 16) || (e >=4500 && e < 5000)) E = 4;
        if((e >= 12 && e < 14) || (e >=5000 && e < 5500)) E = 2;
        if((e >= 10 && e < 12) || (e >=5500 && e < 6000)) E = 1;
        if(e <10 || e >= 6000) E = 0;
        data[2] = E;
    }

    public EnviScoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EnviScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
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
