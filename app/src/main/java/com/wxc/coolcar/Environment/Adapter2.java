package com.wxc.coolcar.Environment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxc.coolcar.Health.Adapter;
import com.wxc.coolcar.R;
import com.wxc.coolcar.User.User;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2018/5/24.
 */

public class Adapter2 extends BaseAdapter {
    public Context con;
    public List<User> list;
    public LayoutInflater inflater;
    private Adapter.MyClickListener mListener;
    private Adapter.MyClickListener nListener;

    public Adapter2(Context context, List<User> user,
                    Adapter.MyClickListener listener, Adapter.MyClickListener listener1) {
        this.con = context;
        this.list = user;
        inflater = LayoutInflater.from(con);
        mListener = listener;
        nListener = listener1;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item2, null);
        TextView tv1 = (TextView) view.findViewById(R.id.tv1);
        TextView tv2 = (TextView) view.findViewById(R.id.tv2);
        TextView tv3 = (TextView) view.findViewById(R.id.tv3);
        TextView tv4 = (TextView) view.findViewById(R.id.tv4);
        TextView tv5 = (TextView) view.findViewById(R.id.tv5);
        TextView tv6 = (TextView) view.findViewById(R.id.tv6);
        TextView img = (TextView) view.findViewById(R.id.img);
        ImageView img1 = (ImageView) view.findViewById(R.id.imageView);
        int ii1 = list.get(position).getTimeStamp1().indexOf('[') + 1;
        int ii2 = list.get(position).getTimeStamp1().length();
        String tt1 = list.get(position).getTimeStamp1().substring(ii1, ii2);
        String tt2 = list.get(position).getTimeStamp2().substring(0, 8);
        String tt24 = list.get(position).getTimeStamp2().substring(2, 8);
        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.getDefault());
        String date = sdf.format(new java.util.Date());
        int time1 = Integer.parseInt(date);
        int time2 = Integer.parseInt(tt2.substring(0, 2));
        if (time1 > 12)
            time2 += 12;
        String tth = String.valueOf(time2);
        String tt3 = "<small><small>" + tt1 + "</small></small><br/>" + "<strong>" + tth + tt24 + "</strong>";

        img.setText(Html.fromHtml(tt3));
        int start = list.get(position).getTimeStamp1().indexOf('[');
        String gzz = list.get(position).getTimeStamp1().substring(0, start);
        int gz = Integer.parseInt(gzz);
        tv1.setText("\n   温度:    " + list.get(position).getEnvironmentTemperature() + "℃"); //在这里为ListView更新UI
        tv2.setText("   湿度:    " + list.get(position).getAmbientHumidity() + "%RH");
        tv3.setText("   烟雾浓度:    " + list.get(position).getSmokeDensity() + "ppm");
        if (gz <= 15)
            tv4.setText("   光照强度:    " + "过弱");              //光照强度传感器返回的是高低电平，故只有正常与过强两种情况
        else if (gz < 1000)
            tv4.setText("   光照强度:    " + "正常");
        else
            tv4.setText("   光照强度:    " + "过强");
        tv5.setText("   CO浓度:    " + list.get(position).getCODensity() + "ppm");
        tv6.setText("   H2S浓度:    " + list.get(position).getH2S() + "ppm");
        int k = 5;
        double environmentTemperature = Double.parseDouble(list.get(position).getEnvironmentTemperature());
        double ambientHumidity = Double.parseDouble(list.get(position).getAmbientHumidity());
        double smokeDensity = Double.parseDouble(list.get(position).getSmokeDensity());
        double CODensity = Double.parseDouble(list.get(position).getCODensity());
        int Lux = Integer.parseInt(list.get(position).getLux());        //将数据库中String类型数据转换格式进行分析
        if (45 < ambientHumidity && ambientHumidity < 70) {
            k--;
        }
        if (5 < environmentTemperature && environmentTemperature < 40) {
            k--;
        }
        if (smokeDensity < 5000) {
            k--;
        }

        if (CODensity < 2000) {
            k--;
        }

        if (Lux > 15 && Lux < 1000) {
            k--;
        }

        if (k == 0)
            img1.setImageResource(R.drawable.i1);   //为条目右侧的ImageView设置分级标识
        if (k == 1)
            img1.setImageResource(R.drawable.i2);
        if (k == 2)
            img1.setImageResource(R.drawable.i3);
        if (k == 3)
            img1.setImageResource(R.drawable.i4);
        if (k == 4)
            img1.setImageResource(R.drawable.i5);

        img1.setOnClickListener(mListener);         //设置分级的点击事件
        img1.setTag(position);
        img.setOnClickListener(nListener);
        img.setTag(position);
        return view;
    }

    ;

    public static abstract class MyClickListener implements View.OnClickListener {
        /**
         * 基类的onClick方法
         */
        @Override
        public void onClick(View v) {
            myOnClick((Integer) v.getTag(), v);
        }

        public abstract void myOnClick(int position, View v);
    }


}

