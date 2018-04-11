package cn.levey.bannerlib.manager;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import cn.levey.bannerlib.base.RxBannerConfig;
import cn.levey.bannerlib.base.RxBannerLogger;
import cn.levey.bannerlib.base.WeakHandler;


public class AutoPlaySnapHelper extends CenterSnapHelper {


    private WeakHandler handler;
    private int timeInterval;
    private Runnable autoPlayRunnable;
    private boolean runnableAdded;
    private RxBannerConfig.OrderType direction;
    private boolean viewPaperMode = true;


    AutoPlaySnapHelper(int timeInterval, RxBannerConfig.OrderType direction) {
        checkTimeInterval(timeInterval);
        checkDirection(direction);
        handler = new WeakHandler();
    }

    void setViewPaperMode(boolean viewPaperMode) {
        this.viewPaperMode = viewPaperMode;
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        if (mRecyclerView == recyclerView) {
            return; // nothing to do
        }
        if (mRecyclerView != null) {
            destroyCallbacks();
        }
        mRecyclerView = recyclerView;
        if (mRecyclerView != null) {
            final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            if (!(layoutManager instanceof ViewPagerLayoutManager)) return;


            setupCallbacks();
            mGravityScroller = new Scroller(mRecyclerView.getContext(),
                    new DecelerateInterpolator());


            snapToCenterView((ViewPagerLayoutManager) layoutManager,
                    ((ViewPagerLayoutManager) layoutManager).onBannerChangeListener,((ViewPagerLayoutManager) layoutManager).onInnerBannerChangeListener);


            autoPlayRunnable = new Runnable() {
                @Override
                public void run() {
                    final int currentPosition =
                            ((ViewPagerLayoutManager) layoutManager).getCurrentPosition();
                    mRecyclerView.smoothScrollToPosition(direction == RxBannerConfig.OrderType.ASC ? currentPosition + 1 : currentPosition - 1);
                    handler.postDelayed(autoPlayRunnable, timeInterval);
                }
            };
            handler.postDelayed(autoPlayRunnable, timeInterval);
            runnableAdded = true;
        }
    }

    @Override
    void destroyCallbacks() {
        super.destroyCallbacks();
        if (runnableAdded) {
            handler.removeCallbacks(autoPlayRunnable);
            runnableAdded = false;

        }
        handler = null;
    }

    void pause() {
        if (runnableAdded) {
            handler.removeCallbacks(autoPlayRunnable);
            runnableAdded = false;
        }
    }

    void start() {
        if (!runnableAdded) {
            handler.postDelayed(autoPlayRunnable, timeInterval);
            runnableAdded = true;
        }
    }

    boolean isRunning(){
        return runnableAdded;
    }

    void setTimeInterval(int timeInterval) {
        checkTimeInterval(timeInterval);

    }

    void setDirection(RxBannerConfig.OrderType direction) {
        checkDirection(direction);
    }

    private void checkDirection(RxBannerConfig.OrderType direction) {
        if (direction != RxBannerConfig.OrderType.DESC && direction != RxBannerConfig.OrderType.ASC) {
            throw new IllegalArgumentException(RxBannerLogger.LOGGER_TAG + ": direction should be one of ASC or DESC");
        }else {
            this.direction = direction;
            //reset
            if(handler != null && autoPlayRunnable != null){
                pause();
                start();
            }
        }
    }

    private void checkTimeInterval(int timeInterval) {
        if (timeInterval <= 0) {
            throw new IllegalArgumentException(RxBannerLogger.LOGGER_TAG + ": time interval should greater than 0");
        }else {
            this.timeInterval = timeInterval;
            if(handler != null &&  autoPlayRunnable != null){
                pause();
                start();
            }
        }
    }

    @Override
    public boolean onFling(int velocityX, int velocityY) {
        if(!viewPaperMode){
            ViewPagerLayoutManager layoutManager = (ViewPagerLayoutManager) mRecyclerView.getLayoutManager();
            if (layoutManager == null) {
                return false;
            }
            RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
            if (adapter == null) {
                return false;
            }

            if (!layoutManager.getInfinite() &&
                    (layoutManager.mOffset == layoutManager.getMaxOffset()
                            || layoutManager.mOffset == layoutManager.getMinOffset())) {
                return false;
            }

            final int minFlingVelocity = mRecyclerView.getMinFlingVelocity();
            mGravityScroller.fling(0, 0, velocityX, velocityY,
                    Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (layoutManager.mOrientation == ViewPagerLayoutManager.VERTICAL
                    && Math.abs(velocityY) > minFlingVelocity) {
                final int currentPosition = layoutManager.getCurrentPosition();
                final int offsetPosition = (int) (mGravityScroller.getFinalY() /
                        layoutManager.mInterval / layoutManager.getDistanceRatio());
                onInnerBannerChangeListener.onInnerBannerSelected(layoutManager.getReverseLayout() ?
                        currentPosition - offsetPosition : currentPosition + offsetPosition);
                mRecyclerView.smoothScrollToPosition(layoutManager.getReverseLayout() ?
                        currentPosition - offsetPosition : currentPosition + offsetPosition);
                return true;
            } else if (layoutManager.mOrientation == ViewPagerLayoutManager.HORIZONTAL
                    && Math.abs(velocityX) > minFlingVelocity) {
                final int currentPosition = layoutManager.getCurrentPosition();
                final int offsetPosition = (int) (mGravityScroller.getFinalX() /
                        layoutManager.mInterval / layoutManager.getDistanceRatio());
                mRecyclerView.smoothScrollToPosition(layoutManager.getReverseLayout() ?
                        currentPosition - offsetPosition : currentPosition + offsetPosition);
                return true;
            }
        }else {
            ViewPagerLayoutManager layoutManager = (ViewPagerLayoutManager) mRecyclerView.getLayoutManager();
            if (layoutManager == null) {
                return false;
            }
            RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
            if (adapter == null) {
                return false;
            }

            if (!layoutManager.getInfinite() &&
                    (layoutManager.mOffset == layoutManager.getMaxOffset()
                            || layoutManager.mOffset == layoutManager.getMinOffset())) {
                return false;
            }

            final int minFlingVelocity = mRecyclerView.getMinFlingVelocity();
            mGravityScroller.fling(0, 0, velocityX, velocityY,
                    Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (layoutManager.mOrientation == ViewPagerLayoutManager.VERTICAL
                    && Math.abs(velocityY) > minFlingVelocity) {
                final int currentPosition = layoutManager.getCurrentPosition();
                final int offsetPosition = mGravityScroller.getFinalY() * layoutManager.getDistanceRatio() > layoutManager.mInterval ? 1 : 0;
                mRecyclerView.smoothScrollToPosition(layoutManager.getReverseLayout() ?
                        currentPosition - offsetPosition : currentPosition + offsetPosition);
                return true;
            } else if (layoutManager.mOrientation == ViewPagerLayoutManager.HORIZONTAL
                    && Math.abs(velocityX) > minFlingVelocity) {
                final int currentPosition = layoutManager.getCurrentPosition();
                final int offsetPosition = mGravityScroller.getFinalX() * layoutManager.getDistanceRatio() > layoutManager.mInterval ? 1 : 0;
                mRecyclerView.smoothScrollToPosition(layoutManager.getReverseLayout() ?
                        currentPosition - offsetPosition : currentPosition + offsetPosition);
                return true;
            }
        }

        return true;
    }

}
