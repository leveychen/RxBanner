package cn.levey.bannerlib.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class RxBannerTextView extends android.support.v7.widget.AppCompatTextView {

    private boolean isFocused = false;

    public RxBannerTextView(Context context) {
        super(context);
    }

    public RxBannerTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RxBannerTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
    }
}
