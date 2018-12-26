package google.architecture.coremodel.data.xlj.shopdata;

import java.util.List;

public class ShopData {

    private List<ImgInfo> slideImg;//焦点图

    private List<ImgInfo> brandNav;//品牌

    private List<List<TextInfo>> classify;//分类

    private List<List<Goods>> goodsList;//商品楼层按顺序

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
}
