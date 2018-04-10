package cn.levey.rxbanner.loader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import cn.levey.bannerlib.impl.RxBannerLoaderInterface;


/**
 * Created by Levey on 2018/4/2 17:41.
 * e-mail: m@levey.cn
 * @link https://github.com/square/picasso
 */

public class PicassoLoader implements RxBannerLoaderInterface<ImageView> {
    @Override
    public void show(Context context, Object path, ImageView item) {
        if (path instanceof Integer) {
            Picasso.get().load((int) path).into(item);
            return;
        }
        if (path instanceof String) {
            Picasso.get().load((String) path).into(item);
            return;
        }
        if (path instanceof Uri) {
            Picasso.get().load((Uri) path).into(item);
            return;
        }
        if (path instanceof File) {
            Picasso.get().load((File) path).into(item);
        }
    }

    @Override
    public ImageView create(Context context) {
        return new ImageView(context);
    }
}
