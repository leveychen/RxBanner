package cn.levey.rxbanner;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.xw.repo.BubbleSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.levey.bannerlib.base.RxBannerLogger;
import cn.levey.bannerlib.base.RxBannerUtil;
import cn.levey.rxbanner.fake.DemoConfig;
import cn.levey.rxbanner.fake.Sys;
import cn.levey.rxbanner.view.DemoActivity;
import cn.levey.rxbanner.view.FragmentActivity;
import cn.levey.rxbanner.view.GuideActivity;
import cn.levey.rxbanner.view.RecyclerViewActivity;


/**
 * Created by Levey on 2018/4/2 17:38.
 * e-mail: m@levey.cn
 */

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity implements ColorChooserDialog.ColorCallback {

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
    @BindView(R.id.canSwipe)
    Switch canSwipe;
    @BindView(R.id.orientation)
    Button orientation;
    @BindView(R.id.orderType)
    Button orderType;
    @BindView(R.id.item_percent)
    Button itemPercent;
    @BindView(R.id.item_scale)
    Button itemScale;
    @BindView(R.id.item_space)
    Button itemSpace;
    @BindView(R.id.side_alpha)
    Button sideAlpha;
    @BindView(R.id.global_color)
    Button globalColor;
    @BindView(R.id.default_banner)
    Button defaultBanner;
    @BindView(R.id.default_scale)
    Button defaultScale;
    @BindView(R.id.default_guide)
    Button defaultGuide;
    @BindView(R.id.animationType)
    Button animationType;
    @BindView(R.id.time_interval)
    Button timeInterval;
    @BindView(R.id.about)
    Button about;


    private MainActivity activity;
    private CharSequence[] gravityItems;
    private SparseArray<Integer[]> indicesArray = new SparseArray<>();
    private SparseArray<float[]> optionalValue = new SparseArray<>();
    private SparseIntArray optionalType = new SparseIntArray();
    private DemoConfig config = new DemoConfig();
    private int selectedColor = Color.parseColor("#FFFFFF");


    /**
     * 此处代码与 RxBanner 无关,仅传入相关配置，详细请跳转至对应内容
     *
     * @link activity_demo.xml
     * @link activity_guide.xml
     * @see DemoActivity
     * @see GuideActivity
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        ButterKnife.bind(this);
        gravityItems = getResArray(R.array.gravity_items);
        String[] mItems = getResources().getStringArray(R.array.banner_parent_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        setDefaultBtn();
        setCreateBannerBtn();
        setGravityValueListener(titleGravity, new Integer[]{3});
        setGravityValueListener(titleLayoutGravity, new Integer[]{2, 5});
        setGravityValueListener(indicatorLayoutGravity, new Integer[]{2, 4});

        setOptionalValue(itemPercent, new float[]{10f, 100f, 100f});
        setOptionalValue(itemScale, new float[]{0.1f, 1.0f, 1.0f});
        setOptionalValue(itemSpace, new float[]{-50, 50, 0});
        setOptionalValue(sideAlpha, new float[]{0.1f, 1.0f, 1.0f});
        setOptionalValue(timeInterval, new float[]{200, 10000, 5000});

        setOptionalType(orientation, getResArray(R.array.rb_orientation));
        setOptionalType(orderType, getResArray(R.array.rb_orderType));
        setOptionalType(animationType, getResArray(R.array.indicator_animationType));
        animationType.setText(animationType.getTag().toString() + Sys.getAnimationTypeStr(0));


        bannerImage.setText(bannerImage.getTag() + "radius " + ((int) config.getCornersRadius()) + (config.isRoundAsCircle() ? ",circle" : ""));
        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(activity)
                        .title("Image View Config")
                        .content("CornersRadius ( float, dp )")
                        .inputType(
                                InputType.TYPE_CLASS_NUMBER)
                        .positiveText("DONE")
                        .input("" + config.getCornersRadius(), "" + ((int) config.getCornersRadius()), new MaterialDialog.InputCallback() {
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
                                bannerImage.setText(bannerImage.getTag() + "radius " + ((int) config.getCornersRadius()) + (config.isRoundAsCircle() ? ",circle" : ""));
                            }
                        })
                        .show();
            }
        });
    }

    private CharSequence[] getResArray(int res) {
        return getApplicationContext().getResources().getTextArray(res);
    }


    private void setOptionalType(final Button btn, final CharSequence[] items) {
        optionalType.put(btn.getId(), 0);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(activity)
                        .title(btn.getTag().toString())
                        .cancelable(false)
                        .items(items)
                        .itemsCallbackSingleChoice(optionalType.get(btn.getId()), new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                return false;
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                optionalType.put(btn.getId(), dialog.getSelectedIndex());
                                if (btn.equals(orientation)) {
                                    btn.setText(btn.getTag().toString() + (dialog.getSelectedIndex() == 0 ? "horizontal" : "vertical"));
                                }

                                if (btn.equals(orderType)) {
                                    btn.setText(btn.getTag().toString() + (dialog.getSelectedIndex() == 0 ? "asc" : "desc"));
                                }

                                if (btn.equals(animationType)) {
                                    btn.setText(btn.getTag().toString() + (Sys.getAnimationTypeStr(dialog.getSelectedIndex())));
                                }
                                dialog.dismiss();
                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.setSelectedIndex(0);
                            }
                        })
                        .alwaysCallMultiChoiceCallback()
                        .positiveText("DONE")
                        .autoDismiss(false)
                        .neutralText("RESET")
                        .show();
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void setOptionalValue(final Button btn, final float[] values) {
        optionalValue.put(btn.getId(), values);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                MaterialDialog dialog = new MaterialDialog.Builder(activity)
                        .title(btn.getTag().toString())
                        .customView(R.layout.dialog_seekbar, false)
                        .cancelable(false)
                        .positiveText("DONE")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                btn.setText(btn.getTag().toString() + (getValue(btn) > 1.0f || getValue(btn) <= 0 ? ((int) getValue(btn)) : getValue(btn)));
                                dialog.dismiss();
                            }
                        })
                        .autoDismiss(false)
                        .build();


                final BubbleSeekBar seekBar = (BubbleSeekBar) dialog.findViewById(R.id.seek_bar);
                seekBar.getConfigBuilder()
                        .min(values[0])
                        .max(values[1])
                        .progress(values[2])
                        .touchToSeek()
                        .build();

                seekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
                    @Override
                    public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                        float[] tv = optionalValue.get(btn.getId());
                        tv[2] = progressFloat;
                        optionalValue.put(btn.getId(), tv);
                    }

                    @Override
                    public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

                    }

                    @Override
                    public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                    }
                });
                dialog.show();
            }
        });

    }

    private void setGravityValueListener(final Button btn, final Integer[] defaultIndices) {
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
                        .positiveText("DONE")
                        .autoDismiss(false)
                        .neutralText("RESET")
                        .show();
            }
        });
    }

    private int getConfigGravity(Button btn) {
        return Sys.getGravity(indicesArray.get(btn.getId()));
    }

    private float getValue(Button btn) {
        return optionalValue.get(btn.getId())[2];
    }


    private void setCreateBannerBtn() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //banner
                config.setAutoPlay(autoPlay.isChecked());
                config.setInfinite(infinite.isChecked());
                config.setCanSwipe(canSwipe.isChecked());
                config.setItemPercent((int) getValue(itemPercent));
                config.setItemScale(getValue(itemScale));
                config.setItemSpaceDp((int) getValue(itemSpace));
                config.setSideAlpha(getValue(sideAlpha));
                config.setTimeInterval((int) getValue(timeInterval));
                config.setOrderType(RxBannerUtil.getOrder(optionalType.get(orderType.getId()) + 1));
                config.setOrientation(optionalType.get(orientation.getId()));
                config.setTitleColor(selectedColor);
                config.getIndicatorConfigConfig().setSelectedColor(selectedColor);
                config.getIndicatorConfigConfig().setAnimationType(Sys.getAnimationType(optionalType.get(animationType.getId())));
                config.getIndicatorConfigConfig().setTextColor(selectedColor);

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
                        Intent demo = new Intent(getApplicationContext(), DemoActivity.class);
                        demo.putExtra(Sys.BANNER_DATA, config);
                        startActivity(demo);
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
                    case "HeaderView":
                        Intent rv = new Intent(getApplicationContext(), RecyclerViewActivity.class);
                        rv.putExtra(Sys.BANNER_DATA, config);
                        startActivity(rv);
                        break;
                    case "Guide/引导页":
                        Intent guide = new Intent(getApplicationContext(), GuideActivity.class);
                        startActivity(guide);
                        break;
                }
            }
        });
    }

    private void setDefaultBtn() {
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(activity)
                        .title("RxBanner")
                        .cancelable(false)
                        .content("Demo Version: " + BuildConfig.VERSION_NAME)
                        .positiveText("DONE")
                        .neutralText("Github")
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri github = Uri.parse("https://github.com/leveychen/RxBanner");
                                intent.setData(github);
                                startActivity(intent);
                            }
                        })
                        .build()
                        .show();

            }
        });
        defaultBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent demo = new Intent(getApplicationContext(), DemoActivity.class);
                demo.putExtra(Sys.BANNER_DATA, new DemoConfig());
                startActivity(demo);
            }
        });

        defaultScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoConfig config = new DemoConfig();
                config.setItemPercent(80);
                config.setItemSpaceDp(-20);
                config.setSideAlpha(0.8f);
                config.setItemScale(0.8f);
                Intent demo = new Intent(getApplicationContext(), DemoActivity.class);
                demo.putExtra(Sys.BANNER_DATA, config);
                startActivity(demo);
            }
        });

        defaultGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guide = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guide);
            }
        });

        globalColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorChooserDialog.Builder(activity, R.string.color_selector)
                        .titleSub(R.string.color_selector)
                        .preselect(selectedColor)
                        .build()
                        .show(getSupportFragmentManager());
            }
        });
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, int selectedColor) {
        this.selectedColor = selectedColor;
        globalColor.setTextColor(selectedColor);
    }

    @Override
    public void onColorChooserDismissed(@NonNull ColorChooserDialog dialog) {

    }
}
