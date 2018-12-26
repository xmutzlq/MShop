package google.architecture.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.coremodel.data.xlj.shopdata.ImgInfo;
import google.architecture.coremodel.datamodel.http.ApiConstants;
import google.architecture.home.R;

public class HomeBrandAdapter extends RecyclerView.Adapter<HomeBrandAdapter.BrandViewHolder> {

    private List<ImgInfo> mList;

    public HomeBrandAdapter(List<ImgInfo> list){
        mList = list;
    }

    @Override
    public BrandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_item_layout,parent,false);
        return new BrandViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(BrandViewHolder holder, int position) {
        String imgUrl = ApiConstants.GankHost+ mList.get(position).getImageUrl();
        ImageLoader.get().load(holder.brandIv, imgUrl);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class BrandViewHolder extends RecyclerView.ViewHolder{

        public ImageView brandIv;

        public BrandViewHolder(View itemView) {
            super(itemView);
            brandIv = itemView.findViewById(R.id.brand_iv);
        }
    }
}
