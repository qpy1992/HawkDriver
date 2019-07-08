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

import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.activity.samedayAct.OrderDetailActivity;
import com.bt.smart.truck_broker.adapter.ReadyRecDriverOrderAdapter;
import com.bt.smart.truck_broker.messageInfo.ReadyRecOrderInfo;
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
 * @创建时间 2019/1/22 19:55
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class OrderListFragment extends Fragment {
    private View mRootView;
    private SwipeRefreshLayout swiperefresh;
    private RecyclerView recyview;
    private ReadyRecDriverOrderAdapter orderAdapter;
    private List<ReadyRecOrderInfo.DataBean> mData;
    private int mType;//fragment需要展示的订单种类//0接单、1运输、2待确认、3已取消、4签收、5预接单、6未中标
//    0已发布、1已报价、2已发协议、3已签署、4运输中、5已签收、6待确认、7已取消、8待支付、9已结单

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.frame_order_list, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        swiperefresh = mRootView.findViewById(R.id.swiperefresh);
        recyview = mRootView.findViewById(R.id.recyview);

    }

    private void initData() {
        //初始化recyclerview
        initRecyclerview();
        //刷新控件
        setSwipRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        getDrivierOrderList(1, 10, MyApplication.userID, "" + mType);
    }

    private void setSwipRefresh() {
        swiperefresh.setColorSchemeColors(getResources().getColor(R.color.blue_icon), getResources().getColor(R.color.yellow_40), getResources().getColor(R.color.red_160));
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //获取司机个人订单列表
                getDrivierOrderList(1, 10, MyApplication.userID, "" + mType);
            }
        });
    }

    private void initRecyclerview() {
        mData = new ArrayList();
        recyview.setLayoutManager(new LinearLayoutManager(getContext()));
        orderAdapter = new ReadyRecDriverOrderAdapter(R.layout.adpter_sameday_order, getContext(), mData);
        recyview.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转订单详情
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                intent.putExtra("orderID", mData.get(position).getId());
                intent.putExtra("orderType", mType);
                startActivity(intent);
            }
        });
    }

    private void getDrivierOrderList(int no, int size, String userID, String type) {
        swiperefresh.setRefreshing(true);
        RequestParamsFM heardParam = new RequestParamsFM();
        heardParam.put("X-AUTH-TOKEN", MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", userID);
        params.put("status", type);
        HttpOkhUtils.getInstance().doGetWithHeadParams(NetConfig.DRIVERORDERCONTROLLER_ORDER + "/" + no + "/" + size, heardParam, params, new HttpOkhUtils.HttpCallBack() {
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
                ReadyRecOrderInfo readyRecOrderInfo = gson.fromJson(resbody, ReadyRecOrderInfo.class);
                ToastUtils.showToast(getContext(), readyRecOrderInfo.getMessage());
                if (readyRecOrderInfo.isOk()) {
                    if (readyRecOrderInfo.getData().size() > 0) {
                        mData.clear();
                        mData.addAll(readyRecOrderInfo.getData());
                        orderAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public void setType(int type) {
        mType = type;
    }

    public void refreshData() {
        //获取司机个人订单列表
        if (isVisible())
            getDrivierOrderList(1, 10, MyApplication.userID, "" + mType);
    }
}
