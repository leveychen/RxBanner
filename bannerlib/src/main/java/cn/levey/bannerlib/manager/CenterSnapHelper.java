package cn.levey.bannerlib.manager;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import cn.levey.bannerlib.base.RxBannerLogger;
import cn.levey.bannerlib.data.RxBannerGlobalConfig;
import cn.levey.bannerlib.impl.RxBannerChangeListener;
import cn.levey.bannerlib.impl.RxBannerIndicatorChangeListener;

/**
 * Class intended to support snapping for a {@link RecyclerView}
 * which use {@link ViewPagerLayoutManager} as its {@link LayoutManager}.
 * <p>
 * The implementation will snap the center of the target child view to the center of
 * the attached {@link RecyclerView}.
 */
class CenterSnapHelper extends RecyclerView.OnFlingListener {

    RecyclerView mRecyclerView;
    Scroller mGravityScroller;


    private boolean isInitialize = true;
    private boolean paperMode = true;
    private float flingDamping = 1.0f;

    void setPaperMode(boolean viewPaperMode) {
        this.paperMode = viewPaperMode;
    }


    /**
     * when the dataSet is extremely large
     * {@link #snapToCenterView(ViewPagerLayoutManager, RxBannerChangeListener)}
     * may keep calling itself because the accuracy of float
     */
    private boolean snapToCenter = true;

