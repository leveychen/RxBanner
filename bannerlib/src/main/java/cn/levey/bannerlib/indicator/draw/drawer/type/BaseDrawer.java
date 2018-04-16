package cn.levey.bannerlib.indicator.draw.drawer.type;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;

class BaseDrawer {

    Paint paint;
    IndicatorConfig indicatorConfig;

    BaseDrawer(@NonNull Paint paint, @NonNull IndicatorConfig indicatorConfig) {
        this.paint = paint;
        this.indicatorConfig = indicatorConfig;
    }
}
