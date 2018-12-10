package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/7/31
 */

public class HomeStyleInfo {

    @SerializedName("indicatorStyle")
    private String indicatorStyle;
    @SerializedName("indicatorRadius")
    private int indicatorRadius;
    @SerializedName("indicatorMargin")
    private int indicatorMargin;
    @SerializedName("indicatorPosition")
    private String indicatorPosition;
    @SerializedName("defaultIndicatorColor")
    private String defaultIndicatorColor;
    @SerializedName("indicatorColor")
    private String indicatorColor;
    @SerializedName("hasIndicator")
    private String hasIndicator;
    @SerializedName("infinite")
    private String infinite;
    @SerializedName("autoScroll")
    private int autoScroll;
    @SerializedName("animation")
    private String animation;

    @SerializedName("pageHeight")
    private int pageHeight;
    @SerializedName("pageWidth")
    private int pageWidth;

    @SerializedName("align")
    private String align;
    @SerializedName("bgColor")
    private String bgColor;

    @SerializedName("margin")
    private List<Integer> margin;
    @SerializedName("padding")
    private List<Integer> padding;

    @SerializedName("hGap")
    private int hGap;
    @SerializedName("vGap")
    private int vGap;

    @SerializedName("numberOfColumns")
    private int numberOfColumns;

    public String getIndicatorStyle() {
        return indicatorStyle;
    }

    public void setIndicatorStyle(String indicatorStyle) {
        this.indicatorStyle = indicatorStyle;
    }

    public int getIndicatorRadius() {
        return indicatorRadius;
    }

    public void setIndicatorRadius(int indicatorRadius) {
        this.indicatorRadius = indicatorRadius;
    }

    public int getIndicatorMargin() {
        return indicatorMargin;
    }

    public void setIndicatorMargin(int indicatorMargin) {
        this.indicatorMargin = indicatorMargin;
    }

    public String getIndicatorPosition() {
        return indicatorPosition;
    }

    public void setIndicatorPosition(String indicatorPosition) {
        this.indicatorPosition = indicatorPosition;
    }

    public String getDefaultIndicatorColor() {
        return defaultIndicatorColor;
    }

    public void setDefaultIndicatorColor(String defaultIndicatorColor) {
        this.defaultIndicatorColor = defaultIndicatorColor;
    }

    public String getIndicatorColor() {
        return indicatorColor;
    }

    public void setIndicatorColor(String indicatorColor) {
        this.indicatorColor = indicatorColor;
    }

    public String getHasIndicator() {
        return hasIndicator;
    }

    public void setHasIndicator(String hasIndicator) {
        this.hasIndicator = hasIndicator;
    }

    public String getInfinite() {
        return infinite;
    }

    public void setInfinite(String infinite) {
        this.infinite = infinite;
    }

    public int getAutoScroll() {
        return autoScroll;
    }

    public void setAutoScroll(int autoScroll) {
        this.autoScroll = autoScroll;
    }

    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    public int getPageHeight() {
        return pageHeight;
    }

    public void setPageHeight(int pageHeight) {
        this.pageHeight = pageHeight;
    }

    public int getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(int pageWidth) {
        this.pageWidth = pageWidth;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public List<Integer> getMargin() {
        return margin;
    }

    public void setMargin(List<Integer> margin) {
        this.margin = margin;
    }

    public List<Integer> getPadding() {
        return padding;
    }

    public void setPadding(List<Integer> padding) {
        this.padding = padding;
    }

    public int gethGap() {
        return hGap;
    }

    public void sethGap(int hGap) {
        this.hGap = hGap;
    }

    public int getvGap() {
        return vGap;
    }

    public void setvGap(int vGap) {
        this.vGap = vGap;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

}
