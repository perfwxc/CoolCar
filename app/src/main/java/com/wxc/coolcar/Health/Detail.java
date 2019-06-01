package com.wxc.coolcar.Health;

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

import com.wxc.coolcar.Chart.CreditScoreView;
import com.wxc.coolcar.R;

/**
 * Created by Supreme on 2018/7/20.
 */

public class Detail extends AppCompatActivity {
    private static int fly = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
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

        String[] s = new String[]{
                "少食多餐，避免暴饮暴食。",
                "清淡饮食，避免辛辣刺激，油腻食物。 ",
                "戒烟酒 。",
                "注意休息，避免工作劳累。",
                "多吃鲜果蔬菜 。",
                "日常注意补充水分。"
        };

        Intent dataIntent = getIntent();//接收传递的数据
        String ia = dataIntent.getStringExtra("iUserTemperature");
        String ib = dataIntent.getStringExtra("iHeartRate");
        String ic1 = dataIntent.getStringExtra("iHighBloodPressure");
        String ic2 = dataIntent.getStringExtra("iLowBloodPressure");
        String id = dataIntent.getStringExtra("iSaO2");
        String ie = dataIntent.getStringExtra("iLux");
        CreditScoreView view = findViewById(R.id.imark);
        view.setData(ia, ib, ic1, ic2, id, ie);
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
        Button button = findViewById(R.id.ibutton);

        float a = Float.parseFloat(ia);
        float b = Float.parseFloat(ib);
        float c1 = Float.parseFloat(ic1);
        float c2 = Float.parseFloat(ic2);
        float d = Float.parseFloat(id);
        float e = Float.parseFloat(ie);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (a < 36.3) {
            tip1.setText("单纯体温偏低，多属风寒表征，可服用少量药物进行治疗，避免着凉。");
            flag[1] = 1;
        } else {
            tip1.setVisibility(View.INVISIBLE);
            tip1.getLayoutParams().height = 0;
        }
        if (a > 37.2) {
            tip2.setText("单纯体温偏高，多为热血症，可用犀角地黄治疗，如果为发烧，请及时就医。");
            flag[1] = 1;
            flag[4] = 1;
            flag[5] = 1;
        } else {
            tip2.setVisibility(View.INVISIBLE);
            tip2.getLayoutParams().height = 0;
        }

        if (b < 60) {
            tip3.setText("如果有高血脂、高血粘、贫血等要积极治疗。");
            flag[1] = 1;
            flag[2] = 1;
            flag[3] = 1;
        } else {
            tip3.setVisibility(View.INVISIBLE);
            tip3.getLayoutParams().height = 0;
        }

        if (b >= 90) {
            tip4.setText("可以通过练书法，举哑铃等运动修生养性，降低心率。");
            flag[0] = 1;
            flag[1] = 1;
        } else {
            tip4.setVisibility(View.INVISIBLE);
            tip4.getLayoutParams().height = 0;
        }
        if (c1 >= 130 || c2 >= 85) {
            tip5.setText("提倡素食,控制盐的摄入，稳定血压,尽量饮用硬水，泉水，天然矿泉水等。");
            flag[0] = 1;
            flag[1] = 1;
            flag[2] = 1;
            flag[4] = 1;
        } else {
            tip5.setVisibility(View.INVISIBLE);
            tip5.getLayoutParams().height = 0;
        }

        if (c1 <= 100 || c2 <= 70) {
            tip6.setText("多吃蛋白质丰富的食物，建议摄入高营养易消化的富含维生素的饮食，适当引用咖啡，可可，浓茶，提高中枢神经的兴奋性，同时适当参加运动有助于改善心肺功能，提高血压。");
        } else {
            tip6.setVisibility(View.INVISIBLE);
            tip6.getLayoutParams().height = 0;
        }


        if (d <= 94) {
            tip7.setText("宜食用流质食物，例如豆腐，梨，核桃等,如果过低，建议可采用氧疗等系统系统治疗。");
            flag[1] = 1;
        } else {
            tip7.setVisibility(View.INVISIBLE);
            tip7.getLayoutParams().height = 0;
        }

        if (e >= 20) {
            tip8.setText("使自身发汗，宜多吃碳水化合物，和富含维生素的食物,尽量饮用茶水和热汤，甜点等进行醒酒。");
        } else {
            tip8.setVisibility(View.INVISIBLE);
            tip8.getLayoutParams().height = 0;
        }

        for (int i = 0; i <= 5; i++) {
            if (flag[i] == 1) {
                tip9.setText(s[i]);
            }
        }
        tip10.setText("祝您身体健康，车载精灵持续为您保驾护航！");


        String iaa1 = ia + "<small><small> ℃</small></small>";
        String ibb = ib + "<small><small> bpm</small></small>";
        String icc = ic1 + "<small><small> mmHg</small></small><br/>" + ic2 + "<small><small>  mmHg</small></small>";
        String idd = id + "<small><small> %</small></small>";
        String iee = ie + "<small><small> g/L</small></small>";

        tv1.setText(Html.fromHtml(iaa1));
        tv2.setText(Html.fromHtml(ibb));
        tv3.setText(Html.fromHtml(icc));
        tv4.setText(Html.fromHtml(iee));
        tv10.setText(Html.fromHtml(idd));
        tv5.setText("\n" + "   温度");
        tv6.setText("\n" + "   心率");
        tv7.setText("\n" + "   血压");
        tv8.setText("\n" + "   酒精浓度");
        tv9.setText("\n" + "   血氧饱和度");


    }
}



