package google.architecture.coremodel.data.xlj.searchfilter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchFilterData {
    @SerializedName("selectedTags")
    private List<SelectedTagsChild> selectedTags;
    @SerializedName("goodsList")
    private List<GoodsList> goodsList;
    @SerializedName("filters")
    private List<Filter> filters;
    @SerializedName("shopLists")
    private List<ShopLists> shopLists;

    public void setSelectedTags(List<SelectedTagsChild> selectedTags) {
        this.selectedTags = selectedTags;
    }
    public List<SelectedTagsChild> getSelectedTags() {
        return selectedTags;
    }

    public void setGoodsList(List<GoodsList> goodsList) {
        this.goodsList = goodsList;
    }
    public List<GoodsList> getGoodsList() {
        return goodsList;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public void setShopLists(List<ShopLists> shopLists) {
        this.shopLists = shopLists;
    }
    public List<ShopLists> getShopLists() {
        return shopLists;
    }
}
