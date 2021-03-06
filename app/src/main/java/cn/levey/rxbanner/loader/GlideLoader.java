package cn.levey.rxbanner.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cn.levey.bannerlib.impl.RxBannerLoaderInterface;

/**
 * Created by Levey on 2018/4/2 17:41.
 * e-mail: m@levey.cn
 * @link https://github.com/bumptech/glide
 */

public class GlideLoader implements RxBannerLoaderInterface<ImageView> {

    @Override
    public void show(Context context, Object path, ImageView item) {
        Glide.with(context)
                .load(path)
                .apply(RequestOptions.centerCropTransform())
                .into(item);
    }

    @Override
    public ImageView create(Context context) {
        return new ImageView(context);
    }
}
