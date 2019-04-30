package com.bt.smart.truck_broker.servicefile;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.utils.LocationUtils;

/**
 * @创建者 AndyYan
 * @创建时间 2019/3/5 9:37
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class SendLocationService extends Service {

    private static final String TAG = SendLocationService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
    }

    private MyBinder mBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    public void creatNotifacation() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notifacation_layout);
        Notification notification = new Notification.Builder(this.getApplicationContext())
                .setContent(remoteViews)// 设置自定义的Notification内容
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .getNotification();// 获取构建好的通知--.build()最低要求在
        // API16及以上版本上使用，低版本上可以使用.getNotification()。
        notification.defaults = Notification.DEFAULT_SOUND;//设置为默认的声音
        startForeground(110, notification);//开始前台服务
        //        Intent remote=new Intent(this, MainActivity.class);
        //        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        //        PendingIntent remoteIntent=PendingIntent.getActivity(this,0,remote,PendingIntent.FLAG_UPDATE_CURRENT);
        //        RemoteViews remoteViews=new RemoteViews(getPackageName(),R.layout.remoteview);
        //        remoteViews.setTextViewText(R.id.textView2,"我是remoteview");
        //        remoteViews.setImageViewResource(R.id.image,R.drawable.icon);
        //        remoteViews.setOnClickPendingIntent(R.id.button2,remoteIntent);
        //        notification=new Notification();//注意Notification创建得用new的方式了创建，因为
        //        //如果用Builder的方式创建的话，在设置remoteview的时候会用到setCustomContentView方法，
        //        //该方法需要api24才能使用，api版本太高了，但是使用new的方式就可以不用考虑低api的问题。
        //        notification.icon=R.mipmap.ic_launcher;
        //        notification.tickerText="hello,world";
        //        notification.when=System.currentTimeMillis();
        //        notification.contentView=remoteViews;
        //        notification.contentIntent=pendingIntent;
        //        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        //        notification = new Notification();
        //        notification.contentView = remoteViews;
        //        notification.when = System.currentTimeMillis();
        //        notification.icon = R.mipmap.ic_launcher;
        //        notification.defaults = Notification.DEFAULT_SOUND;//设置为默认的声音


        //        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        //        Intent intent1 = new Intent(this, MainActivity.class);
        //        builder.setContentIntent(PendingIntent.getActivity(this, 0, intent1, 0))
        //                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))//设置图标
        //                .setContentTitle("标题")
        //                .setSmallIcon(R.mipmap.ic_launcher)
        //                .setContentText("内容")
        //                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
        //        Notification notification = builder.build();// 获取构建好的Notification
        //        notification.defaults = Notification.DEFAULT_SOUND;//设置为默认的声音
    }

    public class MyBinder extends Binder {
        public SendLocationService getService() {
            return SendLocationService.this;
        }
    }

    private LocationUtils instance;

    public void startGetLoaction() {
        instance = LocationUtils.getInstance(this);
    }

    public Location getLocation() {
        Location location = instance.showLocation();
        return location;
    }
}
