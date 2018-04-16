package cn.levey.bannerlib.indicator.draw.drawer.type;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import cn.levey.bannerlib.indicator.animation.type.AnimationType;
import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;

public class BasicDrawer extends BaseDrawer {

    private Paint strokePaint;

    public BasicDrawer(@NonNull Paint paint, @NonNull IndicatorConfig indicatorConfig) {
        super(paint, indicatorConfig);

        strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setAntiAlias(true);
        strokePaint.setStrokeWidth(indicatorConfig.getStroke());
    }

    public void draw(
            @NonNull Canvas canvas,
            int position,
            boolean isSelectedItem,
            int coordinateX,
            int coordinateY) {

        float radius = indicatorConfig.getRadius();
        int strokePx = indicatorConfig.getStroke();
        float scaleFactor = indicatorConfig.getScale();

        int selectedColor = indicatorConfig.getSelectedColor();
        int unselectedColor = indicatorConfig.getUnselectedColor();
        int selectedPosition = indicatorConfig.getSelectedPosition();
        AnimationType animationType = indicatorConfig.getAnimationType();

		if (animationType == AnimationType.SCALE && !isSelectedItem) {
			radius *= scaleFactor;

		} else if (animationType == AnimationType.SCALE_DOWN && isSelectedItem) {
			radius *= scaleFactor;
		}

        int color = unselectedColor;
        if (position == selectedPosition) {
            color = selectedColor;
        }

        Paint paint;
        if (animationType == AnimationType.FILL && position != selectedPosition) {
            paint = strokePaint;
            paint.setStrokeWidth(strokePx);
        } else {
            paint = this.paint;
        }

        paint.setColor(color);
        canvas.drawCircle(coordinateX, coordinateY, radius, paint);
    }
}
