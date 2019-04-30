package com.bt.smart.truck_broker.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.bt.smart.truck_broker.R;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/28 11:13
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ShowCallUtil {

    public static void showCallDialog(final Context context, final String ftel) {
        if (null == ftel) {
            ToastUtils.showToast(context, "未找到电话号码");
            return;
        }
        //弹出dialog
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setIcon(R.drawable.icon_phone);      //设置图标
        dialog.setTitle("将为您自动跳转拨号页面");    //设置标题
        dialog.setCancelable(false);        //点击旁边不消失
        dialog.setMessage(ftel);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ftel));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });//设置确定键
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "复制电话号码", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(ftel);
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
            }
        });
        dialog.show();
    }

    public static void showCallDialog(final Context context, final String ftel, final String orderID) {
        //弹出dialog
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setIcon(R.drawable.icon_phone);      //设置图标
        dialog.setTitle("将为您自动跳转拨号页面");    //设置标题
        dialog.setCancelable(false);        //点击旁边不消失
        dialog.setMessage(ftel);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //调用服务器，call接口
                //sendMsgToCallInter(context, orderID);
                //跳转拨号界面
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ftel));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });//设置确定键
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "复制电话号码", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(ftel);
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
            }
        });
        dialog.show();
    }
}
