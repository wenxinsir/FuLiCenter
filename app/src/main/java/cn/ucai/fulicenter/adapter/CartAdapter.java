package cn.ucai.fulicenter.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * Created by Administrator on 2016/10/19.
 */
public class CartAdapter extends Adapter<CartAdapter.CartViewHolder> {
    Context mContext;
    ArrayList<CartBean> mList;

    public CartAdapter(Context context, ArrayList<CartBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = new CartViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_cart, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartBean cartBean = mList.get(position);
        GoodsDetailsBean goods = cartBean.getGoods();
        if (goods!=null){
            ImageLoader.downloadImg(mContext,holder.mIvCartThumb,goods.getGoodsThumb());
            holder.mTvCartGoodName.setText(goods.getGoodsName());
            holder.mTvCartPrice.setText(goods.getCurrencyPrice());
        }
        holder.mTvCartCount.setText("("+cartBean.getCount()+")");
        holder.mCbCartSelected.setChecked(false);
//            ImageLoader.downloadImg(mContext,holder.mLvBoutiqueImg,boutiqueBean.getImageurl());
//            holder.mTvBoutiqueTitle.setText(boutiqueBean.getTitle());
//            holder.mTvBoutiqueName.setText(boutiqueBean.getName());
//            holder.mTvBoutiqueDescription.setText(boutiqueBean.getDescription());
//            holder.mLayoutBoutiqueItem.setTag(boutiqueBean);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void initData(ArrayList<CartBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

     class CartViewHolder extends ViewHolder{
        @Bind(R.id.cb_cart_selected)
        CheckBox mCbCartSelected;
        @Bind(R.id.iv_cart_thumb)
        ImageView mIvCartThumb;
        @Bind(R.id.tv_cart_good_name)
        TextView mTvCartGoodName;
        @Bind(R.id.iv_cart_add)
        ImageView mIvCartAdd;
        @Bind(R.id.tv_cart_count)
        TextView mTvCartCount;
        @Bind(R.id.iv_cart_del)
        ImageView mIvCartDel;
        @Bind(R.id.tv_cart_price)
        TextView mTvCartPrice;

         CartViewHolder(View view) {
             super(view);
            ButterKnife.bind(this, view);
        }
    }
}
