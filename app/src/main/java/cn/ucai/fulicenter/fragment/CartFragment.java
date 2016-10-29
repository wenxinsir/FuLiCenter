package cn.ucai.fulicenter.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.MainActivity;
import cn.ucai.fulicenter.adapter.CartAdapter;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.utils.ResultUtils;
import cn.ucai.fulicenter.view.SpaceItemDecoration;

/**
 * Created by Administrator on 2016/10/19.
 */
public class CartFragment extends BaseFragment {

    private static final String TAG = CartFragment.class.getSimpleName();

    @Bind(R.id.tv_refresh)
    TextView mTvRefresh;
    @Bind(R.id.rv)
    RecyclerView mRv;
    @Bind(R.id.srl)
    SwipeRefreshLayout mSrl;

    LinearLayoutManager llm;
    MainActivity mContent;
    CartAdapter mAdapter;
    ArrayList<CartBean> mList;
    @Bind(R.id.tv_cart_sum_price)
    TextView mTvCartSumPrice;
    @Bind(R.id.tv_cart_save_price)
    TextView mTvCartSavePrice;
    @Bind(R.id.layout_cart)
    RelativeLayout mLayoutCart;
    @Bind(R.id.tv_nothing)
    TextView mTvNothing;
    private boolean cartLayout;

    updateCartReceiver mReceiver;
    int orderPrice = 0;
    String cartIds = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, layout);
        mContent = (MainActivity) getContext();
        mList = new ArrayList<>();
        mAdapter = new CartAdapter(mContent, mList);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void setListener() {
        setPullDownListener();
        IntentFilter filter= new IntentFilter(I.BROADCAST_UPDATA_CART);
        mReceiver = new updateCartReceiver();
        mContent.registerReceiver(mReceiver,filter);
    }

    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                downloadCart();
            }
        });
    }

    @Override
    protected void initData() {
        downloadCart();
    }

    public void downloadCart() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            NetDao.downloadCart(mContent, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
                @Override
                public void onSuccess(String s) {
                    ArrayList<CartBean> list = ResultUtils.getCartFromJson(s);
                    L.e(TAG, "result=" + list);
                    mSrl.setRefreshing(false);
                    mTvRefresh.setVisibility(View.GONE);
                    if (list != null && list.size() > 0) {
                        mList.clear();
                        mList.addAll(list);
                        mAdapter.initData(list);
                        setCartLayout(true);
                    }else {
                        setCartLayout(false);
                    }
                }

                @Override
                public void onError(String error) {
                    setCartLayout(false);
                    mSrl.setRefreshing(false);
                    mTvRefresh.setVisibility(View.GONE);
                    CommonUtils.showShortToast(error);
                    L.e("error" + error);
                }
            });
        }
    }

    @Override
    protected void initView() {
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        llm = new LinearLayoutManager(mContent);
        mRv.setLayoutManager(llm);
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new SpaceItemDecoration(12));
        setCartLayout(false);
    }
    public void setCartLayout(boolean hasCart) {
        mLayoutCart.setVisibility(hasCart?View.VISIBLE:View.GONE);
        mTvNothing.setVisibility(hasCart?View.GONE:View.VISIBLE);
        mRv.setVisibility(hasCart?View.VISIBLE:View.GONE);
        sumPrice();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tv_cart_buy)
    public void buy() {
        if (cartIds!= null && !cartIds.equals("") && cartIds.length()>0){
            MFGT.gotoBuy(mContent,cartIds);
        }else {
            CommonUtils.showLongToast(R.string.order_nothing);
        }
    }

    private void sumPrice(){
        cartIds = "";
        int sumPrice = 0;
        int rankPrice = 0;
        if (mList!=null &&  mList.size()>0){
            for (CartBean c:mList){
                if (c.isChecked()){
                    cartIds += c.getId()+",";
                    sumPrice += getPrice(c.getGoods().getCurrencyPrice())*c.getCount();
                    rankPrice += getPrice(c.getGoods().getRankPrice())*c.getCount();
                }
            }
            mTvCartSumPrice.setText("合计：￥0"+Double.valueOf(rankPrice));
            mTvCartSavePrice.setText("节省：￥0"+Double.valueOf(sumPrice-rankPrice));
        }else {
            cartIds = "";
            mTvCartSumPrice.setText("合计：￥0");
            mTvCartSavePrice.setText("节省：￥0");
        }
    }
    private int getPrice(String price){
        price = price.substring(price.indexOf("￥")+1);
        return Integer.valueOf(price);
    }

    class updateCartReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            L.e(TAG,"updateCartReceiver .....");
            sumPrice();
            setCartLayout(mList!=null&&mList.size()>0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver!=null){
            mContent.unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        L.e(TAG,"onResume.....");
        initData();
    }
}
