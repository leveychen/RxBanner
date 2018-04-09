package cn.levey.bannerlib.base;

/**
 * Created by Levey on 2018/4/9 10:57.
 * e-mail: m@levey.cn
 */

public class BannerUtil {

    public static int getPercentSize(int parentSize, int itemPercent){
        return (int) (parentSize * (itemPercent / 100f));
    }

    public static int getOrderType(RxBannerConfig.OrderType direction){
        switch (direction){
            case ASC:
                return 1;
            case DESC:
                return 2;
        }
        return 1;
    }

    public static RxBannerConfig.OrderType getOrder(int direction){
        switch (direction){
            case 1:
                return RxBannerConfig.OrderType.ASC;
            case 2:
                return RxBannerConfig.OrderType.DESC;
        }
        return RxBannerConfig.OrderType.ASC;
    }

}
