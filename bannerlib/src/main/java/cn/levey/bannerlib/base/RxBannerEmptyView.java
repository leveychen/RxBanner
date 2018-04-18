package cn.levey.bannerlib.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import cn.levey.bannerlib.R;

/**
 * Created by Levey on 2018/4/18 11:07.
 * e-mail: m@levey.cn
 */
public class RxBannerEmptyView extends android.support.v7.widget.AppCompatImageView {
    public RxBannerEmptyView(Context context) {
        this(context,null);
    }

    public RxBannerEmptyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RxBannerEmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.textString = context.getResources().getString(R.string.rb_empty_view_string);
    }

    protected String textString = "";

    public void setImageResId(int resId){
        this.textString = "";
        setImageResource(resId);
        drawableStateChanged();
    }

    public void setText(String s){
        if(s == null || s.isEmpty() || s.equals("")) {
            this.textString = getContext().getResources().getString(R.string.rb_empty_view_string);
        }else {
            this.textString = s;
        }
        setImageResource(0);
        drawableStateChanged();
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!textString.equals("")) {
            Rect targetRect = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3);
            paint.setTextSize(80);
            paint.setColor(Color.TRANSPARENT);
            canvas.drawRect(targetRect, paint);
            paint.setColor(Color.parseColor("#BEBEBE"));
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(textString, targetRect.centerX(), baseline, paint);
        }
    }
}
