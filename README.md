# RxBanner
[![](https://jitpack.io/v/leveychen/RxBanner.svg)](https://jitpack.io/#leveychen/RxBanner)    [![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)       [![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

Flexible banner base on RecyclerView

[ENGLISH](https://github.com/leveychen/RxBanner/blob/master/README.md)&nbsp;&nbsp;&nbsp;[中文文档](https://github.com/leveychen/RxBanner/blob/master/README_CN.md)

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
    implementation 'com.github.leveychen:RxBanner:x.x.x'
```
[LATEST RELEASE](https://github.com/leveychen/RxBanner/releases/latest)

## Usage
### sample
#### `Layout`
````xml
    <cn.levey.bannerlib.RxBanner
        android:id="@+id/rx_banner"
        android:layout_width="match_parent"
        android:layout_height="160dp"/>
````

#### `Java Code`
````java
    banner = findViewById(R.id.rx_banner);
    banner
        .setLoader(new ImageLoader())
        .setDatas(imageUrls, titles)
        .start();
````

you `MUST` set a image loader and datas before start

#### image loader sample

[Fresco](https://github.com/leveychen/RxBanner/blob/master/app/src/main/java/cn/levey/rxbanner/loader/FrescoLoader.java)&nbsp;&nbsp;&nbsp;&nbsp;
[Glide](https://github.com/leveychen/RxBanner/blob/master/app/src/main/java/cn/levey/rxbanner/loader/GlideLoader.java)&nbsp;&nbsp;&nbsp;&nbsp;
[Picasso](https://github.com/leveychen/RxBanner/blob/master/app/src/main/java/cn/levey/rxbanner/loader/PicassoLoader.java)&nbsp;&nbsp;&nbsp;&nbsp;
[UniversalImageLoader](https://github.com/leveychen/RxBanner/blob/master/app/src/main/java/cn/levey/rxbanner/loader/UniversalImageLoader.java)
### listener
````java
    .setOnBannerClickListener(new RxBannerClickListener())
    .setOnBannerChangeListener(new RxBannerChangeListener())
    .setOnBannerTitleClickListener(new RxBannerTitleClickListener())
````


### custom indicator
````java
    banner.setCustomIndicator(indicator)
    .setOnBannerChangeListener(new RxBannerChangeListener() {
                        @Override
                        public void onBannerSelected(int position) {
                            indicator.setSelection(position)
                        }

                        @Override
                        public void onBannerScrollStateChanged(int state) {

                        }
                    })
````

## Global Settings
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

## Lifecycle
lifecycle for Activity or Fragment and other views
````java
    banner.onResume()
    banner.onPause()
    banner.onDestroy() // not necessary, onDetachedFromWindow handled it.
````


## Attributes
All the `rb_` attributes here are specific for RxBanner
### Banner
|Attributes|format|default|description
|---|---|---|---|
|rb_infinite|boolean|true|infinite loop
|rb_timeInterval|integer (`millisecond`)|5000| for better performance, `rb_timeInterval` should be greater than 200 millisecond
|rb_orientation|horizontal / vertical|horizontal|layout orientation
|rb_itemPercent|integer|100| item width or height percentage
|rb_itemScale|float|1|banner item scale
|rb_itemSpace|dimension|0|banner item space
|rb_centerAlpha|float|1|center item alpha
|rb_sideAlpha|float|1|side item alpha
|rb_itemMoveSpeed|float|1|fling speed
|rb_orderType|asc / desc|asc| /
|rb_viewPaperMode|boolean|true| one fling one paper like `ViewPaper`



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



### Indicator
|Attributes|format|default|description
|---|---|---|---|
|rb_indicator_visible|boolean|true| view visibility
|rb_indicator_clickable|boolean|true| clicke position to change banner selection
|rb_indicator_orientation|horizontal / vertical|horizontal|layout orientation
|rb_indicator_layout_gravity|gravity|BOTTOM / END|layout gravity
|rb_indicator_radius|dimension|5dp|indicator radius
|rb_indicator_size|dimension|5dp|the same as `rb_indicator_radius`
|rb_indicator_scale|float|0.7| set unselected scale when `rb_indicator_animationType` is `scale` or `scale_down`
|rb_indicator_margin|dimension|8dp| margin
|rb_indicator_padding|dimension|3dp| padding
|rb_indicator_selected_color|color|#FFFFFF|  /
|rb_indicator_unselected_color|color|#33FFFFFF|  /
|rb_indicator_interactiveAnimation|boolean|false|  /
|rb_indicator_animationDuration|integer|350|millisecond
|rb_indicator_animationType|type|none|see `AnimationType`
|rb_indicator_rtl_mode|on / off / auto|auto|support [`RTL` ](https://android-developers.googleblog.com/2013/03/native-rtl-support-in-android-42.html)

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


## Permission
display images from network
````xml
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
````

## Release Note
See release notes on [Github Releases](https://github.com/leveychen/RxBanner/releases)

## Thanks
[ViewPagerLayoutManager](https://github.com/leochuan/ViewPagerLayoutManager)  and
[PageIndicatorView](https://github.com/romandanylyk/PageIndicatorView)
## License
Apache-2.0. See [LICENSE](http://www.apache.org/licenses/LICENSE-2.0.html)  for detail