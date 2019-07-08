package com.bt.smart.truck_broker.utils;

import android.widget.EditText;
import android.widget.TextView;

/**
 * @创建者 AndyYan
 * @创建时间 2018/4/12 19:43
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class MyTextUtils {//edittext工具类

    public static boolean isEditTextEmpty(EditText et, String contY) {
        String cont = String.valueOf(et.getText()).trim();
        if ("".equals(cont) || contY.equals(cont)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getEditTextContent(EditText et) {
        String cont = String.valueOf(et.getText()).trim();
        return cont;
    }

    public static boolean isTvTextEmpty(TextView tv, String contY) {
        String cont = String.valueOf(tv.getText()).trim();
        if ("".equals(cont) || contY.equals(cont)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getTvTextContent(TextView tv) {
        String cont = String.valueOf(tv.getText()).trim();
        return cont;
    }
}
