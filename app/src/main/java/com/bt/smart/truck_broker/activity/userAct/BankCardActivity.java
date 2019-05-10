package com.bt.smart.truck_broker.activity.userAct;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.messageInfo.BCardInfo;
import com.bt.smart.truck_broker.messageInfo.UpPicInfo;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.MyAlertDialog;
import com.bt.smart.truck_broker.utils.ProgressDialogUtil;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Request;

public class BankCardActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_cardno,et_fname;
    private TextView tv_qx,tv_khh;
    private Button btn_sub;

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
        tv_qx = findViewById(R.id.tv_qx);
        tv_khh = findViewById(R.id.tv_khh);
        btn_sub = findViewById(R.id.btn_sub);
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
                khh();
                break;
            case R.id.tv_qx:
                this.onBackPressed();
                break;
            case R.id.btn_sub:
                String cardno = et_cardno.getText().toString();
                String fname = et_fname.getText().toString();
                String khh = tv_khh.getText().toString();
                if(cardno.equals("")){
                    ToastUtils.showToast(BankCardActivity.this,"请输入卡号");
                    return;
                }
                if(fname.equals("")){
                    ToastUtils.showToast(BankCardActivity.this,"请输入姓名");
                    return;
                }
                if(khh.equals("")){
                    ToastUtils.showToast(BankCardActivity.this,"请选择开户行");
                    return;
                }
                BCardInfo.DataBean data = new BCardInfo.DataBean();
                data.setFid(MyApplication.userID);
                data.setFkhh(khh);
                data.setFkhhno(khh);
                data.setFname(fname);
                data.setFcardno(cardno);
                Bind(data);
                break;
        }
    }

    protected void khh(){
        final View v = getLayoutInflater().inflate(R.layout.item_khh,null);
        final EditText et_ser = v.findViewById(R.id.et_ser);
        final ImageView iv_ser = v.findViewById(R.id.iv_ser);
        final ListView lv_khh = v.findViewById(R.id.lv_khh);
        final List<String> list = new ArrayList<>();
        list.add("中国银行南通支行");
        list.add("工商银行南通支行");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        lv_khh.setAdapter(adapter);
        iv_ser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ser = et_ser.getText().toString();
                if(ser.equals("")){
                    ToastUtils.showToast(BankCardActivity.this,"请输入开户行的关键字");
                    return;
                }
            }
        });
        final AlertDialog builder = new AlertDialog.Builder(this).create();
        builder.show();
        Window window = builder.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(v);
        lv_khh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tv_khh.setText(list.get(i));
                builder.dismiss();
            }
        });
    }

    private void Bind(BCardInfo.DataBean data) {
        RequestParamsFM headParams = new RequestParamsFM();
        headParams.put("X-AUTH-TOKEN", MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        params.put("fid", data.getFid());
        params.put("fname", data.getFname());
        params.put("fkhh", data.getFkhh());
        params.put("fkhhno", data.getFkhhno());
        params.put("fcardno", data.getFcardno());
        params.setUseJsonStreamer(true);
        HttpOkhUtils.getInstance().doPostWithHeader(NetConfig.BIND, headParams, params, new HttpOkhUtils.HttpCallBack() {
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
                }
            }
        });
    }
}
