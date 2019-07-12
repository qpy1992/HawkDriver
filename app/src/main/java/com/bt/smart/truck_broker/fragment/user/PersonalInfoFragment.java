package com.bt.smart.truck_broker.fragment.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.activity.userAct.GetDriveCardPhotoActivity;
import com.bt.smart.truck_broker.activity.userAct.GetFacePhotoActivity;
import com.bt.smart.truck_broker.utils.EditTextUtils;
import com.bt.smart.truck_broker.utils.GlideLoaderUtil;
import com.bt.smart.truck_broker.utils.MyTextUtils;
import com.bt.smart.truck_broker.utils.ToastUtils;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/8 9:54
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class PersonalInfoFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    private ImageView img_back;
    private TextView tv_title;
    //    private ImageView img_up_head;
    private ImageView img_up_card;
    private TextView tv_name;
    private TextView tv_code;
    private TextView tv_submit;
    private int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 10087;//照相权限申请码
    private int REQUEST_FOR_FACE = 10086;
    private int RESULT_FOR_FACE = 10088;
    private int REQUEST_FOR_CARD = 10096;
    private int RESULT_FOR_CARD = 10098;
    private String mImageFaceFileUrl;
    private String mImageCardFileUrl;
    private String testFileUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.frame_personal_info, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        img_back = mRootView.findViewById(R.id.img_back);
        tv_title = mRootView.findViewById(R.id.tv_title);
//        img_up_head = mRootView.findViewById(R.id.img_up_head);
        img_up_card = mRootView.findViewById(R.id.img_up_card);
        tv_name = mRootView.findViewById(R.id.tv_name);
        tv_code = mRootView.findViewById(R.id.tv_code);
        tv_submit = mRootView.findViewById(R.id.tv_submit);
    }

    private void initData() {
        img_back.setVisibility(View.VISIBLE);
        tv_title.setText("司机身份认证");
        img_back.setOnClickListener(this);
//        img_up_head.setOnClickListener(this);
        img_up_card.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_name.setText(MyApplication.userName);
        tv_code.setText(MyApplication.userSFID);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
//            case R.id.img_up_head:
//                //上传人脸头像,人脸活体检测(暂无)
//                toGetFacePic();
//                break;
            case R.id.img_up_card:
                //上传驾驶证
                toPhotoDriveCard();
                break;
            case R.id.tv_submit:
                //下一步，填写车辆信息
                toWriteCarInfo();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_FOR_FACE == requestCode && resultCode == RESULT_FOR_FACE) {
            mImageFaceFileUrl = data.getStringExtra("face_pic_url");
//            GlideLoaderUtil.showImageView(getContext(), mImageFaceFileUrl, img_up_head);
        }
        if (REQUEST_FOR_CARD == requestCode && resultCode == RESULT_FOR_CARD) {
            mImageCardFileUrl = data.getStringExtra("card_pic_url");
            GlideLoaderUtil.showImageView(getContext(), mImageCardFileUrl, img_up_card);
        }
        //相册返回，获取图片路径
        if (requestCode == IMAGE && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            testFileUrl = imagePath;
            ToastUtils.showToast(getContext(), "图片地址：" + testFileUrl);
            c.close();
        }
    }

    private void toWriteCarInfo() {
//        if (null == mImageFaceFileUrl || "".equals(mImageFaceFileUrl)) {
//            ToastUtils.showToast(getContext(), "未拍摄人脸");
//            return;
//        }
        if (null == mImageCardFileUrl || "".equals(mImageCardFileUrl)) {
            ToastUtils.showToast(getContext(), "未拍摄驾驶证");
            return;
        }
//        if (MyTextUtils.isTvTextEmpty(tv_name, "--")) {
//            ToastUtils.showToast(getContext(), "姓名不能为空");
//            return;
//        }

//        if (EditTextUtils.isEmpty(et_code, "请输入身份证号")) {
//            ToastUtils.showToast(getContext(), "身份证号不能为空");
//            return;
//        }
//        if (18 != EditTextUtils.getContent(et_code).length()) {
//            ToastUtils.showToast(getContext(), "身份证号长度不正确");
//            return;
//        }
        PersonalCarInfoFragment personalCarFt = new PersonalCarInfoFragment();
        personalCarFt.setSomeInfo(mImageCardFileUrl, MyTextUtils.getTvTextContent(tv_name), MyTextUtils.getTvTextContent(tv_code));
        FragmentTransaction ftt = getFragmentManager().beginTransaction();
        ftt.add(R.id.frame, personalCarFt, "personalCarFt");
        ftt.addToBackStack("personalCarFt");
        ftt.commit();
    }

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
            startActivityForResult(intent, REQUEST_FOR_FACE);
            //            String mFilePath = Environment.getExternalStorageDirectory().getPath();//获取SD卡路径
            //            long photoTime = System.currentTimeMillis();
            //            mFilePath = mFilePath + "/temp" + photoTime + ".jpg";//指定路径
            //权限已经被授予，在这里直接写要执行的相应方法即可
            //            //调用相机
            //            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //            Uri photoUri = Uri.fromFile(new File(mFilePath)); // 传递路径
            //            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径
        }
    }

    private int IMAGE = 10056;//调用相册requestcode

    private void toPhotoDriveCard() {
        //测试调用相册上传图片
        //        //第二个参数是需要申请的权限
        //        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        //                != PackageManager.PERMISSION_GRANTED) {
        //            //权限还没有授予，需要在这里写申请权限的代码
        //            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
        //                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        //        } else {
        //            //权限已经被授予，在这里直接写要执行的相应方法即可
        //            //调用相册
        //            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //            getActivity().startActivityForResult(intent, IMAGE);
        //        }


        //第二个参数是需要申请的权限
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ToastUtils.showToast(getContext(), "面部认证功能，需要拍摄照片，请开启手机相机权限!");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        } else {
            Intent intent = new Intent(getContext(), GetDriveCardPhotoActivity.class);
            startActivityForResult(intent, REQUEST_FOR_CARD);
        }
    }
}
