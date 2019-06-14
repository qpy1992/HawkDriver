package com.bt.smart.truck_broker.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.bt.smart.truck_broker.adapter.RecyOrderAdapter;
import com.bt.smart.truck_broker.messageInfo.AllOrderListInfo;
import com.bt.smart.truck_broker.messageInfo.LinesOrderInfo;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.MyAlertDialogHelper;
import com.bt.smart.truck_broker.utils.ProgressDialogUtil;
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
 * @创建时间 2019/1/10 13:45
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class OrderListByLineFragment extends Fragment implements View.OnClickListener {
    private View                                mRootView;
    private ImageView                           img_back;
    private ImageView                           img_empty;
    private TextView                            tv_title;
    private TextView                            tv_lineName;
    private TextView                            tv_lenmol;
    private RecyclerView                        recy_order;
    private List<AllOrderListInfo.PageListBean> mData;
    private RecyOrderAdapter                    orderAdapter;
    private int REQUEST_FOR_TAKE_ORDER = 12087;//接单返回
    private int RESULT_TAKE_ORDER      = 12088;//接单成功响应值
    private String lineID;
    private String line_name;
    private String line_model;
    private int    mOrderSize;//总条目
    private int    mSumPageSize;//总共页数
    private int    mWhichPage;//获取哪页数据

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.frame_line_order_list, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        img_back = mRootView.findViewById(R.id.img_back);
        img_empty = mRootView.findViewById(R.id.img_empty);
        tv_title = mRootView.findViewById(R.id.tv_title);
        tv_lineName = mRootView.findViewById(R.id.tv_lineName);
        tv_lenmol = mRootView.findViewById(R.id.tv_lenmol);
        recy_order = mRootView.findViewById(R.id.recy_order);
    }

    private void initData() {
        img_back.setVisibility(View.VISIBLE);
        tv_title.setText("货源列表");
        lineID = getActivity().getIntent().getStringExtra("lineID");
        line_name = getActivity().getIntent().getStringExtra("lineName");
        line_model = getActivity().getIntent().getStringExtra("lineModel");

        tv_lineName.setText(line_name);
        tv_lenmol.setText(line_model);
        //初始化货源列表数据
        initOrderList();
        //获取线路货源
        getOrdersByLine(1, 10);

        img_back.setOnClickListener(this);
        img_empty.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.img_empty:
                //获取线路货源
                getOrdersByLine(1, 10);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_FOR_TAKE_ORDER == requestCode && RESULT_TAKE_ORDER == resultCode) {
            //刷新界面
            //获取线路货源
            getOrdersByLine(1, 10);
        }
    }

    private void getOrdersByLine(int no, int size) {
        img_empty.setVisibility(View.VISIBLE);
        RequestParamsFM headParam = new RequestParamsFM();
        headParam.put("X-AUTH-TOKEN", MyApplication.userToken);
        HttpOkhUtils.getInstance().doGetWithOnlyHeader(NetConfig.DRIVERJOURNEYCONTROLLER + "/getOrder/" + no + "/" + size + "/" + lineID, headParam, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(getContext(), "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(getContext(), "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                LinesOrderInfo linesOrderInfo = gson.fromJson(resbody, LinesOrderInfo.class);
                ToastUtils.showToast(getContext(), linesOrderInfo.getMessage());
                if (linesOrderInfo.isOk()) {
                    mData.clear();
                    mOrderSize = linesOrderInfo.getSize();
                    mSumPageSize = mOrderSize % 10 == 0 ? mOrderSize / 10 : mOrderSize / 10 + 1;
                    mWhichPage = 1;
                    if (linesOrderInfo.getData().size() > 0) {
                        img_empty.setVisibility(View.GONE);
                    }
                    for (LinesOrderInfo.DataBean bean : linesOrderInfo.getData()) {
                        AllOrderListInfo.PageListBean bean1 = new AllOrderListInfo.PageListBean();
//                        bean1.setFhAddress(bean.getFh_address());
//                        bean1.setShAddress(bean.getSh_address());
                        bean1.setId(bean.getId());
                        bean1.setGoodsName(bean.getGoods_name());
                        bean1.setZh_time(bean.getZh_time());
                        bean1.setFhName(bean.getFh_name());
                        bean1.setFhTele(bean.getFh_telephone());
                        mData.add(bean1);
                    }
                    orderAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initOrderList() {
        mData = new ArrayList();
        recy_order.setLayoutManager(new LinearLayoutManager(getContext()));
        orderAdapter = new RecyOrderAdapter(R.layout.adpter_sameday_order, getContext(), mData);
        recy_order.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!"3".equals(MyApplication.checkStatus)) {
                    ToastUtils.showToast(getContext(), "请先提交资料，认证通过才能联系货主哦！");
                    //弹出dialog提示
                    showCheckWarning();
                    return;
                }
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                intent.putExtra("orderID", mData.get(position).getId());
                startActivityForResult(intent, REQUEST_FOR_TAKE_ORDER);
            }
        });
        orderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                //上拉加载更多
                getMorePageInfo();
            }
        }, recy_order);
    }

    private void getMorePageInfo() {
        if (mSumPageSize > 1 && mWhichPage < mSumPageSize) {
            getMoreOrderList(mWhichPage + 1, 10);
        } else {
            orderAdapter.disableLoadMoreIfNotFullPage();
            ToastUtils.showToast(getContext(), "没有更多数据了");
        }
    }

    private void getMoreOrderList(int no, int size) {
        RequestParamsFM headParam = new RequestParamsFM();
        headParam.put("X-AUTH-TOKEN", MyApplication.userToken);
        HttpOkhUtils.getInstance().doGetWithOnlyHeader(NetConfig.DRIVERJOURNEYCONTROLLER + "/getOrder/" + no + "/" + size + "/" + lineID, headParam, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(getContext(), "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(getContext(), "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                LinesOrderInfo linesOrderInfo = gson.fromJson(resbody, LinesOrderInfo.class);
                ToastUtils.showToast(getContext(), linesOrderInfo.getMessage());
                if (linesOrderInfo.isOk()) {
                    mWhichPage++;
                    for (LinesOrderInfo.DataBean bean : linesOrderInfo.getData()) {
                        AllOrderListInfo.PageListBean bean1 = new AllOrderListInfo.PageListBean();
//                        bean1.setFhAddress(bean.getFh_address());
//                        bean1.setShAddress(bean.getSh_address());
                        bean1.setId(bean.getId());
                        bean1.setGoodsName(bean.getGoods_name());
                        bean1.setZh_time(bean.getZh_time());
                        bean1.setFhName(bean.getFh_name());
                        bean1.setFhTele(bean.getFh_telephone());
                        mData.add(bean1);
                    }
                    orderAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void showCheckWarning() {
        final MyAlertDialogHelper dialogHelper = new MyAlertDialogHelper();
        View view = View.inflate(getContext(), R.layout.dialog_check_warning, null);
        dialogHelper.setDIYView(getContext(), view);
        dialogHelper.show();
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_sure = view.findViewById(R.id.tv_sure);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHelper.disMiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHelper.disMiss();
            }
        });
    }
}
