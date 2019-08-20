package com.bt.smart.truck_broker.fragment.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.adapter.TagAdapter;
import com.bt.smart.truck_broker.messageInfo.ChioceAdapterContentInfo;
import com.bt.smart.truck_broker.messageInfo.CommenInfo;
import com.bt.smart.truck_broker.messageInfo.TsTypeInfo;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.MyFragmentManagerUtil;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Request;

public class CommentFragment extends Fragment implements View.OnClickListener{
    private View view;
    private static String TAG = "CommentFragment";
    private ImageView iv_back;
    private TextView tv_title;
    private RatingBar ratingBar;
    private RecyclerView recy_biaoqian;
    private EditText et_comments;
    private Button btn_submit_comments;
    private float rating = 5.0f;
    private TagAdapter adapter;
    private List<ChioceAdapterContentInfo> mbqData;
    private String labels="",orderno="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_comment,null);
        setViews();
        setListeners();
        return view;
    }

    protected void setViews(){
        iv_back = view.findViewById(R.id.img_back);
        iv_back.setVisibility(View.VISIBLE);
        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.rat));
        ratingBar = view.findViewById(R.id.ratingBar);
        recy_biaoqian = view.findViewById(R.id.recy_biaoqian);
        et_comments = view.findViewById(R.id.et_comments);
        btn_submit_comments = view.findViewById(R.id.btn_submit_comments);
        setTagData();
    }

    protected void setListeners(){
        iv_back.setOnClickListener(this);
        btn_submit_comments.setOnClickListener(this);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Log.i(TAG,String.valueOf(v));
                rating = v;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                MyFragmentManagerUtil.closeFragmentOnAct(this);
                break;
            case R.id.btn_submit_comments:
                submitComments();
                break;
        }
    }

    protected void setTagData(){
        mbqData = new ArrayList<>();
        RequestParamsFM headParam = new RequestParamsFM();
        headParam.put(NetConfig.HEAD, MyApplication.userToken);
        HttpOkhUtils.getInstance().doGetWithOnlyHeader(NetConfig.TSTYPE + "/2c90b4bf6cac7abf016cacc4d5c10008", headParam, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                Log.i(TAG,"网络错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                if(code!=200){
                    Log.i(TAG,"访问异常"+code);
                }
                Gson gson = new Gson();
                TsTypeInfo tsTypeInfo = gson.fromJson(resbody,TsTypeInfo.class);
                for(TsTypeInfo.DataBean bean : tsTypeInfo.getData()){
                    ChioceAdapterContentInfo contentInfo = new ChioceAdapterContentInfo();
                    contentInfo.setId(bean.getTypecode());
                    contentInfo.setCont(bean.getTypename());
                    contentInfo.setChioce(false);
                    mbqData.add(contentInfo);
                }
                recy_biaoqian.setLayoutManager(new GridLayoutManager(getContext(), 4));
                adapter = new TagAdapter(R.layout.adapter_comment_tag,getContext(),mbqData);
                recy_biaoqian.setAdapter(adapter);
                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        mbqData.get(position).setChioce(!mbqData.get(position).isChioce());
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    protected void submitComments(){
        RequestParamsFM headparam = new RequestParamsFM();
        headparam.put(NetConfig.HEAD,MyApplication.userToken);
        RequestParamsFM params = new RequestParamsFM();
        params.put("fcontent",et_comments.getText().toString());
        params.put("rating",rating);
        params.put("fid",MyApplication.userID);
        params.put("ftype",1);
        for(ChioceAdapterContentInfo info:mbqData){
            if(info.isChioce()){
                labels = labels+info.getId()+",";
            }
        }
        if(labels.length()>0){
            params.put("label",labels.substring(0,labels.length()-1));
        }else{
            params.put("label","");
        }
        params.put("forderno",orderno);
        params.setUseJsonStreamer(true);
        HttpOkhUtils.getInstance().doPostWithHeader(NetConfig.COMMENTS, headparam, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                Log.i(TAG,"网络错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                if(code!=200){
                    Log.i(TAG,"访问异常"+code);
                }
                CommenInfo commenInfo = new Gson().fromJson(resbody,CommenInfo.class);
                if(commenInfo.isOk()){
                    ToastUtils.showToast(getContext(),"评论成功");
                    getActivity().finish();
                }
            }
        });
    }
}
