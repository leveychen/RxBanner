package cn.levey.bannerlib.indicator;

import android.support.annotation.Nullable;
import cn.levey.bannerlib.indicator.animation.AnimationManager;
import cn.levey.bannerlib.indicator.animation.controller.ValueController;
import cn.levey.bannerlib.indicator.animation.data.Value;
import cn.levey.bannerlib.indicator.draw.DrawManager;
import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;

public class IndicatorManager implements ValueController.UpdateListener {

    private DrawManager drawManager;
    private AnimationManager animationManager;
    private Listener listener;

    public interface Listener {
        void onIndicatorUpdated();
    }

    public IndicatorManager(@Nullable Listener listener,IndicatorConfig indicatorConfig) {
        this.listener = listener;
        this.drawManager = new DrawManager(indicatorConfig);
        this.animationManager = new AnimationManager(drawManager.indicator(), this);
    }

    public AnimationManager animate() {
        return animationManager;
    }

    public IndicatorConfig indicator() {
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
