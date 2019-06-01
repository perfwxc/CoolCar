package com.wxc.coolcar.Information;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wxc.coolcar.R;
import com.wxc.coolcar.User.User;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.githang.statusbar.StatusBarCompat.setStatusBarColor;

public class ShowActivity extends AppCompatActivity {
    private static final String TAG = "ShowActivity";
    public String date;
    public ListView lv;
    public ArrayList<User> listuser;
    public android.os.Handler han = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //Toast.makeText(MainActivity.this,""+date,Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    lv.setAdapter(new IAdapter(ShowActivity.this, listuser));       //调用listview适配器
                    break;
            }
        }

    };

    public ArrayList<User> getListuser() {
        return listuser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        setStatusBarColor(this, 2000, true);

        lv = (ListView) findViewById(R.id.lv);
        final NestedScrollView ss = findViewById(R.id.ScrollView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar1);
        ImageView fruitImageView = (ImageView) findViewById(R.id.fruit_image_view);
        toolbar.setTitle("                个人信息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        init();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.settings:
                Intent intent2 = new Intent(ShowActivity.this, PHPActivity.class);
                startActivity(intent2);
                break;

            case R.id.shua:
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();  // Always call the superclass method first

        // The activity is either being restarted or started for the first time
        // so this is where we should make sure that GPS is enabled
        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsEnabled) {
            // Create a dialog here that requests the user to enable GPS, and use an intent
            // with the android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS action
            // to take the user to the Settings screen to enable GPS when they click "OK"
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first

        // Activity being restarted from stopped state
    }

    //获取json数据部分
    private void init() {
        new Thread(new Runnable() {
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                //服务器返回的地址
                Request request = new Request.Builder()
                        .url("https://perfwxc.cn/coolcar/json/info_json.php").build();

                try {
                    Response response = okHttpClient.newCall(request).execute();
                    //获取到数据
                    date = response.body().string();
                    //在线程中没有办法实现主线程操作
                    Message message = new Message();
                    message.what = 1;
                    han.sendMessage(message);
                    //把数据传入解析josn数据方法
                    Gsonjx(date);

                } catch (IOException e) {


                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void Gsonjx(String date) { //gson解析部分
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(date).getAsJsonArray();
        Gson gson = new Gson();
        listuser = new ArrayList<>();
        for (JsonElement user : jsonArray) {
            User userBean = gson.fromJson(user, User.class);
            listuser.add(userBean);
        }
        Message message = new Message();
        message.what = 2;
        han.sendMessage(message);
    }
}

