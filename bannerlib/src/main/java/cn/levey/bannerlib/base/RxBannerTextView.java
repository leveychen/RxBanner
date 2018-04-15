package cn.levey.bannerlib.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import cn.levey.bannerlib.impl.RxBannerTitleChangeListener;
import cn.levey.bannerlib.manager.ViewPagerLayoutManager;

public class RxBannerTextView extends android.support.v7.widget.AppCompatTextView implements RxBannerTitleChangeListener{

    private List<String> mTitles = new ArrayList<>();

    private boolean isFocused = false;
    private int lastPosition = -1;

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

    @Override
    public void onBannerSelected(int position) {
        set(position);
    }

    public List<String> getTitleDatas() {
        return mTitles;
    }

    public void setDatas(List<String> titles) {
        if(titles !=null && !titles.isEmpty()) {
            this.mTitles.clear();
            this.mTitles.addAll(titles);
            set(0);
        }else {
            this.mTitles.clear();
            setVisibility(GONE);
        }
    }

    public void addDatas(List<String> titles){
        if(titles !=null && !titles.isEmpty()) {
            this.mTitles.addAll(titles);
        }
    }

    public void setSelection(int position){
        set(position);
    }

    protected void set(int position){
        if(lastPosition == position) return;
        lastPosition = position;
        try {
            setText(mTitles.get(position));
            setVisibility(VISIBLE);
        } catch (Exception e) {
            setVisibility(GONE);
        }
    }

    public void setLayoutManager(ViewPagerLayoutManager layoutManager){
        if(layoutManager != null) layoutManager.setRxBannerTitleChangeListener(this);
    }
}
