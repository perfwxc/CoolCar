package com.wxc.coolcar.Chart;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.wxc.coolcar.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by Supreme on 2018/7/25.
 */

public class Web extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);
        //将屏幕设置为全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setDomStorageEnabled(true);
        loadLocalUrl("https://d.xiumi.us/stage/v5/3xfL0/99316328l");//本地的页面要放在assets目录下

        //loadLocalUrl("file:///android_asset/ce/index.html");//本地的页面要放在assets目录下

    }

    private void loadLocalUrl(String url) {
        WebSettings ws = webView.getSettings();
        //如果访问的页面中有javastripts,则webview必须支持javascripts.
        ws.setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}