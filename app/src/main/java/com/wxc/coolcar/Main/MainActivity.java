package com.wxc.coolcar.Main;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.Manifest;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.igexin.sdk.PushManager;
import com.wxc.coolcar.Chart.ChartActivity;
import com.wxc.coolcar.Chat.ChatActivity;

import com.wxc.coolcar.Environment.NActivity;
import com.wxc.coolcar.Guide.WelActivity;
import com.wxc.coolcar.Health.MActivity;
import com.wxc.coolcar.Information.ShowActivity;
import com.wxc.coolcar.Map.MapActivity;
import com.wxc.coolcar.R;
import com.wxc.coolcar.User.User;
import com.wxc.coolcar.User.UserAdapter;
import com.wxc.coolcar.Util.IntentService;
import com.wxc.coolcar.Util.NotificationUtil;
import com.wxc.coolcar.Util.PushService;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.igexin.assist.sdk.AssistPushConsts.LOG_TAG;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SpeechSynthesizer mSpeechSynthesizer;
    private String mSampleDirPath;
    private static final String SAMPLE_DIR_NAME = "baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license";
    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";
    private DrawerLayout mDrawerLayout;
    private User fruits = new User("人体健康", R.drawable.health, 1);
    private User enir = new User("车内环境", R.drawable.enir, 2);
    private List<User> fruitList = new ArrayList<>();
    private UserAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        setSupportActionBar(toolbar);
        txtLog = (TextView) findViewById(R.id.txtLog);
    //    initialEnv();
   //     initialTts();
        PushManager.getInstance().initialize(this.getApplicationContext(), PushService.class);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_location:
                        Intent intent1 = new Intent(MainActivity.this, MapActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_friends:
                        Intent intent2 = new Intent(MainActivity.this, ChatActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_call:
                        Intent intent3 = new Intent(MainActivity.this, ShowActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_mail:
                        Intent intent4 = new Intent(MainActivity.this, WelActivity.class);
                        startActivity(intent4);
                        break;
                    default:

                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), IntentService.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_MAIN); i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addCategory(Intent.CATEGORY_HOME); startActivity(i);
            }
        });
        initFruits();
        initPermission();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UserAdapter(fruitList);
        recyclerView.setAdapter(adapter);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setEnabled(false);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initFruits() {
        fruitList.clear();
        fruitList.add(fruits);
        fruitList.add(enir);

    }

    public void postNotification1(Context context) {
        int channelId = 0x22222;
        Notification.Builder builder;
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //NotificationManagerCompat managerCompat= (NotificationManagerCompat) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {//8.0系统之上
            NotificationChannel channel = new NotificationChannel(String.valueOf(channelId), "chanel_name", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            builder = new Notification.Builder(context, String.valueOf(channelId));
            //或者
            //builder = new Notification.Builder(context);
            //builder.setChannelId(String.valueOf(channelId)); //创建通知时指定channelId
        } else {
            builder = new Notification.Builder(context);
        }
        //需要跳转指定的页面
        Intent intent = new Intent(context, MActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        builder.setTicker("new message")
                .setSmallIcon(R.drawable.push_small)
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setFullScreenIntent(pendingIntent,true)
                .setContentTitle("系统检测到您的身体出现异常，请及时查看！")
                .setContentText("您的体温偏高，请及时查明原因")
                .setContentIntent(pendingIntent);
        manager.notify(channelId, builder.build());
    }
    public void postNotification2(Context context) {
        int channelId = 0x22222;
        Notification.Builder builder;
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //NotificationManagerCompat managerCompat= (NotificationManagerCompat) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {//8.0系统之上
            NotificationChannel channel = new NotificationChannel(String.valueOf(channelId), "chanel_name", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            builder = new Notification.Builder(context, String.valueOf(channelId));
            //或者
            //builder = new Notification.Builder(context);
            //builder.setChannelId(String.valueOf(channelId)); //创建通知时指定channelId
        } else {
            builder = new Notification.Builder(context);
        }
        //需要跳转指定的页面
        Intent intent = new Intent(context, NActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        builder.setTicker("new message")
                .setSmallIcon(R.drawable.push_small)
                .setPriority(Notification.PRIORITY_HIGH)
                .setFullScreenIntent(pendingIntent,true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentTitle("系统检测到车内环境出现异常，请及时查看！")
                .setContentText("车内光照强度偏高，请及时查明原因")
                .setContentIntent(pendingIntent);
        manager.notify(channelId, builder.build());
    }
    public void onClick1(View v) {
        postNotification1(this);
    }
    public void onClick2(View v) {
        postNotification2(this);
    }
    private void initialTts() {
        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        this.mSpeechSynthesizer.setContext(this);
        this.mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
            @Override
            public void onSynthesizeStart(String s) {

            }

            @Override
            public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

            }

            @Override
            public void onSynthesizeFinish(String s) {

            }

            @Override
            public void onSpeechStart(String s) {

            }

            @Override
            public void onSpeechProgressChanged(String s, int i) {

            }

            @Override
            public void onSpeechFinish(String s) {

            }

            @Override
            public void onError(String s, SpeechError speechError) {

            }
        });
        // 文本模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
                + TEXT_MODEL_NAME);
        // 声学模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
        // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了正式离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
        // 如果合成结果出现临时授权文件将要到期的提示，说明使用了临时授权文件，请删除临时授权即可。
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/"
                + LICENSE_FILE_NAME);
        // 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
        this.mSpeechSynthesizer.setAppId("11319692"/*这里只是为了让Demo运行使用的APPID,请替换成自己的id。*/);
        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
        this.mSpeechSynthesizer.setApiKey("iRwaZnUB2fiGI7i1fdMttAld",
                "SjNwKCkIItx4ABRejGSP9kciwbQc51VQ"/*这里只是为了让Demo正常运行使用APIKey,请替换成自己的APIKey*/);
        // 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置Mix模式的合成策略
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 授权检测接口(只是通过AuthInfo进行检验授权是否成功。)
        // AuthInfo接口用于测试开发者是否成功申请了在线或者离线授权，如果测试授权成功了，可以删除AuthInfo部分的代码（该接口首次验证时比较耗时），不会影响正常使用（合成使用时SDK内部会自动验证授权）
        AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);

        if (authInfo.isSuccess()) {
            Toast.makeText(this,"车载精灵为您服务",Toast.LENGTH_SHORT).show();
            speak("车载精灵为您服务");
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            Toast.makeText(this,"auth failed errorMsg=" + errorMsg,Toast.LENGTH_LONG).show();
        }

        // 初始化tts
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        // 加载离线英文资源（提供离线英文合成功能）
//        int result =
//                mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath
//                        + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        //Toast.makeText(this,"loadEnglishModel result=" + result,Toast.LENGTH_LONG).show();

        //打印引擎信息和model基本信息
        //printEngineInfo();
    }
    private void speak(String text) {
//        String text = this.mInput.getText().toString();
        //需要合成的文本text的长度不能超过1024个GBK字节。
//        if (TextUtils.isEmpty(mInput.getText())) {
//            text = "欢迎使用百度语音合成SDK,百度语音为你提供支持。";
//            mInput.setText(text);
//        }
        int result = this.mSpeechSynthesizer.speak(text);
        if (result < 0) {
            Toast.makeText(this,"error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ",Toast.LENGTH_LONG).show();
        }
    }

    private void initialEnv() {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        }
        makeDir(mSampleDirPath);
        copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME);
        copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_TEXT_MODEL_NAME);
    }

    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 将sample工程需要的资源文件拷贝到SD卡中使用（授权文件为临时授权文件，请注册正式授权）
     *
     * @param isCover 是否覆盖已存在的目标文件
     * @param source
     * @param dest
     */
    private void copyFromAssetsToSdcard(boolean isCover, String source, String dest) {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = getResources().getAssets().open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.settings:
                Intent intent2 = new Intent(MainActivity.this, ChartActivity.class);
                startActivity(intent2);
                break;
            default:
        }
        return true;
    }
    private void initPermission() {
        String permissions[] = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
        };
        ArrayList<String> toApplyList = new ArrayList<String>();
        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();//注释掉这行,back键不退出activity
        Intent i = new Intent(Intent.ACTION_MAIN); i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME); startActivity(i);
        Log.i(LOG_TAG, "onBackPressed");
    }
    @Override
    protected void onResume() {
        super.onResume();

        // 唤醒功能打开步骤
        // 1) 创建唤醒事件管理器
        mWpEventManager = EventManagerFactory.create(MainActivity.this, "wp");

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
                         //   mWakelock.acquire();//唤醒屏幕
                            //      mediaPlayer1.seekTo(0);  //音乐从头开始
                            //      mediaPlayer1.start();  //播放音乐
                            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                            long [] pattern = {100,400,100,400}; // 停止 开启 停止 开启
                            vibrator.vibrate(pattern,-1); //重复两次上面的pattern 如果只想震动一次，index设为-1

                            /**
                             * 计时器
                             * 1000标示这个计时器最先打开后延迟1s执行
                             * 100标示每隔0.8秒执行一次
                             * */
                            Toast.makeText(getApplicationContext(), "恭喜你唤醒成功！", Toast.LENGTH_SHORT).show();
                            speak("恭喜你唤醒成功，有什么可以为您效劳的呢？");
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else if (word.equals("健康助手")) {
                            // 通过包名获取要跳转的app，创建intent对象
                            //  Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                            // 这里如果intent为空，就说名没有安装要跳转的应用嘛
                            Intent intent =new Intent (MainActivity.this, MActivity.class);
                            if (intent != null) {
                                // 这里跟Activity传递参数一样的嘛，不要担心怎么传递参数，还有接收参数也是跟Activity和Activity传参数一样
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "已成功为您切换到健康信息页面！", Toast.LENGTH_SHORT).show();
                                speak("已成功为您切换到健康信息页面，快查看您的身体状况吧！");
                                try {
                                    Thread.sleep(200);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                // 没有安装要跳转的app应用，提醒一下
                                Toast.makeText(getApplicationContext(), "哟，赶紧下载安装这个APP吧", Toast.LENGTH_LONG).show();
                            }

                        } else if (word.equals("原地待命")) {

                            Toast.makeText(getApplicationContext(), "车载精灵为您保驾护航！", Toast.LENGTH_SHORT).show();
                            speak("主人再见，车载精灵祝您一路平安。");

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
        params.put("kws-file", "assets:///WakeUp3.bin"); // 设置唤醒资源, 唤醒资源请到 http://yuyin.baidu.com/wake#m4 来评估和导出
      //  mWpEventManager.send("wp.start", new JSONObject(params).toString(), null, 0, 0);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 停止唤醒监听
        mWpEventManager.send("wp.stop", null, null, 0, 0);
//        mediaPlayer1.stop();
//        mediaPlayer1.release();  //释放mediaPlayer
//        mWakelock.release();//释放
    //    timer.cancel();  //结束计时
    //    powerLED.Destroy();//释放相机的闪光灯
    }
}
