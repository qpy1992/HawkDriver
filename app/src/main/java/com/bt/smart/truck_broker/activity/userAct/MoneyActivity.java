package com.bt.smart.truck_broker.activity.userAct;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.utils.ToastUtils;

import java.lang.reflect.Field;

public class MoneyActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mon_back,mon_detail,mon_number,mon_bind;
    private Button mon_recharge,mon_withdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        setViews();
        setListeners();
    }

    protected void setViews(){
        mon_back = findViewById(R.id.mon_back);
        mon_detail = findViewById(R.id.mon_detail);
        mon_number = findViewById(R.id.mon_number);
        mon_recharge = findViewById(R.id.mon_recharge);
        mon_withdraw = findViewById(R.id.mon_withdraw);
        mon_bind = findViewById(R.id.mon_bind);
        mon_number.setText("￥"+MyApplication.money);
    }

    protected void setListeners(){
        mon_back.setOnClickListener(this);
        mon_detail.setOnClickListener(this);
        mon_recharge.setOnClickListener(this);
        mon_withdraw.setOnClickListener(this);
        mon_bind.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mon_back:
                //返回
                this.finish();
                break;
            case R.id.mon_detail:
                //明细
                startActivity(new Intent(this,MDetailActivity.class));
                break;
            case R.id.mon_recharge:
                //充值
                recharge();
                break;
            case R.id.mon_withdraw:
                //提现
                withdraw();
                break;
            case R.id.mon_bind:
                //绑定银行卡
                bind();
                break;
        }
    }

    protected void recharge(){
        final EditText et = new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        final AlertDialog builder = new AlertDialog.Builder(this).setTitle("充值").setMessage("请输入充值数额").setView(et)
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", null).create();
        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialogInterface) {
                Button positionButton=builder.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton=builder.getButton(AlertDialog.BUTTON_NEGATIVE);
                positionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String amount = et.getText().toString();
                        if(amount.equals("")){
                            ToastUtils.showToast(MoneyActivity.this,"请输入金额");
                        }else{

                        }
                    }
                });
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
            }
        });
        builder.show();
    }

    protected void withdraw(){

    }

    protected void bind(){
        View v = getLayoutInflater().inflate(R.layout.item_bank,null);
    }
}
