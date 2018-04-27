package cn.levey.bannerlib.indicator.draw.controller;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import cn.levey.bannerlib.R;
import cn.levey.bannerlib.base.RxBannerLogger;
import cn.levey.bannerlib.base.RxBannerUtil;
import cn.levey.bannerlib.indicator.animation.type.AnimationType;
import cn.levey.bannerlib.indicator.animation.type.BaseAnimation;
import cn.levey.bannerlib.indicator.animation.type.ColorAnimation;
import cn.levey.bannerlib.indicator.animation.type.FillAnimation;
import cn.levey.bannerlib.indicator.animation.type.ScaleAnimation;
import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;
import cn.levey.bannerlib.indicator.draw.data.Orientation;
import cn.levey.bannerlib.indicator.draw.data.RtlMode;

public class AttributeController {

    private IndicatorConfig indicatorConfig;

    public AttributeController() {
        this.indicatorConfig = new IndicatorConfig();
    }

    public IndicatorConfig getIndicatorConfig() {
        return indicatorConfig;
    }

    public IndicatorConfig init(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RxBanner);
        initViewAttribute(typedArray);

        indicatorConfig.setDynamicCount(true);
        indicatorConfig.setCount(0);
        indicatorConfig.setSelectedPosition(0);
        indicatorConfig.setSelectingPosition(0);
        indicatorConfig.setLastSelectedPosition(0);
        initColorAttribute(typedArray);
        initAnimationAttribute(typedArray);
        initSizeAttribute(typedArray);
        typedArray.recycle();
        return indicatorConfig;
    }

    private void initViewAttribute(@NonNull TypedArray typedArray) {
        boolean clickable = typedArray.getBoolean(R.styleable.RxBanner_rb_indicator_clickable, true);
        indicatorConfig.setClickable(clickable);
        int rb_indicator_layout_gravity = typedArray.getInt(R.styleable.RxBanner_rb_indicator_layout_gravity, Gravity.BOTTOM | Gravity.END);
        indicatorConfig.setGravity(rb_indicator_layout_gravity);
        int margin = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_margin, RxBannerUtil.dp2px(IndicatorConfig.DEFAULT_MARGIN_DP));
        int marginTop = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_marginTop, 0);
        int marginBottom = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_marginBottom, 0);
        int marginStart = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_marginStart, 0);
        int marginEnd = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_marginEnd, 0);
        if (marginTop == 0) marginTop = margin;
        if (marginBottom == 0) marginBottom = margin;
        if (marginStart == 0) marginStart = margin;
        if (marginEnd == 0) marginEnd = margin;
        indicatorConfig.setMargin(margin);
        indicatorConfig.setMarginTop(marginTop);
        indicatorConfig.setMarginBottom(marginBottom);
        indicatorConfig.setMarginStart(marginStart);
        indicatorConfig.setMarginEnd(marginEnd);

        int padding = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_padding, RxBannerUtil.dp2px(IndicatorConfig.DEFAULT_PADDING_DP));
        int paddingTop = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_paddingTop, 0);
        int paddingBottom = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_paddingBottom, 0);
        int paddingStart = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_paddingStart, 0);
        int paddingEnd = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_paddingEnd, 0);
        if (paddingTop == 0) paddingTop = padding;
        if (paddingBottom == 0) paddingBottom = padding;
        if (paddingStart == 0) paddingStart = padding;
        if (paddingEnd == 0) paddingEnd = padding;
        indicatorConfig.setPadding(padding);
        indicatorConfig.setPaddingTop(paddingTop);
        indicatorConfig.setPaddingBottom(paddingBottom);
        indicatorConfig.setPaddingStart(paddingStart);
        indicatorConfig.setPaddingEnd(paddingEnd);

        try {
            indicatorConfig.setWidth(typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_width, ViewGroup.LayoutParams.WRAP_CONTENT));
        } catch (Exception e) {
            indicatorConfig.setWidth(typedArray.getInt(R.styleable.RxBanner_rb_indicator_width, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        try {
            indicatorConfig.setHeight(typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_height, ViewGroup.LayoutParams.WRAP_CONTENT));
        } catch (Exception e) {
            indicatorConfig.setHeight(typedArray.getInt(R.styleable.RxBanner_rb_indicator_height, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private void initColorAttribute(@NonNull TypedArray typedArray) {

        int unselectedColor = typedArray.getColor(R.styleable.RxBanner_rb_indicator_unselected_color, Color.parseColor(ColorAnimation.DEFAULT_UNSELECTED_COLOR));
        int selectedColor = typedArray.getColor(R.styleable.RxBanner_rb_indicator_selected_color, Color.parseColor(ColorAnimation.DEFAULT_SELECTED_COLOR));
        indicatorConfig.setUnselectedColor(unselectedColor);
        indicatorConfig.setSelectedColor(selectedColor);

        indicatorConfig.setTextSize(typedArray.getColor(R.styleable.RxBanner_rb_indicator_textSize, RxBannerUtil.sp2px(14)));
        indicatorConfig.setTextColor(typedArray.getColor(R.styleable.RxBanner_rb_indicator_textColor, Color.WHITE));
//        indicatorConfig.setBackgroundColor(typedArray.getColor(R.styleable.RxBanner_rb_indicator_backgroundColor, RxBannerUtil.DEFAULT_BG_COLOR));
        indicatorConfig.setBackgroundColor(typedArray.getColor(R.styleable.RxBanner_rb_indicator_backgroundColor, RxBannerUtil.DEFAULT_BG_COLOR));
        indicatorConfig.setBackgroundResource(typedArray.getResourceId(R.styleable.RxBanner_rb_indicator_backgroundResource, Integer.MAX_VALUE));

    }

    private void initAnimationAttribute(@NonNull TypedArray typedArray) {
        boolean interactiveAnimation = typedArray.getBoolean(R.styleable.RxBanner_rb_indicator_interactiveAnimation, false);
        int animationDuration = typedArray.getInt(R.styleable.RxBanner_rb_indicator_animationDuration, BaseAnimation.DEFAULT_ANIMATION_TIME);
        if (animationDuration < 0) {
            animationDuration = 0;
        }

        int animIndex = typedArray.getInt(R.styleable.RxBanner_rb_indicator_animationType, AnimationType.NONE.ordinal());

        RxBannerLogger.i(" ANIM = " + animIndex);
        AnimationType animationType = getAnimationType(animIndex);

        int rtlIndex = typedArray.getInt(R.styleable.RxBanner_rb_indicator_rtl_mode, RtlMode.Off.ordinal());
        RtlMode rtlMode = getRtlMode(rtlIndex);

        indicatorConfig.setAnimationDuration(animationDuration);
        indicatorConfig.setInteractiveAnimation(interactiveAnimation);
        indicatorConfig.setAnimationType(animationType);
        indicatorConfig.setRtlMode(rtlMode);
    }

    private void initSizeAttribute(@NonNull TypedArray typedArray) {
        int orientationIndex = typedArray.getInt(R.styleable.RxBanner_rb_indicator_orientation, Orientation.HORIZONTAL.ordinal());
        Orientation orientation;

        if (orientationIndex == 0) {
            orientation = Orientation.HORIZONTAL;
        } else {
            orientation = Orientation.VERTICAL;
        }

        int radius = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_radius, RxBannerUtil.dp2px(IndicatorConfig.DEFAULT_RADIUS_DP));
        if (radius < 0) {
            radius = 0;
        }

        float scaleFactor = typedArray.getFloat(R.styleable.RxBanner_rb_indicator_scale, ScaleAnimation.DEFAULT_SCALE_FACTOR);
        if (scaleFactor < ScaleAnimation.MIN_SCALE_FACTOR) {
            scaleFactor = ScaleAnimation.MIN_SCALE_FACTOR;

        } else if (scaleFactor > ScaleAnimation.MAX_SCALE_FACTOR) {
            scaleFactor = ScaleAnimation.MAX_SCALE_FACTOR;
        }

        int stroke = typedArray.getDimensionPixelSize(R.styleable.RxBanner_rb_indicator_stroke_width, RxBannerUtil.dp2px(FillAnimation.DEFAULT_STROKE_DP));
        if (stroke > radius) {
            stroke = radius;
        }

        if (indicatorConfig.getAnimationType() != AnimationType.FILL) {
            stroke = 0;
        }

        indicatorConfig.setRadius(radius);
        indicatorConfig.setOrientation(orientation);
        indicatorConfig.setScale(scaleFactor);
        indicatorConfig.setStroke(stroke);
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
            case 10:
                return AnimationType.NUMERIC;
            case 11:
                return AnimationType.NUMERIC_CIRCLE;
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
