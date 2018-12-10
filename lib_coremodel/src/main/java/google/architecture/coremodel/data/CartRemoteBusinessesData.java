package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/8/30
 */

public class CartRemoteBusinessesData  {
    @SerializedName("supplier_id")
    private String supplier_id; //店铺id
    @SerializedName("shop_name")
    private String shop_name; //店铺名称
    @SerializedName("shop_list")
    private List<CartRemoteGoodsData> shop_list;

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public List<CartRemoteGoodsData> getShop_list() {
        return shop_list;
    }

    public void setShop_list(List<CartRemoteGoodsData> shop_list) {
        this.shop_list = shop_list;
    }
}
