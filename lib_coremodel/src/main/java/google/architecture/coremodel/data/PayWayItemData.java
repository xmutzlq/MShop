package google.architecture.coremodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/9/11
 */

public class PayWayItemData implements Parcelable {
    @SerializedName("code")
    private String code; //代码标识  weixin=微信支付，alipay=支付宝
    @SerializedName("name")
    private String name; //名称
    @SerializedName("icon")
    private String icon; //图标

    public boolean isChecked;

    protected PayWayItemData(Parcel in) {
        code = in.readString();
        name = in.readString();
        icon = in.readString();
        isChecked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(icon);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PayWayItemData> CREATOR = new Creator<PayWayItemData>() {
        @Override
        public PayWayItemData createFromParcel(Parcel in) {
            return new PayWayItemData(in);
        }

        @Override
        public PayWayItemData[] newArray(int size) {
            return new PayWayItemData[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
