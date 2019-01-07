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
import google.architecture.coremodel.datamodel.http.ApiClient;
import google.architecture.coremodel.datamodel.http.ApiConstants;
import google.architecture.home.R;

public class HomeGridAdapter extends RecyclerView.Adapter<HomeGridAdapter.GridViewHolder> {

    List<Goods> mList;
    private OnItemClickListener mListener;

    public HomeGridAdapter(List<Goods> list, OnItemClickListener listener){
        mList = list;
        mListener = listener;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_grid,parent,false);
        return new GridViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        Goods item = mList.get(position);
        ImageLoader.get().load(holder.goodsImgIv, ApiConstants.GankHost+item.getImageUrl());
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

    public static class GridViewHolder extends RecyclerView.ViewHolder{

        public ImageView goodsImgIv;
        public TextView goodsNameTv;
        public TextView goodsPrice;
        public View clickView;

        public GridViewHolder(View itemView) {
            super(itemView);
            clickView = itemView;
            goodsImgIv = itemView.findViewById(R.id.goods_img_iv);
            goodsNameTv = itemView.findViewById(R.id.goods_name_tv);
            goodsPrice = itemView.findViewById(R.id.goods_price_original_tv);
        }
    }

    public static interface OnItemClickListener{
        public void onItemClick(View v, int position);
    }

}
