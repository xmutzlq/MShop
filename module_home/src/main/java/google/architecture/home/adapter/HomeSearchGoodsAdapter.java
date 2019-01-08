package google.architecture.home.adapter;

import android.graphics.Paint;
import android.support.v7.util.DiffUtil;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.soulwolf.widget.ratiolayout.widget.RatioFrameLayout;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseApplication;
import google.architecture.common.util.DimensionsUtil;
import google.architecture.coremodel.data.SearchResult;
import google.architecture.coremodel.datamodel.http.ApiConstants;
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

    public void refresh() {
        notifyItemRangeChanged(0, getData().size());
    }

    RequestOptions options = new RequestOptions()
            .error(google.architecture.common.R.drawable.image_mark)
            .placeholder(google.architecture.common.R.drawable.image_mark)
            .override(DimensionsUtil.dip2px(BaseApplication.getIns(), 120), DimensionsUtil.dip2px(BaseApplication.getIns(), 120))
            .dontAnimate();

    @Override
    public void onViewDetachedFromWindow(BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        setHolder(holder);
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        setHolder(holder);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateViewHolder(parent, viewType);
        setHolder(holder);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResult.GoodsItem item) {
        switch (helper.getItemViewType()) {
            case SearchResult.GoodsItem.ITEM_TYPE_LIST:

                SimpleDraweeView imageView = helper.getView(R.id.search_goods_list_iv);
                imageView.post(() -> {
                    String lImg = ApiConstants.XLJimgHost + item.getOriginal_img();
                    imageView.setImageURI(lImg);
                });

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
                helper.setText(R.id.search_goods_price_list_tv, "¥" + item.getShop_price());

                if(TextUtil.isEmpty(item.getMarketprice())) item.setMarketprice("0.00");
                TextView marketPrice = helper.getView(R.id.search_goods_market_price_list_tv);
                marketPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
                helper.setText(R.id.search_goods_market_price_list_tv, "¥" + item.getMarketprice());
                break;
            case SearchResult.GoodsItem.ITEM_TYPE_GRID:
                SimpleDraweeView gImageView = helper.getView(R.id.search_goods_grid_iv);
                gImageView.post(() -> {
                    String gImg = ApiConstants.XLJimgHost + item.getOriginal_img();
                    gImageView.setImageURI(gImg);
                });

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
                helper.setText(R.id.search_goods_price_grid_tv, "¥" + item.getShop_price());

                if(TextUtil.isEmpty(item.getMarketprice())) item.setMarketprice("0.00");
                TextView gMarketPrice = helper.getView(R.id.search_goods_market_price_grid_tv);
                gMarketPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
                helper.setText(R.id.search_goods_market_price_grid_tv, "¥" + item.getMarketprice());
                break;
        }
    }

    private void setHolder(BaseViewHolder holder) {
        switch (holder.getItemViewType()) {
            case SearchResult.GoodsItem.ITEM_TYPE_LIST:
                RatioFrameLayout ratioFrameLayout = holder.getView(R.id.list_ratio_lay);
                ViewGroup.LayoutParams layoutParams = ratioFrameLayout.getLayoutParams();
                layoutParams.width = DimensionsUtil.dip2px(mContext, 120);
                layoutParams.height = DimensionsUtil.dip2px(mContext, 120);

                TextView titleTv = holder.getView(R.id.search_goods_title_list_tv);
                titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

                TextView priceTv = holder.getView(R.id.search_goods_price_list_tv);
                priceTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

                TextView marketTv = holder.getView(R.id.search_goods_market_price_list_tv);
                marketTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                break;
            case SearchResult.GoodsItem.ITEM_TYPE_GRID:

                TextView titleTv2 = holder.getView(R.id.search_goods_title_grid_tv);
                titleTv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

                TextView priceTv2 = holder.getView(R.id.search_goods_price_grid_tv);
                priceTv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

                TextView marketTv2 = holder.getView(R.id.search_goods_market_price_grid_tv);
                marketTv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

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
