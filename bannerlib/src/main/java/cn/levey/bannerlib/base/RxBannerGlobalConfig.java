package cn.levey.bannerlib.base;

import android.widget.LinearLayout;

import cn.levey.bannerlib.impl.RxBannerLoaderInterface;
import cn.levey.bannerlib.impl.RxBannerScrollStateChangedListener;

/**
 * Created by Levey on 2018/4/3 10:59.
 * e-mail: m@levey.cn
 */

public class RxBannerGlobalConfig {

    public enum OrderType {
        DESC,
        ASC
    }


    private boolean debug = false;


    private static final RxBannerGlobalConfig ourInstance = new RxBannerGlobalConfig();

    private RxBannerGlobalConfig() {

    }
    public static RxBannerGlobalConfig getInstance() {
        return ourInstance;
    }

    private RxBannerLoaderInterface mLoader;
    private RxBannerScrollStateChangedListener scrollStateChangedListener;

    private int timeInterval = 5000;
    private OrderType orderType = OrderType.ASC;
    private int orientation = LinearLayout.HORIZONTAL;

    public int getOrientation() {
        return orientation;
    }

    public RxBannerGlobalConfig setOrientation(int orientation) {
        if(orientation == LinearLayout.HORIZONTAL  || orientation == LinearLayout.VERTICAL){
            this.orientation = orientation;
        }else {
            throw new IllegalArgumentException(RxBannerLogger.LOGGER_TAG + " orientation should be one of HORIZONTAL or VERTICAL");
        }
        return this;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public RxBannerGlobalConfig setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
        return this;
    }

    public OrderType getOrderType() {
        return orderType;
    }


    public RxBannerGlobalConfig setOrderType(OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    public boolean isDebug() {
        return debug;
    }

    public RxBannerGlobalConfig setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public RxBannerLoaderInterface getLoader(){
        if(mLoader == null){
            throw new NullPointerException(
                    "\n\t\t\tPlease set a image loader implements RxBannerLoaderInterface<T>"
                            + "\n\t\t\tDefault ImageView loader: new RxBannerImageViewLoader()"
            );
        }
        return mLoader;
    }

    public RxBannerGlobalConfig setLoader(RxBannerLoaderInterface mLoader) {
        this.mLoader = mLoader;
        return this;
    }

    public RxBannerScrollStateChangedListener getScrollStateChangedListener() {
        return scrollStateChangedListener;
    }

    public RxBannerGlobalConfig setScrollStateChangedListener(RxBannerScrollStateChangedListener scrollStateChangedListener) {
        this.scrollStateChangedListener = scrollStateChangedListener;
        return this;
    }
}
