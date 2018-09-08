package cn.levey.bannerlib.view;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import cn.levey.bannerlib.base.RxBannerLogger;
import cn.levey.bannerlib.impl.RxBannerIndicatorChangeListener;
import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;
import cn.levey.bannerlib.manager.AutoPlayRecyclerView;
import cn.levey.bannerlib.manager.ScaleLayoutManager;

public class RxBannerCustomIndicator extends LinearLayout implements RxBannerIndicatorChangeListener {

    private RecyclerView.AdapterDataObserver setObserver;
    private AutoPlayRecyclerView recyclerView;
    private ScaleLayoutManager layoutManager;
    private Animator mAnimatorOut;
    private Animator mAnimatorIn;
    private Animator mImmediateAnimatorOut;
    private Animator mImmediateAnimatorIn;
    private IndicatorConfig indicatorConfig;


    public void setIndicatorConfig(IndicatorConfig indicatorConfig) {
        this.indicatorConfig = indicatorConfig;
    }

    private int mLastPosition = -1;

    public RxBannerCustomIndicator(Context context) {
        super(context);
    }

    public RxBannerCustomIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

}

    public RxBannerCustomIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RxBannerCustomIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }






    private void checkIndicatorConfig(Context context) {
        mAnimatorOut = createAnimatorOut(context);
        mImmediateAnimatorOut = createAnimatorOut(context);
        mImmediateAnimatorOut.setDuration(0);

        mAnimatorIn = createAnimatorIn(context);
        mImmediateAnimatorIn = createAnimatorIn(context);
        mImmediateAnimatorIn.setDuration(0);

    }

    private Animator createAnimatorOut(Context context) {
        return AnimatorInflater.loadAnimator(context, indicatorConfig.getAnimatorResId());
    }

    private Animator createAnimatorIn(Context context) {
        Animator animatorIn;
        if (indicatorConfig.getAnimatorReverseResId() == 0) {
            animatorIn = AnimatorInflater.loadAnimator(context, indicatorConfig.getAnimatorResId());
            animatorIn.setInterpolator(new ReverseInterpolator());
        } else {
            animatorIn = AnimatorInflater.loadAnimator(context, indicatorConfig.getAnimatorReverseResId());
        }
        return animatorIn;
    }

    public void releaseView() {
        if (recyclerView != null) {
            recyclerView = null;
        }
    }

    private int getRecyclerViewCount() {
        if (recyclerView != null && recyclerView.getAdapter() != null) {
            return recyclerView.getAdapter().getItemCount();
        }else return 0;
    }

    public void setRecyclerView(@Nullable AutoPlayRecyclerView pager) {
        releaseView();
        if (pager == null) {
            return;
        }
        checkIndicatorConfig(getContext());
        recyclerView = pager;
        layoutManager = (ScaleLayoutManager) recyclerView.getLayoutManager();
        layoutManager.setRxBannerIndicatorChangeListener(this);
        mLastPosition = -1;
        registerSetObserver();
        createIndicators();
    }


    private void registerSetObserver() {
        if (setObserver != null || recyclerView == null || recyclerView.getAdapter() == null) {
            return;
        }

        setObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                if (recyclerView == null) {
                    return;
                }

                int newCount = getRecyclerViewCount();
                int currentCount = getChildCount();

                if (newCount == currentCount) {  // No change
                    return;
                } else if (mLastPosition < newCount) {
                    mLastPosition = layoutManager.getCurrentPosition();
                } else {
                    mLastPosition = -1;
                }
                createIndicators();
            }

        };

        try {
            recyclerView.getAdapter().registerAdapterDataObserver(setObserver);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }


    private void unRegisterSetObserver() {
        if (setObserver == null || recyclerView == null || recyclerView.getAdapter() == null) {
            return;
        }

        try {
            recyclerView.getAdapter().unregisterAdapterDataObserver(setObserver);
            setObserver = null;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        unRegisterSetObserver();
        releaseView();
        super.onDetachedFromWindow();
    }




    public void setSelection(int position){
        setCurrentPosition(position);
    }



    private void createIndicators() {


        removeAllViews();
        int count = getRecyclerViewCount();
        if (count <= 0) {
            setVisibility(GONE);
            return;
        }
        int currentItem = layoutManager.getCurrentPosition();
        int orientation = getOrientation();

        for (int i = 0; i < count; i++) {
            if (currentItem == i) {
                addIndicator(orientation, indicatorConfig.getIndicatorSelectedBackgroundResId(), mImmediateAnimatorOut);
            } else {
                addIndicator(orientation, indicatorConfig.getIndicatorUnselectedBackgroundResId(), mImmediateAnimatorIn);
            }
        }
        setVisibility(VISIBLE);
        setSelection(0);
        requestLayout();
    }

    private void addIndicator(int orientation, int backgroundDrawableId,
                              Animator animator) {
        if (animator.isRunning()) {
            animator.end();
            animator.cancel();
        }

        View Indicator = new View(getContext());
        Indicator.setBackgroundResource(backgroundDrawableId);
        addView(Indicator, indicatorConfig.getRadius() + indicatorConfig.getPadding() * 2, indicatorConfig.getRadius() + indicatorConfig.getPadding() * 2);
        LayoutParams lp = (LayoutParams) Indicator.getLayoutParams();

        if(indicatorConfig.getPadding() != 0) {
            if (orientation == HORIZONTAL) {
                lp.leftMargin = indicatorConfig.getPadding();
                lp.rightMargin = indicatorConfig.getPadding();
            } else {
                lp.topMargin = indicatorConfig.getPadding();
                lp.bottomMargin = indicatorConfig.getPadding();
            }
        }
        Indicator.setLayoutParams(lp);
//        Indicator.setPadding(mIndicatorMargin,mIndicatorMargin,mIndicatorMargin,mIndicatorMargin);

        animator.setTarget(Indicator);
        animator.start();
    }

    @Override
    public void onBannerSelected(int position) {
        setCurrentPosition(position);
    }

    private void setCurrentPosition(int position){
        if(mLastPosition == position) return;

        RxBannerLogger.i(" onBannerSelected = " + position);
        if (recyclerView.getAdapter() == null || getRecyclerViewCount() <= 0) {
            return;
        }

        if (mAnimatorIn.isRunning()) {
            mAnimatorIn.end();
            mAnimatorIn.cancel();
        }

        if (mAnimatorOut.isRunning()) {
            mAnimatorOut.end();
            mAnimatorOut.cancel();
        }

        View currentIndicator;
        if (mLastPosition >= 0 && (currentIndicator = getChildAt(mLastPosition)) != null) {
            currentIndicator.setBackgroundResource(indicatorConfig.getIndicatorUnselectedBackgroundResId());
            mAnimatorIn.setTarget(currentIndicator);
            mAnimatorIn.start();
        }

        View selectedIndicator = getChildAt(position);
        if (selectedIndicator != null) {
            selectedIndicator.setBackgroundResource(indicatorConfig.getIndicatorSelectedBackgroundResId());
            mAnimatorOut.setTarget(selectedIndicator);
            mAnimatorOut.start();
        }
        mLastPosition = position;
    }

    @Override
    public void onBannerScrollStateChanged(int state) {

    }

    private class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }

}