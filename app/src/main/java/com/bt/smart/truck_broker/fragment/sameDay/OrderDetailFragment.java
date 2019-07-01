package com.bt.smart.truck_broker.fragment.sameDay;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.activity.OpenLockActivity;
import com.bt.smart.truck_broker.activity.SaomiaoUIActivity;
import com.bt.smart.truck_broker.activity.samedayAct.ShowMapActivity;
import com.bt.smart.truck_broker.activity.userAct.GetFacePhotoActivity;
import com.bt.smart.truck_broker.messageInfo.BlueMacInfo;
import com.bt.smart.truck_broker.messageInfo.ForTXAIFaceInfo;
import com.bt.smart.truck_broker.messageInfo.OrderDetailInfo;
import com.bt.smart.truck_broker.messageInfo.TakeOrderResultInfo;
import com.bt.smart.truck_broker.messageInfo.UpPicInfo;
import com.bt.smart.truck_broker.servicefile.SendLocationService;
import com.bt.smart.truck_broker.utils.CommonUtil;
import com.bt.smart.truck_broker.utils.EditTextUtils;
import com.bt.smart.truck_broker.utils.GlideLoaderUtil;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private View mRootView;
    private ImageView img_back, img_empty, iv_l1, iv_l2, iv_l3, iv_load, iv_r1, iv_rece;
    private LinearLayout ll_load, ll_rece;
    private RelativeLayout rlt_tomap;
    private TextView tv_title, tv_place, tv_goodsname, tv_carType, tv_name, tv_fhPlace, tv_phone, tv_cont, tv_take, tv_inter;
    private String orderID;//订单id
    private String mOrder_no;//订单号
    //    private TextView        tv_local;//开始定位按钮
    private OrderDetailInfo orderDetailInfo;//订单详情
    private int RESULT_TAKE_ORDER = 12088;//接单成功响应值
    private Handler mProhandler;
    private Uri imageUri;
    private static final int TAKE_PHOTO = 1002;
    private static final int TAKE_PHOTO_HD = 1004;
    private Bitmap bitmap1 = null;
    // 记录文件保存位置
    private String mFilePath;
    private File file;
    private FileInputStream is = null;
    private int lcount = 0;
    private int hcount = 0;
    //    private String getloadUrl;//装车照片网络地址
