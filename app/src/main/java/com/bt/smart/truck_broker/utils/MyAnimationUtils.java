package com.bt.smart.truck_broker.utils;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/16 15:54
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class MyAnimationUtils {

    public static void rotateView(View view, long time, float... value) {
        ObjectAnimator.ofFloat(view, "rotation", value).setDuration(time).start();
    }
}
