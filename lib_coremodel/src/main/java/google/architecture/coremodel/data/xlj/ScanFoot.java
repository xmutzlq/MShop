package google.architecture.coremodel.data.xlj;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ScanFoot implements Serializable {

    @SerializedName("guid")
    private String guid;
    @SerializedName("title")
    private String title;
    @SerializedName("shortTitle")
    private String shortTitle;
    @SerializedName("address")
    private String address;
    @SerializedName("lbSx")
    private int lbSx;
    @SerializedName("lbSy")
    private int lbSy;
    @SerializedName("text")
    private String text;
    @SerializedName("newPhotoId")
    private String newPhotoId;
    @SerializedName("footTypeName")
    private String footTypeName;
    @SerializedName("footLeft")
    private String footLeft;
    @SerializedName("leftLength")
    private double leftLength;//左脚-长
    @SerializedName("leftBreadth")
    private double leftBreadth;//左脚-宽
    @SerializedName("leftHigh")
    private double leftHigh;//左脚-高
    @SerializedName("leftLowHigh")
    private double leftLowHigh;//左脚-浅口脚背高度
    @SerializedName("leftHeelDist")
    private double leftHeelDist;//左脚-足弓脚后跟距离
    @SerializedName("leftFootCirlen")
    private double leftFootCirlen;//左脚-脚围
    @SerializedName("leftInstepCirlen")
    private double leftInstepCirlen;//左脚-脚背围
    @SerializedName("footRight")
    private String footRight;
    @SerializedName("rightLength")
    private double rightLength;//右脚-长
    @SerializedName("rightBreadth")
    private double rightBreadth;//右脚-宽
    @SerializedName("rightHigh")
    private double rightHigh;//右脚-高
    @SerializedName("rightLowHigh")
    private double rightLowHigh;//右脚-浅口脚背高度
    @SerializedName("rightHeelDist")
    private double rightHeelDist;//右脚-足弓脚后跟距离
    @SerializedName("rightFootCirlen")
    private double rightFootCirlen;//右脚-脚围
    @SerializedName("rightInstepCirlen")//右脚-脚背围
    private double rightInstepCirlen;
    @SerializedName("machingCount")
    private int machingCount;
    @SerializedName("foot_breadth")
    private String foot_breadth;
    @SerializedName("instep_high")
    private String instep_high;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLbSx() {
        return lbSx;
    }

    public void setLbSx(int lbSx) {
        this.lbSx = lbSx;
    }

    public int getLbSy() {
        return lbSy;
    }

    public void setLbSy(int lbSy) {
        this.lbSy = lbSy;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNewPhotoId() {
        return newPhotoId;
    }

    public void setNewPhotoId(String newPhotoId) {
        this.newPhotoId = newPhotoId;
    }

    public String getFootTypeName() {
        return footTypeName;
    }

    public void setFootTypeName(String footTypeName) {
        this.footTypeName = footTypeName;
    }

    public String getFootLeft() {
        return footLeft;
    }

    public void setFootLeft(String footLeft) {
        this.footLeft = footLeft;
    }

    public double getLeftLength() {
        return leftLength;
    }

    public void setLeftLength(double leftLength) {
        this.leftLength = leftLength;
    }

    public double getLeftBreadth() {
        return leftBreadth;
    }

    public void setLeftBreadth(double leftBreadth) {
        this.leftBreadth = leftBreadth;
    }

    public double getLeftHigh() {
        return leftHigh;
    }

    public void setLeftHigh(double leftHigh) {
        this.leftHigh = leftHigh;
    }

    public double getLeftLowHigh() {
        return leftLowHigh;
    }

    public void setLeftLowHigh(double leftLowHigh) {
        this.leftLowHigh = leftLowHigh;
    }

    public double getLeftHeelDist() {
        return leftHeelDist;
    }

    public void setLeftHeelDist(int leftHeelDist) {
        this.leftHeelDist = leftHeelDist;
    }

    public double getLeftFootCirlen() {
        return leftFootCirlen;
    }

    public void setLeftFootCirlen(double leftFootCirlen) {
        this.leftFootCirlen = leftFootCirlen;
    }

    public double getLeftInstepCirlen() {
        return leftInstepCirlen;
    }

    public void setLeftInstepCirlen(double leftInstepCirlen) {
        this.leftInstepCirlen = leftInstepCirlen;
    }

    public String getFootRight() {
        return footRight;
    }

    public void setFootRight(String footRight) {
        this.footRight = footRight;
    }

    public double getRightLength() {
        return rightLength;
    }

    public void setRightLength(double rightLength) {
        this.rightLength = rightLength;
    }

    public double getRightBreadth() {
        return rightBreadth;
    }

    public void setRightBreadth(double rightBreadth) {
        this.rightBreadth = rightBreadth;
    }

    public double getRightHigh() {
        return rightHigh;
    }

    public void setRightHigh(double rightHigh) {
        this.rightHigh = rightHigh;
    }

    public double getRightLowHigh() {
        return rightLowHigh;
    }

    public void setRightLowHigh(double rightLowHigh) {
        this.rightLowHigh = rightLowHigh;
    }

    public double getRightHeelDist() {
        return rightHeelDist;
    }

    public void setRightHeelDist(int rightHeelDist) {
        this.rightHeelDist = rightHeelDist;
    }

    public double getRightFootCirlen() {
        return rightFootCirlen;
    }

    public void setRightFootCirlen(double rightFootCirlen) {
        this.rightFootCirlen = rightFootCirlen;
    }

    public double getRightInstepCirlen() {
        return rightInstepCirlen;
    }

    public void setRightInstepCirlen(double rightInstepCirlen) {
        this.rightInstepCirlen = rightInstepCirlen;
    }

    public int getMachingCount() {
        return machingCount;
    }

    public void setMachingCount(int machingCount) {
        this.machingCount = machingCount;
    }

    public String getFoot_breadth() {
        return foot_breadth;
    }

    public void setFoot_breadth(String foot_breadth) {
        this.foot_breadth = foot_breadth;
    }

    public String getInstep_high() {
        return instep_high;
    }

    public void setInstep_high(String instep_high) {
        this.instep_high = instep_high;
    }
}
