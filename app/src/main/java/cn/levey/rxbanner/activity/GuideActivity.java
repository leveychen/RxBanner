package cn.levey.rxbanner.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.levey.bannerlib.RxBanner;
import cn.levey.bannerlib.base.RxBannerLogger;
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

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.rx_banner)
    RxBanner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        //添加图片资源
        ArrayList<String> list = new ArrayList<>(Arrays.asList(FakeData.FAKE_NUM));
//
//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(R.mipmap.ic_launcher);
//        list.add(R.mipmap.ic_launcher);
//        list.add(R.mipmap.ic_launcher);
//        list.add(R.mipmap.ic_launcher);
//        banner.setLoader(new UniversalImageLoader())
//        banner.setLoader(new PicassoLoader())
//        banner.setLoader(new GlideLoader())

        banner.setLoader(new FrescoLoader())
                .setDatas(list)
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
                    public void onGuideFinished() {
                        RxBannerLogger.i(" onGuideFinished ");
                        Toast.makeText(getApplicationContext(), "Guide Finished" , Toast.LENGTH_SHORT).show();
                        finish();

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
