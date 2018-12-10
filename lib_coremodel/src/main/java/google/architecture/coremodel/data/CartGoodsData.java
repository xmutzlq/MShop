package google.architecture.coremodel.data;

import google.architecture.coremodel.MultiItemTypeHelper;

/**
 * @author lq.zeng
 * @date 2018/5/10
 */

public class CartGoodsData extends CartMultiItemEntity {
    public String cartId;
    public String goodsId;
    public String goodsName;
    public String goodsDes;
    public String goodsPrice;
    public String goodsPic;
    public int goodsStoreCount;
    public int goodsBuyLimit;
    public int goodsState;

    public CartGoodsData(){}

    public CartGoodsData(String goodsId, String goodsName, String goodsPrice, String goodsPic) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsPic = goodsPic;
    }

    @Override
    public int getItemType() {
        return MultiItemTypeHelper.TYPE_BUSINESSES_GOODS;
    }
}
