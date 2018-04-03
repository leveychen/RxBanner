package cn.levey.rxbanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.levey.bannerlib.RxBanner;
import cn.levey.rxbanner.fake.FakeData;
import cn.levey.rxbanner.loader.FrescoLoader;


/**
 * Created by Levey on 2018/4/2 17:38.
 * e-mail: m@levey.cn
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.banner_view_fresco)
    RxBanner banner;
    @BindView(R.id.btn_preview)
    Button btnPreview;
    @BindView(R.id.btn_next)
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ArrayList<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(FakeData.FAKE_IMAGES));
        banner.setLoader(new FrescoLoader()); //custom loader
        banner.setDatas(list);  // set data



    }
}
