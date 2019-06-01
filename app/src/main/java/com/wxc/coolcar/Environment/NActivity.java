package com.wxc.coolcar.Environment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wxc.coolcar.Health.Adapter;
import com.wxc.coolcar.Information.JSONParser;
import com.wxc.coolcar.R;
import com.wxc.coolcar.User.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.githang.statusbar.StatusBarCompat.setStatusBarColor;
import static com.igexin.sdk.GTServiceManager.context;

public class NActivity extends AppCompatActivity implements SpeechSynthesizerListener {
    public static final int MY_ID = R.drawable.timg1;
    private static final String TAG = "NActivity";
    private static final String SAMPLE_DIR_NAME = "baiduTTS";   //导入百度语音合成相关文件
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license_2016-04-05";
    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";
    public String date;
    public ListView lv;
    public ArrayList<User> listuser;
    public ProgressDialog mProgressDialog;
    protected String appId = "11319692";    //百度语音合成设别号0
    protected String appKey = "iRwaZnUB2fiGI7i1fdMttAld";
    protected String secretKey = "SjNwKCkIItx4ABRejGSP9kciwbQc51VQ";
    boolean stopThread = false;
    Timer timer = new Timer();//动态刷新数据
    private String url = "https://perfwxc.cn/coolcar/json/delete.php";
    private SpeechSynthesizer mSpeechSynthesizer;//百度语音合成客户端
    private String mSampleDirPath;
    private int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private int KEEP_ALIVE_TIME = 1;
    private TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private ExecutorService executorService = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, new LinkedBlockingDeque<Runnable>(128));
    private Adapter.MyClickListener nListener = new Adapter.MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            String environmentTemperature = listuser.get(position).getEnvironmentTemperature();
            String ambientHumidity = listuser.get(position).getAmbientHumidity();
            String smokeDensity = listuser.get(position).getSmokeDensity();
            String CODensity = listuser.get(position).getCODensity();
            String H2S = listuser.get(position).getH2S();
            String alcoholConcentration = listuser.get(position).getAlcoholConcentration();

            Intent IntentToEetail = new Intent(NActivity.this, Eetail.class);

            IntentToEetail.putExtra("ienvironmentTemperature", environmentTemperature);
            IntentToEetail.putExtra("iambientHumidity", ambientHumidity);
            IntentToEetail.putExtra("ismokeDensity", smokeDensity);
            IntentToEetail.putExtra("iCODensity", CODensity);
            IntentToEetail.putExtra("iH2S", H2S);
            IntentToEetail.putExtra("ialcoholConcentration", alcoholConcentration);
            startActivity(IntentToEetail);
        }


    };
    private Adapter.MyClickListener mListener = new Adapter.MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            int environmentTemperature = Integer.parseInt(listuser.get(position).getEnvironmentTemperature());
            double ambientHumidity = Double.valueOf(listuser.get(position).getAmbientHumidity());
            int smokeDensity = Integer.parseInt(listuser.get(position).getSmokeDensity());
            int CODensity = Integer.parseInt(listuser.get(position).getCODensity());
            int H2S = Integer.parseInt(listuser.get(position).getH2S());
            int Lux = Integer.parseInt(listuser.get(position).getLux());
            SpeakAdvice(environmentTemperature, ambientHumidity, smokeDensity, CODensity, H2S, Lux);
            if (45 < ambientHumidity && ambientHumidity < 70) {
                Toast.makeText(NActivity.this, "您现在所处的环境湿度十分舒适！", Toast.LENGTH_SHORT).show();
            }
            if (ambientHumidity <= 45) {
                Toast.makeText(NActivity.this, "您所处的环境过于干燥。", Toast.LENGTH_SHORT).show();
            }
            if (ambientHumidity >= 70) {
                Toast.makeText(NActivity.this, "您所处的环境太潮湿了", Toast.LENGTH_SHORT).show();
            }
            if (5 < environmentTemperature && environmentTemperature < 40) {
                Toast.makeText(NActivity.this, "环境的温度适宜！", Toast.LENGTH_SHORT).show();
            }
            if (environmentTemperature <= 5) {
                Toast.makeText(NActivity.this, "车内温度过低！", Toast.LENGTH_SHORT).show();
            }
            if (environmentTemperature >= 40) {
                Toast.makeText(NActivity.this, "车内温度过高，建议您开下空调！", Toast.LENGTH_SHORT).show();
            }
            if (smokeDensity < 5000) {
                Toast.makeText(NActivity.this, "车内的空气烟雾浓度恰到好处！", Toast.LENGTH_SHORT).show();
            }
            if (smokeDensity >= 5000) {
                Toast.makeText(NActivity.this, "车内的空气烟雾浓度偏高，请开窗通风！", Toast.LENGTH_SHORT).show();
            }
            if (CODensity < 100) {
                Toast.makeText(NActivity.this, "一氧化碳浓度不高。", Toast.LENGTH_SHORT).show();
            }
            if (CODensity >= 100) {
                Toast.makeText(NActivity.this, "车内的一氧化碳浓度过高，请及时通风！", Toast.LENGTH_SHORT).show();
            }
            if (Lux == 1) {
                Toast.makeText(NActivity.this, "车内的光照强度不高，适宜开车！", Toast.LENGTH_SHORT).show();
            }
            if (Lux == 0) {
                Toast.makeText(NActivity.this, "车内光照强度过高！请关窗。", Toast.LENGTH_SHORT).show();
            }


        }


    };
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //若工作完成，取消动画，初始化界面

            switch (msg.what) {
                case 1:
                    mProgressDialog.cancel();
                    break;
                case 2:
                    lv.setAdapter(new Adapter2(NActivity.this, listuser, mListener, nListener));//调用listview适配器


                    Adapter2 environmentAdapter = new Adapter2(NActivity.this, listuser, mListener, nListener);
                    lv.setAdapter(environmentAdapter);
                    setListViewHeightBasedOnChildren(lv);


                    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                            final int aaa = listuser.get(position).getId();

                            new Thread(new Runnable() {
                                public void run() {
                                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                                    params.add(new BasicNameValuePair("name", String.valueOf(aaa)));
                                    JSONParser jsonParser = new JSONParser();
                                    try {
                                        JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
                                        Log.v("uploadsucceed", "uploadsucceed");

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }).start();

                            Toast.makeText(NActivity.this, "数据已删除！", Toast.LENGTH_SHORT).show();

                            return true;
                        }
                    });


                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            double environmentTemperature = Double.parseDouble(listuser.get(position).getEnvironmentTemperature());
                            double ambientHumidity = Double.parseDouble(listuser.get(position).getAmbientHumidity());
                            double smokeDensity = Double.parseDouble(listuser.get(position).getSmokeDensity());
                            double CODensity = Double.parseDouble(listuser.get(position).getCODensity());
                            int H2S = Integer.parseInt(listuser.get(position).getH2S());
                            Toast.makeText(NActivity.this, "车内温度为" + environmentTemperature + "摄氏度"
                                    + "\n 车内湿度为相对湿度百分之" + ambientHumidity
                                    + "\n 烟雾浓度为" + smokeDensity + "ppm"
                                    + "\n 光照强度正常"
                                    + "\n 一氧化碳浓度为" + CODensity + "ppm"
                                    + "\n 硫化氢浓度为" + H2S + "ppm", Toast.LENGTH_LONG).show();

                            mSpeechSynthesizer.speak(
                                    "车内温度为" + environmentTemperature + "摄氏度"
                                            + "。。车内湿度为相对湿度百分之" + ambientHumidity
                                            + "。。烟雾浓度为" + smokeDensity + "ppm"
                                            + "。。光照强度正常"
                                            + "。。一氧化碳浓度为" + CODensity + "ppm"
                                            + "\n 硫化氢浓度为" + H2S + "ppm"

                            );

                        }
                    });

                    break;

            }
        }

    };
    TimerTask task = new TimerTask() {
        public void run() {
            if (!stopThread)    //执行线程标志
                init();          //刷新执行函数

        }
    };
    /**
     * 线程
     */
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            /*
                需要耗时的工作
            */

            init();
            initialEnv();
            initialTts();
            try {
                Thread.sleep(4000);      //线程休眠1ms，传递数据
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg = mHandler.obtainMessage();
            msg.what = 1;
            mHandler.sendMessage(msg);


        }
    };

    //动态设置LitView的高度，使得每个条目显示完全
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获得adapter
        Adapter2 adapter = (Adapter2) listView.getAdapter();
        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            //计算总高度
            totalHeight += listItem.getMeasuredHeight();
            totalHeight += 5;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //计算分割线高度
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        //给listview设置高度
        listView.setLayoutParams(params);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_main);
        setStatusBarColor(this, 2000, true);
        lv = (ListView) findViewById(R.id.lv);
        final NestedScrollView ss = findViewById(R.id.ScrollView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar1);
        ImageView fruitImageView = (ImageView) findViewById(R.id.fruit_image_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopThread = true;
                finish();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NActivity.this, "车内环境信息暂未设置折线图分析~", Toast.LENGTH_SHORT).show();

            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("车内环境数据如下：");
        Glide.with(this).load(MY_ID).into(fruitImageView);
        //创建ProgressDialog
        createProgressDialog();
        //启动线程
        executorService.execute(mRunnable);
        timer.schedule(task, 1000 * 4, 1000 * 10); //启动timer，设置任务周期

    }

    /**
     * 创建ProrgressDialog
     */
    private void createProgressDialog() {
        context = this;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("加载数据中,请稍等...");
        //设置点击区域外的屏幕不关闭
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    public void SpeakAdvice(int environmentTemperature, double ambientHumidity, int smokeDensity,
                            int CODensity, int H2S, int Lux) {
        //environmentTemperature, ambientHumidity, smokeDensity, CODensity, H2S, Lux)
        int[] flag = new int[6];

        String[] advice = new String[]{
                "开窗通风",
                "必要时空调降温",
                "必要时空调升温",
                "注意保暖",
                "及时离开车辆",
                "检查车内是否有火源",
                "及时停车，注意光强干扰",
                "打开遮光前板",
                "若为其他车灯照射，可切换远近光提醒对方车辆",
                "若空调开放，请及时关闭",
                "光线较暗，注意安全",
                "打开汽车除湿模式",
                "注意保湿",
                "根据您当前的车内环境，建议您",
                ".易驾为您提供最舒适的环境",
                "车内环境良好，祝您驾驶愉快！"
        };
        int[] aflag = new int[advice.length];
        //	System.out.println(s.length);
        int count = 0;

        if (environmentTemperature < 22) flag[0] = -1;
        if (environmentTemperature > 28) flag[0] = 1;
        if (ambientHumidity < 30) flag[1] = -1;
        if (ambientHumidity > 75) flag[1] = 1;
        if (smokeDensity > 4000) flag[2] = 1;
        if (CODensity > 4000) flag[3] = 1;
        if (H2S > 4000) flag[4] = 1;
        if (Lux <= 15) flag[5] = -1;
        if (Lux > 4000) flag[5] = 1;
        for (int i = 0; i <= 5; i++) {
            if (flag[i] == 0) continue;
            if (flag[i] == 1) {
                switch (i) {
                    case 0:
                        aflag[0] = 1;
                        aflag[1] = 1;
                        break;
                    case 1:
                        aflag[11] = 1;
                        break;
                    case 2:
                        aflag[0] = 1;
                        aflag[5] = 1;
                        break;
                    case 3:
                        aflag[0] = 1;
                        aflag[9] = 1;
                        break;
                    case 4:
                        aflag[0] = 1;
                        aflag[9] = 1;
                        break;
                    case 5:
                        aflag[6] = 1;
                        aflag[7] = 1;
                        aflag[8] = 1;
                        break;
                    default:
                        break;
                }
            }

            if (flag[i] == -1) {
                switch (i) {
                    case 0:
                        aflag[2] = 1;
                        aflag[3] = 1;
                        break;
                    case 1:
                        aflag[12] = 1;
                        break;
                    case 6:
                        aflag[10] = 1;
                        break;
                    default:
                        break;
                }
            }
        }
        for (int i = 0; i < aflag.length; i++) {
            if (aflag[i] != 0) {
                count++;
            }
        }
        if (count == 0) {
            mSpeechSynthesizer.speak(advice[15]);
        } else {
            mSpeechSynthesizer.speak(advice[13]);
            for (int i = 0; i < aflag.length; i++) {
                if (aflag[i] == 1) {
                    mSpeechSynthesizer.speak(advice[i]);
                }
            }
            mSpeechSynthesizer.speak(advice[14]);
        }
    }

    //获取json数据部分
    private void init() {
        new Thread(new Runnable() {
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                //服务器返回的地址
                Request request = new Request.Builder()
                        .url("http://perfwxc.cn/coolcar/json/tojson.php").build();

                try {
                    Response response = okHttpClient.newCall(request).execute();
                    //获取到数据
                    date = response.body().string();
                    //在线程中没有办法实现主线程操作
                    Message message = new Message();
                    message.what = 1;
                    mHandler.sendMessage(message);
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
        mHandler.sendMessage(message);
    }

    private void initialTts() {
        //获取语音合成对象实例
        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        //设置Context
        this.mSpeechSynthesizer.setContext(this);
        //设置语音合成状态监听
        this.mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        //文本模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
                + TEXT_MODEL_NAME);
        //声学模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
        //本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，
        //仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了离线授权，
        //不需要设置该参数，建议将该行代码删除（离线引擎）
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/"
                + LICENSE_FILE_NAME);
        //请替换为语音开发者平台上注册应用得到的App ID (离线授权)
        this.mSpeechSynthesizer.setAppId(appId);
        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
        this.mSpeechSynthesizer.setApiKey(appKey, secretKey);
        //发音人（在线引擎），可用参数为0,1,2,3。。。
        //（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置Mix模式的合成策略
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_HIGH_SPEED_SYNTHESIZE);
        // 授权检测接口(可以不使用，只是验证授权是否成功)
        AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);
        if (authInfo.isSuccess()) {
            Log.i(TAG, ">>>auth success.");
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            Log.i(TAG, ">>>auth failed errorMsg: " + errorMsg);
        }
        // 引擎初始化tts接口
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        // 加载离线英文资源（提供离线英文合成功能）
        int result =
                mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath
                        + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        Log.i(TAG, ">>>loadEnglishModel result: " + result);
    }

    @Override
    public void onSynthesizeStart(String s) {
        //监听到合成开始
        Log.i(TAG, ">>>onSynthesizeStart()<<< s: " + s);
    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
        //监听到有合成数据到达
        Log.i(TAG, ">>>onSynthesizeDataArrived()<<< s: " + s);
    }

    @Override
    public void onSynthesizeFinish(String s) {
        //监听到合成结束
        Log.i(TAG, ">>>onSynthesizeFinish()<<< s: " + s);
    }

    @Override
    public void onSpeechStart(String s) {
        //监听到合成并开始播放
        Log.i(TAG, ">>>onSpeechStart()<<< s: " + s);
    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {
        //监听到播放进度有变化
        Log.i(TAG, ">>>onSpeechProgressChanged()<<< s: " + s);
    }

    @Override
    public void onSpeechFinish(String s) {
        //监听到播放结束
        Log.i(TAG, ">>>onSpeechFinish()<<< s: " + s);
    }


    @Override
    public void onError(String s, SpeechError speechError) {
        //监听到出错
        Log.i(TAG, ">>>onError()<<< description: " + speechError.description + ", code: " + speechError.code);
    }

    private void initialEnv() {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        }
        File file = new File(mSampleDirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
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

    /**
     * 将工程需要的资源文件拷贝到SD卡中使用（授权文件为临时授权文件，请注册正式授权）
     *
     * @param isCover 是否覆盖已存在的目标文件
     * @param source
     * @param dest
     */
    public void copyFromAssetsToSdcard(boolean isCover, String source, String dest) {
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
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}