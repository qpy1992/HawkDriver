package com.bt.smart.truck_broker;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bt.smart.truck_broker.fragment.home.Home_F;
import com.bt.smart.truck_broker.fragment.sameDay.SameDay_F;
import com.bt.smart.truck_broker.fragment.serviceApply.ServApply_F;
import com.bt.smart.truck_broker.fragment.user.User_F;
import com.bt.smart.truck_broker.messageInfo.NewApkInfo;
import com.bt.smart.truck_broker.servicefile.SendLocationService;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.MyAlertDialogHelper;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private long        exitTime   = 0;//记录点击物理返回键的时间
    // 界面底部的菜单按钮
    private ImageView[] bt_menu    = new ImageView[4];
    // 界面底部的未选中菜单按钮资源
    private int[]       select_off = {R.drawable.bt_menu_0_select, R.drawable.bt_menu_1_select, R.drawable.bt_menu_2_select, R.drawable.bt_menu_3_select};
    // 界面底部的选中菜单按钮资源
    private int[]       select_on  = {R.drawable.icon_home_sel, R.drawable.icon_same_day_sel, R.drawable.icon_service_apply_sel, R.drawable.icon_me_sel};
    // 界面底部的菜单按钮id
    private int[]       bt_menu_id = {R.id.iv_menu_0, R.id.iv_menu_1, R.id.iv_menu_2, R.id.iv_menu_3};
    //底部布局按钮的id
    private int[]       linear_id  = {R.id.linear0, R.id.linear1, R.id.linear2, R.id.linear3};
    //底部字体
    private TextView tv_menu_0, tv_menu_1, tv_menu_2, tv_menu_3;
    private List<TextView>      tv_menu;
    private LinearLayout        linear_home;//配送大厅
    private LinearLayout        linear_shopp;//当天货源
    private LinearLayout        linear_play;//服务
    private LinearLayout        linear_mine;//个人中心
    private Home_F              home_F;//配送大厅
    private SameDay_F           sameDay_F;//当天货源
    private ServApply_F         servApply_F;//服务
    private User_F              user_F;//个人中心
    private Handler             mProhandler;//定时播报经纬度
    private SendLocationService service;//定位服务
    private boolean             isBinded;//是否已绑定服务
    private double              lonData;//经度
    private double              latData;//纬度
    //服务连接监听回调
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            isBinded = true;
            ToastUtils.showToast(MainActivity.this, "连上服务");
            SendLocationService.MyBinder myBinder = (SendLocationService.MyBinder) iBinder;
            service = myBinder.getService();
            service.startGetLoaction();
            service.creatNotifacation();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBinded = false;
            ToastUtils.showToast(MainActivity.this, "服务断开");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        setData();
    }

    private void setView() {
        linear_home = findViewById(R.id.linear0);
        linear_shopp = findViewById(R.id.linear1);
        linear_play = findViewById(R.id.linear2);
        linear_mine = findViewById(R.id.linear3);
        tv_menu_0 = findViewById(R.id.tv_menu_0);
        tv_menu_1 = findViewById(R.id.tv_menu_1);
        tv_menu_2 = findViewById(R.id.tv_menu_2);
        tv_menu_3 = findViewById(R.id.tv_menu_3);
        //获取最新的版本
        //        getNewApkInfo();
    }

    private void setData() {
        tv_menu = new ArrayList<>();
        tv_menu.add(tv_menu_0);
        tv_menu.add(tv_menu_1);
        tv_menu.add(tv_menu_2);
        tv_menu.add(tv_menu_3);
        // 找到底部菜单的按钮并设置监听
        for (int i = 0; i < bt_menu.length; i++) {
            bt_menu[i] = (ImageView) findViewById(bt_menu_id[i]);
        }
        linear_home.setOnClickListener(this);
        linear_shopp.setOnClickListener(this);
        linear_play.setOnClickListener(this);
        linear_mine.setOnClickListener(this);
        // 初始化默认显示的界面
        if (home_F == null) {
            home_F = new Home_F();
            addFragment(home_F);
            showFragment(home_F);
            changeTVColor(0);
        } else {
            showFragment(home_F);
            changeTVColor(0);
        }
        // 设置默认首页为点击时的图片
        bt_menu[0].setImageResource(select_on[0]);

        startSendLanAlat();
        //初始化定时刷新器
        //        initHandlerPost();
        MyApplication.needLocationService = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear0://智控界面
                if (home_F == null) {
                    home_F = new Home_F();
                    // 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
                    addFragment(home_F);
                    showFragment(home_F);
                    //改变字体
                    changeTVColor(0);
                } else {
                    if (home_F.isHidden()) {
                        showFragment(home_F);
                        changeTVColor(0);
                    }
                }
                break;
            case R.id.linear1:// 好品界面
                if (sameDay_F == null) {
                    sameDay_F = new SameDay_F();
                    // 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
                    addFragment(sameDay_F);
                    showFragment(sameDay_F);
                    changeTVColor(1);
                } else {
                    if (sameDay_F.isHidden()) {
                        showFragment(sameDay_F);
                        changeTVColor(1);
                    }
                }
                break;
            case R.id.linear2://适玩界面
                if (servApply_F == null) {
                    servApply_F = new ServApply_F();
                    // 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
                    addFragment(servApply_F);
                    showFragment(servApply_F);
                    changeTVColor(2);
                } else {
                    if (servApply_F.isHidden()) {
                        showFragment(servApply_F);
                        changeTVColor(2);
                    }
                }
                break;
            case R.id.linear3:// 个人界面
                if (user_F == null) {
                    user_F = new User_F();
                    // 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
                    addFragment(user_F);
                    showFragment(user_F);
                    changeTVColor(3);
                } else {
                    if (user_F.isHidden()) {
                        showFragment(user_F);
                        changeTVColor(3);
                    }
                }
                break;
        }
        // 设置按钮的选中和未选中资源
        for (int i = 0; i < bt_menu.length; i++) {
            bt_menu[i].setImageResource(select_off[i]);
            if (view.getId() == linear_id[i]) {
                bt_menu[i].setImageResource(select_on[i]);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null == mProhandler) {
            initHandlerPost();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mProhandler)
            mProhandler.removeCallbacksAndMessages(null);
        mProhandler = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != home_F)
            home_F.onActivityResult(requestCode, resultCode, data);
        if (null != sameDay_F)
            sameDay_F.onActivityResult(requestCode, resultCode, data);
    }

    //上传经纬度
    private void initHandlerPost() {
        mProhandler = new Handler();
        mProhandler.postDelayed(new Runnable() {
            public void run() {
                mProhandler.postDelayed(this, 1000 * 60 * 30);//递归执行//半小时执行一次
                if (MyApplication.needLocationService && isBinded && null != service && null != service.getLocation()) {
                    lonData = service.getLocation().getLongitude();
                    latData = service.getLocation().getLatitude();
                    //上传经纬度
                    //                    ToastUtils.showToast(MainActivity.this, "经度：" + lonData + "纬度：" + latData);
                    upDataLocation(lonData, latData);
                }
            }
        }, 1000);
    }

    private void upDataLocation(double lonData, double latData) {
        RequestParamsFM headParams = new RequestParamsFM();
        headParams.put("X-AUTH-TOKEN", MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        params.put("fid", MyApplication.userID);
        params.put("lng", "" + lonData);
        params.put("lat", "" + latData);
        params.setUseJsonStreamer(true);
        HttpOkhUtils.getInstance().doPostWithHeader(NetConfig.ADDTRAIL, headParams, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(int code, String resbody) {

            }
        });

    }

    private void startSendLanAlat() {
        Intent intent = new Intent(this, SendLocationService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void getNewApkInfo() {
        HttpOkhUtils.getInstance().doGet(NetConfig.GETNEWAPPVERSION, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(int code, String resbody) {
                Gson gson = new Gson();
                //                NewApkInfo newApkInfo = gson.fromJson(resbody, NewApkInfo.class);
                //                if (1 == newApkInfo.getCode()) {
                //                    int appVersionCode = getAppVersionCode(FirstActivity.this);
                //                    if (null != newApkInfo.getNewAppVersion()) {
                //                        if (appVersionCode < newApkInfo.getNewAppVersion().getId()) {
                //                            //弹出dailog，提示用户是否下载
                //                            showDialogToDown(newApkInfo);
                //                        }
                //                    }
                //                }
            }
        });
    }

    private void showDialogToDown(NewApkInfo newApkInfo) {
        //        MyApplication.loadUrl = NetConfig.IMG_HEAD_IP + newApkInfo.getNewAppVersion().getApk_file();
        //        UpdateAppUtil.from(this)
        //                .serverVersionCode(newApkInfo.getNewAppVersion().getId())  //服务器versionCode
        //                .serverVersionName(newApkInfo.getNewAppVersion().getShow_code()) //服务器versionName
        //                .apkPath(MyApplication.loadUrl) //最新apk下载地址
        //                .updateInfo(newApkInfo.getNewAppVersion().getChange_message())
        //                .update();
    }

    //获取当前版本号
    private int getAppVersionCode(Context context) {
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

    private void changeTVColor(int item) {
        for (int i = 0; i < tv_menu.size(); i++) {
            if (i == item) {
                tv_menu.get(i).setTextColor(getResources().getColor(R.color.blue_icon));
            } else {
                tv_menu.get(i).setTextColor(getResources().getColor(R.color.lin_black));
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //            exit();

            final MyAlertDialogHelper dialogHelper = new MyAlertDialogHelper();
            View view = View.inflate(this, R.layout.dialog_commen_title, null);
            dialogHelper.setDIYView(this, view);
            dialogHelper.show();
            TextView tv_sure = view.findViewById(R.id.tv_sure);
            TextView tv_cancel = view.findViewById(R.id.tv_cancel);
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    MyApplication.exit();
                }
            });
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogHelper.disMiss();
                }
            });
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出应用", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            MyApplication.exit();
        }
    }

    /**
     * 添加Fragment
     **/
    public void addFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frame, fragment);
        ft.commit();
    }

    /**
     * 显示Fragment
     **/
    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        // 设置Fragment的切换动画
        // ft.setCustomAnimations(R.anim.cu_push_right_in, R.anim.cu_push_left_out);

        // 判断页面是否已经创建，如果已经创建，那么就隐藏掉
        if (home_F != null) {
            ft.hide(home_F);
        }
        if (sameDay_F != null) {
            ft.hide(sameDay_F);
        }
        if (servApply_F != null) {
            ft.hide(servApply_F);
        }
        if (user_F != null) {
            ft.hide(user_F);
        }
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }
}
