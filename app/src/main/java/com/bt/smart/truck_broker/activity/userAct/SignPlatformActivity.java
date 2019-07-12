package com.bt.smart.truck_broker.activity.userAct;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.smart.truck_broker.BaseActivity;
import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.messageInfo.RuleContentInfo;
import com.bt.smart.truck_broker.messageInfo.SignInfo;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.ProgressDialogUtil;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Request;

public class SignPlatformActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tv_title;
    private TextView tv_plat;
    private TextView tv_useName;
    private WebView web_rule;
    private Button bt_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sign_plat);
        setView();
        initData();
    }

    private void setView() {
        img_back = findViewById(R.id.img_back);
        tv_title = findViewById(R.id.tv_title);
        tv_plat = findViewById(R.id.tv_plat);
        tv_useName = findViewById(R.id.tv_useName);
        web_rule = findViewById(R.id.web_rule);
        bt_sign = findViewById(R.id.bt_sign);
    }

    private void initData() {
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(this);
        bt_sign.setOnClickListener(this);
        tv_title.setText("签署协议");
        tv_useName.setText(MyApplication.userName);
        //获取协议内容
        getRuleContent();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.bt_sign:
                //签署协议
                signPlat(2);
                break;
        }
    }

    private void signPlat(int type) {
        RequestParamsFM headParams = new RequestParamsFM();
        headParams.put("X-AUTH-TOKEN", MyApplication.userToken);
        ProgressDialogUtil.startShow(this, "正在提交签署信息");
        HttpOkhUtils.getInstance().doPostWithHeader(NetConfig.SIGNWITHPLATFORM + "/" + MyApplication.userID + "/" + type, headParams, new RequestParamsFM(), new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.startShow(SignPlatformActivity.this, "正在获取协议内容");
                ToastUtils.showToast(SignPlatformActivity.this, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (200 != code) {
                    ToastUtils.showToast(SignPlatformActivity.this, "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                SignInfo signInfo = gson.fromJson(resbody, SignInfo.class);
                ToastUtils.showToast(SignPlatformActivity.this, signInfo.getMessage());
                if (signInfo.isOk()) {
                    finish();
                }
            }
        });
    }

    private void getRuleContent() {
        RequestParamsFM headParam = new RequestParamsFM();
        headParam.put("X-AUTH-TOKEN", MyApplication.userToken);
        ProgressDialogUtil.startShow(this, "正在获取协议内容");
        HttpOkhUtils.getInstance().doGetWithOnlyHeader(NetConfig.CONTENT, headParam, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(SignPlatformActivity.this, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (200 != code) {
                    ToastUtils.showToast(SignPlatformActivity.this, "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                RuleContentInfo ruleContentInfo = gson.fromJson(resbody, RuleContentInfo.class);
                ToastUtils.showToast(SignPlatformActivity.this, ruleContentInfo.getMessage());
                if (ruleContentInfo.isOk()) {
                    tv_plat.setText(ruleContentInfo.getData().getFname());
                    //展示协议内容
                    showRule(ruleContentInfo.getData().getFcontent());
                }
            }
        });
    }

    private void showRule(String content) {
        web_rule.loadDataWithBaseURL("", getNewContent(content), "text/html", "utf-8", "");
        WebSettings settings = web_rule.getSettings();
        settings.setJavaScriptEnabled(true);
        web_rule.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }
}
