package cn.levey.bannerlib.impl;

/**
 * Created by Levey on 2018/4/10 16:40.
 * e-mail: m@levey.cn
 */

public interface RxBannerChangeListener {
     void onBannerSelected(int position);
     void onBannerScrollStateChanged(int state);
     void onGuideFinished();
}
