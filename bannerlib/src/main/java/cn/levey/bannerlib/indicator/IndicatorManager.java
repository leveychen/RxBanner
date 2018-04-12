package cn.levey.bannerlib.indicator;

import android.support.annotation.Nullable;
import cn.levey.bannerlib.indicator.animation.AnimationManager;
import cn.levey.bannerlib.indicator.animation.controller.ValueController;
import cn.levey.bannerlib.indicator.animation.data.Value;
import cn.levey.bannerlib.indicator.draw.DrawManager;
import cn.levey.bannerlib.indicator.draw.data.Indicator;

public class IndicatorManager implements ValueController.UpdateListener {

    private DrawManager drawManager;
    private AnimationManager animationManager;
    private Listener listener;

    interface Listener {
        void onIndicatorUpdated();
    }

    IndicatorManager(@Nullable Listener listener,Indicator indicator) {
        this.listener = listener;
        this.drawManager = new DrawManager(indicator);
        this.animationManager = new AnimationManager(drawManager.indicator(), this);
    }

    public AnimationManager animate() {
        return animationManager;
    }

    public Indicator indicator() {
        return drawManager.indicator();
    }

    public DrawManager drawer() {
        return drawManager;
    }

    @Override
    public void onValueUpdated(@Nullable Value value) {
        drawManager.updateValue(value);
        if (listener != null) {
            listener.onIndicatorUpdated();
        }
    }
}
