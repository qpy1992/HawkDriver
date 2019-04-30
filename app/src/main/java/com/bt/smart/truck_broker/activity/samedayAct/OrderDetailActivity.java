package com.bt.smart.truck_broker.activity.samedayAct;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.bt.smart.truck_broker.BaseActivity;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.fragment.sameDay.OrderDetailFragment;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/8 21:32
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class OrderDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_frame);
        setView();
        setData();
    }

    private void setView() {
        OrderDetailFragment orderDetailFt = new OrderDetailFragment();
        FragmentTransaction ftt = getSupportFragmentManager().beginTransaction();
        ftt.add(R.id.frame, orderDetailFt, "orderDetailFt");
        ftt.commit();
    }

    private void setData() {

    }
}
