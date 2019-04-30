package com.bt.smart.truck_broker.activity.homeAct;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.bt.smart.truck_broker.BaseActivity;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.fragment.home.OrderListByLineFragment;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/10 13:05
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class FindByLinesActivity extends BaseActivity {
    private OrderListByLineFragment orderListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_frame);
        setView();
    }

    private void setView() {
        orderListFragment = new OrderListByLineFragment();
        FragmentTransaction ftt = getSupportFragmentManager().beginTransaction();
        ftt.add(R.id.frame, orderListFragment, "orderListFragment");
        ftt.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        orderListFragment.onActivityResult(requestCode, resultCode, data);
    }
}
