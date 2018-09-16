package cn.levey.rxbanner.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.levey.bannerlib.RxBanner;
import cn.levey.rxbanner.R;
import cn.levey.rxbanner.adapter.RvAdapter;
import cn.levey.rxbanner.fake.DemoConfig;
import cn.levey.rxbanner.fake.FakeData;
import cn.levey.rxbanner.fake.RvItemData;
import cn.levey.rxbanner.fake.Sys;
import cn.levey.rxbanner.loader.FrescoLoader;


/**
 * Created by Levey on 2018/4/2 17:38.
 * e-mail: m@levey.cn
 */

public class RecyclerViewActivity extends AppCompatActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private  RxBanner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        RvAdapter adapter = new RvAdapter(R.layout.rv_item_data, getFakeList());
        recyclerView.setAdapter(adapter);


        DemoConfig config;
        try {
            config = (DemoConfig) getIntent().getSerializableExtra(Sys.BANNER_DATA);
        } catch (Exception e) {
            config = new DemoConfig();
        }

        // add banner by inflate view
        final View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.rv_header_view,null,false);
        banner = v.findViewById(R.id.rv_header_banner);
        banner.setLoader(new FrescoLoader(config.getCornersRadius(),config.isRoundAsCircle()))
                .setConfig(config)
                .setDatas(new ArrayList<>(FakeData.FAKE_DATA()))
                .start();
        adapter.addHeaderView(v);
    }

    private List<RvItemData> getFakeList(){
        List<RvItemData> list = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            RvItemData item = new RvItemData();
            item.title = "这是标题 " + (i + 1);
            item.content = "这是文章正文内容这是文章正文内容这是文章正文内容这是文章正文内容这是文章正文内容" + (i + 1);
            item.author = "作者" + (i + 1);
            list.add(item);
        }
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.onResume();
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
}
