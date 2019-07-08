package com.bt.smart.truck_broker.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bt.smart.truck_broker.R;
import java.io.File;

public class MyPopChoisePic {
    private PopupWindow popupWindow;
    private Activity mActivity;
    private int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 1001;//相册权限申请码
    private int SHOT_CODE = 10069;//调用系统相册-选择图片
    private int IMAGE = 10068;//调用系统相册-选择图片

    public MyPopChoisePic(Activity activity) {
        this.mActivity = activity;
//        mFilePath = Environment.getExternalStorageDirectory().getPath();//获取SD卡路径
//        long photoTime = System.currentTimeMillis();
//        mFilePath = mFilePath + "/temp" + photoTime + ".jpg";//指定路径
    }

    public void showChoose(View v, String filePath) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(mActivity).inflate(R.layout.view_camera_pic_popup, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        //设置消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //设置背景色
                setBackgroundAlpha(1.0f);
            }
        });
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(view, filePath);
        //设置背景色
        setBackgroundAlpha(0.5f);
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = alpha;
        mActivity.getWindow().setAttributes(lp);
    }

    //设置PopupWindow的View点击事件
    public void setOnPopupViewClick(View view, final String filePath) {
        TextView tv_xc, tv_pz, tv_cancel;
        tv_xc = (TextView) view.findViewById(R.id.tv_xc);//相册
        tv_pz = (TextView) view.findViewById(R.id.tv_pz);//拍照
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);//取消
        tv_xc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //第二个参数是需要申请的权限
                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);
                } else {
                    //权限已经被授予，在这里直接写要执行的相应方法即可
                    //调用相册
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    mActivity.startActivityForResult(intent, IMAGE);
                    popupWindow.dismiss();
                }
            }
        });
        tv_pz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //第二个参数是需要申请的权限
                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);
                } else {
                    //权限已经被授予，在这里直接写要执行的相应方法即可
                    //调用相机
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri photoUri = Uri.fromFile(new File(filePath)); // 传递路径
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径
                    mActivity.startActivityForResult(intent, SHOT_CODE);
                    popupWindow.dismiss();
                }
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != popupWindow) {
                    popupWindow.dismiss();
                }
            }
        });
    }
}
