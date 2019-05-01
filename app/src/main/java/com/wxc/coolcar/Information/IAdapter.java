package com.wxc.coolcar.Information;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxc.coolcar.R;
import com.wxc.coolcar.User.User;

import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public class IAdapter extends BaseAdapter {
    public Context con;
    public List<User> list;
    public LayoutInflater inflater;

    public IAdapter(Context context, List<User> user) {
        this.con = context;
        this.list = user;
        inflater = LayoutInflater.from(con);

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
        View view = inflater.inflate(R.layout.information_item, null);
        CardView cv = view.findViewById(R.id.card_view);
        TextView tv1 = (TextView) view.findViewById(R.id.infortext);

        if(list.get(position).getId()==1)
        {
            tv1.setText("   用户名:    " + list.get(position).getName());

        }
        if(list.get(position).getId()==2)
        {
            tv1.setText("   手机号:    " + list.get(position).getNum());

        }
        if(list.get(position).getId()==3)
        {
            tv1.setText("   车型:    " + list.get(position).getType());

        }
        if(list.get(position).getId()==4)
        {
            tv1.setText("   住址:    " + list.get(position).getAddress());

        }
        if(list.get(position).getId()==5)
        {
            tv1.setTextColor(Color.parseColor("#FF0000"));  //为应急联系方式设置红色，突出显示
            tv1.setText("   应急联系方式:    " + list.get(position).getHelp());

        }
        if(list.get(position).getId()>5)
            cv.setVisibility(View.GONE);

            return view;
    }
    ;


}

