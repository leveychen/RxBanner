package cn.levey.bannerlib.indicator.draw.data;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

import cn.levey.bannerlib.R;
import cn.levey.bannerlib.base.RxBannerUtil;
import cn.levey.bannerlib.indicator.animation.type.AnimationType;
import cn.levey.bannerlib.indicator.animation.type.BaseAnimation;
import cn.levey.bannerlib.indicator.animation.type.ColorAnimation;
import cn.levey.bannerlib.indicator.animation.type.FillAnimation;
import cn.levey.bannerlib.indicator.animation.type.ScaleAnimation;

public class IndicatorConfig implements Serializable{

    public static final int DEFAULT_COUNT = 0;
    public static final int MIN_COUNT = 1;
    public static final int COUNT_NONE = -1;

    public static final int DEFAULT_RADIUS_DP = 5;
    public static final int DEFAULT_MARGIN_DP = 3;
    public static final int DEFAULT_PADDING_DP = 3;

    private int height = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int width = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int radius = RxBannerUtil.dp2px(IndicatorConfig.DEFAULT_RADIUS_DP);

    private int textSize = RxBannerUtil.sp2px(14);
    private int textColor = Color.WHITE;
    private int backgroundColor = RxBannerUtil.DEFAULT_BG_COLOR;
//    private int backgroundResource = Integer.MAX_VALUE;
    private int backgroundResource = Integer.MAX_VALUE;

    private int padding  = RxBannerUtil.dp2px(IndicatorConfig.DEFAULT_PADDING_DP);
    private int paddingStart = 0;
    private int paddingTop = 0;
    private int paddingEnd = 0;
    private int paddingBottom = 0;

    private int gravity = Gravity.BOTTOM | Gravity.END;
    private int margin = RxBannerUtil.dp2px(IndicatorConfig.DEFAULT_MARGIN_DP);
    private int marginTop = 0;
    private int marginBottom = 0;
    private int marginStart = 0;
    private int marginEnd = 0;

    private int stroke = RxBannerUtil.dp2px(FillAnimation.DEFAULT_STROKE_DP); //For "Fill" animation only
    private float scale = ScaleAnimation.DEFAULT_SCALE_FACTOR; //For "Scale" animation only

    private int unselectedColor = Color.parseColor(ColorAnimation.DEFAULT_UNSELECTED_COLOR);
    private int selectedColor =  Color.parseColor(ColorAnimation.DEFAULT_SELECTED_COLOR);

    private boolean interactiveAnimation = false;
    private boolean autoVisibility = false;
    private boolean dynamicCount = true;
    private boolean clickable = true;

    private long animationDuration = BaseAnimation.DEFAULT_ANIMATION_TIME;
    private int count = DEFAULT_COUNT;

    private int selectedPosition = 0;
    private int selectingPosition = 0;
    private int lastSelectedPosition = 0;

    private int recyclerViewId = View.NO_ID;

    private int animatorResId = R.animator.rx_banner_scale_with_alpha;
    private int animatorReverseResId = 0;
    private int indicatorSelectedBackgroundResId = R.drawable.rx_banner_white_radius;
    private int indicatorUnselectedBackgroundResId = R.drawable.rx_banner_white_radius;

    private Orientation orientation = Orientation.HORIZONTAL;
    private AnimationType animationType = AnimationType.NONE;
    private RtlMode rtlMode = RtlMode.Auto;


    public int getAnimatorResId() {
        return animatorResId;
    }

    public void setAnimatorResId(int animatorResId) {
        this.animatorResId = animatorResId;
    }

    public int getAnimatorReverseResId() {
        return animatorReverseResId;
    }

    public void setAnimatorReverseResId(int animatorReverseResId) {
        this.animatorReverseResId = animatorReverseResId;
    }

    public int getIndicatorSelectedBackgroundResId() {
        return indicatorSelectedBackgroundResId;
    }

    public void setIndicatorSelectedBackgroundResId(int indicatorSelectedBackgroundResId) {
        this.indicatorSelectedBackgroundResId = indicatorSelectedBackgroundResId;
    }

    public int getIndicatorUnselectedBackgroundResId() {
        return indicatorUnselectedBackgroundResId;
    }

    public void setIndicatorUnselectedBackgroundResId(int indicatorUnselectedBackgroundResId) {
        this.indicatorUnselectedBackgroundResId = indicatorUnselectedBackgroundResId;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBackgroundResource() {
        return backgroundResource;
    }

    public void setBackgroundResource(int backgroundResource) {
        this.backgroundResource = backgroundResource;
    }

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
        return paddingStart > 0 ? paddingStart : getPadding();
    }

    public int getPaddingTop() {
        return paddingTop > 0 ? paddingTop : getPadding();
    }


    public int getPaddingEnd() {
        return paddingEnd > 0 ? paddingEnd : getPadding();
    }


    public int getPaddingBottom() {
        return paddingBottom > 0 ? paddingBottom : getPadding();
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
        return marginTop > 0 ? marginTop : getMargin();
    }



    public int getMarginBottom() {
        return marginBottom  > 0 ? marginBottom : getMargin();
    }



    public int getMarginStart() {
        return marginStart> 0 ? marginStart : getMargin();
    }



    public int getMarginEnd() {
        return marginEnd> 0 ? marginEnd : getMargin();
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
