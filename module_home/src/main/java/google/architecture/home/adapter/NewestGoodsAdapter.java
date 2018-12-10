package google.architecture.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.util.ScreenUtils;
import google.architecture.common.widget.adapter.BaseBindingAdapter;
import google.architecture.coremodel.data.HomeItemsInfo;
import google.architecture.home.R;
import google.architecture.home.databinding.HomeItemNewestGoodsBinding;
import google.architecture.home.substance.HomeGoHelper;

/**
 * @author lq.zeng
 * @date 2018/5/8
 */

public class NewestGoodsAdapter extends BaseBindingAdapter<HomeItemsInfo, HomeItemNewestGoodsBinding> {

    protected boolean isScrolling = false;
    private int width;

    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }

    public NewestGoodsAdapter(Context context, int numColumn) {
        super(context);
        this.width = ScreenUtils.getScreenWidth() / numColumn - DimensionsUtil.dip2px(context, numColumn * 10);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.home_item_newest_goods;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = super.onCreateViewHolder(parent, viewType);
        holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
        return holder;
    }

    @Override
    protected void onBindItem(HomeItemNewestGoodsBinding binding, HomeItemsInfo info) {
        binding.setHomeItemsInfo(info);
        binding.executePendingBindings();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(v -> {
            HomeItemsInfo homeItemsInfo = getItems().get(position);
            if(homeItemsInfo.getAction() != null) {
                HomeGoHelper.goPage(context, homeItemsInfo.getAction());
            }
        });
    }
}
