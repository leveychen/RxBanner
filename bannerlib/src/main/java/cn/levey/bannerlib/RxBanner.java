package cn.levey.bannerlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.levey.bannerlib.base.RxBannerConfig;
import cn.levey.bannerlib.base.RxBannerConstants;
import cn.levey.bannerlib.base.RxBannerGlobalConfig;
import cn.levey.bannerlib.base.RxBannerLogger;
import cn.levey.bannerlib.base.RxBannerTextView;
import cn.levey.bannerlib.base.RxBannerUtil;
import cn.levey.bannerlib.data.RxBannerAdapter;
import cn.levey.bannerlib.impl.RxBannerChangeListener;
import cn.levey.bannerlib.impl.RxBannerClickListener;
import cn.levey.bannerlib.impl.RxBannerLoaderInterface;
import cn.levey.bannerlib.impl.RxBannerTitleChangeListener;
import cn.levey.bannerlib.impl.RxBannerTitleClickListener;
import cn.levey.bannerlib.indicator.RxBannerIndicator;
import cn.levey.bannerlib.indicator.draw.controller.AttributeController;
import cn.levey.bannerlib.indicator.draw.controller.DrawController;
import cn.levey.bannerlib.manager.AutoPlayRecyclerView;
import cn.levey.bannerlib.manager.ScaleLayoutManager;

/**
 * Created by Levey on 2018/4/2 15:11.
 * e-mail: m@levey.cn
 */

public class RxBanner extends FrameLayout {

    private Context mContext;
    private int parentWidth, parentHeight;
    private RxBannerAdapter mAdapter;
    private AutoPlayRecyclerView mBannerRv;
    private RxBannerTextView mTitleTv;
    private ScaleLayoutManager mLayoutManager;
    private List<Object> mUrls = new ArrayList<>();
    private RxBannerClickListener onBannerClickListener;
    private RxBannerChangeListener rxBannerChangeListener;
    private RxBannerLoaderInterface mLoader;
    private RxBannerTitleClickListener onTitleClickListener;
    private RecyclerView.ItemAnimator itemAnimator;
    private boolean needStart = false;
    private View mIndicatorView;

    private RxBannerConfig config = new RxBannerConfig();

    public RxBannerConfig getConfig() {
        return config;
    }

    public RxBanner setConfig(RxBannerConfig config) {
        this.config = config;
        return this;
    }


    public RxBanner(@NonNull Context context) {
        this(context, null);
    }

