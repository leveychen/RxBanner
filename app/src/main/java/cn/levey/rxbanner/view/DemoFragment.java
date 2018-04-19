package cn.levey.rxbanner.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.levey.bannerlib.RxBanner;
import cn.levey.bannerlib.data.RxBannerConfig;
import cn.levey.rxbanner.R;
import cn.levey.rxbanner.fake.FakeData;
import cn.levey.rxbanner.loader.FrescoLoader;

/**
 * Created by Levey on 2018/4/10 14:47.
 * e-mail: m@levey.cn
 */

public class DemoFragment extends Fragment {

    public static final String FRAGMENT_ID = "FRAGMENT_ID";

    private static final float ASPECT_RATIO_16_9 = (float)16/9;
    private static final float ASPECT_RATIO_4_3 = (float)4/3;

    @BindView(R.id.banner_view)
    RxBanner bannerView;
    @BindView(R.id.fragment_id)
    TextView tvFragmentId;
    Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int fragmentId = 1;
        if(getArguments() != null) fragmentId = getArguments().getInt(FRAGMENT_ID, 1);
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        if(view == null) throw new NullPointerException("just kidding");
        unbinder = ButterKnife.bind(this, view);
        RxBannerConfig config = bannerView.getConfig();
        config.setAspectRatio(fragmentId == 1  ? ASPECT_RATIO_16_9 : ASPECT_RATIO_4_3);
        bannerView
                .setConfig(config)
                .setLoader(new FrescoLoader())
                .setDatas(FakeData.FAKE_DATA()) // no title
                .start();
        tvFragmentId.setText("Fragment " + fragmentId);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        bannerView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        bannerView.onResume();
    }
}
