package cn.levey.rxbanner.loader;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.levey.bannerlib.impl.RxBannerLoaderInterface;


/**
 * Created by Levey on 2018/4/2 17:41.
 * e-mail: m@levey.cn
 * @link   https://github.com/nostra13/Android-Universal-Image-Loader
 *
 */

public class UniversalImageLoader implements RxBannerLoaderInterface<ImageView> {
    @Override
    public void show(Context context, Object path, ImageView item) {
        ImageLoader.getInstance().displayImage((String) path,item);
    }

    @Override
    public ImageView create(Context context) {
        return new ImageView(context);
    }
}
