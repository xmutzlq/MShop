package google.architecture.coremodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import google.architecture.coremodel.util.IOUtil;

/**
 * @author lq.zeng
 * @date 2018/6/6
 */

public class OpDiscoverCates extends OpCates implements Parcelable {
    public static final String TYPE_TITLE = "title";  //本地标记

    @SerializedName("parent_id")
    private int parentId;
    @SerializedName("parentChannelId")
    private int parentChannelId;
    @SerializedName("parentTitle")
    private String parentTitle;
    @SerializedName("childPosition")
    private int childPosition;
    @SerializedName("parentPosition")
    private int parentPosition;

    protected OpDiscoverCates(Parcel in) {
        super(in);
        parentId = in.readInt();
        parentChannelId = in.readInt();
        parentTitle = in.readString();
        childPosition = in.readInt();
        parentPosition = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(parentId);
        dest.writeInt(parentChannelId);
        dest.writeString(parentTitle);
        dest.writeInt(childPosition);
        dest.writeInt(parentPosition);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OpDiscoverCates> CREATOR = new Creator<OpDiscoverCates>() {
        @Override
        public OpDiscoverCates createFromParcel(Parcel in) {
            return new OpDiscoverCates(in);
        }

        @Override
        public OpDiscoverCates[] newArray(int size) {
            return new OpDiscoverCates[size];
        }
    };

    public int getChildPosition() {

        return childPosition;
    }

    public void setChildPosition(int childPosition) {

        this.childPosition = childPosition;
    }

    public int getParentPosition() {

        return parentPosition;
    }

    public void setParentPosition(int parentPosition) {

        this.parentPosition = parentPosition;
    }

    public int getParentId() {

        return parentId;
    }

    public void setParentId(int parentId) {

        this.parentId = parentId;
    }

    public int getParentChannelId() {

        return parentChannelId;
    }

    public void setParentChannelId(int channelId) {

        this.parentChannelId = channelId;
    }

    public String getParentTitle() {

        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {

        this.parentTitle = parentTitle;
    }

    public boolean isTypeTitle() {

        return TYPE_TITLE.equals(getElement_type());
    }


    public OpDiscoverCates clone() {

        OpDiscoverCates outer = null;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        ByteArrayInputStream bais = null;
        try {
            // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            bais = new ByteArrayInputStream(baos.toByteArray());
            ois = new ObjectInputStream(bais);
            outer = (OpDiscoverCates) ois.readObject();
        } catch (Exception e) {

            return this;
        } finally {

            IOUtil.closeOutStream(baos);
            IOUtil.closeOutStream(oos);
            IOUtil.closeInStream(ois);
            IOUtil.closeInStream(bais);
        }
        return outer;
    }
}
