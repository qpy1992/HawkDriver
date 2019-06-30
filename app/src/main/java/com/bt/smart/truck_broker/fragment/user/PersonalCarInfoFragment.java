package com.bt.smart.truck_broker.fragment.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.activity.userAct.GetDrivingCardPhotoActivity;
import com.bt.smart.truck_broker.messageInfo.ChioceAdapterContentInfo;
import com.bt.smart.truck_broker.messageInfo.UpDataDriverInfo;
import com.bt.smart.truck_broker.messageInfo.UpPicInfo;
import com.bt.smart.truck_broker.utils.GlideLoaderUtil;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.MyFragmentManagerUtil;
import com.bt.smart.truck_broker.utils.ProgressDialogUtil;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/8 16:39
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class PersonalCarInfoFragment extends Fragment implements View.OnClickListener {
    private View           mRootView;
    private TextView       tv_title;
    private ImageView      img_back;
    private ImageView      img_up_card;
    private EditText       et_name;
    private RelativeLayout rlt_carModel;
    private RelativeLayout rlt_carLength;
    private TextView       tv_carmodel;
    private TextView       tv_carlength;
    private TextView       tv_submit;
    private String         carno;//车牌号
    private String         headUrl;//头像照地址
    private String         driverUrl;//驾驶证地址
    private String         mImageDrivingCardFileUrl;//行驶证地址
    private String         personalNo;//身份证号
    private String         userName;//姓名
    private int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 10087;//照相权限申请码
    private int REQUEST_FOR_DRIVING_CARD           = 10106;
    private int RESULT_FOR_DRIVING_CARD            = 10108;
    private String getHeadUrl;//头像网络地址
    private String getDriverUrl;//驾驶证网络地址
    private String getDrivingUrl;//行驶证网络地址


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.frame_personal_car_info, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        img_back = mRootView.findViewById(R.id.img_back);
        tv_title = mRootView.findViewById(R.id.tv_title);
        img_up_card = mRootView.findViewById(R.id.img_up_card);
        et_name = mRootView.findViewById(R.id.et_name);
        rlt_carModel = mRootView.findViewById(R.id.rlt_carModel);
        tv_carmodel = mRootView.findViewById(R.id.tv_carmodel);
        rlt_carLength = mRootView.findViewById(R.id.rlt_carLength);
        tv_carlength = mRootView.findViewById(R.id.tv_carlength);
        tv_submit = mRootView.findViewById(R.id.tv_submit);
    }

    private void initData() {
        tv_title.setText("司机身份认证");
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(this);
        img_up_card.setOnClickListener(this);
        rlt_carModel.setOnClickListener(this);
        rlt_carLength.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                MyFragmentManagerUtil.closeTopFragment(this);
                break;
            case R.id.img_up_card:
                //拍摄驾驶证
                photoDrivingPic();
                break;
            case R.id.rlt_carModel:
                toSelectModelLength();
                break;
            case R.id.rlt_carLength:
                toSelectModelLength();
                break;
            case R.id.tv_submit:
                //提交
                submitDriveInfo();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_FOR_DRIVING_CARD == requestCode && resultCode == RESULT_FOR_DRIVING_CARD) {
            mImageDrivingCardFileUrl = data.getStringExtra("driving_pic_url");
            GlideLoaderUtil.showImageView(getContext(), mImageDrivingCardFileUrl, img_up_card);
        }
    }

    private void toSelectModelLength() {
        SelectModelLengthFragment mollengthFt = new SelectModelLengthFragment();
        mollengthFt.setTopFragment(this);
        FragmentTransaction ftt = getFragmentManager().beginTransaction();
        ftt.add(R.id.frame, mollengthFt, "mollengthFt");
        ftt.addToBackStack("mollengthFt");
        ftt.commit();
    }

    private void photoDrivingPic() {
        //第二个参数是需要申请的权限
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ToastUtils.showToast(getContext(), "面部认证功能，需要拍摄照片，请开启手机相机权限!");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        } else {
            Intent intent = new Intent(getContext(), GetDrivingCardPhotoActivity.class);
            startActivityForResult(intent, REQUEST_FOR_DRIVING_CARD);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent(getContext(), GetDrivingCardPhotoActivity.class);
        startActivityForResult(intent, REQUEST_FOR_DRIVING_CARD);
    }

    private void submitDriveInfo() {
        carno = String.valueOf(et_name.getText()).trim();
        if ("".equals(carno) || "请输入车牌号".equals(carno)) {
            ToastUtils.showToast(getContext(), "车牌号不能为空");
            return;
        }
        //上传三张照片
        sendPicList();

    }

    private void sendPicList() {
        ProgressDialogUtil.startShow(getContext(), "正在上传...");
        UpDataPic(headUrl, 1);
        UpDataPic(driverUrl, 2);
        UpDataPic(mImageDrivingCardFileUrl, 3);
    }

    private int times;

    public void setSomeInfo(String img_headUrl, String img_driverUrl, String user_name, String personNo) {
        headUrl = img_headUrl;
        driverUrl = img_driverUrl;
        userName = user_name;
        personalNo = personNo;
    }

    private void UpDataPic(String fileUrl, final int kind) {
        File file = new File(fileUrl);
        if (null == file || !file.exists()) {
            ToastUtils.showToast(getContext(), "照片获取失败,请返回重新拍摄");
            return;
        }
        RequestParamsFM headParam = new RequestParamsFM();
        headParam.put("X-AUTH-TOKEN", MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        params.put("folder","Auth");
        params.put("kind",kind);
        params.put("type",1);
        HttpOkhUtils.getInstance().upDateFile(NetConfig.PHOTO, headParam, params, "file", file, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                if (1 == kind) {
                    times++;
                    ToastUtils.showToast(getContext(), "头像上传失败");
                } else if (2 == kind) {
                    times++;
                    ToastUtils.showToast(getContext(), "驾驶证上传失败");
                } else if (3 == kind) {
                    times++;
                    ToastUtils.showToast(getContext(), "行驶证上传失败");
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
                        getHeadUrl = upPicInfo.getData();
                        times++;
                        ToastUtils.showToast(getContext(), "头像上传成功");
                    } else if (2 == kind) {
                        getDriverUrl = upPicInfo.getData();
                        times++;
                        ToastUtils.showToast(getContext(), "驾驶证上传成功");
                    } else if (3 == kind) {
                        getDrivingUrl = upPicInfo.getData();
                        times++;
                        ToastUtils.showToast(getContext(), "行驶证上传成功");
                    }
                    if (times == 3) {
                        //提交信息
                        sendDriverInfo();
                    }
                }
            }
        });
        return;
    }

    //提交认证信息
    private void sendDriverInfo() {
        RequestParamsFM headParams = new RequestParamsFM();
        headParams.put("X-AUTH-TOKEN", MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        params.put("submitMobile", MyApplication.userPhone);
        params.put("name", userName);//姓名
        params.put("idnumber", personalNo);//身份证号
        params.put("headpic", getHeadUrl);//头像
        params.put("drivingLicence", getDriverUrl);//驾驶证
        params.put("vehicleLicence", getDrivingUrl);//行驶证
        params.put("fcartype", carModel);//车型
        params.put("fcarlength", carLeng);//车长
        params.put("fcarno", carno);//车牌号
        params.setUseJsonStreamer(true);
        HttpOkhUtils.getInstance().doPostWithHeader(NetConfig.DRIVERGDCONTROLLER, headParams, params, new HttpOkhUtils.HttpCallBack() {
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
                UpDataDriverInfo upDataDriverInfo = gson.fromJson(resbody, UpDataDriverInfo.class);
                ToastUtils.showToast(getContext(), upDataDriverInfo.getMessage());
                if (upDataDriverInfo.isOk()) {
                    MyApplication.checkStatus = "1";
                    getActivity().finish();
                }
            }
        });
    }

    private String carLeng  = "";
    private String carModel = "";

    public void setChioceTerm(List<ChioceAdapterContentInfo> lengthData, List<ChioceAdapterContentInfo> modelData) {
        if (null != lengthData) {
            for (int i = 0; i < lengthData.size(); i++) {
                if (lengthData.get(i).isChioce()) {
                    if ("".equals(carLeng)) {
                        carLeng = carLeng + lengthData.get(i).getCont();
                    } else {
                        carLeng = carLeng + "/" + lengthData.get(i).getCont();
                    }

                }
            }
        }
        tv_carlength.setText(carLeng);

        if (null != modelData) {
            for (int i = 0; i < modelData.size(); i++) {
                if (modelData.get(i).isChioce()) {
                    if ("".equals(carModel)) {
                        carModel = carModel + modelData.get(i).getCont();
                    } else {
                        carModel = carModel + "/" + modelData.get(i).getCont();
                    }

                }
            }
        }
        tv_carmodel.setText(carModel);
    }
}
