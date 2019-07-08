package com.bt.smart.truck_broker.fragment.mineOrders;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.adapter.MyPagerAdapter;
import com.bt.smart.truck_broker.fragment.user.OrderListFragment;
import com.bt.smart.truck_broker.utils.GlideImageLoader;
import com.bt.smart.truck_broker.viewmodel.MyFixedViewpager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/18 9:58
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class MyOrders_F extends Fragment {
    private View mRootView;
    //    private Banner banner;
    private List<String> imgListUrl;//
    private TextView tv_title;
    private TabLayout tablayout;
    private MyFixedViewpager view_pager;
    private List<OrderListFragment> fragmentsList;
    private MyPagerAdapter myPagerAdapter;
    private List<String> contsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.serv_apply_f, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
//        banner = mRootView.findViewById(R.id.banner);
        tv_title = mRootView.findViewById(R.id.tv_title);
        tablayout = mRootView.findViewById(R.id.tablayout);
        view_pager = mRootView.findViewById(R.id.view_pager);
    }

    private void initData() {
//        imgListUrl=new ArrayList<>();
//        imgListUrl.add("http://img.bimg.126.net/photo/ZZ5EGyuUCp9hBPk6_s4Ehg==/5727171351132208489.jpg");
//        imgListUrl.add("http://wx3.sinaimg.cn/large/006nLajtly1fkegnmnwuxj30dw0dw408.jpg");
//        imgListUrl.add("http://img4.duitang.com/uploads/blog/201407/11/20140711194751_Z5EWV.jpeg");
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
//        //设置图片加载器
//        banner.setImageLoader(new GlideImageLoader());
//        //设置图片集合
//        banner.setImages(imgListUrl);
//        //设置轮播时间
//        banner.setDelayTime(2000);
//        //banner设置方法全部调用完毕时最后调用
//        banner.start();
        tv_title.setText("订单列表");
        contsList = new ArrayList<>();
//        contsList.add("预接单");//5
//        contsList.add("未中标");//6
//        contsList.add("已接单");//0
//        contsList.add("运输中");//1
//        contsList.add("取消待确认");//2
//        contsList.add("已取消");//3
//        contsList.add("已结单");//4

//        contsList.add("已发布");//0
        contsList.add("已报价");//1
        contsList.add("已发协议");//2
        contsList.add("已签署");//3
        contsList.add("运输中");//4
        contsList.add("已签收");//5
        contsList.add("待确认");//6
        contsList.add("已取消");//7
        contsList.add("待支付");//8
        contsList.add("已结单");//9

        fragmentsList = new ArrayList<>();

//        OrderListFragment orderListFgt5 = new OrderListFragment();
//        orderListFgt5.setType(5);
//        fragmentsList.add(orderListFgt5);
//        OrderListFragment orderListFgt6 = new OrderListFragment();
//        orderListFgt6.setType(6);
//        fragmentsList.add(orderListFgt6);
//        OrderListFragment orderListFgt0 = new OrderListFragment();
//        orderListFgt0.setType(0);
//        fragmentsList.add(orderListFgt0);
//        OrderListFragment orderListFgt1 = new OrderListFragment();
//        orderListFgt1.setType(1);
//        fragmentsList.add(orderListFgt1);
//        OrderListFragment orderListFgt2 = new OrderListFragment();
//        orderListFgt2.setType(2);
//        fragmentsList.add(orderListFgt2);
//        OrderListFragment orderListFgt3 = new OrderListFragment();
//        orderListFgt3.setType(3);
//        fragmentsList.add(orderListFgt3);
//        OrderListFragment orderListFgt4 = new OrderListFragment();
//        orderListFgt4.setType(4);
//        fragmentsList.add(orderListFgt4);

//        OrderListFragment orderListFgt0 = new OrderListFragment();
//        orderListFgt0.setType(0);
//        fragmentsList.add(orderListFgt0);
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
        OrderListFragment orderListFgt5 = new OrderListFragment();
        orderListFgt5.setType(5);
        fragmentsList.add(orderListFgt5);
        OrderListFragment orderListFgt6 = new OrderListFragment();
        orderListFgt6.setType(6);
        fragmentsList.add(orderListFgt6);
        OrderListFragment orderListFgt7 = new OrderListFragment();
        orderListFgt6.setType(7);
        fragmentsList.add(orderListFgt7);
        OrderListFragment orderListFgt8 = new OrderListFragment();
        orderListFgt6.setType(8);
        fragmentsList.add(orderListFgt8);
        OrderListFragment orderListFgt9 = new OrderListFragment();
        orderListFgt6.setType(9);
        fragmentsList.add(orderListFgt9);
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

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
//        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
//        banner.stopAutoPlay();
    }
}
