package cn.levey.bannerlib.indicator.draw.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import cn.levey.bannerlib.indicator.animation.data.Value;
import cn.levey.bannerlib.indicator.draw.data.IndicatorConfig;
import cn.levey.bannerlib.indicator.draw.drawer.type.*;

public class Drawer {

    private BasicDrawer basicDrawer;
    private ColorDrawer colorDrawer;
    private ScaleDrawer scaleDrawer;
    private WormDrawer wormDrawer;
    private SlideDrawer slideDrawer;
    private FillDrawer fillDrawer;
    private ThinWormDrawer thinWormDrawer;
    private DropDrawer dropDrawer;
    private SwapDrawer swapDrawer;
    private ScaleDownDrawer scaleDownDrawer;

    private int position;
    private int coordinateX;
    private int coordinateY;

    public Drawer(@NonNull IndicatorConfig indicatorConfig) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        basicDrawer = new BasicDrawer(paint, indicatorConfig);
        colorDrawer = new ColorDrawer(paint, indicatorConfig);
        scaleDrawer = new ScaleDrawer(paint, indicatorConfig);
        wormDrawer = new WormDrawer(paint, indicatorConfig);
        slideDrawer = new SlideDrawer(paint, indicatorConfig);
        fillDrawer = new FillDrawer(paint, indicatorConfig);
        thinWormDrawer = new ThinWormDrawer(paint, indicatorConfig);
        dropDrawer = new DropDrawer(paint, indicatorConfig);
        swapDrawer = new SwapDrawer(paint, indicatorConfig);
        scaleDownDrawer = new ScaleDownDrawer(paint, indicatorConfig);
    }

    public void setup(int position, int coordinateX, int coordinateY) {
        this.position = position;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public void drawBasic(@NonNull Canvas canvas, boolean isSelectedItem) {
        if (colorDrawer != null) {
            basicDrawer.draw(canvas, position, isSelectedItem, coordinateX, coordinateY);
        }
    }

    public void drawColor(@NonNull Canvas canvas, @NonNull Value value) {
        if (colorDrawer != null) {
            colorDrawer.draw(canvas, value, position, coordinateX, coordinateY);
        }
    }

    public void drawScale(@NonNull Canvas canvas, @NonNull Value value) {
        if (scaleDrawer != null) {
            scaleDrawer.draw(canvas, value, position, coordinateX, coordinateY);
        }
    }

    public void drawWorm(@NonNull Canvas canvas, @NonNull Value value) {
        if (wormDrawer != null) {
            wormDrawer.draw(canvas, value, coordinateX, coordinateY);
        }
    }

    public void drawSlide(@NonNull Canvas canvas, @NonNull Value value) {
        if (slideDrawer != null) {
            slideDrawer.draw(canvas, value, coordinateX, coordinateY);
        }
    }

    public void drawFill(@NonNull Canvas canvas, @NonNull Value value) {
        if (fillDrawer != null) {
            fillDrawer.draw(canvas, value, position, coordinateX, coordinateY);
        }
    }

    public void drawThinWorm(@NonNull Canvas canvas, @NonNull Value value) {
        if (thinWormDrawer != null) {
            thinWormDrawer.draw(canvas, value, coordinateX, coordinateY);
        }
    }

    public void drawDrop(@NonNull Canvas canvas, @NonNull Value value) {
        if (dropDrawer != null) {
            dropDrawer.draw(canvas, value, coordinateX, coordinateY);
        }
    }

    public void drawSwap(@NonNull Canvas canvas, @NonNull Value value) {
        if (swapDrawer != null) {
            swapDrawer.draw(canvas, value, position, coordinateX, coordinateY);
        }
    }

    public void drawScaleDown(@NonNull Canvas canvas, @NonNull Value value) {
        if (scaleDownDrawer != null) {
            scaleDownDrawer.draw(canvas, value, position, coordinateX, coordinateY);
        }
    }
}
