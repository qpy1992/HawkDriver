package com.bt.smart.truck_broker.servicefile;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.bt.smart.truck_broker.MainActivity;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.utils.ThreadUtils;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.PushService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

public class GeTuiIntentService extends GTIntentService {
    private String content = "";
    private int markExamine;

    public GeTuiIntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int i) {

    }

    @Override
    public void onReceiveClientId(Context context, String s) {

    }

    @Override
    public void onReceiveMessageData(final Context context, GTTransmitMessage gtTransmitMessage) {
        // 透传消息的处理，详看SDK demo
        byte[] payload = gtTransmitMessage.getPayload();
        if (null != payload) {
            content = new String(payload);
        }
        if ("".equals(content))
            return;

        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(context, content);
                //自定义推送通知
                //显示通知
                sendNotification(context, content);
            }
        });
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {

    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {

    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    private void sendNotification(Context context, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        //延时意图
        /**
         * 参数2：请求码 大于1
         */
        markExamine++;
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //最大音量
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0); //tempVolume:音量绝对值
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Intent[] intents = {mainIntent};
        PendingIntent pendingIntent = PendingIntent.getActivities(context, 1, intents, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(context)
                .setAutoCancel(true) //当点击后自动删除
                .setSmallIcon(R.mipmap.ic_launcher) //必须设置
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentTitle("新消息")
                .setContentText(message)
                .setContentInfo("")
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setSound(uri)
                .build();
        notificationManager.notify(markExamine, notification);
    }
}
