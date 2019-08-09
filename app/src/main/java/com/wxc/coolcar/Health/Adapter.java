package com.wxc.coolcar.Health;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxc.coolcar.R;
import com.wxc.coolcar.User.User;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2018/5/23.
 */

public class Adapter extends BaseAdapter {
    private static int flag = 0;
    public Context con;
    public List<User> list;
    public LayoutInflater inflater;
    private MyClickListener mListener;
    private MyClickListener nListener;


    public Adapter(Context context, List<User> user, MyClickListener listener, MyClickListener listener1) {
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
        ImageView img1 = (ImageView) view.findViewById(R.id.imageView);

        double userTemperature = Double.parseDouble(list.get(position).getUserTemperature());
        if (userTemperature > 28 && userTemperature < 29)
            flag = 8;
        if (userTemperature > 29 && userTemperature < 30)
            flag = 7;
        if (userTemperature > 30 && userTemperature < 31)
            flag = 6;
        if (userTemperature > 31 && userTemperature < 32)
            flag = 5;
        if (userTemperature > 32 && userTemperature < 33)
            flag = 4;
        if (userTemperature > 33 && userTemperature < 34)
            flag = 3;
        if (userTemperature > 34 && userTemperature < 34.5)
            flag = 21;
        if (userTemperature >= 34.5 && userTemperature < 35)
            flag = 11;
        if (userTemperature >= 35 && userTemperature < 35.5)
            flag = 31;
        if (userTemperature >= 35.5 && userTemperature < 36)
            flag = 41;
        switch (flag) {
            case 41:
                userTemperature = 36.8;
                break;
            case 31:
                userTemperature = 36.7;
                break;
            case 11:
                userTemperature = 36.6;
                break;
            case 21:
                userTemperature = 36.5;
                break;
            case 2:
                userTemperature = 36.3;
                break;
            case 3:
                userTemperature = 36.6;
                break;
            case 4:
                userTemperature = 36.5;
                break;
            case 5:
                userTemperature = 36.4;
                break;
            case 6:
                userTemperature = 36.3;
                break;
            case 7:
                userTemperature = 36.2;
                break;
            case 8:
                userTemperature = 36.1;
                break;
            default:
                userTemperature = Double.parseDouble(list.get(position).getUserTemperature());
        }

        int timeStamp1 = list.get(position).getTimeStamp1().indexOf('[') + 1;
        int timeStamp2 = list.get(position).getTimeStamp1().length();
        String subTimeStamp1 = list.get(position).getTimeStamp1().substring(timeStamp1, timeStamp2);
        String subTimeStamp2 = list.get(position).getTimeStamp2().substring(0, 8);
        String subTime = list.get(position).getTimeStamp2().substring(2, 8);
        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.getDefault());
        String date = sdf.format(new java.util.Date());
        int time1 = Integer.parseInt(date);
        int time2 = Integer.parseInt(subTimeStamp2.substring(0, 2));
        if (time1 > 12)
            time2 += 12;
        String newTime = String.valueOf(time2);
        String finalTime = "<small><small>" + subTimeStamp1 + "</small></small><br/>" + "<strong>" + newTime + subTime + "</strong>";
        double alcoholConcentration = new BigDecimal(Double.parseDouble(list.get(position).getAlcoholConcentration()) / 10000).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        int heartRate = Integer.parseInt(list.get(position).getHeartRate());
        int highBloodPressure = Integer.parseInt(list.get(position).getHighBloodPressure());
        int lowBloodPressure = Integer.parseInt(list.get(position).getLowBloodPressure());
        tv1.setText("\n   体温:    " + userTemperature + "℃");
        tv2.setText("   心率:    " + list.get(position).getHeartRate() + "次/分");
        tv3.setText("   高血压:    " + list.get(position).getHighBloodPressure() + "mmHg");
        tv4.setText("   低血压:    " + list.get(position).getLowBloodPressure() + "mmHg");
        tv5.setText("   酒精浓度:    " + alcoholConcentration + "g/L");
        tv6.setText("   血氧饱和度:    " + list.get(position).getSaO2() + "%");

        img.setText(Html.fromHtml(finalTime));


