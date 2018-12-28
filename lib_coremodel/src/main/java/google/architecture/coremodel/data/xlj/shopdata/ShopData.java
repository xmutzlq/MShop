package google.architecture.coremodel.data.xlj.shopdata;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopData {

    @SerializedName("slideImg")
    private List<ImgInfo> slideImg;//焦点图
    @SerializedName("brandNav")
    private List<ImgInfo> brandNav;//品牌
    @SerializedName("classify")
    private List<List<TextInfo>> classify;//分类
    @SerializedName("goodsList")
    private List<List<Goods>> goodsList;//商品楼层按顺序
    @SerializedName("floor")
    private List<ImgInfo> floor;

    public List<ImgInfo> getSlideImg() {
        return slideImg;
    }

    public void setSlideImg(List<ImgInfo> slideImg) {
        this.slideImg = slideImg;
    }

    public List<ImgInfo> getBrandNav() {
        return brandNav;
    }

    public void setBrandNav(List<ImgInfo> brandNav) {
        this.brandNav = brandNav;
    }

    public List<List<TextInfo>> getClassify() {
        return classify;
    }

    public void setClassify(List<List<TextInfo>> classify) {
        this.classify = classify;
    }

    public List<List<Goods>> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<List<Goods>> goodsList) {
        this.goodsList = goodsList;
    }

    public List<ImgInfo> getFloor() {
        return floor;
    }

    public void setFloor(List<ImgInfo> floor) {
        this.floor = floor;
    }
}
