package cn.levey.rxbanner;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;

import cn.levey.bannerlib.utils.RxBannerManager;

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
        initRxBanner();
    }

    private void initFresco(){
        Fresco.initialize(this);
    }

    private void initLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {return;}
        LeakCanary.install(this);
    }

    private void initRxBanner(){
        RxBannerManager.getInstance()
                .setDebug(true);
//                .setLoader(new RxBannerImageViewLoader()); // global loader
    }
}
