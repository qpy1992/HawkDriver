package com.bt.smart.truck_broker;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.bt.smart.truck_broker.activity.FirstActivity;
import com.bt.smart.truck_broker.servicefile.GeTuiIntentService;
import com.bt.smart.truck_broker.servicefile.GeTuiPushService;
import com.igexin.sdk.PushManager;

/**
 * @创建者 AndyYan
 * @创建时间 2018/5/22 8:53
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MyApplication.flag == -1) {//flag为-1说明程序被杀掉
            protectApp();
        }
        MyApplication.listActivity.add(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//保持竖屏
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//取消键盘自动弹出
    }

    protected void protectApp() {
        Intent intent = new Intent(this, FirstActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//清空栈里firstActivity之上的所有activty
        startActivity(intent);
        finish();
        MyApplication.flag = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.listActivity.remove(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PushManager.getInstance().initialize(this.getApplicationContext(), GeTuiPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GeTuiIntentService.class);
        PushManager.getInstance().bindAlias(this, MyApplication.userID);
        PushManager.getInstance().turnOnPush(this);
    }
}
