# RxBanner
[![](https://jitpack.io/v/leveychen/RxBanner.svg)](https://jitpack.io/#leveychen/RxBanner)[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

Flexible banner base on RecyclerView

## Integration
### Step 1. Add it in your root build.gradle at the end of repositories:
```xml
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

### Step 2. Add the dependency

```xml
    implementation 'com.github.leveychen:RxBanner:x.x.x'
```
[Latest Release](https://github.com/leveychen/RxBanner/releases/latest)

## Usage
in `xml`
````xml
    <cn.levey.bannerlib.RxBanner
        android:id="@+id/rx_banner"
        android:layout_width="match_parent"
        android:layout_height="160dp"/>
````

in `Java`
````java
    banner = findViewById(R.id.rx_banner);
    banner
        .setLoader(new ImageLoader())
        .setDatas(imageUrls, titles)
        .start();
````

you MUST set a image loader before start,then call `start()` method



## Attributes
All the `rb_` attributes here are specific for RxBanner
## banner item
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



## title
|Attributes|format|default|description
|---|---|---|---|
|rb_title_visible|boolean|true| view visibility
|rb_title_gravity|gravity|CENTER| text gravity
|rb_title_layout_gravity|gravity|CENTER_HORIZONTAL and BOTTOM|layout gravity
|rb_title_margin|dimension|0dp| margin
|rb_title_padding|dimension|0dp| padding
|rb_title_width|dimension / enum|MATCH_PARENT| /
|rb_title_height|dimension / enum|WRAP_CONTENT| /
|rb_title_size|dimension|14sp| /
|rb_title_color|color|Color.WHITE| /
|rb_title_backgroundColor|color|#55000000| /
|rb_title_backgroundResource|reference| / | /
|rb_title_marquee|boolean|true| /



### Permission
````xml
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
````