//    private String getreceUrl;//回单照片网络地址
//    private String fstatus;
    private final static String TAG = "OrderDetailFragment";

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
        rlt_tomap = mRootView.findViewById(R.id.rlt_tomap);
        tv_goodsname = mRootView.findViewById(R.id.tv_goodsname);
        tv_carType = mRootView.findViewById(R.id.tv_carType);
        tv_name = mRootView.findViewById(R.id.tv_name);
        tv_fhPlace = mRootView.findViewById(R.id.tv_fhPlace);
        tv_phone = mRootView.findViewById(R.id.tv_phone);
        tv_cont = mRootView.findViewById(R.id.tv_cont);
        tv_take = mRootView.findViewById(R.id.tv_take);
        tv_inter = mRootView.findViewById(R.id.tv_inter);
        //        tv_local = mRootView.findViewById(R.id.tv_local);
        iv_l1 = mRootView.findViewById(R.id.iv_l1);
        iv_l2 = mRootView.findViewById(R.id.iv_l2);
        iv_l3 = mRootView.findViewById(R.id.iv_l3);
        iv_load = mRootView.findViewById(R.id.iv_load);
        iv_r1 = mRootView.findViewById(R.id.iv_r1);
        iv_rece = mRootView.findViewById(R.id.iv_rece);
        ll_load = mRootView.findViewById(R.id.ll_load);
        ll_rece = mRootView.findViewById(R.id.ll_rece);
    }

    private void initData() {
        tv_title.setText("货源详情");
        img_back.setVisibility(View.VISIBLE);
        orderID = getActivity().getIntent().getStringExtra("orderID");
        //获取货源详情
        getOrderDetail();
        img_back.setOnClickListener(this);
        rlt_tomap.setOnClickListener(this);
        tv_cont.setOnClickListener(this);
        tv_take.setOnClickListener(this);
        img_empty.setOnClickListener(this);
        iv_load.setOnClickListener(this);
        iv_rece.setOnClickListener(this);
        iv_l1.setOnClickListener(this);
        iv_l2.setOnClickListener(this);
        iv_l3.setOnClickListener(this);
        iv_r1.setOnClickListener(this);
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
        if (0 == orderType) {
            ll_load.setVisibility(View.VISIBLE);
        }
        if (1 == orderType) {
            ll_rece.setVisibility(View.VISIBLE);
        }
        if (4 == orderType) {
            iv_load.setVisibility(View.GONE);
            iv_rece.setVisibility(View.GONE);
        }
        //初始化定时刷新器
        initHandlerPost();
//        loadpics(orderDetailInfo.getData().getFstatus(),orderDetailInfo.getData().getFloadpics());
        // 获取SD卡路径
        mFilePath = Environment.getExternalStorageDirectory().getPath();
        // 文件名
        mFilePath = mFilePath + "/" + "photo.png";
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
            case R.id.rlt_tomap://跳转地图
                Intent mapIntent=new Intent(getActivity(), ShowMapActivity.class);
                startActivity(mapIntent);
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
//                    toGetFacePic();
                }
                break;
            //            case R.id.tv_local:
            //                //开始定位
            //                MyApplication.needLocationService = true;
            //                //                ToastUtils.showToast(getContext(), "开始定位...");
            //                //                startSendLanAlat();
            //                break;
            case R.id.iv_load:
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ToastUtils.showToast(getContext(), "请开启手机相机权限!");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);
                } else {
                    //启动相机程序
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    // 加载路径
                    Uri uri;
                    file = new File(mFilePath);
                    if (Build.VERSION.SDK_INT >= 24) {
                        uri = FileProvider.getUriForFile(getActivity().getApplicationContext(), "com.bt.smart.truck_broker.fileprovider", file);
                    } else {
                        uri = Uri.fromFile(file);
                    }
                    // 指定存储路径，这样就可以保存原图了
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, TAKE_PHOTO);
                }
                break;
            case R.id.iv_rece:
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ToastUtils.showToast(getContext(), "请开启手机相机权限!");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);
                } else {
                    //启动相机程序
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    // 加载路径
                    Uri uri;
                    file = new File(mFilePath);
                    if (Build.VERSION.SDK_INT >= 24) {
                        uri = FileProvider.getUriForFile(getActivity().getApplicationContext(), "com.bt.smart.truck_broker.fileprovider", file);
                    } else {
                        uri = Uri.fromFile(file);
                    }
                    // 指定存储路径，这样就可以保存原图了
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, TAKE_PHOTO_HD);
                }
                break;
            case R.id.iv_l1:
                expandImg(iv_l1);
                break;
            case R.id.iv_l2:
                expandImg(iv_l2);
                break;
            case R.id.iv_l3:
                expandImg(iv_l3);
                break;
            case R.id.iv_r1:
                expandImg(iv_r1);
                break;
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
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
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
                break;
            case TAKE_PHOTO:
                //将拍摄的照片显示出来
                try {
                    try {
                        // 获取输入流
                        is = new FileInputStream(mFilePath);
                        // 把流解析成bitmap
                        bitmap1 = BitmapFactory.decodeStream(is);
                        //*****旋转一下
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        bitmap1 = Bitmap.createBitmap(bitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);
                        if (lcount == 0) {
                            // 设置图片
                            iv_l1.setVisibility(View.VISIBLE);
                            iv_l1.setImageBitmap(bitmap1);
                            lcount++;
                        }
                        if (lcount == 1) {
                            iv_l2.setVisibility(View.VISIBLE);
                            iv_l2.setImageBitmap(bitmap1);
                            lcount++;
                        }
                        if (lcount == 2) {
                            iv_l3.setVisibility(View.VISIBLE);
                            iv_l3.setImageBitmap(bitmap1);
                            iv_load.setVisibility(View.GONE);
                            lcount++;
                        }
                        UpDataPic(file, 1);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        // 关闭流
                        try {
                            is.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            case TAKE_PHOTO_HD:
                try {
                    try {
                        // 获取输入流
                        is = new FileInputStream(mFilePath);
                        // 把流解析成bitmap
                        bitmap1 = BitmapFactory.decodeStream(is);
                        //*****旋转一下
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        bitmap1 = Bitmap.createBitmap(bitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);

                        iv_r1.setVisibility(View.VISIBLE);
                        iv_r1.setImageBitmap(bitmap1);
                        iv_rece.setVisibility(View.GONE);
                        UpDataPic(file, 2);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        // 关闭流
                        try {
                            is.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            case FOR_FACE:
                if (data != null) {
                    String mImageFaceFileUrl = data.getStringExtra("face_pic_url");
                    File file = compressImage(mImageFaceFileUrl);
                    RequestParamsFM headParam = new RequestParamsFM();
                    headParam.put("X-AUTH-TOKEN", MyApplication.userToken);
                    RequestParamsFM params = new RequestParamsFM();
                    params.put("str", getImgStr(file));
                    params.put("id", MyApplication.userID);
                    HttpOkhUtils.getInstance().doPostWithHeader(NetConfig.FACE, headParam, params, new HttpOkhUtils.HttpCallBack() {
                        @Override
                        public void onError(Request request, IOException e) {
                            ToastUtils.showToast(getContext(), "网络连接错误");
                        }

                        @Override
                        public void onSuccess(int code, String resbody) {
                            if (code != 200) {
                                ToastUtils.showToast(getContext(), "网络错误" + code);
                                return;
                            }
                            Log.i(TAG, resbody);
                            Gson gson = new Gson();
                            UpPicInfo upPicInfo = gson.fromJson(resbody, UpPicInfo.class);
                            ForTXAIFaceInfo info = gson.fromJson(upPicInfo.getData(), ForTXAIFaceInfo.class);
                            ToastUtils.showToast(getContext(), info.getMsg());
                            show2WriteMoney();
                        }
                    });
                }
                break;
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
    private boolean isBinded;
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

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 1001;//申请照相机权限结果
    private static final int REQUEST_CODE = 1003;//接收扫描结果
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

    private static final int FOR_FACE = 10086;

    private void toGetFacePic() {
        //第二个参数是需要申请的权限
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ToastUtils.showToast(getContext(), "面部认证功能，需要拍摄照片，请开启手机相机权限!");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        } else {
            Intent intent = new Intent(getContext(), GetFacePhotoActivity.class);
            startActivityForResult(intent, FOR_FACE);
        }
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
                Log.i("orderDetailInfo", resbody);
                orderDetailInfo = gson.fromJson(resbody, OrderDetailInfo.class);
                ToastUtils.showToast(getContext(), orderDetailInfo.getMessage());
                if (orderDetailInfo.isOk()) {
                    img_empty.setVisibility(View.GONE);
                    mOrder_no = orderDetailInfo.getData().getOrder_no();
                    tv_place.setText(orderDetailInfo.getData().getOrigin() + "  →  " + orderDetailInfo.getData().getDestination());
                    tv_goodsname.setText(orderDetailInfo.getData().getGoodsname() + " " + orderDetailInfo.getData().getCartype() + " " + orderDetailInfo.getData().getSh_address());
                    tv_carType.setText(orderDetailInfo.getData().getFh_address());
                    tv_name.setText(orderDetailInfo.getData().getFh_name());
                    tv_fhPlace.setText(orderDetailInfo.getData().getFh_address());
                    tv_phone.setText(orderDetailInfo.getData().getFh_telephone());
                    tv_inter.setText(orderDetailInfo.getData().getTime_interval());
                    String getloadUrl = orderDetailInfo.getData().getFloadpics();
                    String getreceUrl = orderDetailInfo.getData().getFrecepics();
                    if (CommonUtil.isNotEmpty(getloadUrl)) {
                        String[] s = getloadUrl.split(",");
                        switch (s.length) {
                            case 1:
                                GlideLoaderUtil.showImgWithIcon(getContext(), NetConfig.IMG_HEAD + s[0], R.drawable.iman, R.drawable.iman, iv_l1);
                                iv_l1.setVisibility(View.VISIBLE);
                                break;
                            case 2:
                                GlideLoaderUtil.showImgWithIcon(getContext(), NetConfig.IMG_HEAD + s[0], R.drawable.iman, R.drawable.iman, iv_l1);
                                GlideLoaderUtil.showImgWithIcon(getContext(), NetConfig.IMG_HEAD + s[1], R.drawable.iman, R.drawable.iman, iv_l2);
                                iv_l1.setVisibility(View.VISIBLE);
                                iv_l2.setVisibility(View.VISIBLE);
                                break;
                            case 3:
                                GlideLoaderUtil.showImgWithIcon(getContext(), NetConfig.IMG_HEAD + s[0], R.drawable.iman, R.drawable.iman, iv_l1);
                                GlideLoaderUtil.showImgWithIcon(getContext(), NetConfig.IMG_HEAD + s[1], R.drawable.iman, R.drawable.iman, iv_l2);
                                GlideLoaderUtil.showImgWithIcon(getContext(), NetConfig.IMG_HEAD + s[2], R.drawable.iman, R.drawable.iman, iv_l3);
                                iv_l1.setVisibility(View.VISIBLE);
                                iv_l2.setVisibility(View.VISIBLE);
                                iv_l3.setVisibility(View.VISIBLE);
                                iv_load.setVisibility(View.GONE);
                                break;
                        }
                    }
                    if (CommonUtil.isNotEmpty(getreceUrl)) {
                        GlideLoaderUtil.showImgWithIcon(getContext(), NetConfig.IMG_HEAD + getreceUrl, R.drawable.iman, R.drawable.iman, iv_r1);
                        iv_r1.setVisibility(View.VISIBLE);
                        iv_rece.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    protected void loadpics(String fstatus, String getloadUrl) {
        try {
            if (fstatus.equals("1") || fstatus.equals("3") || fstatus.equals("4") || fstatus.equals("7")) {
                if (CommonUtil.isNotEmpty(getloadUrl)) {
                    String[] s = getloadUrl.split(",");
                    switch (s.length) {
                        case 1:
                            iv_l1.setImageBitmap(getBitmap(NetConfig.IMG_HEAD + s[0]));
                            break;
                        case 2:
                            iv_l1.setImageBitmap(getBitmap(NetConfig.IMG_HEAD + s[0]));
                            iv_l2.setImageBitmap(getBitmap(NetConfig.IMG_HEAD + s[1]));
                            break;
                        case 3:
                            iv_l1.setImageBitmap(getBitmap(NetConfig.IMG_HEAD + s[0]));
                            iv_l2.setImageBitmap(getBitmap(NetConfig.IMG_HEAD + s[1]));
                            iv_l3.setImageBitmap(getBitmap(NetConfig.IMG_HEAD + s[2]));
                            iv_load.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void expandImg(ImageView iv) {
        iv.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(iv.getDrawingCache());
        iv.setDrawingCacheEnabled(false);
        ImageView iv1 = new ImageView(getActivity());
        iv1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        iv1.setImageBitmap(bitmap);
        final AlertDialog builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen).create();
        builder.show();
        Window window = builder.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(iv1);
        iv1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                builder.cancel();
            }
        });
    }

    private void UpDataPic(File file, final int kind) {
        if (null == file || !file.exists()) {
            ToastUtils.showToast(getContext(), "照片获取失败,请返回重新拍摄");
            return;
        }
        RequestParamsFM headParam = new RequestParamsFM();
        headParam.put("X-AUTH-TOKEN", MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        params.put("folder", "order");
        params.put("kind", kind);
        params.put("id", orderDetailInfo.getData().getId());
        params.put("driverId", MyApplication.userID);
        HttpOkhUtils.getInstance().upDateFile(NetConfig.PHOTO1, headParam, params, "file", file, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                if (1 == kind) {
                    ToastUtils.showToast(getContext(), "装车照片上传失败");
                } else if (2 == kind) {
                    ToastUtils.showToast(getContext(), "回单照片上传失败");
                }
            }

            @Override
            public void onSuccess(int code, String resbody) {
                if (code != 200) {
                    ToastUtils.showToast(getContext(), "网络错误" + code);
                    return;
                }
                /*{"message":"成功","data":"upload/files/1547108710666.png","ok":true,"respCode":"0"}*/
                Gson gson = new Gson();
                UpPicInfo upPicInfo = gson.fromJson(resbody, UpPicInfo.class);
                ToastUtils.showToast(getContext(), upPicInfo.getMessage());
                if (upPicInfo.isOk()) {
                    if (1 == kind) {
//                        getloadUrl = upPicInfo.getData();
                        ToastUtils.showToast(getContext(), "装车照片上传成功");
                    } else if (2 == kind) {
//                        getreceUrl = upPicInfo.getData();
                        ToastUtils.showToast(getContext(), "回单照片上传成功");
                        iv_rece.setVisibility(View.GONE);
                    }
                }
            }
        });
        return;
    }

    public static Bitmap getBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public Bitmap zoomImage(Bitmap bgimage, double newWidth,
                            double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 压缩图片（质量压缩）
     *
     * @param path
     */
    public File compressImage(String path) {
        // 设置参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        BitmapFactory.decodeFile(path, options);
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 2; // 默认像素压缩比例，压缩为原图的1/2
        int minLen = Math.min(height, width); // 原图的最小边长
        if (minLen > 100) { // 如果原始图像的最小边长大于100dp（此处单位我认为是dp，而非px）
            float ratio = (float) minLen / 100.0f; // 计算像素压缩比例
            inSampleSize = (int) ratio;
        }
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
        Bitmap bm = BitmapFactory.decodeFile(path, options); // 解码文件
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(), filename + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }


    public void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps == null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }

    /**
     * * 将图片转换成Base64编码
     * * @param imgFile 待处理图片
     * * @return
     */
    public static String getImgStr(File file) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(data));
    }
}
