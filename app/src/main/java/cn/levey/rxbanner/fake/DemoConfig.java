package cn.levey.rxbanner.fake;

import java.io.Serializable;

import cn.levey.bannerlib.data.RxBannerConfig;

/**
 * Created by Levey on 2018/4/19 17:26.
 * e-mail: m@levey.cn
 *
 *   demo 演示用途，继承RxBannerConfig 并且构建自己需要的参数，方便使用，一般情况下不需要如此操作，直接
 *
 *   RxBannerConfig config = banner.getConfig()  即可
 *
 */
public class DemoConfig extends RxBannerConfig implements Serializable {
    private boolean isRoundAsCircle = false;
    private float cornersRadius = 30f;

    public boolean isRoundAsCircle() {
        return isRoundAsCircle;
    }

    public void setRoundAsCircle(boolean roundAsCircle) {
        isRoundAsCircle = roundAsCircle;
    }

    public float getCornersRadius() {
        return cornersRadius;
    }

    public void setCornersRadius(float cornersRadius) {
        this.cornersRadius = cornersRadius;
    }
}
