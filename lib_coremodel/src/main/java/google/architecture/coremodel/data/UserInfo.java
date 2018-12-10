package google.architecture.coremodel.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lq.zeng
 * @date 2018/4/11
 */

public class UserInfo implements Parcelable {
    public String id;
    public String openId;
    public String cellphone;//手机号
    public String email;//邮箱
    public String realname;//实名姓名
    public String username; //用户名
    public String access_token;//

    public UserInfo() {}

    protected UserInfo(Parcel in) {
        id = in.readString();
        openId = in.readString();
        cellphone = in.readString();
        email = in.readString();
        realname = in.readString();
        username = in.readString();
        access_token = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(openId);
        parcel.writeString(cellphone);
        parcel.writeString(email);
        parcel.writeString(realname);
        parcel.writeString(username);
        parcel.writeString(access_token);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", openId='" + openId + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", email='" + email + '\'' +
                ", realname='" + realname + '\'' +
                ", username='" + username + '\'' +
                ", access_token='" + access_token + '\'' +
                '}';
    }
}
