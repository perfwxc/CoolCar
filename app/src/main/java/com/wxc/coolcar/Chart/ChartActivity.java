package com.wxc.coolcar.Chart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wxc.coolcar.Health.MActivity;
import com.wxc.coolcar.R;
import com.wxc.coolcar.User.User;
import com.wxc.coolcar.Util.LineChartManager;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.githang.statusbar.StatusBarCompat.setStatusBarColor;
import static java.lang.Thread.sleep;


public class ChartActivity extends AppCompatActivity {

    private LineChart lineChart1, lineChart2, lineChart3;
    private LineData lineData;
    public String date;
    public ArrayList<String> iaa;
    public ArrayList<String> ibb;
    public ArrayList<String> icc;
    public ArrayList<String> idd;
    public static ArrayList<String> ABCD = new ArrayList<>();
    private static final String TAG = "ChartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_layout);
        setStatusBarColor(this, 2000, true);        //设置折线颜色

        TextView tv = findViewById(R.id.webstart);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ChartActivity.this, Web.class);
                startActivity(intent2);
            }
        });
        Intent dataIntent = getIntent();//接收传递的数据

        Bundle awd =  dataIntent.getExtras();
        Bundle bxt =  dataIntent.getExtras();
        Bundle cxyh =  dataIntent.getExtras();
        Bundle dxyl =  dataIntent.getExtras();

        iaa =  awd.getStringArrayList("wd");
        ibb =  bxt.getStringArrayList("xt");
        icc =  cxyh.getStringArrayList("xyh");
        idd =  dxyl.getStringArrayList("xyl");


        initChart1();           //体温折线图
        initChart2();           //血压折线图
        initChart3();           //心率折线图

    }


    public android.os.Handler han = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    break;

            }
        }

    };



    private void initChart1() {
        lineChart1 = (LineChart) findViewById(R.id.spread_line_chart);
        //设置图表的描述
        lineChart1.setDescription("体温分布图");
        //设置x轴的数据
        int numX =iaa.size();
        //设置y轴的数据
        YAxis leftAxis = lineChart1.getAxisLeft();
        LimitLine yLimitLine = new LimitLine(37f,"高体温分界线");
        yLimitLine.setLineColor(Color.RED);
        yLimitLine.setLineWidth(1f);
        yLimitLine.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
        yLimitLine.setTextSize(10f);
        leftAxis.addLimitLine(yLimitLine);
        leftAxis.setStartAtZero(false);


        float[] datas1 = new float[iaa.size()];            //线程休眠一秒为传输数据提供时间
        for (int i = 0; i < iaa.size(); i++) {
            datas1[i]= Float.parseFloat(iaa.get(i));
        };//数据
        String[] datas2 = new String[iaa.size()];
        for (int i = 0; i < iaa.size(); i++)
            datas2[i]= "第"+(i+1)+"次";


        //设置折线的名称
        LineChartManager.setLineName("体温");
        //设置第二条折线y轴的数据
         //创建两条折线的图表
        lineData = LineChartManager.initSingleLineChart(this, lineChart1, numX, datas1,datas2);
        LineChartManager.initDataStyle(lineChart1, lineData, this);
    }


    private void initChart2() {
        lineChart2 = (LineChart) findViewById(R.id.line_chart);
        //设置图表的描述
        lineChart2.setDescription("血压分布图");
        //设置x轴的数据
        int numX = iaa.size();
        //设置y轴的数据
        float[] datas1 = new float[iaa.size()];
        for (int i = 0; i <iaa.size(); i++) {
            datas1[i]= Float.parseFloat(icc.get(i));
        };//数据
        YAxis leftAxis = lineChart2.getAxisLeft();
        LimitLine yLimitLine1 = new LimitLine(140f,"高血压分界线");
        yLimitLine1.setLineColor(Color.RED);
        yLimitLine1.setLineWidth(1f);
        yLimitLine1.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
        yLimitLine1.setTextSize(10f);
        LimitLine yLimitLine2 = new LimitLine(60f,"60mmHg分界线");
        yLimitLine2.setLineColor(Color.RED);
        yLimitLine2.setLineWidth(1f);
        yLimitLine2.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
        yLimitLine2.setTextSize(10f);
        LimitLine yLimitLine3 = new LimitLine(90f,"90mmHg分界线");
        yLimitLine3.setLineColor(Color.GREEN);
        yLimitLine3.setLineWidth(1f);
        yLimitLine3.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
        yLimitLine3.setTextSize(10f);
        leftAxis.addLimitLine(yLimitLine2);
        leftAxis.addLimitLine(yLimitLine1);
        leftAxis.addLimitLine(yLimitLine3);
        leftAxis.setStartAtZero(false);

        float[] datas2 = new float[iaa.size()];
        for (int i = 0; i < iaa.size(); i++)
            datas2[i]= Float.parseFloat(idd.get(i));
        for(int i=0;i<iaa.size();i++)
        {
            int flag=0;
            float a;
            a=datas2[i];
            if (a > 28 && a < 29)
                flag=8;
            if (a > 29 && a < 30)
                flag=7;
            if (a > 30 && a < 31)
                flag=6;
            if (a > 31 && a < 32)
                flag=5;
            if (a > 32 && a < 33)
                flag=4;
            if (a > 33 && a < 34)
                flag=3;
            if (a > 34 && a < 34.5)
                flag=21;
            if (a >= 34.5 && a < 35)
                flag=11;
            if (a >= 35 && a < 35.5)
                flag=31;
            if (a >= 35.5 && a < 36)
                flag=41;
            switch (flag) {
                case 41:
                    datas2[i] = Float.parseFloat("36.8");
                    break;
                case 31:
                    datas2[i] = Float.parseFloat("36.7");
                    break;
                case 11:
                    datas2[i] = Float.parseFloat("36.6");
                    break;
                case 21:
                    datas2[i]= Float.parseFloat("36.5");
                    break;
                case 2:
                    datas2[i] = Float.parseFloat("36.3");
                    break;
                case 3:
                    datas2[i] = Float.parseFloat("36.2");
                    break;
                case 4:
                    datas2[i] = Float.parseFloat("36.5");
                    break;
                case 5:
                    datas2[i] = Float.parseFloat("36.4");
                    break;
                case 6:
                    datas2[i] = Float.parseFloat("36.3");
                    break;
                case 7:
                    datas2[i] = Float.parseFloat("36.2");
                    break;
                case 8:
                    datas2[i] = Float.parseFloat("36.1");
                    break;
            }

        }

        String[] datas3 = new String[iaa.size()];
        for (int i = 0; i < iaa.size(); i++)
            datas3[i]= "第"+(i+1)+"次";
        //数据
        //设置折线的名称
        LineChartManager.setLineName("高血压");
        //设置第二条折线y轴的数据
        LineChartManager.setLineName1("低血压");
        //创建两条折线的图表
        lineData = LineChartManager.initDoubleLineChart(this, lineChart1, numX, datas1, datas2 ,datas3);
        LineChartManager.initDataStyle(lineChart2, lineData, this);}

        private void initChart3() {
            lineChart3 = (LineChart) findViewById(R.id.line_chart2);
            //设置图表的描述
            lineChart3.setDescription("心率分布图");
            //设置x轴的数据
            int numX = iaa.size();
            //设置y轴的数据
            float[] datas1 = new float[iaa.size()];
            for (int i = 0; i < iaa.size(); i++) {
                datas1[i]= Float.parseFloat(ibb.get(i));
            };///数据
            String[] datas2 = new String[iaa.size()];
            for (int i = 0; i < iaa.size(); i++)
                datas2[i]= "第"+(i+1)+"次";
            YAxis leftAxis = lineChart3.getAxisLeft();
            leftAxis.setStartAtZero(false);
            //设置折线的名称
            LineChartManager.setLineName("心率");
            //设置第二条折线y轴的数据
                   //创建两条折线的图表
            lineData = LineChartManager.initSingleLineChart(this, lineChart1, numX, datas1,datas2);
            LineChartManager.initDataStyle(lineChart3, lineData, this);
        }


    }





















