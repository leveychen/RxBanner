package cn.levey.bannerlib.utils;

import cn.levey.bannerlib.impl.RxBannerLoaderInterface;
import cn.levey.bannerlib.manager.AutoPlaySnapHelper;

/**
 * Created by Levey on 2018/4/3 10:59.
 * e-mail: m@levey.cn
 */

public class RxBannerManager {

    private boolean debug = false;


    private static final RxBannerManager ourInstance = new RxBannerManager();

    private RxBannerManager() {

    }
    public static RxBannerManager getInstance() {
        return ourInstance;
    }

    private RxBannerLoaderInterface mLoader;

    private int timeInterval = 2000;
    private int direction = AutoPlaySnapHelper.RIGHT;

    public int getTimeInterval() {
        return timeInterval;
    }

    public RxBannerManager setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
        return this;
    }

    public int getDirection() {
        return direction;
    }

    public RxBannerManager setDirection(int direction) {
        this.direction = direction;
        return this;
    }

    public boolean isDebug() {
        return debug;
    }

    public RxBannerManager setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public RxBannerLoaderInterface getLoader(){
        if(mLoader == null){
            throw new NullPointerException(
                    "\n\t\t\tPlease set a image loader implements RxBannerLoaderInterface<T>"
                            + "\n\t\t\tGlobal: RxBannerManager.getInstance().setLoader()"
                            + "\n\t\t\tLocal: banner.setLoader()"
                            + "\n\t\t\tDefault ImageView loader: new RxBannerImageViewLoader()"
            );
        }
        return mLoader;
    }

    public RxBannerManager setLoader(RxBannerLoaderInterface mLoader) {
        this.mLoader = mLoader;
        return this;
    }

}
