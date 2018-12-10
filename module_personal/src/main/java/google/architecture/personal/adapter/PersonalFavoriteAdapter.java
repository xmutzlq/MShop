package google.architecture.personal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.coremodel.data.FavoriteData;
import google.architecture.personal.R;

/**
 * @author lq.zeng
 * @date 2018/9/19
 */

public class PersonalFavoriteAdapter extends BaseQuickAdapter<FavoriteData.FavoriteItem, BaseViewHolder> {

    public PersonalFavoriteAdapter(int layoutResId) {
        super(layoutResId, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, FavoriteData.FavoriteItem item) {
        ImageLoader.get().load(helper.getView(R.id.personal_favorites_goods_iv), item.getOriginalImg());
        helper.setText(R.id.personal_favorites_goods_name, item.getGoodsName());
        helper.setText(R.id.personal_favorites_goods_price, "¥" + item.getShopPrice());
    }
}
