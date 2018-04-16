package cn.levey.rxbanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.levey.bannerlib.base.RxBannerLogger;
import cn.levey.rxbanner.activity.DemoActivity;
import cn.levey.rxbanner.activity.FragmentActivity;
import cn.levey.rxbanner.activity.FullActivity;
import cn.levey.rxbanner.activity.SplashActivity;
import cn.levey.rxbanner.fake.Sys;


/**
 * Created by Levey on 2018/4/2 17:38.
 * e-mail: m@levey.cn
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.btn_create)
    Button btnCreate;
    @BindView(R.id.title_gravity)
    TextView titleGravity;
    @BindView(R.id.title_layout_gravity)
    TextView titleLayoutGravity;

    private MainActivity activity;
    private CharSequence[] gravityItems;
    private SparseArray<Integer[]> indicesArray = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        ButterKnife.bind(this);
        gravityItems = getApplicationContext().getResources().getTextArray(R.array.gravity_items);
        String[] mItems = getResources().getStringArray(R.array.banner_parent_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        setCreateBtn();
        setGravityValueListener(titleGravity, new Integer[]{3});
        setGravityValueListener(titleLayoutGravity, new Integer[]{2, 5});
    }

    public void setGravityValueListener(final TextView tv, final Integer[] defaultIndices) {
        indicesArray.put(tv.getId(), defaultIndices);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(activity)
                        .title(tv.getTag().toString())
                        .cancelable(false)
                        .items(gravityItems)
                        .itemsCallbackMultiChoice(indicesArray.get(tv.getId()), new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                return true;
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                indicesArray.put(tv.getId(), dialog.getSelectedIndices());
                                StringBuilder g = new StringBuilder();
                                for (int i = 0; i < indicesArray.get(tv.getId()).length; i++) {
                                    if (i == indicesArray.get(tv.getId()).length - 1) {
                                        g.append(gravityItems[indicesArray.get(tv.getId())[i]]);
                                    } else {
                                        g.append(gravityItems[indicesArray.get(tv.getId())[i]]).append(" | ");
                                    }
                                }
                                String tempStr = tv.getTag().toString() + " " + g;
                                tv.setText(tempStr);
                                RxBannerLogger.i(tv.getTag().toString() + " " + g + "  " + Sys.getGravity(indicesArray.get(tv.getId())));
                                dialog.dismiss();
                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                indicesArray.put(tv.getId(), defaultIndices);
                                dialog.setSelectedIndices(indicesArray.get(tv.getId()));
                            }
                        })
                        .alwaysCallMultiChoiceCallback()
                        .positiveText("OK")
                        .autoDismiss(false)
                        .neutralText("RESET")
                        .show();
            }
        });
    }

    private void setCreateBtn() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = spinner.getSelectedItem().toString();
                switch (type) {
                    case "Activity":
                        Intent acty = new Intent(getApplicationContext(), DemoActivity.class);
                        startActivity(acty);
                        break;
                    case "Fragment":
                        Intent frag = new Intent(getApplicationContext(), FragmentActivity.class);
                        startActivity(frag);
                        break;
                    case "ScrollView":
                        Intent scroll = new Intent(getApplicationContext(), DemoActivity.class);
                        scroll.putExtra(DemoActivity.NEED_SCROLL_VIEW, true);
                        startActivity(scroll);
                        break;
                    case "FullScreen":
                        Intent full = new Intent(getApplicationContext(), FullActivity.class);

                        startActivity(full);
                        break;
                    case "Splash":
                        Intent splash = new Intent(getApplicationContext(), SplashActivity.class);
                        startActivity(splash);
                        break;
                }
            }
        });
    }
}
