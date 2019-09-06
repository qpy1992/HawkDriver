package com.bt.smart.truck_broker.activity.userAct;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.messageInfo.ApkInfo;
import com.bt.smart.truck_broker.messageInfo.RuleContentInfo;
import com.bt.smart.truck_broker.util.UpApkDataFile.UpdateAppUtil;
import com.bt.smart.truck_broker.utils.CommonUtil;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.ProgressDialogUtil;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.Request;

public class AboutUsActivity extends AppCompatActivity {
    private static String TAG = "AboutUsActivity";
    private WebView wv_aboutus;
    private ImageView iv_back;
    private TextView tv_title,tv_checkUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setViews();
        setListeners();
    }

    protected void setViews(){
        iv_back = findViewById(R.id.img_back);
        iv_back.setVisibility(View.VISIBLE);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.aboutus));
        wv_aboutus = findViewById(R.id.wv_aboutus);
        tv_checkUpdate = findViewById(R.id.tv_checkUpdate);
        getAboutUs();
    }

    protected void setListeners(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_checkUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getNewApkInfo();
            }
        });
    }

    protected void getAboutUs(){
        RequestParamsFM headParam = new RequestParamsFM();
        headParam.put("X-AUTH-TOKEN", MyApplication.userToken);
        ProgressDialogUtil.startShow(this, "正在获取协议内容");
        HttpOkhUtils.getInstance().doGetWithOnlyHeader(NetConfig.CONTENT+"/A0007", headParam, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(AboutUsActivity.this, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (200 != code) {
                    ToastUtils.showToast(AboutUsActivity.this, "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                RuleContentInfo ruleContentInfo = gson.fromJson(resbody, RuleContentInfo.class);
                ToastUtils.showToast(AboutUsActivity.this, ruleContentInfo.getMessage());
                if (ruleContentInfo.isOk()) {
                    //展示协议内容
                    showRule(ruleContentInfo.getData().getFcontent());
                }
            }
        });
    }

    protected void getNewApkInfo() {
        RequestParamsFM headparam = new RequestParamsFM();
        headparam.put(NetConfig.HEAD,MyApplication.userToken);
        HttpOkhUtils.getInstance().doGetWithOnlyHeader(NetConfig.CHECKUPDATE + "/1", headparam, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                Log.i(TAG,"网络错误!");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                if(code!=200){
                    Log.i(TAG,"网络错误!");
                    return;
                }
                ApkInfo info = new Gson().fromJson(resbody,ApkInfo.class);
                if(getAppVersionCode(AboutUsActivity.this)<info.getData().getVersionCode()){
                    showDialogToDown(info);
                }else{
                    ToastUtils.showToast(AboutUsActivity.this,"已是最新版本");
                }
            }
        });
    }

    //获取当前版本号
    protected int getAppVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    protected void showDialogToDown(ApkInfo apkInfo) {
        MyApplication.loadUrl = NetConfig.IMG_HEAD + apkInfo.getData().getApkPath();
        UpdateAppUtil.from(this)
                .serverVersionCode(apkInfo.getData().getVersionCode())  //服务器versionCode
                .serverVersionName(apkInfo.getData().getVersionName()) //服务器versionName
                .apkPath(MyApplication.loadUrl) //最新apk下载地址
                .updateInfo(apkInfo.getData().getApkInfo())
                .update();
    }

    protected void showRule(String content){
        wv_aboutus.loadDataWithBaseURL("", CommonUtil.getNewContent(content), "text/html", "utf-8", "");
        WebSettings settings = wv_aboutus.getSettings();
        settings.setJavaScriptEnabled(true);
        wv_aboutus.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
