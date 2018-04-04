package cn.levey.bannerlib.base;

import cn.levey.bannerlib.impl.RxBannerLoaderInterface;

/**
 * Created by Levey on 2018/4/3 10:59.
 * e-mail: m@levey.cn
 */

public class RxBannerConfig {

    public enum DirectionType{
        DESC,
        ASC
    }


    private boolean debug = false;


    private static final RxBannerConfig ourInstance = new RxBannerConfig();

    private RxBannerConfig() {

    }
    public static RxBannerConfig getInstance() {
        return ourInstance;
    }

    private RxBannerLoaderInterface mLoader;

    private int timeInterval = 5000;
    private DirectionType direction = DirectionType.ASC;

    public int getTimeInterval() {
        return timeInterval;
    }

    public RxBannerConfig setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
        return this;
    }

    public DirectionType getDirection() {
        return direction;
    }

    public RxBannerConfig setDirection(DirectionType direction) {
        this.direction = direction;
        return this;
    }

    public boolean isDebug() {
        return debug;
    }

    public RxBannerConfig setDebug(boolean debug) {
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

    public RxBannerConfig setLoader(RxBannerLoaderInterface mLoader) {
        this.mLoader = mLoader;
        return this;
    }

}
