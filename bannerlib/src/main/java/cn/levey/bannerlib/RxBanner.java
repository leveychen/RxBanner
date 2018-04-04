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

import cn.levey.bannerlib.base.RxBannerConfig;
import cn.levey.bannerlib.base.RxBannerLogger;
import cn.levey.bannerlib.data.RxBannerAdapter;
import cn.levey.bannerlib.data.RxBannerData;
import cn.levey.bannerlib.impl.RxBannerLoaderInterface;
import cn.levey.bannerlib.manager.AutoPlayRecyclerView;
import cn.levey.bannerlib.manager.ScaleLayoutManager;

/**
 * Created by Levey on 2018/4/2 15:11.
 * e-mail: m@levey.cn
 */

public class RxBanner extends FrameLayout {

    private Context mContext;
    private RxBannerAdapter mAdapter;
    private AutoPlayRecyclerView mRecyclerView;
    private ScaleLayoutManager mLayoutManager;
    private int timeInterval = RxBannerConfig.getInstance().getTimeInterval();
    private RxBannerConfig.DirectionType direction = RxBannerConfig.getInstance().getDirection();
    private boolean autoPlay = true;


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
        mLayoutManager = new ScaleLayoutManager.Builder(mContext,0).build();
        mLayoutManager.setMinScale(0.5f);
        mLayoutManager.setMinAlpha(0.3f);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        initAdapter();
    }

    public  ScaleLayoutManager getLayoutM(){
        return mLayoutManager;
    }

    private void initAdapter(){
        if(mAdapter == null) {
            mAdapter = new RxBannerAdapter(mContext);
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    public RxBanner setLoader(RxBannerLoaderInterface mLoader) {
        if(mAdapter != null){
            mAdapter.setLoader(mLoader);
        }
        return this;
    }

    public RxBanner setDatas(List<RxBannerData> list){
        if(mAdapter != null){
            ArrayList<String> urls = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                urls.add(list.get(i).getUrl());
            }
            mAdapter.setDatas(urls);
        }
        return this;
    }

    //Integer, String data list
    public RxBanner setDatas(ArrayList<?> urls){
        if(mAdapter != null){
            mAdapter.setDatas(urls);
        }
        if(RxBannerConfig.getInstance().isDebug()){
            for (int i = 0; i < urls.size(); i++) {
                RxBannerLogger.i("setDatas " + i + " = " + urls.get(i));
            }
        }
        return this;
    }
    public RxBanner setTitles(ArrayList<String> titles){

        return this;
    }

    public RxBanner setDirection(RxBannerConfig.DirectionType direction){
        if(mRecyclerView != null) {
            mRecyclerView.setDirection(direction);
        }
        this.direction = direction;
        RxBannerLogger.i("setDirection = " + timeInterval);
        return this;
    }

    public RxBanner setTimeInterval(int timeInterval){
        if(mRecyclerView != null) {
            mRecyclerView.setTimeInterval(timeInterval);
        }
        this.timeInterval = timeInterval;
        RxBannerLogger.i("setTimeInterval = " + timeInterval);
        return this;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public RxBannerConfig.DirectionType getDirection() {
        return direction;
    }

    public void start(){
        if(mRecyclerView != null && mAdapter != null && !mAdapter.getDatas().isEmpty() && isAutoPlay()){
            RxBannerLogger.i("start success");
            mRecyclerView.start();
        }else if(mRecyclerView != null && mAdapter != null && !mAdapter.getDatas().isEmpty() && !isAutoPlay()){
            RxBannerLogger.i("can not start, auto play = false");
        }else {
            throw new NullPointerException(RxBannerLogger.LOGGER_TAG + " should setDatas before start");
        }
    }

    public void pause(){
        if(mRecyclerView != null){
            mRecyclerView.pause();
        }
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    public RxBanner setAutoPlay(boolean autoPlay) {
        if(autoPlay && mRecyclerView != null && mAdapter != null && !mAdapter.getDatas().isEmpty() ){
            mRecyclerView.setAutoPlay(autoPlay);
        }
        if(!autoPlay && mRecyclerView!=null){
            mRecyclerView.setAutoPlay(autoPlay);
        }
        this.autoPlay = autoPlay;
        RxBannerLogger.i("setAutoPlay = " + autoPlay);
        return this;
    }

    public void setCurrentPosition(int position){
        if(mRecyclerView != null && mAdapter != null && !mAdapter.getDatas().isEmpty() && position >= 0){
            mRecyclerView.smoothScrollToPosition(position);
        }
    }

    public int getCurrentPosition(){
        if(mRecyclerView != null && mAdapter != null && !mAdapter.getDatas().isEmpty()){
            return mLayoutManager.getCurrentPosition();
        }
        return -1;
    }
}
