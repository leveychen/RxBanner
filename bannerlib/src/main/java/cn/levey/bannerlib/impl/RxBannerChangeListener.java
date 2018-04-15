package cn.levey.bannerlib.impl;

/**
 * Created by Levey on 2018/4/10 16:40.
 * e-mail: m@levey.cn
 */

public abstract class RxBannerChangeListener {
    public void onBannerSelected(int position){}
    public void onBannerScrollStateChanged(int state){}
    public void onGuideFinished(){}
}
