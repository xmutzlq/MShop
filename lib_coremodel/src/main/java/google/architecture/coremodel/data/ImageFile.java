package google.architecture.coremodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author lq.zeng
 * @date 2018/9/17
 */

public class ImageFile extends BaseFile implements MultiItemEntity, Parcelable {
    private int orientation;   //0, 90, 180, 270
    private int itemType;

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public ImageFile() {
    }

    public ImageFile(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(getName());
        dest.writeString(getPath());
        dest.writeLong(getSize());
        dest.writeString(getBucketId());
        dest.writeString(getBucketName());
        dest.writeLong(getDate());
        dest.writeByte((byte) (isSelected() ? 1 : 0));
        dest.writeInt(getUploadState());
        dest.writeInt(orientation);
        dest.writeInt(itemType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageFile> CREATOR = new Creator<ImageFile>() {
        @Override
        public ImageFile[] newArray(int size) {
            return new ImageFile[size];
        }

        @Override
        public ImageFile createFromParcel(Parcel in) {
            ImageFile file = new ImageFile();
            file.setId(in.readLong());
            file.setName(in.readString());
            file.setPath(in.readString());
            file.setSize(in.readLong());
            file.setBucketId(in.readString());
            file.setBucketName(in.readString());
            file.setDate(in.readLong());
            file.setSelected(in.readByte() != 0);
            file.setUploadState(in.readInt());
            file.setOrientation(in.readInt());
            file.setItemType(in.readInt());
            return file;
        }
    };

    @Override
    public int getItemType() {
        return itemType;
    }
}
