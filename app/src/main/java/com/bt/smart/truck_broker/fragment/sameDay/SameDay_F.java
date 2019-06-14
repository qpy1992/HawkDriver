package com.bt.smart.truck_broker.fragment.sameDay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.activity.samedayAct.OrderDetailActivity;
import com.bt.smart.truck_broker.adapter.RecyOrderAdapter;
import com.bt.smart.truck_broker.adapter.RecyPlaceAdapter;
import com.bt.smart.truck_broker.messageInfo.AllOrderListInfo;
import com.bt.smart.truck_broker.messageInfo.ChioceAdapterContentInfo;
import com.bt.smart.truck_broker.messageInfo.ShengDataInfo;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.LocationUtils;
import com.bt.smart.truck_broker.utils.MyAlertDialogHelper;
import com.bt.smart.truck_broker.utils.MyAnimationUtils;
import com.bt.smart.truck_broker.utils.PopupOpenHelper;
import com.bt.smart.truck_broker.utils.ProgressDialogUtil;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Request;


/**
 * @创建者 AndyYan
 * @创建时间 2018/11/6 8:32
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class SameDay_F extends Fragment implements View.OnClickListener {
    private View               mRootView;
    private TextView           tv_title;
    private SwipeRefreshLayout swiperefresh;
    private NestedScrollView   nestscroll;
    //    private ImageView          img_refresh, img_totop;
    private TextView           tv_start;//起点
    private TextView           tv_end;//终点
    private LinearLayout       line_start;
    private LinearLayout       line_end;
    private LinearLayout       line_screen;
    private ImageView          img_start, img_end;//起点终点的箭头
    private RecyclerView                        rec_order;
    private RecyOrderAdapter                    orderAdapter;
    private List<AllOrderListInfo.PageListBean> mData;
    private int REQUEST_FOR_TAKE_ORDER = 12087;//接单返回
    private int RESULT_TAKE_ORDER      = 12088;//接单成功响应值
    private int mOrderSize;//总条目
    private int mSumPageSize;//总共页数
    private int mWhichPage;//获取哪页数据
    private static String TAG = "SameDay_F";
    private double lat,lng;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.sameday_f, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        tv_title = mRootView.findViewById(R.id.tv_title);
        swiperefresh = mRootView.findViewById(R.id.swiperefresh);
        nestscroll = mRootView.findViewById(R.id.nestscroll);
        //        img_refresh = mRootView.findViewById(R.id.img_refresh);
        //        img_totop = mRootView.findViewById(R.id.img_totop);
        line_start = mRootView.findViewById(R.id.line_start);
        img_start = mRootView.findViewById(R.id.img_start);
        line_end = mRootView.findViewById(R.id.line_end);
        img_end = mRootView.findViewById(R.id.img_end);
        line_screen = mRootView.findViewById(R.id.line_screen);
        rec_order = mRootView.findViewById(R.id.rec_order);
        tv_start = mRootView.findViewById(R.id.tv_start);
        tv_end = mRootView.findViewById(R.id.tv_end);
        Bundle bundle = getArguments();
        lat = bundle.getDouble("lat");
        lng = bundle.getDouble("lng");
    }

    private void initData() {
        tv_title.setText("最新货源");
        //初始化货源列表
        initOrderList();
        //初始化起点线路
        initStartPlace();

        //获取订单列表信息
        getOrderList(1, 10, lat, lng);

        swiperefresh.setColorSchemeColors(getResources().getColor(R.color.blue_icon), getResources().getColor(R.color.yellow_40), getResources().getColor(R.color.red_160));
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //获取订单列表信息
                getOrderList(1, 10, lat, lng);
            }
        });
        //设置rec_order滑动事件
        //        setRecyclerviewMoveEvent();
        line_start.setOnClickListener(this);
        line_end.setOnClickListener(this);
        line_screen.setOnClickListener(this);
        //        img_refresh.setOnClickListener(this);
        //        img_totop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //            case R.id.img_refresh://按钮刷新列表
            //                mData.clear();
            //                orderAdapter.notifyDataSetChanged();
            //                //                MyAnimationUtils.rotateView(img_refresh, 2000, 0f, 90f, 180f, 360f);
            //                //获取订单列表信息
            //                getOrderList(1, 10, 0, null);
            //                break;
            //            case R.id.img_totop://滑动顶端按钮
            //                nestscroll.scrollTo(0, line_start.getHeight());
            //                break;
            case R.id.line_start:
                //选择运输起点
                choiceStartPlace(0);
                break;
            case R.id.line_end:
                //选择运输终点
                choiceStartPlace(1);
                break;
            case R.id.line_screen:
                //筛选运输条件
                screenAllTerm();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_FOR_TAKE_ORDER == requestCode && RESULT_TAKE_ORDER == resultCode) {
            //刷新界面
            getOrderList(1, 10, lat, lng);
        }
    }

    private void initStartPlace() {
        //获取省的数据
        mSHEData = new ArrayList();
        mSHIData = new ArrayList();
        mQUData = new ArrayList();
        //选择窗数据
        mDataPopEd = new ArrayList<>();
        getShengFen();
    }

    private void initOrderList() {
        mData = new ArrayList();
        rec_order.setLayoutManager(new LinearLayoutManager(getContext()));
        //解决数据加载不完的问题
        //            rec_order.setNestedScrollingEnabled(false);
        //            rec_order.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        rec_order.setFocusable(false);
        rec_order.setNestedScrollingEnabled(false);
        orderAdapter = new RecyOrderAdapter(R.layout.adpter_sameday_order, getContext(), mData);
        rec_order.setAdapter(orderAdapter);
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
                //上拉加载
                getMorePageInfo();
            }
        }, rec_order);

        //        rec_order.addOnScrollListener(new RecyclerView.OnScrollListener() {
        //            @Override
        //            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        //                super.onScrollStateChanged(recyclerView, newState);
        //                LinearLayoutManager layoutManager = (LinearLayoutManager) rec_order.getLayoutManager();
        //                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //                if (lastVisibleItemPosition == mData.size() - 1) {//&& mData.get(lastVisibleItemPosition) instanceof FootType
        //                    //上拉加载
        //                    getMorePageInfo();
        //                }
        //            }
        //        });
    }

    private void getMorePageInfo() {
        if (mSumPageSize > 1 && mWhichPage < mSumPageSize) {
            getMoreOrderList(mWhichPage + 1, 10, lat, lng);
        } else {
            orderAdapter.disableLoadMoreIfNotFullPage();
            ToastUtils.showToast(getContext(), "没有更多数据了");
        }
    }

    private void getMoreOrderList(int no, int size, double lat, double lng) {
        RequestParamsFM headParams = new RequestParamsFM();
        headParams.put("X-AUTH-TOKEN", MyApplication.userToken);
        String finalUrl;
        finalUrl = NetConfig.ALL_ORDER_LIST + "/" + no + "/" + size +"/" + lat +"/" + lng;
        HttpOkhUtils.getInstance().doGetWithOnlyHeader(finalUrl, headParams, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ToastUtils.showToast(getContext(), "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                if (code != 200) {
                    ToastUtils.showToast(getContext(), "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                AllOrderListInfo allOrderListInfo = gson.fromJson(resbody, AllOrderListInfo.class);
                Log.i(TAG,resbody);
                ToastUtils.showToast(getContext(), allOrderListInfo.getMessage());
                if (allOrderListInfo.getOk()) {
                    mWhichPage++;
                    mData.addAll(allOrderListInfo.getData());
                    orderAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getOrderList(int no, int size, double lat, double lng) {
        swiperefresh.setRefreshing(true);
        RequestParamsFM headParams = new RequestParamsFM();
        headParams.put("X-AUTH-TOKEN", MyApplication.userToken);
        String finalUrl;
        finalUrl = NetConfig.ALL_ORDER_LIST + "/" + no + "/" + size + "/" + lat + "/" + lng;
        HttpOkhUtils.getInstance().doGetWithOnlyHeader(finalUrl, headParams, new HttpOkhUtils.HttpCallBack() {
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
                Log.i(TAG,resbody);
                Gson gson = new Gson();
                AllOrderListInfo allOrderListInfo = gson.fromJson(resbody, AllOrderListInfo.class);
                ToastUtils.showToast(getContext(), allOrderListInfo.getMessage());
                if (allOrderListInfo.getOk()) {
                    mData.clear();
                    mOrderSize = allOrderListInfo.getSize();
                    mSumPageSize = mOrderSize % 10 == 0 ? mOrderSize / 10 : mOrderSize / 10 + 1;
                    mWhichPage = 1;
                    for (AllOrderListInfo.PageListBean bean : allOrderListInfo.getData()) {
                        bean.setZh_time(bean.getZh_time());
                    }
                    mData.addAll(allOrderListInfo.getData());
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

    private void getShengFen() {
        RequestParamsFM headParam = new RequestParamsFM();
        headParam.put("X-AUTH-TOKEN", MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        params.put("pid", "1");
        HttpOkhUtils.getInstance().doGetWithHeadParams(NetConfig.REGIONSELECT, headParam, params, new HttpOkhUtils.HttpCallBack() {
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
                ShengDataInfo shengDataInfo = gson.fromJson(resbody, ShengDataInfo.class);
                ToastUtils.showToast(getContext(), shengDataInfo.getMessage());
                if (shengDataInfo.isOk()) {
                    mSHEData.clear();
                    mSHEData.addAll(shengDataInfo.getData());
                    if (null == mDataPopEd) {
                        mDataPopEd = new ArrayList<>();
                    } else {
                        mDataPopEd.clear();
                    }
                    for (ShengDataInfo.DataBean bean : mSHEData) {
                        ChioceAdapterContentInfo contentInfo = new ChioceAdapterContentInfo();
                        contentInfo.setCont(bean.getName());
                        contentInfo.setId(bean.getId());
                        mDataPopEd.add(contentInfo);
                    }
                }
            }
        });
    }

    private List<ChioceAdapterContentInfo> mDataPopEd;
    private List<ShengDataInfo.DataBean>   mSHEData;
    private List<ShengDataInfo.DataBean>   mSHIData;
    private List<ShengDataInfo.DataBean>   mQUData;
    private int                            stCityLevel;
    private PopupOpenHelper                openHelper;

    private void choiceStartPlace(final int kind) {
        if (kind == 1) {
            MyAnimationUtils.rotateView(img_end, 500, 0f, 180f);
        } else {
            MyAnimationUtils.rotateView(img_start, 500, 0f, 180f);
        }
        openHelper = new PopupOpenHelper(getContext(), line_start, R.layout.popup_choice_start);
        openHelper.openPopupWindowWithView(true, 0, (int) line_start.getY() + line_start.getHeight());
        openHelper.setOnPopupViewClick(new PopupOpenHelper.ViewClickListener() {
            @Override
            public void onViewListener(PopupWindow popupWindow, View inflateView) {
                RecyclerView recy_city = inflateView.findViewById(R.id.recy_city);
                final TextView tv_back = inflateView.findViewById(R.id.tv_back);
                final TextView tv_cancel = inflateView.findViewById(R.id.tv_cancel);
                if (stCityLevel != 0) {
                    tv_back.setVisibility(View.VISIBLE);
                }
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //设置背景色
                        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                        lp.alpha = 1.0f;
                        getActivity().getWindow().setAttributes(lp);
                        if (kind == 1) {
                            MyAnimationUtils.rotateView(img_end, 500, 180f, 0f);
                        } else {
                            MyAnimationUtils.rotateView(img_start, 500, 180f, 0f);
                        }
                    }
                });
                recy_city.setLayoutManager(new GridLayoutManager(getContext(), 4));
                final RecyPlaceAdapter recyPlaceAdapter = new RecyPlaceAdapter(R.layout.adpter_pop_city_place, getContext(), mDataPopEd);
                recy_city.setAdapter(recyPlaceAdapter);
                recyPlaceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        String id = mDataPopEd.get(position).getId();
                        if (stCityLevel == 0) {
                            //获取省份对应城市
                            getCityBySheng(id, tv_back, recyPlaceAdapter);
                            stCityLevel++;
                        } else if (stCityLevel == 1) {
                            //获取城市对应的区
                            getTownByCity(id, tv_back, recyPlaceAdapter);
                            stCityLevel++;
                        } else {
                            if (kind == 0) {
                                //将选择的起点填写
                                tv_start.setText(mDataPopEd.get(position).getCont());
                                openHelper.dismiss();
                            } else {
                                //将选择的目的地填写
                                tv_end.setText(mDataPopEd.get(position).getCont());
                                openHelper.dismiss();
                            }
                        }
                    }
                });
                //设置返回上一级事件
                tv_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stCityLevel--;
                        if (stCityLevel == 0) {
                            tv_back.setVisibility(View.GONE);
                            mDataPopEd.clear();
                            //添加上一级省数据
                            for (ShengDataInfo.DataBean bean : mSHEData) {
                                ChioceAdapterContentInfo contentInfo = new ChioceAdapterContentInfo();
                                contentInfo.setCont(bean.getName());
                                contentInfo.setId(bean.getId());
                                mDataPopEd.add(contentInfo);
                            }
                            recyPlaceAdapter.notifyDataSetChanged();
                        } else if (stCityLevel == 1) {
                            mDataPopEd.clear();
                            //添加上一级城市数据
                            for (ShengDataInfo.DataBean bean : mSHIData) {
                                ChioceAdapterContentInfo contentInfo = new ChioceAdapterContentInfo();
                                contentInfo.setCont(bean.getName());
                                contentInfo.setId(bean.getId());
                                mDataPopEd.add(contentInfo);
                            }
                            recyPlaceAdapter.notifyDataSetChanged();
                        }
                    }
                });
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //                        stCityLevel = 0;
                        openHelper.dismiss();
                    }
                });
            }
        });
    }

    private void getCityBySheng(String id, final TextView tv_back, final RecyPlaceAdapter recyPlaceAdapter) {
        RequestParamsFM headParam = new RequestParamsFM();
        headParam.put("X-AUTH-TOKEN", MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        params.put("pid", id);
        HttpOkhUtils.getInstance().doGetWithHeadParams(NetConfig.REGIONSELECT, headParam, params, new HttpOkhUtils.HttpCallBack() {
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
                ShengDataInfo shengDataInfo = gson.fromJson(resbody, ShengDataInfo.class);
                ToastUtils.showToast(getContext(), shengDataInfo.getMessage());
                if (shengDataInfo.isOk()) {
                    tv_back.setVisibility(View.VISIBLE);
                    mSHIData.clear();
                    mSHIData.addAll(shengDataInfo.getData());
                    if (null == mDataPopEd) {
                        mDataPopEd = new ArrayList<>();
                    } else {
                        mDataPopEd.clear();
                    }
                    for (ShengDataInfo.DataBean bean : mSHIData) {
                        ChioceAdapterContentInfo contentInfo = new ChioceAdapterContentInfo();
                        contentInfo.setCont(bean.getName());
                        contentInfo.setId(bean.getId());
                        mDataPopEd.add(contentInfo);
                    }
                    recyPlaceAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getTownByCity(String id, final TextView tv_back, final RecyPlaceAdapter recyPlaceAdapter) {
        RequestParamsFM headParam = new RequestParamsFM();
        headParam.put("X-AUTH-TOKEN", MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        params.put("pid", id);
        HttpOkhUtils.getInstance().doGetWithHeadParams(NetConfig.REGIONSELECT, headParam, params, new HttpOkhUtils.HttpCallBack() {
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
                ShengDataInfo shengDataInfo = gson.fromJson(resbody, ShengDataInfo.class);
                ToastUtils.showToast(getContext(), shengDataInfo.getMessage());
                if (shengDataInfo.isOk()) {
                    tv_back.setVisibility(View.VISIBLE);
                    mQUData.clear();
                    mQUData.addAll(shengDataInfo.getData());
                    if (null == mDataPopEd) {
                        mDataPopEd = new ArrayList<>();
                    } else {
                        mDataPopEd.clear();
                    }
                    for (ShengDataInfo.DataBean bean : mQUData) {
                        ChioceAdapterContentInfo contentInfo = new ChioceAdapterContentInfo();
                        contentInfo.setCont(bean.getName());
                        contentInfo.setId(bean.getId());
                        mDataPopEd.add(contentInfo);
                    }
                    recyPlaceAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void screenAllTerm() {

    }

    private int scDownY;
    private int scMoveY;
    private int rltTop;
    private int rltBot;
    private int imgTop;

    private void setRecyclerviewMoveEvent() {
        rec_order.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        scDownY = (int) motionEvent.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (scDownY == 0) {
                            scDownY = (int) motionEvent.getRawY();
                        }
                        scMoveY = (int) motionEvent.getRawY();
                        //                        rltTop = rltTop + (scMoveY - scDownY);
                        imgTop = imgTop + (scDownY - scMoveY);
                        //                        rltBot = rltTop + liner_top.getHeight();
                        //标题的位置
                        //                        if (rltTop >= 0) {
                        //                            rltTop = 0;
                        //                            rltBot = liner_top.getHeight();
                        //                        }
                        //                        if (rltBot < 0) {
                        //                            rltBot = 0;
                        //                            rltTop = rltBot - liner_top.getHeight();
                        //                        }
                        //                        //刷新按钮的位置
                        //                        if (imgTop > view_b.getTop()) {
                        //                            imgTop = view_b.getTop();
                        //                        }
                        //                        if (imgTop < view_a.getTop()) {
                        //                            imgTop = view_a.getTop();
                        //                        }
                        //设置位置
                        //                        liner_top.layout(0, rltTop, liner_top.getWidth(), rltBot);
                        //                        rec_order.layout(0, rltTop + liner_top.getHeight(), liner_top.getWidth(), view_b.getTop());
                        //       later                 img_refresh.layout(img_refresh.getLeft(), imgTop, img_refresh.getLeft() + img_refresh.getWidth(), imgTop + img_refresh.getHeight());
                        //平和滑动
                        scDownY = (int) motionEvent.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        //抬起时要从新
                        scDownY = 0;
                        scMoveY = 0;
                        break;
                }
                return false;
            }
        });
    }
}
