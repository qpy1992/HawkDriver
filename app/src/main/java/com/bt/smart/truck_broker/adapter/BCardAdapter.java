package com.bt.smart.truck_broker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.messageInfo.BCardInfo;

import java.util.List;

public class BCardAdapter extends BaseAdapter {
    Context mContext;
    List<BCardInfo.DataBean> list;

    public BCardAdapter(Context context, List<BCardInfo.DataBean> list){
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
            holder = new Holder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_bcard,null);
            holder.bc_khh1 = view.findViewById(R.id.bc_khh1);
            holder.bc_kh = view.findViewById(R.id.bc_kh);
            holder.bc_ckr = view.findViewById(R.id.bc_ckr);
            view.setTag(holder);
        }else{
            holder = (Holder)view.getTag();
        }
        holder.bc_khh1.setText(list.get(i).getFkhh());
        String kh = list.get(i).getFcardno();
        holder.bc_kh.setText(kh);
        holder.bc_ckr.setText(list.get(i).getFname());
        return view;
    }

    class Holder{
        TextView bc_khh1,bc_kh,bc_ckr;
    }
}
