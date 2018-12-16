package google.architecture.coremodel.data.xlj.goodsdetail;

import com.google.gson.annotations.SerializedName;

public class Shop {
    @SerializedName("shopAddress")
    private String shopAddress;
    @SerializedName("shopKeeper")
    private String shopKeeper;
    @SerializedName("shopImg")
    private String shopImg;
    @SerializedName("shopQQ")
    private String shopQQ;
    @SerializedName("shopId")
    private int shopId;
    @SerializedName("shopName")
    private String shopName;
    @SerializedName("shopTel")
    private String shopTel;
    @SerializedName("freight")
    private int freight;
    @SerializedName("areaId")
    private int areaId;
    @SerializedName("scoreId")
    private int scoreId;
    @SerializedName("totalScore")
    private int totalScore;
    @SerializedName("goodsScore")
    private int goodsScore;
    @SerializedName("serviceScore")
    private int serviceScore;
    @SerializedName("timeScore")
    private int timeScore;
    @SerializedName("areas")
    private Areas areas;
    @SerializedName("catId")
    private int catId;
    @SerializedName("cat")
    private String cat;

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }
    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopKeeper(String shopKeeper) {
        this.shopKeeper = shopKeeper;
    }
    public String getShopKeeper() {
        return shopKeeper;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }
    public String getShopImg() {
        return shopImg;
    }

    public void setShopQQ(String shopQQ) {
        this.shopQQ = shopQQ;
    }
    public String getShopQQ() {
        return shopQQ;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
    public int getShopId() {
        return shopId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getShopName() {
        return shopName;
    }

    public void setShopTel(String shopTel) {
        this.shopTel = shopTel;
    }
    public String getShopTel() {
        return shopTel;
    }

    public void setFreight(int freight) {
        this.freight = freight;
    }
    public int getFreight() {
        return freight;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }
    public int getAreaId() {
        return areaId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }
    public int getScoreId() {
        return scoreId;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
    public int getTotalScore() {
        return totalScore;
    }

    public void setGoodsScore(int goodsScore) {
        this.goodsScore = goodsScore;
    }
    public int getGoodsScore() {
        return goodsScore;
    }

    public void setServiceScore(int serviceScore) {
        this.serviceScore = serviceScore;
    }
    public int getServiceScore() {
        return serviceScore;
    }

    public void setTimeScore(int timeScore) {
        this.timeScore = timeScore;
    }
    public int getTimeScore() {
        return timeScore;
    }

    public void setAreas(Areas areas) {
        this.areas = areas;
    }
    public Areas getAreas() {
        return areas;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }
    public int getCatId() {
        return catId;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
    public String getCat() {
        return cat;
    }
}
