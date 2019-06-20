package com.bt.smart.truck_broker.activity.userAct;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bt.smart.truck_broker.BaseActivity;
import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.utils.MyAlertDialog;
import com.bt.smart.truck_broker.utils.ToastUtils;

import java.lang.reflect.Field;

public class MoneyActivity extends BaseActivity implements View.OnClickListener {
    private TextView mon_back, mon_detail, mon_number, mon_bind;
    private Button mon_recharge, mon_withdraw;
    private int REQUEST_CHARGE_CODE = 10025;
    private int RESULT_CHARGE_CODE = 10026;
    private int RESULT_MONEY_CODE = 10016;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        setViews();
        setListeners();
    }

    protected void setViews() {
        mon_back = findViewById(R.id.mon_back);
        mon_detail = findViewById(R.id.mon_detail);
        mon_number = findViewById(R.id.mon_number);
        mon_recharge = findViewById(R.id.mon_recharge);
        mon_withdraw = findViewById(R.id.mon_withdraw);
        mon_bind = findViewById(R.id.mon_bind);
        mon_number.setText("￥" + MyApplication.money);
    }

    protected void setListeners() {
        mon_back.setOnClickListener(this);
        mon_detail.setOnClickListener(this);
        mon_recharge.setOnClickListener(this);
        mon_withdraw.setOnClickListener(this);
        mon_bind.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mon_back:
                //返回
                this.finish();
                break;
            case R.id.mon_detail:
                //明细
                startActivity(new Intent(this, MDetailActivity.class));
                break;
            case R.id.mon_recharge:
                //充值
                startActivityForResult(new Intent(this, RechargeActivity.class), REQUEST_CHARGE_CODE);
                break;
            case R.id.mon_withdraw:
                //提现
                startActivity(new Intent(this, WithdrawActivity.class));
                break;
            case R.id.mon_bind:
                //绑定银行卡
                startActivity(new Intent(this, BCardActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mon_number.setText("￥" + MyApplication.money);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CHARGE_CODE == requestCode && RESULT_CHARGE_CODE == resultCode) {
            //刷新数据
            setResult(RESULT_MONEY_CODE);
            finish();
        }
    }
}
