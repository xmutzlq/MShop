package google.architecture.personal.adapter;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.coremodel.data.xlj.personal.LikeGoods;
import google.architecture.coremodel.datamodel.http.ApiConstants;
import google.architecture.personal.R;

public class PersonalNewAdapter extends RecyclerView.Adapter<PersonalNewAdapter.PersonViewHolder> {

    private List<LikeGoods> mList;
    private OnItemClickListener mListener;

    public PersonalNewAdapter(List<LikeGoods> list, OnItemClickListener listener){
        mList = list;
        mListener = listener;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item_layout,parent,false);
        return new PersonViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {

        LikeGoods item = mList.get(position);
        ImageLoader.get().load(holder.mIvGoodsImg, ApiConstants.GankHost+item.getGoodsImg());
        holder.mTvGoodsName.setText(item.getGoodsName());
        holder.mTvGoodsPrice.setText(item.getShopPrice());
        holder.mTvGoodsPriceOrig.setText(item.getMarketPrice());
        holder.clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder{

        public ImageView mIvGoodsImg;
        public TextView mTvGoodsName;
        public TextView mTvGoodsPrice;
        public TextView mTvGoodsPriceOrig;
        public View clickView;

        public PersonViewHolder(View view){
            super(view);
            clickView = view;
            mIvGoodsImg = view.findViewById(R.id.goods_img_iv);
            mTvGoodsName = view.findViewById(R.id.goods_name_tv);
            mTvGoodsPrice = view.findViewById(R.id.goods_price_tv);
            mTvGoodsPriceOrig = view.findViewById(R.id.goods_price_original_tv);
            mTvGoodsPriceOrig.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    public static interface OnItemClickListener{
        public void onItemClick(View v, int position);
    }

}
