package cn.levey.bannerlib.indicator.draw.controller;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;

import cn.levey.bannerlib.R;
import cn.levey.bannerlib.base.RxBannerLogger;
import cn.levey.bannerlib.indicator.animation.type.AnimationType;
import cn.levey.bannerlib.indicator.animation.type.BaseAnimation;
import cn.levey.bannerlib.indicator.animation.type.ColorAnimation;
import cn.levey.bannerlib.indicator.animation.type.FillAnimation;
import cn.levey.bannerlib.indicator.animation.type.ScaleAnimation;
import cn.levey.bannerlib.indicator.draw.data.Indicator;
import cn.levey.bannerlib.indicator.draw.data.Orientation;
import cn.levey.bannerlib.indicator.draw.data.RtlMode;
import cn.levey.bannerlib.indicator.utils.DensityUtils;

public class AttributeController {

    private Indicator indicator;

    public AttributeController() {
        this.indicator = new Indicator();
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public Indicator init(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RxBanner);
        initViewAttribute(typedArray);
        
        indicator.setDynamicCount(true);
        indicator.setCount(0);
        indicator.setSelectedPosition(0);
        indicator.setSelectingPosition(0);
        indicator.setLastSelectedPosition(0);
        initColorAttribute(typedArray);
        initAnimationAttribute(typedArray);
        initSizeAttribute(typedArray);
        typedArray.recycle();
        return indicator;
    }

    private void initViewAttribute(@NonNull TypedArray typedArray){
        boolean clickable = typedArray.getBoolean(R.styleable.RxBanner_rb_indicator_clickable, true);
        indicator.setClickable(clickable);
        int rb_indicator_layout_gravity = typedArray.getInt(R.styleable.RxBanner_rb_indicator_layout_gravity, Gravity.BOTTOM | Gravity.END);
        indicator.setGravity(rb_indicator_layout_gravity);
        int margin = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_margin, DensityUtils.dpToPx(Indicator.DEFAULT_MARGIN_DP));
        int marginTop = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_marginTop, 0);
        int marginBottom = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_marginBottom, 0);
        int marginStart = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_marginStart, 0);
        int marginEnd = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_marginEnd, 0);
        if (marginTop == 0) marginTop = margin;
        if (marginBottom == 0) marginBottom = margin;
        if (marginStart == 0) marginStart = margin;
        if (marginEnd == 0) marginEnd = margin;
        indicator.setMargin(margin);
        indicator.setMarginTop(marginTop);
        indicator.setMarginBottom(marginBottom);
        indicator.setMarginStart(marginStart);
        indicator.setMarginEnd(marginEnd);

        RxBannerLogger.i("marginBottom =  " + indicator.getMarginBottom());
        RxBannerLogger.i("marginEnd =  " + indicator.getMarginEnd());

    }

    private void initColorAttribute(@NonNull TypedArray typedArray) {

        int unselectedColor = typedArray.getColor(R.styleable.RxBanner_rb_indicator_unselected_color, Color.parseColor(ColorAnimation.DEFAULT_UNSELECTED_COLOR));
        int selectedColor = typedArray.getColor(R.styleable.RxBanner_rb_indicator_selected_color, Color.parseColor(ColorAnimation.DEFAULT_SELECTED_COLOR));
        indicator.setUnselectedColor(unselectedColor);
        indicator.setSelectedColor(selectedColor);
    }

    private void initAnimationAttribute(@NonNull TypedArray typedArray) {
        boolean interactiveAnimation = typedArray.getBoolean(R.styleable.RxBanner_rb_indicator_interactiveAnimation, false);
        int animationDuration = typedArray.getInt(R.styleable.RxBanner_rb_indicator_animationDuration, BaseAnimation.DEFAULT_ANIMATION_TIME);
        if (animationDuration < 0) {
            animationDuration = 0;
        }

        int animIndex = typedArray.getInt(R.styleable.RxBanner_rb_indicator_animationType, AnimationType.NONE.ordinal());
        AnimationType animationType = getAnimationType(animIndex);

        int rtlIndex = typedArray.getInt(R.styleable.RxBanner_rb_indicator_rtl_mode, RtlMode.Off.ordinal());
        RtlMode rtlMode = getRtlMode(rtlIndex);

        indicator.setAnimationDuration(animationDuration);
        indicator.setInteractiveAnimation(interactiveAnimation);
        indicator.setAnimationType(animationType);
        indicator.setRtlMode(rtlMode);
    }

    private void initSizeAttribute(@NonNull TypedArray typedArray) {
        int orientationIndex = typedArray.getInt(R.styleable.RxBanner_rb_indicator_orientation, Orientation.HORIZONTAL.ordinal());
        Orientation orientation;

        if (orientationIndex == 0) {
            orientation = Orientation.HORIZONTAL;
        } else {
            orientation = Orientation.VERTICAL;
        }

        int radius = (int) typedArray.getDimension(R.styleable.RxBanner_rb_indicator_radius, DensityUtils.dpToPx(Indicator.DEFAULT_RADIUS_DP));
        if (radius < 0) {
            radius = 0;
        }



        int padding = (int) typedArray.getDimension(R.styleable.RxBanner_rb_indicator_padding, DensityUtils.dpToPx(Indicator.DEFAULT_PADDING_DP));
        if (padding < 0) {
            padding = 0;
        }

        float scaleFactor = typedArray.getFloat(R.styleable.RxBanner_rb_indicator_scale, ScaleAnimation.DEFAULT_SCALE_FACTOR);
        if (scaleFactor < ScaleAnimation.MIN_SCALE_FACTOR) {
            scaleFactor = ScaleAnimation.MIN_SCALE_FACTOR;

        } else if (scaleFactor > ScaleAnimation.MAX_SCALE_FACTOR) {
            scaleFactor = ScaleAnimation.MAX_SCALE_FACTOR;
        }

        int stroke = (int) typedArray.getDimension(R.styleable.RxBanner_rb_indicator_stroke_width, DensityUtils.dpToPx(FillAnimation.DEFAULT_STROKE_DP));
        if (stroke > radius) {
            stroke = radius;
        }

        if (indicator.getAnimationType() != AnimationType.FILL) {
            stroke = 0;
        }

        indicator.setRadius(radius);
        indicator.setOrientation(orientation);
        indicator.setPadding(padding);
        indicator.setScale(scaleFactor);
        indicator.setStroke(stroke);
    }

    private AnimationType getAnimationType(int index) {
        switch (index) {
            case 0:
                return AnimationType.NONE;
            case 1:
                return AnimationType.COLOR;
            case 2:
                return AnimationType.SCALE;
            case 3:
                return AnimationType.WORM;
            case 4:
                return AnimationType.SLIDE;
            case 5:
                return AnimationType.FILL;
            case 6:
                return AnimationType.THIN_WORM;
            case 7:
                return AnimationType.DROP;
            case 8:
                return AnimationType.SWAP;
            case 9:
                return AnimationType.SCALE_DOWN;
        }

        return AnimationType.NONE;
    }

    private RtlMode getRtlMode(int index) {
        switch (index) {
            case 0:
                return RtlMode.On;
            case 1:
                return RtlMode.Off;
            case 2:
                return RtlMode.Auto;
        }

        return RtlMode.Auto;
    }
}
