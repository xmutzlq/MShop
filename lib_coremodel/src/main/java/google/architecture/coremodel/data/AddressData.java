package google.architecture.coremodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/9/10
 */

public class AddressData {

    @SerializedName("list")
    private List<AddressItem> list;

    public List<AddressItem> getList() {
        return list;
    }

    public void setList(List<AddressItem> list) {
        this.list = list;
    }

    public static class AddressItem implements Parcelable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public AddressItem(){}

        protected AddressItem(Parcel in) {
            id = in.readInt();
            name = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<AddressItem> CREATOR = new Creator<AddressItem>() {
            @Override
            public AddressItem createFromParcel(Parcel in) {
                return new AddressItem(in);
            }

            @Override
            public AddressItem[] newArray(int size) {
                return new AddressItem[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
