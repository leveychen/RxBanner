# RxBanner
[![](https://jitpack.io/v/leveychen/RxBanner.svg)](https://jitpack.io/#leveychen/RxBanner)    [![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)       [![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

Flexible banner base on RecyclerView

[中文文档](https://github.com/leveychen/RxBanner/blob/master/README.md)&nbsp;&nbsp;&nbsp;[English](https://github.com/leveychen/RxBanner/blob/master/README_EN.md)

## Demo Apk

fir.im &nbsp;&nbsp;&nbsp;&nbsp; [rxbanner_demo.apk](https://fir.im/rxbanner)

github &nbsp;&nbsp;&nbsp;&nbsp; [rxbanner_demo.apk](https://github.com/leveychen/RxBanner/releases/download/1.1.2/rxbanner_v1.1.2_demo.apk)



## Preview
![](http://wx1.sinaimg.cn/mw690/0060lm7Tly1fqaqqlrawxg30bo06tb2b.gif)&nbsp;&nbsp;&nbsp;&nbsp;![](http://wx1.sinaimg.cn/mw690/0060lm7Tly1fqat74eq9yg30bo06t4qq.gif)

![](http://wx3.sinaimg.cn/mw690/0060lm7Tly1fqrjdjt47zg307i0dcb2a.gif)&nbsp;&nbsp;&nbsp;&nbsp;![](http://wx3.sinaimg.cn/mw690/0060lm7Tly1fqriet88rxg307i0dcdn3.gif)




## Integration
### Step 1. Add it in your root `build.gradle` at the end of repositories:
```xml
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

### Step 2. Add the `dependency` &nbsp;&nbsp;&nbsp;&nbsp;[![](https://jitpack.io/v/leveychen/RxBanner.svg)](https://jitpack.io/#leveychen/RxBanner)

```xml
    implementation 'com.github.leveychen:RxBanner:1.1.2'
```
[LATEST RELEASE](https://github.com/leveychen/RxBanner/releases/latest)

## Usage
#### `layout`
see `Attributes`
````xml
    <cn.levey.bannerlib.RxBanner
        android:id="@+id/rx_banner"
        android:layout_width="match_parent"
        android:layout_height="160dp"/>
````

#### `java`
````java
    banner = findViewById(R.id.rx_banner);
    banner
        .setLoader(new ImageLoader())               // see `image loader`
        .setConfig(config)                          // see `config`
        .setDatas(iamgesUrls, titles)
        .start();
````



#### `image loader`

[Fresco](https://github.com/leveychen/RxBanner/blob/master/app/src/main/java/cn/levey/rxbanner/loader/FrescoLoader.java)&nbsp;&nbsp;&nbsp;&nbsp;
[Glide](https://github.com/leveychen/RxBanner/blob/master/app/src/main/java/cn/levey/rxbanner/loader/GlideLoader.java)&nbsp;&nbsp;&nbsp;&nbsp;
[Picasso](https://github.com/leveychen/RxBanner/blob/master/app/src/main/java/cn/levey/rxbanner/loader/PicassoLoader.java)&nbsp;&nbsp;&nbsp;&nbsp;
[UniversalImageLoader](https://github.com/leveychen/RxBanner/blob/master/app/src/main/java/cn/levey/rxbanner/loader/UniversalImageLoader.java)


#### `config` - optional
custom your config, the priority is higher than the `xml`
````java
    RxBannerConfig config = banner.getConfig();
    config.set(value);
    ...
    banner.setConfig(config);
    banner.start();
````

you `MUST` set `config`  before `setDatas()` and `start()`

#### `listener` - optional
````java
    banner.setOnBannerClickListener(new RxBannerClickListener())
        onItemClick(int position, Object data)
        onItemLongClick(int position, Object data)

    banner.setOnBannerTitleClickListener(new RxBannerTitleClickListener())
        onTitleClick(int position, String title)

    banner.setOnBannerChangeListener(new RxBannerChangeListener())
        onBannerSelected(int position)
        onBannerScrollStateChanged(int state)

    banner.setOnGuideFinishedListener(new RxBannerGuideFinishedListener()
        onGuideFinished()
````



#### `lifecycle`
lifecycle for Activity or Fragment and other views
````java
    banner.onResume()
    banner.onPause()
    banner.onDestroy()
````

## Global Settings - optional
````java
    RxBannerConfig
        .getInstance()
        .setDebug(true)  // debug: default false
        .setScrollStateChangedListener(new RxBannerScrollStateChangedListener()
        .setLoader(new RxBannerImageViewLoader())
        .setOrientation(LinearLayout.HORIZONTAL)
        .setOrderType(RxBannerConfig.OrderType.ASC)
        .setTimeInterval(5000);

````

## Guide View
just custom layout

[activity_guide.xml](https://github.com/leveychen/RxBanner/blob/master/app/src/main/res/layout/activity_guide.xml)

for better performance
````xml
    banner:rb_infinite="false"
    banner:rb_autoPlay="false"
````
then
````java
    banner.setOnGuideFinishedListener(new RxBannerGuideFinishedListener()
        onGuideFinished()
        // todo
````
all done


## Attributes
All the `rb_` attributes here are specific for RxBanner
### Banner
|Attributes|format|default|description
|---|---|---|---|
|rb_autoPlay|boolean|true|auto play with `rb_timeInterval` delay
|rb_infinite|boolean|true|infinite loop, if `false`
|rb_canSwipe|boolean|true| can swipe manually or not
|rb_canSwipeWhenSingle|boolean|true|can swipe manually or not when only one image
|rb_aspectRatio|float|/| should be greater than 0, `android:layout_height` NOT be `wrap_content` or `match_parent`. recommend `1dp`
|rb_timeInterval|integer (`millisecond`)|5000| for better performance, `rb_timeInterval` should be greater than 200 millisecond
|rb_orientation|horizontal / vertical|horizontal|layout orientation
|rb_itemPercent|integer|100| item width or height percentage
|rb_itemScale|float|1|banner item scale
|rb_itemSpace|dimension|0|banner item space
|rb_centerAlpha|float|1|center item alpha
|rb_sideAlpha|float|1|side item alpha
|rb_itemMoveSpeed|float|1|fling speed
|rb_flingDamping|float|1|fling damping
|rb_orderType|asc / desc|asc| /
|rb_viewPaperMode|boolean|true| one fling one paper like `ViewPaper`
|rb_emptyViewText|string|'No Banner'| display when data is empty and 'rb_emptyViewResource' not define
|rb_emptyViewResource|reference| / | display when data is empty



### Title
|Attributes|format|default|description
|---|---|---|---|
|rb_title_visible|boolean|true| view visibility
|rb_title_gravity|gravity|START| text gravity
|rb_title_layout_gravity|gravity|CENTER_HORIZONTAL and BOTTOM|layout gravity
|rb_title_margin|dimension|0dp| margin
|rb_title_padding|dimension|3dp| padding
|rb_title_width|dimension / enum|MATCH_PARENT| /
|rb_title_height|dimension / enum|WRAP_CONTENT| /
|rb_title_size|dimension|14sp| /
|rb_title_color|color|Color.WHITE| /
|rb_title_backgroundColor|color|#55000000| /
|rb_title_backgroundResource|reference| / | /
|rb_title_marquee|boolean|true| /
|rb_title_lineSpacingMultiplier|float|1.0f| lineSpacingMultiplier



### Indicator
|Attributes|format|default|description
|---|---|---|---|
|rb_indicator_visible|boolean|true| view visibility
|rb_indicator_clickable|boolean|true| click position to change banner selection
|rb_indicator_orientation|horizontal / vertical|horizontal|layout orientation
|rb_indicator_layout_gravity|gravity|BOTTOM / END|layout gravity
|rb_indicator_radius|dimension|5dp|indicator radius
|rb_indicator_textSize|dimension|14sp|`numeric` `numeric_circle` only
|rb_indicator_textColor|color|Color.WHITE|`numeric` `numeric_circle` only
|rb_indicator_backgroundColor|color|/|`numeric` `numeric_circle` only
|rb_indicator_backgroundResource|reference|/|`numeric` `numeric_circle` only
|rb_indicator_scale|float|0.7|  `scale` or `scale_down` only
|rb_indicator_margin|dimension|8dp| margin
|rb_indicator_padding|dimension|3dp| padding
|rb_indicator_selected_color|color|#FFFFFF|  /
|rb_indicator_unselected_color|color|#33FFFFFF|  /
|rb_indicator_interactiveAnimation|boolean|false|  /
|rb_indicator_animationDuration|integer|350|millisecond
|rb_indicator_animationType|type|none|see `AnimationType`
|rb_indicator_rtl_mode|on / off / auto|auto|support
|rb_indicator_selectedResource|reference|/| `custom` only,see [`CircleIndicator` ](https://github.com/ongakuer/CircleIndicator)
|rb_indicator_unselectedResource|reference|/|  `custom` only,see [`CircleIndicator` ](https://github.com/ongakuer/CircleIndicator)
|rb_indicator_animatorResource|reference|/|  `custom` only,see [`CircleIndicator` ](https://github.com/ongakuer/CircleIndicator)
|rb_indicator_animatorReverseResource|reference|/|  `custom` only,see [`CircleIndicator` ](https://github.com/ongakuer/CircleIndicator)


### AnimationType
forked from [PageIndicatorView](https://github.com/romandanylyk/PageIndicatorView)

|Name|Attributes| Preview
|---|---|---
|`AnimationType.NONE`|`none`| ![anim_none](https://raw.githubusercontent.com/romandanylyk/PageIndicatorView/master/assets/anim_none.gif)
|`AnimationType.COLOR`|`color` |![anim_color](https://raw.githubusercontent.com/romandanylyk/PageIndicatorView/master/assets/anim_color.gif)
|`AnimationType.SCALE`|`scale` |![anim_scale](https://raw.githubusercontent.com/romandanylyk/PageIndicatorView/master/assets/anim_scale.gif)
|`AnimationType.SCALE_DOWN`|`scale_down`|![anim_swap](https://raw.githubusercontent.com/romandanylyk/PageIndicatorView/master/assets/anim_scale.gif)
|`AnimationType.SLIDE`|`slide`|![anim_slide](https://raw.githubusercontent.com/romandanylyk/PageIndicatorView/master/assets/anim_slide.gif)
|`AnimationType.WORM`|`worm`|![anim_worm](https://raw.githubusercontent.com/romandanylyk/PageIndicatorView/master/assets/anim_worm.gif)
|`AnimationType.FILL`|`fill`|![anim_worm](https://raw.githubusercontent.com/romandanylyk/PageIndicatorView/master/assets/anim_fill.gif)
|`AnimationType.THIN_WORM`|`thin_worm`|![anim_thin_worm](https://raw.githubusercontent.com/romandanylyk/PageIndicatorView/master/assets/anim_thin_worm.gif)
|`AnimationType.DROP`|`drop`|![anim_drop](https://raw.githubusercontent.com/romandanylyk/PageIndicatorView/master/assets/anim_drop.gif)
|`AnimationType.SWAP`|`swap`|![anim_swap](https://raw.githubusercontent.com/romandanylyk/PageIndicatorView/master/assets/anim_swap.gif)
|`AnimationType.NUMERIC`|`numeric`| 1/8
|`AnimationType.NUMERIC_CIRCLE`|`numeric_circle`| (1/8)
|`AnimationType.CUSTOM`|`custom`| see [`CircleIndicator` ](https://github.com/ongakuer/CircleIndicator)


## Permission
display images from network
````xml
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
````

## Proguard
````xml
    -keep class cn.levey.bannerlib.** {
        *;
    }
````

## Release Note
See release notes on [Github Releases](https://github.com/leveychen/RxBanner/releases)

## Thanks
[ViewPagerLayoutManager](https://github.com/leochuan/ViewPagerLayoutManager)
[PageIndicatorView](https://github.com/romandanylyk/PageIndicatorView)
[CircleIndicator](https://github.com/ongakuer/CircleIndicator)
## License
Apache-2.0. See [LICENSE](http://www.apache.org/licenses/LICENSE-2.0.html)  for detail