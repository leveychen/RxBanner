package cn.levey.bannerlib.data;

import java.io.Serializable;

/**
 * Created by Levey on 2018/4/2 15:34.
 * e-mail: m@levey.cn
 */

public class RxBannerData implements Serializable {

    private String title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
