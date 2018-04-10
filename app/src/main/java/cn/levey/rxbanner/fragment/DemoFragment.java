package cn.levey.rxbanner.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.levey.bannerlib.RxBanner;
import cn.levey.rxbanner.R;
import cn.levey.rxbanner.fake.FakeData;
import cn.levey.rxbanner.loader.FrescoLoader;

/**
 * Created by Levey on 2018/4/10 14:47.
 * e-mail: m@levey.cn
 */

public class DemoFragment extends Fragment {

    public static final String FRAGMENT_ID = "FRAGMENT_ID";

    @BindView(R.id.banner_view)
    RxBanner bannerView;
    @BindView(R.id.fragment_id)
    TextView tvFragmentId;
    Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int fragmentId = 1;
        if(getArguments() != null) {
            fragmentId = getArguments().getInt(FRAGMENT_ID, 1);
        }
        View view = null;
        if(fragmentId == 1){
            view = inflater.inflate(R.layout.fragment_view_01, container, false);
        }
        if(fragmentId ==2){
            view = inflater.inflate(R.layout.fragment_view_02, container, false);
        }
        if(view == null){
            throw new NullPointerException("just demo");
        }
        unbinder = ButterKnife.bind(this, view);
        ArrayList<String> list = new ArrayList<>();
        switch (fragmentId) {
            case 1:
                list.addAll(Arrays.asList(FakeData.FAKE_IMAGES_01));
                break;
            case 2:
                list.addAll(Arrays.asList(FakeData.FAKE_IMAGES_02));
                break;
        }
        bannerView
                .setLoader(new FrescoLoader())
                .setDatas(list)
                .start();
        tvFragmentId.setText("Fragment " + fragmentId);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bannerView.onDestroy();
        unbinder.unbind();

    }
}
