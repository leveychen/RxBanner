package cn.levey.bannerlib.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by Levey on 2018/4/2 16:15.
 * e-mail: m@levey.cn
 */

public class RxBannerImageViewLoader implements RxBannerLoaderInterface<ImageView> {
    @Override
    public void show(Context context, Object path, ImageView item) {
        if(path instanceof Integer){
            item.setImageResource((int)path);
            return;
        }
        if(path instanceof Bitmap){
            item.setImageBitmap((Bitmap) path);
            return;
        }
        if(path instanceof Drawable){
            item.setImageDrawable((Drawable) path);
            return;
        }
        if(path instanceof String){
            item.setImageURI(Uri.parse((String)path));
        }
    }

    @Override
    public ImageView create(Context context) {
        return new ImageView(context);
    }

}
