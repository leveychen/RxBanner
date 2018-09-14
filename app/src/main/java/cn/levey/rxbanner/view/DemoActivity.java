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
import cn.levey.bannerlib.impl.RxBannerChangeListener;
import cn.levey.bannerlib.impl.RxBannerClickListener;
import cn.levey.bannerlib.impl.RxBannerGuideFinishedListener;
import cn.levey.bannerlib.indicator.animation.type.AnimationType;
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
    @BindView(R.id.btn_update)
    Button btnUpdate;
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
            config = (DemoConfig) banner.getConfig();
        }

        if(config.getIndicatorConfig().getAnimationType() == AnimationType.CUSTOM){
            setTitle("CUSTOM - RxBanner");
        }


//        config.setItemMoveSpeed(0.5f);
//        config.setOrientation(LinearLayout.VERTICAL);
//        config.setViewPaperMode(false);


        //添加图片资源


//        ArrayList<Integer> images = new ArrayList<>();
//        images.add(R.mipmap.ic_launcher);
//        images.add(R.mipmap.ic_launcher);
//        images.add(R.mipmap.ic_launcher);
//        images.add(R.mipmap.ic_launcher);

        // disable marquee to support html tag
        config.setTitleMarquee(false);
//        config.setFlingDamping(20f);
//        config.setItemMoveSpeed(0.1f);
//        config.setViewPaperMode(false);

//        config.setCanSwipe(false);

//        config.setCanSwipeWhenSingle(false);

        for (int i = 0; i < images.size(); i++) {
            // html tag text
//            titles.add("<h1><font color=\"#FF0000\">banner title big</font></h1><br/> small title  " + (i + 1));
            titles.add("banner=" + (i + 1));
        }
//        banner.setLoader(new UniversalImageLoader())
//        banner.setLoader(new PicassoLoader())
//        banner.setLoader(new GlideLoader())

        banner.setLoader(new FrescoLoader(config.getCornersRadius(), config.isRoundAsCircle()));


//        RxBannerConfig config = banner.getConfig();
//        config.setTitleColorResource(getApplicationContext(),R.color.colorPrimary);
//        config.getIndicatorConfig().setSelectedColorResource(getApplicationContext(),R.color.colorAccent);
//        config.setAutoPlay(false);
//        config.setInfinite(true);
//        banner.setConfig(config);
        
//        config.setTimeInterval(50000);


        banner
                .setConfig(config)
                .setDatas(images, titles)

//               .setDatas(images)  // no title
//                .setOnBannerTitleClickListener(new RxBannerTitleClickListener() {
//                    @Override
//                    public void onTitleClick(int position, String title) {
//                        Toast.makeText(getApplicationContext(), "TITLE : " + position + " / " + title, Toast.LENGTH_SHORT).show();
//                    }
//                })
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
                    banner.forceStart();
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
                if(!banner.isDatasEmpty()) {
                    int cp = banner.getCurrentPosition();
                    banner.updateData(
                            FakeData.FAKE_GUIDE[cp > FakeData.FAKE_GUIDE.length - 1 ? 0 : cp],
                            "Guide Title\n Updated " + (cp + 1),
                            cp);
                }
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
