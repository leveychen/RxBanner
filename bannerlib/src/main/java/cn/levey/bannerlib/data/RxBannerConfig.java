package cn.levey.bannerlib.data;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.Serializable;

import cn.levey.bannerlib.base.RxBannerLogger;
import cn.levey.bannerlib.base.RxBannerUtil;
import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;

public class RxBannerConfig implements Serializable {

    

    private float aspectRatio = -1f;
    private boolean swipeManually = true;
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
    private int titleGravity = Gravity.START ;
    private int titleLayoutGravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private boolean titleVisible = true;
    private int titleMargin = 0;
    private int titleMarginTop = 0;
    private int titleMarginBottom = 0;
    private int titleMarginStart = 0;
    private int titleMarginEnd = 0;
    private int titlePadding = RxBannerUtil.dp2px(3);
    private int titlePaddingTop = 0;
    private int titlePaddingBottom = 0;
    private int titlePaddingStart = 0;
    private int titlePaddingEnd = 0;
    private int titleWidth = ViewGroup.LayoutParams.MATCH_PARENT;
    private int titleHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int titleSize = RxBannerUtil.sp2px(14);
    private int titleColor = Color.WHITE;
    private int titleBackgroundColor = RxBannerUtil.DEFAULT_BG_COLOR;
    private int titleBackgroundResource = Integer.MAX_VALUE;
    private boolean titleMarquee = true;
    private boolean indicatorVisible = true;
    private IndicatorConfig indicatorConfigConfig = new IndicatorConfig();
    private int emptyViewResource = 0;
    private String emptyViewText = "";

    public boolean isSwipeManually() {
        return swipeManually;
    }

    public void setSwipeManually(boolean swipeManually) {
        this.swipeManually = swipeManually;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        if (aspectRatio <= 0 && aspectRatio != -1f)
            throw new IllegalArgumentException(RxBannerLogger.LOGGER_TAG + ": aspectRatio should be greater than 0");
        this.aspectRatio = aspectRatio;
    }

    public int getEmptyViewResource() {
        return emptyViewResource;
    }

    public void setEmptyViewResource(int resourceId) {
        this.emptyViewResource = resourceId;
    }

    public String getEmptyViewText() {
        return emptyViewText;
    }

    public void setEmptyViewText(String str) {
        this.emptyViewText = str;
    }

    public boolean isIndicatorVisible() {
        return indicatorVisible;
    }

    public void setIndicatorVisible(boolean indicatorVisible) {
        this.indicatorVisible = indicatorVisible;
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
        if (timeInterval < 200)
            throw new IllegalArgumentException(RxBannerLogger.LOGGER_TAG + ": for better performance, timeInterval should be greater than 200 millisecond");
        this.timeInterval = timeInterval;
    }

    public int getItemPercent() {
        return itemPercent;
    }

    public void setItemPercent(int itemPercent) {
        if (itemPercent <= 0)
            throw new IllegalArgumentException(RxBannerLogger.LOGGER_TAG + ": itemPercent should be greater than 0");
        this.itemPercent = itemPercent;
    }

    public float getItemMoveSpeed() {
        return itemMoveSpeed;
    }

    public void setItemMoveSpeed(float itemMoveSpeed) {
        if (itemMoveSpeed <= 0)
            throw new IllegalArgumentException(RxBannerLogger.LOGGER_TAG + ": itemMoveSpeed should be greater than 0");
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

    public int getTitleGravity() {
        return titleGravity;
    }

    public void setTitleGravity(int titleGravity) {
        this.titleGravity = titleGravity;
    }

    public int getTitleLayoutGravity() {
        return titleLayoutGravity;
    }

    public void setTitleLayoutGravity(int titleLayoutGravity) {
        this.titleLayoutGravity = titleLayoutGravity;
    }

    public boolean isTitleVisible() {
        return titleVisible;
    }

    public void setTitleVisible(boolean titleVisible) {
        this.titleVisible = titleVisible;
    }

    public int getTitleMargin() {
        return titleMargin;
    }


    public int getTitleMarginTop() {
        return titleMarginTop > 0 ? titleMarginTop : getTitleMargin();
    }


    public int getTitleMarginBottom() {
        return titleMarginBottom > 0 ? titleMarginBottom : getTitleMargin();
    }


    public int getTitleMarginStart() {
        return titleMarginStart > 0 ? titleMarginStart : getTitleMargin();
    }


    public int getTitleMarginEnd() {
        return titleMarginEnd > 0 ? titleMarginEnd : getTitleMargin();
    }

    public int getTitlePadding() {
        return titlePadding;
    }


    public int getTitlePaddingTop() {
        return titlePaddingTop > 0 ? titlePaddingTop : getTitlePadding();
    }


    public int getTitlePaddingBottom() {
        return titlePaddingBottom > 0 ? titlePaddingBottom : getTitlePadding();
    }


    public int getTitlePaddingStart() {
        return titlePaddingStart > 0 ? titlePaddingStart : getTitlePadding();
    }



    public int getTitlePaddingEnd() {
        return titlePaddingEnd > 0 ? titlePaddingEnd : getTitlePadding();
    }


    public int getTitleWidth() {
        return titleWidth;
    }



    public int getTitleHeight() {
        return titleHeight;
    }


    public int getTitleSize() {
        return titleSize;
    }


    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }
    public void setTitleColorResource(Context context,int resColorId) {
        this.titleColor = context.getResources().getColor(resColorId);
    }

