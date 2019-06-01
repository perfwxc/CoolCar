package com.wxc.coolcar.Environment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.wxc.coolcar.Chart.EnviScoreView;
import com.wxc.coolcar.R;


/**
 * Created by Supreme on 2018/7/20.
 */

public class Eetail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eetail_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//5.0 全透明实现
//getWindow.setStatusBarColor(Color.TRANSPARENT)
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        int[] flag = new int[6];
        String[] string = new String[]{
                "开窗通风",
                "必要时空调降温",
                "必要时空调升温",
                "注意保暖",
                "及时离开车辆",
                "检查车内是否有火源",
                "及时停车，注意光强干扰",
                "打开遮光前板",
                "若为其他车灯照射，可切换远近光提醒对方车辆",
                "若空调开放，请及时关闭",
                "光线较暗，注意安全",
                "打开汽车除湿模式",
                "注意保湿",
                "根据您当前的车内环境，建议您",
                "车载精灵为您提供最舒适的环境",
                "车内环境良好，祝您驾驶愉快！"
        };
//可以输入zhio
        int[] aflag = new int[string.length];
//	System.out.println(s.length);
        int count = 0;
        String[] six = new String[8];
        Intent dataIntent = getIntent();//接收传递的数据
        String ia = dataIntent.getStringExtra("ienvironmentTemperature");
        String ib = dataIntent.getStringExtra("iambientHumidity");
        String ic = dataIntent.getStringExtra("ismokeDensity");
        String id = dataIntent.getStringExtra("iCODensity");
        String ie = dataIntent.getStringExtra("iH2S");
        String if1 = dataIntent.getStringExtra("ialcoholConcentration");
        EnviScoreView view = findViewById(R.id.imark);
        view.setData(ia, ib, ic, id, ie, if1);
        TranslateAnimation showAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        showAnim.setDuration(1500);
        view.startAnimation(showAnim);
        view.setVisibility(View.VISIBLE);
        TextView tv1 = findViewById(R.id.dwd);
        TextView tv2 = findViewById(R.id.dxl);
        TextView tv3 = findViewById(R.id.dxy);
        TextView tv4 = findViewById(R.id.djj);
        TextView tv5 = findViewById(R.id.iwd);
        TextView tv6 = findViewById(R.id.ixl);
        TextView tv7 = findViewById(R.id.ixy);
        TextView tv8 = findViewById(R.id.ijj);
        TextView tv9 = findViewById(R.id.iqt);
        TextView tv10 = findViewById(R.id.dqt);
        TextView tip1 = findViewById(R.id.tip1);
        TextView tip2 = findViewById(R.id.tip2);
        TextView tip3 = findViewById(R.id.tip3);
        TextView tip4 = findViewById(R.id.tip4);
        TextView tip5 = findViewById(R.id.tip5);
        TextView tip6 = findViewById(R.id.tip6);
        TextView tip7 = findViewById(R.id.tip7);
        TextView tip8 = findViewById(R.id.tip8);
        TextView tip9 = findViewById(R.id.tip9);
        TextView tip10 = findViewById(R.id.tip10);
        Button button = findViewById(R.id.ebutton);

        float a1 = Float.parseFloat(ia);
        float a2 = Float.parseFloat(ib);
        float b = Float.parseFloat(ic);
        float c = Float.parseFloat(id);
        float d = Float.parseFloat(ie);
        float e = Float.parseFloat(if1);


        if (a1 < 22) flag[0] = -1;
        if (a1 > 28) flag[0] = 1;
        if (a2 < 30) flag[1] = -1;
        if (a2 > 75) flag[1] = 1;
        if (b > 4000) flag[2] = 1;
        if (c > 4000) flag[3] = 1;
        if (d > 4000) flag[4] = 1;
        if (e <= 15) flag[5] = -1;
        if (e > 4000) flag[5] = 1;
        for (int i = 0; i <= 5; i++) {
            if (flag[i] == 0) continue;
            if (flag[i] == 1) {
                switch (i) {
                    case 0:
                        aflag[0] = 1;
                        aflag[1] = 1;
                        break;
                    case 1:
                        aflag[11] = 1;
                        break;
                    case 2:
                        aflag[0] = 1;
                        aflag[5] = 1;
                        break;
                    case 3:
                        aflag[0] = 1;
                        aflag[9] = 1;
                        break;
                    case 4:
                        aflag[0] = 1;
                        aflag[9] = 1;
                        break;
                    case 5:
                        aflag[6] = 1;
                        aflag[7] = 1;
                        aflag[8] = 1;
                        break;
                    default:
                        break;
                }
            }

            if (flag[i] == -1) {
                switch (i) {
                    case 0:
                        aflag[2] = 1;
                        aflag[3] = 1;
                        break;
                    case 1:
                        aflag[12] = 1;
                        break;
                    case 6:
                        aflag[10] = 1;
                        break;
                    default:
                        break;
                }
            }
        }
        for (int i = 0; i < aflag.length; i++) {
            if (aflag[i] != 0) {
                count++;
            }
        }
        if (count == 0) {
            tip1.setText(string[15]);
        } else {
            tip2.setText(string[13]);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < aflag.length; i++) {
                if (aflag[i] == 1) {
                    sb.append(string[i]);

                }
            }

            tip3.setText(sb);
            tip4.setText(string[14]);
        }


        String iaa = ia + "<small><small> ℃</small></small><br/><small><small><small><small><small><br/></small></small></small></small></small>" + ib + "<small><small>  %RH</small></small>";
        String ibb = ic + "<small><small> ppm</small></small>";
        String icc = if1 + "<small><small> </small></small>";
        String idd = ie + "<small><small> g/L</small></small>";
        String iff = id + "<small><small> ppm</small></small>";

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv1.setText(Html.fromHtml(iaa));
        tv2.setText(Html.fromHtml(ibb));
        tv3.setText(Html.fromHtml(icc));
        tv4.setText(Html.fromHtml(idd));
        tv10.setText(Html.fromHtml(iff));
        tv5.setText("\n" + "   温湿度");
        tv6.setText("\n" + "   烟雾浓度");
        tv7.setText("\n" + "   光照强度");
        tv8.setText("\n" + "   一氧化碳浓度");
        tv9.setText("\n" + "   硫化氢浓度");

    }
}


