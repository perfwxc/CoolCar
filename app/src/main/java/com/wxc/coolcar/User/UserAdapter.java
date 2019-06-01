package com.wxc.coolcar.User;

/**
 * Created by Administrator on 2018/5/23.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wxc.coolcar.Environment.NActivity;
import com.wxc.coolcar.Health.MActivity;
import com.wxc.coolcar.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private static final String TAG = "UserAdapter";

    private Context mContext;

    private List<User> mFruitList;

    public UserAdapter(List<User> fruitList) {
        mFruitList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_layout, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                User fruit = mFruitList.get(position);
                if (fruit.getId() == 1) {
                    Intent intent = new Intent(mContext, MActivity.class);
                    mContext.startActivity(intent);
                }
                if (fruit.getId() == 2) {
                    Intent intent2 = new Intent(mContext, NActivity.class);
                    mContext.startActivity(intent2);
                }
            }
            //Intent intent = new Intent(mContext,MActivity.class);
            //mContext.startActivity(intent);


        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getUserTemperature());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
        }
    }

}
