package cn.levey.rxbanner.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.levey.bannerlib.RxBanner;
import cn.levey.bannerlib.base.RxBannerLogger;
import cn.levey.bannerlib.impl.RxBannerChangeListener;
import cn.levey.bannerlib.impl.RxBannerClickListener;
import cn.levey.bannerlib.impl.RxBannerTitleClickListener;
import cn.levey.rxbanner.R;
import cn.levey.rxbanner.fake.DemoConfig;
import cn.levey.rxbanner.fake.FakeData;
import cn.levey.rxbanner.fake.Sys;
import cn.levey.rxbanner.loader.FrescoLoader;


/**
 * Created by Levey on 2018/4/2 17:38.
 * e-mail: m@levey.cn
 */

public class DemoActivity extends AppCompatActivity {

    public static final String NEED_SCROLL_VIEW = "NEED_SCROLL_VIEW";

    @BindView(R.id.banner_view_fresco)
    RxBanner banner;
    @BindView(R.id.btn_preview)
    Button btnPreview;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.btn_auto)
    Button btnAuto;
    @BindView(R.id.view_01)
    LinearLayout view01;
    @BindView(R.id.view_02)
    LinearLayout view02;
    @BindView(R.id.view_03)
    LinearLayout view03;
    @BindView(R.id.btn_network)
    Button btnNetwork;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_remove)
    Button btnRemove;
    private List<String> images = FakeData.FAKE_DATA();
    private ArrayList<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
        setTitle("Activity - RxBanner");
        if (getIntent().getBooleanExtra(NEED_SCROLL_VIEW, false)) {
            view01.setVisibility(View.VISIBLE);
            view02.setVisibility(View.VISIBLE);
            view03.setVisibility(View.VISIBLE);
            setTitle("ScrollView - RxBanner");
        }

        DemoConfig config;
        try {
            config = (DemoConfig) getIntent().getSerializableExtra(Sys.BANNER_DATA);
        } catch (Exception e) {
            config = new DemoConfig();
        }


        //添加图片资源


//        ArrayList<Integer> images = new ArrayList<>();
//        images.add(R.mipmap.ic_launcher);
//        images.add(R.mipmap.ic_launcher);
//        images.add(R.mipmap.ic_launcher);
//        images.add(R.mipmap.ic_launcher);

        for (int i = 0; i < images.size(); i++) {
            titles.add("banner title " + (i + 1));
        }
//        banner.setLoader(new UniversalImageLoader())
//        banner.setLoader(new PicassoLoader())
//        banner.setLoader(new GlideLoader())

        banner.setLoader(new FrescoLoader(config.getCornersRadius(), config.isRoundAsCircle()));


//        RxBannerConfig config = banner.getConfig();
////        config.setTitleColorResource(getApplicationContext(),R.color.colorPrimary);
////        config.getIndicatorConfigConfig().setSelectedColorResource(getApplicationContext(),R.color.colorAccent);
////        config.setAutoPlay(false);
////        config.setInfinite(true);
//        banner.setConfig(config);


        RxBannerLogger.i(" G = " + config.getTitleGravity());
//        config.setTimeInterval(50000);

        config.setAutoPlay(false);
        banner
                .setConfig(config)
                .setDatas(images, titles)

//               .setDatas(images)  // no title
                .setOnBannerTitleClickListener(new RxBannerTitleClickListener() {
                    @Override
                    public void onTitleClick(int position, String title) {
                        Toast.makeText(getApplicationContext(), "TITLE : " + position + " / " + title, Toast.LENGTH_SHORT).show();
                    }
                })
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

                    }

                    @Override
                    public void onBannerScrollStateChanged(int state) {

                    }

                    @Override
                    public void onGuideFinished() {
                        Toast.makeText(getApplicationContext(), "GUIDE FINISHED", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();

        banner.setCurrentPosition(2);


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
                    banner.forceStart();
                }
            }
        });

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RxBannerLogger.i(" btnPreview = " + banner.getCurrentPosition());
                banner.setCurrentPosition(banner.getCurrentPosition() - 1);
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banner.setCurrentPosition(banner.getCurrentPosition() + 1);
            }
        });

        btnNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                images.clear();
                titles.clear();
                images.addAll(FakeData.FAKE_DATA());
                for (int i = 0; i < images.size(); i++) {
                    titles.add(" banner " + (i + 1));
                }
                banner.setDatas(images, titles);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banner.updateData(
                        "http://wx1.sinaimg.cn/large/0060lm7Tly1fqav85d0j4j30u01hcgma.jpg?id=1",
                        "Guide Title 1",
                        0);

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banner.addData(
                        "http://wx1.sinaimg.cn/large/0060lm7Tly1fqav85d0j4j30u01hcgma.jpg?id=9",
                        "Guide Title LAST",0);
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banner.removeData(0);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        banner.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.onResume();
    }
}
