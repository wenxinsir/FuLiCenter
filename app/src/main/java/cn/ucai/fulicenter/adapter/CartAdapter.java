package cn.ucai.fulicenter.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/10/19.
 */
public class CartAdapter extends Adapter<CartAdapter.CartViewHolder> {
    Context mContext;
    ArrayList<CartBean> mList;


    public CartAdapter(Context context, ArrayList<CartBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = new CartViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_cart, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        final CartBean cartBean = mList.get(position);
        GoodsDetailsBean goods = cartBean.getGoods();
        if (goods!=null){
            ImageLoader.downloadImg(mContext,holder.mIvCartThumb,goods.getGoodsThumb());
            holder.mTvCartGoodName.setText(goods.getGoodsName());
            holder.mTvCartPrice.setText(goods.getCurrencyPrice());
        }
        holder.mTvCartCount.setText("("+cartBean.getCount()+")");
        holder.mCbCartSelected.setChecked(cartBean.isChecked());
        holder.mCbCartSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                cartBean.setChecked(b);
                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
            }
        });
        holder.mIvCartAdd.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void initData(ArrayList<CartBean> list) {
        mList = list;
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
         //增加减少和删除购物车的数量
         @OnClick(R.id.iv_cart_add)
         public void addCart(){
              final int position = (int) mIvCartAdd.getTag();
             CartBean cart = mList.get(position);
             NetDao.updateCart(mContext, cart.getId(), cart.getCount() + 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                 @Override
                 public void onSuccess(MessageBean result) {
                     if (result!=null && result.isSuccess()){
                         mList.get(position).setCount(mList.get(position).getCount()+1);
                         mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                         mTvCartCount.setText("("+(mList.get(position).getCount())+")");
                     }
                 }

                 @Override
                 public void onError(String error) {
                 }
             });
         }
         @OnClick(R.id.iv_cart_del)
         public void delCart(){
             final int position = (int) mIvCartAdd.getTag();
             CartBean cart = mList.get(position);
             if (cart.getCount()>1) {
                 NetDao.updateCart(mContext, cart.getId(), cart.getCount() - 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                     @Override
                     public void onSuccess(MessageBean result) {
                         if (result != null && result.isSuccess()) {
                             mList.get(position).setCount(mList.get(position).getCount() - 1);
                             mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                             mTvCartCount.setText("(" + (mList.get(position).getCount()) + ")");
                         }
                     }

                     @Override
                     public void onError(String error) {
                     }
                 });
             }else {
                NetDao.deleteCart(mContext, cart.getId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result!=null && result.isSuccess()){
                            mList.remove(position);
                            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
             }
         }
    }
}
