package cn.levey.bannerlib.indicator.animation;

import android.support.annotation.NonNull;
import cn.levey.bannerlib.indicator.animation.controller.AnimationController;
import cn.levey.bannerlib.indicator.animation.controller.ValueController;
import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;

public class AnimationManager {

    private AnimationController animationController;

    public AnimationManager(@NonNull IndicatorConfig indicatorConfig, @NonNull ValueController.UpdateListener listener) {
        this.animationController = new AnimationController(indicatorConfig, listener);
    }

    public void basic() {
        if (animationController != null) {
            animationController.end();
            animationController.basic();
        }
    }

    public void interactive(float progress) {
        if (animationController != null) {
            animationController.interactive(progress);
        }
    }

    public void end() {
        if (animationController != null) {
            animationController.end();
        }
    }
}
