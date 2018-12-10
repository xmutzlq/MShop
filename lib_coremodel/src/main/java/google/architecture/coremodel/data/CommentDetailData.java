package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/9/29
 */

public class CommentDetailData {
    @SerializedName("order_id")
    private String order_id; //订单id
    @SerializedName("order_no")
    private String order_no; // 订单号

    @SerializedName("satisfy_rank_list")
    List<SatisfyRankListInfo> satisfy_rank_list;
    @SerializedName("shop_list")
    List<OrderRemoteGoodsData> shop_list;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public List<SatisfyRankListInfo> getSatisfy_rank_list() {
        return satisfy_rank_list;
    }

    public void setSatisfy_rank_list(List<SatisfyRankListInfo> satisfy_rank_list) {
        this.satisfy_rank_list = satisfy_rank_list;
    }

    public List<OrderRemoteGoodsData> getShop_list() {
        return shop_list;
    }

    public void setShop_list(List<OrderRemoteGoodsData> shop_list) {
        this.shop_list = shop_list;
    }
}
