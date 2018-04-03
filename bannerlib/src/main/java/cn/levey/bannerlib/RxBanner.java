package cn.levey.bannerlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import cn.levey.bannerlib.data.RxBannerAdapter;
import cn.levey.bannerlib.data.RxBannerData;
import cn.levey.bannerlib.impl.RxBannerLoaderInterface;
import cn.levey.bannerlib.manager.AutoPlayRecyclerView;
import cn.levey.bannerlib.manager.AutoPlaySnapHelper;
import cn.levey.bannerlib.manager.CircleLayoutManager;

/**
 * Created by Levey on 2018/4/2 15:11.
 * e-mail: m@levey.cn
 */

public class RxBanner extends FrameLayout {

    private Context mContext;
    private RxBannerAdapter mAdapter;
    private AutoPlaySnapHelper autoPlaySnapHelper;
    private AutoPlayRecyclerView mRecyclerView;


    public RxBanner(@NonNull Context context) {
        this(context, null);
    }

    public RxBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RxBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs){
        initView();
    }

    private void initView(){
        mRecyclerView = new AutoPlayRecyclerView(mContext);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mRecyclerView, layoutParams);
        CircleLayoutManager layoutManager = new CircleLayoutManager.Builder(mContext).build();
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        initAdapter();
    }

    private void initAdapter(){
        if(mAdapter == null) {
            mAdapter = new RxBannerAdapter(mContext);
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setLoader(RxBannerLoaderInterface mLoader) {
        if(mAdapter != null){
            mAdapter.setLoader(mLoader);
        }
    }

    public void setDatas(List<RxBannerData> list){
        if(mAdapter != null){
            ArrayList<String> urls = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                urls.add(list.get(i).getUrl());
            }
            mAdapter.setDatas(urls);
        }
    }

    //Integer, String data list
    public void setDatas(ArrayList<?> urls){
        if(mAdapter != null){
            mAdapter.setDatas(urls);
            mRecyclerView.start();
        }
    }
    public void setTitles(ArrayList<String> titles){
    }



}
