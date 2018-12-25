package google.architecture.personal.adapter;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import google.architecture.personal.R;

public class PersonalNewAdapter extends RecyclerView.Adapter<PersonalNewAdapter.PersonViewHolder> {

    private List mList;

    public PersonalNewAdapter(List list){
        mList = list;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item_layout,parent,false);
        return new PersonViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        if(position == 0){
            holder.mIvGoodsImg.setBackgroundResource(R.drawable.temp_shop_a);
        }else if(position == 1){
            holder.mIvGoodsImg.setBackgroundResource(R.drawable.temp_shop_b);
        }else{
            holder.mIvGoodsImg.setBackgroundResource(R.drawable.temp_shop_b);
        }
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

        public PersonViewHolder(View view){
            super(view);
            mIvGoodsImg = view.findViewById(R.id.goods_img_iv);
            mTvGoodsName = view.findViewById(R.id.goods_name_tv);
            mTvGoodsPrice = view.findViewById(R.id.goods_price_tv);
            mTvGoodsPriceOrig = view.findViewById(R.id.goods_price_original_tv);
            mTvGoodsPriceOrig.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

}
