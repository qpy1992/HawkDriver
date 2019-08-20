package com.bt.smart.truck_broker.activity.userAct;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.fragment.user.CommentFragment;

public class CommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_frame);
        setViews();
    }

    protected void setViews(){
        CommentFragment commentFragment = new CommentFragment();
        FragmentTransaction ftt = getSupportFragmentManager().beginTransaction();
        ftt.add(R.id.frame, commentFragment, "commentFragment");
        ftt.commit();
    }
}
