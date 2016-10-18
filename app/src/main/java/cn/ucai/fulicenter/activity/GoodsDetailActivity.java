package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.utils.CommonUtils;
import cn.ucai.fulicenter.activity.utils.L;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;

public class GoodsDetailActivity extends AppCompatActivity {

    @Bind(R.id.tv_common_title)
    TextView tvCommonTitle;
    @Bind(R.id.tv_cart_count)
    TextView tvCartCount;
    @Bind(R.id.tv_good_name_english)
    TextView tvGoodNameEnglish;
    @Bind(R.id.tv_good_name)
    TextView tvGoodName;
    @Bind(R.id.tv_good_price_shop)
    TextView tvGoodPriceShop;
    @Bind(R.id.tv_good_price_current)
    TextView tvGoodPriceCurrent;
    @Bind(R.id.wv_goods_brief)
    WebView wvGoodsBrief;

    int goodsId;
    GoodsDetailActivity mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        int goodsID = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("details", "goodsid=" + goodsID);
        if (goodsId == 0) {
            finish();
        }
        initView();
        initData();
        setListener();
    }

    private void setListener() {

    }

    private void initData() {
        NetDao.downloadGoodsDetail(mContent, goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                L.i("details=" + result);
                if (result != null) {
                    showGoodDetails(result);
                } else {
                    finish();
                }
            }

            @Override
            public void onError(String error) {
                finish();
                L.e("details,error=" + error);
                CommonUtils.showLongToast(error);
            }
        });
    }

    private void showGoodDetails(GoodsDetailsBean details) {
        tvGoodNameEnglish.setText(details.getGoodsEnglishName());
        tvGoodName.setText(details.getGoodsName());
        tvGoodPriceCurrent.setText(details.getCurrencyPrice());
        tvGoodPriceShop.setText(details.getShopPrice());
    }

    private void initView() {

    }
}
