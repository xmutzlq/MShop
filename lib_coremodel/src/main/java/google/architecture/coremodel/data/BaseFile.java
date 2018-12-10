package google.architecture.coremodel.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * @author lq.zeng
 * @date 2018/9/17
 */

public class BaseFile implements Parcelable {

    public static final int STATE_UPLOAD_NORMAL = 0;
    public static final int STATE_UPLOADING = 1;
    public static final int STATE_UPLOADED = 2;

    private transient long id;
    private transient String name;
    private transient String url;
    private transient String path;
    private transient long size;   //byte
    private transient String bucketId;  //Directory ID
    private transient String bucketName;  //Directory Name
    private transient long date;  //Added Date
    private transient boolean isSelected;
    private transient int uploadState = STATE_UPLOAD_NORMAL;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseFile)) return false;

        BaseFile file = (BaseFile) o;
        return !TextUtils.isEmpty(this.path) && this.path.equals(file.path);
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getUploadState() {
        return uploadState;
    }

    public void setUploadState(int uploadState) {
        this.uploadState = uploadState;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(path);
        dest.writeLong(size);
        dest.writeString(bucketId);
        dest.writeString(bucketName);
        dest.writeLong(date);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeInt(uploadState);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseFile> CREATOR = new Creator<BaseFile>() {
        @Override
        public BaseFile[] newArray(int size) {
            return new BaseFile[size];
        }

        @Override
        public BaseFile createFromParcel(Parcel in) {
            BaseFile file = new BaseFile();
            file.id = in.readLong();
            file.name = in.readString();
            file.url = in.readString();
            file.path = in.readString();
            file.size = in.readLong();
            file.bucketId = in.readString();
            file.bucketName = in.readString();
            file.date = in.readLong();
            file.isSelected = in.readByte() != 0;
            file.uploadState = in.readInt();
            return file;
        }
    };
}
