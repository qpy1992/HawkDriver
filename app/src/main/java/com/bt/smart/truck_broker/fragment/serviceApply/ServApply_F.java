package com.bt.smart.truck_broker.fragment.serviceApply;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.utils.GlideImageLoader;
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

public class ServApply_F extends Fragment {
    private View   mRootView;
    private Banner banner;
    private List<String> imgListUrl;//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.serv_apply_f, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        banner = mRootView.findViewById(R.id.banner);
    }

    private void initData() {
        imgListUrl=new ArrayList<>();
        imgListUrl.add("http://img.bimg.126.net/photo/ZZ5EGyuUCp9hBPk6_s4Ehg==/5727171351132208489.jpg");
        imgListUrl.add("http://wx3.sinaimg.cn/large/006nLajtly1fkegnmnwuxj30dw0dw408.jpg");
        imgListUrl.add("http://img4.duitang.com/uploads/blog/201407/11/20140711194751_Z5EWV.jpeg");
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(imgListUrl);
        //设置轮播时间
        banner.setDelayTime(2000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
