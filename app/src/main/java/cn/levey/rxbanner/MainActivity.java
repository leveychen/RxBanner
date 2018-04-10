package cn.levey.rxbanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.levey.rxbanner.activity.DemoActivity;
import cn.levey.rxbanner.activity.FragmentActivity;


/**
 * Created by Levey on 2018/4/2 17:38.
 * e-mail: m@levey.cn
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.btn_create)
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String[] mItems = getResources().getStringArray(R.array.banner_parent_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = spinner.getSelectedItem().toString();
                switch (type){
                    case "Activity":
                        Intent acty = new Intent(getApplicationContext(), DemoActivity.class);
                        startActivity(acty);
                        break;
                    case "Fragment":
                        Intent frag = new Intent(getApplicationContext(), FragmentActivity.class);
                        startActivity(frag);
                        break;
                }
            }
        });
    }
}
