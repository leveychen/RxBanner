package cn.levey.bannerlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.levey.bannerlib.base.RxBannerConstants;
import cn.levey.bannerlib.base.RxBannerLogger;
import cn.levey.bannerlib.base.RxBannerUtil;
import cn.levey.bannerlib.data.RxBannerAdapter;
import cn.levey.bannerlib.data.RxBannerConfig;
import cn.levey.bannerlib.data.RxBannerGlobalConfig;
import cn.levey.bannerlib.impl.RxBannerChangeListener;
import cn.levey.bannerlib.impl.RxBannerClickListener;
import cn.levey.bannerlib.impl.RxBannerCustomIndicatorClickListener;
import cn.levey.bannerlib.impl.RxBannerGuideFinishedListener;
import cn.levey.bannerlib.impl.RxBannerLoaderInterface;
import cn.levey.bannerlib.impl.RxBannerTitleChangeListener;
import cn.levey.bannerlib.impl.RxBannerTitleClickListener;
import cn.levey.bannerlib.indicator.animation.type.AnimationType;
import cn.levey.bannerlib.indicator.draw.controller.AttributeController;
import cn.levey.bannerlib.indicator.draw.controller.DrawController;
import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;
import cn.levey.bannerlib.manager.AutoPlayRecyclerView;
import cn.levey.bannerlib.manager.ScaleLayoutManager;
import cn.levey.bannerlib.view.RxBannerCustomIndicator;
import cn.levey.bannerlib.view.RxBannerEmptyView;
import cn.levey.bannerlib.view.RxBannerIndicator;
import cn.levey.bannerlib.view.RxBannerNumericIndicator;
import cn.levey.bannerlib.view.RxBannerTitle;

/**
 * Created by Levey on 2018/4/2 15:11.
 * e-mail: m@levey.cn
 */

public class RxBanner extends FrameLayout {

    private Context mContext;
    private int parentWidth = 0, parentHeight = 0;
    private RxBannerAdapter mAdapter;

    private AutoPlayRecyclerView mBannerRv;
    private RxBannerTitle mTitleView;
    private ScaleLayoutManager mLayoutManager;
    private List<Object> mUrls = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private RxBannerClickListener onBannerClickListener;
    private RxBannerChangeListener onBannerChangeListener;
    private RxBannerLoaderInterface mLoader;
    private RxBannerTitleClickListener onBannerTitleClickListener;
    private RxBannerGuideFinishedListener onGuideFinishedListener;
    private RecyclerView.ItemAnimator itemAnimator;
    private boolean needStart = false;
    private View mIndicatorView;
    private RxBannerEmptyView emptyView;
    private View swipeDisableView;
    private int currentPosition = 0;

    private RxBannerConfig config = new RxBannerConfig();

    public RxBannerConfig getConfig() {
        return config;
    }

    public RxBanner setConfig(RxBannerConfig config) {
        this.config = config;
        if(mBannerRv != null)  mBannerRv.setFlingDamping(config.getFlingDamping());
        if(mLayoutManager != null)  mLayoutManager.setCanSwipeWhenSingle(config.isCanSwipeWhenSingle());
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
        initInnerView(context, attrs);
    }

