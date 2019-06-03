package com.bt.smart.truck_broker.activity.userAct;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.adapter.RecyPlaceAdapter;
import com.bt.smart.truck_broker.messageInfo.BCardInfo;
import com.bt.smart.truck_broker.messageInfo.ChioceAdapterContentInfo;
import com.bt.smart.truck_broker.messageInfo.ShengDataInfo;
import com.bt.smart.truck_broker.messageInfo.UpPicInfo;
import com.bt.smart.truck_broker.utils.CommonUtil;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.MyAlertDialog;
import com.bt.smart.truck_broker.utils.PopupOpenHelper;
import com.bt.smart.truck_broker.utils.ProgressDialogUtil;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Request;

public class BankCardActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_cardno,et_fname,et_mobile;
    private TextView tv_qx,tv_khh;
    private Button btn_sub;
    private List<ChioceAdapterContentInfo> mDataPopEd;
    private List<ShengDataInfo.DataBean>   mSHEData;
    private int                            stCityLevel;
    private PopupOpenHelper                openHelper;
    private List<ShengDataInfo.DataBean>   mSHIData;
    private String province = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card);
        setViews();
        setListeners();
    }

    protected void setViews(){
        et_cardno = findViewById(R.id.et_cardno);
        et_fname = findViewById(R.id.et_fname);
        et_mobile = findViewById(R.id.et_mobile);
        tv_qx = findViewById(R.id.tv_qx);
        tv_khh = findViewById(R.id.tv_khh);
        btn_sub = findViewById(R.id.btn_sub);
//        et_cardno.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.del,0);
        et_cardno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        CommonUtil.bankCardInput(et_cardno);
        et_fname.setText(MyApplication.userName);
        //获取省的数据
        mSHEData = new ArrayList();
        mSHIData = new ArrayList();
        //选择窗数据
        mDataPopEd = new ArrayList<>();
        getProvince();
    }

    protected void setListeners(){
        tv_khh.setOnClickListener(this);
        tv_qx.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        new MyAlertDialog(this, MyAlertDialog.WARNING_TYPE_1).setTitleText("放弃添加?")
                .setConfirmText("确定")
                .setCancelText("取消")
                .showCancelButton(true)
                .setConfirmClickListener(new MyAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(MyAlertDialog sweetAlertDialog) {
                        finish();
                        sweetAlertDialog.dismiss();
                    }
                })
                .setCancelClickListener(null)
        .show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_khh:
                selectStartPlace();
                break;
            case R.id.tv_qx:
                this.onBackPressed();
                break;
            case R.id.btn_sub:
                String cardno = et_cardno.getText().toString();
                String fname = et_fname.getText().toString();
                String fmobile = et_mobile.getText().toString();
                String khh = tv_khh.getText().toString();
                if(cardno.equals("")){
                    ToastUtils.showToast(BankCardActivity.this,"请输入卡号");
                    return;
                }
                if(fname.equals("")){
                    ToastUtils.showToast(BankCardActivity.this,"请输入姓名");
                    return;
                }
                if(fmobile.equals("")){
                    ToastUtils.showToast(BankCardActivity.this,"请输入手机号");
                    return;
                }
                if(khh.equals("")){
                    ToastUtils.showToast(BankCardActivity.this,"请选择开户行地区");
                    return;
                }
                BCardInfo.DataBean data = new BCardInfo.DataBean();
                data.setFid(MyApplication.userID);
                String[] s = khh.split(" ");
                data.setFprovince(s[0]);
                data.setFcity(s[1]);
                data.setFname(fname);
                data.setFcardno(cardno);
                data.setFmobile(fmobile);
                Bind(data);
                break;
        }
    }

    protected void getProvince(){
            RequestParamsFM headParam = new RequestParamsFM();
            headParam.put("X-AUTH-TOKEN", MyApplication.userToken);
            RequestParamsFM params = new RequestParamsFM();
            params.put("pid", "1");
            HttpOkhUtils.getInstance().doGetWithHeadParams(NetConfig.REGIONSELECT, headParam, params, new HttpOkhUtils.HttpCallBack() {
                @Override
                public void onError(Request request, IOException e) {
                    ProgressDialogUtil.hideDialog();
                    ToastUtils.showToast(BankCardActivity.this, "网络连接错误");
                }

                @Override
                public void onSuccess(int code, String resbody) {
                    ProgressDialogUtil.hideDialog();
                    if (code != 200) {
                        ToastUtils.showToast(BankCardActivity.this, "网络错误" + code);
                        return;
                    }
                    Gson gson = new Gson();
                    ShengDataInfo shengDataInfo = gson.fromJson(resbody, ShengDataInfo.class);
                    ToastUtils.showToast(BankCardActivity.this, shengDataInfo.getMessage());
                    if (shengDataInfo.isOk()) {
                        mSHEData.clear();
                        mSHEData.addAll(shengDataInfo.getData());
                        if (null == mDataPopEd) {
                            mDataPopEd = new ArrayList<>();
                        } else {
                            mDataPopEd.clear();
                        }
                        for (ShengDataInfo.DataBean bean : mSHEData) {
                            ChioceAdapterContentInfo contentInfo = new ChioceAdapterContentInfo();
                            contentInfo.setCont(bean.getName());
                            contentInfo.setId(bean.getId());
                            mDataPopEd.add(contentInfo);
                        }
                    }
                }
            });
    }

    private void selectStartPlace() {
        openHelper = new PopupOpenHelper(BankCardActivity.this, tv_khh, R.layout.popup_choice_start);
        openHelper.openPopupWindowWithView(true, 0, (int) tv_khh.getY() + tv_khh.getHeight());
        openHelper.setOnPopupViewClick(new PopupOpenHelper.ViewClickListener() {
            @Override
            public void onViewListener(PopupWindow popupWindow, View inflateView) {
                RecyclerView recy_city = inflateView.findViewById(R.id.recy_city);
                final TextView tv_back = inflateView.findViewById(R.id.tv_back);
                final TextView tv_cancel = inflateView.findViewById(R.id.tv_cancel);
                if (stCityLevel != 0) {
                    tv_back.setVisibility(View.VISIBLE);
                }
                recy_city.setLayoutManager(new GridLayoutManager(BankCardActivity.this, 4));
                final RecyPlaceAdapter recyPlaceAdapter = new RecyPlaceAdapter(R.layout.adpter_pop_city_place, BankCardActivity.this, mDataPopEd);
                recy_city.setAdapter(recyPlaceAdapter);
                recyPlaceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        String id = mDataPopEd.get(position).getId();
                        if (stCityLevel == 0) {
                            //获取省份对应城市
                            getCityBySheng(id, tv_back, recyPlaceAdapter);
                            province = mDataPopEd.get(position).getCont();
                            stCityLevel++;
                        } else {
                                tv_khh.setText(province+" "+mDataPopEd.get(position).getCont());
                                openHelper.dismiss();
                        }
                    }
                });
                //设置返回上一级事件
                tv_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stCityLevel--;
                        if (stCityLevel == 0) {
                            tv_back.setVisibility(View.GONE);
                            mDataPopEd.clear();
                            //添加上一级省数据
                            for (ShengDataInfo.DataBean bean : mSHEData) {
                                ChioceAdapterContentInfo contentInfo = new ChioceAdapterContentInfo();
                                contentInfo.setCont(bean.getName());
                                contentInfo.setId(bean.getId());
                                mDataPopEd.add(contentInfo);
                            }
                            recyPlaceAdapter.notifyDataSetChanged();
                        } else if (stCityLevel == 1) {
                            mDataPopEd.clear();
                            //添加上一级城市数据
                            for (ShengDataInfo.DataBean bean : mSHIData) {
                                ChioceAdapterContentInfo contentInfo = new ChioceAdapterContentInfo();
                                contentInfo.setCont(bean.getName());
                                contentInfo.setId(bean.getId());
                                mDataPopEd.add(contentInfo);
                            }
                            recyPlaceAdapter.notifyDataSetChanged();
                        }
                    }
                });
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //                        stCityLevel = 0;
                        openHelper.dismiss();
                    }
                });
            }
        });
    }

    private void Bind(BCardInfo.DataBean data) {
        RequestParamsFM headParams = new RequestParamsFM();
        headParams.put("X-AUTH-TOKEN", MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        params.put("fid", data.getFid());
        params.put("fname", data.getFname());
        params.put("fprovince", data.getFprovince());
        params.put("fcity", data.getFcity());
        params.put("fcardno", data.getFcardno());
        params.put("fmobile",data.getFmobile());
        params.setUseJsonStreamer(true);
        HttpOkhUtils.getInstance().doPostWithHeader(NetConfig.B_C_CHECK, headParams, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(BankCardActivity.this, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(BankCardActivity.this, "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                UpPicInfo info = gson.fromJson(resbody, UpPicInfo.class);
                ToastUtils.showToast(BankCardActivity.this, info.getMessage());
                if (info.isOk()) {
                    ToastUtils.showToast(BankCardActivity.this,info.getMessage());
                    finish();
                }else {
                    ToastUtils.showToast(BankCardActivity.this,info.getMessage());
                }
            }
        });
    }

    private void getCityBySheng(String id, final TextView tv_back, final RecyPlaceAdapter recyPlaceAdapter) {
        RequestParamsFM headParam = new RequestParamsFM();
        headParam.put("X-AUTH-TOKEN", MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        params.put("pid", id);
        HttpOkhUtils.getInstance().doGetWithHeadParams(NetConfig.REGIONSELECT, headParam, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(BankCardActivity.this, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(BankCardActivity.this, "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                ShengDataInfo shengDataInfo = gson.fromJson(resbody, ShengDataInfo.class);
                ToastUtils.showToast(BankCardActivity.this, shengDataInfo.getMessage());
                if (shengDataInfo.isOk()) {
                    tv_back.setVisibility(View.VISIBLE);
                    mSHIData.clear();
                    mSHIData.addAll(shengDataInfo.getData());
                    if (null == mDataPopEd) {
                        mDataPopEd = new ArrayList<>();
                    } else {
                        mDataPopEd.clear();
                    }
                    for (ShengDataInfo.DataBean bean : mSHIData) {
                        ChioceAdapterContentInfo contentInfo = new ChioceAdapterContentInfo();
                        contentInfo.setCont(bean.getName());
                        contentInfo.setId(bean.getId());
                        mDataPopEd.add(contentInfo);
                    }
                    recyPlaceAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}
