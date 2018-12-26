package google.architecture.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class HomeBrandAdapter extends RecyclerView.Adapter<HomeBrandAdapter.BrandViewHolder> {

    @Override
    public BrandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BrandViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class BrandViewHolder extends RecyclerView.ViewHolder{

        public BrandViewHolder(View itemView) {
            super(itemView);
        }
    }
}
