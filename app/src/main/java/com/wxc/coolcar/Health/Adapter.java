package com.wxc.coolcar.Health;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v4.util.DebugUtils;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.tts.client.SpeechSynthesizer;
import com.wxc.coolcar.Environment.NActivity;
import com.wxc.coolcar.R;
import com.wxc.coolcar.User.User;
import com.wxc.coolcar.User.UserAdapter;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Administrator on 2018/5/23.
 */

public class Adapter extends BaseAdapter {
    public Context con;
    public List<User> list;
    public LayoutInflater inflater;
    private MyClickListener mListener;
    private MyClickListener nListener;
    private static int fly =0;
    private static int flag=0;
    private static int bb=1;



    public Adapter(Context context, List<User> user,  MyClickListener listener, MyClickListener listener1)
    {
            this.con = context;
            this.list = user;
            inflater = LayoutInflater.from(con);
            mListener = listener;
            nListener = listener1;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item, null);
        TextView tv1 = (TextView) view.findViewById(R.id.tv1);
        TextView tv2 = (TextView) view.findViewById(R.id.tv2);
        TextView tv3 = (TextView) view.findViewById(R.id.tv3);
        TextView tv4 = (TextView) view.findViewById(R.id.tv4);
        TextView tv5 = (TextView) view.findViewById(R.id.tv5);
        TextView tv6 = (TextView) view.findViewById(R.id.tv6);
        TextView img = (TextView) view.findViewById(R.id.img);
        ImageView img1= (ImageView) view.findViewById(R.id.imageView);

