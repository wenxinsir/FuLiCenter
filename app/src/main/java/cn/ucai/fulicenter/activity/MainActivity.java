package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.new_good)
    RadioButton newGood;
    @Bind(R.id.boutique)
    RadioButton boutique;
    @Bind(R.id.category)
    RadioButton category;
    @Bind(R.id.cart)
    RadioButton cart;
    @Bind(R.id.tvCartHint)
    TextView tvCartHint;
    @Bind(R.id.personal)
    RadioButton personal;

    int index;
    RadioButton[] rbs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.i("MainActivity", "onCreate");
        initView();
    }

    private void initView() {
        rbs = new RadioButton[5];
        rbs[0] = newGood;
        rbs[1] = boutique;
        rbs[2] = category;
        rbs[3] = cart;
        rbs[4] = personal;
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.new_good:
                index = 0;
                break;
            case R.id.boutique:
                index = 1;
                break;
            case R.id.category:
                index = 2;
                break;
            case R.id.cart:
                index = 3;
                break;
            case R.id.personal:
                index = 4;
                break;
        }
        setRadioButtonStatus();
    }

    private void setRadioButtonStatus() {
        for (int i = 0; i < rbs.length; i++) {
            if (rbs[i] == cart) {
                rbs[i].setChecked(true);
            } else {
                rbs[i].setChecked(false);
            }
        }
    }
}
