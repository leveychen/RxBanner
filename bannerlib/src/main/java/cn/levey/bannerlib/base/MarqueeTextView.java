package cn.levey.bannerlib.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class MarqueeTextView extends android.support.v7.widget.AppCompatTextView {

    private boolean isFocused = false;

    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
