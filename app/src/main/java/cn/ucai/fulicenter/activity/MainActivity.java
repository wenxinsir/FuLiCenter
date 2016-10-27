package cn.ucai.fulicenter.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.fragment.CartFragment;
import cn.ucai.fulicenter.fragment.PersonalCenterFragment;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.fragment.BoutiqueFragment;
import cn.ucai.fulicenter.fragment.CategoryFragment;
import cn.ucai.fulicenter.fragment.NewgoodsFragment;
import cn.ucai.fulicenter.utils.MFGT;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

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
    int currentIndex = 0;
    RadioButton[] rbs;
    Fragment[] mFragments;
    NewgoodsFragment mNewgoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    CategoryFragment mCategoryFragment;
    CartFragment mCartFragment;
    PersonalCenterFragment mPersonalCenterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.i("MainActivity", "onCreate");
        super.onCreate(savedInstanceState);
    }

    private void initFragment() {
        mFragments = new Fragment[5];
        // 实例化
        mNewgoodsFragment = new NewgoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mPersonalCenterFragment = new PersonalCenterFragment();
        mCartFragment = new CartFragment();
        mFragments[0] = mNewgoodsFragment;
        mFragments[1] = mBoutiqueFragment;
        mFragments[2] = mCategoryFragment;
        mFragments[3] = mPersonalCenterFragment;
        mFragments[4] = mCartFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mNewgoodsFragment)
//                .add(R.id.fragment_container, mBoutiqueFragment)
//                .add(R.id.fragment_container, mCategoryFragment)
//                .hide(mBoutiqueFragment)
//                .hide(mCategoryFragment)
                .show(mNewgoodsFragment)
                .commit();
    }

    @Override
    protected void initView() {
        rbs = new RadioButton[5];
        rbs[0] = newGood;
        rbs[1] = boutique;
        rbs[2] = category;
        rbs[3] = personal;
        rbs[4] = cart;
    }

    @Override
    protected void initData() {
        initFragment();
    }

    @Override
    protected void setListener() {

    }

    public void onCheckedChange(View view) {
        Log.i("fulicenter", "onCheckedChange: " + index);
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
            case R.id.personal:
                if(FuLiCenterApplication.getUsername()==null){
                    MFGT.gotoLoginFromCart(this);
                }else {
                    index = 3;
                }
                break;
            case R.id.cart:
                if(FuLiCenterApplication.getUsername()==null){
                    MFGT.gotoLogin(this);
                }else {
                    index = 4;
                }
                break;
        }
        setFragments();
        L.i("index="+index);
        L.i("currentIndex = " + currentIndex);
    }

    private void setFragments(){
        if (index!=currentIndex){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (!mFragments[index].isAdded()){
                ft.add(R.id.fragment_container,mFragments[index]);
            }
            ft.hide(mFragments[currentIndex]);
            ft.show(mFragments[index]).commit();
        }
        currentIndex = index;
        setRadioButtonStatus();
    }

    private void setRadioButtonStatus() {
        L.e("index="+index);
        for (int i = 0; i < rbs.length; i++) {
            if (i == index) {
                rbs[i].setChecked(true);
            } else {
                rbs[i].setChecked(false);
            }
        }
    }
    public void onBackPressed(){
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.e(TAG,"onResume...");
        if (index == 3 && FuLiCenterApplication.getUser()==null){
            index = 0;
        }
        setFragments();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e(TAG,"onActivityResult,requestCode="+requestCode);
        if (requestCode == I.REQUEST_CODE_LOGIN ){
            index = 3;
        }
        if(requestCode == I.REQUEST_CODE_LOGIN_FROM_CART){
            index = 4;
        }
    }
}
