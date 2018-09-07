package cn.levey.rxbanner.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.levey.bannerlib.RxBanner;
import cn.levey.rxbanner.R;

/**
 * Created by Levey on 2018/8/31 11:39.
 * e-mail: m@levey.cn
 */
public class CustomIndicatorActivity extends AppCompatActivity {

    @BindView(R.id.banner_view_fresco)
    RxBanner bannerViewFresco;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_indicator);
        ButterKnife.bind(this);
    }
}
