package cn.levey.bannerlib.indicator.draw.drawer.type;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import cn.levey.bannerlib.indicator.animation.data.Value;
import cn.levey.bannerlib.indicator.animation.data.type.ScaleAnimationValue;
import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;

public class ScaleDrawer extends BaseDrawer {

    public ScaleDrawer(@NonNull Paint paint, @NonNull IndicatorConfig indicatorConfig) {
        super(paint, indicatorConfig);
    }

    public void draw(
            @NonNull Canvas canvas,
            @NonNull Value value,
            int position,
            int coordinateX,
            int coordinateY) {

        if (!(value instanceof ScaleAnimationValue)) {
            return;
        }

        ScaleAnimationValue v = (ScaleAnimationValue) value;
        float radius = indicatorConfig.getRadius();
        int color = indicatorConfig.getSelectedColor();

        int selectedPosition = indicatorConfig.getSelectedPosition();
        int selectingPosition = indicatorConfig.getSelectingPosition();
        int lastSelectedPosition = indicatorConfig.getLastSelectedPosition();

        if (indicatorConfig.isInteractiveAnimation()) {
            if (position == selectingPosition) {
                radius = v.getRadius();
                color = v.getColor();

            } else if (position == selectedPosition) {
                radius = v.getRadiusReverse();
                color = v.getColorReverse();
            }

        } else {
            if (position == selectedPosition) {
                radius = v.getRadius();
                color = v.getColor();

            } else if (position == lastSelectedPosition) {
                radius = v.getRadiusReverse();
                color = v.getColorReverse();
            }
        }

        paint.setColor(color);
        canvas.drawCircle(coordinateX, coordinateY, radius, paint);
    }
}
