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
    private RxBannerConfig.DirectionType direction;

    public AutoPlaySnapHelper(int timeInterval, RxBannerConfig.DirectionType direction) {
        checkTimeInterval(timeInterval);
        checkDirection(direction);
        handler = new WeakHandler();
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
                    ((ViewPagerLayoutManager) layoutManager).onPageChangeListener);

            ((ViewPagerLayoutManager) layoutManager).setInfinite(true);

            autoPlayRunnable = new Runnable() {
                @Override
                public void run() {
                    final int currentPosition =
                            ((ViewPagerLayoutManager) layoutManager).getCurrentPosition();
                    mRecyclerView.smoothScrollToPosition(direction == RxBannerConfig.DirectionType.ASC ? currentPosition + 1 : currentPosition - 1);
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
    }

    public void pause() {
        if (runnableAdded) {
            handler.removeCallbacks(autoPlayRunnable);
            runnableAdded = false;
        }
    }

    public void start() {
        if (!runnableAdded) {
            handler.postDelayed(autoPlayRunnable, timeInterval);
            runnableAdded = true;
        }
    }

    void setTimeInterval(int timeInterval) {
        checkTimeInterval(timeInterval);

    }

    void setDirection(RxBannerConfig.DirectionType direction) {
        checkDirection(direction);
    }

    private void checkDirection(RxBannerConfig.DirectionType direction) {
        if (direction != RxBannerConfig.DirectionType.DESC && direction != RxBannerConfig.DirectionType.ASC) {
            throw new IllegalArgumentException(RxBannerLogger.LOGGER_TAG + " direction should be one of asc or desc");
        }else {
            this.direction = direction;
            //reset
            if(handler != null){
                pause();
                start();
            }
        }
    }

    private void checkTimeInterval(int timeInterval) {
        if (timeInterval <= 0) {
            throw new IllegalArgumentException(RxBannerLogger.LOGGER_TAG + " time interval should greater than 0");
        }else {
            this.timeInterval = timeInterval;
            if(handler != null){
                pause();
                start();
            }
        }
    }
}
