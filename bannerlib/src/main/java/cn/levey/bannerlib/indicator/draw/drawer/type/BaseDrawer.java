package cn.levey.bannerlib.indicator.draw.drawer.type;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import cn.levey.bannerlib.indicator.draw.data.Indicator;

class BaseDrawer {

    Paint paint;
    Indicator indicator;

    BaseDrawer(@NonNull Paint paint, @NonNull Indicator indicator) {
        this.paint = paint;
        this.indicator = indicator;
    }
}
