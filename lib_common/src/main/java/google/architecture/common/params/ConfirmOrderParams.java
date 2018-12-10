package google.architecture.common.params;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lq.zeng
 * @date 2018/9/20
 */

public class ConfirmOrderParams implements Parcelable{
    public String goodsId;
    public String itemsId;
    public String goodsNum;

    public ConfirmOrderParams() {}

    protected ConfirmOrderParams(Parcel in) {
        goodsId = in.readString();
        itemsId = in.readString();
        goodsNum = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(goodsId);
        dest.writeString(itemsId);
        dest.writeString(goodsNum);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ConfirmOrderParams> CREATOR = new Creator<ConfirmOrderParams>() {
        @Override
        public ConfirmOrderParams createFromParcel(Parcel in) {
            return new ConfirmOrderParams(in);
        }

        @Override
        public ConfirmOrderParams[] newArray(int size) {
            return new ConfirmOrderParams[size];
        }
    };
}
