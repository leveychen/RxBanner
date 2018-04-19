package cn.levey.rxbanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.SparseArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.levey.bannerlib.base.RxBannerLogger;
import cn.levey.rxbanner.fake.DemoConfig;
import cn.levey.rxbanner.fake.Sys;
import cn.levey.rxbanner.view.DemoActivity;
import cn.levey.rxbanner.view.FragmentActivity;
import cn.levey.rxbanner.view.GuideActivity;


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
    Button titleGravity;
    @BindView(R.id.title_layout_gravity)
    Button titleLayoutGravity;
    @BindView(R.id.indicator_layout_gravity)
    Button indicatorLayoutGravity;
    @BindView(R.id.autoPlay)
    Switch autoPlay;
    @BindView(R.id.infinite)
    Switch infinite;
    @BindView(R.id.title_visible)
    Switch titleVisible;
    @BindView(R.id.indicator_visible)
    Switch indicatorVisible;
    @BindView(R.id.banner_image)
    Button bannerImage;


    private MainActivity activity;
    private CharSequence[] gravityItems;
    private SparseArray<Integer[]> indicesArray = new SparseArray<>();
    private DemoConfig config = new DemoConfig();


    /**
     * 此处代码与 RxBanner 无关，请直接跳转去看
     *
     * @link activity_demo.xml
     * @link activity_guide.xml
     * @see DemoActivity
     * @see GuideActivity
     * <p>
     * 对应布局查看
     */
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
        setGravityValueListener(indicatorLayoutGravity, new Integer[]{2, 4});

        bannerImage.setText(bannerImage.getTag() + " CornersRadius = " + config.getCornersRadius() + (config.isRoundAsCircle() ? "f, RoundAsCircle" : "f"));
        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(activity)
                        .title("Image View Config")
                        .content("CornersRadius ( float, dp )")
                        .inputType(
                                InputType.TYPE_CLASS_NUMBER)
                        .positiveText("OK")
                        .input("" + config.getCornersRadius(), "" + config.getCornersRadius(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                 config.setCornersRadius(Float.parseFloat(input.toString()));
                            }
                        })
                        .checkBoxPrompt("isRoundAsCircle", config.isRoundAsCircle(), new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                config.setRoundAsCircle(isChecked);
                            }
                        })
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                bannerImage.setText(bannerImage.getTag() + " CornersRadius = " + config.getCornersRadius() + (config.isRoundAsCircle() ? "f, RoundAsCircle" : "f"));
                            }
                        })
                        .show();
            }
        });
    }

    public void setGravityValueListener(final Button btn, final Integer[] defaultIndices) {
        indicesArray.put(btn.getId(), defaultIndices);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(activity)
                        .title(btn.getTag().toString())
                        .cancelable(false)
                        .items(gravityItems)
                        .itemsCallbackMultiChoice(indicesArray.get(btn.getId()), new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                return true;
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (dialog.getSelectedIndices() != null && dialog.getSelectedIndices().length > 0) {
                                    indicesArray.put(btn.getId(), dialog.getSelectedIndices());
                                }

                                StringBuilder g = new StringBuilder();
                                for (int i = 0; i < indicesArray.get(btn.getId()).length; i++) {
                                    if (i == indicesArray.get(btn.getId()).length - 1) {
                                        g.append(gravityItems[indicesArray.get(btn.getId())[i]]);
                                    } else {
                                        g.append(gravityItems[indicesArray.get(btn.getId())[i]]).append(" | ");
                                    }
                                }
                                String tempStr = btn.getTag().toString() + " " + g;
                                btn.setText(tempStr);
                                RxBannerLogger.i(btn.getTag().toString() + " " + g + "  " + Sys.getGravity(indicesArray.get(btn.getId())));
                                dialog.dismiss();
                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                indicesArray.put(btn.getId(), defaultIndices);
                                dialog.setSelectedIndices(indicesArray.get(btn.getId()));
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

    private int getConfigGravity(Button btn) {
        return Sys.getGravity(indicesArray.get(btn.getId()));
    }


    private void setCreateBtn() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //banner
                config.setAutoPlay(autoPlay.isChecked());
                config.setInfinite(infinite.isChecked());

                //title
                config.setTitleVisible(titleVisible.isChecked());
                config.setTitleGravity(getConfigGravity(titleGravity));
                config.setTitleLayoutGravity(getConfigGravity(titleLayoutGravity));

                //indicator
                config.setIndicatorVisible(indicatorVisible.isChecked());
                config.getIndicatorConfigConfig().setGravity(getConfigGravity(indicatorLayoutGravity));

                String type = spinner.getSelectedItem().toString();
                switch (type) {
                    case "Activity":
                        Intent acty = new Intent(getApplicationContext(), DemoActivity.class);
                        acty.putExtra(Sys.BANNER_DATA, config);
                        startActivity(acty);
                        break;
                    case "Fragment":
                        Intent frag = new Intent(getApplicationContext(), FragmentActivity.class);
                        startActivity(frag);
                        break;
                    case "ScrollView":
                        Intent scroll = new Intent(getApplicationContext(), DemoActivity.class);
                        scroll.putExtra(Sys.BANNER_DATA, config);
                        scroll.putExtra(DemoActivity.NEED_SCROLL_VIEW, true);
                        startActivity(scroll);
                        break;
                    case "Guide/引导页":
                        Intent splash = new Intent(getApplicationContext(), GuideActivity.class);
                        startActivity(splash);
                        break;
                }
            }
        });
    }
}
