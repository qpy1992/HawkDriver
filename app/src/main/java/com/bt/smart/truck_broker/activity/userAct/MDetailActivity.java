package com.bt.smart.truck_broker.activity.userAct;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.fragment.user.MDFragment;
import com.bt.smart.truck_broker.fragment.user.MDeFragment;

public class MDetailActivity extends FragmentActivity {
    private TextView de_back;
    private MDFragment mdFragment;
    private MDeFragment mDeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdetail);
        setViews();
        setListeners();
    }

    protected void setViews(){
        de_back = findViewById(R.id.de_close);
        mdFragment = new MDFragment();
        mDeFragment = new MDeFragment();
        addFragment(mdFragment);
        showFragment(mdFragment);
    }

    protected void setListeners(){
        de_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.md_ll);
        System.out.println(current);
        if(current.getClass()==MDFragment.class){
            super.onBackPressed();
        }else {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.md_ll, new MDFragment());
            ft.commit();
        }
    }

    /**
     * 添加Fragment
     **/
    public void addFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.add(R.id.md_ll, fragment);
        ft.commit();
    }

    /**
     * 显示Fragment
     **/
    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    /**
     * 删除Fragment
     **/
    public void removeFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }
}
