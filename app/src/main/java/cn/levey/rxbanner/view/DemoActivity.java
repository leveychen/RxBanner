package cn.levey.rxbanner.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.levey.bannerlib.RxBanner;
import cn.levey.bannerlib.base.RxBannerLogger;
import cn.levey.rxbanner.R;
import cn.levey.rxbanner.fake.FakeData;
import cn.levey.rxbanner.fake.GankBean;
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
    @BindView(R.id.btn_network)
    Button btnNetwork;
    private int mPage = 1;
    private ArrayList<String> images = new ArrayList<>(Arrays.asList(FakeData.FAKE_IMAGES_02));
    private ArrayList<String> titles = new ArrayList<>();
    private DemoActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
        activity = this;
        setTitle("Activity - RxBanner");
        if (getIntent().getBooleanExtra(NEED_SCROLL_VIEW, false)) {
            view01.setVisibility(View.VISIBLE);
            view02.setVisibility(View.VISIBLE);
            setTitle("ScrollView - RxBanner");
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

       // RxBannerConfig config = new RxBannerConfig();
        //config.setIndicatorVisible(true);

    //    config.setTitleWidthPx(ViewGroup.LayoutParams.WRAP_CONTENT);
        banner.setLoader(new FrescoLoader());
//        RxBannerConfig config = banner.getConfig();
////        config.setTitleColorResource(getApplicationContext(),R.color.colorPrimary);
////        config.getIndicatorConfigConfig().setSelectedColorResource(getApplicationContext(),R.color.colorAccent);
////        config.setAutoPlay(false);
////        config.setInfinite(true);
//        banner.setConfig(config);
//        banner.setDatas(images, titles)
//                .setDatas(images)  // no title
//                .setOnBannerClickListener(new RxBannerClickListener() {
//
//                    @Override
//                    public void onItemClick(int position, Object data) {
//                        Toast.makeText(getApplicationContext(), "Click : " + position, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onItemLongClick(int position, Object data) {
//                        Toast.makeText(getApplicationContext(), "LONG : " + position, Toast.LENGTH_SHORT).show();
//                    }
//                })
//
//                .setOnBannerChangeListener(new RxBannerChangeListener() {
//                    @Override
//                    public void onBannerSelected(int position) {
//                    }
//
//                    @Override
//                    public void onBannerScrollStateChanged(int state) {
//
//                    }
//
//                    @Override
//                    public void onGuideFinished() {
//                        Toast.makeText(getApplicationContext(), "GUIDE FINISHED", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .start();


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

                final MaterialDialog dialog  = new MaterialDialog.Builder(activity)
                        .title("正在获取Banner")
                        .cancelable(false)
                        .content("请稍候...")
                        .positiveText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                OkGo.getInstance().cancelAll();
                            }
                        })
                        .progress(true,0)
                        .build();

                dialog.show();

                 int size = (int)Math.round(Math.random()*10 + 1);

                final String pageUrl = "http://gank.io/api/data/福利/" + size + "/" + mPage++;
                OkGo.<String>get(pageUrl).execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        GankBean gank = JSON.parseObject(response.body(),GankBean.class);
                        images.clear();
                        titles.clear();
                        for (int i = 0; i < gank.getResults().size(); i++) {
                            images.add(gank.getResults().get(i).getUrl());
                            titles.add("Image ID =  " + (i + 1));
                        }
                        banner.setDatas(images,titles);
                        dialog.dismiss();
                    }
                });
                if(mPage > 10) mPage = 1;
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
