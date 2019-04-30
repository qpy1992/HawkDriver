package com.bt.smart.truck_broker.utils;

import android.support.v4.app.Fragment;

/**
 * @创建者 AndyYan
 * @创建时间 2018/11/7 9:46
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class MyFragmentManagerUtil {

    public static void closeTopFragment(Fragment fragment) {
        //弹出回退栈最上面的fragment
        if (null != fragment)
            fragment.getFragmentManager().popBackStackImmediate(null, 0);
    }

    public static void closeFragmentOnAct(Fragment fragment) {
        //弹出回退栈最上面的fragment
        if (null != fragment)
            fragment.getActivity().finish();
    }
}
