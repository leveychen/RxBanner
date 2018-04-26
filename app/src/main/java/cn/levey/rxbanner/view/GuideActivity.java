package cn.levey.rxbanner.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.levey.bannerlib.RxBanner;
import cn.levey.bannerlib.impl.RxBannerChangeListener;
import cn.levey.bannerlib.impl.RxBannerClickListener;
import cn.levey.rxbanner.R;
import cn.levey.rxbanner.fake.FakeData;
import cn.levey.rxbanner.fake.Sys;
import cn.levey.rxbanner.loader.PicassoLoader;


/**
 * Created by Levey on 2018/4/2 17:38.
 * e-mail: m@levey.cn
 */

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.rx_banner)
    RxBanner banner;


    /**
     * 引导页的三个要点
     * Guide page, three points
     *
     *  rb_infinite="false"
     *  rb_autoPlay="false"
     *
     *  setOnBannerChangeListener(new RxBannerChangeListener);
     *      onGuideFinished()
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        ArrayList<String> list = new ArrayList<>(Arrays.asList(FakeData.FAKE_GUIDE));

        banner.setOnBannerClickListener(new RxBannerClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Toast.makeText(getApplicationContext(),"ssssss",Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onItemLongClick(int position, Object data) {

            }
        });


        banner.setLoader(new PicassoLoader())
                .setDatas(list) // no title
                .setCurrentPosition(3)
                .setOnBannerChangeListener(new RxBannerChangeListener() {

                    @Override
                    public void onBannerSelected(int position) {

                    }

                    @Override
                    public void onBannerScrollStateChanged(int state) {

                    }

                    @Override
                    public void onGuideFinished() {
                        Sys.toast(getApplicationContext(),"Guide Finished");
                        finish();

                    }
                })
                .start();


    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        banner.onDestroy();
    }
}
