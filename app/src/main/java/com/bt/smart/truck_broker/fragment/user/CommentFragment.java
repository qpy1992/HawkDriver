package com.bt.smart.truck_broker.fragment.user;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.smart.truck_broker.R;

import org.w3c.dom.Text;

public class CommentFragment extends Fragment {
    private View view;
    private ImageView iv_back;
    private TextView tv_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_comment,null);
        setViews();
        setListeners();
        return view;
    }

    protected void setViews(){
        iv_back = view.findViewById(R.id.img_back);
        iv_back.setVisibility(View.VISIBLE);
        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.rat));
    }

    protected void setListeners(){

    }
}
