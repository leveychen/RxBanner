package cn.levey.bannerlib.manager;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import cn.levey.bannerlib.data.RxBannerGlobalConfig;
import cn.levey.bannerlib.base.RxBannerLogger;
import cn.levey.bannerlib.base.WeakHandler;


public class AutoPlaySnapHelper extends CenterSnapHelper {


    private WeakHandler handler;
    private int timeInterval;
    private Runnable autoPlayRunnable;
    private boolean runnableAdded;
    private RxBannerGlobalConfig.OrderType orderType;



    AutoPlaySnapHelper(int timeInterval, RxBannerGlobalConfig.OrderType orderType) {
        checkTimeInterval(timeInterval);
        checkDirection(orderType);
        handler = new WeakHandler();
    }

    public void setViewPaperMode(boolean viewPaperMode) {
        setPaperMode(viewPaperMode);
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
                    ((ViewPagerLayoutManager) layoutManager).getRxBannerChangeListener());


            autoPlayRunnable = new Runnable() {
                @Override
                public void run() {

                    if(!((ViewPagerLayoutManager) layoutManager).isAutoPlay()){
                        pause();
                        return;
                    }
                    final ViewPagerLayoutManager viewPagerLayoutManager = (ViewPagerLayoutManager) layoutManager;

                    final int currentPosition = viewPagerLayoutManager.getCurrentPosition();
                    int cp = orderType == RxBannerGlobalConfig.OrderType.ASC ? currentPosition + 1 : currentPosition - 1;
                    if(cp == -1) {
                        if(!((ViewPagerLayoutManager) layoutManager).isInfinite()) {
                            if(viewPagerLayoutManager.getRxBannerChangeListener() != null){
                                if(cp == viewPagerLayoutManager.getItemCount()) viewPagerLayoutManager.getRxBannerChangeListener().onGuideFinished();
                            }
                            pause();
                            return;
                        }
                        cp = layoutManager.getItemCount()  - 1;
                    }else if(cp == layoutManager.getItemCount()){
                        if(!((ViewPagerLayoutManager) layoutManager).isInfinite()) {
                            if(viewPagerLayoutManager.getRxBannerChangeListener() != null){
                                if(cp == viewPagerLayoutManager.getItemCount()) viewPagerLayoutManager.getRxBannerChangeListener().onGuideFinished();
                            }
                            pause();
                            return;
                        }
                        cp = 0;
                    }
                    if(viewPagerLayoutManager.getRxBannerChangeListener() != null){
                        if(cp == viewPagerLayoutManager.getItemCount()) viewPagerLayoutManager.getRxBannerChangeListener().onGuideFinished();
                    }
                    if(viewPagerLayoutManager.getRxBannerIndicatorChangeListener() != null)
                        viewPagerLayoutManager.getRxBannerIndicatorChangeListener().onBannerSelected(cp);
                    if(viewPagerLayoutManager.getRxBannerTitleChangeListener() != null)
                        viewPagerLayoutManager.getRxBannerTitleChangeListener().onBannerSelected(cp);
                    mRecyclerView.smoothScrollToPosition(cp);
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

    void setOrderType(RxBannerGlobalConfig.OrderType orderType) {
        checkDirection(orderType);
    }

    private void checkDirection(RxBannerGlobalConfig.OrderType direction) {
        if (direction != RxBannerGlobalConfig.OrderType.DESC && direction != RxBannerGlobalConfig.OrderType.ASC) {
            throw new IllegalArgumentException(RxBannerLogger.LOGGER_TAG + ": orderType should be one of ASC or DESC");
        }else {
            this.orderType = direction;
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
}
