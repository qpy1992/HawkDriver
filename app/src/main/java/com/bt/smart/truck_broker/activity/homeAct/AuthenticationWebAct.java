package com.bt.smart.truck_broker.activity.homeAct;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bt.smart.truck_broker.BaseActivity;
import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.util.checkFaceFile.WBH5FaceVerifySDK;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class AuthenticationWebAct extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tv_title;
    private WebView web_show;
    private int RESULT_AUTHENTICA_CODE = 10111;

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void initWebView() {
        web_show.setWebViewClient(new H5FaceWebViewClient());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            web_show.getSettings().setSafeBrowsingEnabled(false);
        }

        web_show.setWebChromeClient(new H5FaceWebChromeClient(this));

        // 腾讯云刷脸兼容性插件
        WBH5FaceVerifySDK.getInstance().setWebViewSettings(web_show, getApplicationContext());
        processExtraData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (WBH5FaceVerifySDK.getInstance().receiveH5FaceVerifyResult(requestCode, resultCode, data)) {
            return;
        }
    }

    private void processExtraData() {
        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri != null) {
            // 芝麻认证刷脸结束返回获取后续操作页面地址
            String realNameUrl = uri.getQueryParameter("realnameUrl");
            if (!TextUtils.isEmpty(realNameUrl)) {
                try {
                    web_show.loadUrl(URLDecoder.decode(realNameUrl, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        } else {
            String url = intent.getStringExtra("url");
            String title = intent.getStringExtra("title");

            web_show.loadUrl(url);
        }

    }

    private class H5FaceWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url == null) {
                return false;
            }

            Uri uri = Uri.parse(url);
            if (uri.getScheme().equals("http") || uri.getScheme().equals("https")) {
                view.loadUrl(url);
                return true;
            } else if (uri.getScheme().equals("js")) {

                if (uri.getAuthority().equals("tsignRealBack")) {
                    // 实名认证结束 返回按钮/倒计时返回/暂不认证
                    String serviceId = uri.getQueryParameter("serviceId");
                    boolean status = uri.getBooleanQueryParameter("status", false);
                    Toast.makeText(AuthenticationWebAct.this, "实名认证结束 serviceId = " + serviceId + " status = " + status, Toast.LENGTH_LONG).show();
                    if (status){
                        MyApplication.checkFace = true;
                        setResult(RESULT_AUTHENTICA_CODE);
                        finish();
                    }
                }

                return true;
            } else {
                // alipays://platformapi/startapp?appId=20000067&pd=NO&url=https%3A%2F%2Fzmcustprod.zmxy.com.cn%2Fcertify%2Fbegin.htm%3Ftoken%3DZM201811133000000050500431389414
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }

        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    }

    public class H5FaceWebChromeClient extends WebChromeClient {
        private Activity activity;

        public H5FaceWebChromeClient(Activity mActivity) {
            this.activity = mActivity;
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        @TargetApi(8)
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            if (WBH5FaceVerifySDK.getInstance().recordVideoForApiBelow21(uploadMsg, acceptType, activity)) {
                return;
            }

        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            if (WBH5FaceVerifySDK.getInstance().recordVideoForApiBelow21(uploadMsg, acceptType, activity)) {
                return;
            }
        }

        @TargetApi(21)
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (WBH5FaceVerifySDK.getInstance().recordVideoForApi21(webView, filePathCallback, activity, fileChooserParams)) {
                return true;
            }
            return true;
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processExtraData();
    }
}
