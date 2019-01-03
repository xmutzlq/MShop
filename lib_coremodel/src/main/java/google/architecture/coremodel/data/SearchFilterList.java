package google.architecture.coremodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/9/20
 */

public class SearchFilterList implements Parcelable {
    @SerializedName("c_id")
    private String id;
    @SerializedName("c_name")
    private String name;

    @SerializedName("c_char")
    private String c_char;
    @SerializedName("c_urlId")
    private String c_urlId;
    @SerializedName("select")
    private String select;

    protected SearchFilterList(Parcel in) {
        id = in.readString();
        name = in.readString();
        c_char = in.readString();
        c_urlId = in.readString();
        select = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(c_char);
        dest.writeString(c_urlId);
        dest.writeString(select);
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

    public String getC_char() {
        return c_char;
    }

    public void setC_char(String c_char) {
        this.c_char = c_char;
    }

    public String getC_urlId() {
        return c_urlId;
    }

    public void setC_urlId(String c_urlId) {
        this.c_urlId = c_urlId;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }
}
