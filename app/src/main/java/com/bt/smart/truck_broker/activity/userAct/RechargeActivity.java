package com.bt.smart.truck_broker.activity.userAct;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.messageInfo.DownOrderResultInfo;
import com.bt.smart.truck_broker.messageInfo.PayResult;
import com.bt.smart.truck_broker.messageInfo.WXOrderResultInfo;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import okhttp3.Request;

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView re_back;
    EditText re_et;
    Button btn_re;
    CheckBox cb_weixin,cb_alipay;
    LinearLayout ll_wx,ll_ali;
    private int payKind = 0;
    private double orderPrice;//订单价格

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        setViews();
        setListeners();
    }

    protected void setViews(){
        re_back = findViewById(R.id.re_back);
        re_et = findViewById(R.id.re_et);
        cb_weixin = findViewById(R.id.cb_weixin);
        cb_alipay = findViewById(R.id.cb_alipay);
        btn_re = findViewById(R.id.btn_re);
        ll_wx = findViewById(R.id.ll_wx);
        ll_ali = findViewById(R.id.ll_ali);
    }

    protected void setListeners(){
        re_back.setOnClickListener(this);
        ll_wx.setOnClickListener(this);
        ll_ali.setOnClickListener(this);
        btn_re.setOnClickListener(this);
        re_et.setFilters(new InputFilter[]{lengthFilter});
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.re_back:
            finish();
            break;
            case R.id.ll_wx:
                cb_alipay.setChecked(false);
                if(cb_weixin.isChecked()){
                    cb_weixin.setChecked(false);
                    payKind = 0;
                }else {
                    cb_weixin.setChecked(true);
                    payKind = 1;
                }
                break;
            case R.id.ll_ali:
                cb_weixin.setChecked(false);
                if(cb_alipay.isChecked()){
                    cb_alipay.setChecked(false);
                    payKind = 0;
                }else {
                    cb_alipay.setChecked(true);
                    payKind = 2;
                }
                break;
            case R.id.btn_re:
            //跳转支付链接
                RequestParamsFM headParams = new RequestParamsFM();
                headParams.put("X-AUTH-TOKEN", MyApplication.userToken);
                String price = String.valueOf(re_et.getText());
                if(price.length()==0){
                    ToastUtils.showToast(this, "请输入充值金额");
                    return;
                }else{
                    orderPrice = Double.parseDouble(re_et.getText().toString());
                    if(orderPrice<0.01){
                        ToastUtils.showToast(this, "最少充值0.01元");
                        return;
                    }
                }
                if (payKind == 0) {
                    ToastUtils.showToast(this, "请选择支付方式");
                    return;
                }
                if (payKind == 1) {
//                    ToastUtils.showToast(this, "您选择了微信支付");
                    /*1.客户端（APP）提交订单信息给服务端，服务端提交微信，根据微信接口：统一下单接口，
                     *2.生成预支付Id(prepay_id)返回给客户端，客户端（APP）根据预支付Id（prepay_id）调起微信支付
                     *3.接收微信返回的支付信息：成功，错误，取消
                     *4.支付成功，再在服务器上下单，确认订单已支付.该订单支付完成*/
                    //提交订单信息，服务器获取订单信息后调用微信统一下单接口，请求完成，将prepay_id返回app
                    // （具体返回参数：appid，商户号：partnerid，预支付id：prepay_id，扩展字段package（固定值Sign=WXPay）,随机字符串noncestr，时间戳timestamp，签名sign），
                    //获取返回参数后，调用微信app支付
                    RequestParamsFM params = new RequestParamsFM();
                    params.put("userid", MyApplication.userID);
                    params.put("ip", "205.168.1.102");
                    params.put("fee", orderPrice+"");
                    params.setUseJsonStreamer(true);
                    HttpOkhUtils.getInstance().doPostWithHeader(NetConfig.WX, headParams, params, new HttpOkhUtils.HttpCallBack() {
                        @Override
                        public void onError(Request request, IOException e) {
                            ToastUtils.showToast(RechargeActivity.this, "下单失败");
                        }

                        @Override
                        public void onSuccess(int code, String resbody) {
                            if (code != 200) {
                                ToastUtils.showToast(RechargeActivity.this, "下单失败");
                                return;
                            }
                            Log.i("WXRESBODY",resbody);
                            Gson gson = new Gson();
                            WXOrderResultInfo orderResInfo = gson.fromJson(resbody, WXOrderResultInfo.class);
                            String return_code = orderResInfo.getReturn_code();
                            String return_msg = orderResInfo.getReturn_msg();
                            if (return_code.equals("SUCCESS")) {
                                String appId = orderResInfo.getAppId();
                                String partnerId = orderResInfo.getPartnerId();
                                String prepayId = orderResInfo.getPrepayId();
                                String nonceStr = orderResInfo.getNonceStr();
                                int timeStamp = orderResInfo.getTimeStamp();
                                String sign = orderResInfo.getSign();
                                toWXPay(appId, partnerId, prepayId, nonceStr, timeStamp, sign);
                            }
//                            ToastUtils.showToast(RechargeActivity.this, return_msg);
                        }
                    });
                    return;
                }
                if (payKind == 2) {
//                    ToastUtils.showToast(RechargeActivity.this, "你选择了支付宝支付");
                    //先提交下单信息到服务器(状态时已下单，但未支付)，获取订单号
                    //获取订单信息后，调用支付宝支付,
                    //接收支付宝返回的支付信息：取消支付，成功支付，支付失败。
                    //支付成功，再在服务器上下单，确认订单已支付.该订单支付完成
                    RequestParamsFM params = new RequestParamsFM();
                    params.put("userid", MyApplication.userID);
//                    params.put("device_id", payInfo.getParkid());
//                    params.put("paycode", "1");
//                    params.put("ip", "205.168.1.102");
                    Log.i("FEE",orderPrice+"");
                    params.put("fee", orderPrice);
//                    params.put("plateno", mPlateNo);
                    HttpOkhUtils.getInstance().doPostWithHeader(NetConfig.ALIPAY, headParams, params, new HttpOkhUtils.HttpCallBack() {
                        @Override
                        public void onError(Request request, IOException e) {
                            ToastUtils.showToast(RechargeActivity.this, "下单失败");
                            System.out.println("babababab");
                        }

                        @Override
                        public void onSuccess(int code, String resbody) {
                            if (code != 200) {
                                ToastUtils.showToast(RechargeActivity.this, "下单失败");
                                return;
                            }
                            Log.i("ALIPAYRESBODY",resbody);
                            Gson gson = new Gson();
                            DownOrderResultInfo downOrderResultInfo = gson.fromJson(resbody, DownOrderResultInfo.class);
                            int result = downOrderResultInfo.getResult();
                            String message = downOrderResultInfo.getMessage();
                            if (result == 0) {
                                orderStr = String.valueOf(downOrderResultInfo.getId());
                                // DecimalFormat df = new DecimalFormat("######0.00");
                                // double resultPrice = Double.parseDouble(df.format(orderPrice));
                                if (orderPrice < 0.01) {
                                    // orderOverOK(orderStr, "", "zhifubao");
                                    ToastUtils.showToast(RechargeActivity.this, "免费");
                                } else {
                                    if (checkAliPayInstalled(RechargeActivity.this)) {
                                        String orderinfo = downOrderResultInfo.getOrderinfo();
                                        testSend(orderPrice, orderinfo);
                                    } else {
                                        ToastUtils.showToast(RechargeActivity.this, "您未安装支付宝");
                                    }
                                }
                            }
                            ToastUtils.showToast(RechargeActivity.this, message);
                        }
                    });
                }
            break;
        }
    }

    private InputFilter lengthFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // source:当前输入的字符
            // start:输入字符的开始位置
            // end:输入字符的结束位置
            // dest：当前已显示的内容
            // dstart:当前光标开始位置
            // dent:当前光标结束位置
            //Log.e("", "source=" + source + ",start=" + start + ",end=" + end + ",dest=" + dest.toString() + ",dstart=" + dstart + ",dend=" + dend);
            if (dest.length() == 0 && source.equals(".")) {
                return "0.";
            }
            String dValue = dest.toString();
            String[] splitArray = dValue.split("\\.");
            if (splitArray.length > 1) {
                String dotValue = splitArray[1];
//                if (dotValue.length() == 2) {//输入框小数的位数是2的情况，整个输入框都不允许输入
//                    return "";
//                }
                if (dotValue.length() == 2 && dest.length() - dstart < 3){ //输入框小数的位数是2的情况时小数位不可以输入，整数位可以正常输入
                    return "";
                }
            }
            return null;
        }
    };

    private IWXAPI iwxapi; //微信支付api

    private void toWXPay(final String appId, final String partnerId, final String prepayId, final String nonceStr, final int timeStamp, final String sign) {
        iwxapi = WXAPIFactory.createWXAPI(RechargeActivity.this, appId); //初始化微信api
        iwxapi.registerApp(appId); //注册appid  appid可以在开发平台获取
        Runnable payRunnable = new Runnable() {  //这里注意要放在子线程
            @Override
            public void run() {
                PayReq request = new PayReq(); //调起微信APP的对象
                //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                request.appId = appId;
                request.partnerId = partnerId;//微信支付分配的商户号
                request.prepayId = prepayId;//微信返回的支付 交易会话ID
                request.packageValue = "Sign=WXPay";
                request.nonceStr = nonceStr;//随机字符串，不长于32位。
                request.timeStamp = String.valueOf(timeStamp);//时间戳
                request.sign = sign;//签名
                iwxapi.sendReq(request);//发送调起微信的请求
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public static boolean checkAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    private static final int    SDK_ALPAY_FLAG = 1001;
    private static final int    SDK_WXPAY_FLAG = 1000;
    private              String orderStr       = "";//记录订单id

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_ALPAY_FLAG:
                    //Map<String, String> map = (Map<String, String>) msg.obj;
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    //同步获取结果
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(RechargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        //支付成功后，关闭当前界面，修改前面的UI，显示剩余离场时间 paySuccessResult
                        changeUpFragmentUI();
                        MyApplication.money = MyApplication.money.add(new BigDecimal(orderPrice));
                        //getActivity().finish();
                        // String tradeNo = resultInfo.split("trade_no\":\"")[1];
                        // tradeNo = tradeNo.substring(0, tradeNo.indexOf("\""));
                        // orderOverOK(orderStr, tradeNo, "zhifubao");
                    } else {
                        Toast.makeText(RechargeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case SDK_WXPAY_FLAG:
                    //                  String result = String.valueOf(msg.obj);
                    String result = msg.obj.toString();
                    if ("支付成功".equals(result)) {
                        ToastUtils.showToast(RechargeActivity.this, "WX支付成功");
                        MyApplication.money = MyApplication.money.add(new BigDecimal(orderPrice));
                        finish();
                    } else {
                        ToastUtils.showToast(RechargeActivity.this, "WX支付失败");
                    }
                    break;
            }
        }
    };

    private void testSend(double price, final String orderInfo) {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        //        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(AppConstants.ALI_PLAY_APPID, "mOrder.getFname()", price);
        //        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        //        //
        //        String sign = OrderInfoUtil2_0.getSign(params, AppConstants.ALI_PLAY_RSA2_PRIVATE, true);
        //        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(RechargeActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_ALPAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void changeUpFragmentUI(){
        finish();
    }
}
