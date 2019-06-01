package com.wxc.coolcar.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wxc.coolcar.Main.MainActivity;
import com.wxc.coolcar.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StartActivity extends Activity {

    static String code2 = "NOONEWILLKNOWWHATSTHIS";
    int time;
    int tpl_id = 230646;
    String nationcode = "86";
    String mobile = "13296507815";
    String pass;
    String strSrc;
    String sig;
    //
    int i = 30;
    // 手机号输入框
    private EditText inputPhoneEt;
    // 验证码输入框
    private EditText inputCodeEt;
    // 获取验证码按钮
    private Button requestCodeBtn;
    /**
     *
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                requestCodeBtn.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                requestCodeBtn.setText("获取验证码");
                requestCodeBtn.setClickable(true);
                i = 30;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);

            }
        }
    };
    // 注册按钮
    private Button commitBtn;

    /**
     * SHA加密
     *
     * @param strSrc 明文
     * @return 加密之后的密文
     */

    public static String shaEncrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-256");// 将此换成SHA-1、SHA-512、SHA-384等参数
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    /**
     * byte数组转换为16进制字符串
     *
     * @param bts 数据源
     * @return 16进制字符串
     */
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    /**
     * 判断一个字符串的位数
     *
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取unix 时间戳（单位：秒）
        long timeStampSec = System.currentTimeMillis() / 1000;
        String timestamp = String.format("%010d", timeStampSec);
        time = Integer.parseInt(timestamp);
        strSrc = "appkey=fd63df8f1b3813f8af0533686a3624f0&random=7226249334&time=" + timestamp + "&mobile=13296507815";
        Log.e("车载精灵", strSrc);
        sig = shaEncrypt(strSrc);
        inputPhoneEt = findViewById(R.id.login_input_phone_et);
        inputCodeEt = findViewById(R.id.login_input_code_et);
        requestCodeBtn = findViewById(R.id.login_request_code_btn);
        requestCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取unix 时间戳（单位：秒）
                long timeStampSec = System.currentTimeMillis() / 1000;
                String timestamp = String.format("%010d", timeStampSec);
                time = Integer.parseInt(timestamp);
                String phoneNum = inputPhoneEt.getText().toString();
                strSrc = "appkey=fd63df8f1b3813f8af0533686a3624f0&random=7226249334&time=" + timestamp + "&mobile=" + phoneNum;
                sig = shaEncrypt(strSrc);
                if (!judgePhoneNums(phoneNum)) {
                    return;
                }
                Send();
                requestCodeBtn.setClickable(false);
                requestCodeBtn.setText("重新发送(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
            }
        });
        commitBtn = findViewById(R.id.login_commit_btn);
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("车载精灵", code2);
                pass = inputCodeEt.getText().toString();
                Log.e("车载精灵输入", pass);
                if (pass.equals(code2)) {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                }        //createProgressBar();
            }
        });

    }

    public void Send() {
        Random rand = new Random();
        int code1 = rand.nextInt(9999 - 1000 + 1) + 1000;
        code2 = String.valueOf(code1);
        Log.e("车载精灵", code2);
        String code[] = {code2, "2"};
        Tg tg = new Tg();
        tg.setTime(time);
        tg.setTpl_id(tpl_id);
        tg.setSig(sig);
        tg.setParams(code);
        tg.setTel(new Tg.TelBean(inputPhoneEt.getText().toString(), nationcode));
        //Tel tel=new Tel();
        // tel.setTel(mobile);
        Gson gson = new Gson();
        String route = gson.toJson(tg);//通过Gson将Bean转化为Json字符串形式
        //String route= gson.toJson(tel);//通过Gson将Bean转化为Json字符串形式
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://yun.tim.qq.com/v5/tlssmssvr/")
                //.baseUrl("http://btsc.botian120.com/admin/sms/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostRoute postRoute = retrofit.create(PostRoute.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), route);
        Call<Tl> call = postRoute.postFlyRoute("1400096345", "7226249334", body);
        call.enqueue(new Callback<Tl>() {
            @Override
            public void onResponse(Call<Tl> call, Response<Tl> response) {
                Log.e("发送结果", "-----------------------" + response.body().getErrmsg());//这里是用于测试，服务器返回的数据就是提交的数据。
            }

            @Override
            public void onFailure(Call<Tl> call, Throwable t) {
                Log.e("sssss", "tyhtr");
            }
        });
    }

    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * progressbar
     */
    private void createProgressBar() {
        FrameLayout layout = (FrameLayout) findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        ProgressBar mProBar = new ProgressBar(this);
        mProBar.setLayoutParams(layoutParams);
        mProBar.setVisibility(View.VISIBLE);
        layout.addView(mProBar);
    }


}
