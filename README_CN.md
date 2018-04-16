# RxBanner
[![](https://jitpack.io/v/leveychen/RxBanner.svg)](https://jitpack.io/#leveychen/RxBanner)    [![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)       [![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

一个灵活可制制定的基于 Recyclerview 的轮播图

[ENGLISH](https://github.com/leveychen/RxBanner/blob/master/README.md)&nbsp;&nbsp;|&nbsp;&nbsp;[中文文档](https://github.com/leveychen/RxBanner/blob/master/README_CN.md)



## 预览
![](http://wx1.sinaimg.cn/mw690/0060lm7Tly1fqaqqlrawxg30bo06tb2b.gif)&nbsp;&nbsp;&nbsp;&nbsp;
![](http://wx1.sinaimg.cn/mw690/0060lm7Tly1fqat74eq9yg30bo06t4qq.gif)

## 引入
### 1.添加 jitpack
```xml
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

### 2.导入引用      [![](https://jitpack.io/v/leveychen/RxBanner.svg)](https://jitpack.io/#leveychen/RxBanner)

```xml
    implementation 'com.github.leveychen:RxBanner:x.x.x'
```
[LATEST RELEASE](https://github.com/leveychen/RxBanner/releases/latest)

## 用法
### 示例
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

在 `start()` 之前必须设置好 image loader 和 setDatas

#### 各种 image loader 栗子

[Fresco](https://github.com/leveychen/RxBanner/blob/master/app/src/main/java/cn/levey/rxbanner/loader/FrescoLoader.java)&nbsp;&nbsp;&nbsp;&nbsp;
[Glide](https://github.com/leveychen/RxBanner/blob/master/app/src/main/java/cn/levey/rxbanner/loader/GlideLoader.java)&nbsp;&nbsp;&nbsp;&nbsp;
[Picasso](https://github.com/leveychen/RxBanner/blob/master/app/src/main/java/cn/levey/rxbanner/loader/PicassoLoader.java)&nbsp;&nbsp;&nbsp;&nbsp;
[UniversalImageLoader](https://github.com/leveychen/RxBanner/blob/master/app/src/main/java/cn/levey/rxbanner/loader/UniversalImageLoader.java)
### 各类监听器
````java
    .setOnBannerClickListener(new RxBannerClickListener())
    .setOnBannerChangeListener(new RxBannerChangeListener())
    .setOnBannerTitleClickListener(new RxBannerTitleClickListener())
````

### 自定义指示器
如果觉得自带的指示器效果不好，自己找一个指示器设置好监听然后丢进去
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


## 全局设置
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

## 生命周期
更好的 在 Activity 、 Fragment 和其他 view 中管理生命周期
````code
    banner.onResume()
    banner.onPause()
    banner.onDestroy() // 非必需, onDetachedFromWindow 里面已经处理
````


## 属性
所有属性均以 `rb_` 开头
### Banner
|属性|格式|初始值|描述
|---|---|---|---|
|rb_infinite|boolean|true|是否无限循环,关闭无限循环就是 `引导页` ,了解一下
|rb_timeInterval|integer (`millisecond`)|5000| 200毫秒以上
|rb_orientation|horizontal / vertical|horizontal| /
|rb_itemPercent|integer|100| 宽度或者高度的百分比，取决于 `rb_orientation` 方向
|rb_itemScale|float|1|缩放比
|rb_itemSpace|dimension|0|两个item之间的间距
|rb_centerAlpha|float|1|中间 item 透明度
|rb_sideAlpha|float|1|旁边 item 透明度
|rb_itemMoveSpeed|float|1|滑动速度
|rb_orderType|asc / desc|asc| 升序或者降序排列
|rb_viewPaperMode|boolean|true|跟 `ViewPaper` 一样，一次滑动一页



### 标题
|属性|格式|初始值|描述
|---|---|---|---|
|rb_title_visible|boolean|true| /
|rb_title_gravity|gravity|START| /
|rb_title_layout_gravity|gravity|CENTER_HORIZONTAL and BOTTOM| /
|rb_title_margin|dimension|0dp| margin
|rb_title_padding|dimension|3dp| padding
|rb_title_width|dimension / enum|MATCH_PARENT| /
|rb_title_height|dimension / enum|WRAP_CONTENT| /
|rb_title_size|dimension|14sp| /
|rb_title_color|color|Color.WHITE| /
|rb_title_backgroundColor|color|#55000000| /
|rb_title_backgroundResource|reference| / | /
|rb_title_marquee|boolean|true| 跑马灯



### 指示器
|属性|格式|初始值|描述
|---|---|---|---|
|rb_indicator_visible|boolean|true| /
|rb_indicator_clickable|boolean|true| 是否可以点击指示器来切换banner
|rb_indicator_orientation|horizontal / vertical|horizontal|/
|rb_indicator_layout_gravity|gravity|BOTTOM / END|/
|rb_indicator_radius|dimension|5dp|指示器大小
|rb_indicator_size|dimension|5dp|同上
|rb_indicator_scale|float|0.7|  当 `rb_indicator_animationType` 为 `scale` or `scale_down` 用于调整缩放比
|rb_indicator_margin|dimension|8dp| margin
|rb_indicator_padding|dimension|3dp| padding
|rb_indicator_selected_color|color|#FFFFFF|  /
|rb_indicator_unselected_color|color|#33FFFFFF|  /
|rb_indicator_interactiveAnimation|boolean|false|  /
|rb_indicator_animationDuration|integer|350|毫秒
|rb_indicator_animationType|type|none|see `AnimationType`
|rb_indicator_rtl_mode|on / off / auto|auto|支持左右自动布局 [`RTL` ](https://android-developers.googleblog.com/2013/03/native-rtl-support-in-android-42.html)

### AnimationType
这里抄的，了解一下 [PageIndicatorView](https://github.com/romandanylyk/PageIndicatorView)

|名称|属性| 预览
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