    // Handles the snap on scroll case.
    private final RecyclerView.OnScrollListener mScrollListener =
            new RecyclerView.OnScrollListener() {

                boolean mScrolled = false;

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    final ViewPagerLayoutManager layoutManager = (ViewPagerLayoutManager) recyclerView.getLayoutManager();

                    final RxBannerChangeListener changeListener = layoutManager.getRxBannerChangeListener();

                    if (changeListener != null) {
                        changeListener.onBannerScrollStateChanged(newState);
                    }
                    final RxBannerIndicatorChangeListener indicatorChangeListener = layoutManager.getRxBannerIndicatorChangeListener();
                    if (indicatorChangeListener != null) {
                        indicatorChangeListener.onBannerScrollStateChanged(newState);
                    }
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && mScrolled) {
                        mScrolled = false;
                        layoutManager.isScrollEnabled = true;
                        if (!snapToCenter) {
                            snapToCenter = true;
                            snapToCenterView(layoutManager, changeListener);
                        } else {
                            snapToCenter = false;
                        }
                    }
                    if (RxBannerGlobalConfig.getInstance().getScrollStateChangedListener() != null) {
                        RxBannerGlobalConfig.getInstance().getScrollStateChangedListener().onScrollStateChanged(recyclerView, newState);
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dx != 0 || dy != 0) {
                        mScrolled = true;
                    }
                }
            };


    /**
     * Please attach after {{@link LayoutManager} is setting}
     * Attaches the {@link CenterSnapHelper} to the provided RecyclerView, by calling
     * {@link RecyclerView#setOnFlingListener(RecyclerView.OnFlingListener)}.
     * You can call this method with {@code null} to detach it from the current RecyclerView.
     *
     * @param recyclerView The RecyclerView instance to which you want to add this helper or
     *                     {@code null} if you want to remove CenterSnapHelper from the current
     *                     RecyclerView.
     * @throws IllegalArgumentException if there is already a {@link RecyclerView.OnFlingListener}
     *                                  attached to the provided {@link RecyclerView}.
     */
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView)
            throws IllegalStateException {
        if (mRecyclerView == recyclerView) {
            return; // nothing to do
        }
        if (mRecyclerView != null) {
            destroyCallbacks();
        }
        mRecyclerView = recyclerView;
        if (mRecyclerView != null) {
            final LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            if (!(layoutManager instanceof ViewPagerLayoutManager)) return;

            setupCallbacks();
            mGravityScroller = new Scroller(mRecyclerView.getContext(),
                    new DecelerateInterpolator());
            snapToCenterView((ViewPagerLayoutManager) layoutManager, ((ViewPagerLayoutManager) layoutManager).getRxBannerChangeListener());
        }
    }

    void snapToCenterView(ViewPagerLayoutManager layoutManager, RxBannerChangeListener changeListener) {

        final int cp = layoutManager.getCurrentPosition();
        if (changeListener != null) {
            changeListener.onBannerSelected(cp);
        }

        if(!isInitialize) {
            if (layoutManager.getRxBannerIndicatorChangeListener() != null)
                layoutManager.getRxBannerIndicatorChangeListener().onBannerSelected(cp);

            if (layoutManager.getRxBannerTitleChangeListener() != null)
                layoutManager.getRxBannerTitleChangeListener().onBannerSelected(cp);
        }
        isInitialize = false;

        final int delta = layoutManager.getOffsetToCenter();
        if (delta != 0) {
            if (layoutManager.getOrientation()
                    == ViewPagerLayoutManager.VERTICAL)
                mRecyclerView.smoothScrollBy(0, delta);
            else
                mRecyclerView.smoothScrollBy(delta, 0);
        } else {
            // set it false to make smoothScrollToPosition keep trigger the listener
            snapToCenter = false;
        }
    }

    /**
     * Called when an instance of a {@link RecyclerView} is attached.
     */
    void setupCallbacks() throws IllegalStateException {
        if (mRecyclerView.getOnFlingListener() != null) {
            throw new IllegalStateException("An instance of OnFlingListener already set.");
        }
        mRecyclerView.addOnScrollListener(mScrollListener);
        mRecyclerView.setOnFlingListener(this);
    }

    /**
     * Called when the instance of a {@link RecyclerView} is detached.
     */
    void destroyCallbacks() {
        mRecyclerView.removeOnScrollListener(mScrollListener);
        mRecyclerView.setOnFlingListener(null);
    }


    @Override
    public boolean onFling(int velocityX, int velocityY) {

        if (!paperMode) {
            ViewPagerLayoutManager layoutManager = (ViewPagerLayoutManager) mRecyclerView.getLayoutManager();
            if (layoutManager == null) {
                return false;
            }
            RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
            if (adapter == null) {
                return false;
            }

            if (!layoutManager.isInfinite() &&
                    (layoutManager.mOffset == layoutManager.getMaxOffset()
                            || layoutManager.mOffset == layoutManager.getMinOffset())) {
                final int minFlingVelocity = mRecyclerView.getMinFlingVelocity();
                mGravityScroller.fling(0, 0, velocityX, velocityY,
                        Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (layoutManager.mOrientation == ViewPagerLayoutManager.VERTICAL
                        && Math.abs(velocityY) > minFlingVelocity * flingDamping) {
                    if(layoutManager.getRxBannerChangeListener() != null && layoutManager.mOffset == layoutManager.getMaxOffset()) {
                        layoutManager.getRxBannerChangeListener().onGuideFinished();
                    }
                }else if (layoutManager.mOrientation == ViewPagerLayoutManager.HORIZONTAL
                        && Math.abs(velocityX) > minFlingVelocity * flingDamping) {
                    if(layoutManager.getRxBannerChangeListener() != null && layoutManager.mOffset == layoutManager.getMaxOffset()) {
                        layoutManager.getRxBannerChangeListener().onGuideFinished();
                    }
                }else {
                    final int cp = layoutManager.getCurrentPosition();
                    mRecyclerView.smoothScrollToPosition(cp);

                    if(layoutManager.getRxBannerIndicatorChangeListener() != null)
                        layoutManager.getRxBannerIndicatorChangeListener().onBannerSelected(cp);
                    if(layoutManager.getRxBannerTitleChangeListener() != null)
                        layoutManager.getRxBannerTitleChangeListener().onBannerSelected(cp);
                }
            }

            final int minFlingVelocity = mRecyclerView.getMinFlingVelocity();
            mGravityScroller.fling(0, 0, velocityX, velocityY,
                    Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (layoutManager.mOrientation == ViewPagerLayoutManager.VERTICAL
                    && Math.abs(velocityY) > minFlingVelocity * flingDamping) {
                final int currentPosition = layoutManager.getCurrentPositionOffset();
                final int offsetPosition = (int) (mGravityScroller.getFinalY() /
                        layoutManager.mInterval / layoutManager.getDistanceRatio());
                int cp = layoutManager.getReverseLayout() ?
                        currentPosition - offsetPosition : currentPosition + offsetPosition;
                if (cp == layoutManager.getItemCount()) cp = 0;
                mRecyclerView.smoothScrollToPosition(cp);
                if(layoutManager.getRxBannerIndicatorChangeListener() != null)
                    layoutManager.getRxBannerIndicatorChangeListener().onBannerSelected(cp);
                if(layoutManager.getRxBannerTitleChangeListener() != null)
                    layoutManager.getRxBannerTitleChangeListener().onBannerSelected(cp);
                return true;
            } else if (layoutManager.mOrientation == ViewPagerLayoutManager.HORIZONTAL
                    && Math.abs(velocityX) > minFlingVelocity * flingDamping) {
                final int currentPosition = layoutManager.getCurrentPositionOffset();
                final int offsetPosition = (int) (mGravityScroller.getFinalX() /
                        layoutManager.mInterval / layoutManager.getDistanceRatio());
                int cp = layoutManager.getReverseLayout() ? currentPosition - offsetPosition : currentPosition + offsetPosition;
                if (cp == layoutManager.getItemCount()) cp = 0;
                mRecyclerView.smoothScrollToPosition(cp);
                if(layoutManager.getRxBannerIndicatorChangeListener() != null)
                    layoutManager.getRxBannerIndicatorChangeListener().onBannerSelected(cp);
                if(layoutManager.getRxBannerTitleChangeListener() != null)
                    layoutManager.getRxBannerTitleChangeListener().onBannerSelected(cp);
                return true;
            }else {
                final int cp = layoutManager.getCurrentPosition();
                mRecyclerView.smoothScrollToPosition(cp);

                if(layoutManager.getRxBannerIndicatorChangeListener() != null)
                    layoutManager.getRxBannerIndicatorChangeListener().onBannerSelected(cp);
                if(layoutManager.getRxBannerTitleChangeListener() != null)
                    layoutManager.getRxBannerTitleChangeListener().onBannerSelected(cp);
                return true;
            }
        } else {
            ViewPagerLayoutManager layoutManager = (ViewPagerLayoutManager) mRecyclerView.getLayoutManager();
            if (layoutManager == null) {
                return false;
            }
            RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
            if (adapter == null) {
                return false;
            }

            if (!layoutManager.isInfinite() &&
                    (layoutManager.mOffset == layoutManager.getMaxOffset()
                            || layoutManager.mOffset == layoutManager.getMinOffset())) {
                final int minFlingVelocity = mRecyclerView.getMinFlingVelocity();
                mGravityScroller.fling(0, 0, velocityX, velocityY,
                        Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (layoutManager.mOrientation == ViewPagerLayoutManager.VERTICAL
                        && Math.abs(velocityY) > minFlingVelocity * flingDamping) {
                    if(layoutManager.getRxBannerChangeListener() != null && layoutManager.mOffset == layoutManager.getMaxOffset()) {
                        layoutManager.getRxBannerChangeListener().onGuideFinished();
                    }
                }else if (layoutManager.mOrientation == ViewPagerLayoutManager.HORIZONTAL
                        && Math.abs(velocityX) > minFlingVelocity * flingDamping) {
                    if(layoutManager.getRxBannerChangeListener() != null && layoutManager.mOffset == layoutManager.getMaxOffset()) {
                        layoutManager.getRxBannerChangeListener().onGuideFinished();
                    }
                }else {
                    final int cp = layoutManager.getCurrentPosition();
                    mRecyclerView.smoothScrollToPosition(cp);

                    if(layoutManager.getRxBannerIndicatorChangeListener() != null)
                        layoutManager.getRxBannerIndicatorChangeListener().onBannerSelected(cp);
                    if(layoutManager.getRxBannerTitleChangeListener() != null)
                        layoutManager.getRxBannerTitleChangeListener().onBannerSelected(cp);
                }
            }
            final int minFlingVelocity = mRecyclerView.getMinFlingVelocity();




            mGravityScroller.fling(0, 0, velocityX, velocityY,Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (layoutManager.mOrientation == ViewPagerLayoutManager.VERTICAL
                    && Math.abs(velocityY) > minFlingVelocity * flingDamping && Math.abs(mGravityScroller.getFinalY()) > minFlingVelocity * flingDamping) {
                final int currentPosition = layoutManager.getCurrentPosition();
//                final int offsetPosition = mGravityScroller.getFinalY() * layoutManager.getDistanceRatio() > interval ? 1 : 0;
//                int cp = layoutManager.getReverseLayout() ? currentPosition - offsetPosition : currentPosition + offsetPosition;

                int offsetPosition = mGravityScroller.getFinalY() > 0 ? 1 : -1;
                int cp = currentPosition + offsetPosition;
                if (cp == layoutManager.getItemCount()) cp = 0;
                mRecyclerView.smoothScrollToPosition(cp);
                if(layoutManager.getRxBannerIndicatorChangeListener() != null)
                    layoutManager.getRxBannerIndicatorChangeListener().onBannerSelected(cp);
                if(layoutManager.getRxBannerTitleChangeListener() != null)
                    layoutManager.getRxBannerTitleChangeListener().onBannerSelected(cp);
                return true;
            } else if (layoutManager.mOrientation == ViewPagerLayoutManager.HORIZONTAL
                    && Math.abs(velocityX) > minFlingVelocity * flingDamping && Math.abs(mGravityScroller.getFinalX()) > minFlingVelocity * flingDamping) {
                final int currentPosition = layoutManager.getCurrentPosition();

//                int offsetPosition = Math.abs(mGravityScroller.getFinalX()) * layoutManager.getDistanceRatio() > interval ? 1 : 0;
                int offsetPosition = mGravityScroller.getFinalX() > 0 ? 1 : -1;

//                final int offsetPosition = Math.abs(mGravityScroller.getFinalX()) > minFlingVelocity ? 1 : 0;
//                int cp = layoutManager.getReverseLayout() ? currentPosition - offsetPosition : currentPosition + offsetPosition;

                int cp = currentPosition + offsetPosition;
                if (cp == layoutManager.getItemCount()) cp = 0;

                mRecyclerView.smoothScrollToPosition(cp);

                if(layoutManager.getRxBannerIndicatorChangeListener() != null)
                layoutManager.getRxBannerIndicatorChangeListener().onBannerSelected(cp);
                if(layoutManager.getRxBannerTitleChangeListener() != null)
                layoutManager.getRxBannerTitleChangeListener().onBannerSelected(cp);
                return true;
            }else {
                final int cp = layoutManager.getCurrentPosition();
                mRecyclerView.smoothScrollToPosition(cp);

                if(layoutManager.getRxBannerIndicatorChangeListener() != null)
                    layoutManager.getRxBannerIndicatorChangeListener().onBannerSelected(cp);
                if(layoutManager.getRxBannerTitleChangeListener() != null)
                    layoutManager.getRxBannerTitleChangeListener().onBannerSelected(cp);
                return true;

            }
        }
    }


    public void setFlingDamping(float flingDamping){
        this.flingDamping = flingDamping;
    }


}
