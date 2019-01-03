package google.architecture.coremodel.data.xlj.searchfilter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ShopLists implements Parcelable {
    @SerializedName("shopId")
    private int shopId;
    @SerializedName("shopName")
    private String shopName;

    protected ShopLists(Parcel in) {
        shopId = in.readInt();
        shopName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(shopId);
        dest.writeString(shopName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopLists> CREATOR = new Creator<ShopLists>() {
        @Override
        public ShopLists createFromParcel(Parcel in) {
            return new ShopLists(in);
        }

        @Override
        public ShopLists[] newArray(int size) {
            return new ShopLists[size];
        }
    };

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
}
