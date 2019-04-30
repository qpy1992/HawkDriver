package com.bt.smart.truck_broker.fragment.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.adapter.RecyPlaceAdapter;
import com.bt.smart.truck_broker.messageInfo.ChioceAdapterContentInfo;
import com.bt.smart.truck_broker.utils.EditTextUtils;
import com.bt.smart.truck_broker.utils.MyFragmentManagerUtil;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/9 22:07
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ChoiceCarModelFragment extends Fragment implements View.OnClickListener {
    private View                           mRootView;
    private TextView                       tv_title;
    private ImageView                      img_back;
    private RecyclerView                   recy_length;
    private RecyclerView                   recy_model;
    private EditText                       et_length;
    private TextView                       tv_sureLength;
    private TextView                       tv_clear;
    private TextView                       tv_sure;
    private List<ChioceAdapterContentInfo> mLengthData;
    private List<ChioceAdapterContentInfo> mModelData;
    private RecyPlaceAdapter               placeAdapter;
    private RecyPlaceAdapter               modelAdapter;
    private SetLinesAddressFragment        mTopFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.frame_choice_car, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        tv_title = mRootView.findViewById(R.id.tv_title);
        img_back = mRootView.findViewById(R.id.img_back);
        recy_length = mRootView.findViewById(R.id.recy_length);
        et_length = mRootView.findViewById(R.id.et_length);
        tv_sureLength = mRootView.findViewById(R.id.tv_sureLength);
        recy_model = mRootView.findViewById(R.id.recy_model);
        tv_clear = mRootView.findViewById(R.id.tv_clear);
        tv_sure = mRootView.findViewById(R.id.tv_sure);

    }

    private void initData() {
        img_back.setVisibility(View.VISIBLE);
        tv_title.setText("车长车型");
        //设置车长数据
        setLengthData();
        //设置车型数据
        setModelData();

        img_back.setOnClickListener(this);
        tv_sureLength.setOnClickListener(this);
        tv_clear.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                MyFragmentManagerUtil.closeTopFragment(this);
                break;
            case R.id.tv_sureLength://其他车长
                //添加其他车长到列表
                addOtherLength();
                break;
            case R.id.tv_clear://清空已选条件
                clearALLSelectData();
                break;
            case R.id.tv_sure://确定已选条件
                //传递选择条件、关闭页面
                makeSureTerm();
                break;
        }
    }

    private void setLengthData() {
        mLengthData = new ArrayList();
        ChioceAdapterContentInfo contentInfo = new ChioceAdapterContentInfo();
        contentInfo.setCont("不限车长");
        contentInfo.setChioce(false);
        mLengthData.add(contentInfo);
        ChioceAdapterContentInfo contentInfo1 = new ChioceAdapterContentInfo();
        contentInfo1.setCont("1.8米");
        contentInfo1.setChioce(false);
        mLengthData.add(contentInfo1);
        ChioceAdapterContentInfo contentInfo2 = new ChioceAdapterContentInfo();
        contentInfo2.setCont("2.7米");
        contentInfo2.setChioce(false);
        mLengthData.add(contentInfo2);
        ChioceAdapterContentInfo contentInfo3 = new ChioceAdapterContentInfo();
        contentInfo3.setCont("3.8米");
        contentInfo3.setChioce(false);
        mLengthData.add(contentInfo3);
        recy_length.setLayoutManager(new GridLayoutManager(getContext(), 4));
        placeAdapter = new RecyPlaceAdapter(R.layout.adpter_pop_city_place, getContext(), mLengthData);
        recy_length.setAdapter(placeAdapter);
        placeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mLengthData.get(position).setChioce(!mLengthData.get(position).isChioce());
                if (position == 0) {
                    for (ChioceAdapterContentInfo bean : mLengthData) {
                        bean.setChioce(false);
                    }
                    mLengthData.get(0).setChioce(true);
                } else {
                    mLengthData.get(0).setChioce(false);
                }
                placeAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setModelData() {
        mModelData = new ArrayList();
        ChioceAdapterContentInfo contentInfo = new ChioceAdapterContentInfo();
        contentInfo.setCont("不限车型");
        contentInfo.setChioce(false);
        mModelData.add(contentInfo);
        ChioceAdapterContentInfo contentInfo1 = new ChioceAdapterContentInfo();
        contentInfo1.setCont("平板");
        contentInfo1.setChioce(false);
        mModelData.add(contentInfo1);
        ChioceAdapterContentInfo contentInfo2 = new ChioceAdapterContentInfo();
        contentInfo2.setCont("高栏");
        contentInfo2.setChioce(false);
        mModelData.add(contentInfo2);
        ChioceAdapterContentInfo contentInfo3 = new ChioceAdapterContentInfo();
        contentInfo3.setCont("厢式");
        contentInfo3.setChioce(false);
        mModelData.add(contentInfo3);
        recy_model.setLayoutManager(new GridLayoutManager(getContext(), 4));
        modelAdapter = new RecyPlaceAdapter(R.layout.adpter_pop_city_place, getContext(), mModelData);
        recy_model.setAdapter(modelAdapter);
        modelAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mModelData.get(position).setChioce(!mModelData.get(position).isChioce());
                if (position == 0) {
                    for (ChioceAdapterContentInfo bean : mModelData) {
                        bean.setChioce(false);
                    }
                    mModelData.get(0).setChioce(true);
                } else {
                    mModelData.get(0).setChioce(false);
                }
                modelAdapter.notifyDataSetChanged();
            }
        });
    }

    private void clearALLSelectData() {
        for (ChioceAdapterContentInfo bean : mLengthData) {
            bean.setChioce(false);
        }
        placeAdapter.notifyDataSetChanged();
        for (ChioceAdapterContentInfo bean : mModelData) {
            bean.setChioce(false);
        }
        modelAdapter.notifyDataSetChanged();
    }

    private void addOtherLength() {
        if (!EditTextUtils.isEmpty(et_length, "请输入其他车长")) {
            String content = EditTextUtils.getContent(et_length);
            ChioceAdapterContentInfo contentInfo = new ChioceAdapterContentInfo();
            contentInfo.setCont(content + "米");
            contentInfo.setChioce(true);
            mLengthData.add(contentInfo);
            placeAdapter.notifyDataSetChanged();
        }
    }

    private boolean isChoiceLength;
    private boolean isChoiceModel;

    private void makeSureTerm() {
        for (ChioceAdapterContentInfo bean : mLengthData) {
            if (bean.isChioce()) {
                isChoiceLength = true;
            }
        }
        for (ChioceAdapterContentInfo bean : mModelData) {
            if (bean.isChioce()) {
                isChoiceModel = true;
            }
        }
        if (!isChoiceLength) {
            ToastUtils.showToast(getContext(), "请选择车长");
            return;
        }
        if (!isChoiceModel) {
            ToastUtils.showToast(getContext(), "请选择车型");
            return;
        }
        mTopFragment.setChioceTerm(mLengthData, mModelData);
        MyFragmentManagerUtil.closeTopFragment(this);
    }

    public void setTopFragment(SetLinesAddressFragment fragment) {
        mTopFragment = fragment;
    }
}
