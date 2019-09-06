package com.bt.smart.truck_broker.activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.util.DownloadUtil;

public class XieyiActivity extends AppCompatActivity {

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xieyi);
        wv = findViewById(R.id.wv);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        String path = getIntent().getStringExtra("path");
//        download(NetConfig.IMG_HEAD+MyApplication.fcontract);
        wv.loadUrl("file:///android_asset/mypdf.html?pdfpath="+NetConfig.IMG_HEAD+path);
    }

    private void download(String url) {
        DownloadUtil.download(url, Environment.getExternalStorageDirectory() + "/temp.pdf", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(final String path) {
                Log.d("MainActivity", "onDownloadSuccess: " + path);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        preView(path);
                    }
                });
            }

            @Override
            public void onDownloading(int progress) {
                Log.d("MainActivity", "onDownloading: " + progress);
            }

            @Override
            public void onDownloadFailed(String msg) {
                Log.d("MainActivity", "onDownloadFailed: " + msg);
            }
        });
    }

    /**
     * 预览pdf
     *
     * @param pdfUrl url或者本地文件路径
     */
    private void preView(String pdfUrl) {
        wv.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + pdfUrl);
    }
}
