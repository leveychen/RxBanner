package cn.levey.bannerlib.manager;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Levey on 2018/9/7 14:23.
 * e-mail: m@levey.cn
 */
public class ScrollHelper {

    public static void smoothScrollToPosition(RecyclerView recyclerView, ViewPagerLayoutManager viewPagerLayoutManager, int targetPosition) {
        final int delta = viewPagerLayoutManager.getOffsetToPosition(targetPosition);
        if (viewPagerLayoutManager.getOrientation() == ViewPagerLayoutManager.VERTICAL) {
            recyclerView.smoothScrollBy(0, delta);
        } else {
            recyclerView.smoothScrollBy(delta, 0);
        }
    }

    public static void smoothScrollToTargetView(RecyclerView recyclerView, View targetView) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (!(layoutManager instanceof ViewPagerLayoutManager)) return;
        final int targetPosition = ((ViewPagerLayoutManager) layoutManager).getLayoutPositionOfView(targetView);
        smoothScrollToPosition(recyclerView, (ViewPagerLayoutManager) layoutManager, targetPosition);
    }
}