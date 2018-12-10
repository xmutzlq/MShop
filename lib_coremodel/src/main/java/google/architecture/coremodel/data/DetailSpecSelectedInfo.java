package google.architecture.coremodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/8/28
 */

public class DetailSpecSelectedInfo implements Parcelable {
    @SerializedName("item_ids")
    private String item_ids; //选中的规格ids
    @SerializedName("price")
    private String price; //价格
    @SerializedName("store_count")
    private String store_count; //库存
    @SerializedName("goods_name")
    private String goods_name; //商品名称
    @SerializedName("goods_selected_depict")
    private String goods_selected_depict; //选中规格描述
    @SerializedName("image")
    private String image; //规格对应的图片

    protected DetailSpecSelectedInfo(Parcel in) {
        item_ids = in.readString();
        price = in.readString();
        store_count = in.readString();
        goods_name = in.readString();
        goods_selected_depict = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item_ids);
        dest.writeString(price);
        dest.writeString(store_count);
        dest.writeString(goods_name);
        dest.writeString(goods_selected_depict);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DetailSpecSelectedInfo> CREATOR = new Creator<DetailSpecSelectedInfo>() {
        @Override
        public DetailSpecSelectedInfo createFromParcel(Parcel in) {
            return new DetailSpecSelectedInfo(in);
        }

        @Override
        public DetailSpecSelectedInfo[] newArray(int size) {
            return new DetailSpecSelectedInfo[size];
        }
    };

    public String getItem_ids() {
        return item_ids;
    }

    public void setItem_ids(String item_ids) {
        this.item_ids = item_ids;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStore_count() {
        return store_count;
    }

    public void setStore_count(String store_count) {
        this.store_count = store_count;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_selected_depict() {
        return goods_selected_depict;
    }

    public void setGoods_selected_depict(String goods_selected_depict) {
        this.goods_selected_depict = goods_selected_depict;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
