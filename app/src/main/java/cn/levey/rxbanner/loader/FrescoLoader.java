package cn.levey.rxbanner.loader;

import android.content.Context;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.levey.bannerlib.impl.RxBannerLoaderInterface;

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
//        SimpleDraweeView sdv = new SimpleDraweeView(context);
//        sdv.setHierarchy(hierarchy);
        return new SimpleDraweeView(context);
    }
}
