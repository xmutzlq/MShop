package google.architecture.personal.adapter.order;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.params.OrderGoodsData;
import google.architecture.common.params.OrderShopData;
import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.util.ICalculator;
import google.architecture.common.util.LeftTwoDecimalFormat;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.widget.span.Spans;
import google.architecture.coremodel.MultiItemTypeHelper;
import google.architecture.personal.R;

/**
 * @author lq.zeng
 * @date 2018/9/28
 */

public class OrderDetailAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private ClipboardManager cm;

    public OrderDetailAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(MultiItemTypeHelper.TYPE_ORDER_STATE, R.layout.layout_my_order_state_provider);
        addItemType(MultiItemTypeHelper.TYPE_ORDER_ADDRESS, R.layout.layout_order_item_consignee);
        addItemType(MultiItemTypeHelper.TYPE_ORDER_BUSINESSES, R.layout.layout_my_order_shop_provider);
        addItemType(MultiItemTypeHelper.TYPE_ORDER_BUSINESSES_GOODS, R.layout.layout_my_order_goods_provider);
        addItemType(MultiItemTypeHelper.TYPE_ORDER_PAY, R.layout.layout_order_detail_pay_provider);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = super.onCreateViewHolder(parent, viewType);
        cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        return baseViewHolder;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case MultiItemTypeHelper.TYPE_ORDER_STATE:
                final OrderStateData orderStateData = (OrderStateData) item;
                helper.setText(R.id.my_order_state_tv, orderStateData.getState_desc());
                break;
            case MultiItemTypeHelper.TYPE_ORDER_ADDRESS: //收货地址
                final OrderAddressData orderAddressData = (OrderAddressData) item;
                helper.getView(R.id.order_consignee_address_arrows_right_iv).setVisibility(View.INVISIBLE);
                helper.getView(R.id.order_consignee_empty_tv).setVisibility(View.GONE);
                helper.getView(R.id.order_consignee_info_lay).setVisibility(View.VISIBLE);
                helper.getView(R.id.order_consignee_edit).setClickable(false);
                helper.setText(R.id.order_consignee_tv, orderAddressData.getConsignee());
                helper.setText(R.id.order_consignee_phone_tv, orderAddressData.getMobile());
                helper.setText(R.id.order_consignee_address_tv, orderAddressData.getAddress());
                break;
            case MultiItemTypeHelper.TYPE_ORDER_BUSINESSES: //商家
                final OrderShopData orderShopData = (OrderShopData) item;
                helper.setTextColor(R.id.my_order_create_time, ContextCompat.getColor(mContext, R.color.text_color));
                helper.setText(R.id.my_order_create_time, orderShopData.getShop_name());
                helper.setText(R.id.my_order_pay_state, "");
                break;
            case MultiItemTypeHelper.TYPE_ORDER_BUSINESSES_GOODS: //货物
                final OrderGoodsData orderGoodsData = (OrderGoodsData) item;
                ImageLoader.get().load(helper.getView(R.id.my_order_goods_iv), orderGoodsData.getImage());

                String priceStr = "¥" + orderGoodsData.getGoods_price();
                TextView priceTv = helper.getView(R.id.my_order_goods_price);
                priceTv.setText(priceStr);

                //计算价格的宽度
                TextPaint textPaint = priceTv.getPaint();
                float textPaintWidth = textPaint.measureText(priceStr);
                TextView titleTv = helper.getView(R.id.my_order_goods_title);
                titleTv.setText(orderGoodsData.getGoods_name());

                //价格的宽度作为margin
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(DimensionsUtil.dip2px(mContext, 5), 0, (int)textPaintWidth, 0);
                titleTv.setLayoutParams(layoutParams);

                helper.setText(R.id.my_order_goods_num, "×" + orderGoodsData.getGoods_num());
                ICalculator calculator = new LeftTwoDecimalFormat(Double.valueOf(orderGoodsData.getGoods_price()),
                        Double.valueOf(orderGoodsData.getGoods_num()));
                String priceNum = "总金额¥" + calculator.mul();
                helper.setText(R.id.my_order_goods_price_num, Spans.builder().text(priceNum)
                        .newSpanPart(3, priceNum.length(), new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_FD644B))).build());
                break;
            case MultiItemTypeHelper.TYPE_ORDER_PAY: //支付信息
                final OrderPayData orderPayData = (OrderPayData) item;
                helper.setText(R.id.order_detail_goods_total_price_tv, "¥" + orderPayData.getTotal_money());
                helper.setText(R.id.order_detail_goods_dispatch_price_tv, "¥" + orderPayData.getShipping_price());
                helper.setText(R.id.order_detail_goods_order_total_price_tv, "¥" + orderPayData.getOrder_money());
                helper.setText(R.id.order_detail_goods_order_no_tv, mContext.getResources().getString(R.string.personal_order_detail_order_no, orderPayData.getOrder_no()));
                helper.setText(R.id.order_detail_order_create_time_tv, mContext.getResources().getString(R.string.personal_order_detail_order_create_time, orderPayData.getCreate_time()));
                helper.getView(R.id.btn_order_copy).setOnClickListener(v -> {
                    cm.setPrimaryClip(ClipData.newPlainText("text", orderPayData.getOrder_no()));
                    ToastUtils.showShortToast("已复制到粘贴板");
                });
                break;
        }
    }
}
