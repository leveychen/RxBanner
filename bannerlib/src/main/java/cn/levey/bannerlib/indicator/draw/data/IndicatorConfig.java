package cn.levey.bannerlib.indicator.draw.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import cn.levey.bannerlib.indicator.animation.type.AnimationType;

public class IndicatorConfig {

    public static final int DEFAULT_COUNT = 0;
    public static final int MIN_COUNT = 1;
    public static final int COUNT_NONE = -1;

    public static final int DEFAULT_RADIUS_DP = 6;
    public static final int DEFAULT_MARGIN_DP = 6;
    public static final int DEFAULT_PADDING_DP = 3;

    private int height;
    private int width;
    private int radius;

    private int padding;
    private int paddingStart;
    private int paddingTop;
    private int paddingEnd;
    private int paddingBottom;

    private int gravity;
    private int margin;
    private int marginTop;
    private int marginBottom;
    private int marginStart;
    private int marginEnd;

    private int stroke; //For "Fill" animation only
    private float scale; //For "Scale" animation only

    private int unselectedColor;
    private int selectedColor;

    private boolean interactiveAnimation;
    private boolean autoVisibility = false;
    private boolean dynamicCount;
    private boolean clickable;

    private long animationDuration;
    private int count = DEFAULT_COUNT;

    private int selectedPosition;
    private int selectingPosition;
    private int lastSelectedPosition;

    private int recyclerViewId = View.NO_ID;

    private Orientation orientation;
    private AnimationType animationType;
    private RtlMode rtlMode;

    public int getHeight() {
        return height;
    }


    public int getWidth() {
        return width;
    }



    public int getRadius() {
        return radius;
    }


    public int getPadding() {
        return padding;
    }


    public int getPaddingStart() {
        return paddingStart;
    }

    public int getPaddingTop() {
        return paddingTop;
    }


    public int getPaddingEnd() {
        return paddingEnd;
    }


    public int getPaddingBottom() {
        return paddingBottom;
    }


    public int getStroke() {
        return stroke;
    }


    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getUnselectedColor() {
        return unselectedColor;
    }

    public void setUnselectedColor(int unselectedColor) {
        this.unselectedColor = unselectedColor;
    }

    public void setUnselectedColorResource(Context context,int unselectedColorResId) {
        this.unselectedColor = context.getResources().getColor(unselectedColorResId);
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public void setSelectedColorResource(Context context,int selectedColorResId) {
        this.selectedColor = context.getResources().getColor(selectedColorResId);
    }

    public boolean isInteractiveAnimation() {
        return interactiveAnimation;
    }

    public void setInteractiveAnimation(boolean interactiveAnimation) {
        this.interactiveAnimation = interactiveAnimation;
    }

    public boolean isAutoVisibility() {
        return autoVisibility;
    }

    public void setAutoVisibility(boolean autoVisibility) {
        this.autoVisibility = autoVisibility;
    }

    public boolean isDynamicCount() {
        return dynamicCount;
    }

    public void setDynamicCount(boolean dynamicCount) {
        this.dynamicCount = dynamicCount;
    }

    public long getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public int getSelectingPosition() {
        return selectingPosition;
    }

    public void setSelectingPosition(int selectingPosition) {
        this.selectingPosition = selectingPosition;
    }

    public int getLastSelectedPosition() {
        return lastSelectedPosition;
    }

    public void setLastSelectedPosition(int lastSelectedPosition) {
        this.lastSelectedPosition = lastSelectedPosition;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @NonNull
    public Orientation getOrientation() {
        if (orientation == null) {
            orientation = Orientation.HORIZONTAL;
        }
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    @NonNull
    public AnimationType getAnimationType() {
        if (animationType == null) {
            animationType = AnimationType.NONE;
        }
        return animationType;
    }

    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }

    @NonNull
    public RtlMode getRtlMode() {
        if (rtlMode == null) {
            rtlMode = RtlMode.Off;
        }
        return rtlMode;
    }

    public void setRtlMode(RtlMode rtlMode) {
        this.rtlMode = rtlMode;
    }

    public int getRecyclerViewId() {
        return recyclerViewId;
    }

    public void setRecyclerViewId(int recyclerViewId) {
        this.recyclerViewId = recyclerViewId;
    }


    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public int getMargin() {
        return margin;
    }


    public int getMarginTop() {
        return marginTop;
    }



    public int getMarginBottom() {
        return marginBottom;
    }



    public int getMarginStart() {
        return marginStart;
    }



    public int getMarginEnd() {
        return marginEnd;
    }


    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public void setPaddingEnd(int paddingEnd) {
        this.paddingEnd = paddingEnd;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public void setMarginStart(int marginStart) {
        this.marginStart = marginStart;
    }

    public void setMarginEnd(int marginEnd) {
        this.marginEnd = marginEnd;
    }

    public void setStroke(int stroke) {
        this.stroke = stroke;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void setPaddingStart(int paddingStart) {
        this.paddingStart = paddingStart;
    }
}
