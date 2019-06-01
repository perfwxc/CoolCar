package com.wxc.coolcar.Main;

/**
 * Created by Chenge666 on 2018/10/24.
 */

import android.Manifest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.wxc.coolcar.Health.MActivity;
import com.wxc.coolcar.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;


public class VoiceActivity extends MPermissionsActivity {
    private static final String TAG = "ActivityWakeUp";
    private TextView txtLog;
    private MediaPlayer mediaPlayer1 = null;
    private EventManager mWpEventManager;
    private PowerManager.WakeLock mWakelock;
    private Timer timer = null;   //计时器
    private PowerLED powerLED;   //闪光灯的基类
    private Vibrator vibrator; //震动

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wake_up_layout);
        /**
         * 动态申请权限
         * */
        requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO}, 1000);
        txtLog = (TextView) findViewById(R.id.txtLog);
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, "WakeLock");
        powerLED = new PowerLED(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 唤醒功能打开步骤
        // 1) 创建唤醒事件管理器
        mWpEventManager = EventManagerFactory.create(VoiceActivity.this, "wp");

        // 2) 注册唤醒事件监听器
        mWpEventManager.registerListener(new EventListener() {
            @Override
            public void onEvent(String name, final String params, byte[] data, int offset, int length) {
                Log.d(TAG, String.format("event: name=%s, params=%s", name, params));
                try {
                    JSONObject json = new JSONObject(params);
                    if ("wp.data".equals(name)) { // 每次唤醒成功, 将会回调name=wp.data的时间, 被激活的唤醒词在params的word字段
                        String word = json.getString("word");
                        txtLog.setText(word);

                        txtLog.append("唤醒成功, 唤醒词: " + word + "\r\n");
                        Log.e("------", "唤醒成功, 唤醒词: " + word + "\r\n");
                        if (word.equals("车载精灵")) {
                            mWakelock.acquire();//唤醒屏幕
                            //      mediaPlayer1.seekTo(0);  //音乐从头开始
                            //      mediaPlayer1.start();  //播放音乐
                            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                            long[] pattern = {100, 400, 100, 400}; // 停止 开启 停止 开启
                            vibrator.vibrate(pattern, -1); //重复两次上面的pattern 如果只想震动一次，index设为-1
                            /**
                             * 计时器
                             * 1000标示这个计时器最先打开后延迟1s执行
                             * 100标示每隔0.8秒执行一次
                             * */
                            Toast.makeText(getApplicationContext(), "哟，赶紧下载安装这个APP吧", Toast.LENGTH_LONG).show();


                        } else if (word.equals("健康助手")) {
                            // 通过包名获取要跳转的app，创建intent对象
                            //  Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                            // 这里如果intent为空，就说名没有安装要跳转的应用嘛
                            Intent intent = new Intent(VoiceActivity.this, MActivity.class);
                            if (intent != null) {
                                // 这里跟Activity传递参数一样的嘛，不要担心怎么传递参数，还有接收参数也是跟Activity和Activity传参数一样
                                startActivity(intent);

                            } else {
                                // 没有安装要跳转的app应用，提醒一下
                                Toast.makeText(getApplicationContext(), "哟，赶紧下载安装这个APP吧", Toast.LENGTH_LONG).show();
                            }

                        } else if (word.equals("原地待命")) {
                            if (powerLED.m_isOn) {
                                powerLED.turnOff();
                                Log.e("--------", "关闭屏幕");
                            }

                        }
                    } else if ("wp.exit".equals(name)) {
                        txtLog.append("唤醒已经停止: " + params + "\r\n");
                        Log.d("******", "唤醒已经停止: " + params + "\r\n");
                    }
                } catch (JSONException e) {
                    throw new AndroidRuntimeException(e);
                }
            }
        });

        // 3) 通知唤醒管理器, 启动唤醒功能
        HashMap params = new HashMap();
        params.put("kws-file", "assets:///WakeUp2.bin");
        // 设置唤醒资源, 唤醒资源请到 http://yuyin.baidu.com/wake#m4 来评估和导出
        mWpEventManager.send("wp.start", new JSONObject(params).toString(), null, 0, 0);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 停止唤醒监听
        mWpEventManager.send("wp.stop", null, null, 0, 0);
        //        mediaPlayer1.stop();
        //        mediaPlayer1.release();
        // 释放mediaPlayer
        mWakelock.release();//释放
        timer.cancel();  //结束计时
        powerLED.Destroy();//释放相机的闪光灯
    }

}
