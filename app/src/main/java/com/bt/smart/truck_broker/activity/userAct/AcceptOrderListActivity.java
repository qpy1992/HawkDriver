package com.bt.smart.truck_broker.activity.userAct;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.bt.smart.truck_broker.BaseActivity;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.fragment.user.AcceptOrderFragment;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/18 9:55
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class AcceptOrderListActivity extends BaseActivity {
    private AcceptOrderFragment mAcceptOrderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_frame);
        setView();
    }

    private void setView() {
        mAcceptOrderFragment = new AcceptOrderFragment();
        FragmentTransaction ftt = getSupportFragmentManager().beginTransaction();
        ftt.add(R.id.frame, mAcceptOrderFragment, "mAcceptOrderFragment");
        ftt.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAcceptOrderFragment.onActivityResult(requestCode, resultCode, data);
    }
}
