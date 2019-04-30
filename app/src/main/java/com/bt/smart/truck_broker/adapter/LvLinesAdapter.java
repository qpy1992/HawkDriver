package com.bt.smart.truck_broker.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.fragment.home.Home_F;
import com.bt.smart.truck_broker.messageInfo.CommenInfo;
import com.bt.smart.truck_broker.messageInfo.SearchDriverLinesInfo;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.ProgressDialogUtil;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/9 8:59
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class LvLinesAdapter extends BaseAdapter {
    private Context                              mContext;
    private List<SearchDriverLinesInfo.DataBean> mList;
    private Home_F                               homeF;

    public LvLinesAdapter(Context context, List<SearchDriverLinesInfo.DataBean> list, Home_F home_f) {
        this.mContext = context;
        this.mList = list;
        this.homeF = home_f;
    }

    @Override
    public int getCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final MyViewHolder viewholder;
        if (null == view) {
            viewholder = new MyViewHolder();
            view = View.inflate(mContext, R.layout.adapter_personal_lines, null);
            viewholder.tv_place = view.findViewById(R.id.tv_place);
            viewholder.tv_explain = view.findViewById(R.id.tv_explain);
            viewholder.tv_del = view.findViewById(R.id.tv_del);
            view.setTag(viewholder);
        } else {
            viewholder = (MyViewHolder) view.getTag();
        }
        String oriLine = mList.get(i).getOrigin1();
        String desLine = mList.get(i).getDestination1();
        if (null != mList.get(i).getOrigin2() && !"".equals(mList.get(i).getOrigin2())) {
            oriLine = oriLine + "/" + mList.get(i).getOrigin2();
        } else if (null != mList.get(i).getOrigin3() && !"".equals(mList.get(i).getOrigin3())) {
            oriLine = oriLine + "/" + mList.get(i).getOrigin3();
        }
        if (null != mList.get(i).getDestination2() && !"".equals(mList.get(i).getDestination2())) {
            desLine = desLine + "/" + mList.get(i).getDestination2();
        } else if (null != mList.get(i).getDestination3() && !"".equals(mList.get(i).getDestination3())) {
            desLine = desLine + "/" + mList.get(i).getDestination3();
        }
        viewholder.tv_place.setText(oriLine + "  →  " + desLine);
        viewholder.tv_explain.setText(mList.get(i).getCar_long() + "  /  " + mList.get(i).getCar_type());
        if (mList.get(i).isCanDel()) {
            viewholder.tv_del.setVisibility(View.VISIBLE);
        } else {
            viewholder.tv_del.setVisibility(View.GONE);
        }
        viewholder.tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //从服务器删除线路
                deletLines(i);
            }
        });

        return view;
    }

    private void deletLines(final int i) {
        RequestParamsFM headParam = new RequestParamsFM();
        headParam.put("X-AUTH-TOKEN", MyApplication.userToken);
        HttpOkhUtils.getInstance().doDeleteOnlyWithHead(NetConfig.DRIVERJOURNEYCONTROLLER + "/" + mList.get(i).getId(), headParam, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(mContext, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(mContext, "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                CommenInfo commenInfo = gson.fromJson(resbody, CommenInfo.class);
                ToastUtils.showToast(mContext, commenInfo.getMessage());
                if (commenInfo.isOk()) {
                    mList.remove(i);
                    if (mList.size() == 0) {
                        homeF.setUIChange();
                    } else {
                        homeF.changeTitle();
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }

    private class MyViewHolder {
        TextView tv_del, tv_explain, tv_place;
    }
}
