package cn.levey.bannerlib.base;

import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;

public class RxBannerConfig {
    private boolean autoPlay = true;
    private boolean infinite = true;
    private float itemScale = 1.0f;
    private int timeInterval = RxBannerGlobalConfig.getInstance().getTimeInterval();
    private int itemPercent = 100;
    private float itemMoveSpeed = 1.0f;
    private float centerAlpha = 1.0f;
    private float sideAlpha = 1.0f;
    private int itemSpace = 0;
    private RxBannerGlobalConfig.OrderType orderType = RxBannerGlobalConfig.getInstance().getOrderType();
    private int orientation = LinearLayout.HORIZONTAL;
    private boolean viewPaperMode = true;

    private int title_gravity = Gravity.START ;
    private int title_layout_gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private boolean title_visible = true;
    private int title_margin = 0;
    private int title_marginTop = 0;
    private int title_marginBottom = 0;
    private int title_marginStart = 0;
    private int title_marginEnd = 0;
    private int title_padding = RxBannerUtil.dp2px(3);
    private int title_paddingTop = 0;
    private int title_paddingBottom = 0;
    private int title_paddingStart = 0;
    private int title_paddingEnd = 0;
    private int title_width = ViewGroup.LayoutParams.MATCH_PARENT;
    private int title_height = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int title_size = RxBannerUtil.sp2px(14);
    private int title_color = Color.WHITE;
    private int title_backgroundColor = RxBannerUtil.DEFAULT_BG_COLOR;
    private int title_backgroundResource = Integer.MAX_VALUE;
    private boolean title_marquee = true;
    private boolean indicator_visible = true;
    private IndicatorConfig indicatorConfigConfig = new IndicatorConfig();

    public boolean isIndicator_visible() {
        return indicator_visible;
    }

    public void setIndicator_visible(boolean indicator_visible) {
        this.indicator_visible = indicator_visible;
    }



    public IndicatorConfig getIndicatorConfigConfig() {
        return indicatorConfigConfig;
    }

    public void setIndicatorConfigConfig(IndicatorConfig indicatorConfigConfig) {
        this.indicatorConfigConfig = indicatorConfigConfig;
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    public boolean isInfinite() {
        return infinite;
    }

    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }

    public float getItemScale() {
        return itemScale;
    }

    public void setItemScale(float itemScale) {
        this.itemScale = itemScale;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    public int getItemPercent() {
        return itemPercent;
    }

    public void setItemPercent(int itemPercent) {
        this.itemPercent = itemPercent;
    }

    public float getItemMoveSpeed() {
        return itemMoveSpeed;
    }

    public void setItemMoveSpeed(float itemMoveSpeed) {
        this.itemMoveSpeed = itemMoveSpeed;
    }

    public float getCenterAlpha() {
        return centerAlpha;
    }

    public void setCenterAlpha(float centerAlpha) {
        this.centerAlpha = centerAlpha;
    }

    public float getSideAlpha() {
        return sideAlpha;
    }

    public void setSideAlpha(float sideAlpha) {
        this.sideAlpha = sideAlpha;
    }

    public int getItemSpace() {
        return itemSpace;
    }

    public void setItemSpace(int itemSpace) {
        this.itemSpace = itemSpace;
    }

    public RxBannerGlobalConfig.OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(RxBannerGlobalConfig.OrderType orderType) {
        this.orderType = orderType;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public boolean isViewPaperMode() {
        return viewPaperMode;
    }

    public void setViewPaperMode(boolean viewPaperMode) {
        this.viewPaperMode = viewPaperMode;
    }

    public int getTitle_gravity() {
        return title_gravity;
    }

    public void setTitle_gravity(int title_gravity) {
        this.title_gravity = title_gravity;
    }

    public int getTitle_layout_gravity() {
        return title_layout_gravity;
    }

    public void setTitle_layout_gravity(int title_layout_gravity) {
        this.title_layout_gravity = title_layout_gravity;
    }

    public boolean isTitle_visible() {
        return title_visible;
    }

    public void setTitle_visible(boolean title_visible) {
        this.title_visible = title_visible;
    }

    public int getTitle_margin() {
        return title_margin;
    }

    public void setTitle_margin(int title_margin) {
        this.title_margin = title_margin;
    }

    public int getTitle_marginTop() {
        return title_marginTop;
    }

    public void setTitle_marginTop(int title_marginTop) {
        this.title_marginTop = title_marginTop;
    }

    public int getTitle_marginBottom() {
        return title_marginBottom;
    }

    public void setTitle_marginBottom(int title_marginBottom) {
        this.title_marginBottom = title_marginBottom;
    }

    public int getTitle_marginStart() {
        return title_marginStart;
    }

    public void setTitle_marginStart(int title_marginStart) {
        this.title_marginStart = title_marginStart;
    }

    public int getTitle_marginEnd() {
        return title_marginEnd;
    }

    public void setTitle_marginEnd(int title_marginEnd) {
        this.title_marginEnd = title_marginEnd;
    }

    public int getTitle_padding() {
        return title_padding;
    }

    public void setTitle_padding(int title_padding) {
        this.title_padding = title_padding;
    }

    public int getTitle_paddingTop() {
        return title_paddingTop;
    }

    public void setTitle_paddingTop(int title_paddingTop) {
        this.title_paddingTop = title_paddingTop;
    }

    public int getTitle_paddingBottom() {
        return title_paddingBottom;
    }

    public void setTitle_paddingBottom(int title_paddingBottom) {
        this.title_paddingBottom = title_paddingBottom;
    }

    public int getTitle_paddingStart() {
        return title_paddingStart;
    }

    public void setTitle_paddingStart(int title_paddingStart) {
        this.title_paddingStart = title_paddingStart;
    }

    public int getTitle_paddingEnd() {
        return title_paddingEnd;
    }

    public void setTitle_paddingEnd(int title_paddingEnd) {
        this.title_paddingEnd = title_paddingEnd;
    }

    public int getTitle_width() {
        return title_width;
    }

    public void setTitle_width(int title_width) {
        this.title_width = title_width;
    }

    public int getTitle_height() {
        return title_height;
    }

    public void setTitle_height(int title_height) {
        this.title_height = title_height;
    }

    public int getTitle_size() {
        return title_size;
    }

    public void setTitle_size(int title_size) {
        this.title_size = title_size;
    }

    public int getTitle_color() {
        return title_color;
    }

    public void setTitle_color(int title_color) {
        this.title_color = title_color;
    }

    public int getTitle_backgroundColor() {
        return title_backgroundColor;
    }

    public void setTitle_backgroundColor(int title_backgroundColor) {
        this.title_backgroundColor = title_backgroundColor;
    }

    public int getTitle_backgroundResource() {
        return title_backgroundResource;
    }

    public void setTitle_backgroundResource(int title_backgroundResource) {
        this.title_backgroundResource = title_backgroundResource;
    }

    public boolean isTitle_marquee() {
        return title_marquee;
    }

    public void setTitle_marquee(boolean title_marquee) {
        this.title_marquee = title_marquee;
    }

}
