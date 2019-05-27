package com.bt.smart.truck_broker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.messageInfo.MDetailInfo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MDetailAdapter extends BaseAdapter {
    Context mContext;
    List<MDetailInfo.DataBean> list = new ArrayList<>();

    public MDetailAdapter(Context context, List<MDetailInfo.DataBean> list){
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if(view==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_mdetail,null);
            holder = new Holder();
            holder.md_title = view.findViewById(R.id.md_title);
            holder.md_time = view.findViewById(R.id.md_time);
            holder.md_amount = view.findViewById(R.id.md_amount);
            view.setTag(holder);
        }else{
            holder = (Holder)view.getTag();
        }
        holder.md_title.setText(list.get(i).getFnote());
        holder.md_time.setText(list.get(i).getAddtime());
        holder.md_amount.setText(list.get(i).getFmoney()+"");
        return view;
    }

    class Holder{
        private TextView md_title,md_time,md_amount;
    }
}
