package com.bt.smart.truck_broker.activity.homeAct;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bt.smart.truck_broker.BaseActivity;
import com.bt.smart.truck_broker.R;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class AuthenticationWebAct extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tv_title;
    private WebView web_show;
    private String webUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        setView();
        setData();
    }

    private void setView() {
        img_back = findViewById(R.id.img_back);
        tv_title = findViewById(R.id.tv_title);
        web_show = findViewById(R.id.web_show);
    }

    private void setData() {
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(this);
        tv_title.setText("人脸认证");
        initWebView();
    }

    private void initWebView() {
        //启用支持javascript
        WebSettings settings = web_show.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);//打开本地缓存提供JS调用,至关重要
        settings.setAllowFileAccess(true);
        // 设置允许JS弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置webview的uri
        webUri = getIntent().getStringExtra("uri");
        if (null != webUri && !"".equals(webUri)) {
            web_show.loadUrl(webUri);
        } else {
            Uri uri = getIntent().getData();
            if (uri != null) {
                String realNameUrl = uri.getQueryParameter("realnameUrl");
                if (!TextUtils.isEmpty(realNameUrl)) {
                    try {
                        web_show.loadUrl(URLDecoder.decode(realNameUrl, "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        web_show.canGoBack();
        web_show.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                if (uri.toString().contains("alipay")) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
                if (uri.getScheme().equals("js")) {
                    if (uri.getAuthority().equals("tsignRealBack")) {
                        // 实名认证结束 返回按钮/倒计时返回/暂不认证
                        Toast.makeText(AuthenticationWebAct.this, "实名认证结束", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        // 腾讯云刷脸兼容性插件
//        WBH5FaceVerifySDK.getInstance().setWebViewSettings(web_show, getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = getIntent().getData();
        if (uri != null) {
            String realNameUrl = uri.getQueryParameter("realnameUrl");
            if (!TextUtils.isEmpty(realNameUrl)) {
                try {
                    web_show.loadUrl(URLDecoder.decode(realNameUrl, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