    public int getTitleBackgroundColor() {
        return titleBackgroundColor;
    }

    public void setTitleBackgroundColor(int titleBackgroundColor) {
        this.titleBackgroundColor = titleBackgroundColor;
    }

    public int getTitleBackgroundResource() {
        return titleBackgroundResource;
    }

    public void setTitleBackgroundResource(int titleBackgroundResource) {
        this.titleBackgroundResource = titleBackgroundResource;
    }

    public boolean isTitleMarquee() {
        return titleMarquee;
    }

    public void setTitleMarquee(boolean titleMarquee) {
        this.titleMarquee = titleMarquee;
    }

    public void setItemSpacePx(int itemSpacePx) {
        this.itemSpace = itemSpacePx;
    }

    public void setTitleMarginPx(int titleMarginPx) {
        this.titleMargin = titleMarginPx;
    }

    public void setTitleMarginTopPx(int titleMarginTopPx) {
        this.titleMarginTop = titleMarginTopPx;
    }

    public void setTitleMarginBottomPx(int titleMarginBottomPx) {
        this.titleMarginBottom = titleMarginBottomPx;
    }

    public void setTitleMarginStartPx(int titleMarginStartPx) {
        this.titleMarginStart = titleMarginStartPx;
    }

    public void setTitleMarginEndPx(int titleMarginEndPx) {
        this.titleMarginEnd = titleMarginEndPx;
    }

    public void setTitlePaddingPx(int titlePaddingPx) {
        this.titlePadding = titlePaddingPx;
    }

    public void setTitlePaddingTopPx(int titlePaddingTopPx) {
        this.titlePaddingTop = titlePaddingTopPx;
    }

    public void setTitlePaddingBottomPx(int titlePaddingBottomPx) {
        this.titlePaddingBottom = titlePaddingBottomPx;
    }

    public void setTitlePaddingStartPx(int titlePaddingStartPx) {
        this.titlePaddingStart = titlePaddingStartPx;
    }

    public void setTitlePaddingEndPx(int titlePaddingEndPx) {
        this.titlePaddingEnd = titlePaddingEndPx;
    }

    public void setTitleWidthPx(int titleWidthPx) {
        this.titleWidth = titleWidthPx;
    }

    public void setTitleHeightPx(int titleHeightPx) {
        this.titleHeight = titleHeightPx;
    }

    public void setTitleSizePx(int titleSizePx) {
        this.titleSize = titleSizePx;
    }



    //

    public void setItemSpaceDp(int itemSpaceDp) {
        this.itemSpace = RxBannerUtil.dp2px(itemSpaceDp);
    }

    public void setTitleMarginDp(int titleMarginDp) {
        this.titleMargin = RxBannerUtil.dp2px(titleMarginDp);
    }

    public void setTitleMarginTopDp(int titleMarginTopDp) {
        this.titleMarginTop = RxBannerUtil.dp2px(titleMarginTopDp);
    }

    public void setTitleMarginBottomDp(int titleMarginBottomDp) {
        this.titleMarginBottom = RxBannerUtil.dp2px(titleMarginBottomDp);
    }

    public void setTitleMarginStartDp(int titleMarginStartDp) {
        this.titleMarginStart = RxBannerUtil.dp2px(titleMarginStartDp);
    }

    public void setTitleMarginEndDp(int titleMarginEndDp) {
        this.titleMarginEnd = RxBannerUtil.dp2px(titleMarginEndDp);
    }

    public void setTitlePaddingDp(int titlePaddingDp) {
        this.titlePadding = RxBannerUtil.dp2px(titlePaddingDp);
    }

    public void setTitlePaddingTopDp(int titlePaddingTopDp) {
        this.titlePaddingTop = RxBannerUtil.dp2px(titlePaddingTopDp);
    }

    public void setTitlePaddingBottomDp(int titlePaddingBottomDp) {
        this.titlePaddingBottom = RxBannerUtil.dp2px(titlePaddingBottomDp);
    }

    public void setTitlePaddingStartDp(int titlePaddingStartDp) {
        this.titlePaddingStart = RxBannerUtil.dp2px(titlePaddingStartDp);
    }

    public void setTitlePaddingEndDp(int titlePaddingEndDp) {
        this.titlePaddingEnd = RxBannerUtil.dp2px(titlePaddingEndDp);
    }

    public void setTitleWidthDp(int titleWidthDp) {
        this.titleWidth = titleWidthDp > 0 ? RxBannerUtil.dp2px(titleWidthDp) : titleWidthDp;
    }

    public void setTitleHeightDp(int titleHeightDp) {
        this.titleHeight = titleHeightDp > 0 ? RxBannerUtil.dp2px(titleHeightDp) : titleHeightDp;
    }

    public void setTitleSizeSp(int titleSizeSp) {
        this.titleSize = RxBannerUtil.sp2px(titleSizeSp);
    }

}
