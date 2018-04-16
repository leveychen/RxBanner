package cn.levey.bannerlib.indicator.draw.controller;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import cn.levey.bannerlib.indicator.animation.data.Value;
import cn.levey.bannerlib.indicator.animation.type.AnimationType;
import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;
import cn.levey.bannerlib.indicator.draw.drawer.Drawer;
import cn.levey.bannerlib.indicator.utils.CoordinatesUtils;

public class DrawController {

	private Value value;
	private Drawer drawer;
	private IndicatorConfig indicatorConfig;
	private ClickListener listener;

	public interface ClickListener {

		void onIndicatorClicked(int position);
	}

	public DrawController(@NonNull IndicatorConfig indicatorConfig) {
		this.indicatorConfig = indicatorConfig;
		this.drawer = new Drawer(indicatorConfig);
	}

	public void updateValue(@Nullable Value value) {
        this.value = value;
    }

	public void setClickListener(@Nullable ClickListener listener) {
		this.listener = listener;
	}

	public void touch(@Nullable MotionEvent event) {
		if (event == null) {
			return;
		}

		switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				onIndicatorTouched(event.getX(), event.getY());
				break;
			default:
		}
	}

	private void onIndicatorTouched(float x, float y) {
		if (listener != null) {
			int position = CoordinatesUtils.getPosition(indicatorConfig, x, y);
			if (position >= 0) {
				listener.onIndicatorClicked(position);
			}
		}
	}

	public void draw(@NonNull Canvas canvas) {
        int count = indicatorConfig.getCount();

        for (int position = 0; position < count; position++) {
            int coordinateX = CoordinatesUtils.getXCoordinate(indicatorConfig, position);
            int coordinateY = CoordinatesUtils.getYCoordinate(indicatorConfig, position);
            drawIndicator(canvas, position, coordinateX, coordinateY);
        }
    }

    private void drawIndicator(
            @NonNull Canvas canvas,
            int position,
            int coordinateX,
            int coordinateY) {

        boolean interactiveAnimation = indicatorConfig.isInteractiveAnimation();
        int selectedPosition = indicatorConfig.getSelectedPosition();
        int selectingPosition = indicatorConfig.getSelectingPosition();
        int lastSelectedPosition = indicatorConfig.getLastSelectedPosition();

        boolean selectedItem = !interactiveAnimation && (position == selectedPosition || position == lastSelectedPosition);
        boolean selectingItem = interactiveAnimation && (position == selectedPosition || position == selectingPosition);
        boolean isSelectedItem = selectedItem | selectingItem;
        drawer.setup(position, coordinateX, coordinateY);

        if (value != null && isSelectedItem) {
            drawWithAnimation(canvas);
        } else {
            drawer.drawBasic(canvas, isSelectedItem);
        }
    }

    private void drawWithAnimation(@NonNull Canvas canvas) {
        AnimationType animationType = indicatorConfig.getAnimationType();
        switch (animationType) {
            case NONE:
                drawer.drawBasic(canvas, true);
                break;

            case COLOR:
                drawer.drawColor(canvas, value);
                break;

            case SCALE:
                drawer.drawScale(canvas, value);
                break;

            case WORM:
                drawer.drawWorm(canvas, value);
                break;

            case SLIDE:
                drawer.drawSlide(canvas, value);
                break;

            case FILL:
                drawer.drawFill(canvas, value);
                break;

            case THIN_WORM:
                drawer.drawThinWorm(canvas, value);
                break;

            case DROP:
                drawer.drawDrop(canvas, value);
                break;

            case SWAP:
                drawer.drawSwap(canvas, value);
                break;

            case SCALE_DOWN:
                drawer.drawScaleDown(canvas, value);
                break;
        }
    }
}
