package com.bt.smart.truck_broker.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import com.bt.smart.truck_broker.R;


/**
 * @创建者 AndyYan
 * @创建时间 2018/12/18 9:43
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class SoundPoolUtil {
    private static SoundPoolUtil soundPoolUtil;
    private static SoundPool     soundPool;
    private static boolean       canPlay;
    private static boolean       isLoadFinish;
    private static int[] soundGruop = new int[2];

    //单例模式
    public static SoundPoolUtil getInstance(Context context) {
        if (soundPoolUtil == null)
            soundPoolUtil = new SoundPoolUtil(context);
        return soundPoolUtil;
    }

    private SoundPoolUtil(final Context context) {
        canPlay = true;
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        //加载音频文件
        int load0 = soundPool.load(context, R.raw.duing, 1);
        int load1 = soundPool.load(context, R.raw.yulu, 1);
        soundGruop[0] = load0;
        soundGruop[1] = load1;
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                isLoadFinish = true;
                ToastUtils.showToast(context, "点击音效文件加载完毕.");
            }
        });
    }

    public static void play(int number) {
        if (isLoadFinish)
            //播放音频
            if (null != soundPool && canPlay) {
                if (number < 0) {
                    number = 0;
                } else if (number > 1) {
                    number = 1;
                }
                soundPool.play(soundGruop[number], 1, 1, 0, 0, 1);
            }
    }

    public static void pauseSoundPlay() {
        canPlay = false;
    }

    public static void restartSoundPlay() {
        canPlay = true;
    }
    public static void closeSoundPlay() {
        canPlay = false;
        soundPool.release();
    }
}
