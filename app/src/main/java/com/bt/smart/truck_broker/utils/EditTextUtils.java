package com.bt.smart.truck_broker.utils;

import android.widget.EditText;

/**
 * @创建者 AndyYan
 * @创建时间 2018/4/12 19:43
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class EditTextUtils {//edittext工具类

    public static boolean isEmpty(EditText et, String contY) {
        String cont = String.valueOf(et.getText()).trim();
        if ("".equals(cont) || contY.equals(cont)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getContent(EditText et) {
        String cont = String.valueOf(et.getText()).trim();
        return cont;
    }
}
