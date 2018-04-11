package cn.levey.rxbanner.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.levey.rxbanner.R;
import cn.levey.rxbanner.fragment.DemoFragment;
import cn.levey.rxbanner.fragment.FragAdapter;


/**
 * Created by Levey on 2018/4/2 17:38.
 * e-mail: m@levey.cn
 */

public class FragmentActivity extends AppCompatActivity {


    @BindView(R.id.view_paper)
    ViewPager viewPaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_demo);
        ButterKnife.bind(this);
        setTitle("Fragment - RxBanner");


        List<Fragment> fragments=new ArrayList<>();

        DemoFragment demo1 = new DemoFragment();
        Bundle b1 = new Bundle();
        b1.putInt(DemoFragment.FRAGMENT_ID,1);
        demo1.setArguments(b1);
        fragments.add(demo1);

        DemoFragment demo2 = new DemoFragment();
        Bundle b2 = new Bundle();
        b1.putInt(DemoFragment.FRAGMENT_ID,2);
        demo2.setArguments(b2);
        fragments.add(demo2);

        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);
        viewPaper.setAdapter(adapter);
    }
}
