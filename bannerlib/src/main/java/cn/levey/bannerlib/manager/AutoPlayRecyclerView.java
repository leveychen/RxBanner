package cn.levey.bannerlib.manager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import cn.levey.bannerlib.base.RxBannerConfig;
import cn.levey.bannerlib.base.RxBannerLogger;

/**
 * An implement of {@link RecyclerView} which support auto play.
 */

public class AutoPlayRecyclerView extends RecyclerView {

    private boolean autoPlay = true;
    private AutoPlaySnapHelper autoPlaySnapHelper;

    public AutoPlayRecyclerView(Context context) {
        this(context, null);
    }

    public AutoPlayRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPlayRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        autoPlaySnapHelper = new AutoPlaySnapHelper(RxBannerConfig.getInstance().getTimeInterval(), RxBannerConfig.getInstance().getOrderType());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(isAutoPlay()) {
            switch (ev.getAction()) {
                    //按下暂停轮播
                case MotionEvent.ACTION_DOWN:
                    if (autoPlaySnapHelper != null) {
                        autoPlaySnapHelper.pause();
                    }
                    break;
                    //抬起继续轮播,在ScrollView里按住然后滑出Banner时会无法监听 ACTION_UP 事件，所以同时需要监听 ACTION_CANCEL 事件
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (autoPlaySnapHelper != null) {
                        autoPlaySnapHelper.start();
                    }
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void start() {
        if(isAutoPlay()) {
            autoPlaySnapHelper.start();
        }else {
            autoPlaySnapHelper.pause();
        }
    }

    public void pause() {
        autoPlaySnapHelper.pause();
    }

    public boolean isRunning(){
       return autoPlaySnapHelper.isRunning();
    }

    public void setDirection(RxBannerConfig.OrderType direction){
        autoPlaySnapHelper.setDirection(direction);
    }

    public void setTimeInterval(int timeInterval){
        autoPlaySnapHelper.setTimeInterval(timeInterval);
    }

    public void setViewPaperMode(boolean viewPaperMode) {
        autoPlaySnapHelper.setViewPaperMode(viewPaperMode);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        autoPlaySnapHelper.attachToRecyclerView(this);
    }


    public boolean isAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        if(!autoPlay){
            autoPlaySnapHelper.pause();
        }
        this.autoPlay = autoPlay;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        autoPlaySnapHelper.destroyCallbacks();
    }
}
