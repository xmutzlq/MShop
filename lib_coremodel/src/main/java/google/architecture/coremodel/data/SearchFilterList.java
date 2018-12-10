package google.architecture.coremodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/9/20
 */

public class SearchFilterList implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    protected SearchFilterList(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchFilterList> CREATOR = new Creator<SearchFilterList>() {
        @Override
        public SearchFilterList createFromParcel(Parcel in) {
            return new SearchFilterList(in);
        }

        @Override
        public SearchFilterList[] newArray(int size) {
            return new SearchFilterList[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
