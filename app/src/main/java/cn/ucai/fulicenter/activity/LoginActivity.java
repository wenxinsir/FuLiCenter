package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.utils.MFGT;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.username)
    EditText mUsername;
    @Bind(R.id.password)
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                break;
            case R.id.btn_register:
                MFGT.gotoRegister(this);
                break;
        }
    }
}
