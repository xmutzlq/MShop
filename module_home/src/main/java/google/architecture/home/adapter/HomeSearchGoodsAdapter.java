package google.architecture.home.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.widget.span.Spans;
import google.architecture.coremodel.data.SearchResult;
import google.architecture.coremodel.util.TextUtil;
import google.architecture.home.R;

/**
 * @author lq.zeng
 * @date 2018/6/4
 */

public class HomeSearchGoodsAdapter extends BaseMultiItemQuickAdapter<SearchResult.GoodsItem, BaseViewHolder> {

    private int itemType = SearchResult.GoodsItem.ITEM_TYPE_LIST;

    public HomeSearchGoodsAdapter() {
        super(null);
        addItemType(SearchResult.GoodsItem.ITEM_TYPE_LIST, R.layout.home_search_goods_type_list);
        addItemType(SearchResult.GoodsItem.ITEM_TYPE_GRID, R.layout.home_search_goods_type_grid);
    }

    public void setSearchResultList(List<SearchResult.GoodsItem> list){
        List<SearchResult.GoodsItem> tempSearchs = new ArrayList<>();
        tempSearchs.addAll(list);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mData, tempSearchs), true);
        diffResult.dispatchUpdatesTo(this);
        this.mData = tempSearchs;
    }

    public int exchangeShownState() {
        itemType = itemType == SearchResult.GoodsItem.ITEM_TYPE_LIST ? SearchResult.GoodsItem.ITEM_TYPE_GRID : SearchResult.GoodsItem.ITEM_TYPE_LIST;
        if(getData() == null || getData().size() == 0) return itemType;
        for (SearchResult.GoodsItem data : getData()) {
            data.setItemType(itemType);
        }
        return itemType;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResult.GoodsItem item) {
        switch (helper.getItemViewType()) {
            case SearchResult.GoodsItem.ITEM_TYPE_LIST:
                ImageView imageView = helper.getView(R.id.search_goods_list_iv);
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                layoutParams.width = DimensionsUtil.dip2px(mContext, 110);
                layoutParams.height = layoutParams.width;
                ImageLoader.get().load(imageView, item.getOriginal_img(), new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ViewCompat.setTransitionName(imageView, mContext.getResources().getString(R.string.transitionName_list_img_to_img));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        imageView.setImageDrawable(resource);
                        ViewCompat.setTransitionName(imageView, mContext.getResources().getString(R.string.transitionName_list_img_to_img));
                        return false;
                    }
                });
                ((TextView)helper.getView(R.id.search_goods_title_list_tv)).setMaxLines(2);
                if(TextUtil.isEmpty(item.getGoods_name())) {
                    helper.setGone(R.id.search_goods_title_list_tv, false);
                } else {
                    helper.setGone(R.id.search_goods_title_list_tv, true);
                    helper.setText(R.id.search_goods_title_list_tv, item.getGoods_name());
                }
                if(TextUtil.isEmpty(item.getGoods_sn())) {
                    helper.setGone(R.id.search_goods_title_2_list_tv, false);
                } else {
                    helper.setGone(R.id.search_goods_title_2_list_tv, true);
                    helper.setText(R.id.search_goods_title_2_list_tv, item.getGoods_sn());
                }

                if(TextUtil.isEmpty(item.getShop_price())) item.setShop_price("0.00");
                helper.setText(R.id.search_goods_price_list_tv, Spans.builder().text("￥",12, Color.RED)
                        .text(item.getShop_price(), 15, Color.RED).build());

                break;
            case SearchResult.GoodsItem.ITEM_TYPE_GRID:
                ImageView gImageView = helper.getView(R.id.search_goods_grid_iv);
                ImageLoader.get().load(gImageView, item.getOriginal_img(), new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ViewCompat.setTransitionName(gImageView, mContext.getResources().getString(R.string.transitionName_list_img_to_img));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        gImageView.setImageDrawable(resource);
                        ViewCompat.setTransitionName(gImageView, mContext.getResources().getString(R.string.transitionName_list_img_to_img));
                        return false;
                    }
                });

                TextView textView1 = helper.getView(R.id.search_goods_title_grid_tv);
                TextView textView2 = helper.getView(R.id.search_goods_title_2_grid_tv);

                textView1.setEllipsize(TextUtils.TruncateAt.END);
                textView1.setSingleLine(true);

                textView2.setEllipsize(TextUtils.TruncateAt.END);
                textView2.setSingleLine(true);

                if(TextUtil.isEmpty(item.getGoods_name())) {
                    helper.setGone(R.id.search_goods_title_grid_tv, false);
                } else {
                    helper.setGone(R.id.search_goods_title_grid_tv, true);
                    helper.setText(R.id.search_goods_title_grid_tv, item.getGoods_name());
                }

                if(TextUtil.isEmpty(item.getGoods_sn())) {
                    helper.setGone(R.id.search_goods_title_2_grid_tv, false);
                } else {
                    helper.setGone(R.id.search_goods_title_2_grid_tv, true);
                    helper.setText(R.id.search_goods_title_2_grid_tv, item.getGoods_sn());
                }

                if(TextUtil.isEmpty(item.getShop_price())) item.setShop_price("0.00");
                helper.setText(R.id.search_goods_price_grid_tv, Spans.builder().text("￥",12, Color.RED)
                        .text(item.getShop_price(), 15, Color.RED).build());
                break;
        }
    }

    public static class DiffCallBack extends DiffUtil.Callback {
        private List<SearchResult.GoodsItem> mOldDatas, mNewDatas;

        public DiffCallBack(List<SearchResult.GoodsItem> mOldDatas, List<SearchResult.GoodsItem> mNewDatas) {
            this.mOldDatas = mOldDatas;
            this.mNewDatas = mNewDatas;
        }

        @Override
        public int getOldListSize() {
            return mOldDatas.size();
        }

        @Override
        public int getNewListSize() {
            return mNewDatas.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            SearchResult.GoodsItem oldData = mOldDatas.get(oldItemPosition);
            SearchResult.GoodsItem newData = mNewDatas.get(newItemPosition);
            return oldData.getGoods_id().equalsIgnoreCase(newData.getGoods_id());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            SearchResult.GoodsItem oldData = mOldDatas.get(oldItemPosition);
            SearchResult.GoodsItem newData = mNewDatas.get(newItemPosition);
            return oldData.getGoods_id().equalsIgnoreCase(newData.getGoods_id());
        }
    }
}
