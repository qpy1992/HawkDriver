package com.bt.smart.truck_broker.activity.userAct;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.adapter.BCardAdapter;
import com.bt.smart.truck_broker.adapter.BankCardAdapter;
import com.bt.smart.truck_broker.messageInfo.BCardInfo;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.MyAlertDialog;
import com.bt.smart.truck_broker.utils.ProgressDialogUtil;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

public class BCardActivity extends AppCompatActivity implements View.OnClickListener {
    TextView bc_back,bc_add;
    ListView lv_bcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcard);
        setViews();
        setListeners();
    }

    protected void setViews(){
        bc_back = findViewById(R.id.bc_back);
        bc_add = findViewById(R.id.bc_add);
        lv_bcard = findViewById(R.id.lv_bcard);
        BankCard(MyApplication.userID);
    }

    protected void setListeners(){
        bc_back.setOnClickListener(this);
        bc_add.setOnClickListener(this);
        lv_bcard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bc_back:
                finish();
                break;
            case R.id.bc_add:
                startActivity(new Intent(this,BankCardActivity.class));
                break;
        }
    }

    private void BankCard(String id) {
        RequestParamsFM headParams = new RequestParamsFM();
        headParams.put("X-AUTH-TOKEN", MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        params.put("fid", id);
        HttpOkhUtils.getInstance().doGetWithHeadParams(NetConfig.BANKCARD, headParams, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(BCardActivity.this, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(BCardActivity.this, "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                BCardInfo info = gson.fromJson(resbody, BCardInfo.class);
//                ToastUtils.showToast(BCardActivity.this, info.getMessage());
                if (!info.isOk()) {
                    new MyAlertDialog(BCardActivity.this, MyAlertDialog.WARNING_TYPE_1)
                            .setContentText("您还未提交任何银行卡，现在绑定？")
                            .setConfirmText("好的")
                            .setCancelText("算了")
                            .showCancelButton(true)
                            .setConfirmClickListener(new MyAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(MyAlertDialog sweetAlertDialog) {
                                    startActivity(new Intent(BCardActivity.this, BankCardActivity.class));
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .setCancelClickListener(null)
                            .show();
                } else {
                    //显示银行卡列表
                    BCardAdapter adapter = new BCardAdapter(BCardActivity.this, info.getData());
                    lv_bcard.setAdapter(adapter);
                }
            }
        });
    }
    
}
