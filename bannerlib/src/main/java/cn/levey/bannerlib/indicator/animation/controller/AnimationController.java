package cn.levey.bannerlib.indicator.animation.controller;

import android.support.annotation.NonNull;

import cn.levey.bannerlib.indicator.animation.type.AnimationType;
import cn.levey.bannerlib.indicator.animation.type.BaseAnimation;
import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;
import cn.levey.bannerlib.indicator.draw.data.Orientation;
import cn.levey.bannerlib.indicator.utils.CoordinatesUtils;


public class AnimationController {

    private ValueController valueController;
    private ValueController.UpdateListener listener;

    private BaseAnimation runningAnimation;
    private IndicatorConfig indicatorConfig;

    private float progress;
    private boolean isInteractive;

    public AnimationController(@NonNull IndicatorConfig indicatorConfig, @NonNull ValueController.UpdateListener listener) {
        this.valueController = new ValueController(listener);
        this.listener = listener;
        this.indicatorConfig = indicatorConfig;
    }

    public void interactive(float progress) {
        this.isInteractive = true;
        this.progress = progress;
        animate();
    }

    public void basic() {
        this.isInteractive = false;
        this.progress = 0;
        animate();
    }

    public void end() {
        if (runningAnimation != null) {
            runningAnimation.end();
        }
    }

    private void animate() {
        AnimationType animationType = indicatorConfig.getAnimationType();
        switch (animationType) {
            case NONE:
                listener.onValueUpdated(null);
                break;

            case COLOR:
                colorAnimation();
                break;

            case SCALE:
                scaleAnimation();
                break;

            case WORM:
                wormAnimation();
                break;

            case FILL:
                fillAnimation();
                break;

            case SLIDE:
                slideAnimation();
                break;

            case THIN_WORM:
                thinWormAnimation();
                break;

            case DROP:
                dropAnimation();
                break;

            case SWAP:
                swapAnimation();
                break;

            case SCALE_DOWN:
                scaleDownAnimation();
                break;
        }
    }

    private void colorAnimation() {
        int selectedColor = indicatorConfig.getSelectedColor();
        int unselectedColor = indicatorConfig.getUnselectedColor();
        long animationDuration = indicatorConfig.getAnimationDuration();

        BaseAnimation animation = valueController
                .color()
                .with(unselectedColor, selectedColor)
                .duration(animationDuration);

        if (isInteractive) {
            animation.progress(progress);
        } else {
            animation.start();
        }

        runningAnimation = animation;
    }

    private void scaleAnimation() {
        int selectedColor = indicatorConfig.getSelectedColor();
        int unselectedColor = indicatorConfig.getUnselectedColor();
        int radiusPx = indicatorConfig.getRadius();
        float scaleFactor = indicatorConfig.getScale();
        long animationDuration = indicatorConfig.getAnimationDuration();

        BaseAnimation animation = valueController
                .scale()
                .with(unselectedColor, selectedColor, radiusPx, scaleFactor)
                .duration(animationDuration);

        if (isInteractive) {
            animation.progress(progress);
        } else {
            animation.start();
        }

        runningAnimation = animation;
    }

    private void wormAnimation() {
        int fromPosition = indicatorConfig.isInteractiveAnimation() ? indicatorConfig.getSelectedPosition() : indicatorConfig.getLastSelectedPosition();
        int toPosition = indicatorConfig.isInteractiveAnimation() ? indicatorConfig.getSelectingPosition() : indicatorConfig.getSelectedPosition();

        int from = CoordinatesUtils.getCoordinate(indicatorConfig, fromPosition);
        int to = CoordinatesUtils.getCoordinate(indicatorConfig, toPosition);
        boolean isRightSide = toPosition > fromPosition;

        int radiusPx = indicatorConfig.getRadius();
        long animationDuration = indicatorConfig.getAnimationDuration();

        BaseAnimation animation = valueController
                .worm()
                .with(from, to, radiusPx, isRightSide)
                .duration(animationDuration);

        if (isInteractive) {
            animation.progress(progress);
        } else {
            animation.start();
        }

        runningAnimation = animation;
    }

    private void slideAnimation() {
        int fromPosition = indicatorConfig.isInteractiveAnimation() ? indicatorConfig.getSelectedPosition() : indicatorConfig.getLastSelectedPosition();
        int toPosition = indicatorConfig.isInteractiveAnimation() ? indicatorConfig.getSelectingPosition() : indicatorConfig.getSelectedPosition();

        int from = CoordinatesUtils.getCoordinate(indicatorConfig, fromPosition);
        int to = CoordinatesUtils.getCoordinate(indicatorConfig, toPosition);
        long animationDuration = indicatorConfig.getAnimationDuration();

        BaseAnimation animation = valueController
                .slide()
                .with(from, to)
                .duration(animationDuration);

        if (isInteractive) {
            animation.progress(progress);
        } else {
            animation.start();
        }

        runningAnimation = animation;
    }

