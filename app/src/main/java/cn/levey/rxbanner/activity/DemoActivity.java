package cn.levey.rxbanner.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

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
import cn.levey.bannerlib.base.RxBannerConfig;
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
    private int fuliPage = 1;
    private ArrayList<String> list = new ArrayList<>(Arrays.asList(FakeData.FAKE_IMAGES_02));
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

       // RxBannerConfig config = new RxBannerConfig();
        //config.setIndicatorVisible(true);

    //    config.setTitleWidthPx(ViewGroup.LayoutParams.WRAP_CONTENT);
        banner.setLoader(new FrescoLoader());
        RxBannerConfig config = banner.getConfig();
        config.setTitleColorResource(getApplicationContext(),R.color.colorPrimary);
        config.getIndicatorConfigConfig().setSelectedColorResource(getApplicationContext(),R.color.colorAccent);
        config.setAutoPlay(false);
        config.setInfinite(false);
        banner.setConfig(config);
        banner.setDatas(list, titles)
//                .setDatas(list)  // no title
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
//                        RxBannerLogger.i("onBannerSelected = " + position);
//                    }
//
//                    @Override
//                    public void onBannerScrollStateChanged(int state) {
//
//                    }
//                })
//                .setOnBannerTitleClickListener(new RxBannerTitleClickListener() {
//                    @Override
//                    public void onTitleClick(int position) {
//                        Toast.makeText(getApplicationContext(), "TITLE : " + position, Toast.LENGTH_SHORT).show();
//                    }
//                })
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
                        .progress(true,0)
                        .build();

                dialog.show();

                 int size = (int)Math.round(Math.random()*10 + 1);

                if(fuliPage % 3 == 0) size = 1;

                if(fuliPage == 1) size = 1;
                final String pageUrl = "http://gank.io/api/data/福利/" + size + "/" + fuliPage++;
                RxBannerLogger.i(" UPAGE = " + pageUrl);
                OkGo.<String>get(pageUrl).execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        GankBean gank = JSON.parseObject(response.body(),GankBean.class);
                        list.clear();
                        titles.clear();



                        for (int i = 0; i < gank.getResults().size(); i++) {
                            list.add(gank.getResults().get(i).getUrl());
                            titles.add("福利 " + i);

                            RxBannerLogger.i("UUU = " + gank.getResults().get(i).getUrl());
                        }

                        RxBannerLogger.i("LS = " + list.size() + " TS = " + titles.size());
                        banner.setDatas(list,titles);
                        dialog.dismiss();
                    }
                });
                if(fuliPage > 10) fuliPage = 1;
            }
        });
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        banner.onPause();
//    }

//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        banner.onDestroy();
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        banner.onResume();
//    }
}
