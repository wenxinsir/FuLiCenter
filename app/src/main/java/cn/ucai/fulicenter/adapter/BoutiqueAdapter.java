package cn.ucai.fulicenter.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.utils.ImageLoader;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.view.FooterViewHolder;

/**
 * Created by Administrator on 2016/10/19.
 */
public class BoutiqueAdapter extends Adapter {
    Context mContext;
    ArrayList<BoutiqueBean> mList;
    boolean isMore;
    private int footerString;

    public BoutiqueAdapter(Context context,ArrayList<BoutiqueBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_footer, parent, false));
        } else {
            holder = new BoutiqueViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_boutique, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).tvFooter.setText(getFooterString());
        }
        if (holder instanceof BoutiqueViewHolder) {
            BoutiqueBean boutiqueBean = mList.get(position);
            ImageLoader.downloadImg(mContext,((BoutiqueViewHolder)holder).mLvBoutiqueImg,boutiqueBean.getImageurl());
            ((BoutiqueViewHolder)holder).mTvBoutiqueTitle.setText(boutiqueBean.getTitle());
            ((BoutiqueViewHolder)holder).mTvBoutiqueName.setText(boutiqueBean.getName());
            ((BoutiqueViewHolder)holder).mTvBoutiqueDescription.setText(boutiqueBean.getDescription());
        }
    }

    public int getFooterString() {
        return isMore?R.string.load_more:R.string.no_more;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    static class BoutiqueViewHolder extends ViewHolder{
        @Bind(R.id.ivBoutiqueImg)
        ImageView mLvBoutiqueImg;
        @Bind(R.id.tvBoutiqueTitle)
        TextView mTvBoutiqueTitle;
        @Bind(R.id.tvBoutiqueName)
        TextView mTvBoutiqueName;
        @Bind(R.id.tvBoutiqueDescription)
        TextView mTvBoutiqueDescription;
        @Bind(R.id.layout_boutique_item)
        RelativeLayout mLayoutBoutiqueItem;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
