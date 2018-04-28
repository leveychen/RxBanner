package cn.levey.bannerlib.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

public class RxBannerTitle extends android.support.v7.widget.AppCompatTextView{

    private List<String> mTitles = new ArrayList<>();
    private boolean isFocused = false;
    private int currentPosition = 0;

    public RxBannerTitle(Context context) {
        super(context);
    }

    public RxBannerTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RxBannerTitle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
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


    public void updateItem(String title, int position){
        if(title !=null && !title.isEmpty()) {
            this.mTitles.set(position,title);
            if(position == currentPosition) {
                set(position);
            }
        }
    }


    public void addDatas(List<String> titles){
        if(titles !=null && !titles.isEmpty()) {
            this.mTitles.addAll(titles);
        }
    }

    public void addData(String title){
        if(title !=null && !title.isEmpty()) {
            this.mTitles.add(title);
        }
    }

    public void addData(String title, int position){
        if(title !=null && !title.isEmpty()) {
            this.mTitles.add(position,title);
        }
    }

    public void setSelection(int position){
        set(position);
    }

    protected void set(int position){
        this.currentPosition = position;
        try {
            setText(Html.fromHtml(mTitles.get(position)));
            setText(mTitles.get(position).contains("\n") ? mTitles.get(position) : Html.fromHtml(mTitles.get(position)));
            setVisibility(VISIBLE);
        } catch (Exception e) {
            setVisibility(GONE);
        }
    }

    public void removeData(int position){
        mTitles.remove(position);
    }
}
