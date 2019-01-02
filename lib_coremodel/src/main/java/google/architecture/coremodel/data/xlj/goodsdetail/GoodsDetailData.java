package google.architecture.coremodel.data.xlj.goodsdetail;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import google.architecture.coremodel.data.DetailCommentInfo;

public class GoodsDetailData {
    @SerializedName("goodsId")
    private int goodsId; //商品ID
    @SerializedName("goodsSn")
    private String goodsSn;
    @SerializedName("productNo")
    private String productNo; //货号
    @SerializedName("goodsName")
    private String goodsName; //商品名称
    @SerializedName("goodsImg")
    private String goodsImg;
    @SerializedName("shopId")
    private int shopId;
    @SerializedName("goodsType")
    private int goodsType;
    @SerializedName("marketPrice")
    private String marketPrice; //吊牌价
    @SerializedName("shopPrice")
    private String shopPrice; //商品价格
    @SerializedName("warnStock")
    private int warnStock;
    @SerializedName("goodsStock")
    private int goodsStock; //库存
    @SerializedName("webStock")
    private int webStock;
    @SerializedName("goodsUnit")
    private String goodsUnit;
    @SerializedName("goodsTips")
    private String goodsTips;
    @SerializedName("isSale")
    private int isSale;
    @SerializedName("isBest")
    private int isBest;
    @SerializedName("isHot")
    private int isHot;
    @SerializedName("isNew")
    private int isNew;
    @SerializedName("isRecom")
    private int isRecom;
    @SerializedName("goodsCatIdPath")
    private java.util.List<String> goodsCatIdPath;
    @SerializedName("goodsCatId")
    private int goodsCatId;
    @SerializedName("shopCatId1")
    private int shopCatId1;
    @SerializedName("shopCatId2")
    private int shopCatId2;
    @SerializedName("brandId")
    private int brandId; //品牌ID
    @SerializedName("goodsDesc")
    private String goodsDesc; //商品详情
    @SerializedName("goodsStatus")
    private int goodsStatus;
    @SerializedName("saleNum")
    private int saleNum;
    @SerializedName("saleTime")
    private String saleTime;
    @SerializedName("visitNum")
    private int visitNum;
    @SerializedName("appraiseNum")
    private String appraiseNum;
    @SerializedName("isSpec")
    private int isSpec;
    @SerializedName("gallery")
    private java.util.List<String> gallery; //商品焦点图
    @SerializedName("goodsSeoKeywords")
    private String goodsSeoKeywords;
    @SerializedName("illegalRemarks")
    private String illegalRemarks;
    @SerializedName("dataFlag")
    private int dataFlag;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("isFreeShipping")
    private int isFreeShipping;
    @SerializedName("goodsSerachKeywords")
    private String goodsSerachKeywords;
    @SerializedName("goodSource")
    private int goodSource;
    @SerializedName("shop")
    private Shop shop;
    @SerializedName("saleSpec")
    private SaleSpec saleSpec;
    @SerializedName("spec")
    private Spec spec;
    @SerializedName("defaultSpecs")
    private DefaultSpecs defaultSpecs;
    @SerializedName("attrs")
    private java.util.List<Attrs> attrs;
    @SerializedName("scores")
    private String scores;
    @SerializedName("isFavorite")
    private int isFavorite;
    @SerializedName("goodsAppr")
    private java.util.List<DetailCommentInfo> goodsAppr;
    @SerializedName("promotion")
    private Promotion promotion;
    @SerializedName("couponsList")
    private java.util.List<CouponsItem> couponsList;
    @SerializedName("like")
    private java.util.List<Like> like;

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }
    public String getGoodsSn() {
        return goodsSn;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
    public String getProductNo() {
        return productNo;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }
    public String getGoodsImg() {
        return goodsImg;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
    public int getShopId() {
        return shopId;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }
    public int getGoodsType() {
        return goodsType;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }
    public String getMarketPrice() {
        return marketPrice;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }
    public String getShopPrice() {
        return shopPrice;
    }

    public void setWarnStock(int warnStock) {
        this.warnStock = warnStock;
    }
    public int getWarnStock() {
        return warnStock;
    }

    public void setGoodsStock(int goodsStock) {
        this.goodsStock = goodsStock;
    }
    public int getGoodsStock() {
        return goodsStock;
    }

    public void setWebStock(int webStock) {
        this.webStock = webStock;
    }
    public int getWebStock() {
        return webStock;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }
    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsTips(String goodsTips) {
        this.goodsTips = goodsTips;
    }
    public String getGoodsTips() {
        return goodsTips;
    }

    public void setIsSale(int isSale) {
        this.isSale = isSale;
    }
    public int getIsSale() {
        return isSale;
    }

    public void setIsBest(int isBest) {
        this.isBest = isBest;
    }
    public int getIsBest() {
        return isBest;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }
    public int getIsHot() {
        return isHot;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }
    public int getIsNew() {
        return isNew;
    }

    public void setIsRecom(int isRecom) {
        this.isRecom = isRecom;
    }
    public int getIsRecom() {
        return isRecom;
    }

    public void setGoodsCatIdPath(java.util.List<String> goodsCatIdPath) {
        this.goodsCatIdPath = goodsCatIdPath;
    }
    public java.util.List<String> getGoodsCatIdPath() {
        return goodsCatIdPath;
    }

    public void setGoodsCatId(int goodsCatId) {
        this.goodsCatId = goodsCatId;
    }
    public int getGoodsCatId() {
        return goodsCatId;
    }

    public void setShopCatId1(int shopCatId1) {
        this.shopCatId1 = shopCatId1;
    }
    public int getShopCatId1() {
        return shopCatId1;
    }

    public void setShopCatId2(int shopCatId2) {
        this.shopCatId2 = shopCatId2;
    }
    public int getShopCatId2() {
        return shopCatId2;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }
    public int getBrandId() {
        return brandId;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }
    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsStatus(int goodsStatus) {
        this.goodsStatus = goodsStatus;
    }
    public int getGoodsStatus() {
        return goodsStatus;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }
    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleTime(String saleTime) {
        this.saleTime = saleTime;
    }
    public String getSaleTime() {
        return saleTime;
    }

    public void setVisitNum(int visitNum) {
        this.visitNum = visitNum;
    }
    public int getVisitNum() {
        return visitNum;
    }

    public void setAppraiseNum(String appraiseNum) {
        this.appraiseNum = appraiseNum;
    }
    public String getAppraiseNum() {
        return appraiseNum;
    }

    public void setIsSpec(int isSpec) {
        this.isSpec = isSpec;
    }
    public int getIsSpec() {
        return isSpec;
    }

    public void setGallery(java.util.List<String> gallery) {
        this.gallery = gallery;
    }
    public java.util.List<String> getGallery() {
        return gallery;
    }

    public void setGoodsSeoKeywords(String goodsSeoKeywords) {
        this.goodsSeoKeywords = goodsSeoKeywords;
    }
    public String getGoodsSeoKeywords() {
        return goodsSeoKeywords;
    }

    public void setIllegalRemarks(String illegalRemarks) {
        this.illegalRemarks = illegalRemarks;
    }
    public String getIllegalRemarks() {
        return illegalRemarks;
    }

    public void setDataFlag(int dataFlag) {
        this.dataFlag = dataFlag;
    }
    public int getDataFlag() {
        return dataFlag;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getCreateTime() {
        return createTime;
    }

    public void setIsFreeShipping(int isFreeShipping) {
        this.isFreeShipping = isFreeShipping;
    }
    public int getIsFreeShipping() {
        return isFreeShipping;
    }

    public void setGoodsSerachKeywords(String goodsSerachKeywords) {
        this.goodsSerachKeywords = goodsSerachKeywords;
    }
    public String getGoodsSerachKeywords() {
        return goodsSerachKeywords;
    }

    public void setGoodSource(int goodSource) {
        this.goodSource = goodSource;
    }
    public int getGoodSource() {
        return goodSource;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
    public Shop getShop() {
        return shop;
    }

    public void setSaleSpec(SaleSpec saleSpec) {
        this.saleSpec = saleSpec;
    }
    public SaleSpec getSaleSpec() {
        return saleSpec;
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
    }
    public Spec getSpec() {
        return spec;
    }

    public void setDefaultSpecs(DefaultSpecs defaultSpecs) {
        this.defaultSpecs = defaultSpecs;
    }
    public DefaultSpecs getDefaultSpecs() {
        return defaultSpecs;
    }

    public void setAttrs(java.util.List<Attrs> attrs) {
        this.attrs = attrs;
    }
    public java.util.List<Attrs> getAttrs() {
        return attrs;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }
    public String getScores() {
        return scores;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }
    public int getIsFavorite() {
        return isFavorite;
    }

    public void setGoodsAppr(java.util.List<DetailCommentInfo> goodsAppr) {
        this.goodsAppr = goodsAppr;
    }
    public java.util.List<DetailCommentInfo> getGoodsAppr() {
        return goodsAppr;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
    public Promotion getPromotion() {
        return promotion;
    }

    public java.util.List<CouponsItem> getCouponsList() {
        return couponsList;
    }

    public void setCouponsList(List<CouponsItem> couponsList) {
        this.couponsList = couponsList;
    }

    public void setLike(java.util.List<Like> like) {
        this.like = like;
    }
    public java.util.List<Like> getLike() {
        return like;
    }

    @Override
    public String toString() {
        return "GoodsDetailData{" +
                "goodsId=" + goodsId +
                ", goodsSn='" + goodsSn + '\'' +
                ", productNo='" + productNo + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", shopId=" + shopId +
                ", goodsType=" + goodsType +
                ", marketPrice='" + marketPrice + '\'' +
                ", shopPrice='" + shopPrice + '\'' +
                ", warnStock=" + warnStock +
                ", goodsStock=" + goodsStock +
                ", webStock=" + webStock +
                ", goodsUnit='" + goodsUnit + '\'' +
                ", goodsTips='" + goodsTips + '\'' +
                ", isSale=" + isSale +
                ", isBest=" + isBest +
                ", isHot=" + isHot +
                ", isNew=" + isNew +
                ", isRecom=" + isRecom +
                ", goodsCatIdPath=" + goodsCatIdPath +
                ", goodsCatId=" + goodsCatId +
                ", shopCatId1=" + shopCatId1 +
                ", shopCatId2=" + shopCatId2 +
                ", brandId=" + brandId +
                ", goodsDesc='" + goodsDesc + '\'' +
                ", goodsStatus=" + goodsStatus +
                ", saleNum=" + saleNum +
                ", saleTime='" + saleTime + '\'' +
                ", visitNum=" + visitNum +
                ", appraiseNum=" + appraiseNum +
                ", isSpec=" + isSpec +
                ", gallery=" + gallery +
                ", goodsSeoKeywords='" + goodsSeoKeywords + '\'' +
                ", illegalRemarks='" + illegalRemarks + '\'' +
                ", dataFlag=" + dataFlag +
                ", createTime='" + createTime + '\'' +
                ", isFreeShipping=" + isFreeShipping +
                ", goodsSerachKeywords='" + goodsSerachKeywords + '\'' +
                ", goodSource=" + goodSource +
                ", shop=" + shop +
                ", saleSpec=" + saleSpec +
                ", spec=" + spec +
                ", defaultSpecs=" + defaultSpecs +
                ", attrs=" + attrs +
                ", scores='" + scores + '\'' +
                ", isFavorite=" + isFavorite +
                ", goodsAppr=" + goodsAppr +
                ", promotion=" + promotion +
                ", like=" + like +
                '}';
    }
}
