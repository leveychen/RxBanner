package cn.levey.rxbanner.fake;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Levey on 2018/4/11 14:10.
 * e-mail: m@levey.cn
 */
public class Sys {



    public static final String BANNER_DATA = "BANNER_DATA";


    public static void toast(Context context, String s){
        Toast.makeText(context, s , Toast.LENGTH_LONG).show();
    }


    public static int getGravity(Integer[] which){
        int value = Gravity.START;
        for (Integer aWhich : which) {
            switch (aWhich) {
                case 0:
                    value = value | Gravity.CENTER;
                    break;
                case 1:
                    value = value | Gravity.TOP;
                    break;
                case 2:
                    value = value | Gravity.BOTTOM;
                    break;
                case 3:
                    value = value | Gravity.START;
                    break;
                case 4:
                    value = value | Gravity.END;
                    break;
                case 5:
                    value = value | Gravity.CENTER_HORIZONTAL;
                    break;
                case 6:
                    value = value | Gravity.CENTER_VERTICAL;
                    break;
            }
        }
        return value;
    }


}
