package com.bt.smart.truck_broker.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.messageInfo.RuleContentInfo;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import okhttp3.Request;

public class CommonUtil {
    /**
     * 设置银行卡输入时每隔4位多一位空格
     * @param cardEt
     */
    public static void bankCardInput(final EditText cardEt) {
        //设置输入长度不超过23位(包含空格)
        cardEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(23)});
        cardEt.addTextChangedListener(new TextWatcher() {
            char kongge = ' ';
            //改变之前text长度
            int beforeTextLength = 0;
            //改变之前的文字
            private CharSequence beforeChar;
            //改变之后text长度
            int onTextLength = 0;
            //是否改变空格或光标
            boolean isChanged = false;
            // 记录光标的位置
            int location = 0;
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            //已有空格数量
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = cardEt.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == kongge) {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }
                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                            buffer.insert(index, kongge);
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    cardEt.setText(str);
                    Editable etable = cardEt.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });

    }

    /**
     * 判断对象是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(Object str) {
        boolean flag = true;
        if (str != null && !str.equals("")) {
            if (str.toString().length() > 0) {
                flag = true;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    public static Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    /**
     * 去掉市区县
     * @param str
     * @return
     */
    public static String replace(String str){
        if(str.contains("自治")||str.length()<=2||str.contains("新区")){
            if(str.equals("清新区")){
                return "清新";
            }else{
                return str.replaceAll("新区","");
            }
        }else{
            return str.replaceAll("市"," ").replaceAll("区"," ").replaceAll("县"," ");
        }
    }

    public static String toPlainText(final String html) {
        if (html == null) {
            return "";
        }
        final Document document = Jsoup.parse(html);
        final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
        document.outputSettings(outputSettings);
        document.select("br").append("\\n");
        document.select("p").prepend("\\n");
        document.select("p").append("\\n");
        final String newHtml = document.html().replaceAll("\\\\n", "\n");
        final String plainText = Jsoup.clean(newHtml, "", Whitelist.none(), outputSettings);
        final String result = StringEscapeUtils.unescapeHtml3(plainText.trim());
        return result;
    }

    public static void showProtocol(final Context context, final int type){
        String end = "";
        if(type==0){
            end = "A0002";
        }else{
            end = "A0003";
        }
        HttpOkhUtils.getInstance().doGet(NetConfig.CONTENT+"/"+end, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ToastUtils.showToast(context, "网络错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                if (200 != code) {
                    ToastUtils.showToast(context, "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                RuleContentInfo ruleContentInfo = gson.fromJson(resbody, RuleContentInfo.class);
                if (ruleContentInfo.isOk()) {
                    String title = "";
                    if (type==0){
                        title = context.getResources().getString(R.string.fwxy);
                    }else{
                        title = context.getResources().getString(R.string.yszc);
                    }
                    new MyAlertDialog(context)
                            .setTitleText(title)
                            .setContentText(CommonUtil.toPlainText(ruleContentInfo.getData().getFcontent())).show();
                }
            }
        });
    }

    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
