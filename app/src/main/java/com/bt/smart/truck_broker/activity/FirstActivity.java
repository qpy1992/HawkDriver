package com.bt.smart.truck_broker.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.bt.smart.truck_broker.servicefile.GeTuiIntentService;
import com.bt.smart.truck_broker.servicefile.GeTuiPushService;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.ProgressDialogUtil;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.SpUtils;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;

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
    private int MY_PERMISSIONS_REQUEST_LOCATION = 10086;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 1001;//申请照相机权限结果

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
        if (ActivityCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(FirstActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        if (ActivityCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(FirstActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        }
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

    // DemoPushService.class 自定义服务名称, 核心服务
    private Class userPushService = GeTuiPushService.class;

    private boolean isGetPermission() {
        PackageManager pkgManager = getPackageManager();
        // 读写 sd card 权限非常重要, android6.0默认禁止的, 建议初始化之前就弹窗让用户赋予该权限
        boolean sdCardWritePermission = pkgManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        // read phone state用于获取 imei 设备信息
        boolean phoneSatePermission = pkgManager.checkPermission(Manifest.permission.READ_PHONE_STATE, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        return (sdCardWritePermission & phoneSatePermission);
    }

    @Override
    public void onClick(View view) {
        if (!isGetPermission()) {
            requestPermission();
            return;
        }
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

    private static final int REQUEST_PERMISSION = 0;

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA},
                REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
                PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GeTuiIntentService.class);
                PushManager.getInstance().bindAlias(this, MyApplication.userID);
            } else {
                PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
                PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GeTuiIntentService.class);
                PushManager.getInstance().bindAlias(this, MyApplication.userID);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                    MyApplication.userSFID = loginInfo.getData().getRegisterDriver().getIdNumber();
                    MyApplication.userPhone = loginInfo.getData().getRegisterDriver().getFmobile();
                    MyApplication.checkStatus = loginInfo.getData().getRegisterDriver().getCheckStatus();
                    MyApplication.fcontract = loginInfo.getData().getRegisterDriver().getFcontract();
                    MyApplication.userHeadPic = loginInfo.getData().getRegisterDriver().getFphoto();
                    MyApplication.money = loginInfo.getData().getRegisterDriver().getFaccount();
                    MyApplication.faccountid = loginInfo.getData().getRegisterDriver().getFaccountid();
                    startActivity(new Intent(FirstActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
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
