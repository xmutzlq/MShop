package google.architecture.coremodel.data.xlj;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ScanBody implements Serializable {

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
    @SerializedName("scan_id")
    private String scan_id;
    @SerializedName("head_cirlen")
    private double head_cirlen;//头围
    @SerializedName("neck_cirlen")
    private double neck_cirlen;//颈围
    @SerializedName("neck_croth_len")
    private double neck_croth_len;//颈裆长
    @SerializedName("chest_cirlen")
    private double chest_cirlen;//胸围
    @SerializedName("waist_cirlen")
    private double waist_cirlen;//腰围
    @SerializedName("pelvis_cirlen")
    private double pelvis_cirlen;//臀围
    @SerializedName("wrist_left_cirlen")
    private double wrist_left_cirlen;//手腕围(左)
    @SerializedName("wrist_right_cirlen")
    private double wrist_right_cirlen;//手腕围(右)
    @SerializedName("uparm_left_cirlen")
    private double uparm_left_cirlen;//上臂围(左)
    @SerializedName("uparm_right_cirlen")
    private double uparm_right_cirlen;//上臂围(右)
    @SerializedName("forearm_left_cirlen")
    private double forearm_left_cirlen;//前臂围(左)
    @SerializedName("forearm_right_cirlen")
    private double forearm_right_cirlen;//前臂围(右)
    @SerializedName("arm_left_len")
    private double arm_left_len;//臂长(左)
    @SerializedName("arm_right_len")
    private double arm_right_len;//臂长(右)
    @SerializedName("inside_leg_left_len")
    private double inside_leg_left_len;//内腿长(左)
    @SerializedName("inside_leg_right_len")
    private double inside_leg_right_len;//内腿长(右)
    @SerializedName("thigh_left_cirlen")
    private double thigh_left_cirlen;//大腿围(左)
    @SerializedName("thigh_right_cirlen")
    private double thigh_right_cirlen;//大腿围(右)
    @SerializedName("calf_left_cirlen")
    private double calf_left_cirlen;//小腿围(左)
    @SerializedName("calf_right_cirlen")
    private double calf_right_cirlen;//小腿围(右)
    @SerializedName("ankle_left_cirlen")
    private double ankle_left_cirlen;//脚踝围(左)
    @SerializedName("ankle_right_cirlen")
    private double ankle_right_cirlen;//脚踝围(右)
    @SerializedName("body_height")
    private double body_height;//身高
    @SerializedName("shoulder_breadth")
    private double shoulder_breadth;//肩宽
    @SerializedName("back_len")
    private double back_len;//后背长
    @SerializedName("front_back_len")
    private double front_back_len;//前背长
    @SerializedName("leg_len")
    private double leg_len;//腿长

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

    public String getScan_id() {
        return scan_id;
    }

    public void setScan_id(String scan_id) {
        this.scan_id = scan_id;
    }

    public double getHead_cirlen() {
        return head_cirlen;
    }

    public void setHead_cirlen(double head_cirlen) {
        this.head_cirlen = head_cirlen;
    }

    public double getNeck_cirlen() {
        return neck_cirlen;
    }

    public void setNeck_cirlen(double neck_cirlen) {
        this.neck_cirlen = neck_cirlen;
    }

    public double getNeck_croth_len() {
        return neck_croth_len;
    }

    public void setNeck_croth_len(double neck_croth_len) {
        this.neck_croth_len = neck_croth_len;
    }

    public double getChest_cirlen() {
        return chest_cirlen;
    }

    public void setChest_cirlen(double chest_cirlen) {
        this.chest_cirlen = chest_cirlen;
    }

    public double getWaist_cirlen() {
        return waist_cirlen;
    }

    public void setWaist_cirlen(double waist_cirlen) {
        this.waist_cirlen = waist_cirlen;
    }

    public double getPelvis_cirlen() {
        return pelvis_cirlen;
    }

    public void setPelvis_cirlen(double pelvis_cirlen) {
        this.pelvis_cirlen = pelvis_cirlen;
    }

    public double getWrist_left_cirlen() {
        return wrist_left_cirlen;
    }

    public void setWrist_left_cirlen(double wrist_left_cirlen) {
        this.wrist_left_cirlen = wrist_left_cirlen;
    }

    public double getWrist_right_cirlen() {
        return wrist_right_cirlen;
    }

    public void setWrist_right_cirlen(double wrist_right_cirlen) {
        this.wrist_right_cirlen = wrist_right_cirlen;
    }

    public double getUparm_left_cirlen() {
        return uparm_left_cirlen;
    }

    public void setUparm_left_cirlen(double uparm_left_cirlen) {
        this.uparm_left_cirlen = uparm_left_cirlen;
    }

    public double getUparm_right_cirlen() {
        return uparm_right_cirlen;
    }

    public void setUparm_right_cirlen(double uparm_right_cirlen) {
        this.uparm_right_cirlen = uparm_right_cirlen;
    }

    public double getForearm_left_cirlen() {
        return forearm_left_cirlen;
    }

    public void setForearm_left_cirlen(double forearm_left_cirlen) {
        this.forearm_left_cirlen = forearm_left_cirlen;
    }

    public double getForearm_right_cirlen() {
        return forearm_right_cirlen;
    }

    public void setForearm_right_cirlen(double forearm_right_cirlen) {
        this.forearm_right_cirlen = forearm_right_cirlen;
    }

    public double getArm_left_len() {
        return arm_left_len;
    }

    public void setArm_left_len(double arm_left_len) {
        this.arm_left_len = arm_left_len;
    }

    public double getArm_right_len() {
        return arm_right_len;
    }

    public void setArm_right_len(double arm_right_len) {
        this.arm_right_len = arm_right_len;
    }

    public double getInside_leg_left_len() {
        return inside_leg_left_len;
    }

    public void setInside_leg_left_len(double inside_leg_left_len) {
        this.inside_leg_left_len = inside_leg_left_len;
    }

    public double getInside_leg_right_len() {
        return inside_leg_right_len;
    }

    public void setInside_leg_right_len(double inside_leg_right_len) {
        this.inside_leg_right_len = inside_leg_right_len;
    }

    public double getThigh_left_cirlen() {
        return thigh_left_cirlen;
    }

    public void setThigh_left_cirlen(double thigh_left_cirlen) {
        this.thigh_left_cirlen = thigh_left_cirlen;
    }

    public double getThigh_right_cirlen() {
        return thigh_right_cirlen;
    }

    public void setThigh_right_cirlen(double thigh_right_cirlen) {
        this.thigh_right_cirlen = thigh_right_cirlen;
    }

    public double getCalf_left_cirlen() {
        return calf_left_cirlen;
    }

    public void setCalf_left_cirlen(double calf_left_cirlen) {
        this.calf_left_cirlen = calf_left_cirlen;
    }

    public double getCalf_right_cirlen() {
        return calf_right_cirlen;
    }

    public void setCalf_right_cirlen(double calf_right_cirlen) {
        this.calf_right_cirlen = calf_right_cirlen;
    }

    public double getAnkle_left_cirlen() {
        return ankle_left_cirlen;
    }

    public void setAnkle_left_cirlen(double ankle_left_cirlen) {
        this.ankle_left_cirlen = ankle_left_cirlen;
    }

    public double getAnkle_right_cirlen() {
        return ankle_right_cirlen;
    }

    public void setAnkle_right_cirlen(double ankle_right_cirlen) {
        this.ankle_right_cirlen = ankle_right_cirlen;
    }

    public double getBody_height() {
        return body_height;
    }

    public void setBody_height(double body_height) {
        this.body_height = body_height;
    }

    public double getShoulder_breadth() {
        return shoulder_breadth;
    }

    public void setShoulder_breadth(double shoulder_breadth) {
        this.shoulder_breadth = shoulder_breadth;
    }

    public double getBack_len() {
        return back_len;
    }

    public void setBack_len(double back_len) {
        this.back_len = back_len;
    }

    public double getFront_back_len() {
        return front_back_len;
    }

    public void setFront_back_len(double front_back_len) {
        this.front_back_len = front_back_len;
    }

    public double getLeg_len() {
        return leg_len;
    }

    public void setLeg_len(double leg_len) {
        this.leg_len = leg_len;
    }
}
