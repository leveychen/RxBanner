package cn.levey.rxbanner.activity;

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
import cn.levey.bannerlib.impl.RxBannerTitleClickListener;
import cn.levey.rxbanner.R;
import cn.levey.rxbanner.fake.FakeData;
import cn.levey.rxbanner.loader.FrescoLoader;


/**
 * Created by Levey on 2018/4/2 17:38.
 * e-mail: m@levey.cn
 */

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.rx_banner)
    RxBanner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        ArrayList<String> titles = new ArrayList<>();
        //添加图片资源
        ArrayList<String> list = new ArrayList<>(Arrays.asList(FakeData.FAKE_NUM));

//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(R.mipmap.ic_launcher);
//        list.add(R.mipmap.ic_launcher);
//        list.add(R.mipmap.ic_launcher);
//        list.add(R.mipmap.ic_launcher);

        for (int i = 0; i < list.size(); i++) {
            titles.add("banner title " + (i + 1));
        }
//        banner.setLoader(new UniversalImageLoader())
//        banner.setLoader(new PicassoLoader())
//        banner.setLoader(new GlideLoader())

        banner.setLoader(new FrescoLoader())
                .setDatas(list, titles)
//                .setDatas(list)  // no title
                .setOnBannerClickListener(new RxBannerClickListener() {

                    @Override
                    public void onItemClick(int position, Object data) {
                        Toast.makeText(getApplicationContext(), "Click : " + position, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(int position, Object data) {
                        Toast.makeText(getApplicationContext(), "LONG : " + position, Toast.LENGTH_SHORT).show();
                    }
                })

                .setOnBannerChangeListener(new RxBannerChangeListener() {
                    @Override
                    public void onBannerSelected(int position) {
                        //  RxBannerLogger.i("onBannerSelected = " + position);
                    }

                    @Override
                    public void onBannerScrollStateChanged(int state) {

                    }
                })
                .setOnBannerTitleClickListener(new RxBannerTitleClickListener() {
                    @Override
                    public void onTitleClick(int position) {
                        Toast.makeText(getApplicationContext(), "TITLE : " + position, Toast.LENGTH_SHORT).show();
                    }
                })
                .start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.onPause();
    }

//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        banner.onDestroy();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.onResume();
    }
}
