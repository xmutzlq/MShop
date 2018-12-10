package google.architecture.coremodel.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import google.architecture.coremodel.MultiItemTypeHelper;

/**
 * @author lq.zeng
 * @date 2018/6/29
 */

public class DetailRecommendInfo implements MultiItemEntity {
    @SerializedName("goods_id")
    public String rec_id;
    @SerializedName("original_img")
    public String rec_url;
    @SerializedName("goods_name")
    public String rec_title;
    @SerializedName("shop_price")
    public String rec_price;
    public List<DetailRecommendInfo> pageList;

    public DetailRecommendInfo(String rec_url, String rec_title, String rec_price) {
        this.rec_url = rec_url;
        this.rec_title = rec_title;
        this.rec_price = rec_price;
    }

    @Override
    public int getItemType() {
        return MultiItemTypeHelper.TYPE_GUESS_YOU_LIKE;
    }
}
