package google.architecture.coremodel.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lq.zeng
 * @date 2018/8/20
 */

public class SearchResult {

    @SerializedName("screening_conditions")
    private List<FilterContainer> screening_conditions;

    @SerializedName("goods_list")
    private List<GoodsItem> goods_list;

    @SerializedName("goods_total")
    private int goods_total;

    public List<FilterContainer> getScreening_conditions() {
        return screening_conditions;
    }

    public void setScreening_conditions(List<FilterContainer> screening_conditions) {
        this.screening_conditions = screening_conditions;
    }

    public List<GoodsItem> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsItem> goods_list) {
        this.goods_list = goods_list;
    }

    public int getGoods_total() {
        return goods_total;
    }

    public void setGoods_total(int goods_total) {
        this.goods_total = goods_total;
    }

    public static class FilterContainer implements Parcelable{
        @SerializedName("title")
        private String title;

        @SerializedName("search_key")
        private String search_key; //搜索传参的参数名

        @SerializedName("choose")
        private int choose;
        @SerializedName("choose_num")
        private int choose_num;
        @SerializedName("list")
        private List<SearchFilterList> list;

        protected FilterContainer(Parcel in) {
            title = in.readString();
            search_key = in.readString();
            choose = in.readInt();
            choose_num = in.readInt();
            list = in.createTypedArrayList(SearchFilterList.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(search_key);
            dest.writeInt(choose);
            dest.writeInt(choose_num);
            dest.writeTypedList(list);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<FilterContainer> CREATOR = new Creator<FilterContainer>() {
            @Override
            public FilterContainer createFromParcel(Parcel in) {
                return new FilterContainer(in);
            }

            @Override
            public FilterContainer[] newArray(int size) {
                return new FilterContainer[size];
            }
        };

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<SearchFilterList> getList() {
            return list;
        }

        public void setList(List<SearchFilterList> list) {
            this.list = list;
        }

        public int getChoose() {
            return choose;
        }

        public void setChoose(int choose) {
            this.choose = choose;
        }

        public int getChoose_num() {
            return choose_num;
        }

        public void setChoose_num(int choose_num) {
            this.choose_num = choose_num;
        }

        public String getSearch_key() {
            return search_key;
        }

        public void setSearch_key(String search_key) {
            this.search_key = search_key;
        }
    }

    public static class FilterCat implements Parcelable {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;

        protected FilterCat(Parcel in) {
            id = in.readString();
            name = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<FilterCat> CREATOR = new Creator<FilterCat>() {
            @Override
            public FilterCat createFromParcel(Parcel in) {
                return new FilterCat(in);
            }

            @Override
            public FilterCat[] newArray(int size) {
                return new FilterCat[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class FilterBrand implements Parcelable {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;

        protected FilterBrand(Parcel in) {
            id = in.readString();
            name = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<FilterBrand> CREATOR = new Creator<FilterBrand>() {
            @Override
            public FilterBrand createFromParcel(Parcel in) {
                return new FilterBrand(in);
            }

            @Override
            public FilterBrand[] newArray(int size) {
                return new FilterBrand[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class GoodsItem implements MultiItemEntity {

        public static final int ITEM_TYPE_LIST = 0;
        public static final int ITEM_TYPE_GRID = 1;
        private int itemType;

        public GoodsItem(int itemType) {
            this.itemType = itemType;
        }

        @SerializedName("goods_id")
        private String goods_id;
        @SerializedName("goods_sn")
        private String goods_sn;
        @SerializedName("goods_name")
        private String goods_name;
        @SerializedName("click_count")
        private String click_count;
        @SerializedName("brand_id")
        private String brand_id;
        @SerializedName("store_count")
        private String store_count;
        @SerializedName("comment_count")
        private String comment_count;
        @SerializedName("category_path")
        private String category_path;
        @SerializedName("shop_price")
        private String shop_price;
        @SerializedName("original_img")
        private String original_img;
        @SerializedName("is_on_sale")
        private String is_on_sale;
        @SerializedName("is_free_shipping")
        private String is_free_shipping;
        @SerializedName("on_time")
        private String on_time;
        @SerializedName("is_recommend")
        private String is_recommend;
        @SerializedName("is_new")
        private String is_new;
        @SerializedName("is_hot")
        private String is_hot;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_sn() {
            return goods_sn;
        }

        public void setGoods_sn(String goods_sn) {
            this.goods_sn = goods_sn;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getClick_count() {
            return click_count;
        }

        public void setClick_count(String click_count) {
            this.click_count = click_count;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getStore_count() {
            return store_count;
        }

        public void setStore_count(String store_count) {
            this.store_count = store_count;
        }

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public String getCategory_path() {
            return category_path;
        }

        public void setCategory_path(String category_path) {
            this.category_path = category_path;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getOriginal_img() {
            return original_img;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
        }

        public String getIs_on_sale() {
            return is_on_sale;
        }

        public void setIs_on_sale(String is_on_sale) {
            this.is_on_sale = is_on_sale;
        }

        public String getIs_free_shipping() {
            return is_free_shipping;
        }

        public void setIs_free_shipping(String is_free_shipping) {
            this.is_free_shipping = is_free_shipping;
        }

        public String getOn_time() {
            return on_time;
        }

        public void setOn_time(String on_time) {
            this.on_time = on_time;
        }

        public String getIs_recommend() {
            return is_recommend;
        }

        public void setIs_recommend(String is_recommend) {
            this.is_recommend = is_recommend;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

        public String getIs_hot() {
            return is_hot;
        }

        public void setIs_hot(String is_hot) {
            this.is_hot = is_hot;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

    }

    public static class FilterResultData implements Parcelable {
        private String lowPrice;
        private String heightPrice;
        private Map<String, String> params;

        public FilterResultData(){}

        protected FilterResultData(Parcel in) {
            lowPrice = in.readString();
            heightPrice = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(lowPrice);
            dest.writeString(heightPrice);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<FilterResultData> CREATOR = new Creator<FilterResultData>() {
            @Override
            public FilterResultData createFromParcel(Parcel in) {
                return new FilterResultData(in);
            }

            @Override
            public FilterResultData[] newArray(int size) {
                return new FilterResultData[size];
            }
        };

        public String getLowPrice() {
            return TextUtils.isEmpty(lowPrice) ? "" : lowPrice;
        }

        public void setLowPrice(String lowPrice) {
            this.lowPrice = lowPrice;
        }

        public String getHeightPrice() {
            return TextUtils.isEmpty(heightPrice) ? "" : heightPrice;
        }

        public void setHeightPrice(String heightPrice) {
            this.heightPrice = heightPrice;
        }

        public Map<String, String> getParams() {
            return params == null ? new HashMap<>() : params;
        }

        public void setParams(Map<String, String> params) {
            this.params = params;
        }
    }
}
