package cn.levey.bannerlib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Levey on 2018/4/18 11:07.
 * e-mail: m@levey.cn
 * 绘制圆形的背景
 */
public class RxBannerNumericIndicator extends android.support.v7.widget.AppCompatTextView {

    private int totalSize = 0;
    private int currentPosition = 0;
    private boolean isCircle = true;

    private Paint mBgPaint = new Paint();

    PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);

    public RxBannerNumericIndicator(Context context) {
        this(context,null);
    }

    public RxBannerNumericIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RxBannerNumericIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBgPaint.setAntiAlias(true);

    }

    public void setTotal(int size){
        this.totalSize = size;
        setSelection(currentPosition);
    }

    @SuppressLint("SetTextI18n")
    public void setSelection(int position){
        this.currentPosition = position;
        if(position >=0 && position < totalSize){
            setText(getStr(position + 1) + "/" + totalSize);
            if(getVisibility() != VISIBLE) setVisibility(VISIBLE);
        }else {
            if(getVisibility() != GONE) setVisibility(GONE);
        }
    }

    protected String getStr(int index){
        if(totalSize < 10){
            return "" + index;
        }else if( totalSize < 99) {
            if(index < 10){
                return  "0" + index;
            }else {
                return "" + index;
            }
        }
        return "" + index;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(isCircle) {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            int max = Math.max(measuredWidth, measuredHeight);
            setMeasuredDimension(max, max);
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        if(isCircle) {
            mBgPaint.setColor(color);
        }else {
            super.setBackgroundColor(color);
        }
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);

    }

    @Override
    public void draw(Canvas canvas) {
        if(isCircle)  {
            canvas.setDrawFilter(pfd);
            canvas.drawCircle(getWidth()/2, getHeight()/2, Math.max(getWidth(), getHeight())/2, mBgPaint);
        }
        super.draw(canvas);
    }

    public void setCircle(boolean circle) {
        isCircle = circle;
    }
}
