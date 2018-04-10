package cn.levey.rxbanner.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
import cn.levey.rxbanner.loader.FrescoLoader;


/**
 * Created by Levey on 2018/4/2 17:38.
 * e-mail: m@levey.cn
 */

public class DemoActivity extends AppCompatActivity {

    @BindView(R.id.banner_view_fresco)
    RxBanner banner;
    @BindView(R.id.btn_preview)
    Button btnPreview;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.btn_auto)
    Button btnAuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
        setTitle("Activity - RxBanner");
        ArrayList<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(FakeData.FAKE_IMAGES_01));
//        banner.setLoader(new GlideLoader())
        banner.setLoader(new FrescoLoader())
                .setDatas(list,list)
                .setOnBannerClickListener(new RxBannerClickListener() {
                    @Override
                    public void onItemClick(int position, Object data) {
                        Toast.makeText(getApplicationContext(),"Click : " + position,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(int position, Object data) {
                        Toast.makeText(getApplicationContext(),"LONG : " + position,Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnBannerChangeListener(new RxBannerChangeListener() {
                    @Override
                    public void onBannerSelected(int position) {
                      //  RxBannerLogger.i(" OUT onInnerBannerSelected = " + position);
                    }

                    @Override
                    public void onBannerScrollStateChanged(int state) {

                    }
                })
                .start();


        if (banner.isAutoPlay()) {
            btnAuto.setText("Pause");
        } else {
            btnAuto.setText("Start");
        }

        btnAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnAuto.getText().toString().equals("Pause")) {
                    btnAuto.setText("Start");
                    banner.pause();
                } else {
                    btnAuto.setText("Pause");
                    banner.start();
                }
            }
        });

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banner.setCurrentPosition(banner.getCurrentPosition() - 1);
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banner.setCurrentPosition(banner.getCurrentPosition() + 1);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        banner.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.start();
    }
}
