package com.bt.smart.truck_broker.util.UpApkDataFile;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/**
 * @创建者 AndyYan
 * @创建时间 2018/10/29 15:37
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class DownloadAppUtil {
    private static final String TAG = "DownloadAppUtil";

    public static long downloadUpdateApkId = -1;//下载更新Apk 下载任务对应的Id
    public static String downloadUpdateApkFilePath;//下载更新Apk 文件路径

    /**
     * 下载app完成后自动安装
     *
     * @param context
     * @param url
     * @param fileName
     * @param notificationTitle
     */
    public static void downloadWithAutoInstall(Context context, String url, String fileName, String notificationTitle) {
        if (TextUtils.isEmpty(url)) {
            Log.e(TAG, "url为空!!!!!");
            return;
        }
        try {
            Uri uri = Uri.parse(url);
            Log.i(TAG, String.valueOf(uri));
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            //在通知栏中显示
            request.setTitle(notificationTitle);
            request.setVisibleInDownloadsUi(true);

            String filePath = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//SD卡是否正常挂载
                filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            } else {
                Log.i(TAG, "没有SD卡" + "filePath:" + context.getFilesDir().getAbsolutePath());
                //filePath=context.getFilesDir().getAbsolutePath();
                return;
            }
            downloadUpdateApkFilePath = filePath + File.separator + fileName;
            // 若存在，则删除
            deleteFile(downloadUpdateApkFilePath);
            Uri fileUri = Uri.parse("file://" + downloadUpdateApkFilePath);
            request.setDestinationUri(fileUri);
            downloadUpdateApkId = downloadManager.enqueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteFile(String fileStr) {
        File file = new File(fileStr);
        return file.delete();
    }
}
