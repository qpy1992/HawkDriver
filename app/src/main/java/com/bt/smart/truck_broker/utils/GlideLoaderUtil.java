package com.bt.smart.truck_broker.utils;

import android.content.Context;
import android.widget.ImageView;
import com.bt.smart.truck_broker.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * @创建者 AndyYan
 * @创建时间 2018/5/3 16:09
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class GlideLoaderUtil {
    /**
     * (1)
     * 显示图片Imageview
     *
     * @param context  上下文
     * @param url      图片链接
     * @param imageview 组件
     */
    public static void showImageView(Context context, String url, ImageView imageview) {
        Glide.with(context)
                .load(url)// 加载图片
                .placeholder(R.drawable.msg_empty)//图片加载出来前，显示的图片
                .error(R.drawable.msg_empty)// 设置错误图片
                .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
//                .skipMemoryCache(false)
                .into(imageview);
    }

    public static void showImgWithIcon(Context context, String url, int beforeID, int errorID, ImageView imgeview) {
        if (null!=context){
            Glide.with(context)
                    .load(url)// 加载图片
                    .placeholder(beforeID)//图片加载出来前，显示的图片
                    .error(errorID)// 设置错误图片
                    .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imgeview);
        }
    }
}
