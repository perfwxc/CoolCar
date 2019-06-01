package com.wxc.coolcar.Map;

import android.content.Context;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.tts.client.SpeechSynthesizer;
import com.wxc.coolcar.R;

import java.util.List;

public class MapActivity extends AppCompatActivity {
    private MapView mMapView;
    private LocationManager locationManager;
    private String provider;
    private LocationClient locationClient;
    private BDLocation bdLocation;
    private MbdLocationListener mbdLocationListener;
    private boolean isFirstIn = true;
    private double mLatitude;//纬度
    private double mLongitude;//经度
    private BaiduMap baiduMap;
    private Context context;
    private LocationManager locationManger;
    private SpeechSynthesizer mSpeechSynthesizer;//百度语音合成客户端


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());


        setContentView(R.layout.activity_map);

        this.context = this;
        initMapView();

        initLocation();

        Button button = findViewById(R.id.cbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void initMapView() {

        //得到地图实例
        mMapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mMapView.getMap();

        //将BaiduMap的setMyLocationEnabled设置为True
        baiduMap.setMyLocationEnabled(true);


        // 设置地图放大缩小参数
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        baiduMap.setMapStatus(msu);

        //使用LocationManager判断可以使用的定位方式

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providerList = locationManager.getProviders(true);

        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;

        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "No location Provider to use", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void initLocation() {

        //创建LocationClient实例

        locationClient = new LocationClient(getApplicationContext());
        mbdLocationListener = new MbdLocationListener();
        //注册监听器，定位成功后回调
        locationClient.registerLocationListener(mbdLocationListener);
        //初始化
        setLocationOption();
        //启动LocationClient开始定位,定位结果返回在监听器的onReceiveLocation中
        locationClient.start();


    }


    public void setLocationOption() {


        LocationClientOption option = new LocationClientOption();
        option.setCoorType("all");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setIsNeedAddress(true);// 位置，一定要设置，否则后面得不到地址
        option.setOpenGps(true);// 打开GPS
        option.setScanSpan(5000);// 多长时间进行一次请求
        option.setLocationMode(LocationMode.Hight_Accuracy);// 高精度
        locationClient.setLocOption(option);// 使用设置

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        //反注册监听器
        locationClient.unRegisterLocationListener(mbdLocationListener);
    }

    private class MbdLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            //创建MyLocationData类
            MyLocationData locationdata = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            //在BaiduMap中设置
            baiduMap.setMyLocationData(locationdata);

            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();

            //判断是否第一次定位，如果是执行如下代码

            if (isFirstIn) {
                LatLng latLng = new LatLng(mLatitude, mLongitude);
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.animateMapStatus(msu);
                isFirstIn = false;
                Toast.makeText(
                        context,
                        "地址是：" + location.getAddrStr() + "\n"
                                + "定位精度是：" + location.getRadius()
                        , Toast.LENGTH_LONG).show();
                Log.e("Address", String.valueOf(location.getCity()));
                Log.e("District", String.valueOf(location.getAddrStr()));
                Log.e("Location", String.valueOf(location));

            }
        }


    }
}