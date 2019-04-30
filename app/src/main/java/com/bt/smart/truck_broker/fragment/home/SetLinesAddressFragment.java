package com.bt.smart.truck_broker.fragment.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.adapter.RecyPlaceAdapter;
import com.bt.smart.truck_broker.messageInfo.AddDriverLinesInfo;
import com.bt.smart.truck_broker.messageInfo.ChioceAdapterContentInfo;
import com.bt.smart.truck_broker.messageInfo.ShengDataInfo;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.PopupOpenHelper;
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
 * @创建时间 2019/1/9 19:52
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class SetLinesAddressFragment extends Fragment implements View.OnClickListener {
    private View                           mRootView;
    private TextView                       tv_title;
    private ImageView                      img_back;
    private RecyclerView                   recy_st;//起点
    private RecyclerView                   recy_ed;//目的地
    private LinearLayout                   linear_cf00;
    private LinearLayout                   linear_md00;
    private RelativeLayout                 rlt_car_model;
    private TextView                       tv_choice_model;
    private TextView                       tv_submit;
    private List<ChioceAdapterContentInfo> mDataSt;
    private List<ChioceAdapterContentInfo> mDataEd;
    private RecyPlaceAdapter               placeAdapter;
    private RecyPlaceAdapter               placeEdAdapter;
    private int Result_FOR_SELECT_LINES = 10067;//设置线路响应值

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.frame_set_lines, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        tv_title = mRootView.findViewById(R.id.tv_title);
        img_back = mRootView.findViewById(R.id.img_back);
        recy_st = mRootView.findViewById(R.id.recy_st);
        recy_ed = mRootView.findViewById(R.id.recy_ed);
        linear_cf00 = mRootView.findViewById(R.id.linear_cf00);
        linear_md00 = mRootView.findViewById(R.id.linear_md00);
        rlt_car_model = mRootView.findViewById(R.id.rlt_car_model);
        tv_choice_model = mRootView.findViewById(R.id.tv_choice_model);
        tv_submit = mRootView.findViewById(R.id.tv_submit);
    }

    private void initData() {
        img_back.setVisibility(View.VISIBLE);
        tv_title.setText("添加常用路线");

        //初始化起点线路
        initStartPlace();
        //初始化目的地
        initEndPlace();

        img_back.setOnClickListener(this);
        linear_cf00.setOnClickListener(this);
        linear_md00.setOnClickListener(this);
        rlt_car_model.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.linear_cf00://选择出发地
                selectStartPlace(0);
                break;
            case R.id.linear_md00://选择目的地
                selectStartPlace(1);
                break;
            case R.id.rlt_car_model:
                //跳转选择车型界面
                toChoiceCarModel();
                break;
            case R.id.tv_submit:
                //提交线路
                sendDriverLine();
                break;
        }
    }

    private void initEndPlace() {
        mDataEd = new ArrayList();
        recy_ed.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        placeEdAdapter = new RecyPlaceAdapter(R.layout.adpter_city_place, getContext(), mDataEd);
        recy_ed.setAdapter(placeEdAdapter);
        placeEdAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mDataEd.remove(position);
                placeEdAdapter.notifyDataSetChanged();
                linear_md00.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initStartPlace() {
        mDataSt = new ArrayList();
        recy_st.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        placeAdapter = new RecyPlaceAdapter(R.layout.adpter_city_place, getContext(), mDataSt);
        recy_st.setAdapter(placeAdapter);
        placeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mDataSt.remove(position);
                placeAdapter.notifyDataSetChanged();
                linear_cf00.setVisibility(View.VISIBLE);
            }
        });
        //获取省的数据
        mSHEData = new ArrayList();
        mSHIData = new ArrayList();
        mQUData = new ArrayList();
        //选择窗数据
        mDataPopEd = new ArrayList<>();
        getShengFen();
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

    private String origin1      = "";
    private String origin2      = "";
    private String origin3      = "";
    private String destination1 = "";
    private String destination2 = "";
    private String destination3 = "";

    private void sendDriverLine() {
        if (mDataSt.size() == 0) {
            ToastUtils.showToast(getContext(), "出发地不能为空");
            return;
        }
        if (mDataEd.size() == 0) {
            ToastUtils.showToast(getContext(), "目的地不能为空");
            return;
        }
        for (int i = 0; i < mDataSt.size(); i++) {
            if (i == 0) {
                origin1 = mDataSt.get(i).getId();
            } else if (i == 1) {
                origin2 = mDataSt.get(i).getId();
            } else if (i == 2) {
                origin3 = mDataSt.get(i).getId();
            }
        }
        for (int i = 0; i < mDataEd.size(); i++) {
            if (i == 0) {
                destination1 = mDataEd.get(i).getId();
            } else if (i == 1) {
                destination2 = mDataEd.get(i).getId();
            } else if (i == 2) {
                destination3 = mDataEd.get(i).getId();
            }
        }
        RequestParamsFM headParams = new RequestParamsFM();
        headParams.put("X-AUTH-TOKEN", MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        params.put("carType", carModel);
        params.put("carLong", carLeng);
        params.put("driverId", MyApplication.userID);
        params.put("id", MyApplication.userID);
        params.put("origin1", origin1);
        params.put("origin2", origin2);
        params.put("origin3", origin3);
        params.put("destination1", destination1);
        params.put("destination2", destination2);
        params.put("destination3", destination3);
        params.setUseJsonStreamer(true);
        HttpOkhUtils.getInstance().doPostWithHeader(NetConfig.DRIVERJOURNEYCONTROLLER, headParams, params, new HttpOkhUtils.HttpCallBack() {
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
                AddDriverLinesInfo addLinesInfo = gson.fromJson(resbody, AddDriverLinesInfo.class);
                ToastUtils.showToast(getContext(), addLinesInfo.getMessage());
                if (addLinesInfo.isOk()) {
                    getActivity().setResult(Result_FOR_SELECT_LINES);
                    getActivity().finish();
                }
            }
        });
    }

    private void toChoiceCarModel() {
        ChoiceCarModelFragment carModelFragment = new ChoiceCarModelFragment();
        carModelFragment.setTopFragment(this);
        FragmentTransaction ftt = getFragmentManager().beginTransaction();
        ftt.add(R.id.frame, carModelFragment, "carModelFragment");
        ftt.addToBackStack("carModelFragment");
        ftt.commit();
    }

    private List<ChioceAdapterContentInfo> mDataPopEd;
    private List<ShengDataInfo.DataBean>   mSHEData;
    private List<ShengDataInfo.DataBean>   mSHIData;
    private List<ShengDataInfo.DataBean>   mQUData;
    private int                            stCityLevel;
    private PopupOpenHelper                openHelper;

    private void selectStartPlace(final int kind) {
        openHelper = new PopupOpenHelper(getContext(), linear_cf00, R.layout.popup_choice_start);
        openHelper.openPopupWindowWithView(true, 0, (int) linear_cf00.getY() + linear_cf00.getHeight());
        openHelper.setOnPopupViewClick(new PopupOpenHelper.ViewClickListener() {
            @Override
            public void onViewListener(PopupWindow popupWindow, View inflateView) {
                RecyclerView recy_city = inflateView.findViewById(R.id.recy_city);
                final TextView tv_back = inflateView.findViewById(R.id.tv_back);
                final TextView tv_cancel = inflateView.findViewById(R.id.tv_cancel);
                if (stCityLevel != 0) {
                    tv_back.setVisibility(View.VISIBLE);
                }
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
                                ChioceAdapterContentInfo contentInfo = new ChioceAdapterContentInfo();
                                contentInfo.setCont(mDataPopEd.get(position).getCont());
                                contentInfo.setId(mDataPopEd.get(position).getId());
                                mDataSt.add(contentInfo);
                                placeAdapter.notifyDataSetChanged();
                                if (mDataSt.size() >= 3) {
                                    linear_cf00.setVisibility(View.GONE);
                                }
                                openHelper.dismiss();
                                //                                stCityLevel = 0;
                            } else {
                                //将选择的目的地填写
                                ChioceAdapterContentInfo contentInfo = new ChioceAdapterContentInfo();
                                contentInfo.setCont(mDataPopEd.get(position).getCont());
                                contentInfo.setId(mDataPopEd.get(position).getId());
                                mDataEd.add(contentInfo);
                                placeEdAdapter.notifyDataSetChanged();
                                if (mDataEd.size() >= 3) {
                                    linear_md00.setVisibility(View.GONE);
                                }
                                openHelper.dismiss();
                                //                                stCityLevel = 0;
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

    private String carLeng  = "";
    private String carModel = "";

    public void setChioceTerm(List<ChioceAdapterContentInfo> lengthData, List<ChioceAdapterContentInfo> modelData) {
        if (null != lengthData) {
            for (int i = 0; i < lengthData.size(); i++) {
                if (lengthData.get(i).isChioce()) {
                    if ("".equals(carLeng)) {
                        carLeng = carLeng + lengthData.get(i).getCont();
                    } else {
                        carLeng = carLeng + "/" + lengthData.get(i).getCont();
                    }

                }
            }
        }
        if (null != modelData) {
            for (int i = 0; i < modelData.size(); i++) {
                if (modelData.get(i).isChioce()) {
                    if ("".equals(carModel)) {
                        carModel = carModel + modelData.get(i).getCont();
                    } else {
                        carModel = carModel + "/" + modelData.get(i).getCont();
                    }

                }
            }
        }
        tv_choice_model.setText(carLeng + "  ,  " + carModel);
    }
}
