package google.architecture.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.coremodel.data.xlj.shopdata.Goods;
import google.architecture.coremodel.datamodel.http.ApiConstants;
import google.architecture.home.R;

public class HomeHorizontalListAdapter extends RecyclerView.Adapter<HomeHorizontalListAdapter.BrandHViewHolder> {

    private List<Goods> mList;
    private OnItemClickListener mListener;

    public HomeHorizontalListAdapter(List<Goods> list,OnItemClickListener listener){
        mList = list;
        mListener = listener;
    }

    @Override
    public BrandHViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_brand_hori,parent,false);
        return new BrandHViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(BrandHViewHolder holder, int position) {
        Goods item = mList.get(position);
        ImageLoader.get().load(holder.goodsImgIv, ApiConstants.XLJimgHost+item.getImageUrl());
        holder.goodsNameTv.setText(item.getGoodsName());
        holder.goodsPrice.setText("RMB/"+item.getShopPrice());
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

    public static class BrandHViewHolder extends RecyclerView.ViewHolder{

        public ImageView goodsImgIv;
        public TextView goodsNameTv;
        public TextView goodsPrice;
        public View clickView;

        public BrandHViewHolder(View itemView) {
            super(itemView);
            goodsImgIv = itemView.findViewById(R.id.goods_img_iv);
            goodsNameTv = itemView.findViewById(R.id.goods_name_tv);
            goodsPrice = itemView.findViewById(R.id.goods_price_original_tv);
            clickView = itemView;
        }
    }

    public static interface OnItemClickListener{
        public void onItemClick(View v, int position);
    }
}
