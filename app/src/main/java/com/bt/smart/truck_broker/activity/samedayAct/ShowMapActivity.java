package com.bt.smart.truck_broker.activity.samedayAct;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.bt.smart.truck_broker.BaseActivity;
import com.bt.smart.truck_broker.R;

import java.math.BigDecimal;

import static java.lang.StrictMath.atan2;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.sqrt;

public class ShowMapActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tv_title;
    private MapView mMapView;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private TextView tv_nav;
    private PopupWindow popupWindow;
    private double lngMe;//自己经度
    private double latMe;//自己维度
    private double lngTarg = 108.940174;//目的地经度
    private double latTarg = 34.341568;//目的地维度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaode_map);
        setView();
        mMapView.onCreate(savedInstanceState);
        setData();
    }

    private void setView() {
        img_back = findViewById(R.id.img_back);
        tv_title = findViewById(R.id.tv_title);
        mMapView = findViewById(R.id.gaodeMap);
        tv_nav = findViewById(R.id.tv_nav);
    }

    private void setData() {
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(this);
        tv_title.setText("地图");

        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        //显示定位蓝点
        showSelfLocation();
        tv_nav.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_nav://选择导航地图：高德或者百度
                showChoseView(tv_nav);
                break;
        }
    }

    private void showChoseView(View v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(this).inflate(R.layout.view_choice_map_popup, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        //设置消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //设置背景色
                setBackgroundAlpha(1.0f);
            }
        });
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(view);
        //设置背景色
        setBackgroundAlpha(0.5f);
    }

    //设置PopupWindow的View点击事件
    private void setOnPopupViewClick(View view) {
        TextView tv_gd, tv_bd, tv_cancel;
        tv_gd = (TextView) view.findViewById(R.id.tv_gd);//相册
        tv_bd = (TextView) view.findViewById(R.id.tv_bd);//拍照
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);//取消
        tv_gd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toGaodeMap();
            }
        });
        tv_bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toBaiduMap();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != popupWindow) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    private void toGaodeMap() {
        //头部 后面的sourceApplicaiton填自己APP的名字//com.jarvis.mytaobaotest
        String GAODE_HEAD = "androidamap://route?sourceApplication=MyApplication";
        //起点经度
        String GAODE_SLON = "&slon=";
        //起点纬度
        String GAODE_SLAT = "&slat=";
        //起点名字
        String GAODE_SNAME = "&sname=";
        //终点经度
        String GAODE_DLON = "&dlon=";
        //终点纬度
        String GAODE_DLAT = "&dlat=";
        //终点名字
        String GAODE_DNAME = "&dname=";
        // dev 起终点是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
        // t = 1(公交) =2（驾车） =4(步行)
        String GAODE_MODE = "&dev=0&t=2";
        //高德地图包名
        String GAODE_PKG = "com.autonavi.minimap";

        //检测安装和唤起
        if (checkMapAppsIsExist(ShowMapActivity.this, GAODE_PKG)) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setPackage("com.autonavi.minimap");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse(GAODE_HEAD + GAODE_SLAT + latMe + GAODE_SLON + lngMe +
                    GAODE_SNAME + "我的位置" + GAODE_DLAT + latTarg + GAODE_DLON + lngTarg +
                    GAODE_DNAME + "终点" + GAODE_MODE));
            startActivity(intent);
        } else {
            Toast.makeText(ShowMapActivity.this, "高德地图未安装", Toast.LENGTH_SHORT).show();
        }
    }

    private void toBaiduMap() {
        //头部 添加相应地区
        String BAIDU_HEAD = "baidumap://map/direction?region=0";
        //起点的经纬度
        String BAIDU_ORIGIN = "&origin=";
        //终点的经纬度
        String BAIDU_DESTINATION = "&destination=";
        //路线规划方式
        String BAIDU_MODE = "&mode=driving";
        //百度地图的包名
        String BAIDU_PKG = "com.baidu.BaiduMap";

        CoodinateCovertor covertor = new CoodinateCovertor();
        LngLat lngLat = new LngLat(lngMe, latMe);
        LngLat startlngLat = covertor.bd_encrypt(lngLat);
        LngLat lngLat2 = new LngLat(lngTarg, latTarg);
        LngLat endlngLat = covertor.bd_encrypt(lngLat2);

        //检测地图是否安装和唤起
        if (checkMapAppsIsExist(ShowMapActivity.this, BAIDU_PKG)) {
            Toast.makeText(ShowMapActivity.this, "百度地图已经安装", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setData(Uri.parse(BAIDU_HEAD + BAIDU_ORIGIN + startlngLat.getLantitude()
                    + "," + startlngLat.getLongitude() + BAIDU_DESTINATION + endlngLat.getLantitude() + "," + endlngLat.getLongitude()
                    + BAIDU_MODE));
            startActivity(intent);
        } else {
            Toast.makeText(ShowMapActivity.this, "百度地图未安装", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 百度地图坐标和火星坐标转换
     * Created by 明明如月 on 2017-03-22.
     */
    public class CoodinateCovertor {
        private double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

        /**
         * 对double类型数据保留小数点后多少位
         * 高德地图转码返回的就是 小数点后6位，为了统一封装一下
         *
         * @param digit 位数
         * @param in    输入
         * @return 保留小数位后的数
         */
        double dataDigit(int digit, double in) {
            return new BigDecimal(in).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        /**
         * 将火星坐标转变成百度坐标
         *
         * @param lngLat_gd 火星坐标（高德、腾讯地图坐标等）
         * @return 百度坐标
         */
        public LngLat bd_encrypt(LngLat lngLat_gd) {
            double x = lngLat_gd.getLongitude(), y = lngLat_gd.getLantitude();
            double z = sqrt(x * x + y * y) + 0.00002 * sin(y * x_pi);
            double theta = atan2(y, x) + 0.000003 * cos(x * x_pi);
            return new LngLat(dataDigit(6, z * cos(theta) + 0.0065), dataDigit(6, z * sin(theta) + 0.006));

        }

        /**
         * 将百度坐标转变成火星坐标
         *
         * @param lngLat_bd 百度坐标（百度地图坐标）
         * @return 火星坐标(高德 、 腾讯地图等)
         */
        LngLat bd_decrypt(LngLat lngLat_bd) {
            double x = lngLat_bd.getLongitude() - 0.0065, y = lngLat_bd.getLantitude() - 0.006;
            double z = sqrt(x * x + y * y) - 0.00002 * sin(y * x_pi);
            double theta = atan2(y, x) - 0.000003 * cos(x * x_pi);
            return new LngLat(dataDigit(6, z * cos(theta)), dataDigit(6, z * sin(theta)));

        }

        //测试代码
        public void main(String[] args) {
            LngLat lngLat_bd = new LngLat(120.153192, 30.25897);
            System.out.println(bd_decrypt(lngLat_bd));
        }
    }

    /**
     * 经纬度点封装
     * Created by 明明如月 on 2017-03-22.
     */
    public class LngLat {
        private double longitude;//经度
        private double lantitude;//维度

        public LngLat() {
        }

        public LngLat(double longitude, double lantitude) {
            this.longitude = longitude;
            this.lantitude = lantitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLantitude() {
            return lantitude;
        }

        public void setLantitude(double lantitude) {
            this.lantitude = lantitude;
        }

        @Override
        public String toString() {
            return "LngLat{" + "longitude=" + longitude + ", lantitude=" + lantitude + '}';
        }
    }

    /**
     * 检测地图应用是否安装
     *
     * @param context
     * @param packagename
     * @return
     */
    public boolean checkMapAppsIsExist(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    private void showSelfLocation() {
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                latMe = location.getLatitude();
                lngMe = location.getLongitude();
            }
        });

        //显示货源定位
        showOrderDetail();
    }

    private void showOrderDetail() {
        //绘制默认 Marker
        LatLng latLng = new LatLng(39.906901, 116.397972);
        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));

        LatLng latLng2 = new LatLng(34.341568, 108.940174);
        //绘制自定义 Marker
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng2);
        markerOption.title("西安市").snippet("西安市：34.341568, 108.940174");
        markerOption.draggable(true);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_totop)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        aMap.addMarker(markerOption);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng2));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mMapView.onSaveInstanceState(outState);
    }
}
