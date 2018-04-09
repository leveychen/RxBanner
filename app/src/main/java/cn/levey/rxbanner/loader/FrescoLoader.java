package cn.levey.rxbanner.loader;

import android.content.Context;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import cn.levey.bannerlib.impl.RxBannerLoaderInterface;
import cn.levey.rxbanner.R;

/**
 * Created by Levey on 2018/4/2 17:42.
 * e-mail: m@levey.cn
 */

public class FrescoLoader implements RxBannerLoaderInterface<SimpleDraweeView> {

    @Override
    public void show(Context context, Object path, SimpleDraweeView item) {
        if(path instanceof Integer){
            item.setImageResource((int)path);
            return;
        }
        item.setImageURI((String) path);
//        use controller
//        item.setController(controller);
    }

    @Override
    public SimpleDraweeView create(Context context) {
//        set hierarchy
        SimpleDraweeView sdv = new SimpleDraweeView(context);
        sdv.setHierarchy(getRoundHierarchy(context));
//        return new SimpleDraweeView(context);
        return sdv;
    }


    private GenericDraweeHierarchy getRoundHierarchy(Context context) {
        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(context.getResources());
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(20f);
       // roundingParams.setRoundAsCircle(true);
        builder.setRoundingParams(roundingParams);
        return builder
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setPlaceholderImage(R.mipmap.ic_launcher)
                .build();
    }
}
