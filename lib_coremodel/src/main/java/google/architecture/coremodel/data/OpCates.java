package google.architecture.coremodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import google.architecture.coremodel.util.TextUtil;

/**
 * @author lq.zeng
 * @date 2018/6/6
 */

public class OpCates implements Parcelable {
    public static final String TYPE_WEBVIEW = "webview";

    public static final String TYPE_COUPON_IDS = "coupon_ids";
    public static final String TYPE_COUPON_SINGLE = "single_coupon";

    public static final String TYPE_TOPIC_IDS = "topic_ids";
    public static final String TYPE_TOPIC_SINGLE = "single_topic";
    public static final String TYPE_TOPIC_3PIC = "single_topic_3";//新用户专场
    public static final String TYPE_BANNER = "banner";              //Banner类型

    @SerializedName("id")
    private int element_id;
    @SerializedName("element_type")
    private String element_type = TextUtil.TEXT_EMPTY;
    @SerializedName("mobile_name")
    private String title = TextUtil.TEXT_EMPTY;
    @SerializedName("subtitle")
    private String subtitle = TextUtil.TEXT_EMPTY;
    @SerializedName("extend")
    private String extend = TextUtil.TEXT_EMPTY;
    @SerializedName("image")
    private String pic = TextUtil.TEXT_EMPTY;
    @SerializedName("pic_width")
    private int pic_width;
    @SerializedName("pic_height")
    private int pic_height;
    @SerializedName("children")
    private OpChildrenCates children;
    @SerializedName("next_list")
    private ArrayList<OpDiscoverCates> child;

    //bannner
    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("h5_url")
    private String h5_url;

    @SerializedName("target")
    private String target;

    protected OpCates(Parcel in) {
        element_id = in.readInt();
        element_type = in.readString();
        title = in.readString();
        subtitle = in.readString();
        extend = in.readString();
        pic = in.readString();
        pic_width = in.readInt();
        pic_height = in.readInt();
        child = in.readArrayList(OpDiscoverCates.class.getClassLoader());

        name = in.readString();
        type = in.readString();
        h5_url = in.readString();
        target = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(element_id);
        dest.writeString(element_type);
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeString(extend);
        dest.writeString(pic);
        dest.writeInt(pic_width);
        dest.writeInt(pic_height);
        dest.writeList(child);

        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(h5_url);
        dest.writeString(target);
    }

    public static final Creator<OpCates> CREATOR = new Creator<OpCates>() {
        @Override
        public OpCates createFromParcel(Parcel in) {
            return new OpCates(in);
        }

        @Override
        public OpCates[] newArray(int size) {
            return new OpCates[size];
        }
    };

    public ArrayList<OpDiscoverCates> getChild() {
        return child;
    }

    public void setChild(ArrayList<OpDiscoverCates> child) {
        this.child = child;
    }

    public int getElement_id() {
        return element_id;
    }

    public void setElement_id(int element_id) {
        this.element_id = element_id;
    }

    public OpChildrenCates getChildren() {
        return children;
    }

    public void setChildren(OpChildrenCates children) {
        this.children = children;
    }

    public String getElement_type() {
        return element_type;
    }

    public void setElement_type(String element_type) {
        this.element_type = element_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getPic_width() {
        return pic_width;
    }

    public void setPic_width(int pic_width) {
        this.pic_width = pic_width;
    }

    public int getPic_height() {
        return pic_height;
    }

    public void setPic_height(int pic_height) {
        this.pic_height = pic_height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getH5_url() {
        return h5_url;
    }

    public void setH5_url(String h5_url) {
        this.h5_url = h5_url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean isTypeWebView() {

        return TYPE_WEBVIEW.equalsIgnoreCase(element_type);
    }

    public boolean isTypeBanner() {

        return TYPE_BANNER.equals(element_type);
    }

    @Override
    public String toString() {

        return "OpCates{" +
                "element_type='" + element_type + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", extend='" + extend + '\'' +
                ", pic='" + pic + '\'' +
                ", pic_width=" + pic_width +
                ", pic_height=" + pic_height +
                '}';
    }
}