    public RxBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RxBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(context, attrs);
    }

    protected void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RxBanner);
        config.setOrientation(typedArray.getInt(R.styleable.RxBanner_rb_orientation, config.getOrientation()));
        config.setViewPaperMode(typedArray.getBoolean(R.styleable.RxBanner_rb_viewPaperMode, config.isViewPaperMode()));
        config.setInfinite(typedArray.getBoolean(R.styleable.RxBanner_rb_infinite, config.isInfinite()));
        config.setAutoPlay( typedArray.getBoolean(R.styleable.RxBanner_rb_autoPlay, config.isAutoPlay()));
        config.setItemPercent(typedArray.getInt(R.styleable.RxBanner_rb_itemPercent, config.getItemPercent()));
        config.setItemSpacePx(typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_itemSpace, config.getItemSpace()));
        config.setItemScale(typedArray.getFloat(R.styleable.RxBanner_rb_itemScale, config.getItemScale()));
        config.setItemMoveSpeed(typedArray.getFloat(R.styleable.RxBanner_rb_itemMoveSpeed, config.getItemMoveSpeed()));
        if (config.getItemPercent() <= 0)
            throw new IllegalArgumentException(RxBannerLogger.LOGGER_TAG + ": itemPercent should be greater than 0");
        if (config.getItemMoveSpeed() <= 0)
            throw new IllegalArgumentException(RxBannerLogger.LOGGER_TAG + ": itemMoveSpeed should be greater than 0");
        final int timeInterval = typedArray.getInt(R.styleable.RxBanner_rb_timeInterval, RxBannerGlobalConfig.getInstance().getTimeInterval());
        if (timeInterval < 200)
            throw new IllegalArgumentException(RxBannerLogger.LOGGER_TAG + ": for better performance, timeInterval should be greater than 200 millisecond");
        config.setTimeInterval(timeInterval);
        config.setCenterAlpha(typedArray.getFloat(R.styleable.RxBanner_rb_centerAlpha, config.getCenterAlpha()));
        config.setSideAlpha(typedArray.getFloat(R.styleable.RxBanner_rb_sideAlpha, config.getSideAlpha()));
        config.setOrderType(RxBannerUtil.getOrder(typedArray.getInt(R.styleable.RxBanner_rb_orderType, RxBannerUtil.getOrderType(config.getOrderType()))));
        //title

        RxBannerLogger.i(" initTitleConfig 111 = " + config.isTitleVisible());
        RxBannerLogger.i(" initTitleConfig 999 = ");
        initTitleConfig(typedArray);
        //IndicatorConfig
        initIndicatorConfig(typedArray,attrs);
        typedArray.recycle();
        initView();
    }

    private void initTitleConfig(TypedArray typedArray){
        config.setTitleVisible(typedArray.getBoolean(R.styleable.RxBanner_rb_title_visible, config.isTitleVisible()));

        RxBannerLogger.i(" initTitleConfig222 = " + config.isTitleVisible());
        if (config.isTitleVisible()) {
            config.setTitleGravity(typedArray.getInt(R.styleable.RxBanner_rb_title_gravity, config.getTitleGravity()));
            config.setTitleLayoutGravity(typedArray.getInt(R.styleable.RxBanner_rb_title_layout_gravity, config.getTitleLayoutGravity()));
            config.setTitleMarginPx(typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_margin, config.getTitleMargin()));
            int titleMarginTop = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_marginTop, config.getTitleMarginTop());
            int titleMarginBottom = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_marginBottom, config.getTitleMarginBottom());
            int titleMarginStart = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_marginStart, config.getTitleMarginStart());
            int titleMarginEnd = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_marginEnd, config.getTitleMarginEnd());
            if(titleMarginTop == 0) titleMarginTop = config.getTitleMargin();
            if(titleMarginBottom == 0) titleMarginBottom = config.getTitleMargin();
            if(titleMarginStart == 0) titleMarginStart = config.getTitleMargin();
            if(titleMarginEnd == 0) titleMarginEnd = config.getTitleMargin();
            config.setTitleMarginTopPx(titleMarginTop);
            config.setTitleMarginBottomPx(titleMarginBottom);
            config.setTitleMarginStartPx(titleMarginStart);
            config.setTitleMarginEndPx(titleMarginEnd);
            config.setTitlePaddingPx(typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_padding,config.getTitlePadding()));
            int titlePaddingTop = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_paddingTop, config.getTitlePaddingTop());
            int titlePaddingBottom = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_paddingBottom, config.getTitlePaddingBottom());
            int titlePaddingStart = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_paddingStart, config.getTitlePaddingStart());
            int titlePaddingEnd = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_paddingEnd, config.getTitlePaddingEnd());
            if(titlePaddingTop == 0) titlePaddingTop = config.getTitlePadding();
            if(titlePaddingBottom == 0) titlePaddingBottom = config.getTitlePadding();
            if(titlePaddingStart == 0) titlePaddingStart = config.getTitlePadding();
            if(titlePaddingEnd == 0) titlePaddingEnd = config.getTitlePadding();
            config.setTitlePaddingTopPx(titlePaddingTop);
            config.setTitlePaddingBottomPx(titlePaddingBottom);
            config.setTitlePaddingStartPx(titlePaddingStart);
            config.setTitlePaddingEndPx(titlePaddingEnd);
            try {
                config.setTitleWidthPx(typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_width, config.getTitleWidth()));
            } catch (Exception e) {
                config.setTitleWidthPx(typedArray.getInt(R.styleable.RxBanner_rb_title_width, config.getTitleWidth()));
            }
            try {
                config.setTitleHeightPx(typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_height,config.getTitleHeight()));
            } catch (Exception e) {
                config.setTitleHeightPx(typedArray.getInt(R.styleable.RxBanner_rb_title_height, config.getTitleHeight()));
            }
            config.setTitleSizePx(typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_size, config.getTitleSize()));
            config.setTitleColor(typedArray.getColor(R.styleable.RxBanner_rb_title_color, config.getTitleColor()));
            config.setTitleBackgroundColor(typedArray.getColor(R.styleable.RxBanner_rb_title_backgroundColor, config.getTitleBackgroundColor()));
            config.setTitleBackgroundResource(typedArray.getResourceId(R.styleable.RxBanner_rb_title_backgroundResource, config.getTitleBackgroundResource()));
            config.setTitleMarquee(typedArray.getBoolean(R.styleable.RxBanner_rb_title_marquee, config.isTitleMarquee()));
        }
    }

    protected void initIndicatorConfig(TypedArray typedArray,AttributeSet attrs){
        config.setIndicatorVisible(typedArray.getBoolean(R.styleable.RxBanner_rb_title_marquee, config.isIndicatorVisible()));
        if (config.isIndicatorVisible()) {
            AttributeController attributeController = new AttributeController();
            config.setIndicatorConfigConfig(attributeController.init(mContext, attrs));
        }
    }

    protected void initView() {
        mBannerRv = new AutoPlayRecyclerView(mContext);
        mBannerRv.setItemAnimator(null);
        LayoutParams layoutParams = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        addView(mBannerRv, layoutParams);
    }

    protected void createTitle(){
        mTitleTv = new RxBannerTextView(mContext);
        LayoutParams titleLayoutParams = new LayoutParams(config.getTitleWidth(), config.getTitleHeight());
        titleLayoutParams.setMargins(config.getTitleMarginStart(), config.getTitleMarginTop(), config.getTitleMarginEnd(), config.getTitleMarginBottom());
        titleLayoutParams.gravity = config.getTitleLayoutGravity();
        mTitleTv.setLayoutParams(titleLayoutParams);
        mTitleTv.setTextColor(config.getTitleColor());
        mTitleTv.setBackgroundColor(config.getTitleBackgroundColor());
        mTitleTv.setGravity(config.getTitleGravity());
        mTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, config.getTitleSize());
        mTitleTv.setPadding(config.getTitlePaddingStart(), config.getTitlePaddingTop(), config.getTitlePaddingEnd(), config.getTitlePaddingBottom());
        if (config.getTitleBackgroundResource() != Integer.MAX_VALUE)
            mTitleTv.setBackgroundResource(config.getTitleBackgroundResource());
        if (config.isTitleMarquee()) {
            mTitleTv.setFocused(true);
            mTitleTv.setSingleLine(true);
            mTitleTv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            mTitleTv.setMarqueeRepeatLimit(-1);
        } else {
            mTitleTv.setFocused(false);
        }
        mTitleTv.setTag(RxBannerConstants.TAG_TITLE_VIEW + 0);
        addView(mTitleTv, titleLayoutParams);
    }

    public RxBanner setOnBannerChangeListener(RxBannerChangeListener onBannerChangeListener) {
        if (mLayoutManager != null && onBannerChangeListener != null) {
            mLayoutManager.setRxBannerChangeListener(onBannerChangeListener);
        }
        this.rxBannerChangeListener = onBannerChangeListener;
        return this;
    }


    protected void initRvData() {
        if (mBannerRv != null) {
            mBannerRv.setLayoutManager(mLayoutManager);
            mBannerRv.setViewPaperMode(config.isViewPaperMode());
            mBannerRv.setDirection(config.getOrderType());
            mBannerRv.setTimeInterval(config.getTimeInterval());
            mBannerRv.setAutoPlay(config.isAutoPlay());
        }
    }

    protected void initAdapter() {
        initRvData();
        if (mAdapter == null) {
            mAdapter = new RxBannerAdapter(mContext, config.getOrientation(), getPercentSize());
            mAdapter.setLoader(mLoader);
            mAdapter.setDatas(mUrls);
            if (config.getIndicatorConfigConfig() != null) {
                config.getIndicatorConfigConfig().setCount(mUrls.size());
            }
            if(onBannerClickListener != null) {
                mAdapter.setRxBannerClickListener(onBannerClickListener);
            }
            mBannerRv.setAdapter(mAdapter);
        }


        if (onTitleClickListener != null && mTitleTv != null) {
            mTitleTv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mLayoutManager != null) {
                        onTitleClickListener.onTitleClick(mLayoutManager.getCurrentPosition());
                    }
                }
            });
        }

        if (config.isIndicatorVisible()) {
            if (mIndicatorView != null) {
                mIndicatorView.setTag(RxBannerConstants.TAG_INDICATOR_CUSTOM);
                addView(mIndicatorView);
            } else {
                final RxBannerIndicator indicator = new RxBannerIndicator(mContext);
                indicator.setIndicatorConfig(config.getIndicatorConfigConfig());
                indicator.setRecyclerView(mBannerRv);
                if (indicator.getConfig().isClickable()) {
                    indicator.setClickListener(new DrawController.ClickListener() {
                        @Override
                        public void onIndicatorClicked(int position) {
                            setCurrentPosition(position);
                        }
                    });
                }
                LayoutParams indicatorLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                indicatorLp.gravity = indicator.getConfig().getGravity();
                indicatorLp.setMargins(
                        indicator.getConfig().getMarginStart(),
                        indicator.getConfig().getMarginTop(),
                        indicator.getConfig().getMarginEnd(),
                        indicator.getConfig().getMarginBottom());
                indicator.setLayoutParams(indicatorLp);
                mIndicatorView = indicator;
                mIndicatorView.setTag(RxBannerConstants.TAG_INDICATOR_CUSTOM);
                addView(mIndicatorView);
            }
        }

        if(mTitleTv != null ) mTitleTv.setSelection(0);
        setLongClickable(true);

    }

    public View getCustomIndicator(){
        if(mIndicatorView != null && mIndicatorView.getTag().toString().equals(RxBannerConstants.TAG_INDICATOR_CUSTOM)){
            return mIndicatorView;
        }
        throw new NullPointerException("please set a custom indicator view before get it");
    }

    public RxBannerIndicator getDefaultIndicator(){
        if(mIndicatorView != null && mIndicatorView.getTag().toString().equals(RxBannerConstants.TAG_INDICATOR_DEFAULT)){
            return (RxBannerIndicator) mIndicatorView;
        }
        throw new NullPointerException("please check rb_indicator_viable attribute in xml");
    }

    public RxBanner setLoader(RxBannerLoaderInterface mLoader) {
        if (mAdapter != null) {
            mAdapter.setLoader(mLoader);
        } else {
            this.mLoader = mLoader;
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public RxBanner setDatas(List<?> urls) {
        setInnerDatas(urls);
        return this;
    }

    protected void setInnerDatas(List<?> urls){
//        if(mLayoutManager == null){
//            initStart();
//        }
        if (urls != null && !urls.isEmpty()) {

            this.mUrls.clear();
            this.mUrls.addAll(urls);
            if (mBannerRv != null && mAdapter != null && mAdapter.getDatas() != null) {
                mAdapter.setDatas(mUrls);
                setCurrentPosition(0);
            }
        }
    }

    public RxBanner setDatas(List<?> urls, List<String> titles) {
        setDatas(urls);
        setTitles(titles);
        return this;
    }

    public void addDatas(List<?> urls, List<String> titles) {
        addDatas(urls);
        addTitles(titles);
    }

    public void addDatas(List<?> urls) {
        if (urls != null && !urls.isEmpty()) {
            this.mUrls.addAll(urls);
            if (mBannerRv != null && mAdapter != null && mAdapter.getDatas() != null && mLayoutManager != null) {
                mAdapter.setDatas(mUrls);
                setCurrentPosition(mLayoutManager.getCurrentPosition());
            }
        }
    }

    public void removeData(int position){
        if(position < mUrls.size()) {
            mUrls.remove(position);
            if(mTitleTv != null) mTitleTv.removeData(position);
        }
        if (mBannerRv != null && mAdapter != null && mAdapter.getDatas() != null && mLayoutManager != null) {
            mAdapter.setDatas(mUrls);
            if(position == mUrls.size() - 1){
                setCurrentPosition(0);
            }else {
                setCurrentPosition(mLayoutManager.getCurrentPosition());
            }
        }
    }


    protected void setTitles(List<String> titles) {
        if(mTitleTv == null && config.isTitleVisible()){
            createTitle();
        }
        if(mTitleTv != null) {
            mTitleTv.setDatas(titles);
        }
    }

    public RxBanner setOnBannerTitleClickListener(final RxBannerTitleClickListener onTitleClickListener) {

        if (onTitleClickListener != null && mTitleTv != null) {
            mTitleTv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mLayoutManager != null) {
                        onTitleClickListener.onTitleClick(mLayoutManager.getCurrentPosition());
                    }
                }
            });
        }
        this.onTitleClickListener = onTitleClickListener;
        return this;
    }

    protected void addTitles(List<String> titles) {
        if(mTitleTv == null) throw new NullPointerException(" titleView no init, please call setDatas(urls, titles) first");
        if(titles != null && !titles.isEmpty()){
            mTitleTv.addDatas(titles);
        }
    }

    protected void initLayoutManager (){
        mLayoutManager = new ScaleLayoutManager.Builder(mContext, config.getItemSpace()).build();
        mLayoutManager.setOrientation(config.getOrientation());
        mLayoutManager.setItemScale(config.getItemScale());
        mLayoutManager.setAutoPlay(config.isAutoPlay());
        mLayoutManager.setInfinite(config.isInfinite());
        mLayoutManager.setItemMoveSpeed(config.getItemMoveSpeed());
        mLayoutManager.setCenterAlpha(config.getCenterAlpha());
        mLayoutManager.setSideAlpha(config.getSideAlpha());
        if(rxBannerChangeListener != null) mLayoutManager.setRxBannerChangeListener(rxBannerChangeListener);
        mLayoutManager.setRxBannerTitleChangeListener(new RxBannerTitleChangeListener() {
            @Override
            public void onBannerSelected(int position) {
                if(mTitleTv != null) mTitleTv.setSelection(position);
            }
        });
        if (itemAnimator != null) mBannerRv.setItemAnimator(itemAnimator);
        if (config.isTitleVisible() && mTitleTv == null) {
            createTitle();
        }
    }

    protected void initStart() {
        initLayoutManager();
        initAdapter();
        if (mBannerRv != null && mAdapter != null && !mAdapter.getDatas().isEmpty() && isAutoPlay() && needStart) {
            mBannerRv.start();
            RxBannerLogger.i("start success");
        } else if (!isAutoPlay()) {
            RxBannerLogger.i("can not start, auto play = false");
        } else {
            RxBannerLogger.i("please call start() to start banner");
        }
    }

    public void start() {
        needStart = true;
        if (isParentCreate() && config.isAutoPlay()) {
            setAutoPlay(true);
        }
    }

    public void  forceStart(){
        config.setAutoPlay(true);
        start();
    }


    //已在 onDetachedFromWindow 实现回收资源，也可根据需求自行调用完成销毁
    public void onDestroy() {
        pause();
        if(mUrls !=null) mUrls = null;
        if (mAdapter != null) mAdapter = null;
        if (mTitleTv != null) mTitleTv = null;
        if (mBannerRv != null) {
            mBannerRv.removeAllViews();
            mBannerRv.destroyDrawingCache();
            mBannerRv = null;
        }
    }

    public void onResume() {
        start();
    }

    public void pause() {
        if (mBannerRv != null && config.isAutoPlay()) {
            mBannerRv.pause();
        }
    }

    public void onPause() {
        pause();
    }

    protected void restart() {
        if (config.isAutoPlay()) {
            pause();
            start();
        }
    }

    public RxBanner setCustomIndicator(View indicatorView) {
        this.mIndicatorView = indicatorView;
        return this;
    }

    public boolean isAutoPlay() {
        return config.isAutoPlay();
    }

    public RxBanner setAutoPlay(boolean autoPlay) {
        if (mBannerRv != null && mAdapter != null && mLayoutManager != null) {
            mLayoutManager.setAutoPlay(autoPlay);
            mBannerRv.setAutoPlay(autoPlay);
        }
        this.config.setAutoPlay(autoPlay);
        return this;
    }

    public void setCurrentPosition(int position) {

        if (mBannerRv != null && mAdapter != null && mLayoutManager != null && !mAdapter.getDatas().isEmpty()) {
            if(mLayoutManager.isInfinite()) {
                if(position == mUrls.size()) position = 0;
                if (position == -1) position = mUrls.size() - 1;
            }else {
                if(position == mUrls.size()) position = 0;
                if(position == -1 ) position = 0;
            }
            RxBannerLogger.i("save Position = " + position);
            mBannerRv.smoothScrollToPosition(position);
            if(mIndicatorView != null && mIndicatorView.getVisibility() == VISIBLE && mIndicatorView instanceof  RxBannerIndicator)
                ((RxBannerIndicator) mIndicatorView).setSelection(position);
            if(mTitleTv != null) mTitleTv.setSelection(position);
            if (config.isAutoPlay() && mBannerRv.isRunning()) {
                mBannerRv.pause();
                mBannerRv.start();
            }
        }
    }

    public int getCurrentPosition() {
        if (mBannerRv != null && mAdapter != null && !mAdapter.getDatas().isEmpty()) {
            return mLayoutManager != null ? mLayoutManager.getCurrentPosition() : -1;
        }
        return -1;
    }

    public RxBanner setOnBannerClickListener(RxBannerClickListener onClickListener) {
        if (mAdapter != null) {
            mAdapter.setRxBannerClickListener(onClickListener);
        }
        this.onBannerClickListener = onClickListener;
        return this;
    }

    private int getPercentSize() {
        int percentSize = RelativeLayout.LayoutParams.MATCH_PARENT;
        if (config.getOrientation() == LinearLayout.VERTICAL) {
            if (parentHeight == RelativeLayout.LayoutParams.MATCH_PARENT)
                return RelativeLayout.LayoutParams.MATCH_PARENT;
            percentSize = RxBannerUtil.getPercentSize(parentHeight, config.getItemPercent());
        } else if (config.getOrientation() == LinearLayout.HORIZONTAL) {
            if (parentWidth == RelativeLayout.LayoutParams.MATCH_PARENT)
                return RelativeLayout.LayoutParams.MATCH_PARENT;
            percentSize = RxBannerUtil.getPercentSize(parentWidth, config.getItemPercent());
        }
        return percentSize;
    }

    public RxBanner setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        if (mBannerRv != null) {
            mBannerRv.setItemAnimator(itemAnimator);
        }
        this.itemAnimator = itemAnimator;
        return this;
    }

    protected boolean isParentCreate() {
        return parentWidth != 0 && parentWidth != -1 && parentHeight != 0 && parentHeight != -1;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final View v;
        try {
            // 从第一个已创建的子view获取父布局的宽高，用于下面的百分比计算
            v = getChildAt(0);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (v.getWidth() != 0 && v.getHeight() != 0) {
                    if (parentWidth == 0 || parentWidth == -1) parentWidth = v.getWidth();
                    if (parentHeight == 0 || parentHeight == -1) parentHeight = v.getHeight();
                    if (parentWidth != 0 && parentWidth != -1 && parentHeight != 0 && parentHeight != -1 && needStart)
                        initStart();
                }
            }
        });
    }

    public RxBannerTextView getTitleView() {
        if (mTitleTv != null) {
            return mTitleTv;
        }
        return null;
    }

    public String getTitleString() {
        if (mTitleTv != null) {
            return mTitleTv.getText().toString();
        } else {
            return null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDestroy();
    }
}
