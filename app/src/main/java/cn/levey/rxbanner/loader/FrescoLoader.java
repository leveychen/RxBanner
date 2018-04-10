package cn.levey.rxbanner.loader;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import cn.levey.bannerlib.impl.RxBannerLoaderInterface;
import cn.levey.rxbanner.R;

/**
 * Created by Levey on 2018/4/2 17:42.
 * e-mail: m@levey.cn
 */

public class FrescoLoader implements RxBannerLoaderInterface<SimpleDraweeView> {

    @Override
    public void show(Context context, Object path, SimpleDraweeView item) {
        //该方法用于加载resource资源
        if(path instanceof Integer){
            item.setImageResource((int)path);
            return;
        }
//        item.setImageURI((String) path);

//        set controller
        item.setController(getController((String)path));
    }

    @Override
    public SimpleDraweeView create(Context context) {
//        set hierarchy
        SimpleDraweeView sdv = new SimpleDraweeView(context);
        sdv.setHierarchy(getRoundHierarchy(context));
//        return new SimpleDraweeView(context);
        return sdv;
    }


    private DraweeController getController(String url) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setRotationOptions(RotationOptions.autoRotateAtRenderTime())
                .build();
        return Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(url))
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .build();
    }


    private GenericDraweeHierarchy getRoundHierarchy(Context context) {
        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(context.getResources());
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(20f);
        //roundingParams.setRoundAsCircle(true);
        builder.setRoundingParams(roundingParams);
        return builder
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setPlaceholderImage(R.mipmap.ic_launcher)
                .build();
    }
}
