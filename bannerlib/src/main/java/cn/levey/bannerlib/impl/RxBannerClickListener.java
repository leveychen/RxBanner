package cn.levey.bannerlib.impl;

/**
 * Created by Levey on 2018/4/9 17:15.
 * e-mail: m@levey.cn
 */

public interface RxBannerClickListener {
    void onItemClick(int position, Object data);
    void onItemLongClick(int position, Object data);
}
