package cn.levey.bannerlib.utils;

import android.util.Log;

/**
 * Created by Levey on 2018/4/3 10:32.
 * e-mail: m@levey.cn
 */

public class RxBannerLogger {

    private static final String LOGGER_TAG = "RxBanner";

    public static void d(String s){
        if(RxBannerManager.getInstance().isDebug()) {
            Log.d(LOGGER_TAG, s);
        }
    }

    public static void i(String s){
        if(RxBannerManager.getInstance().isDebug()) {
            Log.i(LOGGER_TAG, s);
        }
    }

    public static void w(String s){
        if(RxBannerManager.getInstance().isDebug()) {
            Log.w(LOGGER_TAG, s);
        }
    }

    public static void e(String s){
        if(RxBannerManager.getInstance().isDebug()) {
            Log.e(LOGGER_TAG, s);
        }
    }
}
