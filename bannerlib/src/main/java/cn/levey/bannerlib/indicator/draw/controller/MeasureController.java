package cn.levey.bannerlib.indicator.draw.controller;

import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.View;
import cn.levey.bannerlib.indicator.animation.type.AnimationType;
import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;
import cn.levey.bannerlib.indicator.draw.data.Orientation;

public class MeasureController {

    public Pair<Integer, Integer> measureViewSize(@NonNull IndicatorConfig indicatorConfig, int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);

        int count = indicatorConfig.getCount();
        int radius = indicatorConfig.getRadius();
        int stroke = indicatorConfig.getStroke();

        int padding = indicatorConfig.getPadding();
        int paddingLeft = indicatorConfig.getPaddingLeft();
        int paddingTop = indicatorConfig.getPaddingTop();
        int paddingRight = indicatorConfig.getPaddingRight();
        int paddingBottom = indicatorConfig.getPaddingBottom();

        int circleDiameterPx = radius * 2;
        int desiredWidth = 0;
        int desiredHeight = 0;

        int width;
        int height;

        Orientation orientation = indicatorConfig.getOrientation();
        if (count != 0) {
            int diameterSum = circleDiameterPx * count;
            int strokeSum = (stroke * 2) * count;

            int paddingSum = padding * (count - 1);
            int w = diameterSum + strokeSum + paddingSum;
            int h = circleDiameterPx + stroke;

            if (orientation == Orientation.HORIZONTAL) {
                desiredWidth = w;
                desiredHeight = h;

            } else {
                desiredWidth = h;
                desiredHeight = w;
            }
        }

        if (indicatorConfig.getAnimationType() == AnimationType.DROP) {
            if (orientation == Orientation.HORIZONTAL) {
                desiredHeight *= 2;
            } else {
                desiredWidth *= 2;
            }
        }

        int horizontalPadding = paddingLeft + paddingRight;
        int verticalPadding = paddingTop + paddingBottom;

        if (orientation == Orientation.HORIZONTAL) {
            desiredWidth += horizontalPadding;
            desiredHeight += verticalPadding;

        } else {
            desiredWidth += horizontalPadding;
            desiredHeight += verticalPadding;
        }

        if (widthMode == View.MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == View.MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == View.MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        if (width < 0) {
            width = 0;
        }

        if (height < 0) {
            height = 0;
        }

        indicatorConfig.setWidth(width);
        indicatorConfig.setHeight(height);

        return new Pair<>(width, height);
    }
}
