package com.king.android.order.adapter;

import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.king.android.order.OrderLeftData;
import com.king.android.order.R;

import java.util.List;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.params.OrderGoodsData;
import google.architecture.common.params.OrderShopData;
import google.architecture.common.widget.span.Spans;
import google.architecture.coremodel.MultiItemTypeHelper;

/**
 * @author lq.zeng
 * @date 2018/9/7
 */

public class OrderAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public OrderAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(MultiItemTypeHelper.TYPE_ORDER_BUSINESSES, R.layout.layout_order_item_business);
        addItemType(MultiItemTypeHelper.TYPE_ORDER_BUSINESSES_GOODS, R.layout.layout_order_item_goods);
        addItemType(MultiItemTypeHelper.TYPE_ORDER_LEFT, R.layout.layout_order_item_left);
    }

    @Override
    protected void convert(BaseViewHolder holder, MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case MultiItemTypeHelper.TYPE_ORDER_BUSINESSES: //商家
                final OrderShopData lv0 = (OrderShopData) item;
                holder.setText(R.id.order_shop_title_tv, lv0.getShop_name());
                break;
            case MultiItemTypeHelper.TYPE_ORDER_BUSINESSES_GOODS: //货物
                final OrderGoodsData lv1 = (OrderGoodsData) item;
                ImageLoader.get().load(holder.getView(R.id.order_goods_img_iv), lv1.getImage());
                holder.setText(R.id.order_goods_title_tv, lv1.getGoods_name());
                holder.setText(R.id.order_goods_des_tv, lv1.getSpec_depict());
                holder.setText(R.id.order_goods_price_tv, "¥" + lv1.getGoods_price());
                holder.setText(R.id.order_goods_num_tv, "×" + lv1.getGoods_num());
                break;
            case MultiItemTypeHelper.TYPE_ORDER_LEFT: //留言
                final OrderLeftData lv2 = (OrderLeftData) item;
                SuperTextView superTextView = holder.getView(R.id.order_goods_dispatching_tv);
                superTextView.setLeftTextColor(ContextCompat.getColor(mContext, R.color.color_282828));
                superTextView.setLeftString(mContext.getResources().getString(R.string.order_str_dispatching_type));
                superTextView.setRightIcon(R.drawable.ic_arrow_right);
                superTextView.setRightTextColor(ContextCompat.getColor(mContext, R.color.color_282828));
                if(lv2.getShipping_list() != null && lv2.getShipping_list().size() > 0) {
                    superTextView.setRightString(lv2.getShipping_list().get(0).getShiping_name());
                }
                EditText leftStr = holder.getView(R.id.order_goods_buyers_left_et);
                leftStr.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        lv2.leftStr = s.toString();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                String shopGoodsNum = mContext.getResources().getString(R.string.order_str_shop_goods_num, lv2.getSum_goods_num());
                holder.setText(R.id.order_shop_goods_num_tv, shopGoodsNum);
                String shopGoodsPice = mContext.getResources().getString(R.string.order_str_shop_goods_money) + "¥" + lv2.getSum_goods_price();
                holder.setText(R.id.order_shop_goods_price_tv, Spans.builder().text(shopGoodsPice)
                        .newSpanPart(3, shopGoodsPice.length(), new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_FD644B))).build());
                break;
        }
    }
}
