package com.wxc.coolcar.Health;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.wxc.coolcar.Chart.ChartActivity;
import com.wxc.coolcar.Information.JSONParser;
import com.wxc.coolcar.R;
import com.wxc.coolcar.User.User;
import com.wxc.coolcar.Util.NotificationUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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

public class MActivity extends AppCompatActivity implements SpeechSynthesizerListener {
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 200;
    public static final int MY_ID = R.drawable.timg2;
    private static final String TAG = "MActivity";
    private static final String SAMPLE_DIR_NAME = "baiduTTS";       //百度语音合成所需文件
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license_2016-04-05";
    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";
    public static int AAA[] = new int[100];       //创建数组，防止重复拨号
    private static int flag = 0;
    public String date;
    public String date1;
    public ListView lv;
    public ArrayList<User> listuser;
    public ArrayList<String> UserTemperatureList;
    public ArrayList<String> HeartRateList;
    public ArrayList<String> HighBloodPressureList;
    public ArrayList<String> LowBloodPressureList;
    public ArrayList<String> LuxList;
    public ArrayList<String> IdeList;
    public ArrayList<String> gg;
    public ArrayList<String> test;
    public ProgressDialog mProgressDialog;
    protected String appId = "11319692";
    protected String appKey = "iRwaZnUB2fiGI7i1fdMttAld";           //百度语音合成识别码
    protected String secretKey = "SjNwKCkIItx4ABRejGSP9kciwbQc51VQ";
    boolean stopThread = false;
    Timer timer = new Timer();
    private String url = "https://perfwxc.cn/coolcar/json/delete.php";    //修改数据库数据php代码
    private SpeechSynthesizer mSpeechSynthesizer;//百度语音合成客户端
    private String mSampleDirPath;
    /**
     * 定义线程池，异步操作加载点
     */
    private int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private int KEEP_ALIVE_TIME = 1;
    private TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private ExecutorService executorService = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, new LinkedBlockingDeque<Runnable>(128));
    private Adapter.MyClickListener nListener = new Adapter.MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            String Lux = listuser.get(position).getLux();
            String HeartRate = listuser.get(position).getHeartRate();
            String HighBloodPressure = listuser.get(position).getHighBloodPressure();
            String LowBloodPressure = listuser.get(position).getLowBloodPressure();
            String SaO2 = listuser.get(position).getSaO2();
            Intent intentToDetail = new Intent(MActivity.this, Detail.class);
            double userTemperature = Double.parseDouble(listuser.get(position).getUserTemperature());
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
                    userTemperature = Double.parseDouble(listuser.get(position).getUserTemperature());
            }
            String sUserTemperature = String.valueOf(userTemperature);
            intentToDetail.putExtra("iUserTemperature", sUserTemperature);
            intentToDetail.putExtra("iHeartRate", HeartRate);
            intentToDetail.putExtra("iHighBloodPressure", HighBloodPressure);
            intentToDetail.putExtra("iLowBloodPressure", LowBloodPressure);
            intentToDetail.putExtra("iSaO2", SaO2);
            intentToDetail.putExtra("iLux", Lux);
            startActivity(intentToDetail);

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
    private Adapter.MyClickListener mListener = new Adapter.MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            double userTemperature = Double.parseDouble(listuser.get(position).getUserTemperature());
            int Lux = Integer.parseInt(listuser.get(position).getLux());
            int HeartRate = Integer.parseInt(listuser.get(position).getHeartRate());
            int HighBloodPressure = Integer.parseInt(listuser.get(position).getHighBloodPressure());
            int LowBloodPressure = Integer.parseInt(listuser.get(position).getLowBloodPressure());
            int SaO2 = Integer.parseInt(listuser.get(position).getSaO2());


            SpeakAdvice(userTemperature, HeartRate, HighBloodPressure, LowBloodPressure, SaO2, Lux);

            if (userTemperature < 36.0) {
                Toast.makeText(MActivity.this, "您的体温低于正常水平!!!", Toast.LENGTH_SHORT).show();
            }
            if (userTemperature > 37.0) {
                Toast.makeText(MActivity.this, "您的体温偏高，请及时就医!!!", Toast.LENGTH_SHORT).show();
            }
            if (userTemperature >= 36.0 && userTemperature <= 37.0) {
                Toast.makeText(MActivity.this, "您的体温正常。", Toast.LENGTH_SHORT).show();
            }
            if (HeartRate < 60) {
                Toast.makeText(MActivity.this, "您的心率偏低!!!", Toast.LENGTH_SHORT).show();
            }
            if (HeartRate >= 60 && HeartRate <= 100) {
                Toast.makeText(MActivity.this, "您的心率正常。", Toast.LENGTH_SHORT).show();
            }
            if (HeartRate > 100) {
                Toast.makeText(MActivity.this, "您的心率偏高!!!", Toast.LENGTH_SHORT).show();
            }
            if (HighBloodPressure < 90) {
                Toast.makeText(MActivity.this, "您的高血压过低!!!", Toast.LENGTH_SHORT).show();
            }
            if (HighBloodPressure >= 90 && HighBloodPressure <= 140) {
                Toast.makeText(MActivity.this, "您的高血压正常。", Toast.LENGTH_SHORT).show();
            }
            if (HighBloodPressure > 140) {
                Toast.makeText(MActivity.this, "您的高血压偏高!!!", Toast.LENGTH_SHORT).show();
            }
            if (LowBloodPressure < 60) {
                Toast.makeText(MActivity.this, "您的低血压偏低!!!", Toast.LENGTH_SHORT).show();
            }
            if (LowBloodPressure >= 60 && LowBloodPressure <= 90) {
                Toast.makeText(MActivity.this, "您的低血压正常。", Toast.LENGTH_SHORT).show();
            }
            if (LowBloodPressure > 90) {
                Toast.makeText(MActivity.this, "您的低血压偏高。", Toast.LENGTH_SHORT).show();
            }
            if (Lux >= 20) {
                Toast.makeText(MActivity.this, "您体内的酒精含量超标，请勿酒驾!!!", Toast.LENGTH_SHORT).show();
            }

            //            Toast.makeText(MActivity.this, "您的体温正常!", Toast.LENGTH_SHORT).show();
            //            Toast.makeText(MActivity.this, "您的心率正常!", Toast.LENGTH_SHORT).show();
            //            Toast.makeText(MActivity.this, "您的高血压正常!", Toast.LENGTH_SHORT).show();
            //            Toast.makeText(MActivity.this, "您的低血压正常!", Toast.LENGTH_SHORT).show();
            //            Toast.makeText(MActivity.this, "您的健康状况良好\n!", Toast.LENGTH_SHORT).show();

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
                    lv.setAdapter(new Adapter(MActivity.this, listuser, mListener, nListener));//调用listview适配器
                    Adapter qq = new Adapter(MActivity.this, listuser, mListener, nListener);
                    lv.setAdapter(qq);
                    setListViewHeightBasedOnChildren(lv);
                    lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                            final int aaa = Integer.parseInt(listuser.get(position).getIde());
                            //删除数据方法
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
                            Toast.makeText(MActivity.this, "数据已删除！", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });


                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            double userTemperature = Double.parseDouble(listuser.get(position).getUserTemperature());
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
                            }
                            double Lux = new BigDecimal(Double.parseDouble(listuser.get(position).getLux()) / 10000).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            int HeartRate = Integer.parseInt(listuser.get(position).getHeartRate());
                            int highBloodPressure = Integer.parseInt(listuser.get(position).getHighBloodPressure());
                            int lowBloodPressure = Integer.parseInt(listuser.get(position).getLowBloodPressure());
                            int SaO2 = Integer.parseInt(listuser.get(position).getSaO2());
                            Toast.makeText(MActivity.this, "您的体温为" + userTemperature + "摄氏度"
                                            + "\n您的心率为" + HeartRate + "次每分"
                                            + "\n您的高血压为" + highBloodPressure + "毫米汞柱"
                                            + "\n您的低血压为" + lowBloodPressure + "毫米汞柱"
                                            + "\n您体内的酒精含量为" + Lux + "克每升"
                                            + "\n您体内的血氧饱和度为" + SaO2 + "%"
                                    , Toast.LENGTH_LONG).show();

                            mSpeechSynthesizer.speak(
                                    "您的体温为" + userTemperature + "摄氏度"
                                            + "。。您的心率为" + HeartRate + "次每分"
                                            + "。。您的高血压为" + highBloodPressure + "毫米汞柱"
                                            + "。。您的低血压为" + lowBloodPressure + "毫米汞柱"
                                            + "。。您体内的酒精含量为" + Lux + "克每升"
                                            + "。。您体内的血氧饱和度为百分之" + SaO2
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
            {
                init();
                for (int i = 0; i < listuser.size(); i++) {
                    double userTemperature = Double.parseDouble(UserTemperatureList.get(i));
                    double Lux = Double.parseDouble(LuxList.get(i));
                    int heartRate = Integer.parseInt(HeartRateList.get(i));
                    int highBloodPressure = Integer.parseInt(HighBloodPressureList.get(i));
                    int lowBloodPressure = Integer.parseInt(LowBloodPressureList.get(i));
                    int ide = Integer.parseInt(IdeList.get(i));

                    if (ide == 99) call(test.get(4));


                }

            }
        }
    };

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获得adapter
        Adapter adapter = (Adapter) listView.getAdapter();
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
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar1);   //伸缩折叠布局
        ImageView fruitImageView = (ImageView) findViewById(R.id.fruit_image_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);    //使左上角图标显示
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopThread = true;
                finish();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);    //设置浮动按钮
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToChart = new Intent(MActivity.this, ChartActivity.class);

                Bundle userTemperature = new Bundle();
                Bundle heartRate = new Bundle();
                Bundle highBloodPressure = new Bundle();
                Bundle lowBloodPressure = new Bundle();

                userTemperature.putStringArrayList("userTemperature", UserTemperatureList);
                heartRate.putStringArrayList("heartRate", HeartRateList);
                highBloodPressure.putStringArrayList("highBloodPressure", HighBloodPressureList);
                lowBloodPressure.putStringArrayList("lowBloodPressure", LowBloodPressureList);

                intentToChart.putExtras(userTemperature);
                intentToChart.putExtras(heartRate);
                intentToChart.putExtras(highBloodPressure);
                intentToChart.putExtras(lowBloodPressure);

                startActivity(intentToChart);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("您的健康数据如下：");
        Glide.with(this).load(MY_ID).into(fruitImageView);
        //创建ProgressDialog
        createProgressDialog();
        //启动线程
        executorService.execute(mRunnable);
        timer.schedule(task, 1000 * 4, 1000 * 10); //启动timer，并设置事件间隔


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

    public void SpeakAdvice(double userTemperature, int HeartRate, int HighBloodPressure,
                            int LowBloodPressure, int SaO2, int Lux) {
        //userTemperature, HeartRate, HighBloodPressure, LowBloodPressure, SaO2, Lux
        int[] flag = new int[5];
        String[] advice = new String[]{
                "..少食多餐。",
                "..多吃低热能食物，少吃腌制品和甜食。",
                "..戒烟酒。 ",
                "..注意休息。",
                "..清淡饮食。",
                "..多吃水果蔬菜。",
                "..若为发烧，及时就医。",
                "..多属风寒，用麻黄汤等方剂调节。",
                "..注意锻炼。",
                "..多吃蛋白质丰富的食物，适当饮用咖啡。",
                "..多食流性食物。",
                "..及时饮用茶和热汤等。",
                "..您的健康状况良好。",
                "..您的健康状况有待提高，请您",
                "..易驾持续为您保驾护航！",
                "..您的身体状况不太理想请及时查看详细建议"
        };
        int[] aflag = new int[advice.length];

        if (userTemperature < 36.3) flag[0] = -1;
        if (userTemperature > 37.2) flag[0] = 1;
        if (HeartRate < 60) flag[1] = -1;
        if (HeartRate > 90) flag[1] = 1;
        if (HighBloodPressure >= 130 || LowBloodPressure >= 85) flag[2] = 1;
        if (HighBloodPressure <= 100 || LowBloodPressure <= 70) flag[2] = -1;
        if (SaO2 < 94) flag[3] = -1;
        if (Lux >= 20) flag[4] = 1;
        int count = 0;
        for (int i = 0; i <= 4; i++) {
            if (flag[i] != 0) {
                count++;
            }
        }
        if (count <= 2) {
            //异常依次播报
            for (int i = 0; i <= 4; i++) {
                if (flag[i] == 0) continue;
                if (flag[i] == 1) {
                    switch (i) {
                        case 0:
                            aflag[4] = 1;
                            aflag[5] = 1;
                            aflag[6] = 1;
                            break;
                        case 1:
                            aflag[2] = 1;
                            aflag[3] = 1;
                            aflag[4] = 1;
                            break;
                        case 2:
                            aflag[0] = 1;
                            aflag[4] = 1;
                            aflag[5] = 1;
                            break;
                        case 4:
                            aflag[5] = 1;
                            aflag[11] = 1;
                            break;
                        default:
                            break;
                    }
                }
                if (flag[i] == -1) {
                    switch (i) {
                        case 0:
                            aflag[4] = 1;
                            aflag[7] = 1;
                            break;
                        case 1:
                            aflag[2] = 1;
                            aflag[3] = 1;
                            aflag[4] = 1;
                            break;
                        case 2:
                            aflag[8] = 1;
                            aflag[9] = 1;
                            break;
                        case 3:
                            aflag[10] = 1;
                            break;
                        default:
                            break;
                    }
                }
            }
            if (count == 0) {
                mSpeechSynthesizer.speak(advice[12]);
            } else {
                mSpeechSynthesizer.speak(advice[13]);
                for (int i = 0; i < aflag.length; i++) {
                    if (aflag[i] == 1) {
                        mSpeechSynthesizer.speak(advice[i]);
                    }
                }
            }
            mSpeechSynthesizer.speak(advice[14]);
        } else {
            mSpeechSynthesizer.speak(advice[15]);
        }
        //            mSpeechSynthesizer.speak("您的健康状况有待提高，" +
        //                    "请您.多吃水果蔬菜，" +
        //                    "注意锻炼，多吃蛋白质丰富的食物，" +
        //                    "适当饮用咖啡，及时饮用茶和热汤等，" +
        //                    ".易驾持续为您保驾护航\n");

    }

    public void onClick3(View v) {
        NotificationUtil.showFullScreen(this);
    }

    public void call(String abc) {
        // 检查是否获得了权限（Android6.0运行时权限）

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
        // 返回值：
        //如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
        //如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
        //如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
        // 弹窗需要解释为何需要该权限，再次请求授权
                Toast.makeText(this, "请授权！", Toast.LENGTH_LONG).show();
        // 帮跳转到该应用的设置界面，让用户手动授权
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            } else {
                // 不需要解释为何需要该权限，直接请求授权
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        } else {
            // 已经获得授权，可以打电话
            CallPhone(abc);
        }
    }

    private void CallPhone(String aa) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        //url:统一资源定位符
        //uri:统一资源标示符（更广）
        intent.setData(Uri.parse("tel:" + aa));
        //开启系统拨号器
        startActivity(intent);
    }

    //获取json数据部分
    private void init() {
        new Thread(new Runnable() {
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                //服务器返回的地址
                Request request = new Request.Builder()
                        .url("https://perfwxc.cn/coolcar/json/tojson.php").build();
                Request request1 = new Request.Builder()
                        .url("https://perfwxc.cn/coolcar/json/info_json.php").build();

                try {
                    Response response = okHttpClient.newCall(request).execute();
                    Response response1 = okHttpClient.newCall(request1).execute();

                    //获取到数据
                    date = response.body().string();
                    date1 = response1.body().string();

                    //在线程中没有办法实现主线程操作
                    Message message = new Message();
                    message.what = 1;
                    mHandler.sendMessage(message);
                    //把数据传入解析josn数据方法
                    Gsonjx(date);
                    Gsonjx1(date1);

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
        UserTemperatureList = new ArrayList<>();
        HeartRateList = new ArrayList<>();
        HighBloodPressureList = new ArrayList<>();
        LowBloodPressureList = new ArrayList<>();
        LuxList = new ArrayList<>();
        IdeList = new ArrayList<>();

        for (JsonElement user : jsonArray) {
            User userBean = gson.fromJson(user, User.class);
            listuser.add(userBean);
            UserTemperatureList.add(userBean.getUserTemperature());
            HeartRateList.add(userBean.getHeartRate());
            HighBloodPressureList.add(userBean.getHighBloodPressure());
            LowBloodPressureList.add(userBean.getLowBloodPressure());
            LuxList.add(userBean.getLux());
            IdeList.add(userBean.getIde());

        }
        Message message = new Message();
        message.what = 2;
        mHandler.sendMessage(message);
    }


    private void Gsonjx1(String date) { //gson解析部分
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(date).getAsJsonArray();
        Gson gson = new Gson();
        test = new ArrayList<>();


        for (JsonElement user : jsonArray) {
            User userBean = gson.fromJson(user, User.class);
            test.add(userBean.getEmergencyPhone());
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