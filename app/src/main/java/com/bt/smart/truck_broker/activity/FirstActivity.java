package com.bt.smart.truck_broker.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bt.smart.truck_broker.MainActivity;
import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.messageInfo.LoginInfo;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.ProgressDialogUtil;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.SpUtils;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Request;

/**
 * @创建者 AndyYan
 * @创建时间 2018/10/29 15:37
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class FirstActivity extends Activity implements View.OnClickListener {
    private TextView tv_new;
    private TextView tv_old;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.first_actiivty);
        MyApplication.flag = 0;
        getView();
        setData();
        //        verifyStoragePermissions(this);
    }

    private void getView() {
        tv_new = (TextView) findViewById(R.id.tv_new);
        tv_old = (TextView) findViewById(R.id.tv_old);
    }

    private void setData() {
        tv_new.setOnClickListener(this);
        tv_old.setOnClickListener(this);

        Boolean isRemem = SpUtils.getBoolean(this, "isRem", false);
        if (isRemem) {
            String name = SpUtils.getString(this, "name");
            String psd = SpUtils.getString(this, "psd");
            //直接登录
            loginToService(name, psd);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_new:
                //跳转注册界面
                Intent intent1 = new Intent(this, RegisterActivity.class);
                intent1.putExtra("kind", "rgs");
                startActivity(intent1);
                //finish();
                break;
            case R.id.tv_old:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void loginToService(String phone, final String psd) {
        ProgressDialogUtil.startShow(FirstActivity.this, "正在登录请稍后");
        RequestParamsFM params = new RequestParamsFM();
        params.put("fmobile", phone);
        params.put("password", psd);
        HttpOkhUtils.getInstance().doPost(NetConfig.LOGINURL, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(FirstActivity.this, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(FirstActivity.this, "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                LoginInfo loginInfo = gson.fromJson(resbody, LoginInfo.class);
                //ToastUtils.showToast(FirstActivity.this, loginInfo.getMessage());
                if (loginInfo.isOk()) {
                    MyApplication.userToken = loginInfo.getData().getToken();
                    MyApplication.userID = loginInfo.getData().getRegisterDriver().getId();
                    MyApplication.userName = loginInfo.getData().getRegisterDriver().getFname();
                    MyApplication.userPhone = loginInfo.getData().getRegisterDriver().getFmobile();
                    MyApplication.checkStatus = loginInfo.getData().getRegisterDriver().getCheckStatus();
                    MyApplication.userHeadPic = loginInfo.getData().getRegisterDriver().getFphoto();
                    startActivity(new Intent(FirstActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private static final int      REQUEST_EXTERNAL_STORAGE = 1;
    private static       String[] PERMISSIONS_STORAGE      = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    public void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