    private void fillAnimation() {
        int selectedColor = indicatorConfig.getSelectedColor();
        int unselectedColor = indicatorConfig.getUnselectedColor();
        int radiusPx = indicatorConfig.getRadius();
        int strokePx = indicatorConfig.getStroke();
        long animationDuration = indicatorConfig.getAnimationDuration();

        BaseAnimation animation = valueController
                .fill()
                .with(unselectedColor, selectedColor, radiusPx, strokePx)
                .duration(animationDuration);

        if (isInteractive) {
            animation.progress(progress);
        } else {
            animation.start();
        }

        runningAnimation = animation;
    }

    private void thinWormAnimation() {
        int fromPosition = indicatorConfig.isInteractiveAnimation() ? indicatorConfig.getSelectedPosition() : indicatorConfig.getLastSelectedPosition();
        int toPosition = indicatorConfig.isInteractiveAnimation() ? indicatorConfig.getSelectingPosition() : indicatorConfig.getSelectedPosition();

        int from = CoordinatesUtils.getCoordinate(indicatorConfig, fromPosition);
        int to = CoordinatesUtils.getCoordinate(indicatorConfig, toPosition);
        boolean isRightSide = toPosition > fromPosition;

        int radiusPx = indicatorConfig.getRadius();
        long animationDuration = indicatorConfig.getAnimationDuration();

        BaseAnimation animation = valueController
                .thinWorm()
                .with(from, to, radiusPx, isRightSide)
                .duration(animationDuration);

        if (isInteractive) {
            animation.progress(progress);
        } else {
            animation.start();
        }

        runningAnimation = animation;
    }

    private void dropAnimation() {
        int fromPosition = indicatorConfig.isInteractiveAnimation() ? indicatorConfig.getSelectedPosition() : indicatorConfig.getLastSelectedPosition();
        int toPosition = indicatorConfig.isInteractiveAnimation() ? indicatorConfig.getSelectingPosition() : indicatorConfig.getSelectedPosition();

        int widthFrom = CoordinatesUtils.getCoordinate(indicatorConfig, fromPosition);
        int widthTo = CoordinatesUtils.getCoordinate(indicatorConfig, toPosition);

        int paddingTop = indicatorConfig.getPaddingTop();
        int paddingLeft = indicatorConfig.getPaddingLeft();
        int padding = indicatorConfig.getOrientation() == Orientation.HORIZONTAL ? paddingTop : paddingLeft;

        int radius = indicatorConfig.getRadius();
        int heightFrom = radius * 3 + padding;
        int heightTo = radius + padding;

        long animationDuration = indicatorConfig.getAnimationDuration();

        BaseAnimation animation = valueController
                .drop()
                .duration(animationDuration)
                .with(widthFrom, widthTo, heightFrom, heightTo, radius);

        if (isInteractive) {
            animation.progress(progress);
        } else {
            animation.start();
        }

        runningAnimation = animation;
    }

    private void swapAnimation() {
        int fromPosition = indicatorConfig.isInteractiveAnimation() ? indicatorConfig.getSelectedPosition() : indicatorConfig.getLastSelectedPosition();
        int toPosition = indicatorConfig.isInteractiveAnimation() ? indicatorConfig.getSelectingPosition() : indicatorConfig.getSelectedPosition();

        int from = CoordinatesUtils.getCoordinate(indicatorConfig, fromPosition);
        int to = CoordinatesUtils.getCoordinate(indicatorConfig, toPosition);
        long animationDuration = indicatorConfig.getAnimationDuration();

        BaseAnimation animation = valueController
                .swap()
                .with(from, to)
                .duration(animationDuration);

        if (isInteractive) {
            animation.progress(progress);
        } else {
            animation.start();
        }

        runningAnimation = animation;
    }

    private void scaleDownAnimation() {
        int selectedColor = indicatorConfig.getSelectedColor();
        int unselectedColor = indicatorConfig.getUnselectedColor();
        int radiusPx = indicatorConfig.getRadius();
        float scaleFactor = indicatorConfig.getScale();
        long animationDuration = indicatorConfig.getAnimationDuration();

        BaseAnimation animation = valueController
                .scaleDown()
                .with(unselectedColor, selectedColor, radiusPx, scaleFactor)
                .duration(animationDuration);

        if (isInteractive) {
            animation.progress(progress);
        } else {
            animation.start();
        }

        runningAnimation = animation;
    }
}

