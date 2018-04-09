package cn.levey.bannerlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.levey.bannerlib.base.BannerUtil;
import cn.levey.bannerlib.base.RxBannerConfig;
import cn.levey.bannerlib.base.RxBannerLogger;
import cn.levey.bannerlib.data.RxBannerAdapter;
import cn.levey.bannerlib.data.RxBannerData;
import cn.levey.bannerlib.impl.RxBannerClickListener;
import cn.levey.bannerlib.impl.RxBannerLoaderInterface;
import cn.levey.bannerlib.manager.AutoPlayRecyclerView;
import cn.levey.bannerlib.manager.ScaleLayoutManager;

/**
 * Created by Levey on 2018/4/2 15:11.
 * e-mail: m@levey.cn
 */

public class RxBanner extends FrameLayout {

    private Context mContext;
    private int parentWidth,parentHeight;
    private RxBannerAdapter mAdapter;
    private AutoPlayRecyclerView mRecyclerView;
    private ScaleLayoutManager mLayoutManager;
    private int timeInterval = RxBannerConfig.getInstance().getTimeInterval();
    private RxBannerConfig.OrderType orderType;
    private boolean autoPlay = true;
    private int itemPercent;
    private int itemSpace;
    private int orientation;


    public RxBanner(@NonNull Context context) {
        this(context, null);
    }

    public RxBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RxBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RxBanner);
        parentWidth = typedArray.getLayoutDimension(R.styleable.RxBanner_android_layout_width, -1);
        parentHeight = typedArray.getLayoutDimension(R.styleable.RxBanner_android_layout_height, -1);
        RxBannerLogger.i(" parentWidth = " + parentWidth);
        RxBannerLogger.i(" parentHeight = " + parentHeight);
        orientation = typedArray.getInt(R.styleable.RxBanner_orientation, LinearLayout.HORIZONTAL);
        itemPercent = typedArray.getInt(R.styleable.RxBanner_itemPercent, 100);
        itemSpace = typedArray.getInt(R.styleable.RxBanner_itemSpace, 0);
        timeInterval = typedArray.getInt(R.styleable.RxBanner_timeInterval, RxBannerConfig.getInstance().getTimeInterval());
        orderType = BannerUtil.getOrder(typedArray.getInt(R.styleable.RxBanner_orderType,
                BannerUtil.getOrderType(RxBannerConfig.getInstance().getOrderType())));
        typedArray.recycle();
        initView();
    }

    protected void initView() {
        mRecyclerView = new AutoPlayRecyclerView(mContext);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mRecyclerView, layoutParams);
        mLayoutManager = new ScaleLayoutManager.Builder(mContext, itemSpace).build();
        mLayoutManager.setOrientation(orientation);
        mLayoutManager.setMinScale(0.9f);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setTimeInterval(timeInterval);
        mRecyclerView.setDirection(orderType);
        initAdapter();
    }


    protected void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new RxBannerAdapter(mContext, orientation, getPercentSize());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public RxBanner setLoader(RxBannerLoaderInterface mLoader) {
        if (mAdapter != null) {
            mAdapter.setLoader(mLoader);
        }
        return this;
    }

    public RxBanner setDatas(List<RxBannerData> list) {
        if (mAdapter != null) {
            ArrayList<String> urls = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                urls.add(list.get(i).getUrl());
            }
            mAdapter.setDatas(urls);
        }
        return this;
    }

    //Integer, String data list
    public RxBanner setDatas(ArrayList<?> urls) {
        if (mAdapter != null) {
            mAdapter.setDatas(urls);
        }
        if (RxBannerConfig.getInstance().isDebug()) {
            for (int i = 0; i < urls.size(); i++) {
                RxBannerLogger.i("setDatas " + i + " = " + urls.get(i));
            }
        }
        return this;
    }

    public RxBanner setTitles(ArrayList<String> titles) {

        return this;
    }

    public RxBanner setOrderType(RxBannerConfig.OrderType orderType) {
        if (mRecyclerView != null) {
            mRecyclerView.setDirection(orderType);
        }
        this.orderType = orderType;
        RxBannerLogger.i("setOrderType = " + timeInterval);
        return this;
    }

    public RxBanner setTimeInterval(int timeInterval) {
        if (mRecyclerView != null) {
            mRecyclerView.setTimeInterval(timeInterval);
        }
        this.timeInterval = timeInterval;
        RxBannerLogger.i("setTimeInterval = " + timeInterval);
        return this;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public RxBannerConfig.OrderType getOrderType() {
        return orderType;
    }

    public void start() {
        if (mRecyclerView != null && mAdapter != null && !mAdapter.getDatas().isEmpty() && isAutoPlay()) {
            RxBannerLogger.i("start success");
            mRecyclerView.start();
        } else if (mRecyclerView != null && mAdapter != null && !mAdapter.getDatas().isEmpty() && !isAutoPlay()) {
            RxBannerLogger.i("can not start, auto play = false");
        } else {
            throw new NullPointerException(RxBannerLogger.LOGGER_TAG + " should setDatas before start");
        }
    }

    public void pause() {
        if (mRecyclerView != null) {
            mRecyclerView.pause();
        }
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    public RxBanner setAutoPlay(boolean autoPlay) {
        if (autoPlay && mRecyclerView != null && mAdapter != null && !mAdapter.getDatas().isEmpty()) {
            mRecyclerView.setAutoPlay(autoPlay);
        }
        if (!autoPlay && mRecyclerView != null) {
            mRecyclerView.setAutoPlay(autoPlay);
        }
        this.autoPlay = autoPlay;
        RxBannerLogger.i("setAutoPlay = " + autoPlay);
        return this;
    }

    public void setCurrentPosition(int position) {
        if (mRecyclerView != null && mAdapter != null && !mAdapter.getDatas().isEmpty() && position >= 0) {
            mRecyclerView.pause();
            mRecyclerView.smoothScrollToPosition(position);
            mRecyclerView.start();
        }
    }

    public int getCurrentPosition() {
        if (mRecyclerView != null && mAdapter != null && !mAdapter.getDatas().isEmpty()) {
            return mLayoutManager.getCurrentPosition();
        }
        return -1;
    }

    public RxBanner setOnBannerClickListener(RxBannerClickListener onClickListener) {
        if (mAdapter != null) {
            mAdapter.setRxBannerClickListener(onClickListener);
        }
        return this;
    }

    private int getPercentSize() {
        int percentSize = -1;
        if (orientation == LinearLayout.VERTICAL) {
            if(parentHeight == -1) return -1;
            percentSize = BannerUtil.getPercentSize(parentHeight, itemPercent);
            RxBannerLogger.i(" CH = " + parentHeight + " / " + percentSize);
        } else if(orientation == LinearLayout.HORIZONTAL){
            if(parentWidth == -1 ) return -1;
            percentSize = BannerUtil.getPercentSize(parentWidth, itemPercent);
            RxBannerLogger.i(" CW = " + parentWidth + " / "+ percentSize);
        }
        return percentSize;
    }

    public RxBanner setItemAnimator(RecyclerView.ItemAnimator itemAnimator){
        if(mRecyclerView != null){
            mRecyclerView.setItemAnimator(itemAnimator);
        }
        return this;
    }


    public static int getPercentSize(int parentSize, int itemPercent){
        return (int) (parentSize * (itemPercent / 100f));
    }
}
