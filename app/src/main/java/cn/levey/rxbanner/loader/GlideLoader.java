package cn.levey.rxbanner.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cn.levey.bannerlib.impl.RxBannerLoaderInterface;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Levey on 2018/4/2 17:41.
 * e-mail: m@levey.cn
 */

public class GlideLoader implements RxBannerLoaderInterface<ImageView> {

    @Override
    public void show(Context context, Object path, ImageView item) {
        RoundedCornersTransformation transformation = new RoundedCornersTransformation(50,20);
        Glide.with(context)
                .load(path)
                .apply(RequestOptions.centerCropTransform().transform(transformation))
                .into(item);
    }

    @Override
    public ImageView create(Context context) {
        return new ImageView(context);
    }
}
