package google.architecture.coremodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/9/13
 */

public class S_UserInfo implements Parcelable {
    @SerializedName("user_id")
    public String user_id;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("paypwd")
    public String paypwd;
    @SerializedName("sex")
    public String sex;
    @SerializedName("birthday")
    public String birthday;
    @SerializedName("user_money")
    public String user_money;
    @SerializedName("frozen_money")
    public String frozen_money;
    @SerializedName("distribut_money")
    public String distribut_money;
    @SerializedName("underling_number")
    public String underling_number;
    @SerializedName("pay_points")
    public String pay_points;
    @SerializedName("address_id")
    public String address_id;
    @SerializedName("reg_time")
    public String reg_time;
    @SerializedName("last_login")
    public String last_login;
    @SerializedName("last_ip")
    public String last_ip;
    @SerializedName("qq")
    public String qq;
    @SerializedName("mobile")
    public String mobile;
    @SerializedName("mobile_validated")
    public String mobile_validated;
    @SerializedName("oauth")
    public String oauth;
    @SerializedName("openid")
    public String openid;
    @SerializedName("unionid")
    public String unionid;
    @SerializedName("head_pic")
    public String head_pic;
    @SerializedName("province")
    public String province;
    @SerializedName("city")
    public String city;
    @SerializedName("district")
    public String district;
    @SerializedName("email_validated")
    public String email_validated;
    @SerializedName("nickname")
    public String nickname;
    @SerializedName("level")
    public String level;
    @SerializedName("discount")
    public String discount;
    @SerializedName("total_amount")
    public String total_amount;
    @SerializedName("is_lock")
    public String is_lock;
    @SerializedName("is_distribut")
    public String is_distribut;
    @SerializedName("first_leader")
    public String first_leader;
    @SerializedName("second_leader")
    public String second_leader;
    @SerializedName("third_leader")
    public String third_leader;
    @SerializedName("token")
    public String token;
    @SerializedName("message_mask")
    public String message_mask;
    @SerializedName("push_id")
    public String push_id;
    @SerializedName("distribut_level")
    public String distribut_level;
    @SerializedName("is_vip")
    public String is_vip;
    @SerializedName("device_code")
    public String device_code;
    @SerializedName("version_code")
    public String version_code;
    @SerializedName("phone_code")
    public String phone_code;
    @SerializedName("username")
    public String username;
    @SerializedName("message_num")
    public String message_num;
    @SerializedName("access_token")
    public String access_token;

    public S_UserInfo() {

    }

    protected S_UserInfo(Parcel in) {
        user_id = in.readString();
        email = in.readString();
        password = in.readString();
        paypwd = in.readString();
        sex = in.readString();
        birthday = in.readString();
        user_money = in.readString();
        frozen_money = in.readString();
        distribut_money = in.readString();
        underling_number = in.readString();
        pay_points = in.readString();
        address_id = in.readString();
        reg_time = in.readString();
        last_login = in.readString();
        last_ip = in.readString();
        qq = in.readString();
        mobile = in.readString();
        mobile_validated = in.readString();
        oauth = in.readString();
        openid = in.readString();
        unionid = in.readString();
        head_pic = in.readString();
        province = in.readString();
        city = in.readString();
        district = in.readString();
        email_validated = in.readString();
        nickname = in.readString();
        level = in.readString();
        discount = in.readString();
        total_amount = in.readString();
        is_lock = in.readString();
        is_distribut = in.readString();
        first_leader = in.readString();
        second_leader = in.readString();
        third_leader = in.readString();
        token = in.readString();
        message_mask = in.readString();
        push_id = in.readString();
        distribut_level = in.readString();
        is_vip = in.readString();
        device_code = in.readString();
        version_code = in.readString();
        phone_code = in.readString();
        username = in.readString();
        message_num = in.readString();
        access_token = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(paypwd);
        dest.writeString(sex);
        dest.writeString(birthday);
        dest.writeString(user_money);
        dest.writeString(frozen_money);
        dest.writeString(distribut_money);
        dest.writeString(underling_number);
        dest.writeString(pay_points);
        dest.writeString(address_id);
        dest.writeString(reg_time);
        dest.writeString(last_login);
        dest.writeString(last_ip);
        dest.writeString(qq);
        dest.writeString(mobile);
        dest.writeString(mobile_validated);
        dest.writeString(oauth);
        dest.writeString(openid);
        dest.writeString(unionid);
        dest.writeString(head_pic);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(district);
        dest.writeString(email_validated);
        dest.writeString(nickname);
        dest.writeString(level);
        dest.writeString(discount);
        dest.writeString(total_amount);
        dest.writeString(is_lock);
        dest.writeString(is_distribut);
        dest.writeString(first_leader);
        dest.writeString(second_leader);
        dest.writeString(third_leader);
        dest.writeString(token);
        dest.writeString(message_mask);
        dest.writeString(push_id);
        dest.writeString(distribut_level);
        dest.writeString(is_vip);
        dest.writeString(device_code);
        dest.writeString(version_code);
        dest.writeString(phone_code);
        dest.writeString(username);
        dest.writeString(message_num);
        dest.writeString(access_token);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<S_UserInfo> CREATOR = new Creator<S_UserInfo>() {
        @Override
        public S_UserInfo createFromParcel(Parcel in) {
            return new S_UserInfo(in);
        }

        @Override
        public S_UserInfo[] newArray(int size) {
            return new S_UserInfo[size];
        }
    };

    @Override
    public String toString() {
        return "S_UserInfo{" +
                "user_id='" + user_id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", paypwd='" + paypwd + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", user_money='" + user_money + '\'' +
                ", frozen_money='" + frozen_money + '\'' +
                ", distribut_money='" + distribut_money + '\'' +
                ", underling_number='" + underling_number + '\'' +
                ", pay_points='" + pay_points + '\'' +
                ", address_id='" + address_id + '\'' +
                ", reg_time='" + reg_time + '\'' +
                ", last_login='" + last_login + '\'' +
                ", last_ip='" + last_ip + '\'' +
                ", qq='" + qq + '\'' +
                ", mobile='" + mobile + '\'' +
                ", mobile_validated='" + mobile_validated + '\'' +
                ", oauth='" + oauth + '\'' +
                ", openid='" + openid + '\'' +
                ", unionid='" + unionid + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", email_validated='" + email_validated + '\'' +
                ", nickname='" + nickname + '\'' +
                ", level='" + level + '\'' +
                ", discount='" + discount + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", is_lock='" + is_lock + '\'' +
                ", is_distribut='" + is_distribut + '\'' +
                ", first_leader='" + first_leader + '\'' +
                ", second_leader='" + second_leader + '\'' +
                ", third_leader='" + third_leader + '\'' +
                ", token='" + token + '\'' +
                ", message_mask='" + message_mask + '\'' +
                ", push_id='" + push_id + '\'' +
                ", distribut_level='" + distribut_level + '\'' +
                ", is_vip='" + is_vip + '\'' +
                ", device_code='" + device_code + '\'' +
                ", version_code='" + version_code + '\'' +
                ", phone_code='" + phone_code + '\'' +
                ", username='" + username + '\'' +
                ", message_num='" + message_num + '\'' +
                ", access_token='" + access_token + '\'' +
                '}';
    }
}
