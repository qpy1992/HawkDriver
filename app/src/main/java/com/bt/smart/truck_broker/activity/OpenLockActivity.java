package com.bt.smart.truck_broker.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bt.smart.truck_broker.BaseActivity;
import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.ble.BLEDevice;
import com.bt.smart.truck_broker.ble.GattAttributes;
import com.bt.smart.truck_broker.messageInfo.LoginInfo;
import com.bt.smart.truck_broker.utils.CRCUtil;
import com.bt.smart.truck_broker.utils.CommandUtil;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.LocationUtils;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.github.ring.CircleProgress;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * @创建者 AndyYan
 * @创建时间 2019/2/18 15:09
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class OpenLockActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "OpenLockActivity";
    private ImageView      img_back;
    private TextView       tv_title;
    private TextView       tv_test;//显示连接进程
    private TextView       tv_start;//
    private CircleProgress circleprogress;
    private Handler        mProhandler;
    private String         mBlueMac;
    private String         mOrderNo;
    private String         mBlueXlh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_lock);
        initView();
        initData();
    }

    private void initView() {
        img_back = findViewById(R.id.img_back);
        tv_title = findViewById(R.id.tv_title);
        tv_test = findViewById(R.id.tv_test);
        tv_start = findViewById(R.id.tv_start);
        circleprogress = findViewById(R.id.circleprogress);
    }

    private void initData() {
        img_back.setVisibility(View.VISIBLE);
        tv_title.setText("开锁");
        mBlueMac = getIntent().getStringExtra("macID");
        mOrderNo = getIntent().getStringExtra("orderNo");
        mBlueXlh = getIntent().getStringExtra("blueXlh");
        //先开启蓝牙搜索
        //searchBlueDevice();
        initBLE();

        //初始化进度条
        startReadScend();

        img_back.setOnClickListener(this);
        tv_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_start:
                String tv_state = String.valueOf(tv_start.getText()).trim();
                if ("开始搜索".equals(tv_state)) {
                    if (!mScanning) {
                        scanLeDevice(true);
                    } else {
                        if (null != findDevice) {
                            //先连接，再获取key
                            contactBlueDevice(findDevice);
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPermission();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mScanning) {
            scanLeDevice(false);
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProhandler.removeMessages(0);
        mProhandler = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 2) {
            // 请求定位权限
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                ToastUtils.showToast(this, "您未授予权限");
            }
        }
    }

    private int maxTime = 180;//匹配时间单位s
    private int count   = 180;//搜索时间、单位秒
    private boolean isSendKey;//是否发送获取key的指令了。

    private void startReadScend() {
        mProhandler = new Handler();
        circleprogress.setProgress(0.0f);
        circleprogress.setMaxProgress(maxTime);
        mProhandler.postDelayed(new Runnable() {
            public void run() {
                mProhandler.postDelayed(this, 1000);//递归执行，一秒执行一次
                count--;
                circleprogress.setProgress(maxTime - count);
                if (count == 0) {
                    //连接时间超过*分钟，可关闭界面
                    circleprogress.setProgress(maxTime);
                    mProhandler.removeCallbacks(this);
                } else {
                    if (!isSendKey) {
                        if (null != mBLEGCWrite) {
                            //发送指令 获取key
                            getTheKey();
                            isSendKey = true;
                        }
                    }
                }
            }
        }, 1000);
    }

    private void initBLE() {
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, 1);
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CONNECT_BLE);
        intentFilter.addAction(ACTION_LOCK_CLOSE);
        intentFilter.addAction(ACTION_GET_LOCK_KEY);
        intentFilter.addAction(ACTION_DISCONNECT);
        intentFilter.addAction(ACTION_BLE_LOCK_OPEN_STATUS);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    private Handler mHandler = new Handler();
    private boolean          mScanning;
    private BluetoothAdapter mBluetoothAdapter;
    // Stops scanning after 10 seconds
    private static final long SCAN_PERIOD = 180000; // 10s for scanning
    private BLEDevice findDevice;//记录发现的蓝牙设备

    private BluetoothAdapter.LeScanCallback mLescanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            final BLEDevice bleDevice = new BLEDevice(device, rssi);
            //匹配到蓝牙设备，关闭搜索，开始连接
            if (mBlueMac.equals(device.getAddress())) {
                findDevice = bleDevice;
                if (mScanning) {
                    scanLeDevice(false);
                }
                tv_test.setText("发现设备");
                //先连接，再获取key
                contactBlueDevice(bleDevice);
            }
        }
    };

    //秘钥
    private byte bleCKey = 0;
    private BluetoothGatt               mBLEGatt;
    //蓝牙设备所拥有的特征
    private BluetoothGattCharacteristic mBLEGCWrite;
    private BluetoothGattCharacteristic mBLEGCRead;
    private static final String            ACTION_CONNECT_BLE          = "com.omni.bleDemo.ACTION_CONNECT_BLE";
    private static final String            ACTION_DISCONNECT           = "com.omni.bleDemo.ACTION_DISCONNECT";
    private static final String            ACTION_GET_LOCK_KEY         = "com.omni.bleDemo.ACTION_GET_LOCK_KEY";
    private static final String            ACTION_LOCK_CLOSE           = "com.omni.bleDemo.ACTION_LOCK_CLOSE";
    private static final String            ACTION_BLE_LOCK_OPEN_STATUS = "com.omni.bleDemo.ACTION_BLE_LOCK_OPEN_STATUS";
    //广播接收器
    private              BroadcastReceiver broadcastReceiver           = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_CONNECT_BLE.equals(intent.getAction())) {//连接上蓝牙
                tv_test.setText("连接上设备1...");
            } else if (ACTION_LOCK_CLOSE.equals(intent.getAction())) {
                handler.sendEmptyMessage(HANDLER_CLOSE);
            } else if (ACTION_GET_LOCK_KEY.equals(intent.getAction())) {//接收蓝牙传回的秘钥
                //获取到key
                handler.sendEmptyMessage(HANDLER_GETKEY);
            } else if (ACTION_DISCONNECT.equals(intent.getAction())) {
                //断开连接 、 重新搜索连接
                tv_test.setText("断开连接,请退出重新开锁");
            } else if (ACTION_BLE_LOCK_OPEN_STATUS.equals(intent.getAction())) {//锁打开了
                //                int status = intent.getIntExtra("status", 0);
                //                long timestamp = intent.getLongExtra("timestamp", 0L);
                //                Log.i(TAG, "onReceive: status=" + status);
                //                Log.i(TAG, "onReceive: timestamp=" + timestamp);
                handler.sendEmptyMessage(HANDLER_OPEN);
            }
        }
    };

    public static final int     HANDLER_GETKEY = 1;
    public static final int     HANDLER_CLOSE  = 2;
    public static final int     HANDLER_OPEN   = 3;
    private             Handler handler        = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_CLOSE:
                    tv_test.setText("设备已关闭");
                    sendLockResp();
                    ToastUtils.showToast(OpenLockActivity.this, "设备已关闭");
                    break;
                case HANDLER_OPEN:
                    //添加开锁记录
                    addOpenRecord();
                    tv_test.setText("设备已打开");
                    sendOpenResponse();
                    ToastUtils.showToast(OpenLockActivity.this, "设备已打开");
                    break;
                case HANDLER_GETKEY:
                    //获取到秘钥
                    tv_test.setText("正在开锁2...");
                    //发送开锁指令
                    sendOpenCommand();
                    break;
            }
        }
    };

    //搜索蓝牙设备
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLescanCallback);
                }
            }, SCAN_PERIOD);
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLescanCallback);//扫描低功耗蓝牙设备
            //正在扫描
            tv_test.setText("正在搜索设备锁");
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLescanCallback);
            //终止扫描了
            tv_test.setText("--");
        }
    }

    private void contactBlueDevice(BLEDevice bleDevice) {
        tv_test.setText("开始连接设备...");
        //发起连接
        mBLEGatt = bleDevice.getDevice().connectGatt(this, false, mGattCallback);
    }

    private void getTheKey() {
        //获取秘钥
        if (mBLEGatt != null) {
            byte[] crcOrder = CommandUtil.getCRCKeyCommand2();//*-获取秘钥  获取秘钥的指令
            Log.i(TAG, "onDescriptorWrite: GET KEY COMM=" + getCommForHex(crcOrder));
            mBLEGCWrite.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
            mBLEGCWrite.setValue(crcOrder);//写入值
            mBLEGatt.writeCharacteristic(mBLEGCWrite);
            tv_test.setText("正在获取秘钥...");
        } else {
            Toast.makeText(this, "请重新连接设备", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendOpenCommand() {//发送开锁指令
        if (mBLEGatt != null) {
            int uid = 1;
            long timestamp = System.currentTimeMillis() / 1000;//当前时间 秒s
            byte[] crcOrder = CommandUtil.getCRCOpenCommand(uid, bleCKey, timestamp);
            Log.i(TAG, "sendOpenCommand: openComm=" + getCommForHex(crcOrder));
            mBLEGCWrite.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
            mBLEGCWrite.setValue(crcOrder);//写入值
            mBLEGatt.writeCharacteristic(mBLEGCWrite);
        } else {
            Toast.makeText(this, "请退出重新连接蓝牙设备", Toast.LENGTH_SHORT).show();
        }
    }

    //蓝牙返回接收器
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                //发送本地连接蓝牙广播
                sendLocalBroadcast(ACTION_CONNECT_BLE);
                gatt.discoverServices();
            } else {
                Log.i(TAG, "onConnectionStateChange: ble disconnection");
                //发送断开连接广播
                sendLocalBroadcast(ACTION_DISCONNECT);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {//发现服务后
            List<BluetoothGattService> services = gatt.getServices();
            for (BluetoothGattService bgs : services) {
                if (bgs.getUuid().equals(GattAttributes.UUID_SERVICE)) {
                    //service 6e400001-b5a3-f393-e0a9-e50e24dcca9e
                    //获取到蓝牙GATT对应服务
                    BluetoothGattService bleGattService = gatt.getService(GattAttributes.UUID_SERVICE);
                    if (bleGattService != null) {
                        //write 6e400002-b5a3-f393-e0a9-e50e24dcca9e
                        //蓝牙写入服务线
                        mBLEGCWrite = bleGattService.getCharacteristic(GattAttributes.UUID_CHARACTERISTIC_WRITE);

                        //Notify 1.6e400003-b5a3-f393-e0a9-e50e24dcca9e
                        //1. get read characteristic
                        //蓝牙接收读取线
                        mBLEGCRead = bleGattService.getCharacteristic(GattAttributes.UUID_CHARACTERISTIC_READ);
                        //2. descriptor 00002902-0000-1000-8000-00805f9b34fb
                        //2. set descriptor notify 设置描述符通知
                        BluetoothGattDescriptor descriptor = mBLEGCRead.getDescriptor(GattAttributes.UUID_NOTIFICATION_DESCRIPTOR);
                        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);

                        mBLEGatt.writeDescriptor(descriptor);
                    }
                } else if (bgs.getUuid().equals(GattAttributes.UUID_OBL2_SERVICE)) {
                    BluetoothGattService bleGattService = gatt.getService(GattAttributes.UUID_OBL2_SERVICE);

                    mBLEGCWrite = bleGattService.getCharacteristic(GattAttributes.UUID_OBL2_CHARACTERISTIC_WRITE);
                    //1.0783b03e-8535-b5a0-7140-a304d2495cb8
                    //1. get read characteristic
                    mBLEGCRead = bleGattService.getCharacteristic(GattAttributes.UUID_OBL2_CHARACTERISTIC_READ);
                    //2. descriptor 00002902-0000-1000-8000-00805f9b34fb
                    //2. set descriptor notify
                    BluetoothGattDescriptor descriptor = mBLEGCRead.getDescriptor(GattAttributes.UUID_NOTIFICATION_DESCRIPTOR);
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    mBLEGatt.writeDescriptor(descriptor);
                }
            }
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            //3. set characteristic notify
            gatt.setCharacteristicNotification(mBLEGCRead, true);
            //            Log.i(TAG, "onDescriptorWrite: request the ope key");
            int uid = 1; // user login id

            byte[] crcOrder = CommandUtil.getCRCKeyCommand2();
            // Log.i(TAG, "onDescriptorWrite: GET KEY COMM=" + getCommForHex(crcOrder));
            mBLEGCWrite.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
            mBLEGCWrite.setValue(crcOrder);
            mBLEGatt.writeCharacteristic(mBLEGCWrite);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            byte[] values = characteristic.getValue();
            // Log.i(TAG, "onCharacteristicChanged: values=" + getCommForHex(values));
            int start = 0;
            int copyLen = 0;
            for (int i = 0; i < values.length; i++) {
                if ((values[i] & 0xFF) == 0xFE) {
                    start = i;
                    int randNum = (values[i + 1] - 0x32) & 0xFF; //BF
                    int len = ((values[i + 4]) & 0xFF) ^ randNum;
                    copyLen = len + 7; //16+
                    break;
                }
            }
            if (copyLen == 0)
                return;
            byte[] real = new byte[copyLen];
            //创建了一个空数组real，在将values数组的第start位到第copyLen位 复制到目标数组real 0位开始
            System.arraycopy(values, start, real, 0, copyLen);//源数组  源数组要复制的起始位置  目标数组  目标数组开始位  复制的长度

            byte[] command = new byte[values.length - 2];
            command[0] = values[0]; // 包头
            if (CRCUtil.CheckCRC(real)) {
                // crc校验成功
                byte head = (byte) (real[1] - 0x32);
                command[1] = head;
                for (int i = 2; i < real.length - 2; i++) {
                    command[i] = (byte) (real[i] ^ head);
                }
                handCommand(command);
            } else {
                // CRC校验失败
                // Log.i(TAG, "onCharacteristicChanged: CRC校验失败");
            }
        }
    };

    private void handCommand(byte[] command) {
        switch (command[3]) {
            case 0x11:
                // get key
                handKey(command);
                break;
            case 0x22:
                // lock 锁关闭后发来的信息
                handLockClose(command);
                break;
            case 0x21:
                handLockOpen(command);
                break;
        }
    }

    // get key 获取秘钥
    private void handKey(byte[] command) {
        //获取秘钥
        bleCKey = command[5];
        Log.i(TAG, "handKey: key=0x" + String.format("%02X", bleCKey));
        sendLocalBroadcast(ACTION_GET_LOCK_KEY);
    }

    private void handLockClose(byte[] command) {
        int status = command[5];
        long timestamp = ((command[6] & 0xFF) << 24) | ((command[7] & 0xff) << 16) | ((command[8] & 0xFF) << 8) | (command[9] & 0xFF);
        int runTime = ((command[10] & 0xFF) << 24) | ((command[11] & 0xff) << 16) | ((command[12] & 0xFF) << 8) | (command[13] & 0xFF);
        //        Log.i(TAG, "handLockClose: status=" + status);
        //        Log.i(TAG, "handLockClose: timestamp=" + timestamp);
        //        Log.i(TAG, "handLockClose: runTime=" + runTime);
        Intent intent = new Intent(ACTION_LOCK_CLOSE);
        intent.putExtra("runTime", runTime);
        intent.putExtra("timestamp", timestamp);
        intent.putExtra("status", status);
        sendLocalBroadcast(intent);
    }

    private void handLockOpen(byte[] command) {
        int status = command[5];
        long timestamp = ((command[6] & 0xFF) << 24) | ((command[7] & 0xff) << 16) | ((command[8] & 0xFF) << 8) | (command[9] & 0xFF);
        Intent intent = new Intent(ACTION_BLE_LOCK_OPEN_STATUS);
        intent.putExtra("status", status);
        intent.putExtra("timestamp", timestamp);
        sendLocalBroadcast(intent);
    }

    private void sendLocalBroadcast(String action) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(action));
    }

    private void sendLocalBroadcast(Intent intent) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendLockResp() {
        int uid = 1;
        byte[] crcOrder = CommandUtil.getCRCLockCommand(bleCKey);
        Log.i(TAG, "sendOpenResponse: 上锁回复=" + getCommForHex(crcOrder));
        mBLEGCWrite.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        mBLEGCWrite.setValue(crcOrder);
        mBLEGatt.writeCharacteristic(mBLEGCWrite);

    }

    public void sendOpenResponse() {
        byte[] crcOrder = CommandUtil.getCRCOpenResCommand(bleCKey);
        Log.i(TAG, "sendOpenResponse: 开锁回复=" + getCommForHex(crcOrder));
        mBLEGCWrite.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        mBLEGCWrite.setValue(crcOrder);
        mBLEGatt.writeCharacteristic(mBLEGCWrite);
    }

    //获取16进制
    private String getCommForHex(byte[] values) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < values.length; i++) {
            sb.append(String.format("%02X,", values[i]));
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 检测定位权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }
    }

    private void addOpenRecord() {
        final Location location = LocationUtils.getInstance(this).showLocation();
       /* {
            "lat": "31.896255",
                "lng": "121.182962",
                "lockXlh": "66755004297",
                "orderno":"2019021500609093396",
                "paddress": "",
                "pid": "2c979074687943f3016879727bc70001",
                "prole": 0
        }*/
        RequestParamsFM heardParam = new RequestParamsFM();
        heardParam.put("X-AUTH-TOKEN", MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        if (null != location) {
            params.put("lat", "" + location.getLatitude());
            params.put("lng", "" + location.getLongitude());
            LocationUtils.getInstance(this).removeLocationUpdatesListener();
        } else {
            params.put("lat", 31.896255);
            params.put("lng", 121.182962);
        }
        params.put("lockXlh", mBlueXlh);//锁的序列号
        params.put("orderno", mOrderNo);//订单号
        params.put("paddress", "");//开锁地址
        params.put("pid", MyApplication.userID);//开锁人的id
        params.put("prole", 0);//开锁人的角色
        //params.put("ptime", );//开锁时间
        params.setUseJsonStreamer(true);
        HttpOkhUtils.getInstance().doPostWithHeader(NetConfig.DRIVERORDERCONTROLLER_ADDRECORD, heardParam, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ToastUtils.showToast(OpenLockActivity.this, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                //{"message":"成功","size":0,"data":"添加成功！","respCode":"0","ok":true}
                if (code != 200) {
                    ToastUtils.showToast(OpenLockActivity.this, "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                LoginInfo loginInfo = gson.fromJson(resbody, LoginInfo.class);
                if (loginInfo.isOk()) {
                    ToastUtils.showToast(OpenLockActivity.this, "添加开锁记录成功");
                }
            }
        });
    }
    //    private int REQUEST_ENABLE                  = 400;
    //    private int BLUETOOTH_DISCOVERABLE_DURATION = 120;//Bluetooth 设备可见时间，单位：秒，不设置默认120s。
    //    private void searchBlueDevice() {
    //        //搜索附近蓝牙设备
    //        mBtData.clear();
    //        boolean bluetoothSupported = BluetoothManagerUtils.isBluetoothSupported();
    //        if (bluetoothSupported) {
    //            boolean bluetoothEnabled = BluetoothManagerUtils.isBluetoothEnabled();
    //            if (!bluetoothEnabled) {
    //                //弹出对话框提示用户是否打开
    //                Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    //                // 设置 Bluetooth 设备可以被其它 Bluetooth 设备扫描到
    //                enabler.setAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
    //                // 设置 Bluetooth 设备可见时间
    //                enabler.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, BLUETOOTH_DISCOVERABLE_DURATION);
    //                startActivityForResult(enabler, REQUEST_ENABLE);
    //            } else {
    //                //申请权限
    //                //needLoactionRight();
    //                //开始搜索
    //                startSearchBluetooth();
    //            }
    //        } else {
    //            ToastUtils.showToast(this, "当前设备不支持蓝牙功能");
    //        }
    //    }
}
