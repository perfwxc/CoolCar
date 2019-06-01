package com.wxc.coolcar.Information;

/**
 * Created by Administrator on 2018/6/18.
 */

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.wxc.coolcar.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PHPActivity extends Activity {

    //声明接口地址
    private String url = "https://perfwxc.cn/coolcar/json/new.php";

    private String a;
    private String b;
    private String c;
    private String d;
    private String e;


    private Button btn;
    private ItemGroup et1;
    private ItemGroup et2;
    private ItemGroup et3;
    private ItemGroup et4;
    private ItemGroup et5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_php);

        et1 = findViewById(R.id.name_ig);
        et2 = findViewById(R.id.tel_ig);
        et3 = findViewById(R.id.type_ig);
        et4 = findViewById(R.id.add_ig);
        et5 = findViewById(R.id.call_ig);
        btn = (Button) findViewById(R.id.submit_btn);


        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                //获取输入框中的内容
                a = et1.getText().toString();
                b = et2.getText().toString();
                c = et3.getText().toString();
                d = et4.getText().toString();
                e = et5.getText().toString();

                //替换键值对，这里的键必须和接口中post传递的键一致
                params.add(new BasicNameValuePair("name", a));
                params.add(new BasicNameValuePair("num", b));
                params.add(new BasicNameValuePair("type", c));
                params.add(new BasicNameValuePair("address", d));
                params.add(new BasicNameValuePair("help", e));


                JSONParser jsonParser = new JSONParser();

                try {
                    JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
                    Log.v("uploadsucceed", "uploadsucceed");

                } catch (Exception e) {
                    e.printStackTrace();
                }


                System.out.println("输入的第一个内容：" + a);
                System.out.println("输入的第二个内容：" + b);
                System.out.println("输入的第三个内容：" + c);
                System.out.println("输入的第四个内容：" + d);
                System.out.println("输入的第五个内容：" + e);


            }
        });


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

    }

}