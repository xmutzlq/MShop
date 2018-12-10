package google.architecture.personal.adapter.order;

import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.util.LeftTwoDecimalFormat;
import google.architecture.common.widget.adapter.BaseItemProvider;
import google.architecture.common.widget.span.Spans;
import google.architecture.personal.R;

/**
 * @author lq.zeng
 * @date 2018/9/13
 */

public class GoodsProvider extends BaseItemProvider<MyOrderSpc, BaseViewHolder> {

    @Override
    public int viewType() {
        return MyOrderAdapter.TYPE_GOODS;
    }

    @Override
    public int layout() {
        return R.layout.layout_my_order_goods_provider;
    }

    @Override
    public void convert(BaseViewHolder helper, MyOrderSpc data, int position) {
        if(data.myOrderGood != null) {
            ImageLoader.get().load(helper.getView(R.id.my_order_goods_iv), data.myOrderGood.getImage());

            String priceStr = "¥" + data.myOrderGood.getGoods_price();
            TextView priceTv = helper.getView(R.id.my_order_goods_price);
            priceTv.setText(priceStr);

            //计算价格的宽度
            TextPaint textPaint = priceTv.getPaint();
            float textPaintWidth = textPaint.measureText(priceStr);
            TextView titleTv = helper.getView(R.id.my_order_goods_title);
            titleTv.setText(data.myOrderGood.getGoods_name());

            //价格的宽度作为margin
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(DimensionsUtil.dip2px(mContext, 5), 0, (int)textPaintWidth, 0);
            titleTv.setLayoutParams(layoutParams);

            helper.setText(R.id.my_order_goods_num, "×" + data.myOrderGood.getGoods_num());
            LeftTwoDecimalFormat leftTwoDecimalFormat = new LeftTwoDecimalFormat(Double.valueOf(data.myOrderGood.getGoods_price()),
                    Double.valueOf(data.myOrderGood.getGoods_num()));
            String priceNum = "总金额¥" + leftTwoDecimalFormat.mul();
            helper.setText(R.id.my_order_goods_price_num, Spans.builder().text(priceNum)
                    .newSpanPart(3, priceNum.length(), new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_FD644B))).build());
        }
    }

    @Override
    public void onClick(BaseViewHolder helper, MyOrderSpc data, int position) {
    }

    @Override
    public boolean onLongClick(BaseViewHolder helper, MyOrderSpc data, int position) {
        return true;
    }
}
