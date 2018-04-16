package cn.levey.bannerlib.indicator.draw.drawer.type;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import cn.levey.bannerlib.indicator.animation.data.Value;
import cn.levey.bannerlib.indicator.animation.data.type.ColorAnimationValue;
import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;

public class ColorDrawer extends BaseDrawer {

    public ColorDrawer(@NonNull Paint paint, @NonNull IndicatorConfig indicatorConfig) {
        super(paint, indicatorConfig);
    }

    public void draw(@NonNull Canvas canvas,
              @NonNull Value value,
              int position,
              int coordinateX,
              int coordinateY) {

        if (!(value instanceof ColorAnimationValue)) {
            return;
        }

        ColorAnimationValue v = (ColorAnimationValue) value;
        float radius = indicatorConfig.getRadius();
        int color = indicatorConfig.getSelectedColor();

        int selectedPosition = indicatorConfig.getSelectedPosition();
        int selectingPosition = indicatorConfig.getSelectingPosition();
        int lastSelectedPosition = indicatorConfig.getLastSelectedPosition();

        if (indicatorConfig.isInteractiveAnimation()) {
            if (position == selectingPosition) {
                color = v.getColor();

            } else if (position == selectedPosition) {
                color = v.getColorReverse();
            }

        } else {
            if (position == selectedPosition) {
                color = v.getColor();

            } else if (position == lastSelectedPosition) {
                color = v.getColorReverse();
            }
        }

        paint.setColor(color);
        canvas.drawCircle(coordinateX, coordinateY, radius, paint);
    }
}
