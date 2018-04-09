package cn.levey.rxbanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.levey.bannerlib.RxBanner;
import cn.levey.bannerlib.impl.RxBannerClickListener;
import cn.levey.rxbanner.loader.FrescoLoader;
import jp.wasabeef.recyclerview.animators.LandingAnimator;


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
    @BindView(R.id.btn_auto)
    Button btnAuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ArrayList<String> list = new ArrayList<>();
//        list.addAll(Arrays.asList(FakeData.FAKE_IMAGES));

        for (int i = 1; i < 60; i++) {
            list.add("http://hcd.levey.cn/img/" + i + ".jpg");
        }
//        banner.setLoader(new GlideLoader())
        banner.setLoader(new FrescoLoader())
                .setDatas(list)
                .setOnBannerClickListener(new RxBannerClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getApplicationContext(),"Click : " + position,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                        Toast.makeText(getApplicationContext(),"LONG : " + position,Toast.LENGTH_SHORT).show();
                    }
                })
                .setItemAnimator(new LandingAnimator())
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
}
