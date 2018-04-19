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
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import cn.levey.bannerlib.impl.RxBannerLoaderInterface;

/**
 * Created by Levey on 2018/4/2 17:42.
 * e-mail: m@levey.cn
 * @link https://github.com/facebook/fresco
 */

public class FrescoLoader implements RxBannerLoaderInterface<SimpleDraweeView> {


    public void setCornersRadius(float cornersRadius) {
        this.cornersRadius = cornersRadius;
    }

    private float cornersRadius = 30f;
    private boolean isRoundAsCircle = false;

    public void setRoundAsCircle(boolean roundAsCircle) {
        isRoundAsCircle = roundAsCircle;
    }

    public FrescoLoader(){

    }

    public FrescoLoader(float cornersRadius, boolean isRoundAsCircle){
        this.cornersRadius = cornersRadius;
        this.isRoundAsCircle = isRoundAsCircle;
    }

    @Override
    public void show(Context context, Object path, SimpleDraweeView item) {
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
        ResizeOptions resizeOptions = new ResizeOptions(400,400*16/9);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setRotationOptions(RotationOptions.autoRotateAtRenderTime())
                .setResizeOptions(resizeOptions)
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
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(cornersRadius);
        roundingParams.setRoundAsCircle(isRoundAsCircle);
        builder.setRoundingParams(roundingParams);
        return builder
                .setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP)
                .build();
    }
}
