package com.bt.smart.truck_broker.fragment.sameDay;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.activity.OpenLockActivity;
import com.bt.smart.truck_broker.activity.SaomiaoUIActivity;
import com.bt.smart.truck_broker.messageInfo.BlueMacInfo;
import com.bt.smart.truck_broker.messageInfo.OrderDetailInfo;
import com.bt.smart.truck_broker.messageInfo.TakeOrderResultInfo;
import com.bt.smart.truck_broker.servicefile.SendLocationService;
import com.bt.smart.truck_broker.utils.EditTextUtils;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.MyAlertDialogHelper;
import com.bt.smart.truck_broker.utils.MyFragmentManagerUtil;
import com.bt.smart.truck_broker.utils.MyNumUtils;
import com.bt.smart.truck_broker.utils.ProgressDialogUtil;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.ShowCallUtil;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.google.gson.Gson;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.IOException;

import okhttp3.Request;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/8 21:35
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class OrderDetailFragment extends Fragment implements View.OnClickListener {
    private View            mRootView;
    private ImageView       img_back;
    private ImageView       img_empty;
    private TextView        tv_title;
    private String          orderID;//订单id
    private String          mOrder_no;//订单号
    private TextView        tv_place;
    private TextView        tv_goodsname;
    private TextView        tv_carType;
    private TextView        tv_name;
    private TextView        tv_fhPlace;
    private TextView        tv_phone;
    //    private TextView        tv_local;//开始定位按钮
    private TextView        tv_cont;//联系货主
    private TextView        tv_take;//接单
    private OrderDetailInfo orderDetailInfo;//订单详情
    private int RESULT_TAKE_ORDER = 12088;//接单成功响应值
    private Handler mProhandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.frame_order_detail, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        img_back = mRootView.findViewById(R.id.img_back);
        img_empty = mRootView.findViewById(R.id.img_empty);
        tv_title = mRootView.findViewById(R.id.tv_title);
        tv_place = mRootView.findViewById(R.id.tv_place);
        tv_goodsname = mRootView.findViewById(R.id.tv_goodsname);
        tv_carType = mRootView.findViewById(R.id.tv_carType);
        tv_name = mRootView.findViewById(R.id.tv_name);
        tv_fhPlace = mRootView.findViewById(R.id.tv_fhPlace);
        tv_phone = mRootView.findViewById(R.id.tv_phone);
        tv_cont = mRootView.findViewById(R.id.tv_cont);
        tv_take = mRootView.findViewById(R.id.tv_take);
        //        tv_local = mRootView.findViewById(R.id.tv_local);
    }

    private void initData() {
        tv_title.setText("货源详情");
        img_back.setVisibility(View.VISIBLE);
        orderID = getActivity().getIntent().getStringExtra("orderID");
        //获取货源详情
        getOrderDetail();
        img_back.setOnClickListener(this);
        tv_cont.setOnClickListener(this);
        tv_take.setOnClickListener(this);
        img_empty.setOnClickListener(this);
        //        tv_local.setOnClickListener(this);
        //        String touchKind = getActivity().getIntent().getStringExtra("touchKind");
        //        if (null != touchKind && "accepted".equals(touchKind)) {
        //            tv_take.setVisibility(View.GONE);
        //        }
        int orderType = getActivity().getIntent().getIntExtra("orderType", -1);
        if (0 == orderType || 1 == orderType || 2 == orderType || 5 == orderType) {
            tv_take.setText("开锁");
        } else if (3 == orderType || 4 == orderType || 6 == orderType) {
            tv_take.setVisibility(View.GONE);
        }
        //初始化定时刷新器
        initHandlerPost();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_empty:
                //获取货源详情
                getOrderDetail();
                break;
            case R.id.img_back:
                MyFragmentManagerUtil.closeFragmentOnAct(this);
                break;
            case R.id.tv_cont://联系货主
                ShowCallUtil.showCallDialog(getContext(), orderDetailInfo.getData().getFh_telephone());
                break;
            case R.id.tv_take:
                String cont = String.valueOf(tv_take.getText());
                if ("开锁".equals(cont)) {
                    //跳转开锁页面
                    openLockDevice();
                } else {
                    //弹出自定义的dailog让司机填写报价
                    show2WriteMoney();
                }
                break;
            //            case R.id.tv_local:
            //                //开始定位
            //                MyApplication.needLocationService = true;
            //                //                ToastUtils.showToast(getContext(), "开始定位...");
            //                //                startSendLanAlat();
            //                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isBinded = false;
        if (null != mProhandler)
            mProhandler.removeCallbacksAndMessages(null);
        mProhandler = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果，将扫描获取到的编码上传给服务器
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    mBlueXlh = result;
                    //上传给服务器,获取mac地址
                    ToastUtils.showToast(getContext(), "获取设备锁信息");
                    if (null == mBlueMac) {
                        sendCode2Service(result);
                    } else {
                        //跳转连接蓝牙界面
                        contactBlueDevice();
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void initHandlerPost() {
        mProhandler = new Handler();
        mProhandler.postDelayed(new Runnable() {
            public void run() {
                mProhandler.postDelayed(this, 5000);//递归执行，一秒执行一次
                if (isBinded && null != service && null != service.getLocation()) {
                    ToastUtils.showToast(getContext(), "经度：" + service.getLocation().getLongitude() + "纬度：" + service.getLocation().getLatitude());
                }
            }
        }, 1000);
    }

    private SendLocationService service;
    private boolean             isBinded;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            isBinded = true;
            ToastUtils.showToast(getContext(), "连上服务");
            SendLocationService.MyBinder myBinder = (SendLocationService.MyBinder) iBinder;
            service = myBinder.getService();
            service.startGetLoaction();
            service.creatNotifacation();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBinded = false;
            ToastUtils.showToast(getContext(), "服务断开");
        }
    };

    private void startSendLanAlat() {
        Intent intent = new Intent(getContext(), SendLocationService.class);
        getActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void openLockDevice() {
        //先通过扫描获取 设备编号，从服务器获取 蓝牙mac地址,在打开连接界面
        //扫描二维码，提交服务器，获取对应蓝牙mac地址
        getScanCode();
    }

    private int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 1001;//申请照相机权限结果
    private int REQUEST_CODE                       = 1003;//接收扫描结果
    private String mBlueMac;
    private String mBlueXlh;

    //扫描二维码
    private void getScanCode() {
        //第二个参数是需要申请的权限
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        } else {
            Intent intent = new Intent(getContext(), SaomiaoUIActivity.class);//这是一个自定义的扫描界面，扫描UI框放大了。
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    private void sendCode2Service(String result) {
        ProgressDialogUtil.startShow(getContext(), "正在检索...");
        RequestParamsFM headParam = new RequestParamsFM();
        headParam.put("X-AUTH-TOKEN", MyApplication.userToken);
        HttpOkhUtils.getInstance().doGetWithOnlyHeader(NetConfig.DRIVERORDERCONTROLLER + "/" + result + "/" + orderID, headParam, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(getContext(), "网络错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(getContext(), "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                BlueMacInfo blueMacInfo = gson.fromJson(resbody, BlueMacInfo.class);
                ToastUtils.showToast(getContext(), blueMacInfo.getMessage());
                if (blueMacInfo.isOk()) {
                    //获取到mac地址后，连接蓝牙开锁
                    String data = blueMacInfo.getData();
                    if (null != data && !"".equals(data)) {
                        ToastUtils.showToast(getContext(), "获取到设备锁的信息");
                        mBlueMac = data;
                        //跳转链接界面
                        contactBlueDevice();
                    } else {
                        ToastUtils.showToast(getContext(), "未获取到设备锁的信息");
                    }
                } else {
                    ToastUtils.showToast(getContext(), "获取设备锁的信息失败");
                }
            }
        });
    }

    private void contactBlueDevice() {
        //跳转开锁页面,传递设备mac地址
        Intent intent = new Intent(getActivity(), OpenLockActivity.class);
        intent.putExtra("macID", mBlueMac);
        intent.putExtra("blueXlh", mBlueXlh);
        intent.putExtra("orderNo", mOrder_no);
        startActivity(intent);
    }

    private void show2WriteMoney() {
        final MyAlertDialogHelper dialogHelper = new MyAlertDialogHelper();
        View inflate = View.inflate(getContext(), R.layout.dailog_write_money, null);
        dialogHelper.setDIYView(getContext(), inflate);
        final EditText et_money = inflate.findViewById(R.id.et_money);
        TextView tv_cancle = inflate.findViewById(R.id.tv_cancle);
        TextView tv_sure = inflate.findViewById(R.id.tv_sure);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHelper.disMiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = EditTextUtils.getContent(et_money);
                double doubleNum = MyNumUtils.getDoubleNum(content);
                //接单
                takeOrder(doubleNum);
                dialogHelper.disMiss();
            }
        });
        dialogHelper.show();
    }

    private void takeOrder(double money) {
        RequestParamsFM headParams = new RequestParamsFM();
        headParams.put("X-AUTH-TOKEN", MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        params.put("driverId", MyApplication.userID);
        params.put("id", MyApplication.userID);
        params.put("orderId", orderDetailInfo.getData().getId());
        params.put("orderStatus", "5");
        params.put("ffee", money);
        params.setUseJsonStreamer(true);
        HttpOkhUtils.getInstance().doPostWithHeader(NetConfig.DRIVERORDERCONTROLLER, headParams, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(getContext(), "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(getContext(), "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                TakeOrderResultInfo takeOrderResultInfo = gson.fromJson(resbody, TakeOrderResultInfo.class);
                ToastUtils.showToast(getContext(), takeOrderResultInfo.getMessage());
                if (takeOrderResultInfo.isOk()) {
                    getActivity().setResult(RESULT_TAKE_ORDER);
                    getActivity().finish();
                }
            }
        });
    }

    private void getOrderDetail() {
        ProgressDialogUtil.startShow(getContext(), "正在获取详情...");
        RequestParamsFM headParams = new RequestParamsFM();
        headParams.put("X-AUTH-TOKEN", MyApplication.userToken);
        HttpOkhUtils.getInstance().doGetWithOnlyHeader(NetConfig.ALL_ORDER_DETAIL + "/" + orderID, headParams, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(getContext(), "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                img_empty.setVisibility(View.VISIBLE);
                if (code != 200) {
                    ToastUtils.showToast(getContext(), "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                orderDetailInfo = gson.fromJson(resbody, OrderDetailInfo.class);
                ToastUtils.showToast(getContext(), orderDetailInfo.getMessage());
                if (orderDetailInfo.isOk()) {
                    img_empty.setVisibility(View.GONE);
                    mOrder_no = orderDetailInfo.getData().getOrder_no();
                    tv_place.setText(orderDetailInfo.getData().getOrigin() + "  →  " + orderDetailInfo.getData().getDestination());
                    tv_goodsname.setText(orderDetailInfo.getData().getCar_type());
                    tv_carType.setText(orderDetailInfo.getData().getCar_type());
                    tv_name.setText(orderDetailInfo.getData().getFh_name());
                    tv_fhPlace.setText(orderDetailInfo.getData().getFh_address());
                    tv_phone.setText(orderDetailInfo.getData().getFh_telephone());
                }
            }
        });
    }
}
