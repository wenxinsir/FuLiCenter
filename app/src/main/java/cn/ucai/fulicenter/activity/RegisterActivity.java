package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.view.DisplayUtils;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.username)
    EditText mUsername;
    @Bind(R.id.nick)
    EditText mNick;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.confirm_Password)
    EditText mConfirmPassword;
    @Bind(R.id.btn_register)
    Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(this,"账户注册");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
