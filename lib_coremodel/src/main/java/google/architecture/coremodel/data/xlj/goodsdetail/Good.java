package google.architecture.coremodel.data.xlj.goodsdetail;

import com.google.gson.annotations.SerializedName;

public class Good {
    @SerializedName("allsaleId")
    private int allsaleId;
    @SerializedName("shopId")
    private int shopId;
    @SerializedName("allsaleTitle")
    private String allsaleTitle;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("saleType")
    private int saleType;
    @SerializedName("rewardType")
    private int rewardType;
    @SerializedName("useObjects")
    private int useObjects;
    @SerializedName("useObjectIds")
    private String useObjectIds;
    @SerializedName("dataFlag")
    private int dataFlag;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("id")
    private int id;
    @SerializedName("goodsId")
    private int goodsId;
    @SerializedName("salePrice")
    private String salePrice;

    public void setAllsaleId(int allsaleId) {
        this.allsaleId = allsaleId;
    }
    public int getAllsaleId() {
        return allsaleId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
    public int getShopId() {
        return shopId;
    }

    public void setAllsaleTitle(String allsaleTitle) {
        this.allsaleTitle = allsaleTitle;
    }
    public String getAllsaleTitle() {
        return allsaleTitle;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getStartDate() {
        return startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getEndDate() {
        return endDate;
    }

    public void setSaleType(int saleType) {
        this.saleType = saleType;
    }
    public int getSaleType() {
        return saleType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }
    public int getRewardType() {
        return rewardType;
    }

    public void setUseObjects(int useObjects) {
        this.useObjects = useObjects;
    }
    public int getUseObjects() {
        return useObjects;
    }

    public void setUseObjectIds(String useObjectIds) {
        this.useObjectIds = useObjectIds;
    }
    public String getUseObjectIds() {
        return useObjectIds;
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

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
    public int getGoodsId() {
        return goodsId;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }
    public String getSalePrice() {
        return salePrice;
    }

}
