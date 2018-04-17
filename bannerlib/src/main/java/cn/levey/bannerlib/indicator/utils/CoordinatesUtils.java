package cn.levey.bannerlib.indicator.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import cn.levey.bannerlib.indicator.animation.type.AnimationType;
import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;
import cn.levey.bannerlib.indicator.draw.data.Orientation;

public class CoordinatesUtils {

	@SuppressWarnings("UnnecessaryLocalVariable")
	public static int getCoordinate(@Nullable IndicatorConfig indicatorConfig, int position) {
		if (indicatorConfig == null) {
			return 0;
		}

		if (indicatorConfig.getOrientation() == Orientation.HORIZONTAL) {
			return getXCoordinate(indicatorConfig, position);
		} else {
			return getYCoordinate(indicatorConfig, position);
		}
	}

	@SuppressWarnings("UnnecessaryLocalVariable")
	public static int getXCoordinate(@Nullable IndicatorConfig indicatorConfig, int position) {
		if (indicatorConfig == null) {
			return 0;
		}

		int coordinate;
		if (indicatorConfig.getOrientation() == Orientation.HORIZONTAL) {
			coordinate = getHorizontalCoordinate(indicatorConfig, position);
		} else {
			coordinate = getVerticalCoordinate(indicatorConfig);
		}

		coordinate += indicatorConfig.getPaddingStart();
		return coordinate;
	}

	public static int getYCoordinate(@Nullable IndicatorConfig indicatorConfig, int position) {
		if (indicatorConfig == null) {
			return 0;
		}

		int coordinate;
		if (indicatorConfig.getOrientation() == Orientation.HORIZONTAL) {
			coordinate = getVerticalCoordinate(indicatorConfig);
		} else {
			coordinate = getHorizontalCoordinate(indicatorConfig, position);
		}

		coordinate += indicatorConfig.getPaddingTop();
		return coordinate;
	}

	@SuppressWarnings("SuspiciousNameCombination")
	public static int getPosition(@Nullable IndicatorConfig indicatorConfig, float x, float y) {
		if (indicatorConfig == null) {
			return -1;
		}

		float lengthCoordinate;
		float heightCoordinate;

		if (indicatorConfig.getOrientation() == Orientation.HORIZONTAL) {
			lengthCoordinate = x;
			heightCoordinate = y;
		} else {
			lengthCoordinate = y;
			heightCoordinate = x;
		}

		return getFitPosition(indicatorConfig, lengthCoordinate, heightCoordinate);
	}

	private static int getFitPosition(@NonNull IndicatorConfig indicatorConfig, float lengthCoordinate, float heightCoordinate) {
		int count = indicatorConfig.getCount();
		int radius = indicatorConfig.getRadius();
		int stroke = indicatorConfig.getStroke();
		int padding = indicatorConfig.getPadding();

		int height = indicatorConfig.getOrientation() == Orientation.HORIZONTAL ? indicatorConfig.getHeight() : indicatorConfig.getWidth();
		int length = 0;

		for (int i = 0; i < count; i++) {
			int indicatorPadding = i > 0 ? padding : padding / 2;
			int startValue = length;

			length += radius * 2 + (stroke / 2) + indicatorPadding;
			int endValue = length;

			boolean fitLength = lengthCoordinate >= startValue && lengthCoordinate <= endValue;
			boolean fitHeight = heightCoordinate >= 0 && heightCoordinate <= height;

			if (fitLength && fitHeight) {
				return i;
			}
		}

		return -1;
	}

	private static int getHorizontalCoordinate(@NonNull IndicatorConfig indicatorConfig, int position) {
		int count = indicatorConfig.getCount();
		int radius = indicatorConfig.getRadius();
		int stroke = indicatorConfig.getStroke();
		int padding = indicatorConfig.getPadding();

		int coordinate = 0;
		for (int i = 0; i < count; i++) {
			coordinate += radius + (stroke / 2);

			if (position == i) {
				return coordinate;
			}

			coordinate += radius + padding + (stroke / 2);
		}

		if (indicatorConfig.getAnimationType() == AnimationType.DROP) {
			coordinate += radius * 2;
		}

		return coordinate;
	}

	private static int getVerticalCoordinate(@NonNull IndicatorConfig indicatorConfig) {
		int radius = indicatorConfig.getRadius();
		int coordinate;

		if (indicatorConfig.getAnimationType() == AnimationType.DROP) {
			coordinate = radius * 3;
		} else {
			coordinate = radius;
		}

		return coordinate;
	}

	public static Pair<Integer, Float> getProgress(@NonNull IndicatorConfig indicatorConfig, int position, float positionOffset, boolean isRtl) {
		int count = indicatorConfig.getCount();
		int selectedPosition = indicatorConfig.getSelectedPosition();

		if (isRtl) {
			position = (count - 1) - position;
		}

		if (position < 0) {
			position = 0;

		} else if (position > count - 1) {
			position = count - 1;
		}

		boolean isRightOverScrolled = position > selectedPosition;
		boolean isLeftOverScrolled;

		if (isRtl) {
			isLeftOverScrolled = position - 1 < selectedPosition;
		} else {
			isLeftOverScrolled = position + 1 < selectedPosition;
		}

		if (isRightOverScrolled || isLeftOverScrolled) {
			selectedPosition = position;
			indicatorConfig.setSelectedPosition(selectedPosition);
		}

		boolean slideToRightSide = selectedPosition == position && positionOffset != 0;
		int selectingPosition;
		float selectingProgress;

		if (slideToRightSide) {
			selectingPosition = isRtl ? position - 1 : position + 1;
			selectingProgress = positionOffset;

		} else {
			selectingPosition = position;
			selectingProgress = 1 - positionOffset;
		}

		if (selectingProgress > 1) {
			selectingProgress = 1;

		} else if (selectingProgress < 0) {
			selectingProgress = 0;
		}

		return new Pair<>(selectingPosition, selectingProgress);
	}
}
