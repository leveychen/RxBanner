package cn.levey.bannerlib.base;

import android.util.Log;

/**
 * Created by Levey on 2018/4/3 10:32.
 * e-mail: m@levey.cn
 */

public class RxBannerLogger {

    public static final String LOGGER_TAG = "RxBanner";

    public static void i(String s){
        if(RxBannerGlobalConfig.getInstance().isDebug()) {
            Log.i(LOGGER_TAG, s);
        }
    }

    public static void w(String s){
        if(RxBannerGlobalConfig.getInstance().isDebug()) {
            Log.w(LOGGER_TAG, s);
        }
    }

    public static void e(String s){
        if(RxBannerGlobalConfig.getInstance().isDebug()) {
            Log.e(LOGGER_TAG, s);
        }
    }
}
