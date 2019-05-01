package com.wxc.coolcar.Chat;

/**
 * Created by Administrator on 2018/6/15.
 */
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wxc.coolcar.Health.MActivity;
import com.wxc.coolcar.R;
import com.wxc.mylibrary.PullToZoomListView;

public class ChatActivity extends AppCompatActivity {
    private PullToZoomListView listView;
    private String[] adapterData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Button button = findViewById(R.id.bbutton);
        listView = (PullToZoomListView)findViewById(R.id.list_view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapterData = new String[] {
                "吴先生：\n\n          武汉哪里有好的保养店？车子该保养了。\n                            ",
                "刘女士：\n\n          湖北大学附近有一家还不错，你可以去看看。\n                        ",
                "李先生：\n\n          新买了车，以后大家多多关照！\n                                            ",
                "张女士：\n\n          究竟是买银色还是黑色？\n                            ",
                "吴先生（回复刘女士）：\n\n          谢谢，真的还不错！\n                  ",
                "郭小姐：\n\n          这车保值率真高！                              \n  "
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorChat));
        }
        listView.setAdapter(new ArrayAdapter<>(ChatActivity.this,android.R.layout.simple_list_item_1, adapterData));
        listView.getHeaderView().setImageResource(R.drawable.splash);
        listView.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);
        listView.setShadow(R.drawable.shadow_bottom);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ChatActivity.this, "您点击了这条动态~", Toast.LENGTH_SHORT).show();

            }
        });
    }

}

