package com.bt.smart.truck_broker.fragment.user;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.adapter.MyPagerAdapter;
import com.bt.smart.truck_broker.viewmodel.MyFixedViewpager;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/22 19:32
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class AllOrderFragment extends Fragment {
    private View                    mRootView;
    private TextView                tv_title;
    private TabLayout               tablayout;
    private MyFixedViewpager        view_pager;
    private List<OrderListFragment> fragmentsList;
    private MyPagerAdapter          myPagerAdapter;
    private List<String>            contsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.frame_all_order, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        tv_title = mRootView.findViewById(R.id.tv_title);
        tablayout = mRootView.findViewById(R.id.tablayout);
        view_pager = mRootView.findViewById(R.id.view_pager);

    }

    private void initData() {
        tv_title.setText("订单列表");
        contsList = new ArrayList<>();
        contsList.add("预接单");//5
        contsList.add("未中标");//6
        contsList.add("已接单");//0
        contsList.add("运输中");//1
        contsList.add("取消待确认");//2
        contsList.add("已取消");//3
        contsList.add("已结单");//4
        fragmentsList = new ArrayList<>();
        OrderListFragment orderListFgt5 = new OrderListFragment();
        orderListFgt5.setType(5);
        fragmentsList.add(orderListFgt5);
        OrderListFragment orderListFgt6 = new OrderListFragment();
        orderListFgt6.setType(6);
        fragmentsList.add(orderListFgt6);
        OrderListFragment orderListFgt0 = new OrderListFragment();
        orderListFgt0.setType(0);
        fragmentsList.add(orderListFgt0);
        OrderListFragment orderListFgt1 = new OrderListFragment();
        orderListFgt1.setType(1);
        fragmentsList.add(orderListFgt1);
        OrderListFragment orderListFgt2 = new OrderListFragment();
        orderListFgt2.setType(2);
        fragmentsList.add(orderListFgt2);
        OrderListFragment orderListFgt3 = new OrderListFragment();
        orderListFgt3.setType(3);
        fragmentsList.add(orderListFgt3);
        OrderListFragment orderListFgt4 = new OrderListFragment();
        orderListFgt4.setType(4);
        fragmentsList.add(orderListFgt4);
        // 创建ViewPager适配器
        myPagerAdapter = new MyPagerAdapter(getFragmentManager());//getChildFragmentManager()
        myPagerAdapter.setFragments((ArrayList<OrderListFragment>) fragmentsList);
        // 给ViewPager设置适配器
        view_pager.setAdapter(myPagerAdapter);
        //        view_pager.setCanScroll(true);
        tablayout.setupWithViewPager(view_pager);
        for (int i = 0; i < contsList.size(); i++) {
            tablayout.getTabAt(i).setText(contsList.get(i));
        }
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentsList.get(tab.getPosition()).refreshData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                fragmentsList.get(tab.getPosition()).refreshData();
            }
        });
        tablayout.getTabAt(0).select();
        view_pager.setCurrentItem(0);
    }
}
