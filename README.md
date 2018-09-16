# RxBanner
[![](https://jitpack.io/v/leveychen/RxBanner.svg)](https://jitpack.io/#leveychen/RxBanner)    [![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)       [![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

一个灵活可制定的基于 Recyclerview 的轮播图控件,支持自动轮播,无限循环。

同时可关闭无限循环变成 `引导页` ，带有引导完成接口回调,详见demo


[中文文档](https://github.com/leveychen/RxBanner/blob/master/README.md)&nbsp;&nbsp;&nbsp;[English](https://github.com/leveychen/RxBanner/blob/master/README_EN.md)


## 更新日志
### 1.1.3
1.完善一些点击事件的逻辑

2.新增当 `rb_canSwipe = false` 时的点击事件响应

3.修复 `自定义指示器` 刷新数据时没有默认选中的问题
### 1.1.2
1.新增 `引导页` 监听 `RxBannerGuideFinishedListener` ,调整引导页功能实现

2.新增单张图片时可否滑动设置 `rb_canSwipeWhenSingle`, 仅单张图片时有效
### 1.1.1
1.新增滑动阻尼设置 `rb_flingDamping`, 阻尼越大滑动越困难,默认为RecyclerView标准最小滑动值. 修复 [issues #2](https://github.com/leveychen/RxBanner/issues/2)

2.新增`自定义指示器`,用法与说明请下翻至 `指示器` 部分, 由 [CircleIndicator](https://github.com/ongakuer/CircleIndicator) 修改整合,感谢原作者, 原始需求 [issues #3](https://github.com/leveychen/RxBanner/issues/3)

## Demo Apk

fir.im &nbsp;&nbsp;&nbsp;&nbsp; [rxbanner_demo.apk](https://fir.im/rxbanner)

github &nbsp;&nbsp;&nbsp;&nbsp; [rxbanner_demo.apk](https://github.com/leveychen/RxBanner/releases/download/1.1.3/rxbanner_v1.1.3_demo.apk)


## 预览
![](http://wx1.sinaimg.cn/mw690/0060lm7Tly1fqaqqlrawxg30bo06tb2b.gif)&nbsp;&nbsp;&nbsp;&nbsp;![](http://wx1.sinaimg.cn/mw690/0060lm7Tly1fqat74eq9yg30bo06t4qq.gif)

![](http://wx3.sinaimg.cn/mw690/0060lm7Tly1fqrjdjt47zg307i0dcb2a.gif)&nbsp;&nbsp;&nbsp;&nbsp;![](http://wx3.sinaimg.cn/mw690/0060lm7Tly1fqriet88rxg307i0dcdn3.gif)



## 引用
### 1.添加 jitpack
```xml
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

### 2.导入引用&nbsp;&nbsp;&nbsp;&nbsp;[![](https://jitpack.io/v/leveychen/RxBanner.svg)](https://jitpack.io/#leveychen/RxBanner)

```xml
    implementation 'com.github.leveychen:RxBanner:1.1.3'
```
[LATEST RELEASE](https://github.com/leveychen/RxBanner/releases/latest)

## 用法
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


#### 一些 `image loader` 的栗子

[Fresco](https://github.com/leveychen/RxBanner/blob/master/app/src/main/java/cn/levey/rxbanner/loader/FrescoLoader.java)&nbsp;&nbsp;&nbsp;&nbsp;
[Glide](https://github.com/leveychen/RxBanner/blob/master/app/src/main/java/cn/levey/rxbanner/loader/GlideLoader.java)&nbsp;&nbsp;&nbsp;&nbsp;
[Picasso](https://github.com/leveychen/RxBanner/blob/master/app/src/main/java/cn/levey/rxbanner/loader/PicassoLoader.java)&nbsp;&nbsp;&nbsp;&nbsp;
[UniversalImageLoader](https://github.com/leveychen/RxBanner/blob/master/app/src/main/java/cn/levey/rxbanner/loader/UniversalImageLoader.java)

可以愉快的玩耍了,下面是一些可选内容

#### `配置信息` -可选
自定义配置信息，所有参数设置均和 `xml` 布局里一致，优先级高于 `xml` 布局文件
````java
    RxBannerConfig config = banner.getConfig();
    config.set(value);
    ...
    banner.setConfig(config);
    banner.start();
````

setConfig() 必须在 `setDatas()` 和 `start()` 之前设置。

#### `监听器` -可选
````java
    点击图片时的回调
    banner.setOnBannerClickListener(new RxBannerClickListener())
        onItemClick(int position, Object data)
        onItemLongClick(int position, Object data)

    //点击标题时的回调
    banner.setOnBannerTitleClickListener(new RxBannerTitleClickListener())
        onTitleClick(int position, String title);

    //图片切换时的回调
    banner.setOnBannerChangeListener(new RxBannerChangeListener())
        onBannerSelected(int position)
        onBannerScrollStateChanged(int state)

    //一旦定义引导页监听就可变成引导页
    banner.setOnGuideFinishedListener(new RxBannerGuideFinishedListener()
        onGuideFinished()

````


#### `生命周期`
更好的 在 Activity 、 Fragment 和其他 view 中管理生命周期
````java
    banner.onResume()
    banner.onPause()
    banner.onDestroy()
````

## 全局设置 -可选
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

## 引导页
定义一下布局就行了

[activity_guide.xml](https://github.com/leveychen/RxBanner/blob/master/app/src/main/res/layout/activity_guide.xml)

为了更好的引导页体验,最好关闭无限循环和自动轮播
````xml
    banner:rb_infinite="false"
    banner:rb_autoPlay="false"
````
然后监听一下回调
````java
    banner.setOnGuideFinishedListener(new RxBannerGuideFinishedListener()
        onGuideFinished()
        // todo
````
大功告成

## 属性
所有属性均以 `rb_` 开头
### Banner
|属性|格式|初始值|描述
|---|---|---|---|
|rb_autoPlay|boolean|true|以 `rb_timeInterval` 的间隔自动播放
|rb_infinite|boolean|true|是否无限循环,关闭无限循环并且设置`引导页回调`即可变成`引导页`
|rb_canSwipe|boolean|true|是否允许手动滑动，此设置与自动轮播无关
|rb_canSwipeWhenSingle|boolean|true|单张图片时是否能手动滑动,此设置与自动轮播无关
|rb_aspectRatio|float|/| 宽高比，数值必须大于0，拿16:9为例就是1.7778，高度等于宽度则为1，同时 `android:layout_height` 不能为 `wrap_content` 或者 `match_parent`。需要随便定义一个数值， 比如 `1dp`。当 `orientation = vertical` 且在`ScrollView`中时，高度不推荐大于父容器，否则可能滑动被拦截，导致且在`ScrollView`中时无法滑动
|rb_timeInterval|integer (`millisecond`)|5000| 200毫秒以上
|rb_orientation|horizontal / vertical|horizontal| /
|rb_itemPercent|integer|100| 宽度或者高度的百分比，取决于 `rb_orientation` 方向
|rb_itemScale|float|1|缩放比
|rb_itemSpace|dimension|0|两个item之间的间距
|rb_centerAlpha|float|1|中间 item 透明度
|rb_sideAlpha|float|1|旁边 item 透明度
|rb_itemMoveSpeed|float|1|滑动速度
|rb_flingDamping|float|1|滑动阻尼，越大越不容易滑动
|rb_orderType|asc / desc|asc| 升序或者降序排列
|rb_viewPaperMode|boolean|true|跟 `ViewPaper` 一样，一次滑动一页
|rb_emptyViewText|string|'暂无图片'| 当图片为空且 'rb_emptyViewResource' 没有定义时
|rb_emptyViewResource|reference| / | 当图片为空时显示



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
|rb_title_lineSpacingMultiplier|float|1.0f| lineSpacingMultiplier



### 指示器
|属性|格式|初始值|描述
|---|---|---|---|
|rb_indicator_visible|boolean|true| /
|rb_indicator_clickable|boolean|true| 是否可以点击指示器来切换banner
|rb_indicator_orientation|horizontal / vertical|horizontal|/
|rb_indicator_layout_gravity|gravity|BOTTOM / END|/
|rb_indicator_radius|dimension|5dp|指示器大小
|rb_indicator_textSize|dimension|14sp|`numeric` `numeric_circle` 专用
|rb_indicator_textColor|color|Color.WHITE|`numeric` `numeric_circle` 专用
|rb_indicator_backgroundColor|color|/|`numeric` `numeric_circle` 专用
|rb_indicator_backgroundResource|reference|/|`numeric` `numeric_circle` 专用
|rb_indicator_scale|float|0.7|  `scale` or `scale_down` 专用
|rb_indicator_margin|dimension|8dp| margin
|rb_indicator_padding|dimension|3dp| padding
|rb_indicator_selected_color|color|#FFFFFF|  /
|rb_indicator_unselected_color|color|#33FFFFFF|  /
|rb_indicator_interactiveAnimation|boolean|false|  /
|rb_indicator_animationDuration|integer|350|毫秒
|rb_indicator_animationType|type|none|see `AnimationType`
|rb_indicator_rtl_mode|on / off / auto|auto|支持左右自动布局 [`RTL` ](https://android-developers.googleblog.com/2013/03/native-rtl-support-in-android-42.html)
|rb_indicator_selectedResource|reference|/| `custom` 专用,用法参考 [`CircleIndicator` ](https://github.com/ongakuer/CircleIndicator)
|rb_indicator_unselectedResource|reference|/| `custom` 专用,用法参考  [`CircleIndicator` ](https://github.com/ongakuer/CircleIndicator)
|rb_indicator_animatorResource|reference|/|`custom` 专用,用法参考  [`CircleIndicator` ](https://github.com/ongakuer/CircleIndicator)
|rb_indicator_animatorReverseResource|reference|/|`custom` 专用,用法参考  [`CircleIndicator` ](https://github.com/ongakuer/CircleIndicator)

### AnimationType
这里抄的，了解一下 [PageIndicatorView](https://github.com/romandanylyk/PageIndicatorView) / [CircleIndicator](https://github.com/ongakuer/CircleIndicator)

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
|`AnimationType.NUMERIC`|`numeric`| 1/8
|`AnimationType.NUMERIC_CIRCLE`|`numeric_circle`| (1/8)
|`AnimationType.CUSTOM`|`custom`| 参考 [`CircleIndicator` ](https://github.com/ongakuer/CircleIndicator)



## 权限
显示网络图片
````xml
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
````

## 混淆
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