        double a = Double.parseDouble(list.get(position).getWD());
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
                a = 36.8;
                break;
            case 31:
                a = 36.7;
                break;
            case 11:
                a = 36.6;
                break;
            case 21:
                a = 36.5;
                break;
            case 2:
                a = 36.3;
                break;
            case 3:
                a = 36.6;
                break;
            case 4:
                a = 36.5;
                break;
            case 5:
                a = 36.4;
                break;
            case 6:
                a = 36.3;
                break;
            case 7:
                a = 36.2;
                break;
            case 8:
                a = 36.1;
                break;
            default:
                a=Double.parseDouble(list.get(position).getWD());
        }

        int ii1= list.get(position).getT1().indexOf('[')+1;
        int ii2= list.get(position).getT1().length();
        String tt1 = list.get(position).getT1().substring(ii1,ii2);
        String tt2 = list.get(position).getT2().substring(0,8);
        String tt24 = list.get(position).getT2().substring(2,8);
        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.getDefault());
        String date = sdf.format(new java.util.Date());
        int time1 = Integer.parseInt(date);
        int time2= Integer.parseInt(tt2.substring(0,2));
        if(time1>12)
            time2+=12;
        String tth = String.valueOf(time2);
        String tt3 =  "<small><small>"+tt1+"</small></small><br/>"+"<strong>"+tth+tt24+"</strong>";
        double e = new BigDecimal(Double.parseDouble(list.get(position).getWZ())/10000).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        int b = Integer.parseInt(list.get(position).getXT());
        int c = Integer.parseInt(list.get(position).getXYH());
        int d = Integer.parseInt(list.get(position).getXYL());
        tv1.setText("\n   体温:    " + a + "℃");
        tv2.setText("   心率:    " + list.get(position).getXT() + "次/分");
        tv3.setText("   高血压:    " + list.get(position).getXYH() + "mmHg");
        tv4.setText("   低血压:    " + list.get(position).getXYL() + "mmHg");
        tv5.setText("   酒精浓度:    " +e+ "g/L");
        tv6.setText("   血氧饱和度:    " + list.get(position).getXY() + "%");

        img.setText(Html.fromHtml(tt3));


        if (((a >= 36.0 && a <= 37.0) && (b >= 60 && b <= 100) && (c >= 90 && c <= 140) && (d >= 60 && d <= 90) && (e < 0.800)))
        {  img1.setImageResource(R.drawable.i1);
        }
        if (((a < 36.0 || a > 37.0) && (b >= 60 && b <= 100) && (c >= 90 && c <= 140) && (d >= 60 && d <= 90) && (e < 0.800))
                || ((a >= 36.0 && a <= 37.0) && (b < 60 || b > 100) && (c >= 90 && c <= 140) && (d >= 60 && d <= 90) && (e < 0.800))
                || ((a >= 36.0 && a <= 37.0) && (b >= 60 && b <= 100) && (c < 90 || c > 140) && (d >= 60 && d <= 90) && (e < 0.800))
                || ((a >= 36.0 && a <= 37.0) && (b >= 60 && b <= 100) && (c >= 90 && c <= 140) && (d < 60 || d > 90) && (e < 0.800))
                || ((a >= 36.0 && a <= 37.0) && (b >= 60 && b <= 100) && (c >= 90 && c <= 140) && (d >= 60 && d <= 90) && (e >= 0.800))
                )
            img1.setImageResource(R.drawable.i2);
        if (((a < 36.0 || a > 37.0) && (b < 60 || b > 100) && (c >= 90 && c <= 140) && (d >= 60 && d <= 90) && (e < 0.800))
                || ((a < 36.0 || a > 37.0) && (b >= 60 && b <= 100) && (c >= 90 && c <= 140) && (d >= 60 && d <= 90) && (e < 0.800))
                || ((a < 36.0 || a > 37.0) && (b >= 60 && b <= 100) && (c < 90 || c > 140) && (d < 60 || d > 90) && (e < 0.800))
                || ((a < 36.0 || a > 37.0) && (b >= 60 && b <= 100) && (c >= 90 && c <= 140) && (d >= 60 && d <= 90) && (e > 0.800))

                || ((a >= 36.0 && a <= 37.0) && (b < 60 || b > 100) && (c >= 90 && c <= 140) && (d >= 60 && d <= 90) && (e < 0.800))
                || ((a >= 36.0 && a <= 37.0) && (b < 60 || b > 100) && (c >= 90 && c <= 140) && (d >= 60 && d <= 90) && (e < 0.800))
                || ((a >= 36.0 && a <= 37.0) && (b < 60 || b > 100) && (c >= 90 && c <= 140) && (d >= 60 && d <= 90) && (e > 0.800))

                || ((a >= 36.0 && a <= 37.0) && (b >= 60 && b <= 100) && (c < 90 || c > 140) && (d < 60 || d > 90) && (e < 0.800))
                || ((a >= 36.0 && a <= 37.0) && (b >= 60 && b <= 100) && (c < 90 || c > 140) && (d >= 60 && d <= 90) && (e > 0.800))
                || ((a >= 36.0 && a <= 37.0) && (b >= 60 && b <= 100) && (c >= 90 && c <= 140) && (d < 60 || d > 90) && (e > 0.800))
                )
            img1.setImageResource(R.drawable.i3);
        if (((a >= 36.0 && a <= 37.0) && (b >= 60 && b <= 100) && (c < 90 || c > 140) && (d < 60 || d > 90) && (e > 0.800))
                || ((a >= 36.0 && a <= 37.0) && (b < 60 || b > 100) && (c >= 90 && c <= 140) && (d < 60 || d > 90) && (e > 0.800))
                || ((a >= 36.0 && a <= 37.0) && (b < 60 || b > 100) && (c < 90 || c > 140) && (d >= 60 && d <= 90) && (e > 0.800))
                || ((a >= 36.0 && a <= 37.0) && (b < 60 || b > 100) && (c < 90 || c > 140) && (d < 60 || d > 90) && (e < 0.800))

                || ((a < 36.0 || a > 37.0) && (b >= 60 && b <= 100) && (c >= 90 && c <= 140) && (d < 60 || d > 90) && (e < 0.800))
                || ((a < 36.0 || a > 37.0) && (b >= 60 && b <= 100) && (c < 90 || c > 140) && (d >= 60 && d <= 90) && (e > 0.800))
                || ((a < 36.0 || a > 37.0) && (b >= 60 && b <= 100) && (c < 90 || c > 140) && (d < 60 || d > 90) && (e < 0.800))

                || ((a < 36.0 || a > 37.0) && (b < 60 || b > 100) && (c >= 90 && c <= 140) && (d >= 60 && d <= 90) && (e > 0.800))
                || ((a < 36.0 || a > 37.0) && (b < 60 || b > 100) && (c >= 90 && c <= 140) && (d < 60 || d > 90) && (e < 0.800))
                || ((a < 36.0 || a > 37.0) && (b < 60 || b > 100) && (c < 90 || c > 140) && (d >= 60 && d <= 90) && (e < 0.800))
                )
            img1.setImageResource(R.drawable.i4);
        if (((a < 36.0 || a > 37.0) && (b < 60 || b > 100) && (c < 90 || c > 140) && (d < 60 || d > 90) && (e < 0.800))
                || ((a < 36.0 || a > 37.0) && (b < 60 || b > 100) && (c < 90 || c > 140) && (d >= 60 && d <= 90) && (e > 0.800))
                || ((a < 36.0 || a > 37.0) && (b < 60 || b > 100) && (c >= 90 && c <= 140) && (d < 60 || d > 90) && (e > 0.800))
                || ((a < 36.0 || a > 37.0) && (b >= 60 && b <= 100) && (c < 90 || c > 140) && (d < 60 || d > 90) && (e > 0.800))
                || ((a >= 36.0 && a <= 37.0) && (b < 60 || b > 100) && (c < 90 || c > 140) && (d < 60 || d > 90) && (e > 0.800))
                )
        img1.setImageResource(R.drawable.i5);       //为条目右侧的ImageView设置分级标识
        img1.setOnClickListener(mListener);             //设置分级的点击事件
        img1.setTag(position);
        img .setOnClickListener(nListener);
        img .setTag(position);
        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public static abstract class MyClickListener implements View.OnClickListener
    {
        /**
         * 基类的onClick方法
         */
        @Override
        public void onClick(View v)
        {
            myOnClick((Integer) v.getTag(), v);
        }
        public abstract void myOnClick(int position, View v);
    }


    }


