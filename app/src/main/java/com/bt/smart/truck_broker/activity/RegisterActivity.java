package com.bt.smart.truck_broker.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.bt.smart.truck_broker.BaseActivity;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.fragment.RegisterPhoneFragment;

/**
 * @创建者 AndyYan
 * @创建时间 2018/10/30 16:51
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_frame);
        setView();
        setData();
    }

    private void setView() {
        String kind = getIntent().getStringExtra("kind");
        RegisterPhoneFragment regisPhoneFt = new RegisterPhoneFragment();
        regisPhoneFt.setKind(kind);
        FragmentTransaction ftt = getSupportFragmentManager().beginTransaction();
        ftt.add(R.id.frame, regisPhoneFt, "regisPhoneFt");
        ftt.commit();
    }

    private void setData() {

    }
}
