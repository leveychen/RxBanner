package cn.levey.bannerlib.indicator;

import android.support.v7.widget.RecyclerView;

/**
 * Created by tohure on 19/01/18.
 */

class IndicatorDataObserver extends RecyclerView.AdapterDataObserver {

    private final DotIndicator dotIndicator;

    IndicatorDataObserver(DotIndicator dotIndicator) {
        this.dotIndicator = dotIndicator;
    }

    @Override
    public void onChanged() {
        dotIndicator.updateIndicatorsCount();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        dotIndicator.updateIndicatorsCount();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        dotIndicator.updateIndicatorsCount();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        dotIndicator.updateIndicatorsCount();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        dotIndicator.updateIndicatorsCount();
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        dotIndicator.updateIndicatorsCount();
    }
}