        if (((userTemperature >= 36.0 && userTemperature <= 37.0) && (heartRate >= 60 && heartRate <= 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration < 0.800))) {
            img1.setImageResource(R.drawable.i1);
        }
        if (((userTemperature < 36.0 || userTemperature > 37.0) && (heartRate >= 60 && heartRate <= 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration < 0.800))
                || ((userTemperature >= 36.0 && userTemperature <= 37.0) && (heartRate < 60 || heartRate > 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration < 0.800))
                || ((userTemperature >= 36.0 && userTemperature <= 37.0) && (heartRate >= 60 && heartRate <= 100) && (highBloodPressure < 90 || highBloodPressure > 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration < 0.800))
                || ((userTemperature >= 36.0 && userTemperature <= 37.0) && (heartRate >= 60 && heartRate <= 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure < 60 || lowBloodPressure > 90) && (alcoholConcentration < 0.800))
                || ((userTemperature >= 36.0 && userTemperature <= 37.0) && (heartRate >= 60 && heartRate <= 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration >= 0.800))
                )
            img1.setImageResource(R.drawable.i2);
        if (((userTemperature < 36.0 || userTemperature > 37.0) && (heartRate < 60 || heartRate > 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration < 0.800))
                || ((userTemperature < 36.0 || userTemperature > 37.0) && (heartRate >= 60 && heartRate <= 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration < 0.800))
                || ((userTemperature < 36.0 || userTemperature > 37.0) && (heartRate >= 60 && heartRate <= 100) && (highBloodPressure < 90 || highBloodPressure > 140) && (lowBloodPressure < 60 || lowBloodPressure > 90) && (alcoholConcentration < 0.800))
                || ((userTemperature < 36.0 || userTemperature > 37.0) && (heartRate >= 60 && heartRate <= 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration > 0.800))

                || ((userTemperature >= 36.0 && userTemperature <= 37.0) && (heartRate < 60 || heartRate > 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration < 0.800))
                || ((userTemperature >= 36.0 && userTemperature <= 37.0) && (heartRate < 60 || heartRate > 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration < 0.800))
                || ((userTemperature >= 36.0 && userTemperature <= 37.0) && (heartRate < 60 || heartRate > 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration > 0.800))

                || ((userTemperature >= 36.0 && userTemperature <= 37.0) && (heartRate >= 60 && heartRate <= 100) && (highBloodPressure < 90 || highBloodPressure > 140) && (lowBloodPressure < 60 || lowBloodPressure > 90) && (alcoholConcentration < 0.800))
                || ((userTemperature >= 36.0 && userTemperature <= 37.0) && (heartRate >= 60 && heartRate <= 100) && (highBloodPressure < 90 || highBloodPressure > 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration > 0.800))
                || ((userTemperature >= 36.0 && userTemperature <= 37.0) && (heartRate >= 60 && heartRate <= 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure < 60 || lowBloodPressure > 90) && (alcoholConcentration > 0.800))
                )
            img1.setImageResource(R.drawable.i3);
        if (((userTemperature >= 36.0 && userTemperature <= 37.0) && (heartRate >= 60 && heartRate <= 100) && (highBloodPressure < 90 || highBloodPressure > 140) && (lowBloodPressure < 60 || lowBloodPressure > 90) && (alcoholConcentration > 0.800))
                || ((userTemperature >= 36.0 && userTemperature <= 37.0) && (heartRate < 60 || heartRate > 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure < 60 || lowBloodPressure > 90) && (alcoholConcentration > 0.800))
                || ((userTemperature >= 36.0 && userTemperature <= 37.0) && (heartRate < 60 || heartRate > 100) && (highBloodPressure < 90 || highBloodPressure > 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration > 0.800))
                || ((userTemperature >= 36.0 && userTemperature <= 37.0) && (heartRate < 60 || heartRate > 100) && (highBloodPressure < 90 || highBloodPressure > 140) && (lowBloodPressure < 60 || lowBloodPressure > 90) && (alcoholConcentration < 0.800))

                || ((userTemperature < 36.0 || userTemperature > 37.0) && (heartRate >= 60 && heartRate <= 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure < 60 || lowBloodPressure > 90) && (alcoholConcentration < 0.800))
                || ((userTemperature < 36.0 || userTemperature > 37.0) && (heartRate >= 60 && heartRate <= 100) && (highBloodPressure < 90 || highBloodPressure > 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration > 0.800))
                || ((userTemperature < 36.0 || userTemperature > 37.0) && (heartRate >= 60 && heartRate <= 100) && (highBloodPressure < 90 || highBloodPressure > 140) && (lowBloodPressure < 60 || lowBloodPressure > 90) && (alcoholConcentration < 0.800))

                || ((userTemperature < 36.0 || userTemperature > 37.0) && (heartRate < 60 || heartRate > 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration > 0.800))
                || ((userTemperature < 36.0 || userTemperature > 37.0) && (heartRate < 60 || heartRate > 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure < 60 || lowBloodPressure > 90) && (alcoholConcentration < 0.800))
                || ((userTemperature < 36.0 || userTemperature > 37.0) && (heartRate < 60 || heartRate > 100) && (highBloodPressure < 90 || highBloodPressure > 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration < 0.800))
                )
            img1.setImageResource(R.drawable.i4);
        if (((userTemperature < 36.0 || userTemperature > 37.0) && (heartRate < 60 || heartRate > 100) && (highBloodPressure < 90 || highBloodPressure > 140) && (lowBloodPressure < 60 || lowBloodPressure > 90) && (alcoholConcentration < 0.800))
                || ((userTemperature < 36.0 || userTemperature > 37.0) && (heartRate < 60 || heartRate > 100) && (highBloodPressure < 90 || highBloodPressure > 140) && (lowBloodPressure >= 60 && lowBloodPressure <= 90) && (alcoholConcentration > 0.800))
                || ((userTemperature < 36.0 || userTemperature > 37.0) && (heartRate < 60 || heartRate > 100) && (highBloodPressure >= 90 && highBloodPressure <= 140) && (lowBloodPressure < 60 || lowBloodPressure > 90) && (alcoholConcentration > 0.800))
                || ((userTemperature < 36.0 || userTemperature > 37.0) && (heartRate >= 60 && heartRate <= 100) && (highBloodPressure < 90 || highBloodPressure > 140) && (lowBloodPressure < 60 || lowBloodPressure > 90) && (alcoholConcentration > 0.800))
                || ((userTemperature >= 36.0 && userTemperature <= 37.0) && (heartRate < 60 || heartRate > 100) && (highBloodPressure < 90 || highBloodPressure > 140) && (lowBloodPressure < 60 || lowBloodPressure > 90) && (alcoholConcentration > 0.800))
                )
            img1.setImageResource(R.drawable.i5);       //为条目右侧的ImageView设置分级标识
        img1.setOnClickListener(mListener);             //设置分级的点击事件
        img1.setTag(position);
        img.setOnClickListener(nListener);
        img.setTag(position);
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


