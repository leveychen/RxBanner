package cn.levey.bannerlib.impl;

import android.view.View;

/**
 * Created by Levey on 2018/4/9 17:15.
 * e-mail: m@levey.cn
 */

public interface RxBannerClickListener {
    void onItemClick(View view,int position);
    void onItemLongClick(View view,int position);
}
