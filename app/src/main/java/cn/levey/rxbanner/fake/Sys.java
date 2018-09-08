package cn.levey.rxbanner.fake;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import cn.levey.bannerlib.indicator.animation.type.AnimationType;

/**
 * Created by Levey on 2018/4/11 14:10.
 * e-mail: m@levey.cn
 */
public class Sys {

    public static final String BANNER_DATA = "BANNER_DATA";
    public static final String CORNERS_RADIUS = "CORNERS_RADIUS";


    public static void toast(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }


    public static int getGravity(Integer[] which) {
        int value = 0;
        for (Integer aWhich : which) {
            switch (aWhich) {
                case 0:
                    value |= Gravity.CENTER;
                    break;
                case 1:
                    value |= Gravity.TOP;
                    break;
                case 2:
                    value |= Gravity.BOTTOM;
                    break;
                case 3:
                    value |= Gravity.START;
                    break;
                case 4:
                    value |= Gravity.END;
                    break;
                case 5:
                    value |= Gravity.CENTER_HORIZONTAL;
                    break;
                case 6:
                    value |= Gravity.CENTER_VERTICAL;
                    break;
            }
        }
        return value;
    }


    public static AnimationType getAnimationType(int index) {
        switch (index) {
            case 0:
                return AnimationType.NONE;
            case 1:
                return AnimationType.COLOR;
            case 2:
                return AnimationType.SCALE;
            case 3:
                return AnimationType.WORM;
            case 4:
                return AnimationType.SLIDE;
            case 5:
                return AnimationType.FILL;
            case 6:
                return AnimationType.THIN_WORM;
            case 7:
                return AnimationType.DROP;
            case 8:
                return AnimationType.SWAP;
            case 9:
                return AnimationType.SCALE_DOWN;
            case 10:
                return AnimationType.NUMERIC;
            case 11:
                return AnimationType.NUMERIC_CIRCLE;
            case 12:
                return AnimationType.CUSTOM;
        }

        return AnimationType.NONE;
    }

    public static String getAnimationTypeStr(int index) {
        return getAnimationType(index).name();
    }

}
