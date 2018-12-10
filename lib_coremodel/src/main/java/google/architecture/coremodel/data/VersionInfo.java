package google.architecture.coremodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/8/14
 */

public class VersionInfo implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("platform")
    private String platform;
    @SerializedName("update_log")
    private String update_log;
    @SerializedName("version_code")
    private String version_code;
    @SerializedName("version_name")
    private String version_name;
    @SerializedName("download_url")
    private String download_url;
    @SerializedName("base_url")
    private String base_url;
    @SerializedName("is_force")
    private String is_force;
    @SerializedName("create_time")
    private String create_time;

    protected VersionInfo(Parcel in) {
        id = in.readString();
        platform = in.readString();
        update_log = in.readString();
        version_code = in.readString();
        version_name = in.readString();
        download_url = in.readString();
        base_url = in.readString();
        is_force = in.readString();
        create_time = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(platform);
        dest.writeString(update_log);
        dest.writeString(version_code);
        dest.writeString(version_name);
        dest.writeString(download_url);
        dest.writeString(base_url);
        dest.writeString(is_force);
        dest.writeString(create_time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VersionInfo> CREATOR = new Creator<VersionInfo>() {
        @Override
        public VersionInfo createFromParcel(Parcel in) {
            return new VersionInfo(in);
        }

        @Override
        public VersionInfo[] newArray(int size) {
            return new VersionInfo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUpdate_log() {
        return update_log;
    }

    public void setUpdate_log(String update_log) {
        this.update_log = update_log;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public String getIs_force() {
        return is_force;
    }

    public void setIs_force(String is_force) {
        this.is_force = is_force;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
