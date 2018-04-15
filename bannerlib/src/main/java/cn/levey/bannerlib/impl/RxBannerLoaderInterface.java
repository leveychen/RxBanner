package cn.levey.bannerlib.impl;

import android.content.Context;
import android.view.View;

import java.io.Serializable;

/**
 * Created by Levey on 2018/4/2 15:52.
 * e-mail: m@levey.cn
 */

public interface RxBannerLoaderInterface<T extends View> extends Serializable {
    void show(Context context, Object path, T item);
    T create(Context context);
}