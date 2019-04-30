package com.bt.smart.truck_broker.fragment.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.adapter.MDetailAdapter;
import com.bt.smart.truck_broker.messageInfo.MDetailInfo;

import java.util.ArrayList;
import java.util.List;

public class MDFragment extends Fragment {
    private View view;
    private ListView lv_de;
    private List<MDetailInfo> list;
    private MDetailAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_md, container, false);
        setViews();
        setListeners();
        return view;
    }

    protected void setViews(){
        lv_de = view.findViewById(R.id.lv_de);
        list = new ArrayList<>();
        MDetailInfo info = new MDetailInfo();
        info.setFaccount("5.00");
        info.setFamount("375.00");
        info.setForderno("sghjd128371647861312");
        info.setFtime("2019-04-30 10:00:37");
        info.setFtitle("扫码支付");
        info.setFtype("支出");
        list.add(info);
        adapter = new MDetailAdapter(getActivity(),list);
        lv_de.setAdapter(adapter);
    }

    protected void setListeners(){
        lv_de.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MDeFragment mDeFragment = new MDeFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.md_ll,mDeFragment);
                Bundle bundle = new Bundle();
                bundle.putString("faccount", list.get(i).getFaccount());
                bundle.putString("famount", list.get(i).getFamount());
                bundle.putString("forderno", list.get(i).getForderno());
                bundle.putString("ftime", list.get(i).getFtime());
                bundle.putString("ftitle", list.get(i).getFtitle());
                bundle.putString("ftype", list.get(i).getFtype());
                mDeFragment.setArguments(bundle);
                ft.commit();
            }
        });
    }
}
