package cn.levey.bannerlib.base;

import android.content.res.Resources;
import android.util.TypedValue;

import cn.levey.bannerlib.data.RxBannerGlobalConfig;

/**
 * Created by Levey on 2018/4/9 10:57.
 * e-mail: m@levey.cn
 */

public class RxBannerUtil {

    public static final int DEFAULT_BG_COLOR = 1426063360; // color = #55000000

    public static int getPercentSize(int parentSize, int itemPercent){
        return (int) (parentSize * (itemPercent / 100f));
    }

    public static int getOrderType(RxBannerGlobalConfig.OrderType direction){
        switch (direction){
            case ASC:
                return 1;
            case DESC:
                return 2;
        }
        return 1;
    }

    public static RxBannerGlobalConfig.OrderType getOrder(int direction){
        switch (direction){
            case 1:
                return RxBannerGlobalConfig.OrderType.ASC;
            case 2:
                return RxBannerGlobalConfig.OrderType.DESC;
        }
        return RxBannerGlobalConfig.OrderType.ASC;
    }

    public static int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,Resources.getSystem().getDisplayMetrics());
    }

    public static int px2dp(float px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, Resources.getSystem().getDisplayMetrics());
    }
}
