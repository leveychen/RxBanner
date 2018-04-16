package cn.levey.bannerlib.indicator.draw.drawer.type;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import cn.levey.bannerlib.indicator.animation.data.Value;
import cn.levey.bannerlib.indicator.animation.data.type.FillAnimationValue;
import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;

public class FillDrawer extends BaseDrawer {

    private Paint strokePaint;

    public FillDrawer(@NonNull Paint paint, @NonNull IndicatorConfig indicatorConfig) {
        super(paint, indicatorConfig);

        strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setAntiAlias(true);
    }

    public void draw(
            @NonNull Canvas canvas,
            @NonNull Value value,
            int position,
            int coordinateX,
            int coordinateY) {

        if (!(value instanceof FillAnimationValue)) {
            return;
        }

        FillAnimationValue v = (FillAnimationValue) value;
        int color = indicatorConfig.getUnselectedColor();
        float radius = indicatorConfig.getRadius();
        int stroke = indicatorConfig.getStroke();

        int selectedPosition = indicatorConfig.getSelectedPosition();
        int selectingPosition = indicatorConfig.getSelectingPosition();
        int lastSelectedPosition = indicatorConfig.getLastSelectedPosition();

        if (indicatorConfig.isInteractiveAnimation()) {
            if (position == selectingPosition) {
                color = v.getColor();
                radius = v.getRadius();
                stroke = v.getStroke();

            } else if (position == selectedPosition) {
                color = v.getColorReverse();
                radius = v.getRadiusReverse();
                stroke = v.getStrokeReverse();
            }

        } else {
            if (position == selectedPosition) {
                color = v.getColor();
                radius = v.getRadius();
                stroke = v.getStroke();

            } else if (position == lastSelectedPosition) {
                color = v.getColorReverse();
                radius = v.getRadiusReverse();
                stroke = v.getStrokeReverse();
            }
        }

        strokePaint.setColor(color);
        strokePaint.setStrokeWidth(indicatorConfig.getStroke());
        canvas.drawCircle(coordinateX, coordinateY, indicatorConfig.getRadius(), strokePaint);

        strokePaint.setStrokeWidth(stroke);
        canvas.drawCircle(coordinateX, coordinateY, radius, strokePaint);
    }
}
