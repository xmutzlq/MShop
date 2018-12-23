package google.architecture.coremodel.data.xlj.searchfilter;

import java.util.List;

public class SearchFilterData {
    private SelectedTags selectedTags;
    private List<GoodsList> goodsList;
    private Filters filters;
    private List<ShopLists> shopLists;
    public void setSelectedTags(SelectedTags selectedTags) {
        this.selectedTags = selectedTags;
    }
    public SelectedTags getSelectedTags() {
        return selectedTags;
    }

    public void setGoodsList(List<GoodsList> goodsList) {
        this.goodsList = goodsList;
    }
    public List<GoodsList> getGoodsList() {
        return goodsList;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }
    public Filters getFilters() {
        return filters;
    }

    public void setShopLists(List<ShopLists> shopLists) {
        this.shopLists = shopLists;
    }
    public List<ShopLists> getShopLists() {
        return shopLists;
    }
}