    protected void initInnerView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RxBanner);
        config.setAspectRatio(typedArray.getFloat(R.styleable.RxBanner_rb_aspectRatio, config.getAspectRatio()));
        config.setCanSwipe(typedArray.getBoolean(R.styleable.RxBanner_rb_canSwipe, config.isCanSwipe()));
        config.setCanSwipeWhenSingle(typedArray.getBoolean(R.styleable.RxBanner_rb_canSwipeWhenSingle, config.isCanSwipeWhenSingle()));
        config.setOrientation(typedArray.getInt(R.styleable.RxBanner_rb_orientation, config.getOrientation()));
        config.setViewPaperMode(typedArray.getBoolean(R.styleable.RxBanner_rb_viewPaperMode, config.isViewPaperMode()));
        config.setInfinite(typedArray.getBoolean(R.styleable.RxBanner_rb_infinite, config.isInfinite()));
        config.setAutoPlay(typedArray.getBoolean(R.styleable.RxBanner_rb_autoPlay, config.isAutoPlay()));
        config.setItemPercent(typedArray.getInt(R.styleable.RxBanner_rb_itemPercent, config.getItemPercent()));
        config.setItemSpacePx(typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_itemSpace, config.getItemSpace()));
        config.setItemScale(typedArray.getFloat(R.styleable.RxBanner_rb_itemScale, config.getItemScale()));
        config.setItemMoveSpeed(typedArray.getFloat(R.styleable.RxBanner_rb_itemMoveSpeed, config.getItemMoveSpeed()));
        config.setFlingDamping(typedArray.getFloat(R.styleable.RxBanner_rb_flingDamping, config.getFlingDamping()));
        config.setEmptyViewText(typedArray.getString(R.styleable.RxBanner_rb_emptyViewText));
        config.setEmptyViewResource(typedArray.getResourceId(R.styleable.RxBanner_rb_emptyViewResource, config.getEmptyViewResource()));
        config.setTimeInterval(typedArray.getInt(R.styleable.RxBanner_rb_timeInterval, RxBannerGlobalConfig.getInstance().getTimeInterval()));
        config.setCenterAlpha(typedArray.getFloat(R.styleable.RxBanner_rb_centerAlpha, config.getCenterAlpha()));
        config.setSideAlpha(typedArray.getFloat(R.styleable.RxBanner_rb_sideAlpha, config.getSideAlpha()));
        config.setOrderType(RxBannerUtil.getOrder(typedArray.getInt(R.styleable.RxBanner_rb_orderType, RxBannerUtil.getOrderType(config.getOrderType()))));
        //init title config
        initTitleConfig(typedArray);
        //init indicator config
        initIndicatorConfig(typedArray, attrs);
        typedArray.recycle();
        initInnerView();
    }

    private void initTitleConfig(TypedArray typedArray) {
        config.setTitleVisible(typedArray.getBoolean(R.styleable.RxBanner_rb_title_visible, config.isTitleVisible()));
        if (config.isTitleVisible()) {
            config.setTitleGravity(typedArray.getInt(R.styleable.RxBanner_rb_title_gravity, config.getTitleGravity()));
            config.setTitleLayoutGravity(typedArray.getInt(R.styleable.RxBanner_rb_title_layout_gravity, config.getTitleLayoutGravity()));
            config.setTitleMarginPx(typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_margin, config.getTitleMargin()));
            int titleMarginTop = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_marginTop, config.getTitleMarginTop());
            int titleMarginBottom = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_marginBottom, config.getTitleMarginBottom());
            int titleMarginStart = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_marginStart, config.getTitleMarginStart());
            int titleMarginEnd = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_marginEnd, config.getTitleMarginEnd());
            if (titleMarginTop == 0) titleMarginTop = config.getTitleMargin();
            if (titleMarginBottom == 0) titleMarginBottom = config.getTitleMargin();
            if (titleMarginStart == 0) titleMarginStart = config.getTitleMargin();
            if (titleMarginEnd == 0) titleMarginEnd = config.getTitleMargin();
            config.setTitleMarginTopPx(titleMarginTop);
            config.setTitleMarginBottomPx(titleMarginBottom);
            config.setTitleMarginStartPx(titleMarginStart);
            config.setTitleMarginEndPx(titleMarginEnd);
            config.setTitlePaddingPx(typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_padding, config.getTitlePadding()));
            int titlePaddingTop = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_paddingTop, config.getTitlePaddingTop());
            int titlePaddingBottom = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_paddingBottom, config.getTitlePaddingBottom());
            int titlePaddingStart = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_paddingStart, config.getTitlePaddingStart());
            int titlePaddingEnd = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_paddingEnd, config.getTitlePaddingEnd());
            if (titlePaddingTop == 0) titlePaddingTop = config.getTitlePadding();
            if (titlePaddingBottom == 0) titlePaddingBottom = config.getTitlePadding();
            if (titlePaddingStart == 0) titlePaddingStart = config.getTitlePadding();
            if (titlePaddingEnd == 0) titlePaddingEnd = config.getTitlePadding();
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
                config.setTitleHeightPx(typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_height, config.getTitleHeight()));
            } catch (Exception e) {
                config.setTitleHeightPx(typedArray.getInt(R.styleable.RxBanner_rb_title_height, config.getTitleHeight()));
            }
            config.setTitleSizePx(typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_title_size, config.getTitleSize()));
            config.setTitleColor(typedArray.getColor(R.styleable.RxBanner_rb_title_color, config.getTitleColor()));
            config.setTitleBackgroundColor(typedArray.getColor(R.styleable.RxBanner_rb_title_backgroundColor, config.getTitleBackgroundColor()));
            config.setTitleBackgroundResource(typedArray.getResourceId(R.styleable.RxBanner_rb_title_backgroundResource, config.getTitleBackgroundResource()));
            config.setTitleMarquee(typedArray.getBoolean(R.styleable.RxBanner_rb_title_marquee, config.isTitleMarquee()));
            config.setTitleLineSpacingMultiplier(typedArray.getFloat(R.styleable.RxBanner_rb_title_lineSpacingMultiplier, config.getTitleLineSpacingMultiplier()));
        }
    }

    protected void initIndicatorConfig(TypedArray typedArray, AttributeSet attrs) {
        config.setIndicatorVisible(typedArray.getBoolean(R.styleable.RxBanner_rb_indicator_visible, config.isIndicatorVisible()));
        if (config.isIndicatorVisible()) {
            AttributeController attributeController = new AttributeController();
            attributeController.init(mContext,attrs);
            config.setIndicatorConfig(attributeController.getIndicatorConfig());
        }
    }

    protected void initInnerView() {
        mBannerRv = new AutoPlayRecyclerView(mContext);
        mBannerRv.setItemAnimator(null);
        mBannerRv.setFlingDamping(config.getFlingDamping());
        LayoutParams layoutParams = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        addView(mBannerRv, layoutParams);
    }

    protected void initSwipeDisableView(){
        if(!config.isCanSwipe()){
            if(swipeDisableView != null) return;
            swipeDisableView = new View(mContext);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            swipeDisableView.setLayoutParams(params);
            addView(swipeDisableView);
            swipeDisableView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onBannerClickListener != null){
                        try {
                            onBannerClickListener.onItemClick(currentPosition,mUrls.get(currentPosition));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            swipeDisableView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(onBannerClickListener != null){
                        try {
                            onBannerClickListener.onItemLongClick(currentPosition,mUrls.get(currentPosition));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
            });
        }
    }

    protected void initStart() {
        initSwipeDisableView();
        initLayoutManager();
        initAdapter();
        if (mBannerRv != null && mAdapter != null && !mAdapter.getDatas().isEmpty() && isAutoPlay() && needStart) {
            mBannerRv.start();
            RxBannerLogger.i("start success");
        } else if (!isAutoPlay()) {
            RxBannerLogger.i("can not auto play, autoPlay = false");
        }

    }

    public RxBanner setSwipeManually(boolean swipeManually){
        config.setCanSwipe(swipeManually);
        if(swipeDisableView != null){
            if(swipeManually){
                swipeDisableView.setVisibility(GONE);
            }else {
                swipeDisableView.setVisibility(VISIBLE);
            }
        }else {
            initSwipeDisableView();
        }
        return this;
    }

    protected void initLayoutManager() {
        if (config.getAspectRatio() > 0 && parentWidth > 0) {
            parentHeight = (int) (parentWidth / config.getAspectRatio());
            LinearLayout.LayoutParams parent = new LinearLayout.LayoutParams(parentWidth, parentHeight);
            setLayoutParams(parent);
            requestLayout();

        }
        mLayoutManager = new ScaleLayoutManager.Builder(mContext, config.getItemSpace()).build();
        mLayoutManager.setOrientation(config.getOrientation());
        mLayoutManager.setItemScale(config.getItemScale());
        mLayoutManager.setAutoPlay(config.isAutoPlay());
        mLayoutManager.setInfinite(config.isInfinite());
        mLayoutManager.setItemMoveSpeed(config.getItemMoveSpeed() / 2.0f);
        mLayoutManager.setCenterAlpha(config.getCenterAlpha());
        mLayoutManager.setSideAlpha(config.getSideAlpha());
        mLayoutManager.setCanSwipeWhenSingle(config.isCanSwipeWhenSingle());
        if (onBannerChangeListener != null)
            mLayoutManager.setRxBannerChangeListener(onBannerChangeListener);
        if (onGuideFinishedListener != null)
            mLayoutManager.setRxBannerGuideFinishedListener(onGuideFinishedListener);

        mLayoutManager.setRxBannerTitleChangeListener(new RxBannerTitleChangeListener() {
            @Override
            public void onBannerSelected(int position) {
                currentPosition = position;
                if (mTitleView != null) mTitleView.setSelection(position);

                if(mIndicatorView instanceof RxBannerNumericIndicator){
                    ((RxBannerNumericIndicator) mIndicatorView).setSelection(position);
                }
            }
        });
        if (itemAnimator != null) mBannerRv.setItemAnimator(itemAnimator);
        if (config.isTitleVisible() && mTitleView == null) {
            createTitle();
        }
    }

    protected void createTitle() {

        mTitleView = new RxBannerTitle(mContext);
        LayoutParams titleLayoutParams = new LayoutParams(config.getTitleWidth(), config.getTitleHeight());
        titleLayoutParams.setMargins(config.getTitleMarginStart(), config.getTitleMarginTop(), config.getTitleMarginEnd(), config.getTitleMarginBottom());
        titleLayoutParams.gravity = config.getTitleLayoutGravity();
        mTitleView.setLayoutParams(titleLayoutParams);
        mTitleView.setTextColor(config.getTitleColor());
        mTitleView.setBackgroundColor(config.getTitleBackgroundColor());
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, config.getTitleSize());
        mTitleView.setPadding(config.getTitlePaddingStart(), config.getTitlePaddingTop(), config.getTitlePaddingEnd(), config.getTitlePaddingBottom());
        if (config.getTitleBackgroundResource() != Integer.MAX_VALUE)
            mTitleView.setBackgroundResource(config.getTitleBackgroundResource());
        if (config.isTitleMarquee()) {
            mTitleView.setFocused(true);
            mTitleView.setSingleLine(true);
            mTitleView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            mTitleView.setMarqueeRepeatLimit(-1);
        } else {
            mTitleView.setFocused(false);
            mTitleView.setSingleLine(false);
            mTitleView.setEllipsize(TextUtils.TruncateAt.END);
            mTitleView.setLineSpacing(0,config.getTitleLineSpacingMultiplier());
        }
        mTitleView.setTag(RxBannerConstants.TAG_TITLE_VIEW + currentPosition);
        mTitleView.setGravity(config.getTitleGravity());
        if(!mTitles.isEmpty()){
            mTitleView.setDatas(mTitles);
        }
        addView(mTitleView);
    }

    public RxBanner setOnBannerChangeListener(RxBannerChangeListener onBannerChangeListener) {
        if (mLayoutManager != null && onBannerChangeListener != null) {
            mLayoutManager.setRxBannerChangeListener(onBannerChangeListener);
        }
        this.onBannerChangeListener = onBannerChangeListener;
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

    protected void initIndicator(){
        if (config.isIndicatorVisible()) {
            if (mIndicatorView != null) {
                mIndicatorView.setTag(RxBannerConstants.TAG_INDICATOR_CUSTOM);
                addView(mIndicatorView);
            } else {
                if(config.getIndicatorConfig().getAnimationType() == AnimationType.NUMERIC
                        || config.getIndicatorConfig().getAnimationType() == AnimationType.NUMERIC_CIRCLE){
                    IndicatorConfig indicatorConfig = config.getIndicatorConfig();
                    final RxBannerNumericIndicator numericIndicator = new RxBannerNumericIndicator(mContext);
                    numericIndicator.setTotal(mUrls.size());
                    numericIndicator.setSelection(currentPosition);
                    LayoutParams indicatorLp = new LayoutParams(indicatorConfig.getWidth(), indicatorConfig.getHeight());
                    indicatorLp.gravity = indicatorConfig.getGravity();
                    indicatorLp.setMargins(
                            indicatorConfig.getMarginStart(),
                            indicatorConfig.getMarginTop(),
                            indicatorConfig.getMarginEnd(),
                            indicatorConfig.getMarginBottom());
                    numericIndicator.setLayoutParams(indicatorLp);
                    numericIndicator.setTextSize(TypedValue.COMPLEX_UNIT_PX,indicatorConfig.getTextSize());
                    numericIndicator.setTextColor(indicatorConfig.getTextColor());
                    numericIndicator.setBackgroundColor(indicatorConfig.getBackgroundColor());
                    if(indicatorConfig.getAnimationType() == AnimationType.NUMERIC_CIRCLE) {
                        numericIndicator.setCircle(true);
                    }else {
                        numericIndicator.setCircle(false);
                        if (indicatorConfig.getBackgroundResource() != Integer.MAX_VALUE) {
                            numericIndicator.setBackgroundResource(indicatorConfig.getBackgroundResource());
                        }
                    }
                    numericIndicator.setGravity(Gravity.CENTER);
                    numericIndicator.setPadding(indicatorConfig.getPaddingTop(), indicatorConfig.getPaddingStart(), indicatorConfig.getPaddingEnd(), indicatorConfig.getPaddingBottom());
                    mIndicatorView = numericIndicator;
                    mIndicatorView.setTag(RxBannerConstants.TAG_INDICATOR_NUMERIC);
                    addView(mIndicatorView);
                }else if(config.getIndicatorConfig().getAnimationType() == AnimationType.CUSTOM){
                    final RxBannerCustomIndicator circleIndicator = new RxBannerCustomIndicator(mContext,config.getIndicatorConfig(),new RxBannerCustomIndicatorClickListener() {
                        @Override
                        public void onClick(int position) {
                            setCurrentPosition(position);
                        }
                    });
                    circleIndicator.setRecyclerView(mBannerRv);
                    LayoutParams indicatorLp = new LayoutParams(config.getIndicatorConfig().getWidth(), config.getIndicatorConfig().getHeight());
                    indicatorLp.gravity = config.getIndicatorConfig().getGravity();
                    indicatorLp.setMargins(
                            config.getIndicatorConfig().getMarginStart(),
                            config.getIndicatorConfig().getMarginTop(),
                            config.getIndicatorConfig().getMarginEnd(),
                            config.getIndicatorConfig().getMarginBottom());
                    circleIndicator.setLayoutParams(indicatorLp);
                    mIndicatorView = circleIndicator;
                    mIndicatorView.setTag(RxBannerConstants.TAG_INDICATOR_CUSTOM);
                    addView(mIndicatorView);
                } else {
                    final RxBannerIndicator indicator = new RxBannerIndicator(mContext);
                    indicator.setIndicatorConfig(config.getIndicatorConfig());
                    indicator.setRecyclerView(mBannerRv);
                    if (indicator.getConfig().isClickable()) {
                        indicator.setClickListener(new DrawController.ClickListener() {
                            @Override
                            public void onIndicatorClicked(int position) {
                                setCurrentPosition(position);
                            }
                        });
                    }
                    LayoutParams indicatorLp = new LayoutParams(config.getIndicatorConfig().getWidth(), config.getIndicatorConfig().getHeight());
                    indicatorLp.gravity = indicator.getConfig().getGravity();
                    indicatorLp.setMargins(
                            indicator.getConfig().getMarginStart(),
                            indicator.getConfig().getMarginTop(),
                            indicator.getConfig().getMarginEnd(),
                            indicator.getConfig().getMarginBottom());
                    indicator.setLayoutParams(indicatorLp);
                    mIndicatorView = indicator;
                    mIndicatorView.setTag(RxBannerConstants.TAG_INDICATOR_DEFAULT);
                    addView(mIndicatorView);
                }
            }
        }
    }

    protected void initAdapter() {
        initRvData();
        if (mAdapter == null) {
            mAdapter = new RxBannerAdapter(mContext, config.getOrientation(), getPercentSize());
            mAdapter.setLoader(mLoader);
            mAdapter.setDatas(mUrls);
            if (config.getIndicatorConfig() != null) {
                config.getIndicatorConfig().setCount(mUrls.size());
            }
            if (onBannerClickListener != null) {
                mAdapter.setRxBannerClickListener(onBannerClickListener);
            }
            mBannerRv.setAdapter(mAdapter);
        }
        if (onBannerTitleClickListener != null && mTitleView != null) {
            mTitleView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mLayoutManager != null) {
                        onBannerTitleClickListener.onTitleClick(mLayoutManager.getCurrentPosition(), mTitleView.getText().toString());
                    }
                }
            });

            mTitleView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mLayoutManager != null) {
                        onBannerTitleClickListener.onTitleLongClick(mLayoutManager.getCurrentPosition(), mTitleView.getText().toString());
                    }
                    return true;
                }
            });
        }
        initIndicator();
        setLongClickable(true);
        scrollToCurrentPosition();
        checkEmpty();
    }

    protected void scrollToCurrentPosition(){
        if (mIndicatorView != null && mIndicatorView.getVisibility() == VISIBLE && mIndicatorView instanceof RxBannerIndicator)
            ((RxBannerIndicator) mIndicatorView).setSelection(currentPosition);
        if (mIndicatorView != null && mIndicatorView.getVisibility() == VISIBLE && mIndicatorView instanceof RxBannerNumericIndicator)
            ((RxBannerNumericIndicator) mIndicatorView).setSelection(currentPosition);
        if (mIndicatorView != null && mIndicatorView.getVisibility() == VISIBLE && mIndicatorView instanceof RxBannerCustomIndicator)
            ((RxBannerCustomIndicator) mIndicatorView).setSelection(currentPosition);
        if (mTitleView != null) mTitleView.setSelection(currentPosition);
        if(mLayoutManager != null) mLayoutManager.scrollToPosition(currentPosition);
        reset();
    }

    protected void checkEmpty() {
        if (mUrls.isEmpty()) {
            showEmptyView(VISIBLE);
        } else {
            showEmptyView(GONE);
        }
    }

    protected void showEmptyView(int visible) {
        if (emptyView == null) {
            if (visible == VISIBLE) {
                emptyView = new RxBannerEmptyView(mContext);
                if (config.getEmptyViewResource() != 0) {
                    emptyView.setImageResId(config.getEmptyViewResource());
                } else {
                    emptyView.setText(config.getEmptyViewText());
                }
                emptyView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                emptyView.setLayoutParams(layoutParam);
                emptyView.setVisibility(visible);
                addView(emptyView);
            }
        } else {
            emptyView.setVisibility(visible);
        }
    }


    public RxBannerIndicator getDefaultIndicator() {
        if (mIndicatorView != null && mIndicatorView.getTag().toString().equals(RxBannerConstants.TAG_INDICATOR_DEFAULT)) {
            return (RxBannerIndicator) mIndicatorView;
        }
        throw new NullPointerException("please check rb_indicator_viable or rb_indicator_animationType attribute in xml");
    }

    public RxBannerNumericIndicator getNumericIndicator() {
        if (mIndicatorView != null && mIndicatorView.getTag().toString().equals(RxBannerConstants.TAG_INDICATOR_NUMERIC)) {
            return (RxBannerNumericIndicator) mIndicatorView;
        }
        throw new NullPointerException("please check rb_indicator_viable or rb_indicator_animationType attribute in xml");
    }


    public RxBannerCustomIndicator getCustomIndicator() {
        if (mIndicatorView != null && mIndicatorView.getTag().toString().equals(RxBannerConstants.TAG_INDICATOR_CUSTOM)) {
            return (RxBannerCustomIndicator) mIndicatorView;
        }
        throw new NullPointerException("please check rb_indicator_viable or rb_indicator_animationType attribute in xml");
    }

    public RxBanner setLoader(RxBannerLoaderInterface mLoader) {
        if (mAdapter != null) {
            mAdapter.setLoader(mLoader);
        } else {
            this.mLoader = mLoader;
        }
        return this;
    }

    public RxBanner setBannerMatchParent(){
        if(mBannerRv != null){
            LayoutParams layoutParams = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            mBannerRv.setLayoutParams(layoutParams);
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public RxBanner setDatas(List<?> urls) {
        setInnerDatas(urls);
        setTitles(null);
        return this;
    }

    protected void setInnerDatas(List<?> urls) {
//        if(mLayoutManager == null){
//            initStart();
//        }
        if (urls != null && !urls.isEmpty()) {
            this.mUrls.clear();
            this.mUrls.addAll(urls);
            if(mIndicatorView != null && mIndicatorView instanceof RxBannerNumericIndicator)
                ((RxBannerNumericIndicator) mIndicatorView).setTotal(mUrls.size());

            if(mIndicatorView != null && mIndicatorView instanceof RxBannerIndicator)
                ((RxBannerIndicator) mIndicatorView).setCount(mUrls.size());


            if (mBannerRv != null && mAdapter != null && mAdapter.getDatas() != null) {
                mAdapter.setDatas(mUrls);
                currentPosition = 0;
                setCurrentPosition(0);
            }

        } else {
            pause();
            this.mUrls.clear();
            if (mAdapter != null) {
                mAdapter.setDatas(mUrls);

            }
        }
        checkEmpty();

    }

    public RxBanner setDatas(List<?> urls, List<String> titles) {
        setDatas(urls);
        setTitles(titles);
        return this;
    }



//    public void addData(String url, String title) {
//        this.mTitles.add(title);
//        this.mUrls.add(title);
//        if(mTitleView != null) mTitleView.addData(title);
//        this.mAdapter.addItem(url);
////        scrollToCurrentPosition();
////        checkEmpty();
//    }
//Not tested  2018/04/26
//    public void addData(String url, String title,int position) {
//        if(this.mUrls.isEmpty()){
//            this.mTitles.add(position,title);
//            this.mUrls.add(position,url);
//            if(mTitleView != null) mTitleView.addData(title,position);
//            this.mAdapter.addItem(url,position);
//            currentPosition = position;
//            scrollToCurrentPosition();
//            checkEmpty();
//            return;
//        }
//        this.mTitles.add(position,title);
//        this.mUrls.add(position,title);
//        if(mTitleView != null) mTitleView.addData(title,position);
//        this.mAdapter.addItem(url,position);
//        if(position <= mLayoutManager.getCurrentPosition()) {
//            currentPosition = mLayoutManager.getCurrentPosition() + 1;
//            scrollToCurrentPosition();
//            checkEmpty();
//        }
//    }

    public void addDatas(List<?> urls, List<String> titles) {
        addDatas(urls);
        addTitles(titles);
    }

    public void addDatas(List<?> urls) {
        if (urls != null && !urls.isEmpty()) {
            this.mUrls.addAll(urls);
            if(mIndicatorView != null && mIndicatorView instanceof RxBannerNumericIndicator)
                ((RxBannerNumericIndicator) mIndicatorView).setTotal(mUrls.size());
            if (mBannerRv != null && mAdapter != null && mAdapter.getDatas() != null && mLayoutManager != null) {
                mAdapter.addDatas(mUrls);
            }
        }
    }

    public void updateData(String url, int position){
        if(mUrls.isEmpty()) return;
        if(url != null){
            this.mUrls.set(position,url);
            if(this.mAdapter != null){
                this.mAdapter.updateItem(position,url);
            }
        }
        if(position == mLayoutManager.getCurrentPosition()) reset();
    }

    public void updateData(String url,String title, int position){
        if(mUrls.isEmpty()) return;
        if(mTitleView != null){
            mTitles = mTitleView.getTitleDatas();
            this.mTitles.set(position,title);
            mTitleView.updateItem(title,position);
        }
        if(url != null){
            this.mUrls.set(position,url);
            if(this.mAdapter != null){
                this.mAdapter.updateItem(position,url);
            }
        }
        if(position == mLayoutManager.getCurrentPosition()) reset();
    }

    public boolean isDatasEmpty(){
        return mUrls.isEmpty();
    }

    public int getDatasSize(){
        return mUrls.size();
    }

    //Not tested  2018/04/26
//    public void removeData(int position) {
//        if(mUrls.isEmpty()) {
//            checkEmpty();
//            return;
//        }
//        if (position < mUrls.size()) {
//            mTitles.remove(position);
//            mUrls.remove(position);
//            if (mTitleView != null) {
//                mTitleView.removeData(position);
//            }
//            if (mBannerRv != null && mAdapter != null && mAdapter.getDatas() != null && mLayoutManager != null) {
//                mAdapter.removeItem(position);
//                if(position <= mLayoutManager.getCurrentPosition()) {
//                    currentPosition =   mLayoutManager.getCurrentPosition() - 1;
//                    if(currentPosition < 0) currentPosition = 0;
//                    scrollToCurrentPosition();
//                    checkEmpty();
//                }
//            }
//        }else {
//            throw new IndexOutOfBoundsException("removeData Index: " + position + ", Size : " + mUrls.size());
//        }
//    }


    protected void setTitles(List<String> titles) {
        if (mTitleView != null) {
            mTitleView.setDatas(titles);
            return;
        }
        if(titles != null){
            mTitles.clear();
            mTitles.addAll(titles);
        }
    }

    protected void reset(){
        if (config.isAutoPlay() && mBannerRv.isRunning()) {
            mBannerRv.pause();
            mBannerRv.start();
        }
    }


    public RxBanner setOnGuideFinishedListener(RxBannerGuideFinishedListener onGuideFinishedListener) {
        if (onGuideFinishedListener != null && mLayoutManager != null)
            mLayoutManager.setRxBannerGuideFinishedListener(onGuideFinishedListener);
        this.onGuideFinishedListener = onGuideFinishedListener;
        return this;
    }

    public RxBanner setOnBannerTitleClickListener(final RxBannerTitleClickListener onTitleClickListener) {

        if (onTitleClickListener != null && mTitleView != null) {
            mTitleView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mLayoutManager != null) {
                        onTitleClickListener.onTitleClick(mLayoutManager.getCurrentPosition(), mTitleView.getText().toString());
                    }
                }
            });

            mTitleView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mLayoutManager != null) {
                        onTitleClickListener.onTitleLongClick(mLayoutManager.getCurrentPosition(), mTitleView.getText().toString());
                    }
                    return true;
                }
            });

        }
        this.onBannerTitleClickListener = onTitleClickListener;
        return this;
    }

    protected void addTitles(List<String> titles) {
        if (mTitleView == null)
            throw new NullPointerException(" titleView no init, please call setDatas(urls, titles) first");
        if (titles != null && !titles.isEmpty()) {
            mTitleView.addDatas(titles);
        }
    }




    public void start() {
        needStart = true;
        if (isParentCreate() && config.isAutoPlay()) {
            setAutoPlay(true);
        }
    }

    public void forceStart() {
        config.setAutoPlay(true);
        start();
    }

    public void onDestroy() {
        pause();
        if (mUrls != null) mUrls = null;
        if (mAdapter != null) mAdapter = null;
        if (mTitleView != null) mTitleView = null;
        if(onBannerClickListener != null) onBannerClickListener = null;
        if(onBannerChangeListener != null) onBannerChangeListener = null;
        if(onBannerTitleClickListener != null) onBannerTitleClickListener = null;
        if (mBannerRv != null) {
            mBannerRv.destroyCallbacks();
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


    public RxBanner setCurrentPosition(int position) {

        if(onGuideFinishedListener != null && mLayoutManager != null && position == mLayoutManager.getItemCount()){
            onGuideFinishedListener.onGuideFinished();
            return this;
        }
        if(onGuideFinishedListener != null && mLayoutManager != null && position == -1){
            return this;
        }

        this.currentPosition = position;

        if (mBannerRv != null && mAdapter != null && mLayoutManager != null && !mAdapter.getDatas().isEmpty()) {
            if (mLayoutManager.isInfinite()) {
                if (position == mUrls.size()) position = 0;
                if (position == -1) position = mUrls.size() - 1;
            } else {
                if (position < 0 || position > mUrls.size() - 1) {
                    if (config.isAutoPlay()) {
                        pause();
                    }
                    return this;
                }
                if (position == mUrls.size()) position = 0;
                //  if(position == -1 ) position = 0;
            }


            mBannerRv.smoothScrollToPosition(position);
//            ScrollHelper.smoothScrollToPosition(mBannerRv,mLayoutManager,position);
            if (mIndicatorView != null && mIndicatorView.getVisibility() == VISIBLE && mIndicatorView instanceof RxBannerIndicator)
                ((RxBannerIndicator) mIndicatorView).setSelection(position);
            if (mIndicatorView != null && mIndicatorView.getVisibility() == VISIBLE && mIndicatorView instanceof RxBannerNumericIndicator)
                ((RxBannerNumericIndicator) mIndicatorView).setSelection(position);

            if (mIndicatorView != null && mIndicatorView.getVisibility() == VISIBLE && mIndicatorView instanceof RxBannerCustomIndicator)
                ((RxBannerCustomIndicator) mIndicatorView).setSelection(position);
            if (mTitleView != null) mTitleView.setSelection(position);
            this.currentPosition = position;
            mBannerRv.setCurrentPosition(currentPosition);
            reset();
        }else {
            RxBannerLogger.i(" setCurrentPosition INIT  = " + position);
        }
        return this;
    }

    public int getCurrentPosition() {
//        if (mBannerRv != null && mAdapter != null && !mAdapter.getDatas().isEmpty()) {
//            return mLayoutManager != null ? mLayoutManager.getCurrentPosition() : currentPosition;
//        }
        return currentPosition;
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
                    if (parentWidth != 0 && parentWidth != -1 && parentHeight != 0 && parentHeight != -1 && needStart) {
                        initStart();
                    }
                }
            }
        });
    }

    public RxBannerTitle getTitleView() {
        if (mTitleView != null) {
            return mTitleView;
        }
        return null;
    }

    public String getTitleString() {
        if (mTitleView != null) {
            return mTitleView.getText().toString();
        } else {
            return null;
        }
    }


}
