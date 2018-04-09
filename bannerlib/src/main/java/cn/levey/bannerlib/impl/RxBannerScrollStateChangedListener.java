package cn.levey.bannerlib.impl;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Levey on 2018/4/9 15:36.
 * e-mail: m@levey.cn
 */

public interface RxBannerScrollStateChangedListener {
    void onScrollStateChanged(RecyclerView recyclerView, int newState);
}
