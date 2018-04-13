package cn.levey.rxbanner;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.leakcanary.LeakCanary;

import cn.levey.bannerlib.base.RxBannerConfig;

/**
 * Created by Levey on 2018/4/2 17:38.
 * e-mail: m@levey.cn
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        initLeakCanary();
        initFresco();
        initUniversalImageLoader();
        initRxBanner();

    }

    private void initFresco(){
        Fresco.initialize(this);
    }

    private void initLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {return;}
        LeakCanary.install(this);
    }

    private void initUniversalImageLoader(){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
			.build();
        ImageLoader.getInstance().init(config);
    }

    private void initRxBanner(){
        RxBannerConfig.getInstance()
                .setDebug(true);
//                .setScrollStateChangedListener(new RxBannerScrollStateChangedListener() {
//                    @Override
//                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                        // fresco
//                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                            Fresco.getImagePipeline().resume();
//                        }else {
//                            Fresco.getImagePipeline().pause();
//                        }
//                        // glide
//                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                            Glide.with(getApplicationContext()).resumeRequests();
//                        }else {
//                            Glide.with(getApplicationContext()).pauseRequests();
//                        }
//                        // your image loader
//                    }
//                })
//                .setLoader(new RxBannerImageViewLoader())
//                .setOrientation(LinearLayout.HORIZONTAL)
//                .setOrderType(RxBannerConfig.OrderType.ASC)
//                .setTimeInterval(5000);
    }


}
