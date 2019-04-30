package com.bt.smart.truck_broker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.activity.LoginActivity;
import com.bt.smart.truck_broker.messageInfo.CommonInfo;
import com.bt.smart.truck_broker.messageInfo.RegisterInfo;
import com.bt.smart.truck_broker.messageInfo.SMSInfo;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Request;


/**
 * @创建者 AndyYan
 * @创建时间 2018/10/30 17:03
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class RegisterPhoneFragment extends Fragment implements View.OnClickListener {
    private View      mRootView;
    private ImageView img_back;
    private TextView  tv_regis;//标题
    private EditText  et_phone;
    private TextView  tv_gcode;//点击获取验证码
    private EditText  et_code;//填写验证码
    private EditText  et_psd;//填写密码
    private CheckBox  cb_agree;//是否同意协议
    private TextView  tv_submit;//确认提交

    private String  mPhone;
    private String  vCode;//记录获取的验证码
    private String  mKind;//记录哪个页面跳转过来的。fgt是忘记密码，rgs是注册
    private Handler handler;//用来刷新发送短息按钮
    private int     count    = 60;//验证码可重新点击发送时间间隔、单位秒
    private boolean isFinish = false;//是否关闭页面

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.frame_regis_phone, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        img_back = mRootView.findViewById(R.id.img_back);
        tv_regis = mRootView.findViewById(R.id.tv_regis);
        et_phone = mRootView.findViewById(R.id.et_phone);
        et_code = mRootView.findViewById(R.id.et_code);
        et_psd = mRootView.findViewById(R.id.et_psd);
        cb_agree = mRootView.findViewById(R.id.cb_agree);
        tv_gcode = mRootView.findViewById(R.id.tv_gcode);
        tv_submit = mRootView.findViewById(R.id.tv_submit);
    }

    private void initData() {
        img_back.setOnClickListener(this);
        cb_agree.setChecked(true);
        if ("fgt".equals(mKind)) {
            tv_regis.setText("忘记密码");
        } else {
            tv_regis.setText("注册");
        }
        tv_gcode.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        handler = new Handler();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.tv_gcode://获取验证码
                mPhone = String.valueOf(et_phone.getText()).trim();
                if ("".equals(mPhone) || mPhone.equals("手机号")) {
                    ToastUtils.showToast(getContext(), "请输入手机号码");
                } else {
                    // 账号不匹配手机号格式（11位数字且以1开头）
                    if (mPhone.length() != 11) {
                        ToastUtils.showToast(getContext(), "手机号码格式不正确");
                    } else {
                        //发送验证码
                        sendMsgFromIntnet();
                    }
                }
                break;
            case R.id.tv_submit:
                String newPhone = String.valueOf(et_phone.getText()).trim();
                if (null == mPhone) {
                    ToastUtils.showToast(getContext(), "请获取验证码");
                    return;
                }
                if (!newPhone.equals(mPhone)) {
                    ToastUtils.showToast(getContext(), "您修改了手机号码，请重新获取验证码");
                    return;
                }
                String wrtcode = String.valueOf(et_code.getText()).trim();
                String wrtpsd = String.valueOf(et_psd.getText()).trim();
                if ("".equals(wrtcode) || "请输入验证码".equals(wrtcode)) {
                    ToastUtils.showToast(getContext(), "请输入验证码");
                    return;
                }
                if (!wrtcode.equals(vCode)) {
                    ToastUtils.showToast(getContext(), "验证码不正确");
                    return;
                }
                if ("".equals(wrtpsd) || "请设置密码".equals(wrtpsd)) {
                    ToastUtils.showToast(getContext(), "请设置密码");
                    return;
                }
                if ("fgt".equals(mKind)) {//修改密码
                    changePsd(mPhone, wrtpsd);
                } else {//注册
                    registMember(mPhone, wrtpsd);
                }
                break;
        }
    }

    private void registMember(String phone, String wrtpsd) {
        RequestParamsFM params = new RequestParamsFM();
        params.put("fmobile", phone);
        params.put("fpassword", wrtpsd);
        HttpOkhUtils.getInstance().doPost(NetConfig.REGISTERDRIVER, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ToastUtils.showToast(getContext(), "网络错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                if (code != 200) {
                    ToastUtils.showToast(getContext(), "网络连接错误" + code);
                    return;
                }
                Gson gson = new Gson();
                RegisterInfo sendSMSInfo = gson.fromJson(resbody, RegisterInfo.class);
                ToastUtils.showToast(getContext(), sendSMSInfo.getMessage());
                if (sendSMSInfo.isOk()) {
                    isFinish = true;
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        });
    }

    private void changePsd(String phone, String wrtpsd) {
        RequestParamsFM params = new RequestParamsFM();
        params.put("mobile", phone);
        params.put("fpasswordNew", wrtpsd);
        HttpOkhUtils.getInstance().doPost(NetConfig.BACKFPASSWORDBYMOBILE, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ToastUtils.showToast(getContext(), "网络错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                if (code != 200) {
                    ToastUtils.showToast(getContext(), "网络连接错误" + code);
                    return;
                }
                Gson gson = new Gson();
                CommonInfo sendSMSInfo = gson.fromJson(resbody, CommonInfo.class);
                ToastUtils.showToast(getContext(), sendSMSInfo.getMessage());
                if (1 == sendSMSInfo.getCode()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    isFinish = true;
                    getActivity().finish();
                }
            }
        });
    }

    private void sendMsgFromIntnet() {
        RequestParamsFM params = new RequestParamsFM();
        params.put("fmobile", mPhone);
        HttpOkhUtils.getInstance().doPost(NetConfig.CHECKMESSAGE, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ToastUtils.showToast(getContext(), "网络错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                if (code != 200) {
                    ToastUtils.showToast(getContext(), "发送失败");
                    return;
                }
                Gson gson = new Gson();
                SMSInfo sendSMSInfo = gson.fromJson(resbody, SMSInfo.class);
                if (1 == sendSMSInfo.getResult()) {
                    ToastUtils.showToast(getContext(), "验证码发送成功");
                    vCode = sendSMSInfo.getCode();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            handler.postDelayed(this, 1000);//递归执行，一秒执行一次
                            if (!isFinish) {
                                if (count > 0) {
                                    count--;
                                    tv_gcode.setBackground(getResources().getDrawable(R.drawable.bg_roundcorder_gray));
                                    tv_gcode.setText(count + "秒后可重新发送");
                                    tv_gcode.setClickable(false);
                                } else {
                                    count = 60;
                                    tv_gcode.setBackground(getResources().getDrawable(R.drawable.bg_round_blue_50));
                                    tv_gcode.setText("发送验证码");
                                    tv_gcode.setClickable(true);
                                    handler.removeCallbacks(this);
                                }
                            }
                        }
                    }, 1000);    //第一次执行，一秒之后。第一次执行完就没关系了
                } else {
                    ToastUtils.showToast(getContext(), "验证码获取失败");
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void setKind(String kind) {
        this.mKind = kind;
    }
}
