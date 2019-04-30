package com.bt.smart.truck_broker.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.activity.samedayAct.OrderDetailActivity;
import com.bt.smart.truck_broker.adapter.RecyDriverOrderAdapter;
import com.bt.smart.truck_broker.messageInfo.DrivierOrderInfo;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/18 9:58
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class AcceptOrderFragment extends Fragment implements View.OnClickListener {
    private View                            mRootView;
    private ImageView                       img_back;
    private TextView                        tv_title;
    private SwipeRefreshLayout              swiperefresh;
    private RecyclerView                    recy_order;
    private List<DrivierOrderInfo.DataBean> mData;
    private RecyDriverOrderAdapter          orderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.frame_acce_order_info, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        img_back = mRootView.findViewById(R.id.img_back);
        tv_title = mRootView.findViewById(R.id.tv_title);
        swiperefresh = mRootView.findViewById(R.id.swiperefresh);
        recy_order = mRootView.findViewById(R.id.recy_order);

    }

    private void initData() {
        img_back.setVisibility(View.VISIBLE);
        tv_title.setText("订单列表");

        //初始化订单列表
        initOrderList();
        //设置刷新控件
        setSwipRefresh();
        img_back.setOnClickListener(this);
        //获取司机个人订单列表
        getDrivierOrderList(0, 10, MyApplication.userID, "1");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
        }
    }

    private void setSwipRefresh() {
        swiperefresh.setColorSchemeColors(getResources().getColor(R.color.blue_icon), getResources().getColor(R.color.yellow_40), getResources().getColor(R.color.red_160));
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //获取司机个人订单列表
                getDrivierOrderList(0, 10, MyApplication.userID, "1");
            }
        });
    }

    private void getDrivierOrderList(int no, int size, String userID, String type) {
        swiperefresh.setRefreshing(true);
        mData.clear();
        RequestParamsFM headParam = new RequestParamsFM();
        headParam.put("X-AUTH-TOKEN", MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", userID);
        params.put("status", type);
        HttpOkhUtils.getInstance().doGetWithHeadParams(NetConfig.DRIVERORDERCONTROLLER_ORDER + "/" + no + "/" + size, headParam, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                swiperefresh.setRefreshing(false);
                ToastUtils.showToast(getContext(), "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                swiperefresh.setRefreshing(false);
                if (code != 200) {
                    ToastUtils.showToast(getContext(), "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                DrivierOrderInfo drivierOrderInfo = gson.fromJson(resbody, DrivierOrderInfo.class);
                ToastUtils.showToast(getContext(), drivierOrderInfo.getMessage());
                if (drivierOrderInfo.isOk()) {
                    if (null != drivierOrderInfo.getData() && drivierOrderInfo.getData().size() > 0) {
                        mData.addAll(drivierOrderInfo.getData());
                        orderAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void initOrderList() {
        mData = new ArrayList();
        recy_order.setLayoutManager(new LinearLayoutManager(getContext()));
        //解决数据加载完成后, 没有停留在顶部的问题
        orderAdapter = new RecyDriverOrderAdapter(R.layout.adpter_sameday_order, getContext(), mData);
        recy_order.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                intent.putExtra("orderID", mData.get(position).getId());
                intent.putExtra("touchKind", "accepted");
                startActivity(intent);
            }
        });
    }
}
